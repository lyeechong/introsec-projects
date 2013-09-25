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
        String ssName = TokenHelper.getSubjectName(getInstructionString());
        SystemSubject ss = SystemSubjectsContainer.get(ssName);
        SecurityLevel securityLevel = StaticStuff.getCc().getSubjectClearance(ss);
        StaticStuff.getRf().createObject(objName, securityLevel, 0);
    }

    @Override
    public String toString()
    {
        String subjectName = TokenHelper.getSubjectName(getInstructionString());
        String objectName = TokenHelper.getObjectName(getInstructionString());
        return subjectName + " created " + objectName;
    }
}
