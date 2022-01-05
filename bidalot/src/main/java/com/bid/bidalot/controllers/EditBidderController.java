package com.bid.bidalot.controllers;

import com.bid.bidalot.AuctionApp;
import com.bid.bidalot.objects.Bidder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;

import java.io.IOException;
import java.util.Objects;

import static com.bid.bidalot.AuctionApp.DRIVER;
import static com.bid.bidalot.controllers.BidderController.bidderDetailsScene;

public class EditBidderController {
    private JFrame frame;
    @FXML
    private Button closeButton;
    @FXML
    private TextField BName, BAddress, BPhone;
    @FXML
    private PasswordField BNewPass, BNewPassConfirm;

    @FXML
    protected void initialize() {
        //autofilling Bidder deatils into fields
        BName.setText(BidderController.selectedBidder.getName());
        BAddress.setText(BidderController.selectedBidder.getAddress());
        BPhone.setText(BidderController.selectedBidder.getPhone());
    }

    //password change unlocked after entering password
    @FXML
    protected void unlockPasswordButton(ActionEvent actionEvent){
        String password = JOptionPane.showInputDialog(frame, "Enter password to unlock change password settings:");
        if (password.matches(BidderController.selectedBidder.getPassword())){
            BNewPass.setDisable(false);
            BNewPassConfirm.setDisable(false);
        }else{
            JOptionPane.showMessageDialog(frame, "Incorrect password entered.", "Password Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateBidder(Bidder bidder, String name, String address, String phone, String password){
        if(!name.equals("") && !name.equals(bidder.getName()))  //i.e. if name is to be changed
            if (name.length() <= 40)
                bidder.setName(name);
            else
                bidder.setName(name.substring(0,40));

        if (!address.equals("") && !address.equals(bidder.getAddress()))
            if(address.length() <= 100)
                bidder.setAddress(address);
            else
                bidder.setAddress(address.substring(0,100));

        if (!phone.equals("") && !phone.equals(bidder.getPhone()))
            if (phone.length() != 12) {                                        //adding in spaces between digits if not included
                String temp = phone.replaceAll("[^\\d]", "");
                temp = temp.substring(0, 3) + " " + temp.substring(3, 6) + " " + temp.substring(6);
                bidder.setPhone(temp);
            } else
                bidder.setPhone(phone);

        if (!password.equals("") && !password.equals(bidder.getPassword()))
            bidder.setPassword(password);   //password length validated in FXML method
    }

    @FXML
    protected void updateBidderButton(ActionEvent actionEvent) throws IOException {
        String name = BName.getText().trim();   //removing leading/trailing whitespace
        String address = BAddress.getText().trim();
        String phone = BPhone.getText();
        String password = BNewPass.getText().trim();

        //error checking for phone no. and correct formatting passwords matching
        if (phone.equals("") || phone.matches("^[\\d]{3}[\\s]?[\\d]{3}[\\s]?[\\d]{4}$")){
            if (password.equals("") || password.length() >= 8) {
                if (password.equals(BNewPassConfirm.getText().trim())){
                    if(!password.matches(BidderController.selectedBidder.getPassword())) {

                        //setting up pop up to display only info that will be changed
                        String jOptionConfirmText = "The following details will be updated:\n";
                        //confirming if any changes have been made
                        boolean changeMade = false;
                        //if a title was entered and the entered title was not the same as the lot's title as is
                        if (!name.equals("") && !name.equals(BidderController.selectedBidder.getName())) {
                            if (name.length() <= 25)
                                jOptionConfirmText = jOptionConfirmText + "\nName: " + name;
                            else
                                jOptionConfirmText = jOptionConfirmText + "\nName: " + name.substring(0, 25) + "...";
                            changeMade = true;
                        }
                        if (!address.equals("") && !address.equals(BidderController.selectedBidder.getAddress())) {
                            if (address.length() <= 25)
                                jOptionConfirmText = jOptionConfirmText + "\nAddress: " + address;
                            else
                                jOptionConfirmText = jOptionConfirmText + "\nAddress: " + address.substring(0, 25) + "...";
                            changeMade = true;
                        }

                        if (!phone.equals("") && !phone.equals(BidderController.selectedBidder.getPhone())) {
                            jOptionConfirmText = jOptionConfirmText + "\nPhone Number: " + phone;
                            changeMade = true;
                        }
                        if (!password.equals("") && !password.equals(BidderController.selectedBidder.getPassword())) {
                            jOptionConfirmText = jOptionConfirmText + "\nPassword changed.";
                            changeMade = true;
                        }

                        if(changeMade){
                            int option = JOptionPane.showConfirmDialog(frame, jOptionConfirmText + "\n\nDo you want to proceed with these changes?", "Edit Confirmation", JOptionPane.YES_NO_OPTION);
                            if (option == JOptionPane.YES_OPTION) {
                                JOptionPane.showMessageDialog(frame, "Bidder details have been updated", "Edit Success!", JOptionPane.INFORMATION_MESSAGE);
                                updateBidder(BidderController.selectedBidder,name,address,phone,password);
                                closeWindow();

                                //resetting temp values back to null
                                name = address = phone = password = null;

                                Parent detailsView = FXMLLoader.load(Objects.requireNonNull(AuctionApp.class.getResource("bidder-details-view.fxml")));
                                Stage stage = (Stage) bidderDetailsScene.getWindow();
                                bidderDetailsScene = new Scene(detailsView);
                                stage.setScene(bidderDetailsScene);
                                stage.setTitle("Bid-A-Lot: " + BidderController.selectedBidder.getName());
                            }

                        }else{
                            JOptionPane.showMessageDialog(frame, "No changes to the bidder details have been made.", "Edit Error!", JOptionPane.ERROR_MESSAGE);
                        }
                    }else{
                        JOptionPane.showMessageDialog(frame, "New password is the same as the current password.", "Edit Error!", JOptionPane.ERROR_MESSAGE);
                    }
                }else{
                    JOptionPane.showMessageDialog(frame, "Passwords do not match.", "Edit Error!", JOptionPane.ERROR_MESSAGE);
                }
            }else{
                JOptionPane.showMessageDialog(frame, "Password must be at least 8 characters.", "Edit Error!", JOptionPane.ERROR_MESSAGE);
            }
        }else{
            JOptionPane.showMessageDialog(frame, "Please enter a valid phone number.\n\nFormat: 10 numbers with optional spaces\nE.g. 123 456 7890\n        8647125647", "Edit Error!", JOptionPane.ERROR_MESSAGE);
        }

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
