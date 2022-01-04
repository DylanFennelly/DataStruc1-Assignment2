package com.bid.bidalot.controllers;

import com.bid.bidalot.AuctionApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

import static com.bid.bidalot.AuctionApp.DRIVER;
import static com.bid.bidalot.controllers.BidderController.BidderIndex;
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
            if (AuctionApp.loggedInBidder.getEmail().equals(DRIVER.bidderList.getElementByInt(BidderIndex).getContents().getEmail()) )  //if logged-in user is same as selected user profile, enable editing
                editButton.setDisable(false);
        }

        nameLabel.setText(DRIVER.bidderList.getElementByInt(BidderIndex).getContents().getName());
        addressLabel.setText(DRIVER.bidderList.getElementByInt(BidderIndex).getContents().getAddress());
        phoneLabel.setText(DRIVER.bidderList.getElementByInt(BidderIndex).getContents().getPhone());
        emailLabel.setText(DRIVER.bidderList.getElementByInt(BidderIndex).getContents().getEmail());
    }

    @FXML
    protected void backToBidderMenu(ActionEvent actionEvent) throws IOException{
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(bidScene);
        stage.setTitle("Bid-A-Lot: Bidders");
        stage.show();
    }
}
