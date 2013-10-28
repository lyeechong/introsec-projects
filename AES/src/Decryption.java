
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
public class Decryption
{

    String keyFileName;
    String inputFileName;
    String[][][] roundKeys = new String[11][4][4];

    public Decryption(String keyFileName, String inputFileName)
    {
        this.keyFileName = keyFileName;
        this.inputFileName = inputFileName;
    }

    public void decrypt() throws IOException
    {

        MyFileWriter myFileWriter = new MyFileWriter(inputFileName + ".dec");

        Scanner plainTextFile = new Scanner(new File(inputFileName));
        Scanner keyFile = new Scanner(new File(keyFileName));

        String[][] initialCipherKey = new String[4][4];
        RoundKeyGenerator roundKeyGenerator = null;
        while (keyFile.hasNextLine())
        {
            String keyLine = keyFile.nextLine();

            keyLine = String.format("%-32s", keyLine).replaceAll(" ", "0");
            Utils.log("the keyLine is :: " + keyLine);
            assert keyLine.length() == 32;
            //stuff it into a matrix
            transformIntoMatrix(initialCipherKey, keyLine);
            Utils.log("the keyLine as a matrix is :: ");
            Utils.logMatrix(initialCipherKey);

            //create a new round key generator for use in the rounds
            roundKeyGenerator = new RoundKeyGenerator(initialCipherKey);

        }

        for (int i = 10; i >= 0; i--)
        {
            roundKeys[i] = roundKeyGenerator.getNextCipherKey();
        }

        Utils.log("Starting to process the plaintext file for decryption");

        //now process the plaintext
        while (plainTextFile.hasNextLine())
        {
            String[][] matrix = new String[4][4];
            String plainTextLine = plainTextFile.nextLine();

            plainTextLine = String.format("%-32s", plainTextLine).replaceAll(" ", "0");

            if (!hexCheck(plainTextLine))
            {
                continue;
            }

            Utils.log("The plainTextLine is " + plainTextLine);
            assert plainTextLine.length() == 32;
            transformIntoMatrix(matrix, plainTextLine);
            Utils.log("the plainTextLine as a matrix is :: ");
            Utils.logMatrix(matrix);

            Utils.log("doing initial round key for decrypt");
            addRoundKey(matrix, roundKeys[0]);
            Utils.log("round key being used:");
            Utils.logMatrix(roundKeys[0]);
            Utils.log("after initial round key");
            Utils.logMatrix(matrix);

            Utils.log("beginning the 9 rounds for decryption");
            for (int i = 0; i < 9; i++)
            {
                Utils.log("doing inverse shift rows (round #" + (i + 1) + ")");
                invShiftRows(matrix);
                Utils.log("after inverse shift rows");
                Utils.logMatrix(matrix);

                Utils.log("doing inverse sub bytes (round #" + (i + 1) + ")");
                invSubBytes(matrix);
                Utils.log("after inverse sub bytes");
                Utils.logMatrix(matrix);

                Utils.log("doing round key for decrypt (round #" + (i + 1) + ")");
                Utils.log("round key being used:");
                Utils.logMatrix(roundKeys[i + 1]);
                addRoundKey(matrix, roundKeys[i + 1]);
                Utils.log("after adding round key");
                Utils.logMatrix(matrix);

                Utils.log("doing inv mix for decrypt (round #" + (i + 1) + ")");
                doInvMix(matrix);
                Utils.log("after doing inv mix");
                Utils.logMatrix(matrix);
            }

            //begin the final round!
            Utils.log("Beginning the final round");
            Utils.log("doing inverse shift rows (final round)");
            invShiftRows(matrix);
            Utils.log("after inverse shift rows");
            Utils.logMatrix(matrix);

            Utils.log("doing inverse sub bytes (final round)");
            invSubBytes(matrix);
            Utils.log("after inverse sub bytes");
            Utils.logMatrix(matrix);

            Utils.log("doing round key for decrypt (final round)");
            addRoundKey(matrix, roundKeys[10]);
            Utils.log("after adding round key");
            Utils.logMatrix(matrix);

            Utils.log("the decrypted string is::");
            String decryptedString = Utils.matrixToString(matrix).toUpperCase();
            Utils.log(decryptedString);

            myFileWriter.writeLine(decryptedString);

        }
        myFileWriter.writeOutputFile();
    }

    private boolean hexCheck(String str)
    {
        str = str.replaceAll("[0-9a-fA-F]+", "");
        return str.length() == 0;
    }

    private void transformIntoMatrix(String[][] matrix, String plainTextLine)
    {
        String a = plainTextLine.substring(0, 8);
        String b = plainTextLine.substring(8, 16);
        String c = plainTextLine.substring(16, 24);
        String d = plainTextLine.substring(24, 32);
        matrix[0] = splitInto4s(a);
        matrix[1] = splitInto4s(b);
        matrix[2] = splitInto4s(c);
        matrix[3] = splitInto4s(d);
    }

