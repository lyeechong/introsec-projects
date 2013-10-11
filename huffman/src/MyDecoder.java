
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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
public class MyDecoder
{

    Map<Integer, String> symbolCodes;

    public MyDecoder(Map<Integer, String> symbolCodes)
    {
        this.symbolCodes = symbolCodes;
    }

    public void decodeFile(String fileName, boolean pairs) throws IOException
    {
        String outputFileName;
        

        StringBuffer output = new StringBuffer();

        Scanner file = null;
        if (pairs)
        {
            file = new Scanner(new File(fileName + ".enc2"));
            outputFileName = fileName + ".dec2";
        }
        else
        {
            file = new Scanner(new File(fileName + ".enc1"));
            outputFileName = fileName + ".dec1";
        }

        String currEnc = "";
        while (file.hasNextLine())
        {
            String line = file.nextLine();
            for (char c : line.toCharArray())
            {
                currEnc += c;

                String res = reverseLookUp(currEnc);
                if (res != null)
                {
//                    System.out.println("res" + res);
                    String encodedChar = res + "";
                    output.append(encodedChar);
                    currEnc = "";
                }
            }
        }
        PrintWriter writer = new PrintWriter(outputFileName, "US-ASCII");
        writer.print(output.toString());
        writer.close();
    }

    private String reverseLookUp(String str)
    {
        for (int c : symbolCodes.keySet())
        {
            String mapVal = symbolCodes.get(c);
//            System.out.println("MapVal :: " + mapVal + " ---- str :: " + str);
            if (mapVal.equals(str))
            {
                return SymbolLookup.symbols.get(c);
            }
        }
        return null;
    }
}
