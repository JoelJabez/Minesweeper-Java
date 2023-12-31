package minesweeper.game;

public class MinesweeperUtils {
    final static int GRID_SIZE = 9;

    private MinesweeperUtils() {
    }

    public static int getIntegerFromUserInput(String userInput) {
        try {
            return Integer.parseInt(userInput);
        } catch (NumberFormatException nfe) {
            System.out.println("Please enter an integer");
            return (int) Double.NEGATIVE_INFINITY;
        }
    }

    public static int getCoordinate(String coordinate) {
        int number = getIntegerFromUserInput(coordinate);
        if (number == (int) Double.NEGATIVE_INFINITY) {
            return number;
        }
        return number - 1;
    }

    public static boolean isNumberOfMinesInRange(int numberOfMines) {
        return numberOfMines > 0 && numberOfMines <= Math.pow(GRID_SIZE, 2);
    }

    static boolean isCoordinateInRange(int coordinate) {
        return coordinate >= 0 && coordinate < GRID_SIZE;
    }

    static boolean isInRange(int coordinate) {
        return coordinate < 0 || coordinate >= GRID_SIZE;
    }

    public static boolean isArgumentsEqualToThree(String input) {
        return MinesweeperUtils.splitArguments(input).length == 3;
    }

    static boolean isDigit(char[][] board, int yCoordinate, int xCoordinate) {
        return Character.isDigit(board[yCoordinate][xCoordinate]);
    }

    public static String[] splitArguments(String input) {
        input = input.strip();
        return input.split("\\s+");
    }

    public static String getInputFromIndex(String input, int index) {
        return splitArguments(input)[index].toLowerCase();
    }

    public static boolean isCoordinateValid(int coordinate) {
        return coordinate != (int) Double.NEGATIVE_INFINITY && isCoordinateInRange(coordinate);
    }
}
