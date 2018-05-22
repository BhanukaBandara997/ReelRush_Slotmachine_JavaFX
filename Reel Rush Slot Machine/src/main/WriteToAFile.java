package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Scanner;

// this class helps to save data to a text file after click save button in the statistics window
public class WriteToAFile {


    public void writeFile(int wins, int lost, double netamount) {

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy--HH-mm-ss");
        long systemTime = System.currentTimeMillis();
        String date = dateFormat.format(systemTime);
        String fileName = date+".txt";

        File file = new File(""+fileName);
        PrintWriter printWriter = null;
        FileWriter fileWriter = null;


        try {
            try {
                fileWriter = new FileWriter(file,true);
            } catch (IOException e) {
                System.out.println(e);
            }
            printWriter = new PrintWriter(fileWriter);
            printWriter.println("WINS: "+wins+"\t LOST: "+lost+"\t NET AMOUNT: "+netamount);

        } finally{
            printWriter.close();
        }

    }


}

