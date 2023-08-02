package minesweeper.game;

public class MinesweeperUtils {
    public static int getIntegerFromUserInput(String userInput) {
        try {
            return Integer.parseInt(userInput);
        } catch (NumberFormatException nfe) {
            System.out.println("Please enter an integer");
            return (int) Double.NEGATIVE_INFINITY;
        }
    }

    public static int getCoordinate(String coordinates, int index) {
        String[] coordinatesInString = coordinates.split(" ");
        int number = getIntegerFromUserInput(coordinatesInString[index]);
        if (number == (int) Double.NEGATIVE_INFINITY) {
            return number;
        }
        return number - 1;
    }

    public static boolean isGreaterThanZero(int numberOfMines) {
        return numberOfMines > 0;
    }

    public static boolean isInRange(int numberOfMines, int gridSize) {
        return numberOfMines > 0 && numberOfMines <= Math.pow(gridSize, 2);
    }

    static boolean isCoordinateInRange(int coordinate, int gridSize) {
        return coordinate >= 0 && coordinate < gridSize;
    }
}
