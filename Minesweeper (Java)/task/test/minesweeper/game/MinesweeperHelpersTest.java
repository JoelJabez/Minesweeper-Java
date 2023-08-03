package minesweeper.game;

import static org.junit.Assert.assertNotEquals;

import junit.framework.TestCase;

public class MinesweeperHelpersTest extends  TestCase{

    public void testSplitArguments() {
		assertEquals(1, MinesweeperHelpers.splitArguments("1 ").length);
		assertEquals(2, MinesweeperHelpers.splitArguments("1 2").length);
	    assertEquals(3, MinesweeperHelpers.splitArguments("foo   bar    baz").length);

		assertNotEquals(3, MinesweeperHelpers.splitArguments("1   2").length);
	}

	public void testGetInputFromIndex() {
		assertEquals("1", MinesweeperHelpers.getInputFromIndex("1      5    foo", 0));
		assertEquals("test", MinesweeperHelpers.getInputFromIndex("1 TeSt foo", 1));
		assertEquals("foo", MinesweeperHelpers.getInputFromIndex("1 TeSt FOO", 2));

		assertNotEquals("TeSt", MinesweeperHelpers.getInputFromIndex("1 TeSt foo", 1));
		assertNotEquals("FOO", MinesweeperHelpers.getInputFromIndex("1 TeSt FOO", 2));
	}

	public void testIsCoordinateInRange() {
		assertTrue(MinesweeperHelpers.isCoordinateInRange(0));
		assertTrue(MinesweeperHelpers.isCoordinateInRange(5));
		assertTrue(MinesweeperHelpers.isCoordinateInRange(8));

		assertFalse(MinesweeperHelpers.isCoordinateInRange(-1));
		assertFalse(MinesweeperHelpers.isCoordinateInRange(9));
	}

	public void testIsCoordinateValid() {
		assertTrue(MinesweeperHelpers.isCoordinateInRange(0));
		assertTrue(MinesweeperHelpers.isCoordinateInRange(5));
		assertTrue(MinesweeperHelpers.isCoordinateInRange(8));

		assertFalse(MinesweeperHelpers.isCoordinateInRange(-1));
		assertFalse(MinesweeperHelpers.isCoordinateInRange(9));
		assertFalse(MinesweeperHelpers.isCoordinateInRange((int) Double.NEGATIVE_INFINITY));
	}
}
