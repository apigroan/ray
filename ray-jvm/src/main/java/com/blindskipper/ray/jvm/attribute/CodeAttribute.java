package com.blindskipper.ray.jvm.attribute;

import com.blindskipper.ray.common.FileAssembly;
import com.blindskipper.ray.jvm.ClassAssembly;
import com.blindskipper.ray.jvm.ClassFileReader;
import com.blindskipper.ray.jvm.benua.Opcode;
import com.blindskipper.ray.jvm.bytecode.Instruction;
import com.blindskipper.ray.jvm.bytecode.InstructionFactory;
import com.blindskipper.ray.jvm.constant.ConstantPool;
import com.blindskipper.ray.jvm.data.type.U4;

import java.util.List;

public class CodeAttribute extends AttributeInfo {

    {
        U4 codeLength = new U4();

        u2   ("max_stack");
        u2   ("max_locals");
        add  ("code_length", codeLength);
        add  ("code", new Code(codeLength));
        u2   ("exception_table_length");
        table("exception_table", ExceptionTableEntry.class);
        u2   ("attributes_count");
        table("attributes", AttributeInfo.class);
    }

    public static class ExceptionTableEntry extends ClassAssembly {

        {
            u2  ("start_pc");
            u2  ("end_pc");
            u2  ("handler_pc");
            u2cp("catch_type");
        }

    }

    private class Code extends ClassAssembly {

        private final U4 codeLength;

        public Code(U4 codeLength) {
            this.codeLength = codeLength;
        }

        @Override
        protected void readContent(ClassFileReader reader) {
            final int startPosition = reader.getPosition();
            final int endPosition = startPosition + codeLength.getValue();

            int position;
            while ((position = reader.getPosition()) < endPosition) {
                int pc = position - startPosition;
                byte b = reader.getByte(position);
                Opcode opcode = Opcode.valueOf(Byte.toUnsignedInt(b));
                Instruction instruction = InstructionFactory.create(opcode, pc);
                instruction.read(reader);
                add(instruction);
            }
        }

        @Override
        protected void postRead(ConstantPool cp) {
            List<FileAssembly> instructions = super.getParts();

            int maxPc = ((Instruction) instructions.get(instructions.size() - 1)).getPc();
            int pcWidth = String.valueOf(maxPc).length();
            String fmtStr = "%0" + pcWidth + "d";
            for (FileAssembly c : instructions) {
                Instruction instruction = (Instruction) c;
                instruction.setName(String.format(fmtStr, instruction.getPc()));
            }
        }

    }

}
