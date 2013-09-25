
/**
 * Class to check if instructions are syntactically valid.
 * @author lchong
 */
public class InstructionChecker
{

    private InstructionChecker()
    {
    }

    public static boolean isInstructionIsLegal(String instruction)
    {
        boolean valid = false;

        valid = instructionNotEmptyOrNull(instruction)
                && (validRead(instruction) || validWrite(instruction));

        return valid;
    }

    public static boolean instructionNotEmptyOrNull(String instruction)
    {
        boolean valid = false;

        valid = instruction != null && !instruction.isEmpty();

        return valid;
    }

    public static boolean subjectExists(String subject)
    {
        boolean valid = false;

        valid = SystemSubjectsContainer.get(subject) != null;

        return valid;
    }

    public static boolean objectExists(String object)
    {
        boolean valid = false;

        valid = SystemObjectsContainer.get(object) != null;

        return valid;
    }

    /**
     * Valid values are integers, which can be either positive or negative
     * @param value
     * @return 
     */
    public static boolean validValue(String value)
    {
        boolean valid = false;

        // -- the regex checks for:
        // zero or one negative signs at the start of the digits
        // one or more digits following the optional negative sign
        valid = value.matches("-?\\d+");

        return valid;
    }

    /**
     * Checks to see if it is a valid write instruction
     * @param instruction
     * @return 
     */
    public static boolean validWrite(String instruction)
    {
        boolean valid = false;

        valid = TokenHelper.getNumberOfTokensInInstruction(instruction) == 4
                && TokenHelper.obtainTokenAtIndex(instruction, 0).equalsIgnoreCase(Instructions.WRITE.name())
                && validValue(TokenHelper.obtainTokenAtIndex(instruction, 3))
                && subjectExists(TokenHelper.getSubjectName(instruction))
                && objectExists(TokenHelper.getObjectName(instruction));

        return valid;
    }

    /**
     * Checks to see if it is a valid read instruction
     * @param instruction
     * @return 
     */
    public static boolean validRead(String instruction)
    {
        boolean valid = false;
        valid = TokenHelper.getNumberOfTokensInInstruction(instruction) == 3
                && TokenHelper.obtainTokenAtIndex(instruction, 0).equalsIgnoreCase(Instructions.READ.name())
                && subjectExists(TokenHelper.getSubjectName(instruction))
                && objectExists(TokenHelper.getObjectName(instruction));

        return valid;
    }

    public static boolean validCreate(String instruction)
    {
        boolean valid = false;
        valid = TokenHelper.getNumberOfTokensInInstruction(instruction) == 3
                && TokenHelper.obtainTokenAtIndex(instruction, 0).equalsIgnoreCase(Instructions.CREATE.name())
                && subjectExists(TokenHelper.getSubjectName(instruction));
        return valid;
    }

    public static boolean validDestroy(String instruction)
    {
        boolean valid = false;
        valid = TokenHelper.getNumberOfTokensInInstruction(instruction) == 3
                && TokenHelper.obtainTokenAtIndex(instruction, 0).equalsIgnoreCase(Instructions.DESTROY.name())
                && subjectExists(instruction)
                && objectExists(instruction);
        return valid;
    }

    public static boolean validRun(String instruction)
    {
        boolean valid = false;
        valid = TokenHelper.getNumberOfTokensInInstruction(instruction) == 2
                && TokenHelper.obtainTokenAtIndex(instruction, 0).equalsIgnoreCase(Instructions.RUN.name())
                && subjectExists(instruction);
        return valid;
    }
}
