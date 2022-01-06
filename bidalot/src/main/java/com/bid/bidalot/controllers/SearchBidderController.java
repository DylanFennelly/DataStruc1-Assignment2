package com.bid.bidalot.controllers;

import com.bid.bidalot.AuctionApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static com.bid.bidalot.controllers.StartController.bidScene;

public class SearchBidderController {

    @FXML
    protected void changeToBidderMenu(ActionEvent actionEvent) throws IOException {
        //reload bidder-view to refresh lots TableView to reflect any changes made
        Parent bidView = FXMLLoader.load(Objects.requireNonNull(AuctionApp.class.getResource("bidder-view.fxml")));
        bidScene = new Scene(bidView);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(bidScene);
        stage.setTitle("Bid-A-Lot: Bidders");
        stage.show();
    }
}
