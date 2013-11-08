
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

    public PasswordCrack()
    {
    }

    public static void main(String[] args) throws IOException, InterruptedException
    {
        PasswordCrack pc = new PasswordCrack();
        pc.run(args);
    }

    public void run(String[] args) throws IOException, InterruptedException
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

        while (passwordFile.hasNextLine())
        {
            Thread t = new Thread(new PasswordCrackThreadTask(passwordFile.nextLine()));
            t.start();
            Thread.sleep(1000);
            t.interrupt();
        }

        String encryptedPassword = Jcrypt.crypt(passwordFileName, passwordFileName);

    }
}
