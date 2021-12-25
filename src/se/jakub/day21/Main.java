package se.jakub.day21;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

import static java.util.stream.Collectors.toList;
import static se.jakub.reader.DayReader.inputReader;


public class Main {
    public static void main(String[] args) {
        System.out.println(partOne()); // 1298
        System.out.println(partTwo());
    }

    private static int partOne() {
        /*
            Player 1 starting position: 8
            Player 2 starting position: 4
         */
        var aPoints = 0;
        var aPos = 7;
        var bPoints = 0;
        var bPos = 3;
        var dice = new Dice();
        var numberOfThrows = 0;
        while (true) {
            for (int i = 0; i < 3; i++) {
                aPos = (aPos + dice.throwDice()) % 10;
            }
            numberOfThrows += 3;
            aPoints += aPos + 1;

            if (aPoints >= 1000) {

                return bPoints * numberOfThrows;
            }

            for (int i = 0; i < 3; i++) {
                bPos = (bPos + dice.throwDice()) % 10;
            }
            numberOfThrows += 3;
            bPoints += bPos + 1;

            if (bPoints >= 1000) {
                return aPoints * numberOfThrows;
            }

        }
    }


    private static int partTwo() {
        /*
            Player 1 starting position: 8
            Player 2 starting position: 4
         */
        var aPoints = 0;
        var aPos = 7;
        var bPoints = 0;
        var bPos = 3;
        var dice = new DiracDice();
        var numberOfThrows = 0;
        // [aPos][bPos][dice]
//                                                                       [7,3]
//                      [8,3]                                            [9,3]
//      [9,3]          [10,3]           [1,3]            [10,3]          [1,3]           [2,3]
//[10,3][1,3][2,3] [1,3][2,3][3,3] [2,3][3,3][4,3]  [1,3][2,3][3,3] [2,3][3,3][4,3] [3,3][4,3][5,3]

        var mem = new HashMap<GameState, Integer>();

        while (true) {
            for (int i = 0; i < 3; i++) {
                aPos = (aPos + dice.throwDice()) % 10;
            }
            numberOfThrows += 3;
            aPoints += aPos + 1;

            if (aPoints >= 21) {

                return bPoints * numberOfThrows;
            }

            for (int i = 0; i < 3; i++) {
                bPos = (bPos + dice.throwDice()) % 10;
            }
            numberOfThrows += 3;
            bPoints += bPos + 1;

            if (bPoints >= 21) {
                return aPoints * numberOfThrows;
            }

        }
    }

    static GameState solvePartTwo(Player a, Player b, HashSet<GameState> mem) {
        if (a.points >= 21) {
            return new GameState(1, 0, a, b);
        }
        if (b.points >= 21) {
            return new GameState(0, 1, a, b);
        }

        var gameState = new GameState(0, 0, a,b);
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                for (int k = 1; k <= 3; k++) {
                    var newA = new Player(a.pos, a.points);
                    newA.pos = (newA.pos + i + j + k) % 10;
                    newA.points += newA.pos + 1;
                    var g = solvePartTwo(newA, b, mem);
                    gameState.aWon += g.aWon;
                    gameState.bWon += g.bWon;

                }
            }
        }

        mem.put(gameState)

        return gameState;
    }
}


class Player {
    int pos;
    int points;

    public Player(int pos, int points) {
        this.pos = pos;
        this.points = points;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return pos == player.pos && points == player.points;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pos, points);
    }
}

class GameState {
    int aWon;
    int bWon;
    Player a;
    Player b;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameState gameState = (GameState) o;
        return aWon == gameState.aWon && bWon == gameState.bWon && Objects.equals(a, gameState.a) && Objects.equals(b, gameState.b);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aWon, bWon, a, b);
    }

    public GameState(int aWon, int bWon, Player a, Player b) {
        this.aWon = aWon;
        this.bWon = bWon;
        this.a = a;
        this.b = b;
    }
}

class DiracDice {
    int value;

    DiracDice() {
        value = 0;
    }

    int throwDice() {
        if (value == 3) {
            value = 1;
        } else {
            value++;
        }
        return value;
    }
}

class Dice {
    int value;

    Dice() {
        value = 0;
    }

    int throwDice() {
        if (value == 100) {
            value = 1;
        } else {
            value++;
        }
        return value;
    }
}
