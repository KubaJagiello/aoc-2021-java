package se.jakub.day17;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;

import static java.lang.Math.*;
import static java.util.stream.Collectors.toList;
import static se.jakub.reader.DayReader.inputReader;


public class Main {
    public static void main(String[] args) {
        System.out.println(partOne()); // 8646
        System.out.println(partTwo()); // 5945
    }

    private static int partOne() {
        var l = inputReader(1, 1).collect(toList());

        //var x1 = 20;
        //var x2 = 30;
        //var y1 = -10;
        //var y2 = -5;
        var x1 = 155;
        var x2 = 215;
        var y1 = -132;
        var y2 = -72;

        var queue = new LinkedList<Velocity>();
        queue.add(new Velocity(0, 0));
        var tested = new HashSet<Velocity>();
        var distinct = new HashSet<Velocity>();

        var bestY = 0;
        while (!queue.isEmpty()) {
            var velocity = queue.pop();
            var a = new Velocity(velocity.x + 1, velocity.y);
            var b = new Velocity(velocity.x, velocity.y + 1);
            var c = new Velocity(velocity.x + 1, velocity.y + 1);
            var d = new Velocity(velocity.x, velocity.y - 1);
            var e = new Velocity(velocity.x + 1, velocity.y - 1);

            if (!tested.contains(a)) {
                queue.add(a);
                tested.add(a);
            }
            if (!tested.contains(b)) {
                queue.add(b);
                tested.add(b);
            }
            if (!tested.contains(c)) {
                queue.add(c);
                tested.add(c);
            }
            if (!tested.contains(d)) {
                queue.add(d);
                tested.add(d);
            }
            if (!tested.contains(e)) {
                queue.add(e);
                tested.add(e);
            }

            int x = 0;
            int y = 0;
            int xVel = velocity.x;
            int yVel = velocity.y;
            int tmpBestY = 0;

            while (true) {
                tmpBestY = max(tmpBestY, y);
                if (x >= x1 && x <= x2 && y >= y1 && y <= y2) {
                    distinct.add(velocity);
                    System.out.printf("dist %d\n", distinct.size());
                    if (tmpBestY > bestY) {
                        bestY = tmpBestY;
                        System.out.printf("%d\n", bestY);
                    }
                    break;
                }

                if (x > max(x1, x2) || y < min(y1, y2)) {
                    break;
                }

                x += xVel;
                y += yVel;
                if (xVel > 0) {
                    xVel--;
                } else if (xVel < 0) {
                    xVel++;
                }
                yVel--;
            }
        }
        return 0;
    }

    private static int partTwo() {
        var l = inputReader(1, 2).map(Integer::parseInt).collect(toList());

        return 0;
    }
}

class Velocity {
    int x;
    int y;

    public Velocity(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        Velocity velocity = (Velocity) o;
        return x == velocity.x && y == velocity.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
