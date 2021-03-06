
/**
 * Performs actions requested by subjects. (Note that this class doesn't do the
 * checking of clearance. That task is in the ReferenceMonitor).
 *
 * @author lchong
 */
public class TokenHelper
{
    private static String SPLIT_REGEX = "\\s+";
    public static String obtainTokenAtIndex(String instruction, int index)
    {
        String[] instructionTokens = instruction.split(SPLIT_REGEX);
        return instructionTokens[index];
    }

    public static boolean isRead(String inst)
    {
        return obtainTokenAtIndex(inst, 0).equalsIgnoreCase("read");
    }

    public static boolean isWrite(String inst)
    {
        return obtainTokenAtIndex(inst, 0).equalsIgnoreCase("write");
    }

    public static String getSubjectName(String inst)
    {
        return obtainTokenAtIndex(inst, 1);
    }

    public static String getObjectName(String inst)
    {
        
        return obtainTokenAtIndex(inst, 2);
    }

    public static int getNumberOfTokensInInstruction(String inst)
    {
        return inst.split(SPLIT_REGEX).length;
    }
}
