package com.bid.bidalot.lists;

import java.util.Iterator;

public class MyIterator<K> implements Iterator<K> {     //iterator for performing foreach loops on MyLinkedList
    private Node<K> pos;    //current element

    public MyIterator(Node<K> fnode) {
        pos = fnode;
    }

    @Override
    public boolean hasNext() {  //checks if end of list has been reached
        return pos != null;
    }

    @Override
    public K next() {   //returns next object in list
        Node<K> temp = pos;
        pos = pos.next;
        return temp.getContents();
    }
}
