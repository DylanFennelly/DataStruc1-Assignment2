package com.bid.bidalot.controllers;

import com.bid.bidalot.AuctionApp;
import com.bid.bidalot.objects.Lot;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.util.Objects;

import static com.bid.bidalot.AuctionApp.DRIVER;
import static com.bid.bidalot.controllers.StartController.lotScene;

public class AddLotController {
    private JFrame frame;
    @FXML
    private Button closeButton;

    @FXML
    private TextField lotTitle, lotImage, lotStarting;

    @FXML
    private Spinner<Integer> lotOrigin;

    @FXML
    private TextArea lotDesc;

    @FXML
    private ChoiceBox<String> lotType;

    @FXML
    protected void closeWindow(){    //https://stackoverflow.com/questions/25037724/how-to-close-a-java-window-with-a-button-click-javafx-project/41838183
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void closeWindowButton(){
        closeWindow();
    }

    @FXML
    protected void addLotButton() throws IOException {
        if (!lotTitle.getText().equals("")) {
            if (!lotDesc.getText().equals("")) {
                if (lotType.getValue() != null) {
                    if (lotOrigin.getValue() != null) {
                        if (lotStarting.getText().matches("[\\d]+[.]+[\\d]{2}")) {    //any number of digits, followed by a decimal point and two digits
                            double askingPrice = Double.parseDouble(lotStarting.getText());
                            DRIVER.lotHashTable.add(new Lot(lotTitle.getText(), lotDesc.getText(), lotType.getValue(), lotImage.getText(), lotOrigin.getValue(), askingPrice, AuctionApp.loggedInBidder));
                            JOptionPane.showMessageDialog(frame, "Lot successfully added!", "Add Success!", JOptionPane.INFORMATION_MESSAGE);
                            closeWindow();

                            Parent lotView = FXMLLoader.load(Objects.requireNonNull(AuctionApp.class.getResource("lot-view.fxml")));
                            Stage stage = (Stage) lotScene.getWindow();
                            lotScene = new Scene(lotView);
                            stage.setScene(lotScene);
                            stage.setTitle("Bid-A-Lot: Lots");
                            stage.show();

                        }else {
                            JOptionPane.showMessageDialog(frame, "Please select a valid price.\n\nFormat: Any number of digits, followed by a decimal point and two digits.\nE.g.: 10.00, 150.25, 1298.04", "Add Error!", JOptionPane.ERROR_MESSAGE);
                        }
                    }else {
                        JOptionPane.showMessageDialog(frame, "Please select an estimated year of origin.", "Add Error!", JOptionPane.ERROR_MESSAGE);
                    }
                }else {
                    JOptionPane.showMessageDialog(frame, "Please select a lot type.", "Add Error!", JOptionPane.ERROR_MESSAGE);
                }
            }else {
                JOptionPane.showMessageDialog(frame, "Please enter a description.", "Add Error!", JOptionPane.ERROR_MESSAGE);
            }
        }else {
            JOptionPane.showMessageDialog(frame, "Please enter a title.", "Add Error!", JOptionPane.ERROR_MESSAGE);
        }
    }
}
