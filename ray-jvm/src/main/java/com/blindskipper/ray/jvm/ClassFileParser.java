package com.blindskipper.ray.jvm;


import com.blindskipper.ray.common.FileParser;
import com.blindskipper.ray.common.FileAssembly;
import com.blindskipper.ray.jvm.constant.ConstantPool;

public class ClassFileParser implements FileParser {

    @Override
    public ClassFile parse(byte[] data) {
        ClassFile cf = new ClassFile();
        cf.read(new ClassFileReader(data));
        postRead(cf, cf.getConstantPool());
        return cf;
    }

    private static void postRead(ClassAssembly fc, ConstantPool cp) {
        for (FileAssembly c : fc.getParts()) {
            postRead((ClassAssembly) c, cp);
        }
        fc.postRead(cp);
    }

}
