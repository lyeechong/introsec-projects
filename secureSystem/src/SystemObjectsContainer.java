
import java.util.HashMap;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lchong
 */
public class SystemObjectsContainer {
        private static HashMap<String, SystemObject> soHash; //<Name, Object>    
    
    static
    {
        soHash = new HashMap<>();                
    }
    
    public static SystemObject get(String s)
    {
        return soHash.get(s.toLowerCase());
    }
    
    public static SystemObject put(String key, SystemObject value)
    {
        return soHash.put(key.toLowerCase(), value);
    }
}
