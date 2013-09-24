
import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.HeaderTokenizer.Token;

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

    private ReferenceMonitor rf;
    private ClearanceChecker cc;

    public ObjectManager(ReferenceMonitor rf, ClearanceChecker cc)
    {
        this.rf = rf;
        this.cc = cc;
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
        else if (TokenHelper.isCreate(instruction))
        {
            String objName = TokenHelper.getObjectName(instruction);
            String ssName = TokenHelper.getSubjectName(instruction);
            SystemSubject ss = SystemSubjectsContainer.get(ssName);
            SecurityLevel securityLevel = cc.getSubjectClearance(ss);
            rf.createObject(objName, securityLevel, 0);
        }
        else if (TokenHelper.isDestroy(instruction))
        {
            String objName = TokenHelper.getObjectName(instruction);
            String ssName = TokenHelper.getSubjectName(instruction);
            SystemSubject ss = SystemSubjectsContainer.get(ssName);
            SystemObject so = SystemObjectsContainer.get(objName);
            boolean canDo = cc.hasClearance(ss, so, instruction);
            if (!canDo)
            {
                //do nothing
            }
            else
            {
                //delete obj
                rf.deleteObject(so);
            }
        }
        else if (TokenHelper.isRun(instruction))
        {
            String ssName = TokenHelper.getSubjectName(instruction);
            SystemSubject ss = SystemSubjectsContainer.get(ssName);
        }
        else
        {
            assert false;
        }
    }
}
