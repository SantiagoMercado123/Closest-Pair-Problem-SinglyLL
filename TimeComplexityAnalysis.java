/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.closestpairhalves;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author Santi Mercado
 */
public class TimeComplexityAnalysis {
    private static void create(String name)
    // creates a file with a given name
    {
        try {
            // defines the filename
            String fname = (name);
            // creates a new File object
            File f = new File(fname);

            String msg = "creating file `" + fname + "' ... ";
            // creates the new file
            f.createNewFile();

        } catch (IOException err) {
            // complains if there is an Input/Output Error
            err.printStackTrace();
        }

        return;
    }

    private static void write(String name, int tm, ArrayList<Integer> is, ArrayList<Integer> comparisons,
            ArrayList<Integer> runtimes)
    // writes data to a file with a given name and size
    {
        try {
            // defines the filename
            String filename = (name);
            // creates new PrintWriter object for writing file
            PrintWriter out = new PrintWriter(filename);
            String fmt = ("%10s %10s %10s\n"); // We store 3 values in different columns per line: amount of
                                               // integers(n), comparisons and runtime.
            for (int i = 0; i < tm; ++i) {
                out.printf(fmt, is.get(i), comparisons.get(i), runtimes.get(i));
            }

            out.close(); // closes the output stream
        } catch (FileNotFoundException err) {
            // complains if file does not exist
            err.printStackTrace();
        }

        return;
    }

    public void Analysis(int ii) { // ii is the amount of integers to reach starting at i=1000 growing at a rate of
                                   // 2*i.
        ArrayList<Integer> runtimes = new ArrayList<Integer>();
        ArrayList<Integer> is = new ArrayList<Integer>();
        ArrayList<Integer> comparisons = new ArrayList<Integer>(); // We initialize all 3 lists.
        for (int i = 100; i < ii; i = i * 4 / 3) {
            ClosestPairHalves cph = new ClosestPairHalves(); // Create a new ClosestPair class to test it.
            System.out.println(i);
            long total = 0;
            int count;
            Listy list = cph.Initialize(i); // Creating list of coordinates and sorting them by x position.
            for (int j = 0; j < 256; j++) {
                long startTime = System.nanoTime();
                cph.ClosestPair(i, list, 999999999);
                long endTime = System.nanoTime();
                long totalTime = endTime - startTime; // We calculate the runtime for each repetition and add it to the
                                                      // previous ones..
                total = total + totalTime;
            }
            count = cph.getComparisons();
            count = count / 256; // We find the average comparisons per iteration and store it in a list.
            total = total / 256; // Average runtime
            runtimes.add((int) total);
            is.add(i); // Amount of coords in the array
            comparisons.add(count);
        }
        create("Data.txt");
        write("Data.txt", is.size(), is, comparisons, runtimes);
    }
}
