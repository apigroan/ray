package com.blindskipper.ray.jvm.bytecode;

import com.blindskipper.ray.jvm.benua.Opcode;
import com.blindskipper.ray.jvm.constant.ConstantPool;

public class InstructionCp1 extends Instruction {

    {
        u1  ("opcode");
        u1cp("operand");
    }

    public InstructionCp1(Opcode opcode, int pc) {
        super(opcode, pc);
    }

    @Override
    protected void postRead(ConstantPool cp) {
        setDescription(getDescription() + " " + super.get("operand").getDescription());
    }

}
