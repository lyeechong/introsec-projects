
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
public class UserSpecificDictionary
{

    private PwdFields userPwdFields;
    private Set<String> words;

    public UserSpecificDictionary(PwdFields userPwdFields)
    {
        this.userPwdFields = userPwdFields;
        this.words = new HashSet<>(Dictionary.getWords());
        generateUserSpecificDictionary();
    }

    private void generateUserSpecificDictionary()
    {
        String[] name = userPwdFields.getCgos().split(" ");
        String firstName = name[0];
        String lastName = name[1];

        Permutations pFirstName = new Permutations(firstName);
        Permutations pLastName = new Permutations(lastName);


        words.addAll(pFirstName.getPermutations());
        words.addAll(pLastName.getPermutations());
    }

    public void weNeedToGoDeeper()
    {
        Set<String> temp = new HashSet<>(this.words);
        Set<String> newStuff = new HashSet<>();
        for (String word : temp)
        {
            Permutations p = new Permutations(word);
            newStuff.addAll(p.getPermutations());
        }
        this.words = newStuff;
    }

    public Set<String> getWords()
    {
        return this.words;
    }
}
