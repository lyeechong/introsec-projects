/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lchong
 */
public class ObjectManager
{

    public ObjectManager()
    {
    }

    private void performWrite(SystemObject so, SystemSubject ss, int valToWrite)
    {
        so.setValue(valToWrite);
    }

    private void performRead(SystemObject so, SystemSubject ss)
    {
        int value = so.getValue();
        ss.setTempValue(value);
    }

    public void performUnauthorizedRead(String instruction)
    {
        if (!TokenHelper.isRead(instruction))
        {
            assert false;
        }

        String subjName = TokenHelper.getSubjectName(instruction);
        SystemSubject ss = SystemSubjectsContainer.get(subjName);
        ss.setTempValue(0);
    }

    public void performInstruction(String instruction)
    {
        if (TokenHelper.isRead(instruction))
        {
            String subjName = TokenHelper.getSubjectName(instruction);
            String objName = TokenHelper.getObjectName(instruction);

            SystemSubject ss = SystemSubjectsContainer.get(subjName);
            SystemObject so = SystemObjectsContainer.get(objName);
            performRead(so, ss);

        }
        else if (TokenHelper.isWrite(instruction))
        {
            String subjName = TokenHelper.getSubjectName(instruction);
            String objName = TokenHelper.getObjectName(instruction);
            int val = Integer.parseInt(TokenHelper.obtainTokenAtIndex(instruction, 3));

            SystemSubject ss = SystemSubjectsContainer.get(subjName);
            SystemObject so = SystemObjectsContainer.get(objName);
            performWrite(so, ss, val);
        }
        else
        {
            assert false;
        }
    }
}
