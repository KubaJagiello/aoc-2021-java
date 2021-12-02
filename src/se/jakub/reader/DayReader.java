package se.jakub.reader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.stream.Stream;

import static java.lang.String.format;

public class DayReader {
    private final static String DIRECTORY_PATH = "input/%d/%d";

    public static Stream<String> inputReader(int day, int part) {
        try {
            return new BufferedReader(new FileReader(format(DIRECTORY_PATH, day, part))).lines();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("file not found", e);
        }
    }
}
