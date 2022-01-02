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
import javafx.stage.Modality;
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
    protected void initialize(){
        if (AuctionApp.loggedInBidder != null){
            registerButton.setVisible(false);
            logoutButton.setVisible(true);
            usernameLabel.setText("Welcome,\n" + AuctionApp.loggedInBidder.getName());
            if (AuctionApp.loggedInBidder.getEmail().equals("ADMIN@ADMIN.COM"))
                adminButton.setVisible(true);
        }
    }

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

    @FXML
    protected void changeToRegisterLoginMenu(ActionEvent actionEvent) throws IOException, InterruptedException { //pops up in new window
        Parent addView = FXMLLoader.load(Objects.requireNonNull(AuctionApp.class.getResource("register-login-view.fxml")));
        Scene addScene = new Scene(addView);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);     //locks main window until popup window is closed  |  https://stackoverflow.com/questions/15625987/block-owner-window-java-fx
        stage.initOwner(addView.getScene().getWindow());
        stage.setScene(addScene);
        stage.setTitle("Register/Login");
        stage.show();
    }

    @FXML
    protected void logoutButton(ActionEvent actionEvent){
        System.out.println(AuctionApp.loggedInBidder);
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