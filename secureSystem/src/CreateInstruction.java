/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lchong
 */
public class CreateInstruction extends Instruction
{

    public CreateInstruction(String instructionString, boolean hasClearance)
    {
        super(instructionString, hasClearance);
    }

    protected void execute()
    {
        String objName = TokenHelper.getObjectName(getInstructionString());
        if (SystemObjectsContainer.get(objName) == null)
        {
            String ssName = TokenHelper.getSubjectName(getInstructionString());
            SystemSubject ss = SystemSubjectsContainer.get(ssName);
            SecurityLevel securityLevel = StaticStuff.getCc().getSubjectClearance(ss);
            assert securityLevel != null;
            StaticStuff.getRf().createObject(objName, new SecurityLevel(securityLevel.getName(), securityLevel.getLevel()), 0);
            assert objName.equals("obj");
            assert SystemObjectsContainer.get(objName) != null;
        }
    }

    @Override
    public String toString()
    {
        String subjectName = TokenHelper.getSubjectName(getInstructionString());
        String objectName = TokenHelper.getObjectName(getInstructionString());
        return subjectName + " created " + objectName;
    }
}
