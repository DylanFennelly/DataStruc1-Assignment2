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
import java.util.Locale;
import java.util.Objects;

import static com.bid.bidalot.AuctionApp.DRIVER;
import static com.bid.bidalot.controllers.LotController.lotDetailsScene;
import static com.bid.bidalot.controllers.LotController.selectedLot;
import static com.bid.bidalot.controllers.StartController.lotScene;

public class SearchLotController {
    private JFrame frame;
    boolean yearSpinnerActive = false;
    @FXML
    private Label lotsLabel, loginLabel;
    @FXML
    private TextField titleField, descField;
    @FXML
    private ChoiceBox<String> typeChoiceBox;
    @FXML
    private Spinner<Integer> yearSpinner;
    @FXML
    private ToggleButton yearToggleButton;
    @FXML
    private ToggleGroup soldActive;
    @FXML
    private RadioButton activeLotsRadio, soldLotsRadio, activeSoldRadio;
    @FXML
    private TableView<Lot> activeLotsTV, soldLotsTV;

    @FXML
    protected void initialize() {
        if(AuctionApp.loggedInBidder != null){
            loginLabel.setText("Logged in as: " + AuctionApp.loggedInBidder.getName());
        }
    }

    @FXML
    protected void enableDisableYearSpinner(ActionEvent actionEvent){
        if (yearToggleButton.isSelected()){
            yearToggleButton.setSelected(true);
            yearSpinner.setDisable(false);
            yearSpinnerActive = true;
        }else{
            yearToggleButton.setSelected(false);
            yearSpinner.setDisable(true);
            yearSpinnerActive = false;
        }
    }

