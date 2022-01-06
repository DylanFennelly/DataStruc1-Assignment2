package com.bid.bidalot.objects;

import com.bid.bidalot.controllers.SearchBidderController;
import com.bid.bidalot.hashing.BidderHashTable;
import com.bid.bidalot.lists.MyLinkedList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BidALotTest {
    private Bidder b1,b2,b3,b4,b5,bCorrect,bIncorrect;
    private MyLinkedList<Bidder> bidderList;
    private BidderHashTable bidderHashTable;
    private SearchBidderController searchBidderController;

    @BeforeEach
    void setUp() {
        b1 = new Bidder("Dylan Fennelly", "Tramore, Co. Waterford", "1234567890", "dylanfennelly@gmail.com", "password");
        b2 = new Bidder("Máté Dominics", "Kilkenny Town, Co. Kilkenny", "0987654321", "matedominics@gmail.com", "password");
        b3 = new Bidder("Jason Anca", "Carlow, Co. Carlow", "1029384756", "jasonanca@gmail.com", "password");
        b4 = new Bidder("Ryan Fennelly", "Tramore, Co. Waterford", "1234567890", "ryanfennelly@gmail.com", "password");
        b5 = new Bidder("Abigail Williams", "Salem, Massachusetts", "4567891230", "abbywilliams@gmail.com", "password");

        bidderList= new MyLinkedList<>();
        bidderList.addElementToEnd(b1);
        bidderList.addElementToEnd(b2);
        bidderList.addElementToEnd(b3);
        bidderList.addElementToEnd(b4);
        bidderList.addElementToEnd(b5);

        bidderHashTable = new BidderHashTable();
        bidderHashTable.add(b1);
        bidderHashTable.add(b2);
        bidderHashTable.add(b3);
        bidderHashTable.add(b4);
        bidderHashTable.add(b5);

        searchBidderController = new SearchBidderController();
    }

    @AfterEach
    void tearDown() {
        b1 = b2 = b3 = bCorrect = bIncorrect = null;
        bidderList = null;
        bidderHashTable = null;
        searchBidderController = null;
    }

    //test sorting

    @Test
    void testBidderConstrutor(){
        //expected
        bCorrect = new Bidder("Mark Power","Dublin 13, Co. Dublin","0861234098","markpower@gmail.com","password");

        assertEquals("086 123 4098",bCorrect.getPhone());
        assertEquals("markpower@gmail.com",bCorrect.getEmail());
        assertEquals("password",bCorrect.getPassword());

        //incorrect values
        bIncorrect = new Bidder("Wrong Way", "Cavan, Co. Cavan", "855","wrongwayemail.com","hej");

        assertEquals("0000000000", bIncorrect.getPhone());
        assertEquals("INVALID@EMAIL.COM",bIncorrect.getEmail());
        assertEquals("INVALID",bIncorrect.getPassword());
    }

    @Test
    //new method since first CA for this project
    void removedElementLinkedList(){
        //removed from mid
        assertEquals(b3, bidderList.getElementByInt(2).getContents());
        bidderList.removeElement(2);    //b3, Jason Anca
        assertNotEquals(b3, bidderList.getElementByInt(2).getContents());
        assertEquals(b4, bidderList.getElementByInt(2).getContents());
        assertEquals(b2, bidderList.getElementByInt(1).getContents());

        //remove head
        assertEquals(b1, bidderList.getElementByInt(0).getContents());
        bidderList.removeElement(0);    //b1, Dylan Fennelly
        assertNotEquals(b1, bidderList.getElementByInt(0).getContents());
        assertEquals(b2, bidderList.getElementByInt(0).getContents());
        assertEquals(b4, bidderList.getElementByInt(1).getContents());

        //remove from end
        assertEquals(b5, bidderList.getElementByInt(2).getContents());
        bidderList.removeElement(2);    //b5, Abigail Williams
        assertNotEquals(b5, bidderList.getElementByInt(1).getContents());
        assertEquals(b2, bidderList.getElementByInt(0).getContents());
        assertEquals(b4, bidderList.getElementByInt(1).getContents());

    }

    @Test
    void testBidderHashFunction(){
        assertEquals(3,bidderHashTable.hashFunction(b1));
        assertEquals(12,bidderHashTable.hashFunction(b2));
        assertEquals(9,bidderHashTable.hashFunction(b3));
        assertEquals(17,bidderHashTable.hashFunction(b4));
        assertEquals(0,bidderHashTable.hashFunction(b5));
        Bidder specialCase = new Bidder("007","Home","1234567890","007@uk.gov","password");
        assertEquals(26,bidderHashTable.hashFunction(specialCase));
    }

    @Test
    void testBidderPositionByEmail(){
        assertEquals(b1,bidderHashTable.findPositionByEmail(b1.getEmail()));
        assertEquals(b3,bidderHashTable.findPositionByEmail(b3.getEmail()));
        assertEquals(b4,bidderHashTable.findPositionByEmail(b4.getEmail()));
        Bidder specialCase = new Bidder("007","Home","1234567890","007@uk.gov","password");
        assertNull(bidderHashTable.findPositionByEmail(specialCase.getEmail()));

    }

    @Test
    void testSortByBidderName(){
        assertEquals(b1, bidderList.getElementByInt(0).getContents());
        assertEquals(b5, bidderList.getElementByInt(4).getContents());
        searchBidderController.selectionSortByNameAscending(bidderList.head);
        assertEquals(b5, bidderList.getElementByInt(0).getContents());
        assertEquals(b1,bidderList.getElementByInt(1).getContents());
        assertEquals(b4,bidderList.getElementByInt(4).getContents());
    }

    @Test
    void testSortByBidderAddress(){
        assertEquals(b1, bidderList.getElementByInt(0).getContents());
        assertEquals(b5, bidderList.getElementByInt(4).getContents());
        searchBidderController.selectionSortByAddress(bidderList.head);
        assertEquals(b3, bidderList.getElementByInt(0).getContents());
        assertEquals(b1, bidderList.getElementByInt(4).getContents());
        assertEquals(b5, bidderList.getElementByInt(2).getContents());
    }


}