package com.bid.bidalot.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AddController {
    @FXML
    public Button closeButton;

    @FXML
    protected void closeWindow(ActionEvent actionEvent){    //https://stackoverflow.com/questions/25037724/how-to-close-a-java-window-with-a-button-click-javafx-project/41838183
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
