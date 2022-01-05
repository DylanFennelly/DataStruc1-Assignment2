package com.bid.bidalot.controllers;

import com.bid.bidalot.objects.Lot;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.swing.*;

import static com.bid.bidalot.AuctionApp.DRIVER;
import static com.bid.bidalot.controllers.LotController.LotIndex;

public class EditLotController {
    private JFrame frame;
    @FXML
    private Button closeButton;

    @FXML
    private TextField lotTitle, lotImage;

    @FXML
    private Spinner<Integer> lotOrigin;

    @FXML
    private TextArea lotDesc;

    @FXML
    private ChoiceBox<String> lotType;

    @FXML
    protected void initialize() {
        //autofilling current Lot details into fields
        lotTitle.setText(DRIVER.lotList.getElementByInt(LotIndex).getContents().getTitle());
        lotDesc.setText(DRIVER.lotList.getElementByInt(LotIndex).getContents().getDescription());
        lotType.setValue(DRIVER.lotList.getElementByInt(LotIndex).getContents().getType());
        lotImage.setText(DRIVER.lotList.getElementByInt(LotIndex).getContents().getImageLink());
        //todo: lotOrigin
    }

    @FXML
    protected void closeWindow(){    //https://stackoverflow.com/questions/25037724/how-to-close-a-java-window-with-a-button-click-javafx-project/41838183
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void closeWindowButton(ActionEvent actionEvent){
        closeWindow();
    }

    private void updateLot(Lot lot, String title, String description, String type, String imageLink, int originDate){
        if (!title.equals("")) //i.e. if title is to be changed
            lot.setTitle(title);
        if(!description.equals(""))
            lot.setDescription(description);

    }
}
