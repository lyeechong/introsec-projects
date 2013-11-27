
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
public class ParseFileList
{

    private String fileName;
    private FileSys fs;

    public ParseFileList(String fileName, FileSys fs)
    {
        this.fileName = fileName;
        this.fs = fs;
        try
        {
            parse();
        }
        catch (IOException ex)
        {
            System.out.println("Error parsing file list");
        }
    }

    private void parse() throws IOException
    {
        Scanner file = new Scanner(new File(fileName));
        while (file.hasNextLine())
        {
            String line = file.nextLine();
            parseLine(line);
        }
    }

    private void parseLine(String line)
    {
        //file3 user2 0655
        String[] input = line.split(" ");
        String fileName = input[0];
        String ownerName = input[1];
        String permissions = input[2];
        
        boolean[] b = new boolean[12];
        MyFile file = new MyFile(fileName, ownerName, b);
        fs.putIntoFileMap(fileName, file);
        
        fs.doSilentChmod(ownerName, fileName, permissions);
    }
}