    @FXML
    private void searchButton(ActionEvent actionEvent){
        //clearing both TVs between searches
        activeLotsTV.getItems().clear();
        soldLotsTV.getItems().clear();
        //setting visibility and state of nodes depending on type of search being performed
        if (soldActive.getSelectedToggle() == activeLotsRadio) {  //active lots only
            activeLotsTV.setVisible(true);
            soldLotsTV.setVisible(false);
            lotsLabel.setText("Active Lots");
        }else if (soldActive.getSelectedToggle() == soldLotsRadio){ //sold lots only
            activeLotsTV.setVisible(false);
            soldLotsTV.setVisible(true);
            lotsLabel.setText("Sold Lots");
        }else{  //both - use activeLots TV for both
            activeLotsTV.setVisible(true);
            soldLotsTV.setVisible(false);
            lotsLabel.setText("Active & Sold Lots");
        }


        //setting up boolean conditions for easier tracking
        boolean titleSearch = false, typeSearch = false, descSearch = false;
        if (!titleField.getText().equals(""))   //if there is a value in the title field
            titleSearch = true;
        if (!typeChoiceBox.getValue().equals("Any"))
            typeSearch = true;
        if (!descField.getText().equals(""))
            descSearch = true;

        if (titleSearch && !typeSearch && !yearSpinnerActive && !descSearch){ //title search only
            if (soldActive.getSelectedToggle() == activeLotsRadio) {  //active lots only
                for (int i=0; i<DRIVER.lotHashTable.hashTableLength(); i++){
                    for (Lot temp : DRIVER.lotHashTable.getLinkedList(i)) {
                        if(!temp.isSold() && temp.getTitle().toLowerCase().contains(titleField.getText().toLowerCase().trim()))
                            activeLotsTV.getItems().add(temp);
                    }
                }
            }else if (soldActive.getSelectedToggle() == soldLotsRadio){ //sold lots only
                for (int i=0; i<DRIVER.lotHashTable.hashTableLength(); i++){
                    for (Lot temp : DRIVER.lotHashTable.getLinkedList(i)) {
                        if(temp.isSold() && temp.getTitle().toLowerCase().contains(titleField.getText().toLowerCase().trim()))
                            soldLotsTV.getItems().add(temp);
                    }
                }
            }else{  //both
                for (int i=0; i<DRIVER.lotHashTable.hashTableLength(); i++){
                    for (Lot temp : DRIVER.lotHashTable.getLinkedList(i)) {
                        if(temp.getTitle().toLowerCase().contains(titleField.getText().toLowerCase().trim()))
                            activeLotsTV.getItems().add(temp);
                    }
                }
            }

        }else if(!titleSearch && typeSearch && !yearSpinnerActive && !descSearch){  //type search only
            if (soldActive.getSelectedToggle() == activeLotsRadio) {  //active lots only
                for (int i=0; i<DRIVER.lotHashTable.hashTableLength(); i++){
                    for (Lot temp : DRIVER.lotHashTable.getLinkedList(i)) {
                        if(!temp.isSold() && temp.getType().matches(typeChoiceBox.getValue()))
                            activeLotsTV.getItems().add(temp);
                    }
                }
            }else if (soldActive.getSelectedToggle() == soldLotsRadio){ //sold lots only
                for (int i=0; i<DRIVER.lotHashTable.hashTableLength(); i++){
                    for (Lot temp : DRIVER.lotHashTable.getLinkedList(i)) {
                        if(temp.isSold() && temp.getType().matches(typeChoiceBox.getValue()))
                            soldLotsTV.getItems().add(temp);
                    }
                }
            }else{  //both
                for (int i=0; i<DRIVER.lotHashTable.hashTableLength(); i++){
                    for (Lot temp : DRIVER.lotHashTable.getLinkedList(i)) {
                        if(temp.getType().matches(typeChoiceBox.getValue()))
                            activeLotsTV.getItems().add(temp);
                    }
                }
            }

        }else if(!titleSearch && !typeSearch && yearSpinnerActive && !descSearch){  //year search only
            if (soldActive.getSelectedToggle() == activeLotsRadio) {  //active lots only
                for (int i=0; i<DRIVER.lotHashTable.hashTableLength(); i++){
                    for (Lot temp : DRIVER.lotHashTable.getLinkedList(i)) {
                        if(!temp.isSold() && temp.getOriginDate() == yearSpinner.getValue())
                            activeLotsTV.getItems().add(temp);
                    }
                }
            }else if (soldActive.getSelectedToggle() == soldLotsRadio){ //sold lots only
                for (int i=0; i<DRIVER.lotHashTable.hashTableLength(); i++){
                    for (Lot temp : DRIVER.lotHashTable.getLinkedList(i)) {
                        if(temp.isSold() && temp.getOriginDate() == yearSpinner.getValue())
                            soldLotsTV.getItems().add(temp);
                    }
                }
            }else{  //both
                for (int i=0; i<DRIVER.lotHashTable.hashTableLength(); i++){
                    for (Lot temp : DRIVER.lotHashTable.getLinkedList(i)) {
                        if(temp.getOriginDate() == yearSpinner.getValue())
                            activeLotsTV.getItems().add(temp);
                    }
                }
            }

        }else if(!titleSearch && !typeSearch && !yearSpinnerActive && descSearch){  //desc search only
            if (soldActive.getSelectedToggle() == activeLotsRadio) {  //active lots only
                for (int i=0; i<DRIVER.lotHashTable.hashTableLength(); i++){
                    for (Lot temp : DRIVER.lotHashTable.getLinkedList(i)) {
                        if(!temp.isSold() && temp.getDescription().toLowerCase().contains(descField.getText().toLowerCase().trim()))
                            activeLotsTV.getItems().add(temp);
                    }
                }
            }else if (soldActive.getSelectedToggle() == soldLotsRadio){ //sold lots only
                for (int i=0; i<DRIVER.lotHashTable.hashTableLength(); i++){
                    for (Lot temp : DRIVER.lotHashTable.getLinkedList(i)) {
                        if(temp.isSold() && temp.getDescription().toLowerCase().contains(descField.getText().toLowerCase().trim()))
                            soldLotsTV.getItems().add(temp);
                    }
                }
            }else{  //both
                for (int i=0; i<DRIVER.lotHashTable.hashTableLength(); i++){
                    for (Lot temp : DRIVER.lotHashTable.getLinkedList(i)) {
                        if(temp.getDescription().toLowerCase().contains(descField.getText().toLowerCase().trim()))
                            activeLotsTV.getItems().add(temp);
                    }
                }
            }

        }else if(titleSearch && typeSearch && !yearSpinnerActive && !descSearch) {  //title and type
            if (soldActive.getSelectedToggle() == activeLotsRadio) {  //active lots only
                for (int i=0; i<DRIVER.lotHashTable.hashTableLength(); i++){
                    for (Lot temp : DRIVER.lotHashTable.getLinkedList(i)) {
                        if(!temp.isSold() && temp.getTitle().toLowerCase().contains(titleField.getText().toLowerCase().trim()) && temp.getType().matches(typeChoiceBox.getValue()))
                            activeLotsTV.getItems().add(temp);
                    }
                }
            }else if (soldActive.getSelectedToggle() == soldLotsRadio){ //sold lots only
                for (int i=0; i<DRIVER.lotHashTable.hashTableLength(); i++){
                    for (Lot temp : DRIVER.lotHashTable.getLinkedList(i)) {
                        if(temp.isSold() && temp.getTitle().toLowerCase().contains(titleField.getText().toLowerCase().trim()) && temp.getType().matches(typeChoiceBox.getValue()))
                            soldLotsTV.getItems().add(temp);
                    }
                }
            }else{  //both
                for (int i=0; i<DRIVER.lotHashTable.hashTableLength(); i++){
                    for (Lot temp : DRIVER.lotHashTable.getLinkedList(i)) {
                        if(temp.getTitle().toLowerCase().contains(titleField.getText().toLowerCase().trim()) && temp.getType().matches(typeChoiceBox.getValue()))
                            activeLotsTV.getItems().add(temp);
                    }
                }
            }

        }else if(titleSearch && !typeSearch && yearSpinnerActive && !descSearch){  //title and year
            if (soldActive.getSelectedToggle() == activeLotsRadio) {  //active lots only
                for (int i=0; i<DRIVER.lotHashTable.hashTableLength(); i++){
                    for (Lot temp : DRIVER.lotHashTable.getLinkedList(i)) {
                        if(!temp.isSold() && temp.getTitle().toLowerCase().contains(titleField.getText().toLowerCase().trim()) && temp.getOriginDate() == yearSpinner.getValue())
                            activeLotsTV.getItems().add(temp);
                    }
                }
            }else if (soldActive.getSelectedToggle() == soldLotsRadio){ //sold lots only
                for (int i=0; i<DRIVER.lotHashTable.hashTableLength(); i++){
                    for (Lot temp : DRIVER.lotHashTable.getLinkedList(i)) {
                        if(temp.isSold() && temp.getTitle().toLowerCase().contains(titleField.getText().toLowerCase().trim()) && temp.getOriginDate() == yearSpinner.getValue())
                            soldLotsTV.getItems().add(temp);
                    }
                }
            }else{  //both
                for (int i=0; i<DRIVER.lotHashTable.hashTableLength(); i++){
                    for (Lot temp : DRIVER.lotHashTable.getLinkedList(i)) {
                        if(temp.getTitle().toLowerCase().contains(titleField.getText().toLowerCase().trim()) && temp.getOriginDate() == yearSpinner.getValue())
                            activeLotsTV.getItems().add(temp);
                    }
                }
            }

        }else if(titleSearch && !typeSearch && !yearSpinnerActive && descSearch){  //title and desc
            if (soldActive.getSelectedToggle() == activeLotsRadio) {  //active lots only
                for (int i=0; i<DRIVER.lotHashTable.hashTableLength(); i++){
                    for (Lot temp : DRIVER.lotHashTable.getLinkedList(i)) {
                        if(!temp.isSold() && temp.getTitle().toLowerCase().contains(titleField.getText().toLowerCase().trim()) && temp.getDescription().toLowerCase().contains(descField.getText().toLowerCase().trim()))
                            activeLotsTV.getItems().add(temp);
                    }
                }
            }else if (soldActive.getSelectedToggle() == soldLotsRadio){ //sold lots only
                for (int i=0; i<DRIVER.lotHashTable.hashTableLength(); i++){
                    for (Lot temp : DRIVER.lotHashTable.getLinkedList(i)) {
                        if(temp.isSold() && temp.getTitle().toLowerCase().contains(titleField.getText().toLowerCase().trim()) && temp.getDescription().toLowerCase().contains(descField.getText().toLowerCase().trim()))
                            soldLotsTV.getItems().add(temp);
                    }
                }
            }else{  //both
                for (int i=0; i<DRIVER.lotHashTable.hashTableLength(); i++){
                    for (Lot temp : DRIVER.lotHashTable.getLinkedList(i)) {
                        if(temp.getTitle().toLowerCase().contains(titleField.getText().toLowerCase().trim()) && temp.getDescription().toLowerCase().contains(descField.getText().toLowerCase().trim()))
                            activeLotsTV.getItems().add(temp);
                    }
                }
            }

        }else if(!titleSearch && typeSearch && yearSpinnerActive && !descSearch){  //type and year
            if (soldActive.getSelectedToggle() == activeLotsRadio) {  //active lots only
                for (int i=0; i<DRIVER.lotHashTable.hashTableLength(); i++){
                    for (Lot temp : DRIVER.lotHashTable.getLinkedList(i)) {
                        if(!temp.isSold() && temp.getType().matches(typeChoiceBox.getValue()) && temp.getOriginDate() == yearSpinner.getValue())
                            activeLotsTV.getItems().add(temp);
                    }
                }
            }else if (soldActive.getSelectedToggle() == soldLotsRadio){ //sold lots only
                for (int i=0; i<DRIVER.lotHashTable.hashTableLength(); i++){
                    for (Lot temp : DRIVER.lotHashTable.getLinkedList(i)) {
                        if(temp.isSold() && temp.getType().matches(typeChoiceBox.getValue()) && temp.getOriginDate() == yearSpinner.getValue())
                            soldLotsTV.getItems().add(temp);
                    }
                }
            }else{  //both
                for (int i=0; i<DRIVER.lotHashTable.hashTableLength(); i++){
                    for (Lot temp : DRIVER.lotHashTable.getLinkedList(i)) {
                        if(temp.getType().matches(typeChoiceBox.getValue()) && temp.getOriginDate() == yearSpinner.getValue())
                            activeLotsTV.getItems().add(temp);
                    }
                }
            }

        }else if(!titleSearch && typeSearch && !yearSpinnerActive && descSearch) {  //type and desc
            if (soldActive.getSelectedToggle() == activeLotsRadio) {  //active lots only
                for (int i=0; i<DRIVER.lotHashTable.hashTableLength(); i++){
                    for (Lot temp : DRIVER.lotHashTable.getLinkedList(i)) {
                        if(!temp.isSold() && temp.getType().matches(typeChoiceBox.getValue()) && temp.getDescription().toLowerCase().contains(descField.getText().toLowerCase().trim()))
                            activeLotsTV.getItems().add(temp);
                    }
                }
            }else if (soldActive.getSelectedToggle() == soldLotsRadio){ //sold lots only
                for (int i=0; i<DRIVER.lotHashTable.hashTableLength(); i++){
                    for (Lot temp : DRIVER.lotHashTable.getLinkedList(i)) {
                        if(temp.isSold() && temp.getType().matches(typeChoiceBox.getValue()) && temp.getDescription().toLowerCase().contains(descField.getText().toLowerCase().trim()))
                            soldLotsTV.getItems().add(temp);
                    }
                }
            }else{  //both
                for (int i=0; i<DRIVER.lotHashTable.hashTableLength(); i++){
                    for (Lot temp : DRIVER.lotHashTable.getLinkedList(i)) {
                        if(temp.getType().matches(typeChoiceBox.getValue()) && temp.getDescription().toLowerCase().contains(descField.getText().toLowerCase().trim()))
                            activeLotsTV.getItems().add(temp);
                    }
                }
            }

        }else if(!titleSearch && !typeSearch && yearSpinnerActive && descSearch){  //year and desc
            if (soldActive.getSelectedToggle() == activeLotsRadio) {  //active lots only
                for (int i=0; i<DRIVER.lotHashTable.hashTableLength(); i++){
                    for (Lot temp : DRIVER.lotHashTable.getLinkedList(i)) {
                        if(!temp.isSold() && temp.getOriginDate() == yearSpinner.getValue() && temp.getDescription().toLowerCase().contains(descField.getText().toLowerCase().trim()))
                            activeLotsTV.getItems().add(temp);
                    }
                }
            }else if (soldActive.getSelectedToggle() == soldLotsRadio){ //sold lots only
                for (int i=0; i<DRIVER.lotHashTable.hashTableLength(); i++){
                    for (Lot temp : DRIVER.lotHashTable.getLinkedList(i)) {
                        if(temp.isSold() && temp.getOriginDate() == yearSpinner.getValue() && temp.getDescription().toLowerCase().contains(descField.getText().toLowerCase().trim()))
                            soldLotsTV.getItems().add(temp);
                    }
                }
            }else{  //both
                for (int i=0; i<DRIVER.lotHashTable.hashTableLength(); i++){
                    for (Lot temp : DRIVER.lotHashTable.getLinkedList(i)) {
                        if(temp.getOriginDate() == yearSpinner.getValue() && temp.getDescription().toLowerCase().contains(descField.getText().toLowerCase().trim()))
                            activeLotsTV.getItems().add(temp);
                    }
                }
            }

        }else if(titleSearch && typeSearch && yearSpinnerActive && !descSearch) {  //title, type and year
            if (soldActive.getSelectedToggle() == activeLotsRadio) {  //active lots only
                for (int i=0; i<DRIVER.lotHashTable.hashTableLength(); i++){
                    for (Lot temp : DRIVER.lotHashTable.getLinkedList(i)) {
                        if(!temp.isSold() && temp.getTitle().toLowerCase().contains(titleField.getText().toLowerCase().trim()) && temp.getType().matches(typeChoiceBox.getValue()) && temp.getOriginDate() == yearSpinner.getValue())
                            activeLotsTV.getItems().add(temp);
                    }
                }
            }else if (soldActive.getSelectedToggle() == soldLotsRadio){ //sold lots only
                for (int i=0; i<DRIVER.lotHashTable.hashTableLength(); i++){
                    for (Lot temp : DRIVER.lotHashTable.getLinkedList(i)) {
                        if(temp.isSold() && temp.getTitle().toLowerCase().contains(titleField.getText().toLowerCase().trim()) && temp.getType().matches(typeChoiceBox.getValue()) && temp.getOriginDate() == yearSpinner.getValue())
                            soldLotsTV.getItems().add(temp);
                    }
                }
            }else{  //both
                for (int i=0; i<DRIVER.lotHashTable.hashTableLength(); i++){
                    for (Lot temp : DRIVER.lotHashTable.getLinkedList(i)) {
                        if(temp.getTitle().toLowerCase().contains(titleField.getText().toLowerCase().trim()) && temp.getType().matches(typeChoiceBox.getValue()) && temp.getOriginDate() == yearSpinner.getValue())
                            activeLotsTV.getItems().add(temp);
                    }
                }
            }

        }else if(titleSearch && typeSearch && !yearSpinnerActive && descSearch){  //title, type and desc
            if (soldActive.getSelectedToggle() == activeLotsRadio) {  //active lots only
                for (int i=0; i<DRIVER.lotHashTable.hashTableLength(); i++){
                    for (Lot temp : DRIVER.lotHashTable.getLinkedList(i)) {
                        if(!temp.isSold() && temp.getTitle().toLowerCase().contains(titleField.getText().toLowerCase().trim()) && temp.getType().matches(typeChoiceBox.getValue()) && temp.getDescription().toLowerCase().contains(descField.getText().toLowerCase().trim()))
                            activeLotsTV.getItems().add(temp);
                    }
                }
            }else if (soldActive.getSelectedToggle() == soldLotsRadio){ //sold lots only
                for (int i=0; i<DRIVER.lotHashTable.hashTableLength(); i++){
                    for (Lot temp : DRIVER.lotHashTable.getLinkedList(i)) {
                        if(temp.isSold() && temp.getTitle().toLowerCase().contains(titleField.getText().toLowerCase().trim()) && temp.getType().matches(typeChoiceBox.getValue()) && temp.getDescription().toLowerCase().contains(descField.getText().toLowerCase().trim()))
                            soldLotsTV.getItems().add(temp);
                    }
                }
            }else{  //both
                for (int i=0; i<DRIVER.lotHashTable.hashTableLength(); i++){
                    for (Lot temp : DRIVER.lotHashTable.getLinkedList(i)) {
                        if(temp.getTitle().toLowerCase().contains(titleField.getText().toLowerCase().trim()) && temp.getType().matches(typeChoiceBox.getValue()) && temp.getDescription().toLowerCase().contains(descField.getText().toLowerCase().trim()))
                            activeLotsTV.getItems().add(temp);
                    }
                }
            }

        }else if(titleSearch && !typeSearch && yearSpinnerActive && descSearch) {  //title, year and desc
            if (soldActive.getSelectedToggle() == activeLotsRadio) {  //active lots only
                for (int i=0; i<DRIVER.lotHashTable.hashTableLength(); i++){
                    for (Lot temp : DRIVER.lotHashTable.getLinkedList(i)) {
                        if(!temp.isSold() && temp.getTitle().toLowerCase().contains(titleField.getText().toLowerCase().trim()) && temp.getOriginDate() == yearSpinner.getValue() && temp.getDescription().toLowerCase().contains(descField.getText().toLowerCase().trim()))
                            activeLotsTV.getItems().add(temp);
                    }
                }
            }else if (soldActive.getSelectedToggle() == soldLotsRadio){ //sold lots only
                for (int i=0; i<DRIVER.lotHashTable.hashTableLength(); i++){
                    for (Lot temp : DRIVER.lotHashTable.getLinkedList(i)) {
                        if(temp.isSold() && temp.getTitle().toLowerCase().contains(titleField.getText().toLowerCase().trim()) && temp.getOriginDate() == yearSpinner.getValue() && temp.getDescription().toLowerCase().contains(descField.getText().toLowerCase().trim()))
                            soldLotsTV.getItems().add(temp);
                    }
                }
            }else{  //both
                for (int i=0; i<DRIVER.lotHashTable.hashTableLength(); i++){
                    for (Lot temp : DRIVER.lotHashTable.getLinkedList(i)) {
                        if(temp.getTitle().toLowerCase().contains(titleField.getText().toLowerCase().trim()) && temp.getOriginDate() == yearSpinner.getValue() && temp.getDescription().toLowerCase().contains(descField.getText().toLowerCase().trim()))
                            activeLotsTV.getItems().add(temp);
                    }
                }
            }

        }else if(!titleSearch && typeSearch && yearSpinnerActive && descSearch){  //type, year and desc
            if (soldActive.getSelectedToggle() == activeLotsRadio) {  //active lots only
                for (int i=0; i<DRIVER.lotHashTable.hashTableLength(); i++){
                    for (Lot temp : DRIVER.lotHashTable.getLinkedList(i)) {
                        if(!temp.isSold() && temp.getType().matches(typeChoiceBox.getValue()) && temp.getOriginDate() == yearSpinner.getValue() && temp.getDescription().toLowerCase().contains(descField.getText().toLowerCase().trim()))
                            activeLotsTV.getItems().add(temp);
                    }
                }
            }else if (soldActive.getSelectedToggle() == soldLotsRadio){ //sold lots only
                for (int i=0; i<DRIVER.lotHashTable.hashTableLength(); i++){
                    for (Lot temp : DRIVER.lotHashTable.getLinkedList(i)) {
                        if(temp.isSold() && temp.getType().matches(typeChoiceBox.getValue()) && temp.getOriginDate() == yearSpinner.getValue() && temp.getDescription().toLowerCase().contains(descField.getText().toLowerCase().trim()))
                            soldLotsTV.getItems().add(temp);
                    }
                }
            }else{  //both
                for (int i=0; i<DRIVER.lotHashTable.hashTableLength(); i++){
                    for (Lot temp : DRIVER.lotHashTable.getLinkedList(i)) {
                        if(temp.getType().matches(typeChoiceBox.getValue()) && temp.getOriginDate() == yearSpinner.getValue() && temp.getDescription().toLowerCase().contains(descField.getText().toLowerCase().trim()))
                            activeLotsTV.getItems().add(temp);
                    }
                }
            }

        }else if(titleSearch && typeSearch && yearSpinnerActive && descSearch){  //all
            if (soldActive.getSelectedToggle() == activeLotsRadio) {  //active lots only
                for (int i=0; i<DRIVER.lotHashTable.hashTableLength(); i++){
                    for (Lot temp : DRIVER.lotHashTable.getLinkedList(i)) {
                        if(!temp.isSold() && temp.getTitle().toLowerCase().contains(titleField.getText().toLowerCase().trim()) && temp.getType().matches(typeChoiceBox.getValue()) && temp.getOriginDate() == yearSpinner.getValue() && temp.getDescription().toLowerCase().contains(descField.getText().toLowerCase().trim()))
                            activeLotsTV.getItems().add(temp);
                    }
                }
            }else if (soldActive.getSelectedToggle() == soldLotsRadio){ //sold lots only
                for (int i=0; i<DRIVER.lotHashTable.hashTableLength(); i++){
                    for (Lot temp : DRIVER.lotHashTable.getLinkedList(i)) {
                        if(temp.isSold() && temp.getTitle().toLowerCase().contains(titleField.getText().toLowerCase().trim()) && temp.getType().matches(typeChoiceBox.getValue()) && temp.getOriginDate() == yearSpinner.getValue() && temp.getDescription().toLowerCase().contains(descField.getText().toLowerCase().trim()))
                            soldLotsTV.getItems().add(temp);
                    }
                }
            }else{  //both
                for (int i=0; i<DRIVER.lotHashTable.hashTableLength(); i++){
                    for (Lot temp : DRIVER.lotHashTable.getLinkedList(i)) {
                        if(temp.getTitle().toLowerCase().contains(titleField.getText().toLowerCase().trim()) && temp.getType().matches(typeChoiceBox.getValue()) && temp.getOriginDate() == yearSpinner.getValue() && temp.getDescription().toLowerCase().contains(descField.getText().toLowerCase().trim()))
                            activeLotsTV.getItems().add(temp);
                    }
                }
            }

        }else{  //none, display all
            if (soldActive.getSelectedToggle() == activeLotsRadio) {  //active lots only
                for (int i=0; i<DRIVER.lotHashTable.hashTableLength(); i++){
                    for (Lot temp : DRIVER.lotHashTable.getLinkedList(i)) {
                        if(!temp.isSold())
                            activeLotsTV.getItems().add(temp);
                    }
                }
            }else if (soldActive.getSelectedToggle() == soldLotsRadio){ //sold lots only
                for (int i=0; i<DRIVER.lotHashTable.hashTableLength(); i++){
                    for (Lot temp : DRIVER.lotHashTable.getLinkedList(i)) {
                        if(temp.isSold())
                            soldLotsTV.getItems().add(temp);
                    }
                }
            }else{  //both
                for (int i=0; i<DRIVER.lotHashTable.hashTableLength(); i++){
                    for (Lot temp : DRIVER.lotHashTable.getLinkedList(i)) {
                        activeLotsTV.getItems().add(temp);
                    }
                }
            }
        }
        JOptionPane.showMessageDialog(frame, "Search Complete!", "Search Complete!", JOptionPane.INFORMATION_MESSAGE);
    }

