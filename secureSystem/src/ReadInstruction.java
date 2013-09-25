

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author lchong
 */
public class ReadInstruction extends Instruction
{

    public ReadInstruction(String instructionString, boolean hasClearance)
    {
        super(instructionString, hasClearance);
    }

    @Override
    protected void execute()
    {
        String subjName = TokenHelper.getSubjectName(getInstructionString());
        SystemSubject ss = SystemSubjectsContainer.get(subjName);
        if (hasClearance())
        {
            String objName = TokenHelper.getObjectName(getInstructionString());
            SystemObject so = SystemObjectsContainer.get(objName);
            performRead(so, ss);
        }
        else
        {
            performUnauthorizedRead(ss);
        }
    }

    private void performRead(SystemObject so, SystemSubject ss)
    {
        int value = so.getValue();
        ss.setTempValue(value);

    }

    public void performUnauthorizedRead(SystemSubject ss)
    {
        if (!InstructionChecker.validRead(getInstructionString()))
        {
            assert false;
        }

        ss.setTempValue(0);
    }

    @Override
    public String toString()
    {
        String subjectName = TokenHelper.getSubjectName(getInstructionString());
        String objectName = TokenHelper.getObjectName(getInstructionString());
        return subjectName + " reads " + objectName;
    }
}
