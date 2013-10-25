/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lchong
 */
public class Utils
{

    /**
     * Copies a matrix
     * @param mat
     * @return 
     */
    public static String[][] copyMatrix(String[][] mat)
    {
        String[][] newMat = new String[mat.length][mat[0].length];
        assert mat != null;
        for (int i = 0; i < mat.length; i++)
        {
            for (int j = 0; j < mat.length; j++)
            {
                String val = mat[i][j];
                newMat[i][j] = val;
            }
        }
        return newMat;
    }

    /**
     * Converts an integer to a hex string
     */
    public static String intToHexString(int hex)
    {
        return String.format("%02x", hex);
    }
    
    /**
     * Masks an int into a byte
     * @param i
     * @return 
     */
    public static int maskToByte(int i)
    {
        return i & 0xff;
    }
    
    public static int hexStringToInt(String hexString)
    {
        return Integer.parseInt(hexString, 16);
    }
}
