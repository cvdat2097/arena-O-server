package othelo;

public class IO {
    public static final String BLACK_SQUARE = "X";
    public static final String WHITE_SQUARE = "O";
    public static final int BOARD_SIZE  = 8;

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}