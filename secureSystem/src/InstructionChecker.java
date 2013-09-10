
/**
 * Class to check if instructions are syntactically valid.
 * @author lchong
 */
public class InstructionChecker {

    public InstructionChecker() {
    }

    public boolean isInstructionIsLegal(String instruction) {
        String[] instructionTokens = instruction.split("\\s+");
        boolean valid = false;

        valid = validRead(instructionTokens) || validWrite(instructionTokens);
                
        return valid;
    }

    private boolean validWrite(String[] tokens) {
        boolean valid = false;
        
        valid = tokens[0].equalsIgnoreCase("write") &&
        tokens.length == 4;
        
        return valid;
    }

    private boolean validRead(String[] tokens) {
        boolean valid = false;
        
        valid = tokens[0].equalsIgnoreCase("read") &&
        tokens.length == 3;
        
        return valid;
    }
}
