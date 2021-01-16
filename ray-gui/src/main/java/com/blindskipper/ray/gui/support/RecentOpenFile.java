package com.blindskipper.ray.gui.support;

import java.net.MalformedURLException;

public class RecentOpenFile {

    public final FileType type;
    public final String url;

    public RecentOpenFile(FileType type, String url) {
        this.type = type;
        this.url = url;
    }

    public RecentOpenFile(String str) throws MalformedURLException {
        this(FileType.valueOf(str.split("#=>")[0]), str.split("#=>")[1]);
    }

    @Override
    public String toString() {
        return type + "#=>" + url;
    }

    @Override
    public int hashCode() {
        return url.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof RecentOpenFile)
                && ((RecentOpenFile) o).url.equals(this.url);
    }

}
