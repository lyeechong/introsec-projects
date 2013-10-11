
import java.util.Map;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author lchong
 */
public class EntropyCalculator
{

    public EntropyCalculator()
    {
    }

    public double calculateEntropy(Map<Integer, Integer> freqMap, int total)
    {
        // h -(SUM_i p_i log_2 p_i)

        double h = 0;
        for (int c : freqMap.keySet())
        {
            int freq = freqMap.get(c);
            double prob = 1.0 * freq / total;
            h -= prob * (Math.log(prob) / Math.log(2));
        }
        return h;
    }

    public double calculateEncodingEfficiency(Map<Integer, Integer> freqMap, int total, Map<Integer, String> symbolCodes)
    {
        double h = 0;

        for (int c : freqMap.keySet())
        {
            int freq = freqMap.get(c);
            int numBits = symbolCodes.get(c).length();
            h += 1.0 * freq * numBits / total;
        }

        return h;
    }
}
