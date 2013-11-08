
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
public class Permutations
{

    private final String word;

    public Permutations(String word)
    {
        this.word = word;
    }

    public Set<String> getPermutations()
    {
        Set<String> res = new HashSet<>();
        res.addAll(prependAndAppendLetter());
        res.addAll(deleteFirstAndLastLetter());
        res.addAll(reverseWord());
        res.addAll(duplicateWord());
        res.addAll(reflectWord());
        res.addAll(lowerUpperCaseString());
        res.addAll(toggleCase());
        res.add(word);

        return res;
    }

    private Set<String> prependAndAppendLetter()
    {
        Set<String> res = new HashSet<>();

        String[] chars = PermuteUtils.getCharacters();

        for (String s : chars)
        {
            res.add(s + word);
            res.add(word + s);
        }

        return res;
    }

    private Set<String> deleteFirstAndLastLetter()
    {
        Set<String> res = new HashSet<>();

        if (word.length() >= 2)
        {
            res.add(word.substring(1));
        }
        if (word.length() >= 2)
        {
            res.add(word.substring(0, word.length() - 1));
        }

        return res;
    }

    private Set<String> reverseWord()
    {
        Set<String> res = new HashSet<>();
        res.add(new StringBuffer(word).reverse().toString());
        return res;
    }

    private Set<String> duplicateWord()
    {
        Set<String> res = new HashSet<>();
        res.add(word + word);
        return res;
    }

    private Set<String> reflectWord()
    {
        Set<String> res = new HashSet<>();

        String reversedWord = new StringBuffer(word).reverse().toString();

        res.add(word + reversedWord);
        res.add(reversedWord + word);

        return res;
    }

    private Set<String> lowerUpperCaseString()
    {
        Set<String> res = new HashSet<>();

        res.add(word.toUpperCase());
        res.add(word.toLowerCase());

        if (word.length() >= 2)
        {
            res.add((word.charAt(0) + "").toUpperCase() + word.substring(1).toLowerCase());
            res.add((word.charAt(0) + "").toLowerCase() + word.substring(1).toUpperCase());
        }
        return res;
    }

    private Set<String> toggleCase()
    {
        Set<String> res = new HashSet<>();

        StringBuilder resString = new StringBuilder(word.length());

        int toggle = 1;
        for (char c : word.toCharArray())
        {
            if (toggle == 1)
            {
                resString.append((c + "").toUpperCase());
            }
            else // -1
            {
                resString.append((c + "").toLowerCase());
            }
            toggle *= -1;
        }

        res.add(resString.toString());
        resString = new StringBuilder(word.length());

        toggle = -1;
        for (char c : word.toCharArray())
        {
            if (toggle == 1)
            {
                resString.append((c + "").toUpperCase());
            }
            else // -1
            {
                resString.append((c + "").toLowerCase());
            }
            toggle *= -1;
        }

        res.add(resString.toString());

        return res;
    }
}
