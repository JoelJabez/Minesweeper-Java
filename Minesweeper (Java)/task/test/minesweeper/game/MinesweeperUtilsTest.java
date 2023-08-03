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
        assertEquals(0, MinesweeperUtils.getCoordinate("1"));
        assertEquals(-2, MinesweeperUtils.getCoordinate("-1"));
        assertEquals(9, MinesweeperUtils.getCoordinate("10"));

        assertEquals(NEGATIVE_INFINITY, MinesweeperUtils.getCoordinate("1 2"));
        assertEquals(NEGATIVE_INFINITY, MinesweeperUtils.getCoordinate("3 2"));
        assertEquals(NEGATIVE_INFINITY, MinesweeperUtils.getCoordinate("-1 2"));
        assertEquals(NEGATIVE_INFINITY, MinesweeperUtils.getCoordinate("foo 1"));
        assertEquals(NEGATIVE_INFINITY, MinesweeperUtils.getCoordinate("2 10"));
        assertEquals(NEGATIVE_INFINITY, MinesweeperUtils.getCoordinate("foo"));
        assertEquals(NEGATIVE_INFINITY, MinesweeperUtils.getCoordinate("1 "));
        assertEquals(NEGATIVE_INFINITY, MinesweeperUtils.getCoordinate("foo bar"));
        assertEquals(NEGATIVE_INFINITY, MinesweeperUtils.getCoordinate("1 foo"));
    }

    public void testIsNumberOfMinesInRange() {
        assertTrue(MinesweeperUtils.isNumberOfMinesInRange(1));
        assertTrue(MinesweeperUtils.isNumberOfMinesInRange(40));
        assertTrue(MinesweeperUtils.isNumberOfMinesInRange(81));

        assertFalse(MinesweeperUtils.isNumberOfMinesInRange(0));
        assertFalse(MinesweeperUtils.isNumberOfMinesInRange(-1));
        assertFalse(MinesweeperUtils.isNumberOfMinesInRange(82));
    }

    public void testIsCoordinateInRange() {
        assertTrue(MinesweeperUtils.isCoordinateInRange(0));
        assertTrue(MinesweeperUtils.isCoordinateInRange(4));
        assertTrue(MinesweeperUtils.isCoordinateInRange(8));

        assertFalse(MinesweeperUtils.isCoordinateInRange(-1));
        assertFalse(MinesweeperUtils.isCoordinateInRange(9));
        assertFalse(MinesweeperUtils.isCoordinateInRange(10));
    }

    public void testIsInRange() {
        assertFalse(MinesweeperUtils.isInRange(0));
        assertFalse(MinesweeperUtils.isInRange(4));
        assertFalse(MinesweeperUtils.isInRange(8));

        assertTrue(MinesweeperUtils.isInRange(-1));
        assertTrue(MinesweeperUtils.isInRange(9));
    }

    public void testIsArgumentsEqualToThree() {
        assertTrue(MinesweeperUtils.isArgumentsEqualToThree("   1    2    3"));

        assertFalse(MinesweeperUtils.isArgumentsEqualToThree("1    2   "));
    }
}

