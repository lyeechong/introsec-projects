
import java.util.List;

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

    public void performInstruction(Instruction instruction)
    {
        if (instruction.getClass().equals(ReadInstruction.class))
        {
            instruction.exec();
        }
        else if (instruction.getClass().equals(WriteInstruction.class))
        {
            instruction.exec();
        }
        else if (instruction.getClass().equals(CreateInstruction.class))
        {            
            instruction.exec();
        }
        else if (instruction.getClass().equals(DestroyInstruction.class))
        {
            instruction.exec();
        }
        else if (instruction.getClass().equals(RunInstruction.class))
        {
            instruction.exec();
        }
        else
        {
            assert false;
        }
    }
}
