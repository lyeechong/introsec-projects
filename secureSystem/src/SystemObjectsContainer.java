
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author lchong
 */
public class SystemObjectsContainer
{

    private static Map<String, SystemObject> soHash; //<Name, Object>    

    static
    {
        soHash = new HashMap<>();
    }

    /**
     * Returns the SystemObject, or null if it does not exist
     *
     * @param s
     * @return
     */
    public static synchronized SystemObject get(String s)
    {
        return soHash.get(s.toLowerCase());
    }

    public static synchronized SystemObject put(String key, SystemObject value)
    {
        return soHash.put(key.toLowerCase(), value);
    }

    public static synchronized Set<String> getKeys()
    {
        return soHash.keySet();
    }
}
