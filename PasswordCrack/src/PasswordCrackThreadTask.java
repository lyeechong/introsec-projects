
import java.util.concurrent.CountDownLatch;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author lchong
 */
public class PasswordCrackThreadTask
        implements Runnable
{

    CrackForOneUser cfou;

    public PasswordCrackThreadTask(CrackForOneUser cfou)
    {
        this.cfou = cfou;
    }

    public void doStuff()
    {
        String salt = cfou.getPwdFields().getSalt();
        loop:
        for (String dictionaryWord : cfou.getUsd().getWords())
        {

            if (Thread.interrupted())
            {
                cfou.setPlaintextPassword(CrackForOneUser.FAILURE_MESSAGE);
                return;
            }

            String encryptedPassword = Jcrypt.crypt(salt, dictionaryWord);

            if (encryptedPassword.equals(cfou.getPwdFields().getEncryptedPwd()))
            {
                // found it!
                cfou.setPlaintextPassword(dictionaryWord);
                return;
            }
        }

        cfou.setPlaintextPassword(CrackForOneUser.FAILURE_MESSAGE);

    }

    @Override
    public void run()
    {
        doStuff();
    }
}
