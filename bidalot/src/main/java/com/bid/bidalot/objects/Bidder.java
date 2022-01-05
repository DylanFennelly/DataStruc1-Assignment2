package com.bid.bidalot.objects;

public class Bidder {
    //fields
    private String name, address, phone, email, password;

    //constructor
    public Bidder (String name, String address, String phone, String email, String password){
        if (name.length() <= 40)
            this.name = name;
        else
            this.name = name.substring(0,40);

        if (address.length() <= 100)
            this.address = address;
        else
            this.address = address.substring(0,100);

        if (phone.matches("^[\\d]{3}[\\s]?[\\d]{3}[\\s]?[\\d]{4}$"))    //allows for any 3 digits, an optional space, 3 digits, optional space, and any 4 digits
            if (phone.length() != 12) {                                        //adding in spaces between digits if not included
                String temp = phone.replaceAll("[^\\d]", "");
                temp = temp.substring(0, 3) + " " + temp.substring(3, 6) + " " + temp.substring(6);
                this.phone = temp;
            } else
                this.phone = phone;
        else
            this.phone = "0000000000";

        //email must be unique, used for login
        if (email.matches("^[\\w-.]+@([\\w-]+.)+[\\w-]{2,4}$")) //allows for any number of alphanumeric characters, dashes (-) and dots (.), followed by an @ symbol,
            this.email = email;                                       //any number of alphanumeric characters, dashes and dots, a dot and ending with a two to four alphanumeric string.
        else                                                          //from: https://regexr.com/3e48o
            this.email = "INVALID@EMAIL.COM";

        //used for login
        if (password.length() >= 8)     //validated in controller
            this.password = password;
        else
            this.password = "INVALID";
    }

    //methods
    @Override
    public String toString(){   //only return's name: in bids tableview, it was displaying all details instead of just the name.
        return name;            //setting up the columns to do otherwise was quite complicated so with the time available, this is the best solution
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
        if (name.length() <= 40)
            this.name = name;
    }

    public void setAddress(String address) {
        if (address.length() <= 100)
            this.address = address;
    }

    public void setPhone(String phone) {
        if (phone.matches("^[\\d]{3}[\\s]?[\\d]{3}[\\s]?[\\d]{4}$"))
            if (phone.length() != 12) {
                String temp = phone.replaceAll("[^\\d]", "");
                temp = temp.substring(0, 3) + " " + temp.substring(3, 6) + " " + temp.substring(6);
                this.phone = temp;
            }else
                this.phone = phone;
    }

    public void setEmail(String email) {
        if (email.matches("^[\\w-.]+@([\\w-]+.)+[\\w-]{2,4}$"))
            this.email = email;
    }

    public void setPassword(String password) {
        if (password.length() >= 8)
            this.password = password;
    }
}
