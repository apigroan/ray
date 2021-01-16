package com.blindskipper.ray.jvm.constant;


import com.blindskipper.ray.common.FileAssembly;
import com.blindskipper.ray.common.ParseException;
import com.blindskipper.ray.common.helper.StringHelper;
import com.blindskipper.ray.jvm.ClassAssembly;
import com.blindskipper.ray.jvm.ClassFileReader;
import com.blindskipper.ray.jvm.data.type.U2;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ConstantPool extends ClassAssembly {
    
    private final U2 cpCount;
    private ConstantInfo[] constants;

    public ConstantPool(U2 cpCount) {
        this.cpCount = cpCount;
    }
    
    @Override
    protected void readContent(ClassFileReader reader) {
        constants = new ConstantInfo[cpCount.getValue()];
        for (int i = 1; i < cpCount.getValue(); i++) {
            ConstantInfo c = readConstantInfo(reader);
            setConstantName(c, i);
            constants[i] = c;
            if (c instanceof ConstantLongInfo || c instanceof ConstantDoubleInfo) {
                i++;
            }
        }
        loadConstantDesc();
        reader.setConstantPool(this);
    }
    
    private ConstantInfo readConstantInfo(ClassFileReader reader) {
        byte tag = reader.getByte(reader.getPosition());
        
        ConstantInfo ci = ConstantFactory.create(tag);
        ci.read(reader);
        
        return ci;
    }
    
    private void setConstantName(ConstantInfo constant, int idx) {
        String idxStr = StringHelper.formatIndex(cpCount.getValue(), idx);
        String constantName = constant.getClass().getSimpleName()
                .replace("Constant", "")
                .replace("Info", "");
        constant.setName(idxStr + " (" + constantName + ")");
    }
    
    private void loadConstantDesc() {
        for (ConstantInfo c : constants) {
            if (c != null) {
                c.setDescription(c.loadDesc(this));
            }
        }
    }

    @Override
    public List<FileAssembly> getParts() {
        return Arrays.stream(constants)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public String getUtf8String(int index) {
        return getConstant(ConstantUtf8Info.class, index).getString();
    }
    
    public ConstantUtf8Info getUtf8Info(int index) {
        return getConstant(ConstantUtf8Info.class, index);
    }
    
    public ConstantClassInfo getClassInfo(int index) {
        return getConstant(ConstantClassInfo.class, index);
    }
    
    public ConstantNameAndTypeInfo getNameAndTypeInfo(int index) {
        return getConstant(ConstantNameAndTypeInfo.class, index);
    }
    
    public ConstantFieldrefInfo getFieldrefInfo(int index) {
        return getConstant(ConstantFieldrefInfo.class, index);
    }
    
    public ConstantMethodrefInfo getMethodrefInfo(int index) {
        return getConstant(ConstantMethodrefInfo.class, index);
    }
    
    public ConstantInterfaceMethodrefInfo getInterfaceMethodrefInfo(int index) {
        return getConstant(ConstantInterfaceMethodrefInfo.class, index);
    }
    
    private <T> T getConstant(Class<T> classOfT, int index) {
        ConstantInfo c = constants[index];
        if (c.getClass() != classOfT) {
            throw new ParseException("Constant#" + index
                    + " is " + c.getClass().getSimpleName()
                    + " not " + classOfT.getSimpleName() + "!");
        }
        return classOfT.cast(c);
    }
    
    public String getConstantDesc(int index) {
        ConstantInfo c = constants[index];
        return c.getDescription();
    }
    
}
