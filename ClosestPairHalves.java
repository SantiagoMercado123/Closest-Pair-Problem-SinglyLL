package com.mycompany.closestpairhalves;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 * Algorithms and Complexity                                  November 17, 2022
 * IST 4310-01
 * Prof. M. Diaz-Maldonado
 *
 * Laboratory # 3: Closest Pair Problem Dividing by Halves using Singly Linked List.
 *
 * Synopsis: Find the closest pair of points by recursively dividing in subsets with half the size the set of coordinates 
 * and applying the Brute Force Algorithm when there are only 3 or less points in the subset. Must take into account the 
 * possibility of the closest pair being from 2 different subsets. This means creating a list of candidates by comparing
 * the points in the borders of different subsets between them and applying Brute Force in the candidates. 
 * The original set of coordinates must be created with n points of random coordinates. This must be done with a manually
 * implemented Linked List and analyze the time complexity relative to solving the same task using an ArrayList.
 * The program finds the minimum distance 200 times for every set of coordinates of size n, where n varies from 100 to 
 * 50000 incrementing by a factor of 4/3. This is done in order to analyze the time complexity of the algorithm.
 * 
 * Author: Santiago Andrés Mercado Barandica y David Salgado Cortés
 * ID: 200155614 / 
*/

public class ClosestPairHalves {
    private static int count; // Counts number of comparisons.

    public ClosestPairHalves() {
        count = 0;
    }

    public int getComparisons() {
        return count;
    }

    public static double[] BruteForce(Listy coords, double mdis) { // Finds closest pair of a given set of coordinates via Brute Force Algorithm.
        double dmin = mdis;
        double[] vec = new double[3]; // Array to store min_dis and the index of points with the closest pair.
        vec[0] = dmin;
        for (int i = 0; i < coords.size; i++) {
            for (int j = i + 1; j < coords.size; j++) {
                double d = distance(coords, i, j); // Compares the distance between every pair of coords and return their distance.
                if (d < dmin) {
                    count++;
                    dmin = d;
                    vec[0] = d;
                    vec[1] = coords.get(i).p.pos;
                    vec[2] = coords.get(j).p.pos;
                } else {
                    count++;
                }
            }
        }
        return vec;
    }

    public static void Printc(List<int[]> coords) { // Prints a given set of coordinates (used to test the algorithm).
        for (int i = 0; i < coords.size(); i++) {
            System.out.println("x: " + coords.get(i)[0] + " y: " + coords.get(i)[1] + " pos: " + coords.get(i)[2]);
        }
    }

    public static Listy FindCandidates(Listy coords, double min) { // We check the pairs near the middles closer to each other than that found in the quadrants.
        ArrayList<Point> cand = new ArrayList<Point>();
        int i = 0;
        while (i < coords.size / 2) { // Comparing the distance in both x and y position of the points in the border
            // of both subsets.
            if (Math.abs(coords.get(i).p.x - coords.get(coords.size / 2).p.x) < min && Math.abs(coords.get(i).p.y - coords.get(coords.size / 2).p.y) < min) {
                count++;
                cand.add(coords.get(i).p); // If the distance is less than the actual minimum distance, they are candidates.
                i++;
            } else {
                count++;
                i = coords.size / 2;
            }
        }
        while (i < coords.size) { // We compare first the points in one subset and then the other to avoid
                                  // repeating points.
            if (Math.abs(coords.get(i).p.x - coords.get(coords.size / 2 - 1).p.x) < min
                    && Math.abs(coords.get(i).p.y - coords.get(coords.size / 2 - 1).p.y) < min) {
                count++;
                cand.add(coords.get(i).p);
                i++;
            } else {
                count++;
                i = coords.size;
            }
        }
        // Converting ArrayList to Listy
        Listy listc;
        if (cand.isEmpty()) {
            listc = new Listy(null, cand.size());
        } else {
            listc = new Listy(new Node(cand.get(0)), cand.size());
            Node P = listc.head;
            for (int j = 1; j < cand.size(); j++) {
                P.next = new Node(cand.get(j));
                P = P.next;
            }
        }
        return listc;
    }

    public static double[] ClosestPair(int N, Listy x, double mdis) {
        // Finds closest pair of a given set of coordinates via Brute Force Algorithm,
        // but using recursion.
        if (N > 3) { // If there are more than 3 points in a region, we divide it in 2.
            double[] g1 = new double[3];
            double[] g2 = new double[3];
            int offset = 0;
            count++;
            if (N % 2 == 1) {
                offset = 1; // Taking into account an odd sized sample. The second half will 1 unit bigger
                            // thean the first half.
            }
            // Splits in half the set of coordinates until there are only 3 or less coords
            // and apply the algorithm in both halfs, then compare them.
            Listy l2 = x.subList(N / 2, N, N / 2);
            Listy l1 = x.subList(0, N / 2, N / 2);
            g1 = ClosestPair(N / 2, l1, mdis);
            g2 = ClosestPair(N / 2, l2, mdis);
            double[] g = new double[3];
            if (g1[0] < g2[0]) { // Store the closest pair of the regions.
                g = g1;
                count++;
            } else {
                count++;
                g = g2;
            }
            Listy candidates = FindCandidates(x, g[0]); // List to store possible candidates.
            // Apply the Brute Force algorithm to the set of candidates if there are at
            // least 2.
            if (candidates.size > 1) {
                count++;
                g1 = BruteForce(candidates, mdis);
                if (g1[0] < g[0]) {
                    count++;
                    return g1; // Compare the actual min_dis with the one obtained form the candidates and
                               // return the lowest.
                } else {
                    count++;
                    return g;
                }
            } else {
                count++;
                return g;
            }
        } else {
            count++;
            double[] vec = new double[3];
            return BruteForce(x, mdis); // Apply BruteForce algorithm when there are 3 or less coords.
        }
    }

    public static double distance(Listy coords, int i, int j) { // computes the distance between the ith and jth
                                                                // elements
        // unpacks coordinates of the ith and jth elements.
        int x1 = coords.get(i).p.x;
        int x2 = coords.get(j).p.x;
        int y1 = coords.get(i).p.y;
        int y2 = coords.get(j).p.y;
        double d = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1)); // computes their distance
        return d;
    }

    public static Listy Initialize(int tam) { // Creating a set of random coordinates in a Singly Linked List and sorting it.
        Random rand = new Random();
        Node P = null;
        Node PTR = null;
        for (int i = 0; i < tam; i++) {
            Point temp = new Point(rand.nextInt(10000), rand.nextInt(10000), i);
            Node tempn = new Node(temp);
            if (i == 0) {
                PTR = tempn;
                P = PTR;
            } else {
                P.next = tempn;
                P = P.next;
            }
        }
        SortList(PTR);
        Listy list = new Listy(PTR, tam);
        P = list.head;
        while (P.next != null) {
            P = P.next;
        }
        // P.next = list.head;
        return list;
    }

    private static void SortList(Node PTR) { //Sorts a Linked List by the position in the x coordinate of each point.
        Node P = PTR;
        int tam = 0;
        while (P != null) {
            tam++;
            P = P.next;
        }
        P = PTR;
        for (int i = 0; i < tam; i++) {
            int min = P.p.x;
            Node P2 = P.next;
            while (P2 != null) {
                if (P2.p.x < min) {
                    Point temp = P2.p;
                    P2.p = P.p;
                    P.p = temp;
                    min = P.p.x;
                }
                P2 = P2.next;
            }
            P = P.next;
        }
    }

    public static void main(String[] args) {
        TimeComplexityAnalysis tca = new TimeComplexityAnalysis();
        tca.Analysis(50000);
    }
}
