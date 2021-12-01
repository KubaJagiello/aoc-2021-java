package se.jakub.day1;

import java.io.FileNotFoundException;

import static java.util.stream.Collectors.toList;
import static se.jakub.DayReader.inputReader;


public class Main {
    public static void main(String[] args) {
        System.out.println(partOne()); // 1298
        System.out.println(partTwo()); // 1248
    }

    private static int partOne() {
        var l = inputReader(1, 1).map(Integer::parseInt).collect(toList());

        var c = 0;
        for (int i = 1; i < l.size(); i++) {
            if (l.get(i) > l.get(i - 1)) {
                c++;
            }
        }
        return c;
    }

    private static int partTwo() {
        var l = inputReader(1, 2).map(Integer::parseInt).collect(toList());

        var c = 0;
        for (int i = 2; i < l.size() - 1; i++) {
            if ((l.get(i - 2) + l.get(i - 1) + l.get(i)) < (l.get(i - 1) + l.get(i) + l.get(i + 1))) {
                c++;
            }
        }
        return c;
    }
}
