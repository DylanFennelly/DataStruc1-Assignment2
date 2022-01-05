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

import java.io.IOException;
import java.util.Objects;

import static com.bid.bidalot.AuctionApp.DRIVER;
import static com.bid.bidalot.controllers.BidderController.selectedBidder;
import static com.bid.bidalot.controllers.StartController.bidScene;

public class BidderDetailsController {
    @FXML
    private Label loginLabel , nameLabel, addressLabel, phoneLabel, emailLabel;
    @FXML
    private Button editButton;

    @FXML
    protected void initialize() {
        if (AuctionApp.loggedInBidder != null) {
            loginLabel.setText("Logged in as: " + AuctionApp.loggedInBidder.getName());
            if (AuctionApp.loggedInBidder.getEmail().equals(selectedBidder.getEmail()) )  //if logged-in user is same as selected user profile, enable editing
                editButton.setDisable(false);
        }

        nameLabel.setText(selectedBidder.getName());
        addressLabel.setText(selectedBidder.getAddress());
        phoneLabel.setText(selectedBidder.getPhone());
        emailLabel.setText(selectedBidder.getEmail());
    }

    @FXML
    protected void changeToEditMenu(ActionEvent actionEvent) throws IOException {
        Parent editView = FXMLLoader.load(Objects.requireNonNull(AuctionApp.class.getResource("edit-bidder-view.fxml")));
        Scene editScene = new Scene(editView);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);     //locks main window until popup window is closed  |  https://stackoverflow.com/questions/15625987/block-owner-window-java-fx
        stage.initOwner(editView.getScene().getWindow());
        stage.setScene(editScene);
        stage.setResizable(false);
        stage.setTitle("Edit Bidder");
        stage.show();
    }

    @FXML
    protected void backToBidderMenu(ActionEvent actionEvent) throws IOException{
        //reload bidder-view to refresh lots TableView to reflect any changes made
        Parent bidView = FXMLLoader.load(Objects.requireNonNull(AuctionApp.class.getResource("bidder-view.fxml")));
        bidScene = new Scene(bidView);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(bidScene);
        stage.setTitle("Bid-A-Lot: Bidders");
        stage.show();
    }

    //todo: delete account
}
