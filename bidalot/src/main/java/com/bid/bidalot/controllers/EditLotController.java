package com.bid.bidalot.controllers;

import com.bid.bidalot.AuctionApp;
import com.bid.bidalot.objects.Lot;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.swing.*;

import java.io.IOException;
import java.util.Objects;

import static com.bid.bidalot.AuctionApp.DRIVER;
import static com.bid.bidalot.controllers.LotController.lotDetailsScene;

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
        lotTitle.setText(LotController.selectedLot.getTitle());
        lotDesc.setText(LotController.selectedLot.getDescription());
        lotType.setValue(LotController.selectedLot.getType());
        lotImage.setText(LotController.selectedLot.getImageLink());
        //lot origin is more complicated to setup
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
        if (!title.equals("") && !title.equals(lot.getTitle())) //i.e. if title is to be changed
            if (title.length() <= 50)
                lot.setTitle(title);
            else
                lot.setTitle(title.substring(0,50));

        if(!description.equals("") && !description.equals(lot.getDescription()))
            if (description.length() <= 500)
                lot.setDescription(description);
            else
                lot.setDescription(description.substring(0,500));

        if(!lot.getType().equals(type))
            lot.setType(type);

        if (imageLink.equals(""))
            lot.setImageLink("N/A");
        else if (!imageLink.equals(lot.getImageLink()))
            lot.setImageLink(imageLink);

        if (originDate != lot.getOriginDate())
            lot.setOriginDate(originDate);
    }

    @FXML
    protected void updateLotButton(ActionEvent actionEvent) throws IOException {
        String title = lotTitle.getText().trim();   //removing leading/trailing whitespace
        String description = lotDesc.getText().trim();
        String type = lotType.getValue();
        String imageLink = lotImage.getText().trim();
        int originDate = lotOrigin.getValue();

        //setting up pop up to display only info that will be changed
        String jOptionConfirmText = "The following details will be updated:\n";
        //confirming if any changes have been made
        boolean changeMade = false;
        //if a title was entered and the entered title was not the same as the lot's title as is
        if (!title.equals("") && !title.equals(LotController.selectedLot.getTitle())) {
            if (title.length() <= 25)
                jOptionConfirmText = jOptionConfirmText + "\nTitle: " + title;
            else
                jOptionConfirmText = jOptionConfirmText + "\nTitle: " + title.substring(0,25) + "...";
            changeMade = true;
        }
        if (!description.equals("") && !description.equals(LotController.selectedLot.getDescription())){
            if (description.length() <= 25)
                jOptionConfirmText = jOptionConfirmText + "\nDescription: " + description;
            else
                jOptionConfirmText = jOptionConfirmText + "\nDescription: " + description.substring(0,25) + "...";
            changeMade = true;
        }
        if(!type.equals(LotController.selectedLot.getType())){
            jOptionConfirmText = jOptionConfirmText + "\nLot Type: " + type;
            changeMade = true;
        }
        if (!imageLink.equals("") && !imageLink.equals(LotController.selectedLot.getImageLink())){
            if (imageLink.length() <= 25)
                jOptionConfirmText = jOptionConfirmText + "\nImage Link: " + imageLink;
            else
                jOptionConfirmText = jOptionConfirmText + "\nImage Link: " + imageLink.substring(0,25) + "...";
            changeMade = true;
        }
        if (originDate!=LotController.selectedLot.getOriginDate()){
            jOptionConfirmText = jOptionConfirmText + "\nOrigin Date: " + originDate;
            changeMade = true;
        }

        if (changeMade){
            int option = JOptionPane.showConfirmDialog(frame, jOptionConfirmText + "\n\nDo you want to proceed with these changes?", "Edit Confirmation", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION){
                JOptionPane.showMessageDialog(frame, "Lot details have been updated", "Edit Success!", JOptionPane.INFORMATION_MESSAGE);
                updateLot(LotController.selectedLot,title,description,type,imageLink,originDate);
                closeWindow();

                //resetting temp values back to null
                title = description = type = imageLink = null;

                //refreshing lot scene to reflect updates
                Parent detailsView = FXMLLoader.load(Objects.requireNonNull(AuctionApp.class.getResource("lot-details-view.fxml")));
                Stage stage = (Stage) lotDetailsScene.getWindow();
                lotDetailsScene = new Scene(detailsView);
                stage.setScene(lotDetailsScene);
                stage.setTitle("Bid-A-Lot: " + LotController.selectedLot.getTitle());
            }
        }else{
            JOptionPane.showMessageDialog(frame, "No changes to the lot details have been made.", "Edit Error!", JOptionPane.ERROR_MESSAGE);
        }
    }
}
