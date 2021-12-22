package se.jakub.day20;

import java.util.HashSet;

import static java.lang.Integer.parseInt;
import static java.util.stream.Collectors.toList;
import static se.jakub.reader.DayReader.inputReader;


public class Main {
    public static void main(String[] args) {
        partOneAndTwo(); //
    }

    private static void partOneAndTwo() {
        var l = inputReader(20, 2).collect(toList());
        int minX = 0;
        int minY = 0;
        int maxX = l.get(2).length();
        int maxY = l.size() - 2;

        var algo = l.get(0);
        var image = new HashSet<Point>();
        for (int y = 2; y < l.size(); y++) {
            var line = l.get(y);
            for (int x = 0; x < line.length(); x++) {
                var light = line.charAt(x) == '#';
                if (light)
                    image.add(new Point(x, y - 2));
            }
        }

        for (int iterations = 0; iterations < 50; iterations++) {
            var newImage = new HashSet<Point>();
            // increase by 1 in each direction since we ignore the infinitely switching lights
            minX -= 1;
            minY -= 1;
            maxX += 1;
            maxY += 1;

            for (int y = minY; y < maxY; y++) {
                for (int x = minX; x < maxX; x++) {
                    var str = new StringBuilder();
                    for (int j = y - 1; j < y + 2; j++) {
                        for (int k = x - 1; k < x + 2; k++) {
                            var p = new Point(k, j);
                            if (iterations % 2 == 0) {
                                str.append(image.contains(p) ? "1" : "0");
                            } else {
                                str.append(!image.contains(p) ? "1" : "0");
                            }
                        }
                    }
                    var d = algo.charAt(parseInt(str.toString(), 2));

                    // prepare for next iteration, so that each iter has only finite lights
                    if (iterations % 2 == 0 && d != '#') {
                        newImage.add(new Point(x, y));
                    } else if (iterations % 2 == 1 && d == '#') {
                        newImage.add(new Point(x, y));
                    } /*
                    if ((d == '#') != ((iterations % 2) != 0)) {
                        newImage.add(new Point(x, y));
                    }*/
                }
            }

            if (iterations == 2)
                System.out.printf("part1: %d\n", image.size());
            image = newImage;
        }
        System.out.printf("part2: %d\n", image.size());
    }

    private static void printImage(HashSet<Point> image) {
        int minX = 0;
        int minY = 0;
        int maxX = 0;
        int maxY = 0;
        for (var entry : image) {
            var x = entry.x();
            var y = entry.y();
            minX = Math.min(minX, x);
            maxX = Math.max(maxX, x);
            minY = Math.min(minY, y);
            maxY = Math.max(maxY, y);
        }
        for (int y = minY - 1; y < maxY + 2; y++) {
            for (int x = minX - 1; x < maxX + 2; x++) {
                var c = image.contains(new Point(x, y));
                System.out.print(c ? "#" : ".");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("---------------------");
        System.out.println();
    }

    private static int partTwo() {
        var l = inputReader(1, 2).collect(toList());
        return 0;
    }
}

record Point(int x, int y) {
}