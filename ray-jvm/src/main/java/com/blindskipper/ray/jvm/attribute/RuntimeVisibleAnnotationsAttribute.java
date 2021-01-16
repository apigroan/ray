package com.blindskipper.ray.jvm.attribute;

import com.blindskipper.ray.common.ParseException;
import com.blindskipper.ray.jvm.ClassAssembly;
import com.blindskipper.ray.jvm.ClassFileReader;
import com.blindskipper.ray.jvm.constant.ConstantPool;
import com.blindskipper.ray.jvm.data.type.U1;

public class RuntimeVisibleAnnotationsAttribute extends AttributeInfo {

    {
        u2   ("num_annotations");
        table("annotations", AnnotationInfo.class);
    }
    
    public static class AnnotationInfo extends ClassAssembly {

        {
            u2cp ("type_index");
            u2   ("num_element_value_pairs");
            table("element_value_pairs", ElementValuePair.class);
        }

        
        @Override
        protected void postRead(ConstantPool cp) {
            int typeIndex = super.getUInt("type_index");
            setDescription(cp.getUtf8String(typeIndex));
        }
        
    }
    
    public static class ElementValuePair extends ClassAssembly {

        {
            u2cp("element_name_index");
            add ("value", new ElementValue());
        }

        @Override
        protected void postRead(ConstantPool cp) {
            int elementNameIndex = super.getUInt("element_name_index");
            setDescription(cp.getUtf8String(elementNameIndex));
        }
        
    }
    
    public static class ElementValue extends ClassAssembly {
        
        @Override
        protected void readContent(ClassFileReader reader) {
            byte tag = reader.getByte(reader.getPosition());
            preRead(tag);
            super.readContent(reader);
        }

        private void preRead(byte tag) {
            u1("tag");
            switch (tag) {
                case 'B':
                case 'C':
                case 'D':
                case 'F':
                case 'I':
                case 'J':
                case 'S':
                case 'Z':
                case 's':
                    u2cp("const_value_index");
                    break;
                case 'e':
                    add("enum_const_value", new EnumConstValue());
                    break;
                case 'c':
                    u2cp("class_info_index");
                    break;
                case '@':
                    add("annotation_value", new AnnotationInfo());
                    break;
                case '[':
                    add("array_value", new ArrayValue());
                    break;
                default: throw new ParseException("Invalid element_value tag: " + tag);
            }
        }

        @Override
        protected void postRead(ConstantPool cp) {
            U1 tag = (U1) super.get("tag");
            tag.setDescription(Character.toString((char) tag.getValue()));
        }

    }
    
    public static class EnumConstValue extends ClassAssembly {

        {
            u2cp("type_name_index");
            u2cp("const_name_index");
        }
        
    }
    
    public static class ArrayValue extends ClassAssembly {

        {
            u2   ("num_values");
            table("values", ElementValue.class);
        }
        
    }
    
}
