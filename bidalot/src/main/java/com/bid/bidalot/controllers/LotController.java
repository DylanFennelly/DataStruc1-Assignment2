package com.bid.bidalot.controllers;

import com.bid.bidalot.AuctionApp;
import com.bid.bidalot.objects.Lot;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

import static com.bid.bidalot.AuctionApp.DRIVER;

public class LotController {
    @FXML
    private TableView<Lot> activeLotsTV;

    @FXML
    private Button addLotButton;

    @FXML
    private Label loginLabel;

    @FXML
    protected void initialize() {
        if(AuctionApp.loggedInBidder != null){
            addLotButton.setDisable(false);
            loginLabel.setText("Logged in as: " + AuctionApp.loggedInBidder.getName());
        }
        activeLotsTV.getItems().clear();
        for (Lot l : DRIVER.lotList){
            activeLotsTV.getItems().add(l);
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
    protected void changeToAddMenu(ActionEvent actionEvent) throws IOException {    //pops up in new window     todo: check for signed in
        Parent addView = FXMLLoader.load(Objects.requireNonNull(AuctionApp.class.getResource("add-lot-view.fxml")));
        Scene addScene = new Scene(addView);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);     //locks main window until popup window is closed  |  https://stackoverflow.com/questions/15625987/block-owner-window-java-fx
        stage.initOwner(addView.getScene().getWindow());
        stage.setScene(addScene);
        stage.setTitle("Add Lot");
        stage.show();
    }

    @FXML
    protected void addDummyValues(ActionEvent actionEvent){
        LocalDate origin = LocalDate.now().minusYears(20);
        //Lot testLot = new Lot("Old chair for sale","Dark oak chair from roughly 20 years ago. Ships from Kilkenny, Ireland.","Furniture","",origin,50.00);
        //activeLotsTV.getItems().add(testLot);
    }
}
