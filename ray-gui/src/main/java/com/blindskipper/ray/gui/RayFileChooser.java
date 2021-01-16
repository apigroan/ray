package com.blindskipper.ray.gui;

import com.blindskipper.ray.gui.support.FileType;
import com.blindskipper.ray.gui.support.RecentOpenFiles;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class RayFileChooser {

    private static FileChooser fileChooser;
    private static DirectoryChooser dirChooser;

    public static File showFileChooser(Stage stage, FileType ft) {
        if (fileChooser == null) {
            fileChooser = new FileChooser();
            fileChooser.setTitle("Open file");
        }

        File lastOpenFile = RecentOpenFiles.INSTANCE.getLastOpenFile(ft);
        if (lastOpenFile != null && lastOpenFile.getParentFile().isDirectory()) {
            fileChooser.setInitialDirectory(lastOpenFile.getParentFile());
        }

        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().add(ft.filter);

        return fileChooser.showOpenDialog(stage);
    }

    public static File showDirChooser(Stage stage) {
        if (dirChooser == null) {
            dirChooser = new DirectoryChooser();
            dirChooser.setTitle("Open folder");
        }

        return dirChooser.showDialog(stage);
    }

}
