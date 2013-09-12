
/**
 * Class to check if instructions are syntactically valid.
 * @author lchong
 */
public class InstructionChecker
{

    private static String WRITE_INSTRUCTION = "write";
    private static String READ_INSTRUCTION = "read";

    public InstructionChecker()
    {
    }

    public boolean isInstructionIsLegal(String instruction)
    {
        boolean valid = false;        

        valid = instructionNotEmptyOrNull(instruction)
                && (validRead(instruction) || validWrite(instruction));

        return valid;
    }

    private boolean instructionNotEmptyOrNull(String instruction)
    {
        boolean valid = false;

        valid = instruction != null && !instruction.isEmpty();

        return valid;
    }

    private boolean subjectExists(String subject)
    {
        boolean valid = false;

        valid = SystemSubjectsContainer.get(subject) != null;

        return valid;
    }

    private boolean objectExists(String object)
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
    private boolean validValue(String value)
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
    private boolean validWrite(String instruction)
    {
        boolean valid = false;

        valid = TokenHelper.getNumberOfTokensInInstruction(instruction) == 4
                && TokenHelper.obtainTokenAtIndex(instruction, 0).equalsIgnoreCase(WRITE_INSTRUCTION)
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
    private boolean validRead(String instruction)
    {
        boolean valid = false;
        valid = TokenHelper.getNumberOfTokensInInstruction(instruction) == 3
                && TokenHelper.obtainTokenAtIndex(instruction, 0).equalsIgnoreCase(READ_INSTRUCTION)
                && subjectExists(TokenHelper.getSubjectName(instruction))
                && objectExists(TokenHelper.getObjectName(instruction));

        return valid;
    }
}
