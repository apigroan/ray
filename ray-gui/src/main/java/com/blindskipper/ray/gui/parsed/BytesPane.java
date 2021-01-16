package com.blindskipper.ray.gui.parsed;

import com.blindskipper.ray.common.FileAssembly;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class BytesPane extends Pane {

    private final int byteCount;

    public BytesPane(int byteCount) {
        this.byteCount = byteCount;
    }

    public void select(FileAssembly cc) {
        getChildren().clear();

        final double w = getWidth() - 4;
        final double h = getHeight();

        getChildren().add(new Line(0, h / 2, w, h / 2));
        getChildren().add(new Rectangle(w * cc.getOffset() / byteCount, 4,
                w * cc.getLength() / byteCount, h - 8));
    }

}
