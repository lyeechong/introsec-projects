
/**
 * This is a result object from an instruction. It'll hold either the output of
 * the instruction, or the error as to why the instruction failed (eg:
 * BadInstruction)
 *
 * @author lchong
 */
public class InstructionResult
{

    /**
     * this is either the error message or the output from the instruction,
     * depending on the boolean error
     */
    private String message;
    /**
     * a boolean to indicate if this is an error or not
     */
    private boolean error;

    /**
     * Constructs a new InstructionResult object
     *
     * @param message this is either the error message or the output from the
     * instruction, depending on the boolean error
     * @param error a boolean to indicate if this is an error or not
     */
    public InstructionResult(String message, boolean error)
    {
        this.message = message;
        this.error = error;
    }

    /**
     * Overloaded constructor. Assumes that it's not an error message.
     *
     * @param output
     */
    public InstructionResult(String message)
    {
        this(message, false);
    }

    public String getMessage()
    {
        return message;
    }

    public boolean isError()
    {
        return error;
    }
}
