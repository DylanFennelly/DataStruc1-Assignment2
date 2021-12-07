package com.bid.bidalot.lists;

import java.util.Iterator;

public class MyLinkedList<F> implements Iterable<F> {   //general linked list class
    public Node<F> head = null;

    //adding at top of list
    public void addElementToTop(F e) {
        Node<F> newNode = new Node<>();
        newNode.setContents(e);
        newNode.next = head;
        head = newNode;
    }

    //adding at end of list
    public Node<F> addElementToEnd(F e) {
        Node<F> newNode = new Node<>();
        newNode.setContents(e);
        Node<F> temp = head;

        if (temp == null) {             //while the next reference for temp isnt null
            head = newNode;         //make temp equal to the object at the next reference
        } else {
            while (temp.next != null) {   //when the next reference points to null (i.e. once we have reached the last object in the list),
                temp = temp.next;         //set the next reference to the newVC object
            }
            temp.next = newNode;
        }
        return newNode;
    }

    //removing element in list at input 'index'
    public void removeElement(int x) {
        if (x == 0) {
            head = head.next;   //setting the head to the next element from the head (cutting the head)
        } else {
            Node<F> temp = head;
            for (int i = 1; i < x && temp != null; i++)   //obtaining the element one before the selected one
                temp = temp.next;
            if (temp != null && temp.next != null) {
                temp.next = temp.next.next;     //setting the 'next' reference of the element before the selected element
            }                                   //to the element following the selected element
        }
    }

    //removing all elements
    public void removeAllElements() {
        head = null;
    }


    //getting element in list at input 'index'
    public Node<F> getElementByInt(int x) {
        if (x != 0) {
            Node<F> temp = head;
            for (int i = 1; i < x && temp != null; i++)
                temp = temp.next;
            if (temp != null && temp.next != null)
                return temp.next;
        }
        return head;
    }

    //returning number of elements in the list
    public int getListLength() {
        int i = 0;
        for (Node<F> temp = head; temp != null; temp = temp.next)
            i++;
        return i;
    }

    @Override
    public Iterator<F> iterator() {  //instantiating MyIterator in MyLinkedList
        return new MyIterator<F>(head); //setting head to position (i.e. starting from start of list)
    }
}
