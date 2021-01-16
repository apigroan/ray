package com.blindskipper.ray.gui.support;

import com.blindskipper.ray.common.FileParser;
import com.blindskipper.ray.jvm.ClassFileParser;
import javafx.scene.image.Image;
import javafx.stage.FileChooser.ExtensionFilter;

public enum FileType {

    FOLDER       ("/folder.png",  "Folder",              "/",       null),
    JAVA_JAR     ("/jar.png",     "Java JAR",            "*.jar",   null),
    JAVA_JMOD    ("/jmod.png",    "Java JMOD",           "*.jmod",  null),
    JAVA_CLASS   ("/java.png",    "Java Class",          "*.class", new ClassFileParser()),
    UNKNOWN      ("/file.png",    "Unknown",             "*.*",     FileParser.NOP),
    ;

    public final Image icon;
    public final ExtensionFilter filter;
    public final FileParser parser;

    FileType(String icon,
             String description,
             String extension,
             FileParser parser) {
        this.icon = ImageHelper.loadImage(icon);
        this.filter = new ExtensionFilter(description, extension);
        this.parser = parser;
    }

    public boolean isZip() {
        return this == JAVA_JAR || this == JAVA_JMOD;
    }

}