    private void addRoundKey(String[][] matrix, String[][] roundKey)
    {
        Utils.log("The round key matrix :: ");
        Utils.logMatrix(roundKey);

        Utils.log("The matrix :: ");
        Utils.logMatrix(matrix);

        //xor's each cell in the matrix with each cell in the round key
        for (int i = 0; i < matrix.length; i++)
        {
            for (int j = 0; j < matrix[0].length; j++)
            {
                int matVal = Utils.hexStringToInt(matrix[i][j]);
                int keyVal = Utils.hexStringToInt(roundKey[i][j]);
                int resVal = matVal ^ keyVal;
                matrix[i][j] = Utils.intToHexString(resVal);
            }
        }
    }

    private String[] splitInto4s(String s)
    {
        String[] res = new String[4];
        res[0] = s.substring(0, 2);
        res[1] = s.substring(2, 4);
        res[2] = s.substring(4, 6);
        res[3] = s.substring(6, 8);
        return res;
    }

    private void invSubBytes(String[][] matrix)
    {
        for (int i = 0; i < matrix.length; i++)
        {
            for (int j = 0; j < matrix[0].length; j++)
            {
                String currentCell = matrix[i][j];
                String subbedCell = InvSBox.getInvSBoxForChar(currentCell);
                matrix[i][j] = subbedCell;
            }
        }
    }

    private String[][] invShiftRows(String[][] matrix)
    {
        invShiftSecondTopRightOne(matrix);
        invShiftThirdTopRightTwo(matrix);
        invShiftFourthTopRightThree(matrix);
        return matrix;
    }

    private void invShiftSecondTopRightOne(String[][] matrix)
    {
        String[] secondToprow = new String[4];
        secondToprow[0] = matrix[1][3];
        secondToprow[1] = matrix[1][0];
        secondToprow[2] = matrix[1][1];
        secondToprow[3] = matrix[1][2];
        matrix[1] = secondToprow;
    }

    private void invShiftThirdTopRightTwo(String[][] matrix)
    {
        swapCells(matrix, 2, 2, 2, 0);
        swapCells(matrix, 2, 3, 2, 1);
    }

    private void invShiftFourthTopRightThree(String[][] matrix)
    {
        String[] secondToprow = new String[4];
        secondToprow[0] = matrix[3][1];
        secondToprow[1] = matrix[3][2];
        secondToprow[2] = matrix[3][3];
        secondToprow[3] = matrix[3][0];
        matrix[3] = secondToprow;
    }

    private void swapCells(String[][] matrix, int r, int c, int r2, int c2)
    {
        String cell1 = matrix[r][c];
        String temp = matrix[r2][c2];
        matrix[r][c] = temp;
        matrix[r2][c2] = cell1;
    }

    private void doInvMix(String[][] matrix)
    {
        int[][] mat = new int[4][4];
        for (int i = 0; i < matrix.length; i++)
        {
            for (int j = 0; j < matrix[0].length; j++)
            {
                int cell = Utils.hexStringToInt(matrix[i][j]);
                mat[i][j] = cell;
            }
        }
        mat = doInvMixHelper(mat);

        for (int i = 0; i < matrix.length; i++)
        {
            for (int j = 0; j < matrix[0].length; j++)
            {
                matrix[i][j] = Utils.intToHexString(mat[i][j]);
            }
        }

    }

    private int[][] doInvMixHelper(int[][] mat)
    {
        int[][] result = new int[4][4];

        //b0 = 14a0 + 11a1 + 13a2 + 9a3
        //b1 = 9a0 + 14a1 + 11a2 + 13a3
        //b2 = 13a0 + 9a1 + 14a2 + 11a3
        //b3 = 11a0 + 13a1 + 9a2 + 14a3
        for (int r = 0; r < 4; r++)
        {
            int a0 = mat[0][r];
            int a1 = mat[1][r];
            int a2 = mat[2][r];
            int a3 = mat[3][r];

            int b0 = FieldMath.gmul(14, a0)
                    ^ FieldMath.gmul(11, a1)
                    ^ FieldMath.gmul(13, a2)
                    ^ FieldMath.gmul(9, a3);

            int b1 = FieldMath.gmul(9, a0)
                    ^ FieldMath.gmul(14, a1)
                    ^ FieldMath.gmul(11, a2)
                    ^ FieldMath.gmul(13, a3);

            int b2 = FieldMath.gmul(13, a0)
                    ^ FieldMath.gmul(9, a1)
                    ^ FieldMath.gmul(14, a2)
                    ^ FieldMath.gmul(11, a3);

            int b3 = FieldMath.gmul(11, a0)
                    ^ FieldMath.gmul(13, a1)
                    ^ FieldMath.gmul(9, a2)
                    ^ FieldMath.gmul(14, a3);

            b0 = Utils.maskToByte(b0);
            b1 = Utils.maskToByte(b1);
            b2 = Utils.maskToByte(b2);
            b3 = Utils.maskToByte(b3);

            result[0][r] = b0;
            result[1][r] = b1;
            result[2][r] = b2;
            result[3][r] = b3;
        }

        mat = result;
        return mat;
    }
}
