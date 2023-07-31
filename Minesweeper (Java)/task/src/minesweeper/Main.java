package minesweeper;

import java.util.Scanner;

import minesweeper.game.Minesweeper;
import minesweeper.game.MinesweeperUtils;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Minesweeper minesweeper = new Minesweeper();

        int numberOfMines = getNumberOfMines(scanner);
        minesweeper.generateNewBoard(numberOfMines);
        minesweeper.printBoard();

        boolean isGameNotOver = true;
        do {
            setFlagMineFromUser(minesweeper, scanner);
            minesweeper.printBoard();
            isGameNotOver = minesweeper.isGameNotOver();
        } while (isGameNotOver);

        minesweeper.printBoard();

        System.out.println("Congratulations! You found all the mines!");
        scanner.close();
    }

    static int getNumberOfMines(Scanner scanner) {
        int numberOfMines;
        while (true) {
            System.out.print("How many mines do you want on the field? ");
            String mines = scanner.nextLine();
            numberOfMines = MinesweeperUtils.getIntegerFromUserInput(mines);

            if (numberOfMines > 0) {
                return numberOfMines;
            }
        }
    }

    static void setFlagMineFromUser(Minesweeper minesweeper, Scanner scanner) {
        boolean isCoordinateNotSet = true;
        do {
            System.out.print("Set/delete mine marks (x and y coordinates): ");
            String coordinates = scanner.nextLine();
            isCoordinateNotSet = !minesweeper.setCoordinates(coordinates);
        } while (isCoordinateNotSet);
    }
}
