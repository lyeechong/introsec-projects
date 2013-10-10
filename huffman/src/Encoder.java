
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
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

        Scanner file = new Scanner(new File(frequenciesFileName));
        while(file.hasNextLine())
        {
            String line = file.nextLine();
            System.out.println("Line :: " + line);
        }
    }
}
