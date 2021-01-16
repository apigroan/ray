package com.blindskipper.ray.jvm.bytecode;


import com.blindskipper.ray.jvm.benua.Opcode;
import com.blindskipper.ray.jvm.constant.ConstantPool;

public class InvokeDynamic extends Instruction {

    {
        u1  ("opcode");
        u2cp("index");
        u2  ("zero");
    }

    public InvokeDynamic(Opcode opcode, int pc) {
        super(opcode, pc);
    }
    
    @Override
    protected void postRead(ConstantPool cp) {
        setDescription(getDescription() + " " + super.get("index").getDescription());
    }
    
}
