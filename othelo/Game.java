package othelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Game {
    public static final String BLACK_SQUARE = "X";
    public static final String WHITE_SQUARE = "O";
    public static final int BLACK_VALUE = 1;
    public static final int WHITE_VALUE = -1;
    public static final int EMPTY_VALUE = 0;
    public static final int BOARD_SIZE = 8;

    public int[][] gameBoard;
    public int currentPlayer;
    public int scoreBlack;
    public int scoreWhite;

    private int passCounter;
    private boolean[][] suggestionBoard;

    // Constructors
    public Game() {
        this.gameBoard = new int[BOARD_SIZE][BOARD_SIZE];
        this.suggestionBoard = new boolean[BOARD_SIZE][BOARD_SIZE];
        this.currentPlayer = BLACK_VALUE;
        this.passCounter = 0;
        this.scoreBlack = 2;
        this.scoreWhite = 2;
        this.gameBoard[3][3] = 1;
        this.gameBoard[3][4] = -1;
        this.gameBoard[4][3] = -1;
        this.gameBoard[4][4] = 1;
    }

    static public boolean isInArrayBound(int row, int col) {
        return row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE;
    }

    // Methods
    public void start() throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            // Display board
            updateSuggesstionBoard();
            if (this.passCounter <= 1) {
                showBoard();
            }

            // Check win condition
            int winner = checkWinCondition();
            if (winner != 0) {
                System.out.println("Winner is " + (winner == BLACK_VALUE ? BLACK_SQUARE : WHITE_SQUARE));
                break;
            }

            if (this.passCounter == 0) {
                // Print Game info
                System.out.printf("%s - B[%d] - W[%d] - Pass[%d]\n",
                        this.currentPlayer == BLACK_VALUE ? BLACK_SQUARE : WHITE_SQUARE, this.scoreBlack,
                        this.scoreWhite, this.passCounter);

                // Input
                System.out.print("Next Row: ");
                int nextRow = Integer.parseInt(input.readLine());
                System.out.print("Next Col: ");
                int nextCol = Integer.parseInt(input.readLine());

                // Strike
                if (!strike(nextRow, nextCol)) {
                    System.out.println("Coordination is invalid");
                }
            }

            this.switchPlayer();
        }
    }

    public void showBoard() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            System.out.println("Print Sleep interupted");
        }

        // Clear console
        System.out.print("\033[H\033[2J");
        System.out.flush();

        for (int i = -1; i < BOARD_SIZE; i++) {
            for (int j = -1; j < BOARD_SIZE; j++) {
                if (i == -1 && j != -1) {
                    System.out.printf("%3d", j);
                } else if (j >= 0) {
                    if (this.gameBoard[i][j] != EMPTY_VALUE) {
                        String charToPrint = "";
                        switch (this.gameBoard[i][j]) {
                        case BLACK_VALUE:
                            charToPrint = BLACK_SQUARE;
                            break;
                        case WHITE_VALUE:
                            charToPrint = WHITE_SQUARE;
                            break;
                        }

                        System.out.printf("%3s", charToPrint);
                    } else {
                        if (this.suggestionBoard[i][j]) {
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

    public void updateSuggesstionBoard() {
        boolean pass = true;
        this.suggestionBoard = new boolean[BOARD_SIZE][BOARD_SIZE];

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (isValid(i, j, false)) {
                    this.suggestionBoard[i][j] = true;
                    pass = false;
                }
            }
        }

        if (pass) {
            this.passCounter++;
        } else {
            this.passCounter = 0;
        }
    }

    public void switchPlayer() {
        if (this.currentPlayer == BLACK_VALUE) {
            this.currentPlayer = WHITE_VALUE;
        } else {
            this.currentPlayer = BLACK_VALUE;
        }
    }

    public int getNextPlayer() {
        return (this.currentPlayer == BLACK_VALUE ? WHITE_VALUE : BLACK_VALUE);
    }

    public boolean strike(int row, int col) {
        if (isValid(row, col, true)) {
            // Strike!
            this.gameBoard[row][col] = this.currentPlayer;
            if (this.currentPlayer == BLACK_VALUE) {
                this.scoreBlack++;
            } else {
                this.scoreWhite++;
            }


            return true;
        }

        return false;
    }

    public boolean isValid(int row, int col, boolean flip) {
        boolean isValidMove = false;

        for (int dirRow = -1; dirRow <= 1; dirRow++) {
            for (int dirCol = -1; dirCol <= 1; dirCol++) {
                if (isInArrayBound(row + dirRow, col + dirCol) && this.gameBoard[row][col] == EMPTY_VALUE) {
                    // Advance
                    int advRow = row;
                    int advCol = col;
                    int k = 0;
                    do {
                        k++;
                        advRow += dirRow;
                        advCol += dirCol;
                    } while (isInArrayBound(advRow, advCol) && this.gameBoard[advRow][advCol] == this.getNextPlayer());

                    if (isInArrayBound(advRow, advCol) && this.gameBoard[advRow][advCol] == this.currentPlayer
                            && k > 1) {
                        // This is a valid move
                        isValidMove = true;

                        // Flip squares
                        if (flip) {
                            // Score from flip
                            for (int r = row + dirRow, c = col + dirCol, l = 1; l < k; r += dirRow, c += dirCol, l++) {
                                this.gameBoard[r][c] = this.currentPlayer;
                                if (this.currentPlayer == BLACK_VALUE) {
                                    this.scoreBlack++;
                                    this.scoreWhite--;
                                } else {
                                    this.scoreBlack--;
                                    this.scoreWhite++;
                                }
                            }
                        }

                    }
                }
            }
        }

        return isValidMove;
    }

    public int checkWinCondition() {
        if (this.passCounter >= 2) {
            return this.currentPlayer;
        }

        if (this.scoreBlack == 0) {
            return WHITE_VALUE;
        }

        if (this.scoreWhite == 0) {
            return BLACK_VALUE;
        }

        return EMPTY_VALUE;
    }
}
