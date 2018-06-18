package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import strategies.InformationItem;

public class InformationItemTest {

	/**
	 * Test the constructor.
	 */
	@Test
	public void testConstructor1() {
		InformationItem testItem = new InformationItem(2, 0, 0);
		assertTrue(testItem.getCoopDefectLost() == 2);
		assertTrue(testItem.getDelay() == 0);
		assertTrue(testItem.getSecretDelay() == 0);
	}
	
	/**
	 * Test the constructor.
	 */
	@Test
	public void testConstructor2() {
		InformationItem testItem = new InformationItem(-1, 2, 1);
		assertTrue(testItem.getCoopDefectLost() == -1);
		assertTrue(testItem.getDelay() == 2);
		assertTrue(testItem.getSecretDelay() == 1);
	}

	/**
	 * Test decreasing secret delay.
	 */
	@Test
	public void testDecreaseSecretDelay() {
		InformationItem testItem = new InformationItem(-1, 3, 3);
		testItem.decreaseSecretDelay();
		
		assertTrue(testItem.getDelay() == 3);
		assertTrue(testItem.getSecretDelay() == 2);
	}
	
	/**
	 * Test the equals method
	 */
	@Test
	public void testEquals1() {
		InformationItem testItem = new InformationItem(-1, 3, 3);
		InformationItem testItem2 = new InformationItem(-1, 3, 3);
		assertTrue(testItem.equals(testItem2));
	}
	
	/**
	 * Test the equals method
	 */
	@Test
	public void testEquals2() {
		InformationItem testItem = new InformationItem(-1, 3, 3);
		assertFalse(testItem.equals(6));
	}
	
	/**
	 * Test the equals method
	 */
	@Test
	public void testEquals3() {
		InformationItem testItem = new InformationItem(-1, 3, 3);
		InformationItem testItem2 = new InformationItem(-1, 3, 2);
		assertFalse(testItem.equals(testItem2));
	}
	
	/**
	 * Test the equals method
	 */
	@Test
	public void testEquals4() {
		InformationItem testItem = new InformationItem(-1, 4, 3);
		InformationItem testItem2 = new InformationItem(-1, 3, 3);
		assertFalse(testItem.equals(testItem2));
	}
	
	/**
	 * Test the equals method
	 */
	@Test
	public void testEquals5() {
		InformationItem testItem = new InformationItem(1, 0, 0);
		InformationItem testItem2 = new InformationItem(2, 0, 0);
		assertFalse(testItem.equals(testItem2));
	}
}
