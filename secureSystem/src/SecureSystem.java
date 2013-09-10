
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


/**
 *
 * @author lchong
 */
public class SecureSystem {

    /**
     * Main method.
     * @param args first index in args is the instructionList
     */
    public static void main(String[] args) throws FileNotFoundException {
        SecureSystem ss = new SecureSystem();
        ss.run(args);
    }

    private void run(String[] args) throws FileNotFoundException {
        if (args.length != 1) {
            return;
        }

        String instructionList = args[0];
        
        Scanner file = new Scanner(new File(instructionList));
        
        
        ReferenceMonitor rf = new ReferenceMonitor();
        
        while (file.hasNextLine()) {
            String instruction = file.nextLine();            
            InstructionObject result = rf.performInstruction(instruction);
            System.out.println("Instruction :: " + instruction);
            System.out.println("Result :: " + result.getOutput());            
        }        
    }
}
