
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
    private static int counter;
    private boolean receivingThroughCovert = true;

    public String getName()
    {
        return name;
    }

    public void setBit(int newBit)
    {
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
            bit |= newBit;
            bit &= 255;
        }
        else
        {
            assert false;
        }

        if ((bit & 255) == 0 && receivingThroughCovert)
        {
            receivingThroughCovert = false;
        }

        if (receivingThroughCovert)
        {
            counter++;
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
    }

    public void setTempValue(int value)
    {
        temp = value;
    }

    public void run()
    {
        boolean isHighLevelSubject = 
        
        
        
        
        
        
        if (true)
        {
            //do high
        }
        else
        {
            //do low level stuff
        }

    }
}
