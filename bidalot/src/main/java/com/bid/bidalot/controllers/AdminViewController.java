package com.bid.bidalot.controllers;

import com.bid.bidalot.AuctionApp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javax.swing.*;

import static com.bid.bidalot.AuctionApp.DRIVER;

public class AdminViewController {
    private JFrame frame;

    @FXML
    private Button closeButton;

    @FXML
    protected void closeWindow() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    //temporary save and load buttons for testing
    @FXML
    protected void saveButton() throws Exception {
        AuctionApp.save();
        JOptionPane.showMessageDialog(frame, "Save complete!", "Save Status", JOptionPane.INFORMATION_MESSAGE);
    }

    @FXML
    protected void loadButton() throws Exception {
        AuctionApp.load();
        JOptionPane.showMessageDialog(frame, "Load complete!", "Load Status", JOptionPane.INFORMATION_MESSAGE);
    }

    @FXML
    protected void clearAll() {
        int option = JOptionPane.showConfirmDialog(frame, "Are you sure you want to clear all data in the system?", "Data Deletion Confirmation", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            DRIVER.bidderHashTable.clearAllElements();
            DRIVER.lotHashTable.clearAllElements();
        }
    }
}
