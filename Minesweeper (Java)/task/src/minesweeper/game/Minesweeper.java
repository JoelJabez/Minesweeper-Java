package minesweeper.game;

import java.awt.Point;
import java.util.*;

public class Minesweeper {
    static final int GRID;
    private static final int X_COORDINATE_INDEX;
    private static final int Y_COORDINATE_INDEX;
    private static final char BOARD_SYMBOL_UNDISCOVERED;
    private static final char BOARD_SYMBOL_DISCOVERED;
    private static final char BOARD_SYMBOL_FLAG;

    HashMap<Integer, List<Integer>> coordinates;
    private int correctlyPlacedMines;
    private int numberOfFlagsPlaced;
    private int numberOfMines;
    char[][] BOARD;

    static {
        GRID = 9;
        X_COORDINATE_INDEX = 0;
        Y_COORDINATE_INDEX = 1;
        BOARD_SYMBOL_UNDISCOVERED = '.';
        BOARD_SYMBOL_DISCOVERED = '/';
        BOARD_SYMBOL_FLAG = '*';
    }

    {
        BOARD = new char[GRID][GRID];

        coordinates = new HashMap<>();
        numberOfFlagsPlaced = 0;
    }

    private void setNumberOfMines(int numberOfMines) {
        this.numberOfMines = numberOfMines;
    }

    public void generateNewBoard(int numberOfMines) {
        for (int i = 0; i < GRID; i++) {
            Arrays.fill(BOARD[i], BOARD_SYMBOL_UNDISCOVERED);
        }
        setNumberOfMines(numberOfMines);
        placeMines();
    }

    private void placeMines() {
        Random random = new Random();
        int tempNumberOfMines = numberOfMines;

        do {
            List<Integer> xCoordinates = new ArrayList<>();
            int yCoordinate = random.nextInt(GRID);
            int xCoordinate = random.nextInt(GRID);

            if (!isMineMarked(yCoordinate, xCoordinate)) {
                coordinates.putIfAbsent(yCoordinate, xCoordinates);
                coordinates.get(yCoordinate).add(xCoordinate);

                tempNumberOfMines--;
            }
        } while (tempNumberOfMines != 0);
    }

    void placeCounterOfSurroundingMines() {
        for (int horizontal = 0; horizontal < GRID; horizontal++) {
            for (int vertical = 0; vertical < GRID; vertical++) {
                int counter;
                if (!isMineMarked(horizontal, vertical)) {
                    counter = checkSurrounding(horizontal, vertical);
                    if (counter != 0) {
                        BOARD[horizontal][vertical] = Character.forDigit(counter, 10);
                    }
                }
            }
        }
    }

    public void printBoard() {
        System.out.println();
        printTopGrid();

        for (int horizontal = 0; horizontal < GRID; horizontal++) {
            System.out.printf("%d|", horizontal + 1);
            for (int vertical = 0; vertical < GRID; vertical++) {
                System.out.print(BOARD[horizontal][vertical]);
            }
            System.out.println("|");
        }

        printBottomGrid();
    }

    private void printTopGrid() {
        System.out.print(" |");
        for (int i = 1; i <= GRID; i++) {
            System.out.print(i);
        }
        System.out.println("|");

        printBottomGrid();
    }

    private void printBottomGrid() {
        System.out.print("-|");
        for (int i = 1; i <= GRID; i++) {
            System.out.print("-");
        }
        System.out.println("|");
    }

    private void addFlag(int yCoordinate, int xCoordinate) {
        BOARD[yCoordinate][xCoordinate] = BOARD_SYMBOL_FLAG;
        numberOfFlagsPlaced++;
    }

    private void removeFlag(int yCoordinate, int xCoordinate) {
        BOARD[yCoordinate][xCoordinate] = BOARD_SYMBOL_UNDISCOVERED;
        if (isMineMarked(yCoordinate, xCoordinate)) {
            correctlyPlacedMines--;
        }
        numberOfFlagsPlaced--;
    }

    int checkSurrounding(int horizontal, int vertical) {
        int counter = 0;
        for (int i = horizontal - 1; i <= horizontal + 1; i++) {
            if (0 <= i && i < GRID) {
                for (int j = vertical - 1; j <= vertical + 1; j++) {
                    if ((0 <= j && j < GRID) && isMineMarked(i, j)) {
                        counter++;
                    }
                }
            }
        }
        return counter;
    }

    // did unit test for this already
    public boolean setCoordinates(String coordinates) {
        if (coordinates.split(" ").length != 3) {
            System.out.println("Please provide two coordinates and an action");
            return false;
        }

        int yCoordinate = MinesweeperUtils.getCoordinate(coordinates, Y_COORDINATE_INDEX);
        if (isXCoordinateNotValid(yCoordinate)) {
            return false;
        }

        int xCoordinate = MinesweeperUtils.getCoordinate(coordinates, X_COORDINATE_INDEX);
        if (isYCoordinateNotValid(xCoordinate)) {
            return false;
        }

        String action = coordinates.split(" ")[2];
        switch (action) {
            case "free" -> {
                return isFloodFilled(yCoordinate, xCoordinate);
            }
            case "mine" -> {
                return isFlagPlaced(yCoordinate, xCoordinate);
            }
            default -> {
                System.out.println("Please enter two coordinates and an action(either free or mine)");
                return false;
            }
        }
    }

