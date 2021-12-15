package se.jakub.day15;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;
import static se.jakub.reader.DayReader.inputReader;


public class Main {
    public static void main(String[] args) {
        System.out.println(partOne()); // 652
        System.out.println(partTwo()); // 2938
    }

    private static int partOne() {
        var m = inputReader(15, 2).collect(toList());

        var moves = List.of(
                new Point(1, 0),
                new Point(0, 1),
                new Point(0, -1)
        );

        var queue = new LinkedList<Point>();
        var cost = new HashMap<Point, Integer>();
        queue.addFirst(new Point(0, 0));

        while (!queue.isEmpty()) {
            var p = queue.removeFirst();

            for (var move : moves) {
                var newX = p.x + move.x;
                var newY = p.y + move.y;

                if (newX >= 0 && newY >= 0 && newX < m.get(0).length() && newY < m.size()) {
                    var currentCost = cost.getOrDefault(new Point(newX, newY), Integer.MAX_VALUE);
                    var n = Integer.parseInt(String.valueOf(m.get(newY).charAt(newX)));

                    if (currentCost > (p.pathCost + n)) {
                        var newP = new Point(newX, newY);
                        newP.pathCost = p.pathCost + n;
                        cost.put(newP, newP.pathCost);
                        queue.addLast(newP);
                    }
                }
            }
        }

        var maxX = m.get(0).length() - 1;
        var maxY = m.size() - 1;

        return cost.get(new Point(maxX, maxY));
    }

    private static int partTwo() {
        var m = inputReader(15, 2).collect(toList());

        var moves = List.of(
                new Point(1, 0),
                new Point(0, 1),
                new Point(0, -1),
                new Point(-1, 0)
        );

        var queue = new LinkedList<Point>();
        var cost = new HashMap<Point, Integer>();
        queue.addFirst(new Point(0, 0));

        var actualWidth = m.get(0).length();
        var actualHeight = m.size();

        var width = m.get(0).length() * 5;
        var height = m.size() * 5;

        while (!queue.isEmpty()) {
            var p = queue.removeFirst();

            for (var move : moves) {
                var newX = p.x + move.x;
                var newY = p.y + move.y;

                if (newX >= 0 && newY >= 0 && newX < width && newY < height) {
                    var wrappedX = newX % actualWidth;
                    var wrappedY = newY % actualHeight;

                    var currentCost = cost.getOrDefault(new Point(newX, newY), Integer.MAX_VALUE);
                    var n = Integer.parseInt(String.valueOf(m.get(wrappedY).charAt(wrappedX)));

                    n += newX / actualWidth;
                    n += newY / actualHeight;

                    while (n > 9) {
                        n -= 9;
                    }

                    if (currentCost > (p.pathCost + n)) {
                        var newP = new Point(newX, newY);
                        newP.pathCost = p.pathCost + n;
                        cost.put(newP, newP.pathCost);
                        queue.addLast(newP);
                    }
                }
            }
        }
        return cost.get(new Point(width - 1, height - 1));
    }
}

class Point {
    int x;
    int y;
    int pathCost;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        pathCost = 0;
    }

    @Override
    public boolean equals(Object o) {
        Point point = (Point) o;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}