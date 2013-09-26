
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author lchong
 */
public class BitInput
{

    private ByteArrayInputStream read;
    private int numBitsLeft;
    private int valToSend;
    private final String filename;

    public BitInput(String filename) throws IOException
    {
        numBitsLeft = 8;
        valToSend = 0;
        this.filename = filename;
        
        assert new File(filename).exists();

        if (read == null)
        {
            setup();
        }
    }

    public int getNextBit()
    {
        int res = 0;

        res = (valToSend & 128);
        valToSend <<= 1;
        numBitsLeft--;
        res >>= 7;

        if (numBitsLeft <= 0)
        {
            valToSend = getNextByte();
            numBitsLeft = 8;
        }
        return res;
    }

    private void setup() throws IOException
    {
        File fl = new File(filename);
        read = retrieveByteArrayInputStream(fl);
        valToSend = getNextByte();
    }

    private int getNextByte()
    {
        if (read.available() > 0)
        {
            return read.read();
        }
        else
        {
            return 0;
        }
    }

    private ByteArrayInputStream retrieveByteArrayInputStream(File file) throws IOException
    {
        return new ByteArrayInputStream(Files.readAllBytes(file.toPath()));
    }
}
