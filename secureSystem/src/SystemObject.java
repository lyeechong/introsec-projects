
/**
 * An object in the system. eg. HObj
 *
 * @author lchong
 */
public class SystemObject
{

    /**
     * The name of the object.
     */
    private String name;
    /**
     * What the object stores. (This is what will be returned when someone wants
     * to read the object).
     */
    private String value;

    public SystemObject(String name, String value)
    {
        this.name = name;
        this.value = value;
    }

    /**
     * Meant for if someone wants to write to the object. (Replace the value).
     *
     * @param value
     */
    public void setValue(String value)
    {
        this.value = value;
    }

    /**
     * Meant for if someone wants to read the object. (Obtain the value).
     *
     * @return
     */
    public String getValue()
    {
        return value;
    }

    public String getName()
    {
        return name;
    }
}
