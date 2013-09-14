
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * A thread for a subject. This will queue up the requested instructions the
 * subject wants to execute.
 *
 * @author lchong
 */
public class SubjectProcessor implements Runnable
{

    private BlockingQueue<String> instructions;
    private SubjectProcessorCallHelper spch;
    private ReferenceMonitor rf;
    private Thread thread;

    public SubjectProcessor(ReferenceMonitor rf)
    {
        instructions = new LinkedBlockingQueue<>();
        this.rf = rf;
    }

    public void start()
    {
        thread = new Thread(this);
        thread.start();
    }

    public Thread getThread()
    {
        return thread;
    }

    @Override
    public void run()
    {
        spch = new SubjectProcessorCallHelper(rf);
        while (!instructions.isEmpty())
        {
            try
            {
                synchronized (spch)
                {
                    spch.runLogic(instructions.take());
                }
            }
            catch (InterruptedException ex)
            {
                System.err.println("Interrupted exception thrown: " + ex.getMessage());
            }
        }
    }

    public void addInstruction(String instruction)
    {
        instructions.add(instruction);
    }
}
