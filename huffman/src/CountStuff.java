
import java.io.File;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author lchong
 */
public class CountStuff
{

    public static void main(String[] args) throws Exception
    {
        String InputFileName = "input";
        Scanner file = new Scanner(new File(InputFileName));

        Map<Character, Long> histogram = new TreeMap<>();

        while (file.hasNextLine())
        {
            String line = file.nextLine();
            for (char c : line.toCharArray())
            {
                c = (c + "").toUpperCase().charAt(0);
                if (validChar(c))
                {
                    if (histogram.containsKey(c))
                    {
                        long val = histogram.get(c);
                        histogram.put(c, val + 1);
                    }
                    else
                    {
                        long l = 1;
                        histogram.put(c, l);
                    }
                }
            }
        }

        for (char key : histogram.keySet())
        {
            System.out.println(histogram.get(key));
        }
        System.out.println("-----");
        for (char key : histogram.keySet())
        {
            System.out.println(key);
        }
    }

    public static boolean validChar(char c)
    {
        boolean valid = true;

        valid =
                (c + "").matches("[[a-z][A-Z]]");

        return valid;
    }
}
