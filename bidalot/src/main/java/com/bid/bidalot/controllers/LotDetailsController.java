package com.bid.bidalot.controllers;

import com.bid.bidalot.AuctionApp;
import com.bid.bidalot.objects.Bid;
import com.bid.bidalot.objects.Bidder;
import com.bid.bidalot.objects.Lot;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static com.bid.bidalot.AuctionApp.DRIVER;
import static com.bid.bidalot.controllers.LotController.LotIndex;
import static com.bid.bidalot.controllers.StartController.lotScene;

public class LotDetailsController {
    private JFrame frame;
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
    private Button bidButton, sellLotButton, withdrawLotButton, withdrawBidButton, editLotButton;

    @FXML
    protected void initialize(){
        if(AuctionApp.loggedInBidder != null){
            bidButton.setDisable(false);    //only able to place bid if logged in
            withdrawBidButton.setDisable(false);
            loginLabel.setText("Logged in as: " + AuctionApp.loggedInBidder.getName());
            if (AuctionApp.loggedInBidder == DRIVER.lotList.getElementByInt(LotIndex).getContents().getLotOwner()) {    //if logged in as creator of lot,
                sellLotButton.setVisible(true); //enable sell, edit, and withdraw functionality
                withdrawLotButton.setVisible(true);
                editLotButton.setVisible(true);
                bidButton.setVisible(false);    //cant place bid if owner of lot
                withdrawBidButton.setVisible(false);
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
            withdrawBidButton.setVisible(false);
            bidField.setVisible(false);
            lotSoldDateLabel.setVisible(true);  //show sell date, time and winning bidder
            lotSoldDateLabel.setText("Lot sold on " + DRIVER.lotList.getElementByInt(LotIndex).getContents().getFinalSaleDate().format(DateTimeFormatter.ofPattern("dd/MM/yy")) +
                    " at " + DRIVER.lotList.getElementByInt(LotIndex).getContents().getFinalSaleTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")) +
                    " to " + DRIVER.lotList.getElementByInt(LotIndex).getContents().getListOfBids().head.getContents().getBidder().getName());    //head of bid list will be most recent (and winning) bid
            currentBidLabel.setText("Sold for: " + String.format("%.2f",DRIVER.lotList.getElementByInt(LotIndex).getContents().getFinalSalePrice())); //update current bid to show winning bid
            sellLotButton.setVisible(false);    //hide sell, edit, and withdraw functionality
            withdrawLotButton.setVisible(false);
            editLotButton.setVisible(false);
        }
    }

    @FXML
    protected void addBidButton(ActionEvent actionEvent){
        if (bidField.getText().matches("[\\d]+[.]+[\\d]{2}")){
            double bid = Double.parseDouble(bidField.getText());
                //if bid is greater than current highest bid (asking price)                   OR    if bid is equal to the start price and no bids have been placed yet (allowing a bid to be placed at the starting bid price if no bids have been placed yet)
            if (bid > DRIVER.lotList.getElementByInt(LotIndex).getContents().getAskingPrice() || (bid == DRIVER.lotList.getElementByInt(LotIndex).getContents().getStartPrice() && DRIVER.lotList.getElementByInt(LotIndex).getContents().getListOfBids().getListLength() == 0)){
                int option = JOptionPane.showConfirmDialog(frame, "Are you sure you want to place this bid?\n\nBid amount: "+ String.format("%.2f",bid), "Bid Confirmation", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(frame, "Bid placed!", "Bid Success!", JOptionPane.INFORMATION_MESSAGE);
                    DRIVER.lotList.getElementByInt(LotIndex).getContents().getListOfBids().addElementToTop(new Bid(bid, LocalDate.now(), LocalTime.now(), AuctionApp.loggedInBidder));
                    DRIVER.lotList.getElementByInt(LotIndex).getContents().setAskingPrice(bid);

                    //clear list to ensure bids do not display in wrong order
                    bidTV.getItems().clear();   //populating bid list
                    for (Bid b : DRIVER.lotList.getElementByInt(LotIndex).getContents().getListOfBids()){
                        bidTV.getItems().add(b);
                    }
                    currentBidLabel.setText("Current Bid: " + String.format("%.2f",DRIVER.lotList.getElementByInt(LotIndex).getContents().getAskingPrice()));

                }
            }else {
                JOptionPane.showMessageDialog(frame, "Placed bid must be greater than current highest bid.\n\n Current highest bid: " + String.format("%.2f",DRIVER.lotList.getElementByInt(LotIndex).getContents().getAskingPrice()), "Bid Error!", JOptionPane.ERROR_MESSAGE);
            }
        }else{
            JOptionPane.showMessageDialog(frame, "Please enter a valid bid.\n\nFormat: Any number of digits, followed by a decimal point and two digits.\nE.g.: 10.00, 150.25, 1298.04\nPlaced bid must be higher than current top bid.", "Bid Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    protected void openImageLink(ActionEvent actionEvent) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI(DRIVER.lotList.getElementByInt(LotIndex).getContents().getImageLink()));    //https://www.youtube.com/watch?v=SlE0dCuO5yc
    }

    private void backToLotMenu(ActionEvent actionEvent) throws IOException {
        //reload lot-view to refresh lots TableView to reflect any changes made
        Parent lotView = FXMLLoader.load(Objects.requireNonNull(AuctionApp.class.getResource("lot-view.fxml")));
        lotScene = new Scene(lotView);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(lotScene);
        stage.setTitle("Bid-A-Lot: Lots");
        stage.show();
    }

    @FXML
    private void withdrawLot(ActionEvent actionEvent) throws IOException {
        int option = JOptionPane.showConfirmDialog(frame, "Are you sure you want to withdraw this lot? The lot \nlisting and all bids will be deleted.", "Withdraw Confirmation", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(frame, "Lot withdrawn.", "Withdraw Complete", JOptionPane.INFORMATION_MESSAGE);
            DRIVER.lotList.removeElement(LotIndex);
            backToLotMenu(actionEvent);
        }
    }

    @FXML
    protected void changeToLotMenu(ActionEvent actionEvent) throws IOException {
        backToLotMenu(actionEvent);
    }

    @FXML
    private void sellLot(Lot lot){
        lot.setSold(true);
        lot.setFinalSaleDate(LocalDate.now());
        lot.setFinalSaleTime(LocalTime.now());
        lot.setFinalSalePrice();
    }

    @FXML
    protected void sellLotButton(ActionEvent actionEvent){
        if (DRIVER.lotList.getElementByInt(LotIndex).getContents().getListOfBids().getListLength() > 0) {
            int option = JOptionPane.showConfirmDialog(frame, "Are you sure you want to sell this lot? The current top bid will\nbe accepted and the lot will be closed.\n\nCurrent highest bid: " + String.format("%.2f",DRIVER.lotList.getElementByInt(LotIndex).getContents().getAskingPrice()), "Sell Confirmation", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(frame, "Lot Sold!", "Sell Complete!", JOptionPane.INFORMATION_MESSAGE);

                sellLot(DRIVER.lotList.getElementByInt(LotIndex).getContents());
                initialize();
            }
        }else{
            JOptionPane.showMessageDialog(frame, "The lot cannot be sold as there are no bids placed on the lot.", "Sell Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    //todo: withdraw bids, edit lot

    @FXML
    protected void withdrawBidButton(ActionEvent actionEvent){
        //todo: withdraw bids, regardless if they are top bid or not?
        if (DRIVER.lotList.getElementByInt(LotIndex).getContents().getListOfBids().getListLength() > 0) {
            //if user that placed the current top bid is the same as the logged in user
            if(DRIVER.lotList.getElementByInt(LotIndex).getContents().getListOfBids().head.getContents().getBidder() == AuctionApp.loggedInBidder){
                int option = JOptionPane.showConfirmDialog(frame, "Are you sure you want to withdraw this bid?\n\nBid amount: " + String.format("%.2f",DRIVER.lotList.getElementByInt(LotIndex).getContents().getAskingPrice()), "Withdraw Confirmation", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(frame, "Bid withdrawn.", "Withdraw Complete", JOptionPane.INFORMATION_MESSAGE);
                    DRIVER.lotList.getElementByInt(LotIndex).getContents().getListOfBids().removeElement(0);    //0 = head

                    //if head was only bid (i.e., head is now null)
                    if (DRIVER.lotList.getElementByInt(LotIndex).getContents().getListOfBids().getListLength() == 0)
                        //set asking price back to starting price
                        DRIVER.lotList.getElementByInt(LotIndex).getContents().setAskingPrice( DRIVER.lotList.getElementByInt(LotIndex).getContents().getStartPrice() );
                    //if there is at least 1 bid remaining (i.e., head is not null)
                    else
                        //rollback lot askingPrice (current highest bid) to new head
                        DRIVER.lotList.getElementByInt(LotIndex).getContents().setAskingPrice( DRIVER.lotList.getElementByInt(LotIndex).getContents().getListOfBids().head.getContents().getBidAmount() );

                    //updating UI elements
                    bidTV.getItems().clear();
                    for (Bid b : DRIVER.lotList.getElementByInt(LotIndex).getContents().getListOfBids()){
                        bidTV.getItems().add(b);
                    }
                    currentBidLabel.setText("Current Bid: " + String.format("%.2f",DRIVER.lotList.getElementByInt(LotIndex).getContents().getAskingPrice()));
                }
            }else{
                JOptionPane.showMessageDialog(frame, "The current top bid was not placed by you.\nOnly the top bid may be withdrawn.", "Withdraw Error!", JOptionPane.ERROR_MESSAGE);
            }
        }else{
            JOptionPane.showMessageDialog(frame, "There are no bids to withdraw.", "Withdraw Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    protected void changeToEditMenu(ActionEvent actionEvent) throws IOException {    //pops up in new window
        Parent editView = FXMLLoader.load(Objects.requireNonNull(AuctionApp.class.getResource("edit-lot-view.fxml")));
        Scene editScene = new Scene(editView);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);     //locks main window until popup window is closed  |  https://stackoverflow.com/questions/15625987/block-owner-window-java-fx
        stage.initOwner(editView.getScene().getWindow());
        stage.setScene(editScene);
        stage.setTitle("Edit Lot");
        stage.show();
    }
}
