/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lchong
 */
public class WriteInstruction extends Instruction
{

    public WriteInstruction(String instructionString, boolean hasClearance)
    {
        super(instructionString, hasClearance);
    }

    @Override
    protected void execute()
    {
        String subjName = TokenHelper.getSubjectName(getInstructionString());
        String objName = TokenHelper.getObjectName(getInstructionString());
        int val = Integer.parseInt(TokenHelper.obtainTokenAtIndex(getInstructionString(), 3));

        SystemSubject ss = SystemSubjectsContainer.get(subjName);
        SystemObject so = SystemObjectsContainer.get(objName);
        performWrite(so, ss, val);
    }

    private void performWrite(SystemObject so, SystemSubject ss, int valToWrite)
    {
        so.setValue(valToWrite);
    }

    @Override
    public String toString()
    {
        String subjectName = TokenHelper.getSubjectName(getInstructionString());
        String objectName = TokenHelper.getObjectName(getInstructionString());
        return subjectName + " writes value " + TokenHelper.obtainTokenAtIndex(getInstructionString(), 3) + " to " + objectName;
    }
}
