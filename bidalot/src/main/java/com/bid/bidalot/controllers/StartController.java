package com.bid.bidalot.controllers;

import com.bid.bidalot.AuctionApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.util.Objects;

public class StartController {
    private JFrame frame; //used for popup windows
    public static Scene lotScene;
    public static Scene bidScene;
    @FXML
    private Button registerButton, logoutButton, adminButton;
    @FXML
    private Label usernameLabel;

    @FXML
    protected void changeToLotMenu(ActionEvent actionEvent) throws IOException {
        Parent lotView = FXMLLoader.load(Objects.requireNonNull(AuctionApp.class.getResource("lot-view.fxml")));
        lotScene = new Scene(lotView);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(lotScene);
        stage.setTitle("Bid-A-Lot: Lots");
        stage.show();
    }

    @FXML
    protected void changeToBidderMenu(ActionEvent actionEvent) throws IOException {
        Parent bidView = FXMLLoader.load(Objects.requireNonNull(AuctionApp.class.getResource("bidder-view.fxml")));
        bidScene = new Scene(bidView);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(bidScene);
        stage.setTitle("Bid-A-Lot: Bidders");
        stage.show();
    }

    //temporary save and load buttons for testing
    @FXML
    protected void saveButton(ActionEvent actionEvent) throws Exception{
        AuctionApp.save();
        JOptionPane.showMessageDialog(frame, "Save complete!", "Save Status", JOptionPane.INFORMATION_MESSAGE);
    }

    @FXML
    protected void loadButton(ActionEvent actionEvent)  throws Exception{
        AuctionApp.load();
        JOptionPane.showMessageDialog(frame, "Load complete!", "Load Status", JOptionPane.INFORMATION_MESSAGE);
    }
}