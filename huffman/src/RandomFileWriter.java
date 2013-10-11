
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Map;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author lchong
 */
public class RandomFileWriter
{

    Map<Integer, Integer> map;
    int[] probArray;
    int total;

    public RandomFileWriter(Map<Integer, Integer> map, int total)
    {
        this.map = map;
        this.total = total;
        probArray = new int[total];
        generateProbArray();
    }

    private void generateProbArray()
    {
        int pos = 0;
        for (int c : map.keySet())
        {
            int val = map.get(c);
            for (int i = pos; i < val + pos; i++)
            {
                probArray[i] = c;
            }
            pos += val;
        }
    }

    private int getRandChar()
    {
        int pos = (int) (Math.random() * total);
//        System.out.println("probarray" + Arrays.toString(probArray));
        return probArray[pos];
    }

    public String generateFileText(int fileLength)
    {
        StringBuffer b = new StringBuffer();
        for (int i = 0; i < fileLength; i++)
        {
            int c = getRandChar();            
            String val = SymbolLookup.symbols.get(c);
            b.append(val);
        }
        return b.toString();
    }

    public void writeFileTextToFile(String fileName, int fileLength) throws FileNotFoundException, UnsupportedEncodingException
    {
        PrintWriter writer = new PrintWriter(fileName, "US-ASCII");
        writer.print(generateFileText(fileLength));
        writer.close();
    }
}
