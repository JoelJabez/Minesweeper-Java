package minesweeper.game;

import java.awt.Point;
import java.util.*;

public class Minesweeper {
    private static final int GRID;
    private static final int X_COORDINATE_INDEX;
    private static final int Y_COORDINATE_INDEX;
    private static final int ACTION_COORDINATE_INDEX;
    private static final char BOARD_SYMBOL_UNDISCOVERED;
    private static final char BOARD_SYMBOL_DISCOVERED;
    private static final char BOARD_SYMBOL_FLAG;

    HashMap<Integer, List<Integer>> coordinates;
    private int correctlyPlacedMines;
    private int numberOfFlagsPlaced;
    private int numberOfMines;
    char[][] board;

    static {
        GRID = MinesweeperHelpers.GRID_SIZE;
        X_COORDINATE_INDEX = 0;
        Y_COORDINATE_INDEX = 1;
        ACTION_COORDINATE_INDEX = 2;
        BOARD_SYMBOL_UNDISCOVERED = '.';
        BOARD_SYMBOL_DISCOVERED = '/';
        BOARD_SYMBOL_FLAG = '*';
    }

    {
        board = new char[GRID][GRID];

        coordinates = new HashMap<>();
        numberOfFlagsPlaced = 0;
    }

    // not this
    private void setNumberOfMines(int numberOfMines) {
        this.numberOfMines = numberOfMines;
    }

    // not this
    public void printBoard() {
        System.out.println();
        printTopGrid();

        for (int horizontal = 0; horizontal < GRID; horizontal++) {
            System.out.printf("%d|", horizontal + 1);
            for (int vertical = 0; vertical < GRID; vertical++) {
                System.out.print(board[horizontal][vertical]);
            }
            System.out.println("|");
        }

        printBottomGrid();
    }

    // not this
    private void printTopGrid() {
        System.out.print(" |");
        for (int i = 1; i <= GRID; i++) {
            System.out.print(i);
        }
        System.out.println("|");

        printBottomGrid();
    }

    // not this
    private void printBottomGrid() {
        System.out.print("-|");
        for (int i = 1; i <= GRID; i++) {
            System.out.print("-");
        }
        System.out.println("|");
    }

    // did unit test for this already
    public void generateNewBoard(int numberOfMines) {
        for (int i = 0; i < GRID; i++) {
            Arrays.fill(board[i], BOARD_SYMBOL_UNDISCOVERED);
        }
        setNumberOfMines(numberOfMines);
        placeMines();
    }

    // not this
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

    // not this
    void placeCounterOfSurroundingMines() {
        for (int horizontal = 0; horizontal < GRID; horizontal++) {
            for (int vertical = 0; vertical < GRID; vertical++) {
                if (!isMineMarked(horizontal, vertical)) {
                    isNumberOfSurroundingMinesSet(horizontal, vertical);
                }
            }
        }
    }

    void openGrid(Deque<Point> coordinates, int yCoordinate, int xCoordinate) {
        if (!isNumberOfSurroundingMinesSet(yCoordinate, xCoordinate)) {
            board[yCoordinate][xCoordinate] = BOARD_SYMBOL_DISCOVERED;
            coordinates.push(new Point(xCoordinate, yCoordinate));
        } else {
            isNumberOfSurroundingMinesSet(yCoordinate, xCoordinate);
        }
    }

    // not this
    private void addFlag(int yCoordinate, int xCoordinate) {
        board[yCoordinate][xCoordinate] = BOARD_SYMBOL_FLAG;
        numberOfFlagsPlaced++;
    }

    // not this
    private void removeFlag(int yCoordinate, int xCoordinate) {
        board[yCoordinate][xCoordinate] = BOARD_SYMBOL_UNDISCOVERED;
        if (isMineMarked(yCoordinate, xCoordinate)) {
            correctlyPlacedMines--;
        }
        numberOfFlagsPlaced--;
    }

    // write for this
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
    // redo this for unit test and maybe rename the method
    public boolean isCoordinatesSet(String coordinates) {
        if (!MinesweeperUtils.isArgumentsEqualToThree(coordinates)) {
            System.out.println("Please provide two coordinates and an action");
            return false;
        }

        String stringYCoordinate = MinesweeperHelpers.getInputFromIndex(coordinates, Y_COORDINATE_INDEX);
        String stringXCoordinate = MinesweeperHelpers.getInputFromIndex(coordinates, X_COORDINATE_INDEX);
        String action = MinesweeperHelpers.getInputFromIndex(coordinates, ACTION_COORDINATE_INDEX);

        int yCoordinate = MinesweeperUtils.getCoordinate(stringYCoordinate);
        if (!MinesweeperHelpers.isCoordinateValid(yCoordinate)) {
            return false;
        }

        int xCoordinate = MinesweeperUtils.getCoordinate(stringXCoordinate);
        if (!MinesweeperHelpers.isCoordinateValid(xCoordinate)) {
            return false;
        }

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

    // test this
    public boolean isNotANeighbouringMine(int yCoordinate, int xCoordinate) {
        if (MinesweeperUtils.isDigit(board, yCoordinate, xCoordinate)) {
            System.out.println("There is a number here!");
            return false;
        }

        return true;
    }

    // not sure how to test this but can try
    public boolean isGameNotOver() {
        correctlyPlacedMines = 0;
        for (int i = 0; i < GRID; i++) {
            for (int j = 0; j < GRID; j++) {
                if (board[i][j] == '*' && isMineMarked(i, j)) {
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

    // Unit test this already
    // Unit test this already
    boolean isFlagPlaced(int yCoordinate, int xCoordinate) {
        if (isNotANeighbouringMine(yCoordinate, xCoordinate)) {
            if (board[yCoordinate][xCoordinate] == BOARD_SYMBOL_UNDISCOVERED) {
                addFlag(yCoordinate, xCoordinate);
            } else {
                removeFlag(yCoordinate, xCoordinate);
            }
            return true;
        }
        return false;
    }

    // do unit test for this
    boolean isFloodFilled(int yCoordinate, int xCoordinate) {
        Deque<Point> coordinates = new ArrayDeque<>();

        if (isMineMarked(yCoordinate, xCoordinate)) {
            // reveal all the mines here
            return false;
        }

        if (isNumberOfSurroundingMinesSet(yCoordinate, xCoordinate)) {
            return true;
        }

        board[yCoordinate][xCoordinate] = BOARD_SYMBOL_DISCOVERED;
        coordinates.push(new Point(xCoordinate, yCoordinate));

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

    // unit test this
    private boolean isNumberOfSurroundingMinesSet(int horizontal, int vertical) {
        int numberOfSurroundingMines;
        numberOfSurroundingMines = checkSurrounding(horizontal, vertical);
        if (numberOfSurroundingMines != 0) {
            board[horizontal][vertical] = Character.forDigit(numberOfSurroundingMines, 10);
            return true;
        }
        return false;
    }

    // unit test this
    boolean isValid(int yCoordinate, int xCoordinate) {
        if (MinesweeperUtils.isInRange(yCoordinate) || MinesweeperUtils.isInRange(xCoordinate)
                || board[yCoordinate][xCoordinate] == BOARD_SYMBOL_DISCOVERED
                || MinesweeperUtils.isDigit(board, yCoordinate, xCoordinate)
                || isMineMarked(yCoordinate, xCoordinate)) {
            return false;
        }
        return true;
    }
}
