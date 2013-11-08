/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lchong
 */
public class PermuteUtils
{

    private static String[] characters;

    public static String[] getCharacters()
    {
        if (characters == null)
        {
            characters = "`1234567890-=qwertyuiop[]\\asdfghjkl;'zxcvbnm,./~!@#$%^&*()_+}{POIUYTREWQ|\":LKJHGFDSA?><MNBVCXZ ".split("");
        }
        return characters;
    }
}
