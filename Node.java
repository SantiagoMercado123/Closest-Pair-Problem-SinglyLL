/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.closestpairhalves;

/**
 *
 * @author Santi Mercado
 */
public class Node { //Creating a Node for the Linked List
    Node next;
    Point p;

    public Node(Point point) {
        this.p = point;
        this.next = null;
    }
} 
