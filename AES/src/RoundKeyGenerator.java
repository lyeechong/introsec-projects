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
        Utils.log("Generating the next cipher key");
        Utils.log("The current cipher key is:");
        Utils.logMatrix(currentCipherKey);

        String[][] newCipherKey = new String[4][4];

        for (int i = 0; i < 4; i++)
        {
            //get the vals
            String[] vals = new String[4];
            if (i == 0)
            {
                vals[0] = currentCipherKey[0][3];
                vals[1] = currentCipherKey[1][3];
                vals[2] = currentCipherKey[2][3];
                vals[3] = currentCipherKey[3][3];
            }
            else
            {
                vals[0] = newCipherKey[0][i - 1];
                vals[1] = newCipherKey[1][i - 1];
                vals[2] = newCipherKey[2][i - 1];
                vals[3] = newCipherKey[3][i - 1];
            }
            assert vals != null;
            assert vals[0] != null;
            assert vals[1] != null;
            assert vals[2] != null;
            assert vals[3] != null;

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
                xoredVals[k] = Utils.intToHexString(subVal ^ cipherVal);
            }

            //put the xored vals into the matrix
            for (int k = 0; k < 4; k++)
            {
                newCipherKey[k][i] = xoredVals[k];
            }
        }

        currentCipherKey = Utils.copyMatrix(newCipherKey);

        Utils.log("The new cipher key after round-key-generating is:");
        Utils.logMatrix(currentCipherKey);
    }
}
