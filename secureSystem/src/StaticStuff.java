/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lchong
 */
public class StaticStuff
{

    private static ReferenceMonitor rf;
    private static ClearanceChecker cc;
    private static ObjectManager om;

    private StaticStuff()
    {
    }

    public static ObjectManager getOm()
    {
        return om;
    }

    public static void setOm(ObjectManager om)
    {
        StaticStuff.om = om;
    }

    public static void setCc(ClearanceChecker cc)
    {
        StaticStuff.cc = cc;
    }

    public static void setRf(ReferenceMonitor rf)
    {
        StaticStuff.rf = rf;
    }

    public static ClearanceChecker getCc()
    {
        return cc;
    }

    public static ReferenceMonitor getRf()
    {
        return rf;
    }
}
