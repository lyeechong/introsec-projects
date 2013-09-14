
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

    /**
     * Main method.
     *
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
            if (args.length != 2)
            {
                System.err.println("Wrong number of arguments. Expected: 2, actual: " + args.length);
                return;
            }

            String instructionList1FileName = args[0];
            String instructionList2FileName = args[1];

            System.out.println("Reading from file (1): " + instructionList1FileName);
            System.out.println("Reading from file (2): " + instructionList2FileName + "\n");

            Scanner ilFile1 = new Scanner(new File(instructionList1FileName));
            Scanner ilFile2 = new Scanner(new File(instructionList2FileName));

            SubjectProcessor sp1 = new SubjectProcessor(rf);
            SubjectProcessor sp2 = new SubjectProcessor(rf);

            // -- instruction file 1
            while (ilFile1.hasNextLine())
            {
                String instruction = ilFile1.nextLine();
                sp1.addInstruction(instruction);
            }
            // -- instruction file 2
            while (ilFile2.hasNextLine())
            {
                String instruction = ilFile2.nextLine();
                sp2.addInstruction(instruction);
            }

            sp1.start();
            sp2.start();

            try
            {
                sp1.getThread().join();
                sp2.getThread().join();
            }
            catch (InterruptedException e)
            {
                System.err.println("Interrupted when joining.");
            }

        }
        catch (FileNotFoundException e)
        {
            System.err.println("Could not find the input file! (It needs to be called instructionList)");
        }
    }
}
