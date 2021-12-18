package se.jakub.day18;

import java.util.LinkedList;

import static java.lang.Integer.*;
import static java.util.stream.Collectors.toList;
import static se.jakub.reader.DayReader.inputReader;


public class Main {
    public static void main(String[] args) {
        System.out.println(partOne()); // 3494
        System.out.println(partTwo()); // 4712
    }

    private static int partTwo() {
        var l = inputReader(18, 1).collect(toList());
        var maxMagnitude = 0;

        var list = new LinkedList<String>();

        for (int x = 1; x < l.size(); x++) {
            for (int y = 0; y < l.size(); y++) {
                if (x == y)
                    continue;

                var hehe = String.format("[%s,%s]", l.get(x), l.get(y));

                list = new LinkedList<>();
                for (var c : hehe.toCharArray()) {
                    list.addLast(String.valueOf(c));
                }

                boolean shouldExplode;
                boolean shouldSplit;

                do {
                    shouldExplode = false;
                    shouldSplit = false;
                    var currentIndex = 0;
                    var currentDepth = 0;


                    for (int i = 0; i < list.size(); i++) {
                        var c = list.get(i);
                        if (c.equals("[")) {
                            currentDepth++;
                            currentIndex++;
                            continue;
                        } else if (c.equals("]")) {
                            currentDepth--;
                            currentIndex++;
                            continue;
                        } else if (c.equals(",")) {
                            currentIndex++;
                            continue;
                        }

                        if (currentDepth > 4) {
                            shouldExplode = true;
                            break;
                        }
                        currentIndex++;
                    }

                    if (shouldExplode) {
                        var a = parseInt(list.get(currentIndex));
                        var b = parseInt(list.get(currentIndex + 2));

                        for (int i = currentIndex - 1; i >= 0; i--) {
                            if (!list.get(i).equals(",") && !list.get(i).equals("[") && !list.get(i).equals("]")) {
                                var number = parseInt(list.remove(i));
                                list.add(i, String.valueOf(a + number));
                                break;
                            }
                        }

                        // add right
                        for (int i = currentIndex + 3; i < list.size(); i++) {
                            if (!list.get(i).equals(",") && !list.get(i).equals("[") && !list.get(i).equals("]")) {
                                var number = parseInt(list.remove(i));
                                list.add(i, String.valueOf(b + number));
                                break;
                            }
                        }
                        list.remove(currentIndex - 1); // [
                        list.remove(currentIndex - 1); // x
                        list.remove(currentIndex - 1); // ,
                        list.remove(currentIndex - 1); // y
                        list.remove(currentIndex - 1); // ]
                        list.add(currentIndex - 1, "0");
                        continue;
                    }

                    shouldExplode = false;
                    shouldSplit = false;
                    currentIndex = 0;
                    currentDepth = 0;

                    for (var c : list) {
                        if (c.equals("[")) {
                            currentDepth++;
                            currentIndex++;
                            continue;
                        } else if (c.equals("]")) {
                            currentDepth--;
                            currentIndex++;
                            continue;
                        } else if (c.equals(",")) {
                            currentIndex++;
                            continue;
                        }

                    /*if (currentDepth > 4) {
                        shouldExplode = true;
                        break;
                    } */
                        if (parseInt(c) > 9) {
                            shouldSplit = true;
                            break;
                        }
                        currentIndex++;
                    }

                    if (shouldSplit) {
                        var n = parseInt(list.get(currentIndex));
                        int a = n / 2;
                        int b = n - a;
                        list.remove(currentIndex);
                        list.add(currentIndex, "]");
                        list.add(currentIndex, String.valueOf(b));
                        list.add(currentIndex, ",");
                        list.add(currentIndex, String.valueOf(a));
                        list.add(currentIndex, "[");
                    }
                    //printList(list);
                } while (shouldExplode || shouldSplit);


                while (true) {
                    if (list.size() == 3) {
                        maxMagnitude = Math.max(maxMagnitude, Integer.parseInt(list.get(1)));
                        break;
                    }
                    if (list.size() == 1) {
                        maxMagnitude = Math.max(maxMagnitude, Integer.parseInt(list.get(0)));
                        break;
                    }
                    for (int i = 1; i < list.size() - 1; i++) {
                        var a = list.get(i - 1);
                        var b = list.get(i);
                        var c = list.get(i + 1);
                        if (b.equals(",") && !"[]".contains(a) && !"[]".contains(c)) {
                            var nA = Integer.parseInt(a) * 3;
                            var nC = Integer.parseInt(c) * 2;
                            var m = nA + nC;
                            list.remove(i - 2); // [
                            list.remove(i - 2); // x
                            list.remove(i - 2); // ,
                            list.remove(i - 2); // y
                            list.remove(i - 2); // ]
                            list.add(i - 2, String.valueOf(m));
                            break;
                        }
                    }
                }

            }
        }
        return maxMagnitude;
    }

