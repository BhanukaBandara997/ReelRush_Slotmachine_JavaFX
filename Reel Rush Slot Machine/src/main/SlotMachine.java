package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SlotMachine extends Application {

    // --------------------- Encapsulated fields ------------------

    // Image click boolean
    private boolean isImageClicked1 = false;
    private boolean isImageClicked2 = false;
    private boolean isImageClicked3 = false;

    // Creating a thread1
    Thread thread1, thread2, thread3;

    // Default image for reel
    private Image image = new Image("/images/icon.png");

    // UI variables
    private Text title;
    private Label lblStatus, lblInformationArea, lblBetAmount, lblCreditArea;
    private ImageView image1, image2, image3;
    private Button btnSpin, btnAddCoin, btnBetOne, btnBetMax, btnReset, btnStatistics, btnPayoutTable;


    // Calculation variables
    private static int remainingCoins = 10;
    private int betAmount, wonAmount, wins, lost, reel1Value, reel2Value, reel3Value;

    private int betMaxCount = 0;

    // Sounds
    private Media media = new Media(getClass().getResource("/sounds/Applause sound effect.mp3").toExternalForm());
    private MediaPlayer player = new MediaPlayer(media);

    // --------------------- Main application components -----------------------------
    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 0, 10, 0));
        grid.setHgap(20);
        grid.setVgap(20);

        // Title in row 0 column 3 with styling
        title = new Text();
        title.setCache(true);
        title.setText("REEL RUSH");
        title.setFill(Color.YELLOW);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 70));
        Reflection r = new Reflection();
        title.setEffect(r);
        GridPane.setConstraints(title, 3, 1);
        GridPane.setHalignment(title, HPos.CENTER);

        // Reel1 in row 4 column 2
        image1 = new ImageView(image);
        image1.setFitHeight(300);
        image1.setFitHeight(300);
        GridPane.setConstraints(image1, 2, 4);
        GridPane.setHalignment(image1, HPos.CENTER);

        // Reel2 in row 4 column 3
        image2 = new ImageView(image);
        image2.setFitHeight(300);
        image2.setFitHeight(300);
        GridPane.setConstraints(image2, 3, 4);
        GridPane.setHalignment(image2, HPos.CENTER);

        // Reel3 in row 4 column 4
        image3 = new ImageView(image);
        image3.setFitHeight(300);
        image3.setFitHeight(300);
        GridPane.setConstraints(image3, 4, 4);
        GridPane.setHalignment(image3, HPos.CENTER);

        // adding mouse click event for image views
        image1.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                isImageClicked1 = true;
                if (isImageClicked1 == true && isImageClicked2 == true && isImageClicked3 == true) {
                    imageClicked();
                }
            }
        });

        image2.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                isImageClicked2 = true;
                if (isImageClicked1 == true && isImageClicked2 == true && isImageClicked3 == true) {
                    imageClicked();
                }
            }
        });

        image3.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                isImageClicked3 = true;
                if (isImageClicked1 == true && isImageClicked2 == true && isImageClicked3 == true) {
                    imageClicked();
                }
            }
        });

        // Status label row 8 column 4
        lblStatus = new Label("GOOD LUCK! HAVE FUN!!!");
        lblStatus.setId("label-lblStatus");
        lblStatus.setTextFill(Color.RED);
        lblStatus.setFont(Font.font("Arial", 30));
        GridPane.setConstraints(lblStatus, 3, 8);
        GridPane.setHalignment(lblStatus, HPos.CENTER);

        // Information area label row 9 column 3
        lblInformationArea = new Label("INFORMATION AREA ");
        lblInformationArea.setId("label-lbl");
        GridPane.setConstraints(lblInformationArea, 3, 9);
        GridPane.setHalignment(lblInformationArea, HPos.CENTER);

        // Credit area label row 5 column 2
        lblCreditArea = new Label("CREDIT AREA: " + remainingCoins);
        lblCreditArea.setId("label-lbl");
        GridPane.setConstraints(lblCreditArea, 2, 9);
        GridPane.setHalignment(lblCreditArea, HPos.CENTER);

        // Bet amount label row 5 column 4
        lblBetAmount = new Label("BET AMOUNT: " + betAmount);
        lblBetAmount.setId("label-lbl");
        GridPane.setConstraints(lblBetAmount, 4, 9);
        GridPane.setHalignment(lblBetAmount, HPos.CENTER);

        // Add coin button row 6 column 3
        btnSpin = new Button("SPIN");
        btnSpin.setId("button-btnSpin");
        GridPane.setConstraints(btnSpin, 3, 10);
        GridPane.setHalignment(btnSpin, HPos.CENTER);

        // Add coin button row 12 column 2
        btnAddCoin = new Button("ADD COIN");
        GridPane.setConstraints(btnAddCoin, 2, 11);
        GridPane.setHalignment(btnAddCoin, HPos.CENTER);

        // Bet One coin button row 12 column 1
        btnBetOne = new Button("BET ONE");
        btnBetOne.setFont(Font.font("Arial", 20));
        GridPane.setConstraints(btnBetOne, 1, 11);
        GridPane.setHalignment(btnBetOne, HPos.CENTER);

        // Bet Max button row 12 column 4
        btnBetMax = new Button("BET MAX");
        GridPane.setConstraints(btnBetMax, 4, 11);
        GridPane.setHalignment(btnBetMax, HPos.CENTER);

        // Reset button row 12 column 6
        btnReset = new Button("RESET");
        GridPane.setConstraints(btnReset, 6, 11);
        GridPane.setHalignment(btnReset, HPos.CENTER);

        // Statistics button row 12 column 3
        btnStatistics = new Button("  STATISTICS  ");
        GridPane.setConstraints(btnStatistics, 3, 11);
        GridPane.setHalignment(btnStatistics, HPos.CENTER);

        // PayoutTable button row 12 column 3
        btnPayoutTable = new Button("PAYOUT");
        GridPane.setConstraints(btnPayoutTable, 3, 12);
        GridPane.setHalignment(btnPayoutTable, HPos.CENTER);


        // -------------------  Adding mouse events for each button  ---------------------------

        // To add coin
        btnAddCoin.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                remainingCoins++;
                lblCreditArea.setText("CREDIT AREA: " + remainingCoins);
            }
        });

        // To bet coin
        btnBetOne.setOnAction(new EventHandler<javafx.event.ActionEvent>() {

            @Override
            public void handle(javafx.event.ActionEvent event) {
                if (remainingCoins > 0) {

                    // disabling image while bet
                    image1.setDisable(true);
                    image2.setDisable(true);
                    image3.setDisable(true);

                    remainingCoins--;
                    betAmount++;
                    lblBetAmount.setText("BET AMOUNT: " + betAmount);

                    lblCreditArea.setText("CREDIT AREA: " + remainingCoins);
                } else {
                    lblInformationArea.setText("No CREDIT AVAILABLE !!! ADD A COIN TO CONTINUE");
                }
            }
        });

        // TO spin all three reels
        btnSpin.setOnAction(new EventHandler<javafx.event.ActionEvent>() {

            @Override
            public void handle(javafx.event.ActionEvent event) {
                if (betAmount > 0) {
                    isImageClicked1 = false;
                    isImageClicked2 = false;
                    isImageClicked3 = false;
                    spinReel();
                } else {
                    lblInformationArea.setText("PLEASE BID BEFORE SPIN !!!");
                }
            }
        });

        // To reset all the scene components
        btnReset.setOnAction(new EventHandler<javafx.event.ActionEvent>() {

            @Override
            public void handle(javafx.event.ActionEvent event) {
                player.stop();
                lblStatus.setText("GOOD LUCK! HAVE FUN!!!");
                remainingCoins = remainingCoins+betAmount;
                lblCreditArea.setText("CREDIT AREA: " + remainingCoins);
                betAmount=0;
                lblBetAmount.setText("BET AMOUNT: " + betAmount);
                lblInformationArea.setText("INFORMATION AREA");
                image1.setImage(image);
                image2.setImage(image);
                image3.setImage(image);
                btnBetMax.setDisable(false);

                wins = 0;
                lost = 0;
            }

        });

        // To bet max
        btnBetMax.setOnAction(new EventHandler<javafx.event.ActionEvent>() {

            @Override
            public void handle(javafx.event.ActionEvent event) {
                if (remainingCoins >= 3) {

                    //disabling images while bet
                    image1.setDisable(true);
                    image2.setDisable(true);
                    image3.setDisable(true);

                    remainingCoins = remainingCoins - 3;
                    betAmount = betAmount + 3;
                    lblBetAmount.setText("BET AMOUNT: " + betAmount);

                    lblCreditArea.setText("CREDIT AREA: " + remainingCoins);
                    ++betMaxCount;
                    if (betMaxCount >= 1) {
                        btnBetMax.setDisable(true);
                        lblInformationArea.setText("Press BET MAX once per game");
                    }
                } else {
                    lblInformationArea.setText("No CREDIT AVAILABLE !!! ADD A COIN TO CONTINUE");
                }
            }
        });

        // To view statistics
        btnStatistics.setOnAction(new EventHandler<javafx.event.ActionEvent>() {

            @Override
            public void handle(javafx.event.ActionEvent event) {
                if ((wins + lost) > 0)
                    statistic();
                else
                    lblInformationArea.setText("SPIN THE REEL FIRST");
            }

        });

        // To view payout table
        btnPayoutTable.setOnAction(new EventHandler<javafx.event.ActionEvent>() {

            @Override
            public void handle(javafx.event.ActionEvent event) {
                payoutTable();
            }

        });

        // adding all to the scene and to the stage to show
        grid.getChildren().addAll(title, lblStatus, lblInformationArea, lblCreditArea, lblBetAmount, btnAddCoin, btnBetMax, btnBetOne, btnReset, btnSpin, btnStatistics, btnPayoutTable, image1, image3, image2);
        grid.setAlignment(Pos.TOP_CENTER);
        Scene scene = new Scene(grid, 1500, 1000);
        scene.getStylesheets().add("/css/main.css");
        primaryStage.setTitle("REEL RUSH");
        primaryStage.setScene(scene);
        primaryStage.show();
        Platform.setImplicitExit(true);
        primaryStage.setOnCloseRequest((event) -> {
            Platform.exit();
            System.exit(0);
        });

    }

    // To spin the reel and start the thread1 also it ma help to stop the reel after user click an image
    public synchronized void spinReel() {
        player.stop();
        thread1 = new Thread(new Runnable() {
            @Override
            public void run() {

                while (!isImageClicked1) {

                    image1.setDisable(false);
                    image2.setDisable(false);
                    image3.setDisable(false);

                    //disabling all other buttons while spinning
                    btnSpin.setDisable(true);
                    btnAddCoin.setDisable(true);
                    btnBetOne.setDisable(true);
                    btnBetMax.setDisable(true);
                    btnReset.setDisable(true);
                    btnStatistics.setDisable(true);

                    //creating reel objects for each reel
                    Reel firstReel = new Reel();

                    // Assigning them to a Symbol array
                    Symbol[] firstReelSymbols = firstReel.spin();

                    //looping through all the images and setting them to the imageView
                    for (Symbol item : firstReelSymbols) {
                        Image img = item.getImage();
                        image1.setImage(img);
                        reel1Value = item.getValue();
                    }

                    try {
                        thread1.sleep(200); // sleeping time for the thread1
                    } catch (InterruptedException e) {
                        System.out.println(e);
                    }

                }
            }
        });
        thread1.start(); // starting the thread1 that created

        thread2 = new Thread(new Runnable() {
            @Override
            public void run() {

                while (!isImageClicked2) {

                    image1.setDisable(false);
                    image2.setDisable(false);
                    image3.setDisable(false);

                    //disabling all other buttons while spinning
                    btnSpin.setDisable(true);
                    btnAddCoin.setDisable(true);
                    btnBetOne.setDisable(true);
                    btnBetMax.setDisable(true);
                    btnReset.setDisable(true);
                    btnStatistics.setDisable(true);

                    //creating reel objects for each reel
                    Reel secondReel = new Reel();

                    // Assigning them to a Symbol array
                    Symbol[] secondReelSymbols = secondReel.spin();

                    //looping through all the images and setting them to the imageView
                    for (Symbol item : secondReelSymbols) {
                        Image img = item.getImage();
                        image2.setImage(img);
                        reel2Value = item.getValue();
                    }


                    try {
                        thread2.sleep(200); // sleeping time for the thread1
                    } catch (InterruptedException e) {
                        System.out.println(e);
                    }

                }
            }
        });
        thread2.start(); // starting the thread2 that created

        thread3 = new Thread(new Runnable() {
            @Override
            public void run() {

                while (!isImageClicked3) {

                    image1.setDisable(false);
                    image2.setDisable(false);
                    image3.setDisable(false);

                    //disabling all other buttons while spinning
                    btnSpin.setDisable(true);
                    btnAddCoin.setDisable(true);
                    btnBetOne.setDisable(true);
                    btnBetMax.setDisable(true);
                    btnReset.setDisable(true);
                    btnStatistics.setDisable(true);

                    //creating reel objects for each reel
                    Reel thirdReel = new Reel();

                    // Assigning them to a Symbol array
                    Symbol[] thirdReelSymbols = thirdReel.spin();

                    //looping through all the images and setting them to the imageView
                    for (Symbol item : thirdReelSymbols) {
                        Image img = item.getImage();
                        image3.setImage(img);
                        reel3Value = item.getValue();
                    }

                    try {
                        thread3.sleep(200); // sleeping time for the thread1
                    } catch (InterruptedException e) {
                        System.out.println(e);
                    }

                }
            }
        });
        thread3.start(); // starting the thread3 that created
    }

    // This is execute after stopping reels to show whether you win or not
    public void imageClicked() {
        //disabling all other buttons while spinning
        btnSpin.setDisable(false);
        btnAddCoin.setDisable(false);
        btnBetOne.setDisable(false);
        btnBetMax.setDisable(false);
        btnReset.setDisable(false);
        btnStatistics.setDisable(false);

        // Check if all 3 numbers are same
        if ((reel2Value == reel3Value) || (reel1Value == reel2Value)) {
            wonAmount = (betAmount * reel2Value);
            remainingCoins += (betAmount * reel2Value);
            lblStatus.setText("CONGRATULATION!!! YOU WON - AMOUNT: " + wonAmount);
            lblCreditArea.setText("CREDIT AREA: " + remainingCoins);
            player.play();
            wins++;

        } else if((reel1Value == reel3Value)){
            wonAmount = (betAmount * reel1Value);
            remainingCoins += (betAmount * reel1Value);
            lblStatus.setText("CONGRATULATION!!! YOU WON - AMOUNT: " + wonAmount);
            lblCreditArea.setText("CREDIT AREA: " + remainingCoins);
            player.play();
            wins++;

        } else {
            lblStatus.setText("YOU LOOSE !!! BETTER LUCK NEXT TIME");
            lost++;
            player.stop();
        }
        betAmount = 0;
        lblBetAmount.setText("BET AMOUNT: " + betAmount);
    }

    // Statistics window
    public void statistic() {

        GridPane statisticWindow = new GridPane();
        statisticWindow.setPadding(new Insets(5, 0, 5, 0));
        statisticWindow.setHgap(10);
        statisticWindow.setVgap(10);

        // Creating pie and assign wins and losts to that pie data
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Wins", wins),
                        new PieChart.Data("Loses", lost));
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("STATISTICS");
        GridPane.setConstraints(chart, 2, 3);

        // Calculating net amount per game
        double netAmount = (double) (((remainingCoins - 10) - betAmount) / (wins + lost));
        Label lblNetAverage = new Label("Average Credits Netted Per Game: " + netAmount);
        GridPane.setConstraints(lblNetAverage, 2, 5);
        GridPane.setHalignment(lblNetAverage, HPos.CENTER);

        Button btnSave = new Button("SAVE");
        GridPane.setConstraints(btnSave, 2, 15);
        GridPane.setHalignment(btnSave, HPos.CENTER);

        // Saving to a file using File writer
        btnSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                WriteToAFile writeToAFile = new WriteToAFile();
                writeToAFile.writeFile(wins, lost, netAmount);
            }
        });

        statisticWindow.getChildren().addAll(chart,lblNetAverage,btnSave);
        Scene statisticScene = new Scene(statisticWindow, 400, 500);
        Stage stage = new Stage();
        statisticWindow.setAlignment(Pos.CENTER);
        stage.setScene(statisticScene);
        stage.setResizable(false);
        stage.show();
    }

    // Payout table window
    public void payoutTable() {

        GridPane payoutTableGrid = new GridPane();
        payoutTableGrid.setPadding(new Insets(5, 5, 5, 5));
        payoutTableGrid.setHgap(10);
        payoutTableGrid.setVgap(10);

        Text titlePayoutable = new Text("PAYOUT TABLE");
        titlePayoutable.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        GridPane.setConstraints(titlePayoutable, 5, 0);
        GridPane.setHalignment(titlePayoutable, HPos.CENTER);

        ImageView cherryImage = new ImageView(new Image("/images/cherry.png"));
        cherryImage.setFitWidth(200);
        cherryImage.setFitHeight(200);
        GridPane.setConstraints(cherryImage, 2, 2);
        GridPane.setHalignment(cherryImage, HPos.CENTER);

        Text cherryText = new Text(" 3x   " + betAmount + " * " + " 2 " +" = "+ betAmount * 2+ "\n" +
                                   " 2x   " + betAmount + " * " + " 2 " +" = "+ betAmount * 2);
        cherryText.setFont(Font.font("Arial",20));
        GridPane.setConstraints(cherryText, 2, 3);
        GridPane.setHalignment(cherryText, HPos.CENTER);

        ImageView lemonImage = new ImageView(new Image("/images/lemon.png"));
        lemonImage.setFitWidth(200);
        lemonImage.setFitHeight(200);
        GridPane.setConstraints(lemonImage, 5, 2);
        GridPane.setHalignment(lemonImage, HPos.CENTER);

        Text lemonText = new Text(" 3x   " + betAmount + " * " + " 3 " +" = "+ betAmount * 3+ "\n" +
                                  " 2x   " + betAmount + " * " + " 3 " +" = "+ betAmount * 3);
        lemonText.setFont(Font.font("Arial",20));
        GridPane.setConstraints(lemonText, 5, 3);
        GridPane.setHalignment(lemonText, HPos.CENTER);

        ImageView plumImage = new ImageView(new Image("/images/plum.png"));
        plumImage.setFitWidth(200);
        plumImage.setFitHeight(200);
        GridPane.setConstraints(plumImage, 7, 2);
        GridPane.setHalignment(plumImage, HPos.CENTER);

        Text plumText = new Text(" 3x   " + betAmount + " * " + " 4 " +" = "+ betAmount * 4+ "\n" +
                                 " 2x   " + betAmount + " * " + " 4 " +" = "+ betAmount * 4);
        plumText.setFont(Font.font("Arial",20));
        GridPane.setConstraints(plumText, 7, 3);
        GridPane.setHalignment(plumText, HPos.CENTER);

        ImageView watermelonImage = new ImageView(new Image("/images/watermelon.png"));
        watermelonImage.setFitWidth(200);
        watermelonImage.setFitHeight(200);
        GridPane.setConstraints(watermelonImage, 2, 6);
        GridPane.setHalignment(watermelonImage, HPos.CENTER);

        Text watermelonText = new Text(" 3x   " + betAmount + " * " + " 5 " +" = "+ betAmount * 5+ "\n" +
                                       " 2x   " + betAmount + " * " + " 5 " +" = "+ betAmount * 5);
        watermelonText.setFont(Font.font("Arial",20));
        GridPane.setConstraints(watermelonText, 2, 7);
        GridPane.setHalignment(watermelonText, HPos.CENTER);

        ImageView bellImage = new ImageView(new Image("/images/bell.png"));
        bellImage.setFitWidth(200);
        bellImage.setFitHeight(200);
        GridPane.setConstraints(bellImage, 5, 6);
        GridPane.setHalignment(bellImage, HPos.CENTER);

        Text bellText = new Text(" 3x   " + betAmount + " * " + " 6 " +" = "+ betAmount * 6+ "\n" +
                                 " 2x   " + betAmount + " * " + " 6 " +" = "+ betAmount * 6);
        bellText.setFont(Font.font("Arial",20));
        GridPane.setConstraints(bellText, 5, 7);
        GridPane.setHalignment(bellText, HPos.CENTER);

        ImageView sevenImage = new ImageView(new Image("/images/redSeven.png"));
        sevenImage.setFitWidth(200);
        sevenImage.setFitHeight(200);
        GridPane.setConstraints(sevenImage, 7, 6);
        GridPane.setHalignment(sevenImage, HPos.CENTER);

        Text sevenText = new Text(" 3x   " + betAmount + " * " + " 7 " +" = "+ betAmount * 7+ "\n" +
                                  " 2x   " + betAmount + " * " + " 7 " +" = "+ betAmount * 7);
        sevenText.setFont(Font.font("Arial",20));
        GridPane.setConstraints(sevenText, 7, 7);
        GridPane.setHalignment(sevenText, HPos.CENTER);

        payoutTableGrid.getChildren().addAll(cherryImage, cherryText, lemonImage, lemonText, plumImage, plumText, watermelonImage, watermelonText, bellImage, bellText, sevenImage, sevenText, titlePayoutable);

        Scene payoutTableScene = new Scene(payoutTableGrid, 700, 650);
        Stage stage = new Stage();
        payoutTableGrid.setAlignment(Pos.CENTER);
        stage.setScene(payoutTableScene);
        stage.setTitle("Payout Table");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}


