package minesweeper.game;

import java.util.Random;

public class Minesweeper {
    private int size = 9;
    private int numberOfMines = 10;
    private int range = (int) Math.pow(size, 2) + (size - 1);
    private char[][] board = new char[size][size];

    public void start() {
        newBoard();
        printBoard();
    }

    void newBoard() {
        Random random = new Random();
        do {
            int number = random.nextInt(range);
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

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] != 'X') {
                    board[i][j] = '.';
                }
            }
        }
    }

    void printBoard() {
        for (char[] vertical : board) {
            for (char c : vertical) {
                System.out.print(c);
            }
            System.out.println();
        }
    }
}
