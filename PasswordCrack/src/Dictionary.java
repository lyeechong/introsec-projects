
import java.util.HashSet;
import java.util.Set;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author lchong
 */
public class Dictionary
{

    private static final Set<String> words;

    private Dictionary()
    {
    }

    static
    {
        words = new HashSet<>();
    }

    public static void addWord(String word)
    {
        Set<String> permutations = permuteWord(word);
        words.addAll(permutations);
    }

    private static Set<String> permuteWord(String word)
    {
        Set<String> permutations;

        Permutations p = new Permutations(word);
        permutations = p.getPermutations();

        return permutations;
    }

    public static Set<String> getWords()
    {
        return words;
    }
}
