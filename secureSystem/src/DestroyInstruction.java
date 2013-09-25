/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lchong
 */
public class DestroyInstruction extends Instruction
{

    public DestroyInstruction(String instructionString, boolean hasClearance)
    {
        super(instructionString, hasClearance);
    }

    @Override
    protected void execute()
    {
        String objName = TokenHelper.getObjectName(getInstructionString());
        String ssName = TokenHelper.getSubjectName(getInstructionString());
        SystemSubject ss = SystemSubjectsContainer.get(ssName);
        SystemObject so = SystemObjectsContainer.get(objName);
        boolean canDo = StaticStuff.getCc().hasClearance(ss, so, getInstructionString());
        if (!canDo)
        {
            //do nothing
        }
        else
        {
            //delete obj
            StaticStuff.getRf().deleteObject(so);
        }
    }

    @Override
    public String toString()
    {
        String subjectName = TokenHelper.getSubjectName(getInstructionString());
        String objectName = TokenHelper.getObjectName(getInstructionString());
        return subjectName + " destroyed " + objectName;
    }
}
