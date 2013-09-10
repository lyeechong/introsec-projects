
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

    public boolean hasClearance(SystemSubject ss, SystemObject so, String instruction)
    {
        boolean hasClearance = false;

        SecurityLevel ssSL = subjectSecurityMap.get(ss);
        SecurityLevel soSL = objectSecurityMap.get(so);

        if (ssSL == null || soSL == null)
        {
            return false;
        }

        if (TokenHelper.isRead(instruction))
        {
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
        else if (TokenHelper.isWrite(instruction))
        {
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
}
