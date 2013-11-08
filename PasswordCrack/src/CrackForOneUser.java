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

    public void crack()
    {
        ranCrack = true;
        String salt = pwdFields.getSalt();

        loop:
        for (String dictionaryWord : usd.getWords())
        {
            String encryptedPassword = Jcrypt.crypt(salt, dictionaryWord);
            
            if (encryptedPassword.equals(pwdFields.getEncryptedPwd()))
            {
                // found it!
                this.plaintextPassword = dictionaryWord;
                return;
            }
        }
        
        this.plaintextPassword = FAILURE_MESSAGE;
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
