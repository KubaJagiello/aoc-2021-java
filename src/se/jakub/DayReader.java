package se.jakub;

import java.io.*;

import static java.lang.String.format;

public class DayReader {
    private final static String DIRECTORY_PATH = "input/%d/%d";

    public static Reader inputReader(int day, int part) throws FileNotFoundException {
        return new BufferedReader(new FileReader(format(DIRECTORY_PATH, day, part)));
    }

}
