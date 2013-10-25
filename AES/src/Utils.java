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

    public static boolean LOGGING = true;

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

    public static void log(String logString)
    {
        if (LOGGING)
        {
            System.out.println("LOG :: " + logString);
        }
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

    public static String matrixToString(String[][] matrix)
    {
        String out = "";
        for (int i = 0; i < matrix.length; i++)
        {
            for (int j = 0; j < matrix[0].length; j++)
            {
                out += matrix[i][j];
            }
        }
        return out;
    }

    public static void logMatrix(String[][] matrix)
    {
        for (int i = 0; i < 4; i++)
        {
            System.out.print("\t");
            for (int j = 0; j < 4; j++)
            {
                if(LOGGING)
                {
                    System.out.printf("%2s ", matrix[i][j]);
                }
            }
            System.out.println("");
        }
    }
}
