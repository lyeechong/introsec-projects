/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lchong
 */
public class BadInstruction extends Instruction
{

    public BadInstruction(String instructionString, boolean hasClearance)
    {
        super(instructionString, hasClearance);
    }

    @Override
    protected void execute()
    {
        // do nothing
    }

    @Override
    public String toString()
    {
        return "Bad instruction.";
    }
}
