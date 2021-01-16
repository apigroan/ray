package com.blindskipper.ray.jvm.bytecode;


import com.blindskipper.ray.jvm.ClassFileReader;
import com.blindskipper.ray.jvm.benua.Opcode;

public class Iinc extends Instruction {

    public Iinc(Opcode opcode, int pc) {
        super(opcode, pc);
    }
    
    @Override
    protected void readOperands(ClassFileReader reader) {
        int index = reader.readUnsignedByte();
        int _const = reader.readByte();
        setDescription(getDescription() + " " + index + ", " + _const);
    }
    
}
