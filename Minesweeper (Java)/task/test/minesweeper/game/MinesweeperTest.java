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

    public void testIsNotMarked() {
        generateBoard();
        assertTrue(minesweeper.isMineMarked(1, 0));
    }

}
