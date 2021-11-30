package se.jakub.day1;

import se.jakub.DayReader;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        var t = new Scanner(DayReader.inputReader(1, 1));
        var l = new ArrayList<Integer>();
        while (t.hasNextLine()) {
            l.add(Integer.valueOf(t.nextLine()));
        }

        for (int i = 0; i < l.size(); i++) {
            for (int j = 0; j < l.size(); j++) {
                for (int k = 0; k < l.size(); k++) {

                    if (i == j) {
                        continue;
                    }

                    if (l.get(k) + l.get(i) + l.get(j) == 2020) {
                        System.out.println(l.get(k) * l.get(i) * l.get(j));
                    }
                }
            }
        }
    }
}
