
/**
 *
 * @author lchong
 */
public class CovertChannel
{

    private static final String VERBOSE_FLAG = "v";

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

        StaticStuff.setDoneWithCovertChannel(false);
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
            StaticStuff.setVerbose(args[0].equalsIgnoreCase(VERBOSE_FLAG));
            inputFileName = args[1];
        }
        else if (numArgs == 1)
        {
            StaticStuff.setVerbose(false);
            inputFileName = args[0];
        }
        else
        {
            inputFileName = null;
            assert false;
        }

        if (StaticStuff.isVerbose())
        {
            LogWriterManager.setOutputFileName("log");
        }

        SystemSubjectsContainer.get("Hal").setCovertInputMessageFileName(inputFileName);
        SystemSubjectsContainer.get("Lyle").setCovertInputMessageFileName(inputFileName);

        long time = 0;

        long start = System.currentTimeMillis();

        while (!StaticStuff.isDoneWithCovertChannel())
        {
            StaticStuff.getRf().performInstruction("run Hal");
        }

        long stop = System.currentTimeMillis();
        time = stop - start;
        System.out.println(time);

        if (StaticStuff.isVerbose())
        {
            LogWriterManager.getLog().writeOutputFile();
        }
    }
}
