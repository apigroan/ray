package com.blindskipper.ray.jvm.attribute;


import com.blindskipper.ray.common.ParseException;
import com.blindskipper.ray.jvm.ClassAssembly;
import com.blindskipper.ray.jvm.ClassFileReader;
import com.blindskipper.ray.jvm.data.type.U1Hex;
import com.blindskipper.ray.jvm.data.type.UInt;

public class RuntimeVisibleTypeAnnotationsAttribute extends AttributeInfo {

    {
        u2   ("num_annotations");
        table("annotations", TypeAnnotationInfo.class);
    }
    
    public static class TypeAnnotationInfo extends ClassAssembly {

        {
            U1Hex targetType = new U1Hex();

            add("target_type", targetType);
            add("target_info", new TargetInfo(targetType));
            add("target_path", new TypePath());
            add("annotation", new RuntimeVisibleAnnotationsAttribute.AnnotationInfo());
        }
    
    }
    
    public static class TargetInfo extends ClassAssembly {

        private final UInt targetType;

        public TargetInfo(UInt targetType) {
            this.targetType = targetType;
        }
        
        @Override
        protected void readContent(ClassFileReader reader) {
            switch (targetType.getValue()) {
                case 0x00:
                case 0x01:
                    u1("typeParameterIndex");
                    break;
                case 0x10:
                    u2("supertypeIndex");
                    break;
                case 0x11:
                case 0x12:
                    u1("typeParameterIndex");
                    u1("boundIndex");
                    break;
                case 0x13:
                case 0x14:
                case 0x15:
                    break;
                case 0x16:
                    u1("formalParameterIndex");
                    break;
                case 0x17:
                    u2("throwsTypeIndex");
                    break;
                case 0x40:
                case 0x41:
                    u2("tableLength");
                    table("table", LocalVarInfo.class);
                    break;
                case 0x42:
                    u2("exceptionTableIndex");
                    break;
                case 0x43:
                case 0x44:
                case 0x45:
                case 0x46:
                    u2("offset");
                    break;
                case 0x47:
                case 0x48:
                case 0x49:
                case 0x4A:
                case 0x4B:
                    u2("offset");
                    u1("typeArgumentIndex");
                    break;
                default: throw new ParseException("Invalid target_type: " + targetType.getValue());
            }
            super.readContent(reader);
        }
        
    }
    
    public static class LocalVarInfo extends ClassAssembly {

        {
            u2("start_pc");
            u2("length");
            u2("index");
        }
        
    }
    
    public static class TypePath extends ClassAssembly {

        {
            u1   ("path_length");
            table("path", PathInfo.class);
        }
        
    }
    
    public static class PathInfo extends ClassAssembly {

        {
            u1("type_path_kind");
            u1("type_argument_index");
        }
        
    }
    
}
