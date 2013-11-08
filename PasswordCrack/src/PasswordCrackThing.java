/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lchong
 */
public class PasswordCrackThing
{

    private static final int MAX_DEPTH = 3;
    private String passwordLine;

    public PasswordCrackThing(String passwordLine)
    {
        this.passwordLine = passwordLine;
    }

    public void run()
    {
        try
        {
            
            doStuff();
        }
        catch (InterruptedException ex)
        {
        }
    }

    private void doStuff() throws InterruptedException
    {
        CrackForOneUser cfou = new CrackForOneUser(passwordLine);
        cfou.crack();
        String plaintextPassword = cfou.getPlaintextPassword();

        int depth = 0;
        long startTime = System.currentTimeMillis();
        long MAX_TIME = 10000;
        while (plaintextPassword.equals(CrackForOneUser.FAILURE_MESSAGE) && depth < MAX_DEPTH)
        {
            long currentTime = System.currentTimeMillis();
            if(currentTime > startTime + MAX_TIME)
            {
                plaintextPassword = cfou.getPlaintextPassword();
                System.out.println(plaintextPassword);
                return;                
            }
            
            //System.out.println("Going deeper: " + depth);
            cfou.goDeeper();
            //System.out.println("Cracking");
            
            currentTime = System.currentTimeMillis();
            if(currentTime > startTime + MAX_TIME)
            {
                plaintextPassword = cfou.getPlaintextPassword();
                System.out.println(plaintextPassword);
                return;                
            }
            
            cfou.crack();
            plaintextPassword = cfou.getPlaintextPassword();
            depth++;
        }
        if (depth == MAX_DEPTH || plaintextPassword.equals(CrackForOneUser.FAILURE_MESSAGE))
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
