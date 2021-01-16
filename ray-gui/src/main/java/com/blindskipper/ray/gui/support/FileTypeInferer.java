package com.blindskipper.ray.gui.support;

import java.util.Arrays;

public class FileTypeInferer {

    private static final byte[] classMagicNumber = {
            (byte) 0xCA, (byte) 0xFE, (byte) 0xBA, (byte) 0xBE
    };

    public static FileType inferFileType(String url) {
        url = url.toLowerCase();
       if (url.endsWith(".jar")) {
            return FileType.JAVA_JAR;
        } else if (url.endsWith(".jmod")) {
            return FileType.JAVA_JMOD;
        } else if (url.endsWith(".class")) {
            return FileType.JAVA_CLASS;
        } else {
            return FileType.UNKNOWN;
        }
    }

    public static FileType inferFileType(byte[] data) {
        if (data.length >= 4) {
            byte[] magicNumber = Arrays.copyOf(data, 4);
            if (Arrays.equals(magicNumber, classMagicNumber)) {
                return FileType.JAVA_CLASS;
            }
        }
        return FileType.UNKNOWN;
    }

}
