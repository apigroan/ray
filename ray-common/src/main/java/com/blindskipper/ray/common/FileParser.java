package com.blindskipper.ray.common;

public interface FileParser {

    FileParser NOP = data -> new FileAssembly() {};

    FileAssembly parse(byte[] data);

}
