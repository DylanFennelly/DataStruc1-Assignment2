package com.bid.bidalot;

import com.bid.bidalot.lists.MyLinkedList;
import com.bid.bidalot.objects.Bidder;
import com.bid.bidalot.objects.Lot;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Objects;


public class AuctionApp extends Application {
    //limiting price floats to 2 decimal places   |   https://stackoverflow.com/questions/8911356/whats-the-best-practice-to-round-a-float-to-2-decimals
    public static final DecimalFormat DF = new DecimalFormat("#.00");
    public static AuctionApp.Driver DRIVER;
    public static Scene startScene;

    public static class Driver {
        public MyLinkedList<Lot> lotList = new MyLinkedList<>();
        public MyLinkedList<Bidder> bidderList = new MyLinkedList<>();
    }

    @Override
    public void start(Stage stage) throws IOException {
        DRIVER = new AuctionApp.Driver();

        Parent p = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("start-view.fxml")));
        stage.setTitle("Bid-A-Lot");
        startScene = new Scene(p);
        stage.setScene(startScene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}