package com.bid.bidalot.hashing;

import com.bid.bidalot.lists.MyLinkedList;
import com.bid.bidalot.objects.Lot;


public class LotHashTable {
    //In this hashtable, there are 30 slots. Each lot will be placed into a slot based on the remainder
    //of a number created by the combination of the lot owner and time of creation when 30 is the modulo
    MyLinkedList<Lot>[] lotHashTable;

    //constructor
    public LotHashTable(){
        lotHashTable = new MyLinkedList[30];

        //creating link lists in each slot
        for (int i = 0; i < lotHashTable.length; i++)
            lotHashTable[i] = new MyLinkedList<>();
    }

    public int hashFunction(Lot k){
        //create a combination string of the lotOwner and time lot was started (both attributes are unchangeable and universally unique when combined)
        String combination = k.getLotOwner() + k.getStartTime().toString();
        int total=0;
        for (int i =0; i<combination.length();i++){
            //add up character code values of all characters in the string
            total+=combination.charAt(i);
        }
        //return remainder of total divided by hashtable length (30)
        return total%lotHashTable.length;
    }

    public void add(Lot l){
        lotHashTable[hashFunction(l)].addElementToTop(l);
    }

    public Lot findPosition(Lot l){
        int loc = hashFunction(l);   //generate hashCode from l
        String combination = l.getLotOwner() + l.getStartTime().toString();

        for (Lot lot : lotHashTable[loc]) {     //iterate through list found by hashCode of temp
            String tempCombo = lot.getLotOwner() + lot.getStartTime().toString();
            if (tempCombo.equals(combination)) {     //if temp lot combo string is the same as input lot combo string, then lots are the same
                return lot;   //return bidder
            }
        }

        return null;    //if matching email is not found, return null;
    }

    public void removeElement(Lot l){
        int loc = hashFunction(l);   //generate hashCode from l
        boolean removed = false;

        for (int i = 0; i<lotHashTable[loc].getListLength();i++){//iterate through list found by hashCode of temp
            if (hashFunction(lotHashTable[loc].getElementByInt(i).getContents()) == hashFunction(l)) {     //if matching lot hashcode is found
                lotHashTable[loc].removeElement(i);
                removed = true;
                break;
            }
        }
        if (!removed)
            System.out.println("Hashtable does not contain " + l.getTitle());
    }

    //remove all elements from all linkedLists in hashTable
    public void clearAllElements(){
        for (MyLinkedList<Lot> lots : lotHashTable)
            lots.removeAllElements();
    }

    public MyLinkedList<Lot> getLinkedList(int i){
        return lotHashTable[i];
    }

    public int hashTableLength(){
        return lotHashTable.length;
    }

}
