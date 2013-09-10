
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author lchong
 */
public class SecureSystem {

    ReferenceMonitor rf;

    /**
     * Main method.
     * @param args first index in args is the instructionList
     */
    public static void main(String[] args) throws FileNotFoundException {
        SecureSystem ss = new SecureSystem();
        ss.setup();
        ss.run(args);
    }

    public SecureSystem() {
        rf = new ReferenceMonitor();
    }

    private void setup() {
        // LOW and HIGH are constants defined in the SecurityLevel 
        // class, such that HIGH dominates LOW.

        SecurityLevel low = SecurityLevel.LOW;
        SecurityLevel high = SecurityLevel.HIGH;

        // We add two subjects, one high and one low.

        rf.createSubject("lyle", low);
        rf.createSubject("hal", high);


        // We add two objects, one high and one low.

        rf.createObject("Lobj", low, 0);
        rf.createObject("Hobj", high, 0);
    }

    private void run(String[] args) {

        try {
            if (args.length != 1) {
                return;
            }

            String instructionList = args[0];

            Scanner file = new Scanner(new File(instructionList));


            while (file.hasNextLine()) {
                String instruction = file.nextLine();
                InstructionObject result = rf.performInstruction(instruction);
                System.out.println("Instruction :: " + instruction);
                System.out.println("Result :: " + result.getOutput());
            }
        } catch (FileNotFoundException e) {
            System.out.println("blaggghhh");
        }
    }
}
