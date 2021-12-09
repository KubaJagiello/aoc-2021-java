package se.jakub.day10;

import static java.util.stream.Collectors.toList;
import static se.jakub.reader.DayReader.inputReader;


public class Main {
    public static void main(String[] args) {
        System.out.println(partOne()); //
        System.out.println(partTwo()); //
    }

    private static int partOne() {
        var l = inputReader(10, 1).collect(toList());
        return 0;
    }

    private static int partTwo() {
        var l = inputReader(10, 2).collect(toList());
        return 0;
    }
}
