package com.blindskipper.ray.jvm.bytecode;


import com.blindskipper.ray.jvm.benua.Opcode;
import com.blindskipper.ray.jvm.constant.ConstantPool;

public class InvokeInterface extends Instruction {

    {
        u1  ("opcode");
        u2cp("index");
        u1  ("count");
        u1  ("zero");
    }

    public InvokeInterface(Opcode opcode, int pc) {
        super(opcode, pc);
    }
    
    @Override
    protected void postRead(ConstantPool cp) {
        setDescription(getDescription() + " "
                + super.get("index").getDescription() + ", "
                + super.getUInt("count"));
    }
    
}
