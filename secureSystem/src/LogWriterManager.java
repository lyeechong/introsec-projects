/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lchong
 */
public class LogWriterManager
{

    private static MyFileWriter log;
    private static String outputFileName;

    public static void setOutputFileName(String name)
    {
        outputFileName = name;
    }

    public static MyFileWriter getLog()
    {
        if (log == null)
        {
            assert outputFileName != null;
            log = new MyFileWriter(outputFileName);
        }

        return log;
    }
}
