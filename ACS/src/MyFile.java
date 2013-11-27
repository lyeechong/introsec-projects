
import java.util.Arrays;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lchong
 */
public class MyFile
{

    // 123rwxrwxrwx
    // 1 is the owner run bit
    // 2 is the group run bit
    // 3 is the sticky for directories
    boolean[] permissions;
    String ownerName;
    String fileName;

    public MyFile(String fileName, String ownerName, boolean[] permissions)
    {
        this.fileName = fileName;
        this.ownerName = ownerName;
        this.permissions = new boolean[permissions.length];
        System.arraycopy(permissions, 0, this.permissions, 0, permissions.length);
    }

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public String getOwnerName()
    {
        return ownerName;
    }

    public void setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
    }

    public boolean[] getPermissions()
    {
        return permissions;
    }

    public void setPermissions(boolean[] permissions)
    {
        this.permissions = permissions;
    }

    public boolean otherUsersCanRead()
    {
        System.out.println(Arrays.toString(permissions));
        return permissions[6 + 3];
    }

    public boolean otherUsersCanExecute()
    {
        return permissions[8 + 3];
    }

    public boolean otherUsersCanWrite()
    {
        return permissions[7 + 3];
    }

    public boolean groupMembersCanRead()
    {
        return permissions[6 - 3 + 3];
    }

    public boolean groupMembersCanExecute()
    {
        return permissions[8 - 3 + 3];
    }

    public boolean groupMembersCanWrite()
    {
        return permissions[7 - 3 + 3];
    }

    public boolean ownerCanRead()
    {
        return permissions[6 - 3 - 3 + 3];
    }

    public boolean ownerCanExecute()
    {
        return permissions[8 - 3 - 3 + 3];
    }

    public boolean ownerCanWrite()
    {
        return permissions[7 - 3 - 3 + 3];
    }

    public String getGroup()
    {
        return UserGroups.userGroups.get(this.ownerName);
    }

    public boolean isOwner(String user)
    {
        return user.equals(this.ownerName);
    }

    private String convertModeFormat(String mode)
    {
        char[] modeFormatted = new char[9];
        for (int i = 3; i < permissions.length; i++)
        {
            if (i % 3 == 0) //read bit
            {
                if (permissions[i])
                {
                    modeFormatted[i - 3] = 'r';
                }
                else
                {
                    modeFormatted[i - 3] = '-';
                }
            }
            else if ((i + 1) % 3 == 0) //write bit
            {
                if (permissions[i])
                {
                    modeFormatted[i - 3] = 'w';
                }
                else
                {
                    modeFormatted[i - 3] = '-';
                }
            }
            else if ((i + 2) % 3 == 0) //execute bit
            {
                if (permissions[i])
                {
                    modeFormatted[i - 3] = 'x';
                }
                else
                {
                    modeFormatted[i - 3] = '-';
                }
            }
            else
            {
                assert false;
            }
        }

        // rwx rwx rwx
        // 012 345 678


        //'S' -- If in the owner permissions, the file is not executable and set-user-ID mode is set.
        //Or if in the group permissions, the file is not executable and set-group-ID mode is set.
        if (!ownerCanExecute() && isIdUserSet())
        {
            modeFormatted[2] = 'S';
        }
        else if (!groupMembersCanExecute() && isIdGroupSet())
        {
            modeFormatted[5] = 'S';
        }


        //'s' -- If in the owner permissions, the file is executable and set-user-ID mode is set.
        //If in the group permissions, the file is executable and set-group-ID mode is set. 
        if (ownerCanExecute() && isIdUserSet())
        {
            modeFormatted[2] = 's';
        }
        else if (groupMembersCanExecute() && isIdGroupSet())
        {
            modeFormatted[5] = 's';
        }

        //'T' -- If the sticky bit is set, but not executable 
        if (isStickyBitSet() && !otherUsersCanExecute())
        {
            modeFormatted[8] = 'T';
        }

        //'t' -- If the sticky bit is set, and is executable. 
        if (isStickyBitSet() && otherUsersCanExecute())
        {
            modeFormatted[8] = 't';
        }


        String output = "";
        for (char c : modeFormatted)
        {
            output += c;
        }

        return output;
    }

    public boolean isIdUserSet()
    {
        return permissions[0];
    }

    public boolean isIdGroupSet()
    {
        return permissions[1];
    }

    public boolean isStickyBitSet()
    {
        return permissions[2];
    }

    private String getModeAsString()
    {
        String output = "";
        for (boolean b : permissions)
        {
            if (b)
            {
                output += "1";
            }
            else
            {
                output += "0";
            }
        }
        return output;
    }

    public String toString()
    {
        String output = "";
        output += convertModeFormat(getModeAsString()) + " ";
        output += ownerName + " ";
        output += getGroup() + " ";
        output += fileName;
        return output;
    }
}
