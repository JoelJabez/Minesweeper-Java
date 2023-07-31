package minesweeper.game;

import junit.framework.TestCase;

public class MinesweeperUtilsTest extends TestCase{
    private final int NEGATIVE_INFINITY = (int) Double.NEGATIVE_INFINITY;

    public void testGetIntegerFromUserInput() {
        assertEquals(-2, MinesweeperUtils.getIntegerFromUserInput("-2"));
        assertEquals(10, MinesweeperUtils.getIntegerFromUserInput("10"));

        assertEquals(NEGATIVE_INFINITY, MinesweeperUtils.getIntegerFromUserInput("foo"));
        assertEquals(NEGATIVE_INFINITY, MinesweeperUtils.getIntegerFromUserInput("1a"));
    }

    public void testGetCoordinate() {
        assertEquals(0, MinesweeperUtils.getCoordinate("1 2", 0));
        assertEquals(1, MinesweeperUtils.getCoordinate("3 2", 1));

        assertEquals(1, MinesweeperUtils.getCoordinate("-1 2", 1));
        assertEquals(0, MinesweeperUtils.getCoordinate("foo 1", 1));
        assertEquals(9, MinesweeperUtils.getCoordinate("2 10", 1));
        assertEquals(NEGATIVE_INFINITY, MinesweeperUtils.getCoordinate("foo", 0));
        assertEquals(NEGATIVE_INFINITY, MinesweeperUtils.getCoordinate("1 ", 0));
        assertEquals(NEGATIVE_INFINITY, MinesweeperUtils.getCoordinate("foo bar", 1));
        assertEquals(NEGATIVE_INFINITY, MinesweeperUtils.getCoordinate("1 foo", 1));
        assertEquals(NEGATIVE_INFINITY, MinesweeperUtils.getCoordinate("1", 1));
    }
}

