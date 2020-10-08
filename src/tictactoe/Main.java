package tictactoe;

import java.util.Scanner;

public class Main {

    private static char[][] cells = new char[][]{{' ', ' ', ' '},
            {' ', ' ', ' '},
            {' ', ' ', ' '}};

    private static int round = 1;

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);

        while (getState().equals(State.GAME_ON)) {
            sout();

            String x, y;
            do {
                System.out.print("Enter coordinates: ");
                x = scanner.next();
                y = scanner.next();
            } while (!checkMove(x, y));
            placeChar(x, y);
        }
        sout();
        System.out.println(getState().message);
    }

    private static boolean checkMove(String a, String b) {
        try {
            int x = Integer.parseInt(a) - 1;
            int y = Integer.parseInt(b) - 1;
            if (x > 2 || x < 0 || y > 2 || y < 0) {
                System.out.println("Coordinates should be from 1 to 3!");
                return false;
            } else if (cells[2 - y][x % 3] != ' ') {
                System.out.println("This cell is occupied! Choose another one!");
                return false;
            }
        } catch (Exception e) {
            System.out.println("You should enter numbers!");
            System.out.println(e);
            return false;
        }

        return true;
    }

    private static void placeChar(String a, String b) {
        int x = Integer.parseInt(a) - 1;
        int y = Integer.parseInt(b) - 1;
        if (round % 2 != 0) {
            cells[2 - y][x % 3] = 'X';
        } else {
            cells[2 - y][x % 3] = 'O';
        }
        round++;
    }

    enum State {
        DRAW("Draw"),
        X_WINS("X wins"),
        O_WINS("O wins"),
        GAME_ON("Game not finished"),
        IMPOSSIBRU("Impossible");

        private final String message;

        State(String message) {
            this.message = message;
        }

        String getMessage() {
            return this.message;
        }
    }

    private static State getState() {

        int xCounter = 0;
        int oCounter = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (cells[i][j] == 'X') {
                    xCounter++;
                }
                if (cells[i][j] == 'O') {
                    oCounter++;
                }
            }
        }
        if (Math.abs(xCounter - oCounter) > 1) {
            return State.IMPOSSIBRU;
        }

        int found = 0;
        int w = 0;
        String win = "";


        for (int i = 0; i < 3; i++) {
            if (cells[i][0] != ' ' && cells[i][0] == cells[i][1] && cells[i][0] == cells[i][2]) {
                found++;
                win = "row";
                w = i;
            }
            if (cells[0][i]  != ' ' && cells[0][i] == cells[1][i] && cells[0][i] == cells[2][i]) {
                found++;
                win = "col";
                w = i;
            }
        }
        if (cells[0][0]  != ' ' && cells[0][0] == cells[1][1] && cells[0][0] == cells[2][2]) {
            found++;
            win = "dia";
        }
        if (cells[0][2]  != ' ' && cells[0][2] == cells[1][1] && cells[0][2] == cells[2][0]) {
            found++;
            win = "neg";
        }

        if (found > 1) {
            return State.IMPOSSIBRU;
        }
        switch (win) {
            case "row":
                return cells[w][0] == 'X' ? State.X_WINS : State.O_WINS;
            case "col":
                return cells[0][w] == 'X' ? State.X_WINS : State.O_WINS;
            case "dia":
                return cells[0][0] == 'X' ? State.X_WINS : State.O_WINS;
            case "neg":
                return cells[0][2] == 'X' ? State.X_WINS : State.O_WINS;
            default:
                return xCounter + oCounter == 9 ? State.DRAW : State.GAME_ON;
        }
    }


    private static void sout() {
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(cells[i][j] + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }
}
