
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

    /**
     * Constructor. Inits the subjectSecurityMap and the objectSecurityMap.
     */
    public ReferenceMonitor()
    {
        StaticStuff.setCc(new ClearanceChecker());
        StaticStuff.setOm(new ObjectManager());
    }

    /**
     * Performs the instruction, checking the clearnce and syntax of the instruction before asking the ObjectManager to perform it.
     * @param instruction
     * @return 
     */
    public Instruction performInstruction(String instruction)
    {
        instruction = instruction.trim(); // -- I don't think the user will mind.

        Instruction instructionObject = null;

        boolean validInstruction = InstructionChecker.isInstructionIsLegal(instruction);
        if (!validInstruction) //syntax checking only
        {
            // -- the instruction was illegal, so generate a BadInstruction
            return new BadInstruction("Bad Instruction", false);
        }

        // -- check the security clearance stuff here

        String subjectName = TokenHelper.getSubjectName(instruction);
        String objectName = TokenHelper.getObjectName(instruction);

        SystemSubject ss = SystemSubjectsContainer.get(subjectName);
        SystemObject so = SystemObjectsContainer.get(objectName);
        boolean hasClearance = StaticStuff.getCc().hasClearance(ss, so, instruction);

        if (InstructionChecker.validRead(instruction))
        {
            instructionObject = new ReadInstruction(instruction, hasClearance);
        }
        else if (InstructionChecker.validWrite(instruction))
        {
            instructionObject = new WriteInstruction(instruction, hasClearance);
        }
        else
        {
            assert false;
        }

        if (hasClearance)
        {
            // -- perform the instruction since it was valid (via the ObjectManager)
            StaticStuff.getOm().performInstruction(instructionObject);
        }

        return instructionObject;
    }

    public void createSubject(String name, SecurityLevel securityLevel)
    {
        SystemSubject ss = new SystemSubject(name);
        StaticStuff.getCc().setSubjectClearance(ss, securityLevel);
        SystemSubjectsContainer.put(name, ss);
    }

    public void createObject(String name, SecurityLevel securityLevel, int value)
    {
        SystemObject so = new SystemObject(name, value);
        StaticStuff.getCc().setObjectClearance(so, securityLevel);
        SystemObjectsContainer.put(name, so);
    }

    public void createSecurityLevel(SecurityLevel sl)
    {
        SystemSecurityLevelsContainer.add(sl);
    }

    public void deleteObject(SystemObject so)
    {
        SystemObjectsContainer.remove(so.getName());
        StaticStuff.getCc().deleteObject(so);
    }
}