    private static int partOne() {
        var l = inputReader(18, 1).collect(toList());

        var list = new LinkedList<String>();
        for (var c : l.get(0).toCharArray()) {
            list.addLast(String.valueOf(c));
        }

        for (int k = 1; k < l.size(); k++) {
            var line = l.get(k);

            var tmpStr = new StringBuilder();
            for (String s : list) {
                tmpStr.append(s);
            }

            var hehe = String.format("[%s,%s]", tmpStr, line);

            list = new LinkedList<>();
            for (var c : hehe.toCharArray()) {
                list.addLast(String.valueOf(c));
            }

            boolean shouldExplode;
            boolean shouldSplit;

            do {
                shouldExplode = false;
                shouldSplit = false;
                var currentIndex = 0;
                var currentDepth = 0;

                for (var c : list) {
                    if (c.equals("[")) {
                        currentDepth++;
                        currentIndex++;
                        continue;
                    } else if (c.equals("]")) {
                        currentDepth--;
                        currentIndex++;
                        continue;
                    } else if (c.equals(",")) {
                        currentIndex++;
                        continue;
                    }

                    if (currentDepth > 4) {
                        shouldExplode = true;
                        break;
                    }
                    currentIndex++;
                }

                if (shouldExplode) {
                    var a = parseInt(list.get(currentIndex));
                    var b = parseInt(list.get(currentIndex + 2));

                    for (int i = currentIndex - 1; i >= 0; i--) {
                        if (!list.get(i).equals(",") && !list.get(i).equals("[") && !list.get(i).equals("]")) {
                            var number = parseInt(list.remove(i));
                            list.add(i, String.valueOf(a + number));
                            break;
                        }
                    }

                    // add right
                    for (int i = currentIndex + 3; i < list.size(); i++) {
                        if (!list.get(i).equals(",") && !list.get(i).equals("[") && !list.get(i).equals("]")) {
                            var number = parseInt(list.remove(i));
                            list.add(i, String.valueOf(b + number));
                            break;
                        }
                    }
                    list.remove(currentIndex - 1); // [
                    list.remove(currentIndex - 1); // x
                    list.remove(currentIndex - 1); // ,
                    list.remove(currentIndex - 1); // y
                    list.remove(currentIndex - 1); // ]
                    list.add(currentIndex - 1, "0");
                    continue;
                }

                shouldExplode = false;
                shouldSplit = false;
                currentIndex = 0;
                currentDepth = 0;

                for (var c : list) {
                    if (c.equals("[")) {
                        currentDepth++;
                        currentIndex++;
                        continue;
                    } else if (c.equals("]")) {
                        currentDepth--;
                        currentIndex++;
                        continue;
                    } else if (c.equals(",")) {
                        currentIndex++;
                        continue;
                    }

                    if (parseInt(c) > 9) {
                        shouldSplit = true;
                        break;
                    }
                    currentIndex++;
                }

                if (shouldSplit) {
                    var n = parseInt(list.get(currentIndex));
                    int a = n / 2;
                    int b = n - a;
                    list.remove(currentIndex);
                    list.add(currentIndex, "]");
                    list.add(currentIndex, String.valueOf(b));
                    list.add(currentIndex, ",");
                    list.add(currentIndex, String.valueOf(a));
                    list.add(currentIndex, "[");
                }
            } while (shouldExplode || shouldSplit);
        }
        var magnitude = 0;


        while (true) {
            if (list.size() == 3) {
                magnitude = Integer.parseInt(list.get(1));
                break;
            }
            if (list.size() == 1) {
                magnitude = Integer.parseInt(list.get(0));
                break;
            }
            for (int i = 1; i < list.size() - 1; i++) {
                var a = list.get(i - 1);
                var b = list.get(i);
                var c = list.get(i + 1);
                if (b.equals(",") && !"[]".contains(a) && !"[]".contains(c)) {
                    var nA = Integer.parseInt(a) * 3;
                    var nC = Integer.parseInt(c) * 2;
                    var m = nA + nC;
                    list.remove(i - 2); // [
                    list.remove(i - 2); // x
                    list.remove(i - 2); // ,
                    list.remove(i - 2); // y
                    list.remove(i - 2); // ]
                    list.add(i - 2, String.valueOf(m));
                    break;
                }
            }
        }

        return magnitude;

    }
}
