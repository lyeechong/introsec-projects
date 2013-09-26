/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lchong
 */
public abstract class Instruction
{

    private final String instructionString;
    private boolean hasClearance;

    public Instruction(String instructionString, boolean hasClearance)
    {
        this.instructionString = instructionString;
        this.hasClearance = hasClearance;
    }

    public boolean isHasClearance()
    {
        return hasClearance;
    }

    public void exec()
    {
        if (StaticStuff.isVerbose())
        {
            printToLog();
        }
        //print();
        execute();
    }

    public boolean hasClearance()
    {
        return hasClearance;
    }

    public String getInstructionString()
    {
        return instructionString;
    }

    protected void printToLog()
    {
        LogWriterManager.getLog().writeLine(toString());
    }

    protected void print()
    {
        System.out.println(toString());
    }

    protected abstract void execute();

    public abstract String toString();
}
