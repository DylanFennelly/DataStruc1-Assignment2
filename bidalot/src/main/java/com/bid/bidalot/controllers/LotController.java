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

import javax.swing.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

import static com.bid.bidalot.AuctionApp.DRIVER;

public class LotController {
    public static int LotIndex;
    public static Lot selectedLot;
    private JFrame frame; //used for popup windows
    public static Scene lotDetailsScene;
    boolean activeLots;     //keeping track of what state the view is in - preventing error when trying to enter sold lot
    @FXML
    private TableView<Lot> activeLotsTV, soldLotsTV;

    @FXML
    private Button addLotButton, activeLotsButton, soldLotsButton;

    @FXML
    private Label loginLabel, lotsLabel;

    @FXML
    protected void initialize() {
        if(AuctionApp.loggedInBidder != null){
            addLotButton.setDisable(false);
            loginLabel.setText("Logged in as: " + AuctionApp.loggedInBidder.getName());
        }
        activeLotsTV.getItems().clear();
        for (int i=0; i<DRIVER.lotHashTable.hashTableLength(); i++){
            for (Lot temp : DRIVER.lotHashTable.getLinkedList(i)){
                if(!temp.isSold())  //only displaying lots that have not been sold
                    activeLotsTV.getItems().add(temp);
                else
                    soldLotsTV.getItems().add(temp);
            }
        }
        activeLots = true;
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
        Parent addView = FXMLLoader.load(Objects.requireNonNull(AuctionApp.class.getResource("add-lot-view.fxml")));
        Scene addScene = new Scene(addView);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);     //locks main window until popup window is closed  |  https://stackoverflow.com/questions/15625987/block-owner-window-java-fx
        stage.initOwner(addView.getScene().getWindow());
        stage.setScene(addScene);
        stage.setResizable(false);
        stage.setTitle("Add Lot");
        stage.show();
    }

    @FXML
    protected void changeToLotDetails(ActionEvent actionEvent) throws IOException{
        System.out.println(activeLots);
        if (activeLots) {
            selectedLot = DRIVER.lotHashTable.findPosition(activeLotsTV.getSelectionModel().getSelectedItem());
        } else {
            selectedLot = DRIVER.lotHashTable.findPosition(soldLotsTV.getSelectionModel().getSelectedItem());
        }
        if (selectedLot == null) {
            JOptionPane.showMessageDialog(frame, "Please select a lot to view the details of.", "Selection Error!", JOptionPane.ERROR_MESSAGE);
        }else {
            Parent detailsView = FXMLLoader.load(Objects.requireNonNull(AuctionApp.class.getResource("lot-details-view.fxml")));
            lotDetailsScene = new Scene(detailsView);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(lotDetailsScene);
            stage.setTitle("Bid-A-Lot: " + selectedLot.getTitle());
        }
    }

    @FXML
    protected void showSoldLots(ActionEvent actionEvent){
        soldLotsTV.setVisible(true);
        activeLotsTV.setVisible(false);
        activeLotsButton.setVisible(true);
        soldLotsButton.setVisible(false);
        lotsLabel.setText("Sold Lots");
        activeLots=false;
    }

    @FXML
    protected void showActiveLots(ActionEvent actionEvent){
        soldLotsTV.setVisible(false);
        activeLotsTV.setVisible(true);
        activeLotsButton.setVisible(false);
        soldLotsButton.setVisible(true);
        lotsLabel.setText("Active Lots");
        activeLots=true;
    }
}
