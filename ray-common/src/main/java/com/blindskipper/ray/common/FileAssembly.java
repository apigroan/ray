package com.blindskipper.ray.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class FileAssembly {
    
    private String name;
    private String description;
    private int offset;
    private int length;
    private List<FileAssembly> parts;
    
    public final String getName() {return name;}
    public final void setName(String name) {this.name = name;}
    public final String getDescription() {return description;}
    public final void setDescription(String desc) {this.description = desc;}
    public final int getOffset() {return offset;}
    public final void setOffset(int offset) {this.offset = offset;}
    public final int getLength() {return length;}
    public final void setLength(int length) {this.length = length;}

    public List<FileAssembly> getParts() {
        return parts == null
                ? Collections.emptyList()
                : Collections.unmodifiableList(parts);
    }

    protected final FileAssembly get(String name) {
        for (FileAssembly c : parts) {
            if (name.equals(c.getName())) {
                return c;
            }
        }
        return null;
    }

    protected final void add(String name, FileAssembly subPart) {
        if (name != null) {
            subPart.setName(name);
        }
        if (parts == null) {
            parts = new ArrayList<>();
        }
        parts.add(subPart);
    }

    protected final void clear() {
        parts.clear();
    }

    @Override
    public final String toString() {
        if (name != null && description != null) {
            return name + ": " + description;
        }
        if (name != null) {
            return name;
        }
        if (description != null) {
            return description;
        }

        return getClass().getSimpleName();
    }

}
