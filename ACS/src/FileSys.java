
import java.net.FileNameMap;
import java.util.HashMap;
import java.util.Map;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author lchong
 */
public class FileSys
{

    Map<String, File> fileMap;

    public FileSys()
    {
        fileMap = new HashMap<>();
    }

    public void createRootUserAndStickHimInRootGroup()
    {
        //TODO
    }

    private boolean isInSameGroup(String user, File file)
    {
        return UserGroups.userGroups.get(user).equals(file.getGroup());
    }

    public boolean doRead(String user, String fileName)
    {
        File file = fileMap.get(fileName);
        if (file.otherUsersCanRead())
        {
            return true;
        }
        else if (file.groupMembersCanRead() && isInSameGroup(user, file))
        {
            return true;
        }
        else if (file.isOwner(user) && file.ownerCanRead())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean doWrite(String user, String fileName)
    {
        File file = fileMap.get(fileName);
        if (file.otherUsersCanWrite())
        {
            return true;
        }
        else if (file.groupMembersCanWrite() && isInSameGroup(user, file))
        {
            return true;
        }
        else if (file.isOwner(user) && file.ownerCanWrite())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean doExecute(String user, String fileName)
    {
        File file = fileMap.get(fileName);
        if (file.otherUsersCanExecute())
        {
            return true;
        }
        else if (file.groupMembersCanExecute() && isInSameGroup(user, file))
        {
            return true;
        }
        else if (file.ownerCanExecute() && file.isOwner(user))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean doChmod(String user, String fileName, String extra)
    {
        File file = fileMap.get(fileName);
        if (UserGroups.isRoot(user) || file.isOwner(user))
        {
            //can do the chmod
            boolean[] permissions = new boolean[12];
            for (int i = 0; i < extra.length(); i++)
            {
                char c = extra.charAt(i);
                int p = c - 48;
                Stirng s = Integer.toBinaryString(p);
                //so just go through each char in s and set the permission boolean things
            }
            file.setPermissions(permissions);
            return true;
        }
        else
        {
            //nope, can't do the chmod
            return false;
        }
    }

    public void doExit()
    {
        //TODO
    }
}
