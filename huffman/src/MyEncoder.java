
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
public class MyEncoder
{

    Map<Integer, String> symbolCodes;

    public MyEncoder(Map<Integer, String> symbolCodes)
    {
        this.symbolCodes = symbolCodes;
    }

    public void encodeFile(String fileName, boolean pairs) throws FileNotFoundException, UnsupportedEncodingException
    {
        String outputFileName;
        if (pairs)
        {
            outputFileName = fileName + ".enc2";
        }
        else
        {
            outputFileName = fileName + ".enc1";
        }
        StringBuffer output = new StringBuffer();


        Scanner file = new Scanner(new File(fileName));

        while (file.hasNextLine())
        {
            String line = file.nextLine();
            String curr = "";
            for (char c : line.toCharArray())
            {
                curr += c;
                int i = reverseLookup(curr);
                if (i != -1)
                {
                    curr = "";
                    String encodedChar = symbolCodes.get(i);
                    output.append(encodedChar);
                }
            }
        }
        PrintWriter writer = new PrintWriter(outputFileName, "US-ASCII");
        writer.print(output.toString());
        writer.close();
    }

    private int reverseLookup(String str)
    {
        if (str.length() > 2)
        {
            System.out.println("bad stuff");
        }
        for (int i : SymbolLookup.symbols.keySet())
        {
            String val = SymbolLookup.symbols.get(i);
            if (val.equals(str))
            {
                return i;
            }
        }
        return -1;
    }
}
