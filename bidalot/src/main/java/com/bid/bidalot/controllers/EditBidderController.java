package com.bid.bidalot.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static com.bid.bidalot.AuctionApp.DRIVER;
import static com.bid.bidalot.controllers.BidderController.BidderIndex;

public class EditBidderController {
    @FXML
    private Button closeButton;
    @FXML
    private TextField BName, BAddress, BPhone;

    @FXML
    protected void initialize() {
        //autofilling Bidder deatils into fields
        BName.setText(DRIVER.bidderList.getElementByInt(BidderIndex).getContents().getName());
        BAddress.setText(DRIVER.bidderList.getElementByInt(BidderIndex).getContents().getAddress());
        BPhone.setText(DRIVER.bidderList.getElementByInt(BidderIndex).getContents().getPhone());
    }


    protected void closeWindow(){    //https://stackoverflow.com/questions/25037724/how-to-close-a-java-window-with-a-button-click-javafx-project/41838183
        Stage stage = (Stage) closeButton.getScene().getWindow();   //gets scene from button node, then window from scene and closes the window
        stage.close();
    }

    @FXML
    protected void closeWindowButton(ActionEvent actionEvent){
        closeWindow();
    }
}
