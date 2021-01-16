package com.blindskipper.ray.jvm.bytecode;

import com.blindskipper.ray.jvm.ClassFileReader;
import com.blindskipper.ray.jvm.benua.Opcode;

public class Multianewarray extends Instruction {

    {
        u1  ("opcode");
        u2cp("index");
        u1  ("dimensions");
    }

    public Multianewarray(Opcode opcode, int pc) {
        super(opcode, pc);
    }
    
    @Override
    protected void readOperands(ClassFileReader reader) {
        setDescription(getDescription() + " "
                + super.get("index").getDescription() + ", "
                + super.getUInt("dimensions"));
    }
    
}
