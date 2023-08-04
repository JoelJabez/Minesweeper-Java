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
    private static final char BOARD_SYMBOL_MINE;

    HashMap<Integer, List<Integer>> coordinates;
    private int correctlyPlacedMines;
    private int numberOfUndiscoveredIndex;
    private int numberOfFlagsPlaced;
    private int numberOfMines;
    private boolean isPlayerWon;
    char[][] board;
    int foo;

    static {
        GRID = MinesweeperUtils.GRID_SIZE;
        X_COORDINATE_INDEX = 0;
        Y_COORDINATE_INDEX = 1;
        ACTION_COORDINATE_INDEX = 2;
        BOARD_SYMBOL_UNDISCOVERED = '.';
        BOARD_SYMBOL_DISCOVERED = '/';
        BOARD_SYMBOL_FLAG = '*';
        BOARD_SYMBOL_MINE = 'X';
    }

    {
        isPlayerWon = true;
        board = new char[GRID][GRID];

        coordinates = new HashMap<>();
        numberOfFlagsPlaced = 0;
        numberOfUndiscoveredIndex = 0;
    }

    private void setNumberOfMines(int numberOfMines) {
        foo = GRID;
        this.numberOfMines = numberOfMines;
    }

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

    public void printMessage() {
        if (isPlayerWon) {
            printBoard();
            System.out.println("Congratulations! You found all the mines!");
        } else {
            System.out.println("You stepped on a mine and failed!");
        }
    }

    public void generateNewBoard(int numberOfMines) {
        for (int i = 0; i < GRID; i++) {
            Arrays.fill(board[i], BOARD_SYMBOL_UNDISCOVERED);
        }
        setNumberOfMines(numberOfMines);
        placeMines();
    }

    void openGrid(Deque<Point> coordinates, int yCoordinate, int xCoordinate) {
        if (!isNumberOfSurroundingMinesSet(yCoordinate, xCoordinate)) {
            board[yCoordinate][xCoordinate] = BOARD_SYMBOL_DISCOVERED;
            coordinates.push(new Point(xCoordinate, yCoordinate));
        } else {
            isNumberOfSurroundingMinesSet(yCoordinate, xCoordinate);
        }
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

    private void revealAllMines() {
        coordinates.forEach((key, value) -> {
            for (Integer integer : value) {
                board[key][integer] = BOARD_SYMBOL_MINE;
            }
        });
    }

    private void addFlag(int yCoordinate, int xCoordinate) {
        board[yCoordinate][xCoordinate] = BOARD_SYMBOL_FLAG;
        numberOfFlagsPlaced++;
    }

    private void removeFlag(int yCoordinate, int xCoordinate) {
        board[yCoordinate][xCoordinate] = BOARD_SYMBOL_UNDISCOVERED;
        if (isMineMarked(yCoordinate, xCoordinate)) {
            correctlyPlacedMines--;
        }
        numberOfFlagsPlaced--;
    }

    private void openSurroundingGrid(Deque<Point> coordinates, int yPosition, int xPosition) {
        for (int vertical = yPosition - 1; vertical <= yPosition + 1; vertical++) {
            if (MinesweeperUtils.isCoordinateInRange(vertical)) {
                for (int horizontal = xPosition - 1; horizontal <= xPosition + 1; horizontal++) {
                    if (MinesweeperUtils.isCoordinateInRange(horizontal) && isValid(vertical, horizontal)) {
                        openGrid(coordinates, vertical, horizontal);
                    }
                }
            }
        }
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

    public boolean isCoordinatesSet(String coordinates) {
        if (!MinesweeperUtils.isArgumentsEqualToThree(coordinates)) {
            System.out.println("Please provide two coordinates and an action");
            return false;
        }

        String stringYCoordinate = MinesweeperUtils.getInputFromIndex(coordinates, Y_COORDINATE_INDEX);
        String stringXCoordinate = MinesweeperUtils.getInputFromIndex(coordinates, X_COORDINATE_INDEX);
        String action = MinesweeperUtils.getInputFromIndex(coordinates, ACTION_COORDINATE_INDEX);

        int yCoordinate = MinesweeperUtils.getCoordinate(stringYCoordinate);
        if (!MinesweeperUtils.isCoordinateValid(yCoordinate)) {
            System.out.println("Please input a positive integer in the range of " + GRID);
            return false;
        }

        int xCoordinate = MinesweeperUtils.getCoordinate(stringXCoordinate);
        if (!MinesweeperUtils.isCoordinateValid(xCoordinate)) {
            System.out.println("Please input a positive integer in the range of " + GRID);
            return false;
        }

        return isActionValid(action, yCoordinate, xCoordinate);
    }

    private boolean isActionValid(String action, int yCoordinate, int xCoordinate) {
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
        if (MinesweeperUtils.isDigit(board, yCoordinate, xCoordinate)) {
            System.out.println("There is a number here!");
            return false;
        }

        return true;
    }

    public boolean isGameOver() {
        correctlyPlacedMines = 0;
        numberOfUndiscoveredIndex = 0;

        for (int i = 0; i < GRID; i++) {
            for (int j = 0; j < GRID; j++) {
                if (board[i][j] == BOARD_SYMBOL_MINE) {
                    isPlayerWon = false;
                    return true;
                } else if (board[i][j] == BOARD_SYMBOL_UNDISCOVERED) {
                    numberOfUndiscoveredIndex++;
                } else if (board[i][j] == BOARD_SYMBOL_FLAG && isMineMarked(i, j)) {
                    correctlyPlacedMines++;
                }
            }
        }

        return (correctlyPlacedMines == numberOfFlagsPlaced && correctlyPlacedMines == numberOfMines)
                || (numberOfUndiscoveredIndex == numberOfMines);
    }

    boolean isMineMarked(int yCoordinate, int xCoordinate) {
        if (coordinates.containsKey(yCoordinate)) {
            return coordinates.get(yCoordinate).contains(xCoordinate);
        }
        return false;
    }

    boolean isFlagPlaced(int yCoordinate, int xCoordinate) {
        if (isNotANeighbouringMine(yCoordinate, xCoordinate) && isNotDiscovered(yCoordinate, xCoordinate)) {
            if (board[yCoordinate][xCoordinate] == BOARD_SYMBOL_UNDISCOVERED) {
                addFlag(yCoordinate, xCoordinate);
            } else {
                removeFlag(yCoordinate, xCoordinate);
            }
            return true;
        }
        return false;
    }

    private boolean isNotDiscovered(int yCoordinate, int xCoordinate) {
        if (board[yCoordinate][xCoordinate] == BOARD_SYMBOL_DISCOVERED) {
            System.out.println("This place has already been discovered");
            return false;
        }
        return true;
    }

    boolean isFloodFilled(int yCoordinate, int xCoordinate) {
        Deque<Point> coordinates = new ArrayDeque<>();

        if (isMineMarked(yCoordinate, xCoordinate)) {
            revealAllMines();
            return true;
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

            openSurroundingGrid(coordinates, yPosition, xPosition);
        }
        return true;
    }

    private boolean isNumberOfSurroundingMinesSet(int horizontal, int vertical) {
        int numberOfSurroundingMines;
        numberOfSurroundingMines = checkSurrounding(horizontal, vertical);
        if (numberOfSurroundingMines != 0) {
            board[horizontal][vertical] = Character.forDigit(numberOfSurroundingMines, 10);
            return true;
        }
        return false;
    }

    boolean isValid(int yCoordinate, int xCoordinate) {
        return !MinesweeperUtils.isInRange(yCoordinate) &&
                !MinesweeperUtils.isInRange(xCoordinate) &&
                !MinesweeperUtils.isDigit(board, yCoordinate, xCoordinate) &&
                !isMineMarked(yCoordinate, xCoordinate) &&
                board[yCoordinate][xCoordinate] != BOARD_SYMBOL_DISCOVERED;
    }
}
