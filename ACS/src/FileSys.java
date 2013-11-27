
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

    private MyFileWriter mfw;
    private Map<String, MyFile> fileMap; //maps filenames to MyFile objects

    public FileSys()
    {
        fileMap = new HashMap<>();
        mfw = new MyFileWriter("state.log");
    }

    public void putIntoFileMap(String fileName, MyFile file)
    {
        fileMap.put(fileName, file);
    }

    public void createRootUserAndStickHimInRootGroup()
    {
        UserGroups.userGroups.put("root", UserGroups.ROOT_GROUP_NAME);
    }

    private boolean isInSameGroup(String user, MyFile file)
    {
        return UserGroups.userGroups.get(user).equals(file.getGroup());
    }

    public boolean doRead(String user, String fileName)
    {
        MyFile file = fileMap.get(fileName);
        boolean retVal = false;
        if (file.otherUsersCanRead())
        {
            retVal = true;
        }
        else if (file.groupMembersCanRead() && isInSameGroup(user, file))
        {
            retVal = true;
        }
        else if (file.isOwner(user) && file.ownerCanRead())
        {
            retVal = true;
        }
        figureOutWhoIsRunningActionAndOutput(Commands.READ.name(), user, retVal, file);
        return retVal;
    }

    public boolean doWrite(String user, String fileName)
    {
        boolean retVal = false;
        MyFile file = fileMap.get(fileName);
        if (file.otherUsersCanWrite())
        {
            retVal = true;
        }
        else if (file.groupMembersCanWrite() && isInSameGroup(user, file))
        {
            retVal = true;
        }
        else if (file.isOwner(user) && file.ownerCanWrite())
        {
            retVal = true;
        }
        figureOutWhoIsRunningActionAndOutput(Commands.WRITE.name(), user, retVal, file);
        return retVal;
    }

    public boolean doExecute(String user, String fileName)
    {
        boolean retVal = false;
        MyFile file = fileMap.get(fileName);
        if (file.otherUsersCanExecute())
        {
            retVal = true;
        }
        else if (file.groupMembersCanExecute() && isInSameGroup(user, file))
        {
            retVal = true;
        }
        else if (file.ownerCanExecute() && file.isOwner(user))
        {
            retVal = true;
        }
        figureOutWhoIsRunningActionAndOutput(Commands.EXECUTE.name(), user, retVal, file);
        return retVal;
    }

    public boolean doSilentChmod(String user, String fileName, String extra)
    {

        boolean retVal = false;
        MyFile file = fileMap.get(fileName);
        if (UserGroups.isRoot(user) || file.isOwner(user))
        {
            //can do the chmod
            boolean[] permissions = new boolean[12];
            int ptr = 0;
            for (int i = 0; i < extra.length(); i++)
            {
                char c = extra.charAt(i); //like 7 or 5
                int p = c - 48;
                String s = Integer.toBinaryString(p);
                String padded = String.format("%3s", s).replaceAll(" ", "0");
                assert 3 == padded.length();
                //so just go through each char in s and set the permission boolean things
                for (int k = 0; k < padded.length(); k++)
                {
                    char bit = padded.charAt(k);
                    assert bit == '1' || bit == '0';
                    boolean value = bit == '1';
                    permissions[ptr++] = value;
                }

            }
            file.setPermissions(permissions);
            retVal = true;
        }
        //figureOutWhoIsRunningActionAndOutput(Commands.CHMOD.name(), user, retVal, file);
        return retVal;
    }

    public boolean doChmod(String user, String fileName, String extra)
    {
        boolean retVal = false;
        MyFile file = fileMap.get(fileName);
        if (UserGroups.isRoot(user) || file.isOwner(user))
        {
            //can do the chmod
            boolean[] permissions = new boolean[12];
            int ptr = 0;
            for (int i = 0; i < extra.length(); i++)
            {
                char c = extra.charAt(i); //like 7 or 5
                int p = c - 48;
                String s = Integer.toBinaryString(p);
                String padded = String.format("%3s", s).replaceAll(" ", "0");
                if (3 != padded.length())
                {
                    retVal = false;
                    figureOutWhoIsRunningActionAndOutput(Commands.CHMOD.name(), user, retVal, file);
                    return false;
                }
                //so just go through each char in s and set the permission boolean things
                for (int k = 0; k < padded.length(); k++)
                {
                    char bit = padded.charAt(k);
                    assert bit == '1' || bit == '0';
                    boolean value = bit == '1';
                    permissions[ptr++] = value;
                }

            }
            file.setPermissions(permissions);
            retVal = true;
        }
        figureOutWhoIsRunningActionAndOutput(Commands.CHMOD.name(), user, retVal, file);
        return retVal;
    }

    /**
     * write the output to state.log
     */
    public void doExit()
    {
        // go through each file and write a line for it with the format
        // mode owner ownergroup filename

        for (String key : fileMap.keySet())
        {
            MyFile currFile = fileMap.get(key);

            String line;
            line = currFile.toString();
            mfw.writeLine(line);
        }
        mfw.writeOutputFile();
    }

    public void figureOutWhoIsRunningActionAndOutput(String action, String user, boolean result, MyFile file)
    {
        String group = UserGroups.userGroups.get(user);
        String res = "";
        if (result)
        {
            res = "1";
        }
        else
        {
            res = "0";
        }
        assert !res.equals("");
        figureOutWhoIsRunningActionAndOutput(action, user, group, res, file);
    }

    private void figureOutWhoIsRunningActionAndOutput(String action, String user, String group, String result, MyFile file)
    {
        String runningUser = user;
        String runningGroup = group;

        if (file.isIdUserSet()) //. If set, the first bit will set the running user-id to the id of the file owner
        {
            runningUser = file.ownerName;
        }

        if (file.isIdGroupSet()) //the second bit sets the running group-id to the group-id of the file owner
        {
            runningGroup = UserGroups.userGroups.get(file.ownerName);
        }

        outputToStdout(action, runningUser, runningGroup, result);
    }

    private void outputToStdout(String action, String runningUser, String runningGroup, String result)
    {
        //action running_user running_group result
        System.out.printf("%s %s %s %s\n", action, runningUser, runningGroup, result);
    }
}
