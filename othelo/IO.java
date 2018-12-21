package othelo;

public class IO {
    public static void printBoard(int[][] board) {
        for (int i = -1; i < 8; i++) {
            for (int j = -1; j < 8; j++) {
                if (i == -1 && j != -1) {
                    System.out.printf("%3d", j);
                } else if (j >= 0) {
                    if (board[i][j] != 0) {
                        String charToPrint = "";
                        switch (board[i][j]) {
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
                        System.out.printf("%3s", " ");
                    }
                } else {
                    System.out.printf(i == -1 ? "   " : "%3d", i);
                }
            }

            System.out.println();
        }
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}