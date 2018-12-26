package othelo;

public class Helper {
    static public boolean isInArrayBound(int row, int col) {
        return row >= 0 && row < IO.BOARD_SIZE && col >= 0 && col < IO.BOARD_SIZE;
    }
}