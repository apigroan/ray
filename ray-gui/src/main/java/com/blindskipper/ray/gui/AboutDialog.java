package com.blindskipper.ray.gui;

import com.blindskipper.ray.gui.support.ImageHelper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class AboutDialog {
    
    public static void showDialog() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        
        BorderPane aboutPane = createAboutPane(stage);
        Scene scene = new Scene(aboutPane, 300, 180);
        
        stage.setScene(scene);
        stage.setTitle("About");
        stage.show();
    }
    
    private static BorderPane createAboutPane(Stage dialogStage) {
        BorderPane pane = new BorderPane();
        pane.setCenter(ImageHelper.createImageView("/main.png"));
        pane.setBottom(createHelpLink());
        pane.setOnMouseClicked(e -> dialogStage.close());
        
        return pane;
    }
    
    private static Hyperlink createHelpLink() {
        String homeUrl = "https://www.google.com/";
        Hyperlink link = new Hyperlink(homeUrl);
        link.setOnAction(e -> {
            try {
                Desktop.getDesktop().browse(URI.create(homeUrl));
            } catch (IOException x) {
                x.printStackTrace(System.err);
            }
        });

        BorderPane.setAlignment(link, Pos.CENTER);
        BorderPane.setMargin(link, new Insets(8));
        return link;
    }
    
}
