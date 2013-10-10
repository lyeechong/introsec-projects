
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
    }

    private void run(String[] args) throws IOException
    {
        if (args.length != 1)
        {
            System.err.println("Expected 1 argument, received: " + args.length);
            return;
        }
        String frequenciesFileName = args[0];

        Map<Character, Integer> freqMap = new HashMap<>();

        int total = 0;
        char character = 'A';

        Scanner file = new Scanner(new File(frequenciesFileName));
        List<Integer> list = new ArrayList<>();
        while (file.hasNextLine())
        {
            String line = file.nextLine();
            int value = Integer.parseInt(line);
            list.add(value);
            total += value;
            freqMap.put((character++), value);
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
            System.out.println((char) (i + 65) + "=" + symbolCodes.get(i));
        }
        
        RandomFileWriter rfw = new RandomFileWriter(freqMap, total);
//        System.out.println("freqmap" + freqMap);
//        System.out.println("total" + total);
        
        String fileName = "testText";
        
        rfw.writeFileTextToFile(fileName, 5);
        
        MyEncoder me = new MyEncoder(symbolCodes);
        me.encodeFile(fileName);
        
        MyDecoder md = new MyDecoder(symbolCodes);
        md.decodeFile(fileName);

//        CanonicalCode canonCode = new CanonicalCode(code, 257);
//        code = canonCode.toCodeTree();
//        HuffmanCompress.writeCode(bitOut, canonCode);
//        HuffmanCompress.compress(code, in, bitOut);
//        bitOut.close();
//        return out.toByteArray();
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
            //sb.append(String.format("Code %s: Symbol %d%n", prefix, ((Leaf) node).symbol));
        }
        else
        {
            throw new AssertionError("Illegal node type");
        }
    }
}
