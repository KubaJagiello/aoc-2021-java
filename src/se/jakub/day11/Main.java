package se.jakub.day11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;

import static java.util.stream.Collectors.toList;
import static se.jakub.reader.DayReader.inputReader;


public class Main {
    public static void main(String[] args) {
        System.out.println(partOne()); // 1562
        System.out.println(partTwo()); // 268
    }

    private static int partOne() {
        var l = inputReader(11, 1).collect(toList());
        var octopus = new HashMap<Point, Integer>();
        var moves = getPossibleMoves();
        int width = 0;
        int height = 0;

        for (int y = 0; y < l.size(); y++) {
            int x = 0;
            for (var c : l.get(y).split("")) {
                octopus.put(new Point(x, y), Integer.parseInt(c));
                x++;
            }
            width = x;
            height = y + 1;
        }
        var flashes = 0;

        for (int steps = 0; steps < 100; steps++) {
            var nines = new LinkedList<Point>();
            octopus.replaceAll((k, v) -> octopus.get(k) + 1);
            octopus.keySet().stream().filter(key -> octopus.get(key) == 10).forEach(nines::addLast);
            flashes += nines.size();

            while (!nines.isEmpty()) {
                var o = nines.pop();
                octopus.put(o, 0);

                for (var move : moves) {
                    var newX = o.x + move.x;
                    var newY = o.y + move.y;

                    if (newX >= 0 && newY >= 0 && newX < width && newY < height) {
                        var n = new Point(newX, newY);
                        if (octopus.get(n) == 0) {
                            continue;
                        }
                        if (octopus.get(n) == 9) {
                            octopus.put(n, 0);
                            nines.addLast(n);
                            flashes++;
                        } else {
                            octopus.put(n, octopus.get(n) + 1);
                        }
                    }
                }
            }
        }
        return flashes;
    }

    private static int partTwo() {
        var l = inputReader(11, 2).collect(toList());
        var octopus = new HashMap<Point, Integer>();
        var moves = getPossibleMoves();
        int width = 0;
        int height = 0;

        for (int y = 0; y < l.size(); y++) {
            int x = 0;
            for (var c : l.get(y).split("")) {
                octopus.put(new Point(x, y), Integer.parseInt(c));
                x++;
            }
            width = x;
            height = y + 1;
        }

        for (int steps = 0; steps < 1000000; steps++) {
            var nines = new LinkedList<Point>();
            octopus.replaceAll((k, v) -> octopus.get(k) + 1);
            octopus.keySet().stream().filter(key -> octopus.get(key) == 10).forEach(nines::addLast);

            while (!nines.isEmpty()) {
                var o = nines.pop();
                octopus.put(o, 0);

                for (var move : moves) {
                    var newX = o.x + move.x;
                    var newY = o.y + move.y;

                    if (newX >= 0 && newY >= 0 && newX < width && newY < height) {
                        var n = new Point(newX, newY);
                        if (octopus.get(n) == 0) {
                            continue;
                        }
                        if (octopus.get(n) == 9) {
                            octopus.put(n, 0);
                            nines.addLast(n);
                        } else {
                            octopus.put(n, octopus.get(n) + 1);
                        }
                    }
                }
            }
            var s = 0;
            for (var key : octopus.keySet()) {
                if (octopus.get(key) == 0) {
                    s++;
                }
            }

            if (s == octopus.size()) {
                return steps + 1;
            }
        }
        return 1337;
    }

    private static ArrayList<Point> getPossibleMoves() {
        var moves = new ArrayList<Point>();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                moves.add(new Point(i, j));
            }
        }
        return moves;
    }
}

class Point {
    int x;
    int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
