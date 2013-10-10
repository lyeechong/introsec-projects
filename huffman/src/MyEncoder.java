
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

    public void encodeFile(String fileName) throws FileNotFoundException, UnsupportedEncodingException
    {
        String outputFileName = fileName + ".enc1";
        StringBuffer output = new StringBuffer();


        Scanner file = new Scanner(new File(fileName));

        while (file.hasNextLine())
        {
            String line = file.nextLine();
            for (char c : line.toCharArray())
            {
                String encodedChar = symbolCodes.get((int) c - 65);
                output.append(encodedChar);
            }
        }
        PrintWriter writer = new PrintWriter(outputFileName, "US-ASCII");
        writer.print(output.toString());
        writer.close();
    }
}
