
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author lchong
 */
public class SystemSecurityLevelsContainer
{

    private static Set<SecurityLevel> slSet; //<Name, Object>    

    static
    {
        slSet = new HashSet<>();
    }

    /**
     * Returns the SystemObject, or null if it does not exist
     * @param s
     * @return 
     */
    public static boolean contains(Object o)
    {
        return slSet.contains(o);
    }

    public static boolean add(SecurityLevel sl)
    {
        return slSet.add(sl);
    }

    public static Set<SecurityLevel> getAllSecurityLevels()
    {
        return slSet;
    }
}
