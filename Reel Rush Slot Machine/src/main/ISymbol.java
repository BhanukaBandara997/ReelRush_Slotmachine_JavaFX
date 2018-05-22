package main;

import javafx.scene.image.Image;

// interface for abstract methods
public interface ISymbol {

    void setImage(Image icon);
    Image getImage();
    void setValue(int value);
    int getValue();

}