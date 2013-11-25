
import java.util.HashMap;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author lchong
 */
public class UserGroups
{

    /**
     * Map from user (names) to group (names)
     */
    static HashMap<String, String> userGroups;

    static
    {
        userGroups = new HashMap<>();
    }
    
    public static boolean isRoot(String user)
    {
        //TODO
        return true;
    }
}
