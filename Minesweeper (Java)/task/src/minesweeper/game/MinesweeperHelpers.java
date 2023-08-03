package minesweeper.game;

class MinesweeperHelpers {
    public final static int GRID_SIZE = 9;

    public static String[] splitArguments(String input) {
        input = input.strip();
        return input.split("\\s+");
    }

    public static String getInputFromIndex(String input, int index) {
        return splitArguments(input)[index].toLowerCase();
    }

    public static boolean isCoordinateInRange(int coordinate) {
        if (!MinesweeperUtils.isCoordinateInRange(coordinate)) {
            System.out.println("Please enter the coordinates in the range of " + GRID_SIZE);
            return false;
        }
        return true;
    }

    public static boolean isCoordinateValid(int coordinate) {
        return coordinate != (int) Double.NEGATIVE_INFINITY && isCoordinateInRange(coordinate);
    }
}
