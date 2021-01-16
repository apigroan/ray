package com.blindskipper.ray.jvm.bytecode;

import com.blindskipper.ray.jvm.ClassAssembly;
import com.blindskipper.ray.jvm.ClassFileReader;
import com.blindskipper.ray.jvm.benua.Opcode;

public class Instruction extends ClassAssembly {

    protected final Opcode opcode;
    protected final int pc;

    public Instruction(Opcode opcode, int pc) {
        this.opcode = opcode;
        this.pc = pc;
        setDescription(opcode.name());
    }

    public int getPc() {
        return pc;
    }
    
    @Override
    protected final void readContent(ClassFileReader reader) {
        if (!super.getParts().isEmpty()) {
            super.readContent(reader);
        } else {
            reader.readUnsignedByte();
            readOperands(reader);
        }
    }
    
    protected void readOperands(ClassFileReader reader) {
        if (opcode.operandCount > 0) {
            reader.skipBytes(opcode.operandCount);
        }
    }

}
