package com.bid.bidalot.hashing;

import com.bid.bidalot.lists.MyLinkedList;
import com.bid.bidalot.objects.Bidder;

public class BidderHashTable {
    //separate chaining hashtable: each hash table entry contains a linked list that it adds to.
    //In this hashTable, there is 27 entries: one for each letter of the alphabet and one additional one for extra characters (0-9, dash, underscore etc.)
    MyLinkedList<Bidder>[] bidderHashTable;

    //constructor
    public BidderHashTable() {
        bidderHashTable = new MyLinkedList[27];   //one linked list for each letter of alphabet + one for extra chartacters

        //creating link lists in each slot
        for (int i = 0; i < bidderHashTable.length; i++)
            bidderHashTable[i] = new MyLinkedList<Bidder>();
    }

    public int hashFunction(Bidder k) {
        //hash function generates value depending on lowercase letter values relative to 97.
        //97 was chosen at the modulator so "a" would be in chain 0.
        //if an email begins with a character outside the alphabet (number, dash, etc.), it is stored in chain 26
        String specialCharCheck = "" + k.getEmail().toLowerCase().charAt(0);
        if (specialCharCheck.matches("[a-z]")) {   //if first character of email is a letter
            return k.getEmail().toLowerCase().charAt(0) % 97;   //go between 0 and 25 (a should be 0, so remainder of 97 is used)
        } else
            return 26;  //Chain 26 is for special characters
    }

    public void add(Bidder b) {
        //finds which chain to enter based on hash function, then adds new element as the head of the linked list
        bidderHashTable[hashFunction(b)].addElementToTop(b);
    }

    public void showHashTable() {
        System.out.println("Hash Table (Using Sep Chaining)\n=====================");
        for (int i = 0; i < bidderHashTable.length; i++) {
            System.out.println("Chain " + i + "\n=======");
            for (Object b : bidderHashTable[i])
                System.out.println(b);
            System.out.println();
        }
        System.out.println();
    }

    //finds a bidder in the hashtable based on an input email (email is unique across bidders)
    public Bidder findPositionByEmail(String i) {
        //create temp bidder with input email
        Bidder temp = new Bidder("", "", "", i, "");
        int loc = hashFunction(temp);   //generate hashCode for temp Bidder

        for (Bidder b : bidderHashTable[loc]) {     //iterate through list found by hashCode of temp
            if (b.getEmail().equals(temp.getEmail())) {     //if matching email is found
                System.out.println("Bidder associated with email " + i + " found in chain " + loc);
                return b;   //return bidder
            }
        }
        System.out.println("Hashtable does not contain " + i);
        return null;    //if matching email is not found, return null;
    }
}
