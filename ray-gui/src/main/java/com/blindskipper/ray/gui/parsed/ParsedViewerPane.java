package com.blindskipper.ray.gui.parsed;

import com.blindskipper.ray.common.FileAssembly;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeView;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.BorderPane;

public class ParsedViewerPane extends BorderPane {
    
    private final TreeView<FileAssembly> tree;
    private final HexPane hexPane;
    private final Label statusLabel;
    private final BytesPane bytesBar;
    
    public ParsedViewerPane(FileAssembly file, HexText hex) {
        tree = buildClassTree(file);
        hexPane = new HexPane(hex);
        statusLabel = new Label(" ");
        bytesBar = new BytesPane(file.getLength());
        bytesBar.setMaxHeight(statusLabel.getPrefHeight());
        bytesBar.setPrefWidth(100);
        
        super.setCenter(buildSplitPane());
        super.setBottom(buildStatusBar());
        listenTreeItemSelection();
    }

    private static TreeView<FileAssembly> buildClassTree(FileAssembly file) {
        ParsedTreeItem root = new ParsedTreeItem(file);
        root.setExpanded(true);
        
        TreeView<FileAssembly> tree = new TreeView<>(root);
        tree.setMinWidth(200);
        
        return tree;
    }
    
    private SplitPane buildSplitPane() {
        SplitPane sp = new SplitPane();
        sp.getItems().add(tree);
        sp.getItems().add(hexPane);
        sp.setDividerPositions(0.3, 0.7);
        return sp;
    }
    
    private BorderPane buildStatusBar() {
        BorderPane statusBar = new BorderPane();
        statusBar.setLeft(statusLabel);
        statusBar.setRight(bytesBar);
        return statusBar;
    }
    
    private void listenTreeItemSelection() {
        tree.getSelectionModel().getSelectedItems().addListener(
            (ListChangeListener.Change<? extends TreeItem<FileAssembly>> c) -> {
                if (c.next() && c.wasAdded()) {
                    TreeItem<FileAssembly> node = c.getList().get(c.getFrom());
                    if (node != null && node.getParent() != null) {
                        FileAssembly cc = node.getValue();
                        statusLabel.setText(" " + cc.getClass().getSimpleName());
                        if (cc.getLength() > 0) {
                            hexPane.select(cc);
                            bytesBar.select(cc);
                        }
                    }
                }
            }
        );
    }
    
}
