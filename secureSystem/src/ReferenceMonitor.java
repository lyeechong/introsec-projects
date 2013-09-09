
import java.util.HashMap;
import java.util.Map;

/**
 * Whenever a subject wishes to perform an action, it is directed here and
 * verified that they have the right clearance, and if so, the ObjectManager
 * then performs the action requested. Stores two mappings. The first is of
 * SystemSubjects to their respective SecurityLevel. The second maps
 * SystemObjects to their SecurityLevels.
 *
 * @author lchong
 */
public class ReferenceMonitor
{

    Map<SystemSubject, SecurityLevel> subjectSecurityMap;
    Map<SystemObject, SecurityLevel> objectSecurityMap;
    InstructionChecker instructionChecker;

    /**
     * Constructor. Inits the subjectSecurityMap and the objectSecurityMap.
     */
    public ReferenceMonitor()
    {
        subjectSecurityMap = new HashMap<>();
        objectSecurityMap = new HashMap<>();
        instructionChecker = new InstructionChecker();
    }
    
    public InstructionResult performInstruction(String instruction)
    {
        boolean validInstruction = instructionChecker.isInstructionIsLegal(instruction);
        if(!validInstruction)
        {
            // -- the instruction was illegal, so generate a BadInstruction
            return new InstructionResult("Bad Instruction", false);
        }
        
        // -- perform the instruction since it was valid (via the ObjectManager)
        
        //TODO fill out the rest of this method
        
        
        return null; //TODO change this
    }
}
