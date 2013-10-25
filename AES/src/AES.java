/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lchong
 */
public class AES
{

    public AES()
    {
    }

    public static void main(String[] args)
    {
        AES aes = new AES();
        aes.run(args);
    }
    
    public void run(String[] args)
    {
        // option keyFile inputFile
        
        // option is e or d (encryption or decryption)
        // keyFile is a key file
        // inputFile is the plaintext
        if(args.length != 3)
        {
            System.out.println("Expected 3 args, recieved " + args.length + " args.");
        }
        
        String option = args[0];
        String keyFileName = args[1];
        String inputFileName = args[2];
        
        if(option.equalsIgnoreCase("e"))
        {
            //do encryption
            
        }
        else if (option.equalsIgnoreCase("d"))
        {
            //do decryption
        }
        else
        {
            //shouldn't get here
            assert false;
        }
    }
}
