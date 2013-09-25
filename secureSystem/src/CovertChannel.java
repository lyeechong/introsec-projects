
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
        cc.setup();
        cc.run(args);
    }

    private void setup()
    {
        // LOW and HIGH are constants defined in the SecurityLevel 
        // class, such that HIGH dominates LOW.

        SecurityLevel low = new SecurityLevel("LOW", 0);
        SecurityLevel high = new SecurityLevel("HIGH", 1);

        StaticStuff.setRf(new ReferenceMonitor());
        
        StaticStuff.getRf().createSecurityLevel(low);
        StaticStuff.getRf().createSecurityLevel(high);

        // We add two subjects, one high and one low.

        StaticStuff.getRf().createSubject("Lyle", low);
        StaticStuff.getRf().createSubject("Hal", high);
        
    }

    private void run(String[] args)
    {
        int numArgs = args.length;
        String inputFileName;

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

        SystemSubjectsContainer.get("Hal").setCovertInputMessage(inputFileName);
        StaticStuff.getRf().performInstruction("run Hal");

    }
}
