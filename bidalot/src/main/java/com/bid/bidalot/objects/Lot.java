package com.bid.bidalot.objects;

import com.bid.bidalot.AuctionApp;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;

public class Lot {
    //fields
    private String title, description, type, imageLink;
    private LocalDate originDate, startDate, finalSaleDate;
    private LocalTime startTime, finalSaleTime;
    private double askingPrice, finalSalePrice;
    private boolean sold;   //marks whether lot has been sold or not
    private Bidder lotOwner;    //bidder that put up the lot for sale
    //linkedList of bids

    //constructor
    public Lot (String title, String description, String type, String imageLink, LocalDate originDate, double askingPrice, Bidder lotOwner){
        //todo: validation
        this.title = title;
        this.description = description;
        this.type = type;   //dropdown of pre-selected categories
        if (!imageLink.equals(""))  //todo: replace with file chooser and image upload?
            this.imageLink = imageLink;
        else
            this.imageLink = "N/A";
        this.originDate = originDate;
        this.startDate = LocalDate.now();
        this.startTime = LocalTime.now();
        this.askingPrice = Double.parseDouble(AuctionApp.DF.format(askingPrice));
        this.lotOwner = lotOwner;
        this.sold = false;
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

    public LocalDate getOriginDate() {
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

    //setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setImageLink(String imageLink) {
        if (!imageLink.equals(""))
            this.imageLink = imageLink;
    }

    public void setOriginDate(LocalDate originDate) {
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
}
