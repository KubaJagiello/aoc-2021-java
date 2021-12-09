package se.jakub.day9;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;
import static java.util.Comparator.reverseOrder;
import static java.util.stream.Collectors.toList;
import static se.jakub.reader.DayReader.inputReader;

public class Main {
    public static void main(String[] args) {
        System.out.println(partOne()); // 522
        System.out.println(partTwo()); // 916688
    }

    private static int partOne() {
        var l = inputReader(9, 1).collect(toList());
        var moves = getAllDirections();
        var s = 0;

        for (int i = 0; i < l.size(); i++) {
            for (int j = 0; j < l.get(0).length(); j++) {
                var lowest = true;
                for (var m : moves) {
                    if (isValid(l, i + m.x(), j + m.y()) && !(l.get(i + m.x()).charAt(j + m.y()) > l.get(i).charAt(j))) {
                        lowest = false;
                        break;
                    }
                }
                if (lowest) {
                    s += parseInt(valueOf(l.get(i).charAt(j))) + 1;
                }
            }
        }
        return s;
    }

    private static int partTwo() {
        var l = inputReader(9, 2).collect(toList());
        var moves = getAllDirections();
        var lowest = new ArrayList<Point>();

        for (int i = 0; i < l.size(); i++) {
            for (int j = 0; j < l.get(0).length(); j++) {
                var low = true;
                for (var m : moves) {
                    if (isValid(l, i + m.x(), j + m.y()) && !(l.get(i + m.x()).charAt(j + m.y()) > l.get(i).charAt(j))) {
                        low = false;
                        break;
                    }
                }
                if (low) {
                    lowest.add(new Point(i, j));
                }
            }
        }

        return lowest.stream()
                .map(b -> floodFill(l, b.x(), b.y()))
                .sorted(reverseOrder())
                .limit(3)
                .reduce(1, (a, b) -> a * b);
    }

    static int floodFill(List<String> l, int i, int j) {
        var moves = getAllDirections();
        var queue = new LinkedList<Point>();
        var set = new HashSet<Point>();
        var c = 0;

        queue.addFirst(new Point(i, j));
        set.add(new Point(i, j));

        while (!queue.isEmpty()) {
            var p = queue.removeFirst();
            if ('9' > l.get(p.x()).charAt(p.y())) {
                c++;
            }
            for (var m : moves) {
                var newP = new Point(m.x() + p.x(), m.y() + p.y());
                if (isValid(l, p.x() + m.x(), p.y() + m.y()) && l.get(newP.x()).charAt(newP.y()) < '9' && !set.contains(newP)) {
                    queue.addLast(newP);
                    set.add(newP);
                }
            }
        }
        return c;
    }

    private static List<Point> getAllDirections() {
        return List.of(new Point(-1, 0), new Point(1, 0), new Point(0, 1), new Point(0, -1));
    }

    private static boolean isValid(List<String> l, int i, int j) {
        return i >= 0 && j >= 0 && i < l.size() && j < l.get(0).length();
    }
}

record Point(int x, int y) {
}
