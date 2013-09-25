
import java.util.HashMap;
import java.util.Map;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author lchong
 */
public class ClearanceChecker
{

    Map<SystemSubject, SecurityLevel> subjectSecurityMap;
    Map<SystemObject, SecurityLevel> objectSecurityMap;

    public ClearanceChecker()
    {

        subjectSecurityMap = new HashMap<>();
        objectSecurityMap = new HashMap<>();
    }

    public void deleteObject(SystemObject so)
    {
        objectSecurityMap.remove(so);
    }

    public boolean hasClearance(SystemSubject ss, SystemObject so, String instruction)
    {
        boolean hasClearance = false;

        SecurityLevel ssSL = subjectSecurityMap.get(ss);        
        SecurityLevel soSL = objectSecurityMap.get(so);

        if (InstructionChecker.validRead(instruction))
        {
            if (ssSL == null || soSL == null)
            {
                return false;
            }
            //read down
            if (ssSL.compareTo(soSL) >= 0)
            {
                hasClearance = true;
            }
            else
            {
                hasClearance = false;
            }
        }
        else if (InstructionChecker.validWrite(instruction) || InstructionChecker.validDestroy(instruction))
        {
            if (ssSL == null || soSL == null)
            {
                return false;
            }
            //write up
            if (ssSL.compareTo(soSL) <= 0)
            {
                hasClearance = true;
            }
            else
            {
                hasClearance = false;
            }
        }
        else if (InstructionChecker.validCreate(instruction) || InstructionChecker.validDestroy(instruction) || InstructionChecker.validRun(instruction))
        {
            hasClearance = true;
        }
        else
        {
            assert false;
            return false;
        }

        return hasClearance;
    }

    public void setSubjectClearance(SystemSubject ss, SecurityLevel sl)
    {
        subjectSecurityMap.put(ss, sl);
    }

    public void setObjectClearance(SystemObject so, SecurityLevel sl)
    {
        objectSecurityMap.put(so, sl);
    }

    public SecurityLevel getSubjectClearance(SystemSubject ss)
    {
        return subjectSecurityMap.get(ss);
    }

    public SecurityLevel getObjectClearance(SystemObject so)
    {
        return subjectSecurityMap.get(so);
    }
}
