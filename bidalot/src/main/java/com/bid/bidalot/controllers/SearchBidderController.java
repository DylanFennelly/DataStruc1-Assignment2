package com.bid.bidalot.controllers;

import com.bid.bidalot.AuctionApp;
import com.bid.bidalot.objects.Bidder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.util.Objects;

import static com.bid.bidalot.AuctionApp.DRIVER;
import static com.bid.bidalot.controllers.BidderController.bidderDetailsScene;
import static com.bid.bidalot.controllers.BidderController.selectedBidder;
import static com.bid.bidalot.controllers.StartController.bidScene;

public class SearchBidderController {
    private JFrame frame;
    @FXML
    private Label loginLabel;
    @FXML
    private TextField bidderNameField, bidderAddressField;
    @FXML
    private TableView<Bidder> biddersTV;

    @FXML
    protected void initialize() {
        if(AuctionApp.loggedInBidder != null){
            loginLabel.setText("Logged in as: " + AuctionApp.loggedInBidder.getName());
        }
    }

    @FXML
    private void searchButton(ActionEvent actionEvent){
        //clearing tableViews between searches
        biddersTV.getItems().clear();
        //setting up boolean conditions for easier tracking
        boolean nameSearch = false, addressSearch = false;
        if (!bidderNameField.getText().equals(""))  //if there is a value in the title field
            nameSearch = true;
        if (!bidderAddressField.getText().equals(""))
            addressSearch = true;

        if(nameSearch && !addressSearch){   //name search only
            for (int i=0; i<DRIVER.bidderHashTable.hashTableLength() ;i++){
                for (Bidder temp : DRIVER.bidderHashTable.getLinkedList(i)){
                    if (temp.getName().toLowerCase().contains(bidderNameField.getText().toLowerCase().trim()))
                        biddersTV.getItems().add(temp);
                }
            }
        }else if(!nameSearch && addressSearch) {  //address search only
            for (int i=0; i<DRIVER.bidderHashTable.hashTableLength() ;i++){
                for (Bidder temp : DRIVER.bidderHashTable.getLinkedList(i)){
                    if (temp.getAddress().toLowerCase().contains(bidderAddressField.getText().toLowerCase().trim()))
                        biddersTV.getItems().add(temp);
                }
            }

        }else if (nameSearch && addressSearch){ //both
            for (int i=0; i<DRIVER.bidderHashTable.hashTableLength() ;i++){
                for (Bidder temp : DRIVER.bidderHashTable.getLinkedList(i)){
                    if (temp.getName().toLowerCase().contains(bidderNameField.getText().toLowerCase().trim()) && temp.getAddress().toLowerCase().contains(bidderAddressField.getText().toLowerCase().trim()))
                        biddersTV.getItems().add(temp);
                }
            }

        }else{ //none (display all bidders)
            for (int i=0; i<DRIVER.bidderHashTable.hashTableLength() ;i++){
                for (Bidder temp : DRIVER.bidderHashTable.getLinkedList(i)){
                    biddersTV.getItems().add(temp);
                }
            }
        }
        JOptionPane.showMessageDialog(frame, "Search Complete!", "Search Complete!", JOptionPane.INFORMATION_MESSAGE);

    }
    @FXML
    protected void changeToBidderDetails(ActionEvent actionEvent) throws IOException {
        selectedBidder = DRIVER.bidderHashTable.findPositionByEmail(biddersTV.getSelectionModel().getSelectedItem().getEmail());
        if (selectedBidder == null) {
            JOptionPane.showMessageDialog(frame, "Please select a bidder to view the details of.", "Selection Error!", JOptionPane.ERROR_MESSAGE);
        }else {
            Parent detailsView = FXMLLoader.load(Objects.requireNonNull(AuctionApp.class.getResource("bidder-details-view.fxml")));
            bidderDetailsScene = new Scene(detailsView);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(bidderDetailsScene);
            stage.setTitle("Bid-A-Lot: " + selectedBidder.getName());
        }
    }

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
