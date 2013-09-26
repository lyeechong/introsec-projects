
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
    /**
     * low level specifically. used to denote if Lyle is receiving bytes through the covert channel
     */
    private boolean receivingThroughCovert = true;
    /**
     * high level specifically. used to denote if Hal is sending stuff
     */
    private boolean runningCovertChannel = false;
    /**
     * used by Hal to get the bits from the input file
     */
    private BitInput bi;
    /**
     * the name of the INPUT file to read from
     */
    private String covertInputMessageFilename;
    private MyFileWriter mfw;
    private List<Integer> al;
    private boolean done = false;
    private int init = 8;
    private int cnt = 0;

    public String getName()
    {
        return name;
    }

    public void setCovertInputMessageFileName(String covertInputMessageFilename)
    {
        this.covertInputMessageFilename = covertInputMessageFilename;
    }

    /**
     * Consumes new bits from Hal and when it reaches 8, it prints it out.
     * @param newBit 
     */
    boolean skip = true;
    public void setBit(int newBit) //assume this stuff works
    {
        if(skip)
        {
            skip = false;
            return;
        }
        
        int next = newBit;
        al.add(next);

        if (al.size() == 8)
        {
            done = !al.contains(1);

            if (!done)
            {
                String byteReceived = al.toString().replaceAll("[\\[\\], ]", "");                
                writeToOutputFile(binaryStringToAsciiChar(byteReceived));
            }
            else
            {
                receivingThroughCovert = false;
                mfw.writeOutputFile();
            }
            al.clear();
        }
    }

    private char binaryStringToAsciiChar(String binaryString)
    {
        char res = (char) Integer.parseInt(binaryString, 2);
        return res;
    }

    /**
     * This is for Lyle to write to the output file the byte he got from Hal through the covert channel
     * @param byteReceived 
     */
    private void writeToOutputFile(String byteReceived)
    {
        if (mfw == null)
        {
            mfw = new MyFileWriter(covertInputMessageFilename + ".out");
        }
        mfw.writeString(byteReceived);
    }

    /**
     * This is for Lyle to write to the output file the byte he got from Hal through the covert channel
     * @param byteReceived 
     */
    private void writeToOutputFile(char byteReceived)
    {
        if (mfw == null)
        {
            mfw = new MyFileWriter(covertInputMessageFilename + ".out");
        }
        mfw.writeString(byteReceived);
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
                //instructionList.add(new RunInstruction("run Hal", true));
            }
            else
            {
                StaticStuff.setDoneWithCovertChannel(true);
            }
        }

        return instructionList;
    }

    /**
     * This is used by Hal, not Lyle
     * @return
     * @throws IOException 
     */
    private int getNextBit() throws IOException //works
    {
        if (!runningCovertChannel)
        {
            //set up the covert channel
            assert covertInputMessageFilename != null;
            bi = new BitInput(covertInputMessageFilename);
            runningCovertChannel = true;

        }
        int next = bi.getNextBit();
        return next;
    }
}
