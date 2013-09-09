
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
    
    public SystemSubject(String name)
    {
        this.name = name;
        temp = 0;
    }
}
