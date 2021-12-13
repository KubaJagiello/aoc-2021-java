package se.jakub.day13;

import java.util.HashSet;

import static java.lang.Integer.parseInt;
import static java.util.stream.Collectors.toList;
import static se.jakub.reader.DayReader.inputReader;


public class Main {
    public static void main(String[] args) {
        partOneAndTwo(); // 781 and PERCGJPB
    }

    private static void partOneAndTwo() {
        var l = inputReader(13, 1).collect(toList());
        var i = 0;
        var points = new HashSet<Point>();

        while (true) {
            var line = l.get(i);
            if (line.equals("")) {
                i++;
                break;
            }
            var split = line.split(",");
            points.add(new Point(parseInt(split[0]), parseInt(split[1])));
            i++;
        }

        var partOne = true;
        while (i < l.size()) {
            var line = l.get(i).split(" ");
            var m = line[2].split("=")[0];
            var f = parseInt(line[2].split("=")[1]);
            var newSet = new HashSet<Point>();

            for (var point : points) {
                if (m.equals("x")) {
                    if (point.x() > f) {
                        newSet.add(new Point(point.x() - (2 * ((point.x() - f))), point.y()));
                    } else {
                        newSet.add(point);
                    }
                } else {
                    if (point.y() > f) {
                        newSet.add(new Point(point.x(), point.y() - (2 * ((point.y() - f)))));
                    } else {
                        newSet.add(point);
                    }
                }
            }
            points = newSet;

            if (partOne) {
                System.out.printf("Part 1: %d\n", points.size());
                partOne = false;
            }
            i++;
        }
        System.out.println("Part 2:");
        for (int j = 0; j < 6; j++) {
            for (int k = 0; k < 40; k++) {
                if (points.contains(new Point(k, j))) {
                    System.out.print("#");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}

record Point(int x, int y) {
}