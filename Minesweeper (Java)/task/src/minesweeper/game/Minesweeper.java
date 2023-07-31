package minesweeper.game;

import java.util.*;

public class Minesweeper {
    char[][] BOARD;
    final int GRID;
    private final int X_COORDINATE_INDEX;
    private final int Y_COORDINATE_INDEX;
    private final char BOARD_SYMBOL_UNDISCOVERED;
    private final char BOARD_SYMBOL_FLAG;
    private int NUMBER_OF_MINES;

    HashMap<Integer, List<Integer>> coordinates;
    int numberOfFlagsPlaced;
    int correctlyPlacedMines;

    {
        GRID = 9;
        BOARD = new char[GRID][GRID];
        X_COORDINATE_INDEX = 0;
        Y_COORDINATE_INDEX = 1;
        BOARD_SYMBOL_UNDISCOVERED = '.';
        BOARD_SYMBOL_FLAG = '*';

        coordinates = new HashMap<>();
        numberOfFlagsPlaced = 0;
    }

    private void setNumberOfMines(int numberOfMines) {
        NUMBER_OF_MINES = numberOfMines;
    }

    public void generateNewBoard(int numberOfMines) {
        setNumberOfMines(numberOfMines);
        placeMines();
        placeCounterOfSurroundingMines();
    }

    private void placeMines() {
        Random random = new Random();
        int tempNumberOfMines = NUMBER_OF_MINES;

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
                if (counter == 0) {
                    BOARD[horizontal][vertical] = '.';
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
        if (validateCoordinate(yCoordinate, false)) {
            return false;
        }

        int xCoordinate = MinesweeperUtils.getCoordinate(coordinates, X_COORDINATE_INDEX);
        if (validateCoordinate(xCoordinate, true)) {
            return false;
        }

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


        return !(correctlyPlacedMines == numberOfFlagsPlaced && correctlyPlacedMines == NUMBER_OF_MINES);
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
    boolean validateCoordinate(int coordinate, boolean isX) {
        return coordinate == (int) Double.NEGATIVE_INFINITY || isNotInRange(coordinate, isX);
    }
}