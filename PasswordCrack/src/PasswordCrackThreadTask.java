/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lchong
 */
public class PasswordCrackThreadTask implements Runnable
{

    private static final int MAX_DEPTH = 2;
    private String passwordLine;

    public PasswordCrackThreadTask(String passwordLine)
    {
        this.passwordLine = passwordLine;
    }

    @Override
    public void run()
    {
        doStuff();
    }

    private void doStuff()
    {
        CrackForOneUser cfou = new CrackForOneUser(passwordLine);
        cfou.crack();
        String plaintextPassword = cfou.getPlaintextPassword();

        int depth = 0;
        while (plaintextPassword.equals(CrackForOneUser.FAILURE_MESSAGE) && depth < MAX_DEPTH && !Thread.currentThread().isInterrupted())
        {
            cfou.goDeeper();
            cfou.crack();
            plaintextPassword = cfou.getPlaintextPassword();
        }
        if (depth == MAX_DEPTH || Thread.currentThread().isInterrupted())
        {
            // we failed!
            System.out.println("Failed!!");
        }
        else
        {
            // success
            System.out.println(plaintextPassword);
        }        
    }
}
