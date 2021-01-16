package com.blindskipper.ray.jvm.bytecode;

import com.blindskipper.ray.jvm.ClassFileReader;
import com.blindskipper.ray.jvm.benua.Opcode;

public class Sipush extends Instruction {

    public Sipush(Opcode opcode, int pc) {
        super(opcode, pc);
    }

    @Override
    protected void readOperands(ClassFileReader reader) {
        short operand = reader.readShort();
        setDescription(getDescription() + " " + operand);
    }
    
}
