package main;

import javafx.scene.image.Image;

import java.util.Random;

// reel images are added to a array and set each image value
public class Reel {

   public Symbol[] spin(){

       //creating seperate object for assign values and to add them to the array
        Symbol cherry = new Symbol();
        Symbol redSeven = new Symbol();
        Symbol watermelon = new Symbol();
        Symbol bell	 = new Symbol();
        Symbol lemon = new Symbol();
        Symbol plum = new Symbol();

        //getting a random number
        Random random = new Random();

        //all images are added to this array
        Symbol[] symbolArray = new Symbol[6];

        for (int i = 0; i < symbolArray.length; i++) {
            int randomNumber = random.nextInt(6); // getting a random number within the range of 0-6 without including 6
            switch (randomNumber) {
                case 0:
                    cherry.setValue(2);
                    cherry.setImage(new Image("/images/cherry.png"));
                    symbolArray[i] = cherry;
                    break;
                case 1:
                    lemon.setValue(3);
                    lemon.setImage(new Image("/images/lemon.png"));
                    symbolArray[i] = lemon;
                    break;
                case 2:
                    plum.setValue(4);
                    plum.setImage(new Image("/images/plum.png"));
                    symbolArray[i] = plum;
                    break;
                case 3:
                    watermelon.setValue(5);
                    watermelon.setImage(new Image("/images/watermelon.png"));
                    symbolArray[i] = watermelon;
                    break;
                case 4:
                    bell.setValue(6);
                    bell.setImage(new Image("/images/bell.png"));
                    symbolArray[i] = bell;
                    break;
                case 5:
                    redSeven.setValue(7);
                    redSeven.setImage(new Image("/images/redSeven.png"));
                    symbolArray[i] = redSeven;
                    break;
                default:
                    break;
            }

        }

        return symbolArray;

    }


}
