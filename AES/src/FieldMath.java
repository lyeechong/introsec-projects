/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lchong
 */
public class FieldMath
{

    public static int gmul(int a, int b)
    {
        int p = 0;
        int counter;
        int hi_bit_set;
        for (counter = 0; counter < 8; counter++)
        {
            if ((b & 1) == 1)
            {
                p ^= a;
            }
            hi_bit_set = (a & 0x80);
            a <<= 1;
            if (hi_bit_set == 0x80)
            {
                a ^= 0x1b;
            }
            b >>= 1;
        }
        return p;
    }
    
    public static int add(int a, int b)
    {
        return a ^ b;
    }
}
