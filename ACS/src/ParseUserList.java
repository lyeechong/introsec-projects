
import java.io.IOException;
import java.util.Scanner;
import java.io.File;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lchong
 */
public class ParseUserList
{
    private String fileName;
    
    public ParseUserList(String fileName)
    {
        this.fileName = fileName;
        try
        {
            parse();
        }
        catch (IOException ex)
        {
            System.out.println("Error parsing user list");
        }
    }
    
    private void parse()throws IOException
    {
        Scanner file;
        boolean[] b = new boolean[1];
        File fl = new File(fileName);
        file = new Scanner(fl);
        while(file.hasNextLine())
        {
            String line = file.nextLine();
            parseLine(line);
        }
    }
    
    private void parseLine(String line)
    {
        String[] input = line.split(" ");
        String user = input[0];
        String group = input[1];
        
        UserGroups.userGroups.put(user, group);
    }
}
