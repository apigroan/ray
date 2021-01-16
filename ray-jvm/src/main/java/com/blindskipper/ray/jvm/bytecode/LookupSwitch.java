package com.blindskipper.ray.jvm.bytecode;

import com.blindskipper.ray.jvm.ClassAssembly;
import com.blindskipper.ray.jvm.ClassFileReader;
import com.blindskipper.ray.jvm.benua.Opcode;

public class LookupSwitch extends Instruction {

    public LookupSwitch(Opcode opcode, int pc) {
        super(opcode, pc);
    }
    
    @Override
    protected void readOperands(ClassFileReader reader) {
        skipPadding(reader);
        
        MatchOffset defaultOffset = new MatchOffset(true, pc);
        defaultOffset.read(reader);
        
        int npairs = reader.readInt();
        for (int i = 0; i < npairs; i++) {
            MatchOffset offset = new MatchOffset(false, pc);
            offset.read(reader);
            add(offset);
        }
        
        add(defaultOffset);
    }
    
    private void skipPadding(ClassFileReader reader) {
        for (int i = 1; (pc + i) %4 != 0; i++) {
            reader.readByte();
        }
    }
    
    public static class MatchOffset extends ClassAssembly {

        private final boolean isDefault;
        private final int basePc;
        private int match;
        private int offset;

        public MatchOffset(boolean isDefault, int basePc) {
            this.isDefault = isDefault;
            this.basePc = basePc;
        }
        
        @Override
        protected void readContent(ClassFileReader reader) {
            if (!isDefault) {
                match = reader.readInt();
                setName(String.valueOf(match));
            } else {
                setName("default");
            }
            
            offset = reader.readInt();
            setDescription(Integer.toString(basePc + offset));
        }
        
    }
    
}
