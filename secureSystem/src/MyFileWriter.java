
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Log writer for use by the secure system
 * @author lchong
 */
public final class MyFileWriter
{

    private final String outputFileName;
    private StringBuffer sb;
    private final int SB_LIMIT = 512;

    public MyFileWriter(String outputFileName)
    {
        assert outputFileName != null;
        this.outputFileName = outputFileName;
        clearOldOutputFile();
    }

    public void clearOldOutputFile()
    {
        clearLocalBuffer();
        deleteOutputFile();
    }

    /**
     * Clears the local buffer
     */
    private void clearLocalBuffer()
    {
        if (sb == null)
        {
            sb = new StringBuffer();
        }
        else
        {
            sb.setLength(0);
        }
    }

    /**
     * Deletes the log file matching the log file name
     * @return 
     */
    private boolean deleteOutputFile()
    {
        assert outputFileName != null;
        File file = new File(outputFileName);
        return file.delete();
    }

    /**
     * Doesn't actually write the line into the output file directly, but stores the line in a buffer, which will be written later
     * @param line
     */
    public void writeString(String line)
    {
        sb.append(line);
        checkIfBufferIsFull();
    }

    /**
     * Doesn't actually write the line into the output file directly, but stores the line in a buffer, which will be written later
     * @param line
     */
    public void writeString(char line)
    {
        writeString(line + "");
    }

    public void writeLine(String line)
    {
        sb.append(line).append("\n");
        checkIfBufferIsFull();
    }

    private void checkIfBufferIsFull()
    {
        if (sb.length() >= SB_LIMIT)
        {
            writeOutputFile();
            clearLocalBuffer();            
        }
    }

    /**
     * Writes to the log file (from the internal buffer)
     */
    public void writeOutputFile()
    {
        String data = sb.toString(); // stuff which will be written
        File file = new File(outputFileName);

        //if file doesnt exists, then create it
        if (!file.exists())
        {
            try
            {
                file.createNewFile();
            }
            catch (IOException ex)
            {
                System.err.println("Something went wrong: " + ex.getMessage());
            }
        }

        boolean appendToFile = true;
        FileWriter fileWritter = null;
        try
        {
            fileWritter = new FileWriter(file.getName(), appendToFile);
        }
        catch (IOException ex)
        {
            System.err.println("Something went wrong: " + ex.getMessage());
        }

        assert fileWritter != null;

        try (BufferedWriter bufferWritter = new BufferedWriter(fileWritter))
        {
            bufferWritter.write(data);
            bufferWritter.close();
        }
        catch (IOException ex)
        {
            System.err.println("Something went wrong: " + ex.getMessage());
        }
    }
}
