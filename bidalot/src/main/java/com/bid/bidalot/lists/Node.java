package com.bid.bidalot.lists;

public class Node<N> {  //general object reference, Node can be of any object type
    public Node<N> next = null;
    private N contents; //contents of Node are of the same object type as Node

    public N getContents() {
        return contents;
    }

    public void setContents(N c) {
        contents = c;
    }
}
