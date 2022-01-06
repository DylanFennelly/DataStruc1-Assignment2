package com.bid.bidalot.controllers;

import com.bid.bidalot.AuctionApp;
import com.bid.bidalot.lists.MyLinkedList;
import com.bid.bidalot.objects.Bidder;
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
import static com.bid.bidalot.controllers.BidderController.bidderDetailsScene;
import static com.bid.bidalot.controllers.BidderController.selectedBidder;
import static com.bid.bidalot.controllers.StartController.bidScene;


public class SearchBidderController {
    private MyLinkedList<Bidder> bidderList;
    private JFrame frame;
    @FXML
    private Label loginLabel;
    @FXML
    private TextField bidderNameField, bidderAddressField;
    @FXML
    private TableView<Bidder> biddersTV;
    @FXML
    private ToggleGroup sortGroup;
    @FXML
    private RadioButton nameRadio, noneRadio, addressRadio;

    @FXML
    protected void initialize() {
        if(AuctionApp.loggedInBidder != null){
            loginLabel.setText("Logged in as: " + AuctionApp.loggedInBidder.getName());
        }
    }

    @FXML
    private void searchButton(ActionEvent actionEvent){
        //LinkedList for sorting options
        bidderList = new MyLinkedList<>();
        //clearing tableViews between searches
        biddersTV.getItems().clear();
        //setting up boolean conditions for easier tracking
        boolean nameSearch = false, addressSearch = false;
        if (!bidderNameField.getText().equals(""))  //if there is a value in the title field
            nameSearch = true;
        if (!bidderAddressField.getText().equals(""))
            addressSearch = true;

        if(nameSearch && !addressSearch){   //name search only
            for (int i=0; i<DRIVER.bidderHashTable.hashTableLength() ;i++){
                for (Bidder temp : DRIVER.bidderHashTable.getLinkedList(i)){
                    if (temp.getName().toLowerCase().contains(bidderNameField.getText().toLowerCase().trim()))
                        bidderList.addElementToEnd(temp);
                }
            }
        }else if(!nameSearch && addressSearch) {  //address search only
            for (int i=0; i<DRIVER.bidderHashTable.hashTableLength() ;i++){
                for (Bidder temp : DRIVER.bidderHashTable.getLinkedList(i)){
                    if (temp.getAddress().toLowerCase().contains(bidderAddressField.getText().toLowerCase().trim()))
                        bidderList.addElementToEnd(temp);
                }
            }

        }else if (nameSearch && addressSearch){ //both
            for (int i=0; i<DRIVER.bidderHashTable.hashTableLength() ;i++){
                for (Bidder temp : DRIVER.bidderHashTable.getLinkedList(i)){
                    if (temp.getName().toLowerCase().contains(bidderNameField.getText().toLowerCase().trim()) && temp.getAddress().toLowerCase().contains(bidderAddressField.getText().toLowerCase().trim()))
                        bidderList.addElementToEnd(temp);
                }
            }

        }else{ //none (display all bidders)
            for (int i=0; i<DRIVER.bidderHashTable.hashTableLength() ;i++){
                for (Bidder temp : DRIVER.bidderHashTable.getLinkedList(i)){
                    bidderList.addElementToEnd(temp);
                }
            }
        }
        //sorting list by selected parameters
        if (sortGroup.getSelectedToggle() == nameRadio){
            selectionSortByNameAscending(bidderList.head);
        }else if (sortGroup.getSelectedToggle() == addressRadio){
            selectionSortByAddress(bidderList.head);
        }
        for (Bidder b : bidderList){
            biddersTV.getItems().add(b);
        }

        JOptionPane.showMessageDialog(frame, "Search Complete!", "Search Complete!", JOptionPane.INFORMATION_MESSAGE);

    }

    public void selectionSortByNameAscending(com.bid.bidalot.lists.Node<Bidder> head) {
        //selection sort, from: https://stackoverflow.com/questions/29808971/selection-sorting-a-linked-list
                                                                                                                                //starting from head and going until end of linkedList is reached,
        for (com.bid.bidalot.lists.Node<Bidder> sortingNode = head; sortingNode != null; sortingNode = sortingNode.next) {    //iterating up to the next node in the list each loop (i.e. once head is sorted, head.next is sorted,
                                                                                                                            //then head.next.next and so on until next of last element is reached and is equal to null)
            com.bid.bidalot.lists.Node<Bidder> min = sortingNode;                 //node with lowest value of all nodes compared in loop so far
            for (com.bid.bidalot.lists.Node<Bidder> workingNode = sortingNode; workingNode != null; workingNode = workingNode.next) {   //looping through each node in the list and comparing to the working node.
                if (min.getContents().getName().toLowerCase().compareTo(workingNode.getContents().getName().toLowerCase()) > 0) { //comparing bidderNames alphabetically. If workingNode comes first alphabetically, the compareTo returns
                    min = workingNode;                                                                //positive and min (the lowest value) is set to be workingNode
                }
            }
            com.bid.bidalot.lists.Node<Bidder> temp = new com.bid.bidalot.lists.Node<>();   //temp node to hold details of sortingNode while it is being updated with new values
            temp.setContents(sortingNode.getContents());
            sortingNode.setContents(min.getContents()); //The node with the lowest value (alphabetically comes first) is set to the sortingNode (head)
            min.setContents(temp.getContents());        //the sortingNode (head) is inserted where the lowest value node was
                                                        //The loop repeats, starting next from head.next. The head has been sorted and therefore does not need to be considered for sorting
        }
    }

    public void selectionSortByAddress(com.bid.bidalot.lists.Node<Bidder> head) {
        for (com.bid.bidalot.lists.Node<Bidder> sortingNode = head; sortingNode != null; sortingNode = sortingNode.next) {
            com.bid.bidalot.lists.Node<Bidder> min = sortingNode;
            for (com.bid.bidalot.lists.Node<Bidder> workingNode = sortingNode; workingNode != null; workingNode = workingNode.next) {
                if (min.getContents().getAddress().toLowerCase().compareTo(workingNode.getContents().getAddress().toLowerCase()) > 0) {
                    min = workingNode;
                }
            }
            com.bid.bidalot.lists.Node<Bidder> temp = new com.bid.bidalot.lists.Node<>();
            temp.setContents(sortingNode.getContents());
            sortingNode.setContents(min.getContents());
            min.setContents(temp.getContents());
        }
    }

    @FXML
    protected void changeToBidderDetails(ActionEvent actionEvent) throws IOException {
        selectedBidder = DRIVER.bidderHashTable.findPositionByEmail(biddersTV.getSelectionModel().getSelectedItem().getEmail());
        if (selectedBidder == null) {
            JOptionPane.showMessageDialog(frame, "Please select a bidder to view the details of.", "Selection Error!", JOptionPane.ERROR_MESSAGE);
        }else {
            Parent detailsView = FXMLLoader.load(Objects.requireNonNull(AuctionApp.class.getResource("bidder-details-view.fxml")));
            bidderDetailsScene = new Scene(detailsView);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(bidderDetailsScene);
            stage.setTitle("Bid-A-Lot: " + selectedBidder.getName());
        }
    }

    @FXML
    protected void changeToBidderMenu(ActionEvent actionEvent) throws IOException {
        //reload bidder-view to refresh lots TableView to reflect any changes made
        Parent bidView = FXMLLoader.load(Objects.requireNonNull(AuctionApp.class.getResource("bidder-view.fxml")));
        bidScene = new Scene(bidView);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(bidScene);
        stage.setTitle("Bid-A-Lot: Bidders");
        stage.show();
    }
}
