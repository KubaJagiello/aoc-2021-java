package se.jakub.day21;

import java.util.HashMap;
import java.util.Objects;


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
            dd
         */
        /* TEST CASE
        Player 1 starting position: 4
        Player 2 starting position: 8

         */
        var aPos = 8;
        var bPos = 4;
        // [aPos][bPos][dice]
//                                                                       [7,3]
//                      [8,3]                                            [9,3]
//      [9,3]          [10,3]           [1,3]            [10,3]          [1,3]           [2,3]
//[10,3][1,3][2,3] [1,3][2,3][3,3] [2,3][3,3][4,3]  [1,3][2,3][3,3] [2,3][3,3][4,3] [3,3][4,3][5,3]

        var mem = new HashMap<GameState, GamesWon>();
        var playerA = new Player(aPos - 1, 0);
        var playerB = new Player(bPos - 1, 0);
        var r = solvePartTwo(new GameState(playerA, playerB, true), mem, true);
        System.out.println(r.aWon + "," + r.bWon);
        return 0;
    }

    static GamesWon solvePartTwo(GameState gameState, HashMap<GameState, GamesWon> mem, boolean playerA) {
        if (gameState.a.points >= 21) {
            return new GamesWon(1, 0);
        }
        if (gameState.b.points >= 21) {
            return new GamesWon(0, 1);
        }

        if (mem.containsKey(gameState)) {
            return mem.get(gameState);
        }
        var wins = new GamesWon(0, 0);
        var state = new GameState(gameState.a, gameState.b, playerA);

        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                for (int k = 1; k <= 3; k++) {
                    if (playerA) {
                        var newAPos = (gameState.a.pos + k + i + j) % 10;
                        var newA = new Player(newAPos, gameState.a.points + newAPos + 1);
                        var newB = new Player(gameState.b.pos, gameState.b.points);
                        var newGameState = new GameState(newA, newB, false);
                        var r = solvePartTwo(newGameState, mem, false);
                        wins.aWon += r.aWon;
                        wins.bWon += r.bWon;
                    } else {
                        var newBPos = (gameState.b.pos + k + i + j) % 10;
                        var newA = new Player(gameState.a.pos, gameState.a.points);
                        var newB = new Player(newBPos, gameState.b.points + newBPos + 1);
                        var newGameState = new GameState(newA, newB, true);
                        var r = solvePartTwo(newGameState, mem, true);
                        wins.aWon += r.aWon;
                        wins.bWon += r.bWon;
                    }
                }
            }
        }

        mem.put(state, wins);
        return wins;
    }
}

class GameState {
    Player a;
    Player b;
    boolean playerA;

    public GameState(Player a, Player b, boolean playerA) {
        this.a = a;
        this.b = b;
        this.playerA = playerA;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameState gameState = (GameState) o;
        return playerA == gameState.playerA && Objects.equals(a, gameState.a) && Objects.equals(b, gameState.b);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b, playerA);
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


class GamesWon {
    long aWon;
    long bWon;

    public GamesWon(int aWon, int bWon) {
        this.aWon = aWon;
        this.bWon = bWon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GamesWon gameState = (GamesWon) o;
        return aWon == gameState.aWon && bWon == gameState.bWon;
    }

    @Override
    public int hashCode() {
        return Objects.hash(aWon, bWon);
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
