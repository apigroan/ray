package com.blindskipper.ray.gui.support;

import java.net.URL;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageHelper {

    public static ImageView createImageView(String imgName) {
        return new ImageView(loadImage(imgName));
    }

    public static Image loadImage(String imgName) {
        URL imgUrl = ImageHelper.class.getResource(imgName);
        return new Image(imgUrl.toString());
    }

}
