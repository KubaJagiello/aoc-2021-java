package se.jakub.day4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.iterate;
import static java.util.stream.IntStream.range;
import static se.jakub.reader.DayReader.inputReader;

public class Main {
    public static void main(String[] args) {
        System.out.println(partOne()); // 58412
        System.out.println(partTwo()); // 10030
    }

    private static int partOne() {
        var l = inputReader(4, 1).filter(v -> !v.isEmpty()).collect(toList());
        var numbers = getBingoNumbers(l);
        var boards = createBoards(l);

        var allMarks = range(0, boards.size())
                .mapToObj(i -> new boolean[25])
                .collect(toCollection(ArrayList::new));

        for (var number : numbers) {
            for (int b = 0; b < boards.size(); b++) {
                var board = boards.get(b);
                var marks = allMarks.get(b);

                for (int i = 0; i < board.size(); i++) {
                    if (board.get(i) == number) {
                        marks[i] = true;
                    }
                }

                int r = 0;
                int c = 0;

                while (r < 25) {
                    var rc = (int) range(r, r + 5).filter(i -> marks[i]).count();
                    var cc = (int) iterate(c, j -> j < 25, j -> j + 5).filter(j -> marks[j]).count();

                    if (rc == 5 || cc == 5) {
                        return number * range(0, 25).filter(i -> !marks[i]).map(board::get).sum();
                    }

                    r += 5;
                    c++;
                }
            }
        }
        return 1337;
    }

    private static int partTwo() {
        var l = inputReader(4, 2).filter(v -> !v.isEmpty()).collect(toList());
        var numbers = getBingoNumbers(l);
        var boards = createBoards(l);

        var allMarks = range(0, boards.size())
                .mapToObj(i -> new boolean[25])
                .collect(toCollection(ArrayList::new));

        var boardsLeft = range(0, boards.size()).boxed().collect(toCollection(HashSet::new));
        for (var number : numbers) {
            for (int b = 0; b < boards.size(); b++) {
                var board = boards.get(b);
                var marks = allMarks.get(b);

                for (int i = 0; i < board.size(); i++) {
                    if (board.get(i) == number) {
                        marks[i] = true;
                    }
                }

                int r = 0;
                int c = 0;

                while (r < 25) {
                    var rc = (int) range(r, r + 5).filter(i -> marks[i]).count();
                    var cc = (int) iterate(c, j -> j < 25, j -> j + 5).filter(j -> marks[j]).count();

                    if (rc == 5 || cc == 5) {
                        if (boardsLeft.remove(b) && boardsLeft.isEmpty()) {
                            return number * range(0, 25).filter(i -> !marks[i]).map(board::get).sum();
                        }
                    }

                    r += 5;
                    c++;
                }
            }
        }
        return 1337;
    }

    private static List<Integer> getBingoNumbers(List<String> l) {
        return Arrays.stream(l.get(0).split(","))
                .map(Integer::parseInt)
                .collect(toList());
    }

    private static ArrayList<ArrayList<Integer>> createBoards(List<String> l) {
        var boards = new ArrayList<ArrayList<Integer>>();
        for (int i = 1; i < l.size(); i += 5) {
            var board = new ArrayList<Integer>();
            for (int j = i; j < i + 5; j++) {
                board.addAll(Arrays.stream(l.get(j).split(" "))
                        .filter(v -> !v.equals(""))
                        .map(Integer::parseInt)
                        .collect(toList()));
            }
            boards.add(board);
        }
        return boards;
    }
}
