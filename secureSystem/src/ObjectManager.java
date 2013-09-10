/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lchong
 */
public class ObjectManager {

    public ObjectManager() {
    }

    private InstructionObject performWrite(SystemObject so, SystemSubject ss, int valToWrite) {
        so.setValue(valToWrite);
        return new InstructionObject(ss.getName() + " writes value " + valToWrite + " to " + so.getName());
    }
    
    private InstructionObject performRead(SystemObject so, SystemSubject ss) {
        int value = so.getValue();
        ss.setTempValue(value);
        return new InstructionObject(ss.getName() + " reads " + so.getName());
    }

    public InstructionObject performInstruction(String instruction) {
        if (TokenHelper.isRead(instruction)) {
            InstructionObject result;
            
            String subjName = TokenHelper.getSubjectName(instruction);
            String objName = TokenHelper.getObjectName(instruction);
            
            SystemSubject ss = SystemSubjectsContainer.get(subjName);
            SystemObject so = SystemObjectsContainer.get(objName);
            
            result = performRead(so, ss);
            return result;
        } else if (TokenHelper.isWrite(instruction)) {
            InstructionObject result;
            
            String subjName = TokenHelper.getSubjectName(instruction);
            String objName = TokenHelper.getObjectName(instruction);
            int val = Integer.parseInt(TokenHelper.obtainTokenAtIndex(instruction, 3));                    
            
            SystemSubject ss = SystemSubjectsContainer.get(subjName);
            SystemObject so = SystemObjectsContainer.get(objName);
            
            result = performWrite(so, ss, val);
            return result;
        } else {
            assert false;
            return null;
        }
    }
}
