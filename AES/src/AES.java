
import java.io.IOException;

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
        if (args.length != 3)
        {
            System.out.println("Expected 3 args, recieved " + args.length + " args.");
        }

        Utils.log("Starting program");

        String option = args[0];
        String keyFileName = args[1];
        String inputFileName = args[2];


        Utils.log("Option arg        :: " + option);
        Utils.log("KeyFileName arg   :: " + keyFileName);
        Utils.log("InputFileName arg :: " + inputFileName);

        if (option.equalsIgnoreCase("e"))
        {
            Utils.log("Performing encryption");
            //do encryption
            Encryption e = new Encryption(keyFileName, inputFileName);
            try
            {
                e.encrypt();
            }
            catch (IOException ex)
            {
                Utils.log("The program exploded while doing encryption: " + ex.getMessage());
            }
        }
        else if (option.equalsIgnoreCase("d"))
        {
            Utils.log("Performing decryption");
            assert false;
            //do decryption
            //TODO
        }
        else
        {
            //shouldn't get here
            Utils.log("The program exploded!");
            assert false;
        }
        
        Utils.log("Program ended");
    }
}
