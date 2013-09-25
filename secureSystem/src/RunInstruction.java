

import java.util.ArrayList;
import java.util.List;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author lchong
 */
public class RunInstruction extends Instruction
{

    public RunInstruction(String instructionString, boolean hasClearance)
    {
        super(instructionString, hasClearance);
    }

    @Override
    protected void execute()
    {
        String ssName = TokenHelper.getSubjectName(getInstructionString());
        SystemSubject ss = SystemSubjectsContainer.get(ssName);        
        List<Instruction> instructionSet = ss.run();
        for (Instruction instr : instructionSet)
        {
            StaticStuff.getRf().performInstruction(instr.getInstructionString());                        
        }
    }

    @Override
    public String toString()
    {
        String subjectName = TokenHelper.getSubjectName(getInstructionString());
        return subjectName + " is running private code";
    }
}
