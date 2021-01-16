package com.blindskipper.ray.gui.support;

import com.blindskipper.ray.common.FileAssembly;
import com.blindskipper.ray.gui.parsed.HexText;
import com.blindskipper.ray.gui.tree.ZipTreeNode;

public class OpenFileResult {

    public final String url;
    public final FileType fileType;
    public final ZipTreeNode zipRootNode;
    public final FileAssembly fileRootNode;
    public final HexText hexText;

    public OpenFileResult(String url, FileType fileType,
                          ZipTreeNode zipRootNode) {
        this.url = url;
        this.fileType = fileType;
        this.zipRootNode = zipRootNode;
        this.fileRootNode = null;
        this.hexText = null;
    }

    public OpenFileResult(String url, FileType fileType,
                          FileAssembly fileRootNode, HexText hexText) {
        this.url = url;
        this.fileType = fileType;
        this.zipRootNode = null;
        this.fileRootNode = fileRootNode;
        this.hexText = hexText;
    }

}
