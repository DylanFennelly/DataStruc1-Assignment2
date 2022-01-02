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
import java.util.Locale;
import java.util.Objects;

import static com.bid.bidalot.AuctionApp.DRIVER;
import static com.bid.bidalot.controllers.StartController.bidScene;

public class RegisterLoginController {
    private JFrame frame;
    @FXML
    private TextField BName, BAddress, BPhone, BEmail;
    @FXML
    private PasswordField BPass, BPassConfirm;
    @FXML
    private Button closeButton;

    protected void closeWindow(){    //https://stackoverflow.com/questions/25037724/how-to-close-a-java-window-with-a-button-click-javafx-project/41838183
        Stage stage = (Stage) closeButton.getScene().getWindow();   //gets scene from button node, then window from scene and closes the window
        stage.close();
    }

    @FXML
    protected void closeWindowButton(ActionEvent actionEvent){
        closeWindow();
    }

    protected Bidder addBidder(String name, String address, String phone, String email, String password, String passwordConfirm){
        if (!name.equals("")){
            if(!address.equals("")){
                if(phone.matches("^[\\d]{3}[\\s]?[\\d]{3}[\\s]?[\\d]{4}$")){
                    if(email.matches("^[\\w-.]+@([\\w-]+.)+[\\w-]{2,4}$")){
                        //preventing duplicate email addresses
                        boolean matchingEmail = false;
                        for (Bidder temp : DRIVER.bidderList){  //todo: replace linear search
                            if (temp.getEmail().equalsIgnoreCase(email)) {
                                matchingEmail = true;
                                break;
                            }
                        }
                        if (!matchingEmail) {
                            if (!password.equals("")) {
                                if (password.equals(passwordConfirm)) {
                                    return new Bidder(name, address, phone, email, password);
                                } else {
                                    JOptionPane.showMessageDialog(frame, "Passwords do not match.", "Register Error!", JOptionPane.ERROR_MESSAGE);
                                }
                            } else {
                                JOptionPane.showMessageDialog(frame, "Please enter a password.", "Register Error!", JOptionPane.ERROR_MESSAGE);
                            }
                        }else {
                            JOptionPane.showMessageDialog(frame, "This email address already has a bidder associated with it.", "Register Error!", JOptionPane.ERROR_MESSAGE);
                        }
                    }else {
                        JOptionPane.showMessageDialog(frame, "Please enter a valid email address.\n\nFormat: Any amount of alphanumeric characters, followed by an @,\nany amount of alphanumeric characters, a dot (.), and a 2-4 character code\nE.g. someone@email.com\n        person.being@place.co.uk", "Register Error!", JOptionPane.ERROR_MESSAGE);
                    }
                }else {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid phone number.\n\nFormat: 10 numbers with optional spaces\nE.g. 123 456 7890\n        8647125647", "Register Error!", JOptionPane.ERROR_MESSAGE);
                }
            }else {
                JOptionPane.showMessageDialog(frame, "Please enter an address.", "Register Error!", JOptionPane.ERROR_MESSAGE);
            }
        }else {
            JOptionPane.showMessageDialog(frame, "Please enter a name.", "Register Error!", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    @FXML
    private void addBidderButton(ActionEvent actionEvent) throws IOException {
        Bidder newBidder = addBidder(BName.getText(), BAddress.getText(), BPhone.getText(), BEmail.getText(), BPass.getText(), BPassConfirm.getText());
        if (newBidder != null){
            DRIVER.bidderList.addElementToEnd(newBidder);
            JOptionPane.showMessageDialog(frame, "Bidder successfully registered!", "Register Success!", JOptionPane.INFORMATION_MESSAGE);
            closeWindow();

            //todo: find better way to update table view
            //reloads the bidScene to update TableView
            Parent bidView = FXMLLoader.load(Objects.requireNonNull(AuctionApp.class.getResource("bidder-view.fxml")));
            Stage stage = (Stage) bidScene.getWindow();
            bidScene = new Scene(bidView);
            stage.setScene(bidScene);
            stage.setTitle("Bid-A-Lot: Bidders");
            stage.show();
        }
    }
}
