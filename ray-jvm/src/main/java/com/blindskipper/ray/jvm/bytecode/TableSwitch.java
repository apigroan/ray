package com.blindskipper.ray.jvm.bytecode;

import com.blindskipper.ray.jvm.ClassAssembly;
import com.blindskipper.ray.jvm.ClassFileReader;
import com.blindskipper.ray.jvm.benua.Opcode;

public class TableSwitch extends Instruction {

    public TableSwitch(Opcode opcode, int pc) {
        super(opcode, pc);
    }
    
    @Override
    protected void readOperands(ClassFileReader reader) {
        skipPadding(reader);
        
        JumpOffset defaultOffset = readJumpOffset(reader, "default");
        
        int low = reader.readInt();
        int high = reader.readInt();
        
        for (int i = low; i <= high; i++) {
            JumpOffset offset = readJumpOffset(reader, String.valueOf(i));
            add(offset);
        }

        add(defaultOffset);
    }
    
    private void skipPadding(ClassFileReader reader) {
        for (int i = 1; (pc + i) %4 != 0; i++) {
            reader.readByte();
        }
    }
    
    private JumpOffset readJumpOffset(ClassFileReader reader, String name) {
        JumpOffset offset = new JumpOffset();
        offset.read(reader);
        offset.setName(name);
        offset.setDescription(Integer.toString(pc + offset.offset));
        return offset;
    }

    
    public static class JumpOffset extends ClassAssembly {

        private int offset;
        
        @Override
        protected void readContent(ClassFileReader reader) {
            offset = reader.readInt();
        }
        
    }
    
}
