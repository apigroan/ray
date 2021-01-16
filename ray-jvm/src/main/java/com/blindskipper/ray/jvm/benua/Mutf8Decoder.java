package com.blindskipper.ray.jvm.benua;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Mutf8Decoder {
    
    public static String decodeMutf8(byte[] bytes) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(bytes.length + 2);
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeShort(bytes.length);
        dos.write(bytes);

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        DataInputStream dis = new DataInputStream(bais);
        return dis.readUTF();
    }
    
}
