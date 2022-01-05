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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.util.Objects;

import static com.bid.bidalot.AuctionApp.DRIVER;

public class BidderController {
    public static int BidderIndex;
    private JFrame frame; //used for popup windows
    public static Scene bidderDetailsScene;
    @FXML
    private TableView<Bidder> biddersTV;
    @FXML
    private Button myProfileButton;
    @FXML
    private Label loginLabel;

    @FXML
    protected void initialize() {
        if(AuctionApp.loggedInBidder != null){
            myProfileButton.setVisible(true);
            loginLabel.setText("Logged in as: " + AuctionApp.loggedInBidder.getName());
        }

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
    protected void changeToBidderDetails(ActionEvent actionEvent) throws IOException {
        //todo:hashing
        BidderIndex = biddersTV.getSelectionModel().getSelectedIndex();
        if (BidderIndex == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a bidder to view the details of.", "Selection Error!", JOptionPane.ERROR_MESSAGE);
        }else {
            Parent detailsView = FXMLLoader.load(Objects.requireNonNull(AuctionApp.class.getResource("bidder-details-view.fxml")));
            bidderDetailsScene = new Scene(detailsView);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(bidderDetailsScene);
            stage.setTitle("Bid-A-Lot: " + DRIVER.bidderList.getElementByInt(BidderIndex).getContents().getName());
        }
    }
}
