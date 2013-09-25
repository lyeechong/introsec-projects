
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A subject in the system. eg. Hal
 *
 * @author lchong
 */
public class SystemSubject
{

    /**
     * The name of the subject.
     */
    private String name;
    /**
     * The value the subject last read. Initialized to zero.
     */
    private int temp;
    private int bit;
    private int counter;
    private boolean receivingThroughCovert = true; //low level specifically
    private boolean runningCovertChannel = false; //high level specifically
    private int initialCounter = 8;
    private ByteInput bi;
    private String covertInputMessageFilename;

    public String getName()
    {
        return name;
    }

    public void setCovertInputMessage(String covertInputMessageFilename)
    {
        this.covertInputMessageFilename = covertInputMessageFilename;
    }

    /**
     * Consumes new bits from Hal and when it reaches 8, it prints it out.
     * @param newBit 
     */
    public void setBit(int newBit) //assume this stuff works
    {
        /*
        initialCounter--;
        if (newBit == 1)
        {
        bit = bit << 1;
        bit |= newBit;
        bit &= 255;
        receivingThroughCovert = true;
        }
        else if (newBit == 0)
        {
        bit = bit << 1;
        bit &= newBit;
        bit &= 255;
        }
        else
        {
        assert false;
        }
        
        if ((bit & 255) == 0 && receivingThroughCovert && initialCounter <= 0)
        {
        receivingThroughCovert = false;
        }
        
        System.out.println("bit received is :: " + newBit);
        
        if (receivingThroughCovert)
        {
        counter++;
        if (counter == 8)
        {
        for (int i = 0; i < 100; i++)
        {
        System.out.println("Byte received! :: " + bit);
        }
        bit = 0;
        counter = 0;
        }
        }
         * */
        int next = newBit;
        if (al.size() != 8)
        {
            for (int i = 0; i < 8; i++)
            {
                al.add(0);
            }
        }
        al.add(next);
        init--;

        System.out.println("hello " + next);
        al.remove(0);
        done = true && init < 0;
        for (int i : al)
        {
            if (i != 0)
            {
                done = false;
            }
        }
        if (cnt >= 8)
        {
            cnt = 0;
            System.out.println("-------------------------the al was :: " + al.toString().replaceAll("[\\[\\], ]", ""));
        }
        cnt++;
        if (done == true)
        {
            receivingThroughCovert = false;
        }
    }

    public int getTemp()
    {
        return temp;
    }

    public SystemSubject(String name)
    {
        this.name = name;
        temp = 0;
        al = new ArrayList<>();
    }

    public void setTempValue(int value)
    {
        temp = value;
    }

    public List<Instruction> run()
    {
        SystemSubject ss = SystemSubjectsContainer.get(this.getName());
        boolean isHighLevelSubject = StaticStuff.getCc().getSubjectClearance(ss).getName().equalsIgnoreCase("HIGH");
        List<Instruction> instructionList = new ArrayList<>();

        if (isHighLevelSubject)
        {
            // -- Do high

            // determine if the next bit is a 1 or a 0            
            int nextBit = 100;
            try
            {
                nextBit = getNextBit();
            }
            catch (IOException ex)
            {
                System.err.println("Something went wrong: " + ex.getMessage());
            }

            if (nextBit == 1)
            {
                // don't create the object
            }
            else if (nextBit == 0)
            {
                // create the object
                instructionList.add(new CreateInstruction("create Hal obj", true));
            }
            else
            {
                assert false;
            }

            instructionList.add(new RunInstruction("run Lyle", true));
        }
        else
        {
            // -- Do low level stuff
            if (receivingThroughCovert)
            {
                setBit(temp);
            }

            instructionList.add(new RunInstruction("create Lyle obj", true));
            instructionList.add(new RunInstruction("write Lyle obj 1", true));
            instructionList.add(new RunInstruction("read Lyle obj", true));
            instructionList.add(new RunInstruction("destroy Lyle obj", true));
            if (receivingThroughCovert)
            {
                instructionList.add(new RunInstruction("run Hal", true));
            }
        }

        return instructionList;
    }
    List<Integer> al;
    boolean done = false;
    int init = 8;
    int cnt = 0;

    private int getNextBit() throws IOException //works
    {
        if (!runningCovertChannel)
        {
            //set up the covert channel
            assert covertInputMessageFilename != null;
            bi = new ByteInput(covertInputMessageFilename);
            runningCovertChannel = true;
        }
        int next = bi.getNextBit();
        return next;
    }
}
