
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
    ObjectManager objectManager;
    ClearanceChecker cc;

    /**
     * Constructor. Inits the subjectSecurityMap and the objectSecurityMap.
     */
    public ReferenceMonitor()
    {
        instructionChecker = new InstructionChecker();
        objectManager = new ObjectManager();
        cc = new ClearanceChecker();
    }

    /**
     * Performs the instruction, checking the clearnce and syntax of the instruction before asking the ObjectManager to perform it.
     * @param instruction
     * @return 
     */
    public InstructionObject performInstruction(String instruction)
    {
        instruction = instruction.trim(); // -- I don't think the user will mind.
        
        InstructionObject result = new InstructionObject("Something went terribly wrong.");

        boolean validInstruction = instructionChecker.isInstructionIsLegal(instruction);
        if (!validInstruction) //syntax checking only
        {
            // -- the instruction was illegal, so generate a BadInstruction
            return new BadInstruction("Bad Instruction");
        }

        // -- check the security clearance stuff here

        String subjectName = TokenHelper.getSubjectName(instruction);
        String objectName = TokenHelper.getObjectName(instruction);

        SystemSubject ss = SystemSubjectsContainer.get(subjectName);
        SystemObject so = SystemObjectsContainer.get(objectName);
        boolean hasClearance = cc.hasClearance(ss, so, instruction);

        if (TokenHelper.isRead(instruction))
        {
            result = new InstructionObject(subjectName + " reads " + objectName);
        }
        else if (TokenHelper.isWrite(instruction))
        {
            result = new InstructionObject(subjectName + " writes value " + TokenHelper.obtainTokenAtIndex(instruction, 3) + " to " + objectName);
        }
        else
        {
            assert false;
        }

        if (hasClearance)
        {
            // -- perform the instruction since it was valid (via the ObjectManager)
            objectManager.performInstruction(instruction);
        }
        else
        {
            // -- just set the temp value of the subject to zero
            if (TokenHelper.isRead(instruction))
            {
                objectManager.performUnauthorizedRead(instruction);
            }
        }

        return result;
    }

    public void createSubject(String name, SecurityLevel securityLevel)
    {
        SystemSubject ss = new SystemSubject(name);
        cc.setSubjectClearance(ss, securityLevel);
        SystemSubjectsContainer.put(name, ss);
    }

    public void createObject(String name, SecurityLevel securityLevel, int value)
    {
        SystemObject so = new SystemObject(name, value);
        cc.setObjectClearance(so, securityLevel);
        SystemObjectsContainer.put(name, so);
    }
    
    public void createSecurityLevel(SecurityLevel sl)
    {
        SystemSecurityLevelsContainer.add(sl);
    }
}
