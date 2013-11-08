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
    public PasswordCrackThreadTask()
    {
        
    }

    @Override
    public void run()
    {
        if(Thread.currentThread().isInterrupted())
        {
            //stop
        }
        else
        {
            //do stuff
            doStuff();
        }
    }
    
    private void doStuff()
    {
        
    }
}
