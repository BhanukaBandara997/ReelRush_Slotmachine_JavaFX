package main;

import javafx.scene.image.Image;

// this class implement method of the interface to set images to the imageView
public class Symbol implements ISymbol {

    Image image;
    int value;

    @Override
    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public Image getImage() {
        return image;

    }

    @Override
    public void setValue(int value) {
        this.value = value;

    }

    @Override
    public int getValue() {
        return value;

    }
}