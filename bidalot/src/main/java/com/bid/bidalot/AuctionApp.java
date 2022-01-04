package com.bid.bidalot;

import com.bid.bidalot.lists.MyLinkedList;
import com.bid.bidalot.lists.Node;
import com.bid.bidalot.objects.Bidder;
import com.bid.bidalot.objects.Lot;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.text.DecimalFormat;
import java.util.Objects;


public class AuctionApp extends Application {
    //limiting price floats to 2 decimal places   |   https://stackoverflow.com/questions/8911356/whats-the-best-practice-to-round-a-float-to-2-decimals
    public static final DecimalFormat DF = new DecimalFormat("#.00");
    public static Bidder loggedInBidder = null;
    public static Driver DRIVER;
    public static Scene startScene;

    public static class Driver {
        public MyLinkedList<Bidder> bidderList = new MyLinkedList<>();
        public MyLinkedList<Lot> lotList = new MyLinkedList<>();
    }

    @Override
    public void start(Stage stage) throws IOException {
        DRIVER = new Driver();

        Parent p = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("start-view.fxml")));
        stage.setTitle("Bid-A-Lot");
        startScene = new Scene(p);
        stage.setScene(startScene);
        stage.setResizable(false);
        stage.show();
    }

    public static void save() throws Exception{
        XStream xStream = new XStream((new DomDriver()));
        ObjectOutputStream out = xStream.createObjectOutputStream(new FileWriter("auctionApp.xml"));
        out.writeObject(AuctionApp.DRIVER);
        out.close();
        System.out.println("Saved to auctionApp.xml");
    }

    public static void load() throws Exception {
        XStream xStream = new XStream((new DomDriver()));
        xStream.addPermission(AnyTypePermission.ANY);       //granting permissions to set read object to the driver  | from: https://stackoverflow.com/questions/30812293/com-thoughtworks-xstream-security-forbiddenclassexception
        ObjectInputStream in = xStream.createObjectInputStream(new FileReader("auctionApp.xml"));
        AuctionApp.DRIVER = (Driver) in.readObject();    //casting readObject to type Driver
        in.close();
        System.out.println("Loaded from auctionApp.xml");
    }

    public static void main(String[] args) {
        launch();
    }
}