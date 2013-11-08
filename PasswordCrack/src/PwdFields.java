/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lchong
 */
public class PwdFields
{

    public String getAccount()
    {
        return account;
    }

    public String getCgos()
    {
        return cgos;
    }

    public String getEncryptedPwd()
    {
        return encryptedPwd;
    }

    public String getGid()
    {
        return gid;
    }

    public String getHomedir()
    {
        return homedir;
    }

    public String getOriginalPwdLine()
    {
        return originalPwdLine;
    }

    public String getSalt()
    {
        return salt;
    }

    public String getShell()
    {
        return shell;
    }

    public String getUid()
    {
        return uid;
    }
    private String account;
    private String encryptedPwd;
    private String salt;
    private String uid;
    private String gid;
    private String cgos;
    private String homedir;
    private String shell;
    private String originalPwdLine;

    public PwdFields(String pwdLine)
    {
        this.originalPwdLine = pwdLine;
        parse();
    }

    private void parse()
    {
        String[] stuff = originalPwdLine.split(":");

        account = stuff[0];
        encryptedPwd = stuff[1];
        salt = stuff[1].substring(0, 2);
        uid = stuff[2];
        gid = stuff[3];
        cgos = stuff[4];
        homedir = stuff[5];
        if (stuff.length >= 7)
        {
            shell = stuff[6];
        }
        else
        {
            shell = "";
        }
    }
}
