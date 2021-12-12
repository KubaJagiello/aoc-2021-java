package se.jakub.day12;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Character.isUpperCase;
import static java.util.stream.Collectors.toList;
import static se.jakub.reader.DayReader.inputReader;


public class Main {
    public static void main(String[] args) {
        System.out.println(partOne()); // 4378
        System.out.println(partTwo()); // 133621
    }

    private static int partOne() {
        var graph = new HashMap<String, HashSet<String>>();

        for (var line : inputReader(12, 1).collect(toList())) {
            var split = line.split("-");
            var a = split[0];
            var b = split[1];

            if (!graph.containsKey(a)) {
                graph.put(a, new HashSet<>());
            }
            if (!graph.containsKey(b)) {
                graph.put(b, new HashSet<>());
            }

            graph.get(a).add(b);
            graph.get(b).add(a);
        }

        var q = new LinkedList<PathInfo>();
        for (var n : graph.get("start")) {
            q.addLast(new PathInfo(n, new HashSet<>(List.of(n)), 0));
        }

        var answer = 0;
        while (!q.isEmpty()) {
            var n = q.pop();

            if (n.s.equals("end")) {
                answer++;
                continue;
            }

            for (var neighbour : graph.get(n.s)) {
                if (neighbour.equals("start")) {
                    continue;
                }

                var tmp = new HashSet<>(n.visitedPath);
                if (isUpperCase(neighbour.charAt(0))) {
                    q.addLast(new PathInfo(neighbour, tmp, 0));
                } else {
                    if (!n.visitedPath.contains(neighbour)) {
                        tmp.add(neighbour);
                        q.addLast(new PathInfo(neighbour, tmp, 0));
                    }
                }
            }
        }
        return answer;
    }

    private static int partTwo() {
        var graph = new HashMap<String, HashSet<String>>();

        for (var line : inputReader(12, 2).collect(toList())) {
            var split = line.split("-");
            var a = split[0];
            var b = split[1];

            if (!graph.containsKey(a)) {
                graph.put(a, new HashSet<>());
            }
            if (!graph.containsKey(b)) {
                graph.put(b, new HashSet<>());
            }

            graph.get(a).add(b);
            graph.get(b).add(a);
        }

        var q = new LinkedList<PathInfo>();
        for (var n : graph.get("start")) {
            q.addLast(new PathInfo(n, new HashSet<>(List.of(n)), 0));
        }

        var answer = 0;
        while (!q.isEmpty()) {
            var n = q.pop();

            if (n.s.equals("end")) {
                answer++;
                continue;
            }

            for (var neighbour : graph.get(n.s)) {
                if (neighbour.equals("start")) {
                    continue;
                }

                var tmp = new HashSet<>(n.visitedPath);
                if (isUpperCase(neighbour.charAt(0))) {
                    q.addLast(new PathInfo(neighbour, tmp, n.smallCaveVisits));
                } else {
                    if (!n.visitedPath.contains(neighbour)) {
                        tmp.add(neighbour);
                        q.addLast(new PathInfo(neighbour, tmp, n.smallCaveVisits));
                    } else {
                        if (n.smallCaveVisits == 0) {
                            tmp.add(neighbour);
                            q.addLast(new PathInfo(neighbour, tmp, 1));
                        }
                    }
                }
            }
        }
        return answer;
    }
}

class PathInfo {
    String s;
    HashSet<String> visitedPath;
    int smallCaveVisits;

    public PathInfo(String s, HashSet<String> visitedPath, int smallCaveVisits) {
        this.s = s;
        this.visitedPath = visitedPath;
        this.smallCaveVisits = smallCaveVisits;
    }
}