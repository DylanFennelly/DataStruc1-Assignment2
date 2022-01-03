package com.bid.bidalot.objects;

import com.bid.bidalot.AuctionApp;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;

public class Lot {
    //fields
    private String title, description, type, imageLink;
    private int originDate;
    private LocalDate startDate, finalSaleDate;
    private LocalTime startTime, finalSaleTime;
    private double startPrice, askingPrice, finalSalePrice;
    private boolean sold;   //marks whether lot has been sold or not
    private Bidder lotOwner;    //bidder that put up the lot for sale
    private LinkedList<Bid> listOfBids;

    //constructor
    public Lot (String title, String description, String type, String imageLink, int originDate, double askingPrice, Bidder lotOwner){
        if (title.length() <= 50)
            this.title = title;
        else
            this.title = title.substring(0,50);

        if (description.length() <= 500)
            this.description = description;
        else
            this.description = description.substring(0,500);

        this.type = type;   //dropdown of pre-selected categories
        if (!imageLink.equals(""))  //todo: replace with file chooser and image upload?
            this.imageLink = imageLink;
        else
            this.imageLink = "N/A";
        this.originDate = originDate;
        this.startDate = LocalDate.now();
        this.startTime = LocalTime.now();
        this.askingPrice = this.startPrice = Double.parseDouble(AuctionApp.DF.format(askingPrice)); //validated in controller
        this.lotOwner = lotOwner;
        this.sold = false;
        listOfBids = new LinkedList<>();
    }

    //methods
    //todo: alter/shorten for listView, more in depth details displayed on lot page
    @Override
    public String toString() {
        return title + " | Description: " + description + " | Type: " + type +
                " | Image Link: " + imageLink + " | Origin Date: " + originDate +
                " | Start Date: " + startDate + " | Start Time: " + startTime +
                " | Sold Date: " + finalSaleDate +  " | Sold Time: " + finalSaleDate +
                " | Current Asking Price: " + String.format("%.2f", askingPrice) + " | Sold Price: " + String.format("%.2f", finalSalePrice) + " | Sold Status: " + sold;
                                            // ^ fixing an issue with both decimals places not displaying | https://java2blog.com/format-double-to-2-decimal-places-java/
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

    public LinkedList<Bid> getListOfBids() {
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

    public void setAskingPrice(float askingPrice) {
        this.askingPrice = Float.parseFloat(AuctionApp.DF.format(askingPrice));
    }

    public void setFinalSalePrice(float finalSalePrice) {
        this.finalSalePrice = finalSalePrice;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public void setLotOwner(Bidder lotOwner) {
        this.lotOwner = lotOwner;
    }

    public void setListOfBids(LinkedList<Bid> listOfBids) {
        this.listOfBids = listOfBids;
    }

    public void setStartPrice(double startPrice) {
        this.startPrice = Float.parseFloat(AuctionApp.DF.format(startPrice));
    }

}
