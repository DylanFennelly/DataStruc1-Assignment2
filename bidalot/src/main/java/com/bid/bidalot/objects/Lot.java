package com.bid.bidalot.objects;

import com.bid.bidalot.AuctionApp;
import com.bid.bidalot.lists.MyLinkedList;

import java.time.LocalDate;
import java.time.LocalTime;

public class Lot {
    //fields
    private String title, description, type, imageLink;
    private int originDate;
    private LocalDate startDate, finalSaleDate;
    private LocalTime startTime, finalSaleTime;
    private double startPrice, askingPrice, finalSalePrice;
    private boolean sold;   //marks whether lot has been sold or not
    private Bidder lotOwner;    //bidder that put up the lot for sale
    private MyLinkedList<Bid> listOfBids;

    //constructor
    public Lot(String title, String description, String type, String imageLink, int originDate, double askingPrice, Bidder lotOwner) {
        if (title.length() <= 50)
            this.title = title;
        else
            this.title = title.substring(0, 50);

        if (description.length() <= 500)
            this.description = description;
        else
            this.description = description.substring(0, 500);

        this.type = type;   //dropdown of pre-selected categories
        if (!imageLink.equals(""))
            this.imageLink = imageLink;
        else
            this.imageLink = "N/A";
        this.originDate = originDate;
        this.startDate = LocalDate.now();
        this.startTime = LocalTime.now();
        //asking price = current highest bid
        this.askingPrice = this.startPrice = Double.parseDouble(AuctionApp.DF.format(askingPrice)); //validated in controller
        this.lotOwner = lotOwner;
        this.sold = false;
        listOfBids = new MyLinkedList<>();
    }

    //methods
    @Override
    public String toString() {      //only returns name: same situation as in bidder view: for formatting reasons in tableview (bidder details bids tableview)
        return title;
    }

    //getters
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public String getImageLink() {
        return imageLink;
    }

    public int getOriginDate() {
        return originDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getFinalSaleDate() {
        return finalSaleDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getFinalSaleTime() {
        return finalSaleTime;
    }

    public double getAskingPrice() {
        return askingPrice;
    }

    public double getFinalSalePrice() {
        return finalSalePrice;
    }

    public boolean isSold() {
        return sold;
    }

    public Bidder getLotOwner() {
        return lotOwner;
    }

    public MyLinkedList<Bid> getListOfBids() {
        return listOfBids;
    }

    public double getStartPrice() {
        return startPrice;
    }

    //setters
    public void setTitle(String title) {
        if (title.length() <= 50)
            this.title = title;
    }

    public void setDescription(String description) {
        if (description.length() <= 500)
            this.description = description;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setImageLink(String imageLink) {
        if (!imageLink.equals(""))
            this.imageLink = imageLink;
    }

    public void setOriginDate(int originDate) {
        this.originDate = originDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setFinalSaleDate(LocalDate finalSaleDate) {
        this.finalSaleDate = finalSaleDate;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setFinalSaleTime(LocalTime finalSaleTime) {
        this.finalSaleTime = finalSaleTime;
    }

    public void setAskingPrice(double askingPrice) {
        this.askingPrice = Double.parseDouble(AuctionApp.DF.format(askingPrice));
    }

    public void setFinalSalePrice() {  //final sale price is askingPrice at time of sale
        this.finalSalePrice = this.askingPrice;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public void setLotOwner(Bidder lotOwner) {
        this.lotOwner = lotOwner;
    }

    public void setListOfBids(MyLinkedList<Bid> listOfBids) {
        this.listOfBids = listOfBids;
    }

    public void setStartPrice(double startPrice) {
        this.startPrice = Double.parseDouble(AuctionApp.DF.format(startPrice));
    }

}
