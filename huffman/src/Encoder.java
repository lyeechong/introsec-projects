
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author lchong
 */
public class Encoder
{

    private FrequencyTable freqTable;

    public Encoder()
    {
    }

    public static void main(String[] args) throws IOException
    {
        Encoder e = new Encoder();
        e.run(args);
        e.runPairs(args);
    }

    private void run(String[] args) throws IOException
    {
        System.out.println("Using single symbols in encoding:");
        SymbolLookup.symbols.clear();
        if (args.length != 2)
        {
            System.err.println("Expected 2 argument, received: " + args.length);
            return;
        }
        String frequenciesFileName = args[0];
        int k = Integer.parseInt(args[1]);

        Map<Integer, Integer> freqMap = new HashMap<>();


        int total = 0;
        int symbol = 0;

        Scanner file = new Scanner(new File(frequenciesFileName));
        List<Integer> list = new ArrayList<>();
        while (file.hasNextLine())
        {
            String line = file.nextLine();
            int value = Integer.parseInt(line);
            list.add(value);
            total += value;
            freqMap.put(symbol++, value);
        }

        for (int i = 0; i < 26; i++)
        {
            SymbolLookup.symbols.put(i, "" + (char) (i + 65));
        }

        int arr[] = new int[list.size()];
        int pos = 0;
        for (int i : list)
        {
            arr[pos++] = i;
        }

        freqTable = new FrequencyTable(arr);
        CodeTree code = freqTable.buildCodeTree();
        Map<Integer, String> symbolCodes = traverseCodeTree(code);

        for (int i : symbolCodes.keySet())
        {
            System.out.println(SymbolLookup.symbols.get(i) + "=" + symbolCodes.get(i));
        }


        boolean printEntropy = true;
        if (printEntropy)
        {
            EntropyCalculator ec = new EntropyCalculator();
            double entropy = ec.calculateEntropy(freqMap, total);
            double efficiency = ec.calculateEncodingEfficiency(freqMap, total, symbolCodes);
            System.out.println("Results for using one character encoding:");
            System.out.println("Entropy      :: " + entropy);
            System.out.println("Efficiency   :: " + efficiency);
            System.out.println("Percent diff :: " + (entropy / efficiency * 100) + "%");
        }

        RandomFileWriter rfw = new RandomFileWriter(freqMap, total);

        String fileName = "testText";

        rfw.writeFileTextToFile(fileName, k);

        MyEncoder me = new MyEncoder(symbolCodes);
        me.encodeFile(fileName, false);

        MyDecoder md = new MyDecoder(symbolCodes);
        md.decodeFile(fileName, false);
    }

    private void runPairs(String[] args) throws IOException
    {
        System.out.println("");
        System.out.println("Using pairs for the encoding:");
        SymbolLookup.symbols.clear();
        if (args.length != 2)
        {
            System.err.println("Expected 2 argument, received: " + args.length);
            return;
        }
        String frequenciesFileName = args[0];
        int k = Integer.parseInt(args[1]);

        Map<Integer, Integer> freqMap = new HashMap<>();
        Map<Integer, Integer> tempFreqMap = new HashMap<>();

        int total = 0;
        int symbol = 0;

        Scanner file = new Scanner(new File(frequenciesFileName));
        List<Integer> list = new ArrayList<>();

        int tempSymbol = 0;
        int tempTotal = 0;
        while (file.hasNextLine())
        {
            String line = file.nextLine();
            int value = Integer.parseInt(line);
            tempFreqMap.put(tempSymbol++, value);
            tempTotal += value;
        }

        total = (int) Math.pow(tempTotal, 2);


        for (int i : tempFreqMap.keySet())
        {
            for (int j : tempFreqMap.keySet())
            {
                int val1 = tempFreqMap.get(i);
                int val2 = tempFreqMap.get(j);
                int tot = val1 * val2;
                freqMap.put(symbol, tot);
                SymbolLookup.symbols.put(symbol, (char) (65 + i) + "" + (char) (j + 65));
                symbol++;
                list.add(tot);
            }
        }




        int arr[] = new int[list.size()];
        int pos = 0;
        for (int i : list)
        {
            arr[pos++] = i;
        }

        freqTable = new FrequencyTable(arr);
        CodeTree code = freqTable.buildCodeTree();
        Map<Integer, String> symbolCodes = traverseCodeTree(code);

        for (int i : symbolCodes.keySet())
        {
            System.out.println(SymbolLookup.symbols.get(i) + "=" + symbolCodes.get(i));
        }

        boolean printEntropy = true;
        if (printEntropy)
        {
            EntropyCalculator ec = new EntropyCalculator();
            double entropy = ec.calculateEntropy(freqMap, total);
            double efficiency = ec.calculateEncodingEfficiency(freqMap, total, symbolCodes);
            System.out.println("Results for using two character encoding:");
            System.out.println("Entropy      :: " + entropy);
            System.out.println("Efficiency   :: " + efficiency);
            System.out.println("Percent diff :: " + (entropy / efficiency * 100) + "%");
        }


        String fileName = "testText";

        MyEncoder me = new MyEncoder(symbolCodes);
        me.encodeFile(fileName, true);

        MyDecoder md = new MyDecoder(symbolCodes);
        md.decodeFile(fileName, true);

    }

    public Map<Integer, String> traverseCodeTree(CodeTree code)
    {
        Map<Integer, String> sb = new HashMap<>();
        traverseCodeTree("", code.getRoot(), sb);
        return sb;
    }

    private static void traverseCodeTree(String prefix, Node node, Map<Integer, String> sb)
    {
        if (node instanceof InternalNode)
        {
            InternalNode internalNode = (InternalNode) node;
            traverseCodeTree(prefix + "0", internalNode.leftChild, sb);
            traverseCodeTree(prefix + "1", internalNode.rightChild, sb);
        }
        else if (node instanceof Leaf)
        {
            sb.put(((Leaf) node).symbol, prefix);
        }
        else
        {
            throw new AssertionError("Illegal node type");
        }
    }
}
