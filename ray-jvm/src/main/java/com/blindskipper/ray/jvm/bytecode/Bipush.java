package com.blindskipper.ray.jvm.bytecode;


import com.blindskipper.ray.jvm.ClassFileReader;
import com.blindskipper.ray.jvm.benua.Opcode;

public class Bipush extends Instruction {

    public Bipush(Opcode opcode, int pc) {
        super(opcode, pc);
    }

    @Override
    protected void readOperands(ClassFileReader reader) {
        byte operand = reader.readByte();
        setDescription(getDescription() + " " + operand);
    }
    
}
