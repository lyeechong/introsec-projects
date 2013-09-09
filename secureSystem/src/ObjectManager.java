
/**
 * Performs actions requested by subjects. (Note that this class doesn't do the
 * checking of clearance. That task is in the ReferenceMonitor).
 *
 * @author lchong
 */
public class ObjectManager
{
    public ObjectManager()
    {
    
    }
    
    /**
     * Perform a read on an object.
     * @param objectToReadFrom the object to read from
     * @return the object's value
     */
    public String performRead(SystemObject objectToReadFrom)
    {
        return objectToReadFrom.getValue();
    }
}
