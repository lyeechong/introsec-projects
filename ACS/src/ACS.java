
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
public class ACS
{

    private FileSys fs;

    public static void main(String[] args)
    {
        //java ACS option userList fileList
        ACS acs = new ACS();

        acs.run(args);
    }

    public void run(String[] args)
    {
        fs = new FileSys();
        if (args.length == 3)
        {
            String option = args[0]; //not used
            String userList = args[1];
            String fileList = args[2];
            new ParseUserList(userList);
            new ParseFileList(fileList, fs);
        }
        else if (args.length == 2) //without the -r flag
        {
            String userList = args[0];
            String fileList = args[1];
            fs.createRootUserAndStickHimInRootGroup();
            new ParseUserList(userList);
            new ParseFileList(fileList, fs);
        }
        else
        {
            assert false;
        }
        try
        {
            doInputThing();
        }
        catch (IOException ex)
        {
            System.out.println("Stuff went wrong with user input: " + ex.getMessage());
        }
    }

    public void doInputThing() throws IOException
    {
        Scanner keyboard = new Scanner(System.in);
        while (keyboard.hasNextLine())
        {
            String line = keyboard.nextLine();

            if (line.trim().equalsIgnoreCase(Commands.EXIT.name()))
            {
                doAction(Commands.EXIT.name(), null, null, null);
                return;
            }

            //action user file
            String[] input = line.split(" ");
            String action = input[0];
            String user = input[1];
            String file = input[2];

            String extra = null;
            if (input.length == 4)
            {
                extra = input[3];
            }

            doAction(action, user, file, extra);
        }
    }

    public void doAction(String action, String user, String file, String extra)
    {
        /*
        READ -- Verify if the calling user has permissions to read the requested file
        WRITE -- Verify if the calling user has permissions to write to the requested file
        EXECUTE -- Verify if the calling user has permissions to execute the requested file
        CHMOD -- If and only if the calling user is the file owner or root, modify the files mode according to the mode specification.
         */

        switch (action.toUpperCase())
        {
            //READ, WRITE, EXECUTE, CHMOD, EXIT
            case "READ":
                fs.doRead(user, file);
                break;
            case "WRITE":
                fs.doWrite(user, file);
                break;
            case "EXECUTE":
                fs.doExecute(user, file);
                break;
            case "CHMOD":
                fs.doChmod(user, file, extra);
                break;
            case "EXIT":
                fs.doExit();
                break;
            default:
                System.out.println("Invalid command! (Commands are: READ, WRITE, EXECUTE, CHMOD, EXIT)");
                break;
        }
    }
}
