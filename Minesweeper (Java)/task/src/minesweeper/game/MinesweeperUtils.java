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
        if (coordinatesInString.length != 3) {
            System.out.println("Please enter two coordinates");
            return (int) Double.NEGATIVE_INFINITY;
        }

        int number = getIntegerFromUserInput(coordinatesInString[index]);
        if (number == (int) Double.NEGATIVE_INFINITY) {
            return number;
        }
        return number - 1;
    }
}
