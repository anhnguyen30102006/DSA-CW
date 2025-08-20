package org.example;


public class LostnFoundNode {
    private BaseItem data;              // not null
    private LostnFoundNode next;         // nullable


    public LostnFoundNode(BaseItem data) {
        if (data == null) {
            throw new IllegalArgumentException("Node data cannot be null");
        }
        this.data = data;
        this.next = null; // default
    }


    public BaseItem getData() {
        return data;
    }


    public LostnFoundNode getNext() {
        return next;
    }


    public void setNext(LostnFoundNode next) {
        this.next = next; // can be null for last node
    }
}
