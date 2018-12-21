package othelo;

public class IO {
    public static final String BLACK_SQUARE = "B";
    public static final String WHITE_SQUARE = "W";

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}