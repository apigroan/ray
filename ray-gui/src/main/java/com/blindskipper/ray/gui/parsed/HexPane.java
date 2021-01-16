package com.blindskipper.ray.gui.parsed;

import com.blindskipper.ray.common.FileAssembly;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;

public class HexPane extends TextArea {
    
    private final HexText hex;
    
    public HexPane(HexText hex) {
        super(hex.getText());
        this.hex = hex;
        setEditable(false);
        setFont(Font.font("Courier New", 14));
    }
    
    public void select(FileAssembly cc) {
        HexText.Selection selection = hex.select(cc);
        positionCaret(selection.getStartPosition());
        selectPositionCaret(selection.getEndPosition());
    }
    
}
