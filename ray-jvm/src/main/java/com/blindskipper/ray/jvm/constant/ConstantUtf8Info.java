package com.blindskipper.ray.jvm.constant;

import com.blindskipper.ray.common.ParseException;
import com.blindskipper.ray.common.helper.StringHelper;
import com.blindskipper.ray.jvm.ClassAssembly;
import com.blindskipper.ray.jvm.ClassFileReader;
import com.blindskipper.ray.jvm.benua.Mutf8Decoder;
import com.blindskipper.ray.jvm.data.type.U2;

import java.io.IOException;

public class ConstantUtf8Info extends ConstantInfo {

    {
        U2 length = new U2();

        add("length", length);
        add("bytes", new Mutf8(length));
    }

    public String getString() {
        return ((Mutf8) super.get("bytes")).str;
    }

    @Override
    protected String loadDesc(ConstantPool cp) {
        Mutf8 bytes = (Mutf8) super.get("bytes");
        return StringHelper.chipAndAppendEllipsis(bytes.getDescription(), 100);
    }

    private class Mutf8 extends ClassAssembly {

        private final U2 length;
        private String str;

        public Mutf8(U2 length) {
            this.length = length;
        }

        @Override
        protected void readContent(ClassFileReader reader) {
            byte[] bytes = reader.readBytes(length.getValue());
            try {
                str = Mutf8Decoder.decodeMutf8(bytes);
            } catch (IOException e) {
                throw new ParseException(e);
            }

            setDescription(str);
        }

    }

}
