
import java.util.HashMap;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lchong
 */
public class SystemSubjectsContainer {
    private static HashMap<String, SystemSubject> ssHash; //<Name, Object>    
    
    static
    {
        ssHash = new HashMap<>();                
    }
    
    public static SystemSubject get(String s)
    {
        return ssHash.get(s);
    }
    
    public static SystemSubject put(String key, SystemSubject value)
    {
        return ssHash.put(key, value);
    }
}
