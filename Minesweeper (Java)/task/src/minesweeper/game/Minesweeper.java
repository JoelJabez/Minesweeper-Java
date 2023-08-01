package minesweeper.game;

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

            if (isNotMarked(yCoordinate, xCoordinate)) {
                coordinates.putIfAbsent(yCoordinate, xCoordinates);
                coordinates.get(yCoordinate).add(xCoordinate);

                tempNumberOfMines--;
            }
        } while (tempNumberOfMines != 0);
    }

    void placeCounterOfSurroundingMines() {
        for (int horizontal = 0; horizontal < GRID; horizontal++) {
            for (int vertical = 0; vertical < GRID; vertical++) {
                int counter = 0;
                if (isNotMarked(horizontal, vertical)) {
                    counter = checkSurrounding(horizontal, vertical, counter);
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
        if (!isNotMarked(yCoordinate, xCoordinate)) {
            correctlyPlacedMines--;
        }
        numberOfFlagsPlaced--;
    }

    int checkSurrounding(int horizontal, int vertical, int counter) {
        for (int i = horizontal - 1; i <= horizontal + 1; i++) {
            if (0 <= i && i < GRID) {
                for (int j = vertical - 1; j <= vertical + 1; j++) {
                    if ((0 <= j && j < GRID) && !isNotMarked(i, j)) {
                        counter++;
                    }
                }
            }
        }
        return counter;
    }

    // did unit test for this already
    public boolean setCoordinates(String coordinates) {
        int yCoordinate = MinesweeperUtils.getCoordinate(coordinates, Y_COORDINATE_INDEX);
        if (isXCoordinateNotValid(yCoordinate)) {
            return false;
        }

        int xCoordinate = MinesweeperUtils.getCoordinate(coordinates, X_COORDINATE_INDEX);
        if (isYCoordinateNotValid(xCoordinate)) {
            return false;
        }

        String action;
        if (coordinates.length() != 3) {
            System.out.println("Please provide two coordinates and an action");
            return false;
        }
        action = coordinates.split(coordinates)[2];
        switch (action) {
            case "free" -> {
                return true;
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
        if (Character.isDigit(BOARD[yCoordinate][xCoordinate])) {
            System.out.println("There is a number here!");
            return false;
        }

        return true;
    }

    public boolean isGameNotOver() {
        correctlyPlacedMines = 0;
        for (int i = 0; i < GRID; i++) {
            for (int j = 0; j < GRID; j++) {
                if (BOARD[i][j] == '*' && !isNotMarked(i, j)) {
                    correctlyPlacedMines++;
                }
            }
        }

        return !(correctlyPlacedMines == numberOfFlagsPlaced && correctlyPlacedMines == numberOfMines);
    }

    // Unit test this already
    boolean isNotMarked(int yCoordinate, int xCoordinate) {
        if (coordinates.containsKey(yCoordinate)) {
            return !coordinates.get(yCoordinate).contains(xCoordinate);
        }
        return true;
    }

    // Unit test this already
    boolean isNotInRange(int coordinate, boolean isX) {
        if (-1 >= coordinate || coordinate >= GRID) {
            if (isX) {
                System.out.println("Please enter the X coordinates in the range of " + GRID);
            } else {
                System.out.println("Please enter the Y coordinates in the range of " + GRID);
            }
            return true;
        }
        return false;
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
}
