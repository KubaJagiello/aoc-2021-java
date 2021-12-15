package se.jakub.day15;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;
import static se.jakub.reader.DayReader.inputReader;


public class Main {
    public static void main(String[] args) {
        System.out.println(partOneAndTwo(1, 1)); // 652
        System.out.println(partOneAndTwo(5, 5)); // 2938
    }

    private static int partOneAndTwo(int xTiles, int yTiles) {
        var m = inputReader(15, 2).collect(toList());
        var xMoves = new int[]{0, 0, 1, -1};
        var yMoves = new int[]{1, -1, 0, 0};

        var queue = new PriorityQueue<>(Comparator.comparingInt(Point::pathCost));
        queue.add(new Point(0, 0, 0));

        var actualWidth = m.get(0).length();
        var actualHeight = m.size();
        var width = actualWidth * xTiles;
        var height = actualHeight * yTiles;

        int[][] cost = new int[height][width];
        range(0, height).forEach(i -> Arrays.fill(cost[i], Integer.MAX_VALUE));

        while (!queue.isEmpty()) {
            var p = queue.remove();

            if (p.x() == width - 1 && p.y() == height - 1) {
                break;
            }

            for (int i = 0; i < xMoves.length; i++) {
                var newX = p.x() + xMoves[i];
                var newY = p.y() + yMoves[i];

                if (newX >= 0 && newY >= 0 && newX < width && newY < height) {
                    var n = parseInt(valueOf(m.get(newY % actualHeight).charAt(newX % actualWidth)));
                    n += newX / actualWidth + newY / actualHeight;

                    if (n > 9) {
                        n = (n % 10) + 1;
                    }

                    if (cost[newY][newX] > (p.pathCost() + n)) {
                        queue.add(new Point(newX, newY, p.pathCost() + n));
                        cost[newY][newX] = p.pathCost() + n;
                    }
                }
            }
        }
        return cost[height - 1][width - 1];
    }
}

record Point(int x, int y, int pathCost) {
}