    public boolean isNotANeighbouringMine(int yCoordinate, int xCoordinate) {
        if (isDigit(yCoordinate, xCoordinate)) {
            System.out.println("There is a number here!");
            return false;
        }

        return true;
    }

    public boolean isGameNotOver() {
        correctlyPlacedMines = 0;
        for (int i = 0; i < GRID; i++) {
            for (int j = 0; j < GRID; j++) {
                if (BOARD[i][j] == '*' && isMineMarked(i, j)) {
                    correctlyPlacedMines++;
                }
            }
        }

        return !(correctlyPlacedMines == numberOfFlagsPlaced && correctlyPlacedMines == numberOfMines);
    }

    // Unit test this already
    boolean isMineMarked(int yCoordinate, int xCoordinate) {
        if (coordinates.containsKey(yCoordinate)) {
            return coordinates.get(yCoordinate).contains(xCoordinate);
        }
        return false;
    }

    boolean isNotInRange(int coordinate) {
        return 0 > coordinate || coordinate >= GRID;
    }

    // Unit test this already
    boolean isNotInRange(int coordinate, boolean isX) {
        if (isNotInRange(coordinate)) {
            if (isX) {
                System.out.println("Please enter the X coordinates in the range of " + GRID);
            } else {
                System.out.println("Please enter the Y coordinates in the range of " + GRID);
            }
            return false;
        }
        return true;
    }

    // Unit test this already
    boolean isXCoordinateNotValid(int coordinate) {
        return coordinate == (int) Double.NEGATIVE_INFINITY || isNotInRange(coordinate, true);
    }

    boolean isYCoordinateNotValid(int coordinate) {
        return coordinate == (int) Double.NEGATIVE_INFINITY || isNotInRange(coordinate, false);
    }

    boolean isFlagPlaced(int yCoordinate, int xCoordinate) {
        if (isNotANeighbouringMine(yCoordinate, xCoordinate)) {
            if (BOARD[yCoordinate][xCoordinate] == BOARD_SYMBOL_UNDISCOVERED) {
                addFlag(yCoordinate, xCoordinate);
            } else {
                removeFlag(yCoordinate, xCoordinate);
            }
            return true;
        }
        return false;
    }

    boolean isFloodFilled(int yCoordinate, int xCoordinate) {
        Deque<Point> coordinates = new ArrayDeque<>();

        if (isMineMarked(yCoordinate, xCoordinate)) {
            // reveal all the mines here
            return false;
        }

        int numberOfSurroundingMines = checkSurrounding(yCoordinate, xCoordinate);
        if (numberOfSurroundingMines != 0) {
            BOARD[yCoordinate][xCoordinate] = Character.forDigit(numberOfSurroundingMines, 10);
            return true;
        }

        coordinates.push(new Point(xCoordinate, yCoordinate));
        BOARD[yCoordinate][xCoordinate] = BOARD_SYMBOL_DISCOVERED;

        while (!coordinates.isEmpty()) {
            Point coordinate = coordinates.pop();
            int yPosition = coordinate.y;
            int xPosition = coordinate.x;

            if (isValid(yPosition + 1, xPosition)) {
                openGrid(coordinates, yPosition + 1, xPosition);
            }

            if (isValid(yPosition - 1, xPosition)) {
                openGrid(coordinates, yPosition - 1, xPosition);
            }

            if (isValid(yPosition, xPosition + 1)) {
                openGrid(coordinates, yPosition, xPosition + 1);
            }

            if (isValid(yPosition, xPosition - 1)) {
                openGrid(coordinates, yPosition, xPosition - 1);
            }
        }
        return true;
    }

    boolean isValid(int yPosition, int xPosition) {
        if (isNotInRange(yPosition) || isNotInRange(xPosition)
                || BOARD[yPosition][xPosition] == BOARD_SYMBOL_DISCOVERED
                || isDigit(yPosition, xPosition) || isMineMarked(yPosition, xPosition)) {
            return false;
        }
        return true;
    }

    boolean isDigit(int yCoordinate, int xCoordinate) {
        return Character.isDigit(BOARD[yCoordinate][xCoordinate]);
    }

    void openGrid(Deque<Point> coordinates, int yCoordinate, int xCoordinate) {
        int numberOfSurroundingMines = checkSurrounding(yCoordinate, xCoordinate);
        if (numberOfSurroundingMines != 0) {
            BOARD[yCoordinate][xCoordinate] = Character.forDigit(numberOfSurroundingMines, 10);
        } else {
            BOARD[yCoordinate][xCoordinate] = BOARD_SYMBOL_DISCOVERED;
            coordinates.push(new Point(xCoordinate, yCoordinate));
        }
    }
}
