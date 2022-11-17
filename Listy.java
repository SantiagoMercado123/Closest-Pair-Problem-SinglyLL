/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.closestpairhalves;

/**
 *
 * @author Santi Mercado
 */
public class Listy {
    Node head;
    int size;
    
    public Listy(Node h, int s) { //Implementing our own Singly Linked List with a getter to facilitate the implementation.
        this.head = h;
        this.size = s;
    }

    public Node get(int i) {
        if (i == 0) {
            return this.head;
        } else {
            if (i < 0 || i > size) {
                System.out.println("Error getter");
                return null;
            } else {
                Node P = this.head;
                for (int j = 0; j < i; j++) {
                    P = P.next;
                }
                return P;
            }
        }
    }

    public Listy subList(int i, int f, int tam) { //Creates a sublist that ranges from element in position i to element in pos f
        Listy lists = new Listy(new Node(this.get(i).p), tam);
        Node P = lists.head;
        for (int j = 1; j < tam; j++) {
            P.next = new Node(this.get(j).p);
            P = P.next;
        }
        return lists;
    }
}
