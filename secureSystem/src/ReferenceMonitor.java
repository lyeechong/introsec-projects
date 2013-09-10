
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

    InstructionChecker instructionChecker;
    TokenHelper objectManager;
    ClearanceChecker cc;

    /**
     * Constructor. Inits the subjectSecurityMap and the objectSecurityMap.
     */
    public ReferenceMonitor()
    {
        instructionChecker = new InstructionChecker();
        objectManager = new TokenHelper();
        cc = new ClearanceChecker();
    }
    
    public InstructionObject performInstruction(String instruction)
    {
        boolean validInstruction = instructionChecker.isInstructionIsLegal(instruction);
        if(!validInstruction) //syntax checking only
        {
            // -- the instruction was illegal, so generate a BadInstruction
            return new BadInstruction("Bad Instruction");
        }
        
        //TODO: check the security clearance stuff here
        //read down
        //write up
        
        objectManager.obtainTokenAtIndex(instruction, index);
        
        SystemSubject so = SystemSubjectsContainer.get(subjName);
        SystemObject so = SystemObjectsContainer.get(objName);
        cc.hasClearance();
        
        // -- perform the instruction since it was valid (via the ObjectManager)
        InstructionObject result = objectManager.performInstruction(instruction);
        //TODO fill out the rest of this method
        
        
        return result;
    }
}
