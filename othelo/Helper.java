package othelo;

public class Helper {
    static public boolean isInArrayBound(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }
}