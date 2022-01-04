package com.bid.bidalot.objects;

import com.bid.bidalot.AuctionApp;

import java.time.LocalDate;
import java.time.LocalTime;

public class Bid {
    //fields
    private double bidAmount;
    private LocalDate bidDate;
    private LocalTime bidTime;
    private Bidder bidder;

    //constructor
    public Bid(double bidAmount, LocalDate bidDate, LocalTime bidTime, Bidder bidder){
        this.bidAmount = Double.parseDouble(AuctionApp.DF.format(bidAmount)); //validated in controller
        this.bidDate = bidDate;
        this.bidTime = bidTime;
        this.bidder = bidder;
    }

    //methods
    @Override
    public String toString(){
        return "Bid Amount: " + bidAmount + " | Date: " + bidDate + " | Time: " + bidTime + " | Bidder: " + bidder.getName();
    }

    //getters
    public double getBidAmount() {
        return bidAmount;
    }

    public LocalDate getBidDate() {
        return bidDate;
    }

    public LocalTime getBidTime() {
        return bidTime;
    }

    public Bidder getBidder() {
        return bidder;
    }

    //setters
    public void setBidAmount(double bidAmount) {
        this.bidAmount = bidAmount;
    }

    public void setBidDate(LocalDate bidDate) {
        this.bidDate = bidDate;
    }

    public void setBidTime(LocalTime bidTime) {
        this.bidTime = bidTime;
    }

    public void setBidder(Bidder bidder) {
        this.bidder = bidder;
    }
}
