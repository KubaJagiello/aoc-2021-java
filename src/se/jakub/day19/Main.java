package se.jakub.day19;

import java.util.*;

import static java.lang.Integer.*;
import static java.lang.Math.*;
import static java.util.stream.Collectors.toList;
import static se.jakub.reader.DayReader.inputReader;


public class Main {
    private static HashSet<Point> HMMM = new HashSet<>();
    private static List<Point> sensorsPos = new ArrayList<>();


    public static void main(String[] args) {
        System.out.println("part1: " + partOneAndTwo()); //
    }

    private static int partOneAndTwo() {
        var l = inputReader(19, 4).collect(toList());

        var scanners = new ArrayList<HashSet<Point>>();
        var currentSensorIndex = -1;
        for (var s : l) {
            if (s.equals("")) {
                continue;
            }
            if (s.contains("---")) {
                currentSensorIndex++;
                scanners.add(new HashSet<>());
                continue;
            }

            var splitted = s.split(",");
            var x = parseInt(splitted[0]);
            var y = parseInt(splitted[1]);
            var z = parseInt(splitted[2]);
            scanners.get(currentSensorIndex).add(new Point(x, y, z));
        }

        var permutations = new ArrayList<Tuple>();
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                for (int z = 0; z < 3; z++) {
                    if (x != y && x != z && y != z) {
                        permutations.add(new Tuple(x, y, z));
                    }
                }
            }
        }
        var a = scanners.remove(0);

        while (!scanners.isEmpty()) {
            for (int i = 0; i < scanners.size(); i++) {
                var b = scanners.get(i);
                var bPoints = getPointsThatMatch(a, b, permutations);
                if (bPoints != null) {
                    a.addAll(bPoints);
                    scanners.remove(i);
                    break;
                }
            }
        }

        double currentMaxDist = 0.0;
        for (int i = 0; i < sensorsPos.size(); i++) {
            for (int j = 0; j < sensorsPos.size(); j++) {
                if (i == j) {
                    continue;
                }
                var n = sensorsPos.get(i);
                var m = sensorsPos.get(j);
                var dist = abs(n.x - m.x) + abs(n.y - m.y) + abs(n.z - m.z);
                if (dist > currentMaxDist) {
                    currentMaxDist = dist;
                }
            }
        }
        System.out.println("part2: " + (int) currentMaxDist);
        return a.size() - HMMM.size();
    }

    static Set<Point> getPointsThatMatch(Set<Point> a, Set<Point> b, List<Tuple> permutations) {
        // go through each of the directions
        for (var p : permutations) {
            for (int x = -1; x < 2; x += 2) {
                for (int y = -1; y < 2; y += 2) {
                    for (int z = -1; z < 2; z += 2) {
                        var tmpRotatedPoints = new ArrayList<Point>();
                        // create new list with rotated points of B
                        for (var bPoint : b) {
                            var t = new int[]{bPoint.x, bPoint.y, bPoint.z};

                            var newX = t[p.i()] * x;
                            var newY = t[p.j()] * y;
                            var newZ = t[p.k()] * z;
                            tmpRotatedPoints.add(new Point(newX, newY, newZ));
                        }

                        var counter = new HashMap<Double, Integer>();
                        for (var aPoint : a) {
                            for (var bPoint : tmpRotatedPoints) {
                                var dist = distance(aPoint, bPoint);
                                counter.put(dist, 1 + counter.getOrDefault(dist, 0));
                                if (counter.get(dist) >= 12) {

                                    var realBx = bPoint.x * -1 + aPoint.x;
                                    var realBy = bPoint.y * -1 + aPoint.y;
                                    var realBz = bPoint.z * -1 + aPoint.z;

                                    sensorsPos.add(new Point(realBx, realBy, realBz));

                                    var realPositionsOfB = new HashSet<Point>();
                                    for (var pointB : tmpRotatedPoints) {
                                        var newX = pointB.x + realBx;
                                        var newY = pointB.y + realBy;
                                        var newZ = pointB.z + realBz;
                                        realPositionsOfB.add(new Point(newX, newY, newZ));
                                    }
                                    return realPositionsOfB;
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    static double distance(Point a, Point b) {
        return pow(pow(b.x - a.x, 2)
                + pow(b.y - a.y, 2)
                + pow(b.z - a.z, 2), 0.5);
    }

    private static int partTwo() {
        var l = inputReader(1, 2).collect(toList());
        return 0;
    }
}

class Point {
    int x;
    int y;
    int z;

    public Point(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y && z == point.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }
}

record Tuple(int i, int j, int k) {
}