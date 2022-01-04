package com.bid.bidalot.controllers;

import com.bid.bidalot.AuctionApp;
import com.bid.bidalot.objects.Bid;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static com.bid.bidalot.AuctionApp.DRIVER;
import static com.bid.bidalot.controllers.LotController.LotIndex;

public class LotDetailsController {
    @FXML
    private Label loginLabel, lotTitleLabel, lotTypeLabel, lotOriginLabel, soldLabel, lotNoImageLabel, lotStartedLabel, lotSoldDateLabel, currentBidLabel, startPriceLabel;
    @FXML
    private TextArea lotDescTA;
    @FXML
    private Hyperlink lotImageLink;
    @FXML
    private TextField bidField;
    @FXML
    private TableView<Bid> bidTV;
    @FXML
    private Button bidButton, sellLotButton, withdrawLotButton;

    @FXML
    protected void initialize(){
        if(AuctionApp.loggedInBidder != null){
            bidButton.setDisable(false);    //only able to place bid if logged in
            loginLabel.setText("Logged in as: " + AuctionApp.loggedInBidder.getName());
            if (AuctionApp.loggedInBidder == DRIVER.lotList.getElementByInt(LotIndex).getContents().getLotOwner()) {    //if logged in as creator of lot,
                sellLotButton.setVisible(true); //enable sell and withdraw functionality
                withdrawLotButton.setVisible(true);
                bidButton.setVisible(false);    //cant place bid if owner of lot
                bidField.setVisible(false);
            }
        }
        lotTitleLabel.setText(DRIVER.lotList.getElementByInt(LotIndex).getContents().getTitle());   //setting title
        lotTypeLabel.setText("In " + DRIVER.lotList.getElementByInt(LotIndex).getContents().getType()); //setting lot type
        lotOriginLabel.setText("circa " + DRIVER.lotList.getElementByInt(LotIndex).getContents().getOriginDate());  //setting origin label
        lotDescTA.setText(DRIVER.lotList.getElementByInt(LotIndex).getContents().getDescription()); //setting description
        if (!DRIVER.lotList.getElementByInt(LotIndex).getContents().getImageLink().equals("N/A"))   //if image link supplied, display link
            lotImageLink.setText(DRIVER.lotList.getElementByInt(LotIndex).getContents().getImageLink());
        else {  //else, display N/A label
            lotImageLink.setVisible(false);
            lotNoImageLabel.setVisible(true);
        }
        //setting start date, time and lot owner
        lotStartedLabel.setText("Lot started on " + DRIVER.lotList.getElementByInt(LotIndex).getContents().getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yy")) +     //https://howtodoinjava.com/java/date-time/localdate-format-example/
                " at " + DRIVER.lotList.getElementByInt(LotIndex).getContents().getStartTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")) +                                //https://www.geeksforgeeks.org/localtime-format-method-in-java-with-examples/
                " by " + DRIVER.lotList.getElementByInt(LotIndex).getContents().getLotOwner().getName());
        currentBidLabel.setText("Current Bid: " + String.format("%.2f",DRIVER.lotList.getElementByInt(LotIndex).getContents().getAskingPrice())); //current highest bid
        startPriceLabel.setText("Started at: " + String.format("%.2f",DRIVER.lotList.getElementByInt(LotIndex).getContents().getStartPrice()));   //start asking price

        bidTV.getItems().clear();   //populating bid list
        for (Bid b : DRIVER.lotList.getElementByInt(LotIndex).getContents().getListOfBids()){
            bidTV.getItems().add(b);
        }

        //if lot has been sold
        if(DRIVER.lotList.getElementByInt(LotIndex).getContents().isSold()){
            soldLabel.setVisible(true); //enable SOLD! label
            bidButton.setVisible(false);    //hide bid functionality
            bidField.setVisible(false);
            lotSoldDateLabel.setVisible(true);  //show sell date, time and winning bidder
            lotSoldDateLabel.setText("Lot sold on " + DRIVER.lotList.getElementByInt(LotIndex).getContents().getFinalSaleDate().format(DateTimeFormatter.ofPattern("dd/MM/yy")) +
                    " at " + DRIVER.lotList.getElementByInt(LotIndex).getContents().getFinalSaleTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")) +
                    " to " + DRIVER.lotList.getElementByInt(LotIndex).getContents().getListOfBids().head.getContents().getBidder().getName());    //head of bid list will be most recent (and winning) bid
            currentBidLabel.setText("Sold for: " + String.format("%.2f",DRIVER.lotList.getElementByInt(LotIndex).getContents().getFinalSalePrice())); //update current bid to show winning bid
            sellLotButton.setVisible(false);    //hide sell and withdraw functionality
            withdrawLotButton.setVisible(false);
        }

    }
}
