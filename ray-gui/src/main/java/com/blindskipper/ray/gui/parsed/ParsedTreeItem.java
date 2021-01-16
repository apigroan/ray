package com.blindskipper.ray.gui.parsed;

import com.blindskipper.ray.common.FileAssembly;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

public class ParsedTreeItem extends TreeItem<FileAssembly> {

    private boolean isFirstTimeChildren = true;

    public ParsedTreeItem(FileAssembly cc) {
        super(cc);
    }

    @Override
    public boolean isLeaf() {
        return getValue().getParts().isEmpty();
    }

    @Override
    public ObservableList<TreeItem<FileAssembly>> getChildren() {
        if (isFirstTimeChildren) {
            isFirstTimeChildren = false;
            super.getChildren().setAll(buildChildren());
        }
        
        return super.getChildren();
    }

    private ObservableList<TreeItem<FileAssembly>> buildChildren() {
        ObservableList<TreeItem<FileAssembly>> children = FXCollections.observableArrayList();
        getValue().getParts().forEach(sub -> {
            children.add(new ParsedTreeItem(sub));
        });
        return children;
    }
    
}
