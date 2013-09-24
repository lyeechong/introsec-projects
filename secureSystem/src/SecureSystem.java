
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author lchong
 */
public class SecureSystem
{

    ReferenceMonitor rf;
    final boolean DEBUG = true;

    /**
     * Main method.
     * @param args first index in args is the instructionList
     */
    public static void main(String[] args) throws FileNotFoundException
    {
        SecureSystem ss = new SecureSystem();
        ss.setup();
        ss.run(args);
    }

    public SecureSystem()
    {
        rf = new ReferenceMonitor();
    }

    private void printState()
    {
        System.out.println("The current state is:");
        for (String key : SystemObjectsContainer.getKeys())
        {
            System.out.println("\t" + key + " has value: " + SystemObjectsContainer.get(key).getValue());
        }
        for (String key : SystemSubjectsContainer.getKeys())
        {
            System.out.println("\t" + key + " has recently read: " + SystemSubjectsContainer.get(key).getTemp());
        }
        System.out.println("");
    }

    private void setup()
    {
        // LOW and HIGH are constants defined in the SecurityLevel 
        // class, such that HIGH dominates LOW.

        SecurityLevel low = new SecurityLevel("LOW", 0);
        SecurityLevel high = new SecurityLevel("HIGH", 1);
        
        rf.createSecurityLevel(low);
        rf.createSecurityLevel(high);

        // We add two subjects, one high and one low.

        rf.createSubject("Lyle", low);
        rf.createSubject("Hal", high);


        // We add two objects, one high and one low.

        rf.createObject("Lobj", low, 0);
        rf.createObject("Hobj", high, 0);
    }

    private void run(String[] args)
    {

        try
        {
            if (args.length != 1)
            {
                System.err.println("Wrong number of arguments. Expected: 100, actual: " + args.length);
                return;
            }

            String instructionListFileName = args[0];

            System.out.println("Reading from file: " + instructionListFileName + "\n");

            Scanner ilFile = new Scanner(new File(instructionListFileName));


            while (ilFile.hasNextLine())
            {
                String instruction = ilFile.nextLine();
                InstructionObject result = rf.performInstruction(instruction);
                System.out.println(result.getOutput());
                if (DEBUG)
                {
                    printState();
                }
            }
        }
        catch (FileNotFoundException e)
        {
            System.err.println("Could not find the input file! (It needs to be called instructionList)");
        }
    }
}