    @FXML
    protected void changeToLotDetails(ActionEvent actionEvent) throws IOException{
        if (soldActive.getSelectedToggle() == activeLotsRadio || soldActive.getSelectedToggle() == activeSoldRadio) {    //if activeLotsRadio or activeSoldRadio are selected, activeLotsTV is used
            selectedLot = DRIVER.lotHashTable.findPosition(activeLotsTV.getSelectionModel().getSelectedItem());
        } else {    //otherwise (if soldLotsRadio is selected), soldLotsTV is used
            selectedLot = DRIVER.lotHashTable.findPosition(soldLotsTV.getSelectionModel().getSelectedItem());
        }
        if (selectedLot == null) {
            JOptionPane.showMessageDialog(frame, "Please select a lot to view the details of.", "Selection Error!", JOptionPane.ERROR_MESSAGE);
        }else {
            Parent detailsView = FXMLLoader.load(Objects.requireNonNull(AuctionApp.class.getResource("lot-details-view.fxml")));
            lotDetailsScene = new Scene(detailsView);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(lotDetailsScene);
            stage.setTitle("Bid-A-Lot: " + selectedLot.getTitle());
        }
    }

    @FXML
    private void changeToLotMenu(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(lotScene);
        stage.setTitle("Bid-A-Lot: Lots");
        stage.show();
    }
}
