
import java.io.PrintStream;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author shen
 */
public class SubjectProcessorCallHelper
{

    private ReferenceMonitor rf;
    private final boolean DEBUG = true;

    public SubjectProcessorCallHelper(ReferenceMonitor rf)
    {
        this.rf = rf;
    }

    public synchronized void runLogic(String instruction)
    {

        InstructionObject result = rf.performInstruction(instruction);
        System.out.println(result.getOutput());
        if (DEBUG)
        {
            printState();
        }
    }

    private synchronized void printState()
    {
        PrintStream out = System.out;
        synchronized (out)
        {
            out.println("The current state is:");
            for (String key : SystemObjectsContainer.getKeys())
            {
                out.println("\t" + key + " has value: " + SystemObjectsContainer.get(key).getValue());
            }
            for (String key : SystemSubjectsContainer.getKeys())
            {
                out.println("\t" + key + " has recently read: " + SystemSubjectsContainer.get(key).getTemp());
            }
            out.println("");
        }
    }
}
