/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lchong
 */
public class RoundKeyGenerator
{

    String[][] initialCipherKey;
    String[][] currentCipherKey;

    public RoundKeyGenerator(String[][] initialCipherKey)
    {
        this.initialCipherKey = Utils.copyMatrix(initialCipherKey);
        this.currentCipherKey = Utils.copyMatrix(initialCipherKey);
    }

    public String[][] getNextCipherKey()
    {
        generateNextCipherKeyAndUpdate();
        return Utils.copyMatrix(currentCipherKey);
    }

    /**
     * Generates the next cipher key and sets the current cipher key equal to it
     */
    private void generateNextCipherKeyAndUpdate()
    {
        String[][] newCipherKey = new String[4][4];

        for (int i = 0; i < 4; i++)
        {
            //get the vals
            String[] vals =
            {
                currentCipherKey[0][i + 3],
                currentCipherKey[1][i + 3],
                currentCipherKey[2][i + 3],
                currentCipherKey[3][i + 3]
            };

            //do the substitution
            String[] subbedVals = new String[4];
            for (int k = 0; k < 4; k++)
            {
                subbedVals[k] = SBox.getSBoxForChar(vals[k]);
            }

            //do the "rotation" on the subbed vals
            String temp = subbedVals[0];
            subbedVals[0] = subbedVals[1];
            subbedVals[1] = subbedVals[2];
            subbedVals[2] = subbedVals[3];
            subbedVals[3] = temp;

            //perform the xor
            String[] xoredVals = new String[4];
            for (int k = 0; k < 4; k++)
            {
                int subVal = Utils.hexStringToInt(subbedVals[k]);
                int cipherVal = Utils.hexStringToInt(currentCipherKey[0][i]);
                xoredVals[k] = Utils.intToHexString(subVal  ^ cipherVal);
            }

            //put the xored vals into the matrix
            for(int k = 0; k < 4; k++)
            {
                newCipherKey[i][k] = xoredVals[k];
            }
        }
        
        currentCipherKey = Utils.copyMatrix(newCipherKey);
    }
}
