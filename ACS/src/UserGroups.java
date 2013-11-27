
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

    public static final String ROOT_GROUP_NAME = "root";
    
    static
    {
        userGroups = new HashMap<>();
    }
    
    public static boolean isRoot(String user)
    {
        return userGroups.get(user).equals(ROOT_GROUP_NAME);
    }
}
