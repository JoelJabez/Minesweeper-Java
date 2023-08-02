package minesweeper.game;

import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

public class MinesweeperTest extends TestCase {
    Minesweeper minesweeper;

    public void setUp() {
        minesweeper = new Minesweeper();
    }

    void generateBoard() {
        placeMines();
        minesweeper.placeCounterOfSurroundingMines();
    }

    void placeMines() {
        int tempNumberOfMines = 8;
        int i = 0;

        do {
            List<Integer> yCoordinates = new ArrayList<>();
            int xCoordinate = i;
            int yCoordinate = i;

            if (minesweeper.isMineMarked(yCoordinate, xCoordinate)) {
                minesweeper.coordinates.putIfAbsent(xCoordinate, yCoordinates);
                minesweeper.coordinates.get(xCoordinate).add(yCoordinate);

                tempNumberOfMines--;
                i++;
            }
        } while (tempNumberOfMines != 0);
    }

    public void testGenerateNewBoard() {
        int numberOfMines = 10;
        int wrongNumber = 4;
        minesweeper.generateNewBoard(numberOfMines);

        int counter = 0;
        for (Map.Entry<Integer, List<Integer>> coordinates : minesweeper.coordinates.entrySet()) {
            counter += coordinates.getValue().size();
        }

        assertEquals(numberOfMines, counter);

        assertNotEquals(wrongNumber, counter);
    }

    public void testIsNotInRange() {
        assertTrue(minesweeper.isNotInRange(-1, true));
        assertTrue(minesweeper.isNotInRange(9, true));

        assertFalse(minesweeper.isNotInRange(0, false));
        assertFalse(minesweeper.isNotInRange(4, true));
        assertFalse(minesweeper.isNotInRange(8, false));
    }

    public void testIsCoordinatesSet() {
        generateBoard();
        assertFalse(minesweeper.isCoordinatesSet("1 1"));

        assertFalse(minesweeper.isCoordinatesSet("1 2"));
        assertFalse(minesweeper.isCoordinatesSet("9 9"));

        assertFalse(minesweeper.isCoordinatesSet("-1 2"));
        assertFalse(minesweeper.isCoordinatesSet("2 10"));
        assertFalse(minesweeper.isCoordinatesSet("foo"));
        assertFalse(minesweeper.isCoordinatesSet("1 "));
        assertFalse(minesweeper.isCoordinatesSet("foo bar"));
        assertFalse(minesweeper.isCoordinatesSet("1 foo"));
        assertFalse(minesweeper.isCoordinatesSet("foo 1"));
        assertFalse(minesweeper.isCoordinatesSet("1"));
    }

    public void testIsXCoordinateNotValid() {
        assertTrue(minesweeper.isXCoordinateValid(-1));
        assertTrue(minesweeper.isXCoordinateValid(9));
        assertTrue(minesweeper.isXCoordinateValid((int) Double.NEGATIVE_INFINITY));

        assertFalse(minesweeper.isXCoordinateValid(0));
        assertFalse(minesweeper.isXCoordinateValid(8));
    }

    public void testIsYCoordinateNotValid() {
        assertTrue(minesweeper.isYCoordinateNotValid(-1));
        assertTrue(minesweeper.isYCoordinateNotValid(9));
        assertTrue(minesweeper.isYCoordinateNotValid((int) Double.NEGATIVE_INFINITY));

        assertFalse(minesweeper.isYCoordinateNotValid(0));
        assertFalse(minesweeper.isYCoordinateNotValid(8));
    }

    public void testIsNotMarked() {
        generateBoard();
        printBoard();
        assertTrue(minesweeper.isMineMarked(1, 0));
    }

    public void testIsArgumentsEqualToThree() {
        assertTrue(minesweeper.isArgumentsEqualToThree("1 2 foo"));

        assertTrue(minesweeper.isArgumentsEqualToThree("1 2   foo"));
    }

    void printBoard() {
        for (int i = 0; i < Minesweeper.GRID; i++) {
            for (int j = 0; j < Minesweeper.GRID; j++) {
                System.out.print(minesweeper.BOARD[i][j]);
            }
            System.out.println();
        }
    }
}
