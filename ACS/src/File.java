/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lchong
 */
public class File
{

    // 123rwxrwxrwx
    // 1 is the owner run bit
    // 2 is the group run bit
    // 3 is the sticky for directories
    
    boolean[] permissions;
    String ownerName;
    String fileName;

    public File(String fileName, String ownerName, boolean[] permissions)
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
}
