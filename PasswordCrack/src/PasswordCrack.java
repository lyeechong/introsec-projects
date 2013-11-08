
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author lchong
 */
public class PasswordCrack
{

    private static final int MAX_DEPTH = 2;

    public PasswordCrack()
    {
    }

    public static void main(String[] args) throws IOException
    {
        PasswordCrack pc = new PasswordCrack();
        pc.run(args);
    }

    public void run(String[] args) throws IOException
    {
        assert args.length == 2;

        if (args.length != 2)
        {
            System.out.println("Error: expected 2 arguments, received: " + args.length);
        }

        String dictionaryFileName = args[0];
        String passwordFileName = args[1];

        Scanner dictionaryFile = new Scanner(new File(dictionaryFileName));
        Scanner passwordFile = new Scanner(new File(passwordFileName));


        while (dictionaryFile.hasNextLine())
        {
            String word = dictionaryFile.nextLine().trim();
            Dictionary.addWord(word);
        }

        int count = 0;
        int total = 0;
        while (passwordFile.hasNextLine())
        {
            String passwordLine = passwordFile.nextLine();
            CrackForOneUser cfou = new CrackForOneUser(passwordLine);
            cfou.crack();
            String plaintextPassword = cfou.getPlaintextPassword();

            int depth = 0;
            while (plaintextPassword.equals(CrackForOneUser.FAILURE_MESSAGE) && depth < MAX_DEPTH)
            {
                cfou.goDeeper();
                cfou.crack();
                plaintextPassword = cfou.getPlaintextPassword();
            }
            if (depth == MAX_DEPTH)
            {
                // we failed!
            }
            else
            {
                count++;
            }
            total++;
            System.out.println(plaintextPassword);
        }

        System.out.println("");
        System.out.println("Got " + count + " passwords out of " + total);

        String encryptedPassword = Jcrypt.crypt(passwordFileName, passwordFileName);

    }
}
