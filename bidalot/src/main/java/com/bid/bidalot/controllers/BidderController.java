package com.bid.bidalot.controllers;

import com.bid.bidalot.AuctionApp;
import com.bid.bidalot.objects.Bidder;
import com.bid.bidalot.objects.Lot;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static com.bid.bidalot.AuctionApp.DRIVER;

public class BidderController {
    @FXML
    private TableView<Bidder> biddersTV;

    @FXML
    protected void initialize() {
        biddersTV.getItems().clear();
        for (Bidder b : DRIVER.bidderList){
            biddersTV.getItems().add(b);
        }
    }

    @FXML
    protected void changeToStartMenu(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(AuctionApp.startScene);
        stage.setTitle("Bid-A-Lot");
        stage.show();
    }

    @FXML
    protected void changeToAddMenu(ActionEvent actionEvent) throws IOException {    //pops up in new window
        Parent addView = FXMLLoader.load(Objects.requireNonNull(AuctionApp.class.getResource("add-bidder-view.fxml")));
        Scene addScene = new Scene(addView);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);     //locks main window until popup window is closed  |  https://stackoverflow.com/questions/15625987/block-owner-window-java-fx
        stage.initOwner(addView.getScene().getWindow());
        stage.setScene(addScene);
        stage.setTitle("Add Bidder");
        stage.show();
    }

}