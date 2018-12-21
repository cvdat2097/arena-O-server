package othelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Game {
    public static int[][] gameBoard = new int[8][8];
    public static int currentPlayer = 1;

    public static void init() {
        gameBoard[3][3] = 1;
        gameBoard[3][4] = -1;
        gameBoard[4][3] = -1;
        gameBoard[4][4] = 1;
    }

    public static void start() throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        showBoard();
        while (true) {
            System.out.println(currentPlayer == 1 ? "O" : "X");
            System.out.print("Next Row: ");
            int nextRow = Integer.parseInt(input.readLine());
            System.out.print("Next Col: ");
            int nextCol = Integer.parseInt(input.readLine());

            System.err.println(strike(nextRow, nextCol));
            showBoard();
        }
    }

    public static void showBoard() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            System.out.println("Print Sleep interupted");
        }
        IO.clearScreen();
        
        for (int i = -1; i < 8; i++) {
            for (int j = -1; j < 8; j++) {
                if (i == -1 && j != -1) {
                    System.out.printf("%3d", j);
                } else if (j >= 0) {
                    if (gameBoard[i][j] != 0) {
                        String charToPrint = "";
                        switch (gameBoard[i][j]) {
                        case 1:
                            charToPrint = "O";
                            break;
                        case -1:
                            charToPrint = "X";
                            break;
                        case 3:
                            charToPrint = ".";
                            break;
                        }

                        System.out.printf("%3s", charToPrint);
                    } else {
                        if (Game.isValid(i, j, false)) {
                            System.out.printf("%3s", ".");
                        } else {
                            System.out.printf("%3s", " ");
                        }
                    }
                } else {
                    System.out.printf(i == -1 ? "   " : "%3d", i);
                }
            }

            System.out.println();
        }
    }

    public static void flipChessmen(int prevRow, int prevCol) {

    }

    public static boolean strike(int row, int col) {
        if (isValid(row, col, true)) {
            // Strike!
            gameBoard[row][col] = currentPlayer;
            currentPlayer *= -1;
            return true;
        }

        return false;
    }

    public static boolean isValid(int row, int col, boolean flip) {
        boolean isValidMove = false;

        for (int dirRow = -1; dirRow <= 1; dirRow++) {
            for (int dirCol = -1; dirCol <= 1; dirCol++) {
                if (Helper.isInArrayBound(row + dirRow, col + dirCol) && gameBoard[row][col] == 0) {
                    // Advance
                    int advRow = row;
                    int advCol = col;
                    int k = 0;
                    do {
                        k++;
                        advRow += dirRow;
                        advCol += dirCol;
                    } while (Helper.isInArrayBound(advRow, advCol) && gameBoard[advRow][advCol] == currentPlayer * -1);

                    if (Helper.isInArrayBound(advRow, advCol) && gameBoard[advRow][advCol] == currentPlayer && k > 1) {
                        // This is a valid move
                        isValidMove = true;

                        // Flip squares
                        if (flip) {
                            for (int r = row + dirRow, c = col + dirCol, l = 1; l < k; r += dirRow, c += dirCol, l++) {
                                gameBoard[r][c] = currentPlayer;
                            }
                        }

                    }
                }
            }
        }

        if (isValidMove) {

            return true;
        }

        return false;
    }
}