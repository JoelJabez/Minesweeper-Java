package minesweeper.game;

import java.util.Random;
import java.util.Scanner;

public class Minesweeper {
    private final int SIZE = 9;
    private final int RANGE = (int) Math.pow(SIZE, 2) + (SIZE - 1);

    private int numberOfMines;
    private char[][] board = new char[SIZE][SIZE];

    public void start() {
        getNumberOfMines();
        newBoard();
        printBoard();
    }

    void getNumberOfMines() {
        Scanner scanner = new Scanner(System.in);

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

        scanner.close();
    }

    void newBoard() {
        Random random = new Random();
        do {
            int number = random.nextInt(RANGE);
            int xCoordinate = number / 10;
            int yCoordinate = number % 10;

            if (xCoordinate == 9) {
                xCoordinate--;
            }
            if (yCoordinate == 9) {
                yCoordinate--;
            }
            if (board[xCoordinate][yCoordinate] != 'X') {
                board[xCoordinate][yCoordinate] = 'X';
                numberOfMines--;
            }
        } while (numberOfMines != 0);

        for (int horizontal = 0; horizontal < board.length; horizontal++) {
            for (int vertical = 0; vertical < board.length; vertical++) {
                if (board[horizontal][vertical] != 'X') {
                    board[horizontal][vertical] = '.';
                }
            }
        }
    }

    void printBoard() {
        printTopGrid();

        for (int horizontal = 0; horizontal < SIZE; horizontal++) {
            System.out.printf("%d|", horizontal + 1);
            for (int vertical = 0; vertical < SIZE; vertical++) {
                int counter = 0;
                if (board[horizontal][vertical] != 'X') {
                    counter = checkSurrounding(horizontal, vertical, counter);
                    if (counter != 0) {
                        board[horizontal][vertical] = Character.forDigit(counter, 10);
                    }
                }
                System.out.print(board[horizontal][vertical]);
            }
            System.out.println("|");
        }

        printBottomGrid();
    }

    private void printTopGrid() {
        System.out.print(" |");
        for (int i = 1; i <= SIZE; i++) {
            System.out.print(i);
        }
        System.out.println("|");

        printBottomGrid();
    }

    private void printBottomGrid() {
        System.out.print("-|");
        for (int i = 1; i <= SIZE; i++) {
            System.out.print("-");
        }
        System.out.println("|");
    }

    int checkSurrounding(int horizontal, int vertical, int counter) {
        for (int i = horizontal - 1; i <= horizontal + 1; i++) {
            if (0 <= i && i < SIZE) {
                for (int j = vertical - 1; j <= vertical + 1; j++) {
                    if ((0 <= j && j < SIZE) && board[i][j] == 'X') {
                        counter++;
                    }
                }
            }
        }
        return counter;
    }
}
