package com.bid.bidalot.objects;

public class Bidder {
    //fields
    private String name, address, phone, email, password;

    //constructor
    public Bidder (String name, String address, String phone, String email, String password){
        //todo: validation
        this.name = name;
        this.address = address;

        if (phone.matches("^[\\d]{3}[\\s]?[\\d]{3}[\\s]?[\\d]{4}$"))    //allows for any 3 digits, an optional space, 3 digits, optional space, and any 4 digits
            if (phone.length() != 12) {                                        //adding in spaces between digits if not included
                String temp = phone.replaceAll("[^\\d]", "");
                temp = temp.substring(0, 3) + " " + temp.substring(3, 6) + " " + temp.substring(6);
                this.phone = temp;
            } else
                this.phone = phone;
        else
            this.phone = "0000000000";

        this.email = email;     //email must be unique, used for login
        this.password = password;   //used for login
    }

    //methods
    @Override
    public String toString(){
        return name + " | Address: " + address +  " | Phone No.: " + phone + " | Email: " + email;
    }

    //getters
    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    //setters
    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        if (phone.matches("^[\\d]{3}[\\s]?[\\d]{3}[\\s]?[\\d]{4}$"))
            if (phone.length() != 12) {
                String temp = phone.replaceAll("[^\\d]", "");
                temp = temp.substring(0, 3) + " " + temp.substring(3, 6) + " " + temp.substring(6);
                this.phone = temp;
            }
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
