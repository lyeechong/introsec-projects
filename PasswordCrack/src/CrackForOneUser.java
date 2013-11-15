/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lchong
 */
public class CrackForOneUser
{

    private String plaintextPassword;
    private PwdFields pwdFields;
    private UserSpecificDictionary usd;
    private boolean ranCrack;
    public static String FAILURE_MESSAGE = "I didn't manage to get the plaintext password!";

    public CrackForOneUser(String userPwdLine)
    {
        pwdFields = new PwdFields(userPwdLine);
        usd = new UserSpecificDictionary(pwdFields);
        ranCrack = false;
    }

    public void setPlaintextPassword(String plaintextPassword)
    {
        this.plaintextPassword = plaintextPassword;
    }

    public void crack()
    {
        ranCrack = true;

        Thread t = new Thread(new PasswordCrackThreadTask(this));
        t.start();
        try
        {
            Thread.sleep(20000);
        }
        catch (InterruptedException ex)
        {
            System.out.println("ex" + ex.getMessage());
        }
        t.interrupt();

    }

    public PwdFields getPwdFields()
    {
        return pwdFields;
    }

    public boolean isRanCrack()
    {
        return ranCrack;
    }

    public UserSpecificDictionary getUsd()
    {
        return usd;
    }

    public String getPlaintextPassword()
    {
        if (!ranCrack)
        {
            System.out.println("Dude, run crack()!");
        }
        return this.plaintextPassword;
    }

    public void goDeeper()
    {
        usd.weNeedToGoDeeper();
    }
}
