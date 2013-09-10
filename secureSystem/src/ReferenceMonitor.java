

/**
 * Whenever a subject wishes to perform an action, it is directed here and
 * verified that they have the right clearance, and if so, the ObjectManager
 * then performs the action requested. Stores two mappings. The first is of
 * SystemSubjects to their respective SecurityLevel. The second maps
 * SystemObjects to their SecurityLevels.
 *
 * @author lchong
 */
public class ReferenceMonitor {

    InstructionChecker instructionChecker;
    ObjectManager objectManager;
    ClearanceChecker cc;

    /**
     * Constructor. Inits the subjectSecurityMap and the objectSecurityMap.
     */
    public ReferenceMonitor() {
        instructionChecker = new InstructionChecker();
        objectManager = new TokenHelper();
        cc = new ClearanceChecker();
    }

    public InstructionObject performInstruction(String instruction) {
        
        InstructionObject result = new InstructionObject("Something went terribly wrong.");
        
        boolean validInstruction = instructionChecker.isInstructionIsLegal(instruction);
        if (!validInstruction) //syntax checking only
        {
            // -- the instruction was illegal, so generate a BadInstruction
            return new BadInstruction("Bad Instruction");
        }

        //TODO: check the security clearance stuff here
        //read down
        //write up


        SystemSubject ss = SystemSubjectsContainer.get(TokenHelper.getSubjectName(instruction));
        SystemObject so = SystemObjectsContainer.get(TokenHelper.getObjectName(instruction));
        boolean hasClearance = cc.hasClearance(ss, so, instruction);

        if (TokenHelper.isRead(instruction)) {
            result = new InstructionObject(ss.getName() + " reads " + so.getName());
        } else if (TokenHelper.isWrite(instruction)) {
            result = new InstructionObject(ss.getName() + " writes value " + TokenHelper.obtainTokenAtIndex(instruction, 3) + " to " + so.getName());
        } else {
            assert false;
        }

        if (hasClearance) {
            // -- perform the instruction since it was valid (via the ObjectManager)
            objectManager.performInstruction(instruction);
        }

        return result;
    }
}
