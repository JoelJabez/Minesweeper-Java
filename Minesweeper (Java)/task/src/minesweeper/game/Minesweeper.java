package minesweeper.game;

import java.util.*;

public class Minesweeper {
    HashMap<Integer, List<Integer>> coordinates;
    Scanner scanner;
    private final int GRID;
    private final char[][] BOARD;

    private int numberOfMines;

    {
        scanner = new Scanner(System.in);
        coordinates = new HashMap<>();

        GRID = 9;
        BOARD = new char[GRID][GRID];
    }

    public void start() {
        getNumberOfMines();
        generateNewBoard();
        printBoard();
        getMarkMineInput();
        printBoard();

        scanner.close();
    }

    void getNumberOfMines() {
        while (true) {
            try {
                System.out.print("How may mines do you want on the field? ");
                String mines = scanner.nextLine();
                numberOfMines = Integer.parseInt(mines);
                if (numberOfMines > 0) {
                    break;
                }

                System.out.println("Please enter a positive integer");
            } catch (NumberFormatException nfe) {
                System.out.println("Please enter an integer");
            }
        }
    }

    void generateNewBoard() {
        Random random = new Random();
        do {
            List<Integer> yCoordinates = new ArrayList<>();
            int xCoordinate = random.nextInt(GRID);
            int yCoordinate = random.nextInt(GRID);

            if (isNotOccupied(xCoordinate, yCoordinate)) {
                coordinates.putIfAbsent(xCoordinate, yCoordinates);
                coordinates.get(xCoordinate).add(yCoordinate);

                numberOfMines--;
            }
        } while (numberOfMines != 0);

        for (int horizontal = 0; horizontal < BOARD.length; horizontal++) {
            for (int vertical = 0; vertical < BOARD.length; vertical++) {
                BOARD[horizontal][vertical] = '.';
            }
        }
    }

    private void printBoard() {
        System.out.println();
        printTopGrid();

        for (int horizontal = 0; horizontal < GRID; horizontal++) {
            System.out.printf("%d|", horizontal + 1);
            for (int vertical = 0; vertical < GRID; vertical++) {
                int counter = 0;
                if (isNotOccupied(horizontal, vertical)) {
                    counter = checkSurrounding(horizontal, vertical, counter);
                    if (counter != 0) {
                        BOARD[horizontal][vertical] = Character.forDigit(counter, 10);
                    }
                }
                System.out.print(BOARD[horizontal][vertical]);
            }
            System.out.println("|");
        }

        printBottomGrid();
        System.out.println();

        for (Map.Entry<Integer, List<Integer>> entry : coordinates.entrySet()) {
            Integer key = entry.getKey();
            List<Integer> value = entry.getValue();

            for (Integer integer : value) {
                System.out.println(key + " -> " + integer);
            }
        }
    }

    void getMarkMineInput() {
        int xCoordinate;
        int yCoordinate;

        while (true) {
            try {
                System.out.print("Set/delete mine marks (x and y coordinates): ");
                String coordinates = scanner.nextLine();

                xCoordinate = Integer.parseInt(coordinates.split(" ")[0]);
                yCoordinate = Integer.parseInt(coordinates.split(" ")[1]);

                xCoordinate--;
                yCoordinate--;

                if (isEmpty(xCoordinate, yCoordinate)){
                    break;
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Please input a number");
            } catch (ArrayIndexOutOfBoundsException aiooe) {
                System.out.println("Please enter two positive coordinates");
            }
        }
    }

    private boolean isEmpty(int xCoordinate, int yCoordinate) {
        if (isNotInRange(xCoordinate, true)) {
            return false;
        }
        if (isNotInRange(yCoordinate, false)) {
            return false;
        }
        if (Character.isDigit(BOARD[xCoordinate][yCoordinate])) {
            System.out.println("There is a number here!");
            return false;
        }

        BOARD[xCoordinate][yCoordinate] = '*';
        return true;
    }

    private boolean isNotInRange(int coordinate, boolean isX) {
        if (-1 > coordinate || coordinate > GRID) {
            if (isX) {
                System.out.println("Please enter the X coordinates in the range of " + GRID);
            } else {
                System.out.println("Please enter the Y coordinates in the range of " + GRID);
            }
            return true;
        }
        return false;
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

    int checkSurrounding(int horizontal, int vertical, int counter) {
        for (int i = horizontal - 1; i <= horizontal + 1; i++) {
            if (0 <= i && i < GRID) {
                for (int j = vertical - 1; j <= vertical + 1; j++) {
                    if (horizontal == i && vertical == j) {
                        continue;
                    }

                    if (0 <= j && j < GRID) {
                        if (coordinates.containsKey(i)) {
                            if (coordinates.get(i).contains(j)) {
                                counter++;
                            }
                        }
                    }
                }
            }
        }
        return counter;
    }

    private boolean isNotOccupied(int xCoordinate, int yCoordinate) {
        if (coordinates.containsKey(xCoordinate)) {
            return !coordinates.get(xCoordinate).contains(yCoordinate);
        }
        return true;
    }
}
