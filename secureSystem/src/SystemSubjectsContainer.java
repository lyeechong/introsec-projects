
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
public class SystemSubjectsContainer
{

    private static Map<String, SystemSubject> ssHash; //<Name, Object>    

    static
    {
        ssHash = new HashMap<>();
    }

    /**
     * Returns the SystemSubject, or null if it does not exist
     *
     * @param s
     * @return
     */
    public static synchronized SystemSubject get(String s)
    {
        return ssHash.get(s.toLowerCase());
    }

    public static synchronized SystemSubject put(String key, SystemSubject value)
    {
        return ssHash.put(key.toLowerCase(), value);
    }

    public static synchronized Set<String> getKeys()
    {
        return ssHash.keySet();
    }
}
