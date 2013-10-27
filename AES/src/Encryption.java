
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
public class Encryption
{

    String keyFileName;
    String inputFileName;

    public Encryption(String keyFileName, String inputFileName)
    {
        this.keyFileName = keyFileName;
        this.inputFileName = inputFileName;
    }

    public void encrypt() throws IOException
    {
        String[][] matrix = new String[4][4];
        String[][] initialCipherKey = new String[4][4];
        MyFileWriter myFileWriter = new MyFileWriter(inputFileName + ".enc");

        Scanner plainTextFile = new Scanner(new File(inputFileName));
        Scanner keyFile = new Scanner(new File(keyFileName));

        //grab the cipher key from the file
        while (keyFile.hasNextLine())
        {
            String keyLine = keyFile.nextLine();
            Utils.log("the keyLine is :: " + keyLine);
            assert keyLine.length() == 32;
            //stuff it into a matrix
            transformIntoMatrix(initialCipherKey, keyLine);
            Utils.log("the keyLine as a matrix is :: ");
            Utils.logMatrix(initialCipherKey);
        }
        //create a new round key generator for use in the rounds
        RoundKeyGenerator roundKeyGenerator = new RoundKeyGenerator(initialCipherKey);

        Utils.log("Starting to process the plaintext file");

        //now process the plaintext
        while (plainTextFile.hasNextLine())
        {
            String plainTextLine = plainTextFile.nextLine();
            Utils.log("The plainTextLine is " + plainTextLine);
            assert plainTextLine.length() == 32;
            transformIntoMatrix(matrix, plainTextLine);
            Utils.log("the plainTextLine as a matrix is :: ");
            Utils.logMatrix(matrix);


            Utils.log("Doing initial add round key thing");
            addRoundKey(matrix, roundKeyGenerator.getNextCipherKey());
            Utils.log("Matrix after adding initial round key:");
            Utils.logMatrix(matrix);

            /***
             * 
             * TODO             
             * http://www.cs.bc.edu/~straubin/cs381-05/blockciphers/rijndael_ingles2004.swf
             * do a final round after the 9 rounds
             * do file writing stuff
             */
            // do 9 rounds of the regular stuff
            Utils.log("Beginning the 9 rounds");
            for (int i = 0; i < 9; i++)
            {
                Utils.log("Doing subBytes (round #" + (i + 1) + ")");
                subBytes(matrix);
                Utils.log("Matrix after subBytes: (round #" + (i + 1) + ")");
                Utils.logMatrix(matrix);

                Utils.log("Doing shiftRows (round #" + (i + 1) + ")");
                shiftRows(matrix);
                Utils.log("Matrix after shiftRows: (round #" + (i + 1) + ")");
                Utils.logMatrix(matrix);

                Utils.log("Doing mixing cols (round #" + (i + 1) + ")");
                doMix(matrix);
                Utils.log("Matrix after mixing cols: (round #" + (i + 1) + ")");
                Utils.logMatrix(matrix);

                Utils.log("Doing adding round key (round #" + (i + 1) + ")");
                addRoundKey(matrix, roundKeyGenerator.getNextCipherKey());
                Utils.log("Matrix after adding round key: (round #" + (i + 1) + ")");
                Utils.logMatrix(matrix);
            }

            Utils.log("Beginning the final round");
            //do one final round here (no mixing columns)
            Utils.log("Doing subBytes (final round)");
            subBytes(matrix);
            Utils.log("Matrix after subBytes (final round):");
            Utils.logMatrix(matrix);

            Utils.log("Doing shiftRows (final round)");
            shiftRows(matrix);
            Utils.log("Matrix after shiftRows (final round):");
            Utils.logMatrix(matrix);

            Utils.log("Not doing mixing cols since this is the final round");

            Utils.log("Doing adding round key (final round)");
            addRoundKey(matrix, roundKeyGenerator.getNextCipherKey());
            Utils.log("Matrix after adding round key (final round):");
            Utils.logMatrix(matrix);

            String encryptedString = Utils.matrixToString(matrix);
            Utils.log("Done with encryption of this line!");
            Utils.log("Encrypted line: " + encryptedString);

            Utils.log("Writing to output file");
            myFileWriter.writeLine(encryptedString);
            myFileWriter.writeOutputFile();
        }
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

    private void subBytes(String[][] matrix)
    {
        for (int i = 0; i < matrix.length; i++)
        {
            for (int j = 0; j < matrix[0].length; j++)
            {
                String currentCell = matrix[i][j];
                String subbedCell = SBox.getSBoxForChar(currentCell);
                matrix[i][j] = subbedCell;
            }
        }
    }

    private String[][] shiftRows(String[][] matrix)
    {
        shiftSecondTopLeftOne(matrix);
        shiftThirdTopLeftTwo(matrix);
        shiftFourthTopLeftThree(matrix);
        return matrix;
    }

    private void shiftSecondTopLeftOne(String[][] matrix)
    {
        String[] secondToprow = new String[4];
        secondToprow[0] = matrix[1][1];
        secondToprow[1] = matrix[1][2];
        secondToprow[2] = matrix[1][3];
        secondToprow[3] = matrix[1][0];
        matrix[1] = secondToprow;
    }

    private void shiftThirdTopLeftTwo(String[][] matrix)
    {
        swapCells(matrix, 2, 2, 2, 0);
        swapCells(matrix, 2, 3, 2, 1);
    }

    private void shiftFourthTopLeftThree(String[][] matrix)
    {
        String[] secondToprow = new String[4];
        secondToprow[0] = matrix[3][3];
        secondToprow[1] = matrix[3][0];
        secondToprow[2] = matrix[3][1];
        secondToprow[3] = matrix[3][2];
        matrix[3] = secondToprow;
    }

    private void swapCells(String[][] matrix, int r, int c, int r2, int c2)
    {
        String cell1 = matrix[r][c];
        String temp = matrix[r2][c2];
        matrix[r][c] = temp;
        matrix[r2][c2] = cell1;
    }

    private void doMix(String[][] matrix)
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
        mat = doMixHelper(mat);

        for (int i = 0; i < matrix.length; i++)
        {
            for (int j = 0; j < matrix[0].length; j++)
            {
                matrix[i][j] = Utils.intToHexString(mat[i][j]);
            }
        }

    }

    private int[][] doMixHelper(int[][] mat)
    {
        int[][] result = new int[4][4];

        //b0 = 2a0 + 3a1 + 1a2 + 1a3
        //b1 = 1a0 + 2a1 + 3a2 + 1a3
        //b2 = 1a0 + 1a1 + 2a2 + 3a3
        //b3 = 3a0 + 1a1 + 1a2 + 2a3
        for (int r = 0; r < 4; r++)
        {
            int a0 = mat[0][r];
            int a1 = mat[1][r];
            int a2 = mat[2][r];
            int a3 = mat[3][r];

            int b0 = FieldMath.gmul(2, a0)
                    ^ FieldMath.gmul(3, a1)
                    ^ a2
                    ^ a3;

            int b1 = a0
                    ^ FieldMath.gmul(2, a1)
                    ^ FieldMath.gmul(3, a2)
                    ^ a3;

            int b2 = a0
                    ^ a1
                    ^ FieldMath.gmul(2, a2)
                    ^ FieldMath.gmul(3, a3);

            int b3 = FieldMath.gmul(3, a0)
                    ^ a1
                    ^ a2
                    ^ FieldMath.gmul(2, a3);

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
