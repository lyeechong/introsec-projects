
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author lchong
 */
public class CovertChannel
{

    private boolean verbose = false;

    public CovertChannel()
    {
    }

    public static void main(String[] args)
    {
        CovertChannel cc = new CovertChannel();
        cc.run(args);
    }

    private void run(String[] args)
    {
        int numArgs = args.length;
        String inputFileName;

        try
        {
            if (numArgs > 2)
            {
                System.err.println("Wrong number of arguments. Expected: 100 or 2, actual: " + numArgs);
                return;
            }

            if (numArgs == 2)
            {
                verbose = args[0].equalsIgnoreCase("v");
                inputFileName = args[1];
            }
            else if (numArgs == 1)
            {
                inputFileName = args[0];
            }
            else
            {
                inputFileName = null;
                assert false;
            }

            Scanner inputFile = new Scanner(new File(inputFileName));

            while (inputFile.hasNextLine())
            {
                String line = inputFile.nextLine();
                System.out.println(line); //testing
            }
        }
        catch (FileNotFoundException e)
        {
            System.err.println("Could not find the input file!");
        }
    }
}
