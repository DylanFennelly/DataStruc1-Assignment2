package com.bid.bidalot.objects;

import com.bid.bidalot.AuctionApp;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Bid {
    //fields
    private double bidAmount;
    private String bidDate, bidTime;    //stored as strings for formatting reasons when it comes to display in tableviews
    private Bidder bidder;
    private Lot parentLot;  //used to allow a lot to be navigated to from the bidders-details-view bids tableview

    //constructor
    public Bid(double bidAmount, LocalDate bidDate, LocalTime bidTime, Bidder bidder, Lot parentLot) {
        this.bidAmount = Double.parseDouble(AuctionApp.DF.format(bidAmount)); //validated in controller
        this.bidDate = bidDate.format(DateTimeFormatter.ofPattern("dd/MM/yy"));
        this.bidTime = bidTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        this.bidder = bidder;
        this.parentLot = parentLot;
    }

    //methods
    @Override
    public String toString() {
        return "Bid Amount: " + bidAmount + " | Date: " + bidDate + " | Time: " + bidTime + " | Bidder: " + bidder.getName();
    }

    //getters
    public double getBidAmount() {
        return bidAmount;
    }

    public String getBidDate() {
        return bidDate;
    }

    public String getBidTime() {
        return bidTime;
    }

    public Bidder getBidder() {
        return bidder;
    }

    public Lot getParentLot() {
        return parentLot;
    }

    //setters
    public void setBidAmount(double bidAmount) {
        this.bidAmount = bidAmount;
    }

    public void setBidDate(String bidDate) {
        this.bidDate = bidDate;
    }

    public void setBidTime(String bidTime) {
        this.bidTime = bidTime;
    }

    public void setBidder(Bidder bidder) {
        this.bidder = bidder;
    }

    public void setParentLot(Lot parentLot) {
        this.parentLot = parentLot;
    }
}
