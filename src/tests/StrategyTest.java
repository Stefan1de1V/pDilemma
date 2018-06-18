package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import strategies.InformationItem;
import strategies.Strategy;


public class StrategyTest {

	/**
	 * Test constructing a custom strategy (memory size 1).
	 */
	@Test
	public void testConstructor1() {
		int testStrategyMemorySize = 1;
		int[][] testArray =
				new int[Strategy.getRows(testStrategyMemorySize)][Strategy.getColumns(testStrategyMemorySize)];
		
		//Values strategy table
		testArray[1][1] = 2;
		testArray[1][2] = 1;
		testArray[1][3] = 1;
		testArray[2][1] = 2;
		testArray[2][2] = 1;
		testArray[2][3] = 1;
		testArray[0][0] = 1;
		
		//Create strategy
		Strategy testStrategy = new Strategy(testStrategyMemorySize, testArray);
		
		//Test strategy memory size
		assertTrue(testStrategy.getMemorySize() == 1);
		
		//Test strategy table elements
		assertTrue(testStrategy.getStrategy()[1][1] == 2);
		assertTrue(testStrategy.getStrategy()[1][2] == 1);
		assertTrue(testStrategy.getStrategy()[1][3] == 1);
		assertTrue(testStrategy.getStrategy()[2][1] == 2);
		assertTrue(testStrategy.getStrategy()[2][2] == 1);
		assertTrue(testStrategy.getStrategy()[2][3] == 1);
		assertTrue(testStrategy.getStrategy()[0][0] == 1);
	}
	
	/**
	 * Test constructing the standard strategy "TFT".
	 */
	@Test
	public void testConstructorTFT() {
		Strategy testStrategy = new Strategy(1);
		
		//Test memorySize
		assertTrue(testStrategy.getMemorySize() == 1);
		
		//Test strategy table values
		assertTrue(testStrategy.getStrategy()[1][1] == 1);
		assertTrue(testStrategy.getStrategy()[1][2] == 2);
		assertTrue(testStrategy.getStrategy()[1][3] == 1);
		assertTrue(testStrategy.getStrategy()[2][1] == 1);
		assertTrue(testStrategy.getStrategy()[2][2] == 2);
		assertTrue(testStrategy.getStrategy()[2][3] == 1);
		assertTrue(testStrategy.getStrategy()[0][0] == 1);
	}
	
	/**
	 * Test constructing the standard strategy "Always Cooperate".
	 */
	@Test
	public void testConstructorAC() {
		Strategy testStrategy = new Strategy(2);
		
		//Test memorySize
		assertTrue(testStrategy.getMemorySize() == 1);
		
		//Test strategy table values
		assertTrue(testStrategy.getStrategy()[1][1] == 1);
		assertTrue(testStrategy.getStrategy()[1][2] == 1);
		assertTrue(testStrategy.getStrategy()[1][3] == 1);
		assertTrue(testStrategy.getStrategy()[2][1] == 1);
		assertTrue(testStrategy.getStrategy()[2][2] == 1);
		assertTrue(testStrategy.getStrategy()[2][3] == 1);
		assertTrue(testStrategy.getStrategy()[0][0] == 1);
	}
	
	/**
	 * Test constructing the standard strategy "Always Defect".
	 */
	@Test
	public void testConstructorAD() {
		Strategy testStrategy = new Strategy(3);
		
		//Test memorySize
		assertTrue(testStrategy.getMemorySize() == 1);
		
		//Test strategy table values
		assertTrue(testStrategy.getStrategy()[1][1] == 2);
		assertTrue(testStrategy.getStrategy()[1][2] == 2);
		assertTrue(testStrategy.getStrategy()[1][3] == 2);
		assertTrue(testStrategy.getStrategy()[2][1] == 2);
		assertTrue(testStrategy.getStrategy()[2][2] == 2);
		assertTrue(testStrategy.getStrategy()[2][3] == 2);
		assertTrue(testStrategy.getStrategy()[0][0] == 2);
		
	}
	
	/**
	 * Test constructing the standard strategy "Unforgiving". 
	 */
	@Test
	public void testConstructorUN() {
		Strategy testStrategy = new Strategy(4);
		
		//Test memorySize
		assertTrue(testStrategy.getMemorySize() == 1);
			
		//Test strategy table values
		assertTrue(testStrategy.getStrategy()[1][1] == 1);
		assertTrue(testStrategy.getStrategy()[1][2] == 2);
		assertTrue(testStrategy.getStrategy()[1][3] == 1);
		assertTrue(testStrategy.getStrategy()[2][1] == 2);
		assertTrue(testStrategy.getStrategy()[2][2] == 2);
		assertTrue(testStrategy.getStrategy()[2][3] == 2);
		assertTrue(testStrategy.getStrategy()[0][0] == 1);
	}
	
	/**
	 * Test calculating the number of rows for the Strategy table given a certain memorySize.
	 */
	@Test
	public void testGetRows() {
		assertTrue(Strategy.getRows(1) == (2+1));
		assertTrue(Strategy.getRows(2) == (4+2+1));
		assertTrue(Strategy.getRows(3) == (8+4+2+1));
		
	}
	
	/**
	 * Test calculating the number of columns for the Strategy table given a certain memorySize.
	 */
	@Test
	public void testGetColumns() {
		assertTrue(Strategy.getColumns(1) == (3+1));
		assertTrue(Strategy.getColumns(2) == (25+5+1));
		assertTrue(Strategy.getColumns(3) == (343+49+7+1));
	}
	
	/**
	 * Test calculating the row of the strategy table to use given a memorySize
	 * and a player their past actions (for memory size 1).
	 */
	@Test
	public void testLookupRow1() {
		ArrayList<Integer> testPlayerMemory = new ArrayList<Integer>();
		
		testPlayerMemory.add(1);
		assertTrue(Strategy.lookupRow(1, 1, testPlayerMemory) == 0);
		assertTrue(Strategy.lookupRow(2, 1, testPlayerMemory) == 1);
		
		testPlayerMemory.remove(0);
		testPlayerMemory.add(2);
		assertTrue(Strategy.lookupRow(1, 1, testPlayerMemory) == 0);
		assertTrue(Strategy.lookupRow(2, 1, testPlayerMemory) == 2);
	}
	
	/**
	 * Test calculating the row of the strategy table to use given a memorySize
	 * and a player their past actions (for memory size 2).
	 */
	@Test
	public void testLookupRow2() {
		ArrayList<Integer> testPlayerMemory = new ArrayList<Integer>();
		
		testPlayerMemory.add(1);
		testPlayerMemory.add(1);
		assertTrue(Strategy.lookupRow(3, 2, testPlayerMemory) == 3);
		
		testPlayerMemory = new ArrayList<Integer>();
		testPlayerMemory.add(2);
		testPlayerMemory.add(1);
		assertTrue(Strategy.lookupRow(4, 2, testPlayerMemory) == 4);
		
		testPlayerMemory = new ArrayList<Integer>();
		testPlayerMemory.add(1);
		testPlayerMemory.add(2);
		assertTrue(Strategy.lookupRow(5, 2, testPlayerMemory) == 5);
		
		testPlayerMemory = new ArrayList<Integer>();
		testPlayerMemory.add(2);
		testPlayerMemory.add(2);
		assertTrue(Strategy.lookupRow(6, 2, testPlayerMemory) == 6);
		
		//Incomplete memory
		testPlayerMemory = new ArrayList<Integer>();
		testPlayerMemory.add(1);
		assertTrue(Strategy.lookupRow(1, 2, testPlayerMemory) == 0);
		assertTrue(Strategy.lookupRow(2, 2, testPlayerMemory) == 1);
		
		testPlayerMemory = new ArrayList<Integer>();
		testPlayerMemory.add(2);
		assertTrue(Strategy.lookupRow(1, 2, testPlayerMemory) == 0);
		assertTrue(Strategy.lookupRow(2, 2, testPlayerMemory) == 2);
		
	}
	
	/**
	 * Test calculating the row of the strategy table to use given a memorySize
	 * and a player their past actions (for memory size 3, atleast 3 rounds played).
	 */
	@Test
	public void testLookupRow3() {
		ArrayList<Integer> testPlayerMemory = new ArrayList<Integer>();
		
		testPlayerMemory.add(1);
		testPlayerMemory.add(2);
		testPlayerMemory.add(1);
		assertTrue(Strategy.lookupRow(4, 3, testPlayerMemory) == (1+2+4+2));
		
		testPlayerMemory = new ArrayList<Integer>();
		testPlayerMemory.add(2);
		testPlayerMemory.add(2);
		testPlayerMemory.add(1);
		assertTrue(Strategy.lookupRow(5, 3, testPlayerMemory) == (1+2+4+3));
		
		testPlayerMemory = new ArrayList<Integer>();
		testPlayerMemory.add(2);
		testPlayerMemory.add(1);
		testPlayerMemory.add(2);
		assertTrue(Strategy.lookupRow(6, 3, testPlayerMemory) == (1+2+4+5));
		
		testPlayerMemory = new ArrayList<Integer>();
		testPlayerMemory.add(1);
		testPlayerMemory.add(2);
		testPlayerMemory.add(2);
		assertTrue(Strategy.lookupRow(7, 3, testPlayerMemory) == (1+2+4+6));
		
	}
	
	/**
	 * Test calculating the row of the strategy table to use given a memorySize
	 * and a player their past actions (for memory size 3, no or 1 round played).
	 */
	@Test
	public void testLookupRow4() {
		ArrayList<Integer> testPlayerMemory = new ArrayList<Integer>();
		assertTrue(Strategy.lookupRow(1, 3, testPlayerMemory) == 0);
		
		testPlayerMemory.add(1);
		assertTrue(Strategy.lookupRow(2, 3, testPlayerMemory) == 1);
		
		testPlayerMemory = new ArrayList<Integer>();
		testPlayerMemory.add(2);
		assertTrue(Strategy.lookupRow(2, 3, testPlayerMemory) == 2);
		
		
	}
	
	/**
	 * Test calculating the row of the strategy table to use given a memorySize
	 * and a player their past actions (for memory size 3, 2 rounds played).
	 */
	@Test
	public void testLookupRow5() {
		ArrayList<Integer> testPlayerMemory = new ArrayList<Integer>();
		
		testPlayerMemory.add(1);
		testPlayerMemory.add(1);
		assertTrue(Strategy.lookupRow(3, 3, testPlayerMemory) == (1+2+0));
		
		testPlayerMemory = new ArrayList<Integer>();
		testPlayerMemory.add(2);
		testPlayerMemory.add(1);
		assertTrue(Strategy.lookupRow(3, 3, testPlayerMemory) == (1+2+1));
		
		testPlayerMemory = new ArrayList<Integer>();
		testPlayerMemory.add(1);
		testPlayerMemory.add(2);
		assertTrue(Strategy.lookupRow(3, 3, testPlayerMemory) == (1+2+2));
		
		testPlayerMemory = new ArrayList<Integer>();
		testPlayerMemory.add(2);
		testPlayerMemory.add(2);
		assertTrue(Strategy.lookupRow(3, 3, testPlayerMemory) == (1+2+3));
	}
	
	/**
	 * Test calculating what column of the strategy table to use given a certain memorySize
	 * and a player their past actions (for memory size 1, 0 or more rounds played).
	 */
	@Test
	public void testLookupColumn1() {
		ArrayList<InformationItem> testOpponentMemory = new ArrayList<InformationItem>();
		
		testOpponentMemory.add(new InformationItem(1, 0, 0));
		assertTrue(Strategy.lookupColumn(1, 1, testOpponentMemory) == 0);
		assertTrue(Strategy.lookupColumn(2, 1, testOpponentMemory) == 1);
		
		testOpponentMemory = new ArrayList<InformationItem>();
		testOpponentMemory.add(new InformationItem(2, 0, 0));
		assertTrue(Strategy.lookupColumn(1, 1, testOpponentMemory) == 0);
		assertTrue(Strategy.lookupColumn(3, 1, testOpponentMemory) == 2);
		
		testOpponentMemory = new ArrayList<InformationItem>();
		testOpponentMemory.add(new InformationItem(-1, 0, 0));
		assertTrue(Strategy.lookupColumn(1, 1, testOpponentMemory) == 0);
		assertTrue(Strategy.lookupColumn(4, 1, testOpponentMemory) == 3);
	}
	
	/**
	 * Test calculating what column of the strategy table to use given a certain memorySize
	 * and a player their past actions (for memory size 2, atleast 2 rounds played).
	 */
	@Test
	public void testLookupColumn2() {
		ArrayList<InformationItem> testOpponentMemory = new ArrayList<InformationItem>();
		
		//Delayed defect, lost
		testOpponentMemory.add(new InformationItem(2, 1, 0));
		testOpponentMemory.add(new InformationItem(-1, 0, 0));
		assertTrue(Strategy.lookupColumn(3, 2, testOpponentMemory) == (1+5+23));
		
		//Lost, coop
		testOpponentMemory = new ArrayList<InformationItem>();
		testOpponentMemory.add(new InformationItem(-1, 0, 0));
		testOpponentMemory.add(new InformationItem(1, 0, 0));
		assertTrue(Strategy.lookupColumn(4, 2, testOpponentMemory) == (1+5+4));
		
		//Delayed coop, defect
		testOpponentMemory = new ArrayList<InformationItem>();
		testOpponentMemory.add(new InformationItem(1, 1, 0));
		testOpponentMemory.add(new InformationItem(2, 0, 0));
		assertTrue(Strategy.lookupColumn(5, 2, testOpponentMemory) == (1+5+11));
		
		//Defect, coop
		testOpponentMemory = new ArrayList<InformationItem>();
		testOpponentMemory.add(new InformationItem(2, 0, 0));
		testOpponentMemory.add(new InformationItem(1, 0, 0));
		assertTrue(Strategy.lookupColumn(6, 2, testOpponentMemory) == (1+5+2));
	}
	
	/**
	 * Test calculating what column of the strategy table to use given a certain memorySize
	 * and a player their past actions (for memory size 2, 0 or 1 rounds played).
	 */
	@Test
	public void testLookupColumn3() {
		//Incomplete memory (0 rounds played)
		ArrayList<InformationItem> testOpponentMemory = new ArrayList<InformationItem>();
		assertTrue(Strategy.lookupColumn(1, 2, testOpponentMemory) == 0);
		
		//Incomplete memory (1 round played)
		testOpponentMemory = new ArrayList<InformationItem>();
		testOpponentMemory.add(new InformationItem(1, 0, 0));
		assertTrue(Strategy.lookupColumn(2, 2, testOpponentMemory) == 1);
		
		testOpponentMemory = new ArrayList<InformationItem>();
		testOpponentMemory.add(new InformationItem(2, 0, 0));
		assertTrue(Strategy.lookupColumn(2, 2, testOpponentMemory) == 3); //N, C, C*, D
	}
	
	/**
	 * Test calculating the column of the strategy table to use given a memorySize
	 * and a player their past actions (for memory size 3, at least 3 rounds played).
	 */
	@Test
	public void testLookupColumn4() {
		ArrayList<InformationItem> testOpponentMemory = new ArrayList<InformationItem>();
		
		//Delayed2 defect, lost (actually delayed), coop
		testOpponentMemory.add(new InformationItem(2, 2, 0));
		testOpponentMemory.add(new InformationItem(-1, 1, 1));
		testOpponentMemory.add(new InformationItem(1, 0, 0));
		assertTrue(Strategy.lookupColumn(4, 3, testOpponentMemory) ==
				((7*7*(1-1) + 7*(7-1) + (6-1)) + 1 + 7 + 49));
		
		//Lost, delayed1 coop, coop
		testOpponentMemory = new ArrayList<InformationItem>();
		testOpponentMemory.add(new InformationItem(-1, 0, 0));
		testOpponentMemory.add(new InformationItem(1, 1, 0));
		testOpponentMemory.add(new InformationItem(1, 0, 0));
		assertTrue(Strategy.lookupColumn(5, 3, testOpponentMemory) ==
				((7*7*(1-1) + 7*(2-1) + (7-1)) + 1 + 7 + 49));
		
		//Delayed1 defect, lost, lost
		testOpponentMemory = new ArrayList<InformationItem>();
		testOpponentMemory.add(new InformationItem(2, 1, 0));
		testOpponentMemory.add(new InformationItem(-1, 0, 0));
		testOpponentMemory.add(new InformationItem(-1, 0, 0));
		assertTrue(Strategy.lookupColumn(6, 3, testOpponentMemory) ==
				((7*7*(7-1) + 7*(7-1) + (5-1)) + 1 + 7 + 49));
		
		//Defect, coop, defect
		testOpponentMemory = new ArrayList<InformationItem>();
		testOpponentMemory.add(new InformationItem(2, 0, 0));
		testOpponentMemory.add(new InformationItem(1, 0, 0));
		testOpponentMemory.add(new InformationItem(2, 0, 0));
		assertTrue(Strategy.lookupColumn(7, 3, testOpponentMemory) ==
				((7*7*(4-1) + 7*(1-1) + (4-1)) + 1 + 7 + 49));
	}
	
	/**
	 * Test calculating the column of the strategy table to use given a memorySize
	 * and a player their past actions (for memory size 3, 2 rounds played).
	 */
	@Test
	public void testLookupColumn5() {
		//Delay1Coop, lost
		ArrayList<InformationItem> testOpponentMemory = new ArrayList<InformationItem>();
		testOpponentMemory = new ArrayList<InformationItem>();
		testOpponentMemory.add(new InformationItem(1, 1, 0));
		testOpponentMemory.add(new InformationItem(-1, 0, 0));
		assertTrue(Strategy.lookupColumn(3, 3, testOpponentMemory) ==
				((7*(7-1) + (2-1)) + 1 + 7));
		
		//Defect, coop
		testOpponentMemory = new ArrayList<InformationItem>();
		testOpponentMemory.add(new InformationItem(2, 0, 0));
		testOpponentMemory.add(new InformationItem(1, 0, 0));
		assertTrue(Strategy.lookupColumn(3, 3, testOpponentMemory) ==
				((7*(1-1) + (4-1)) + 1 + 7));
		
		//Lost, lost
		testOpponentMemory = new ArrayList<InformationItem>();
		testOpponentMemory.add(new InformationItem(-1, 0, 0));
		testOpponentMemory.add(new InformationItem(-1, 0, 0));
		assertTrue(Strategy.lookupColumn(3, 3, testOpponentMemory) ==
				((7*(7-1) + (7-1)) + 1 + 7));
	}
	
	/**
	 * Test calculating the next move.
	 */
	@Test
	public void testNextMove() {
		ArrayList<Integer> testPrevChoicesMe = new ArrayList<Integer>();
		ArrayList<InformationItem> testPrevChoicesOpponent = new ArrayList<InformationItem>();
		Strategy testStrategy = new Strategy(1);
		
		assertTrue(testStrategy.nextMove(1, testPrevChoicesMe, testPrevChoicesOpponent) ==
				testStrategy.getStrategy()[0][0]);
		testPrevChoicesMe.add(2);
		testPrevChoicesOpponent.add(new InformationItem(1, 0, 0));
		assertTrue(testStrategy.nextMove(3, testPrevChoicesMe, testPrevChoicesOpponent) ==
				testStrategy.getStrategy()[2][1]);
	}
	
	/**
	 * Test the number of mutations done to the strategies with different memory sizes
	 */
	@Test
	public void testGetNumberOfChanges() {
		assertTrue(Strategy.getNumberOfChanges(1) == 1);
		assertTrue(Strategy.getNumberOfChanges(2) == 20);
		assertTrue(Strategy.getNumberOfChanges(3) == 592);
		//I mean how else am I supposed to test this
	}
	
	/**
	 * Test mutating a strategy (for memorySize 1).
	 */
	@Test
	public void testMutation() {
		Strategy testStrategy = new Strategy(4);
		Strategy testStrategy2 = testStrategy.getMutation();
		
		//Test the same memory size
		assertTrue(testStrategy.getMemorySize() == testStrategy2.getMemorySize());
		
		//Test number of common elements
		int testCommon = 0;
		for(int i = 0; i < Strategy.getRows(1); i++) {
			for(int j = 0; j < Strategy.getColumns(1); j++) {
				if (testStrategy.getStrategy()[i][j] == testStrategy2.getStrategy()[i][j]) {
					testCommon++;
				}
			}
		}
		assertTrue(testCommon == (Strategy.getRows(1) * Strategy.getColumns(1) - Strategy.getNumberOfChanges(1)));
	}
	
	/**
	 * Test mutating a strategy (for memorySize 2).
	 */
	public void testMutation2() {
		int testMemSize = 2;
		Strategy testStrategy = new Strategy(testMemSize,
				new int[Strategy.getRows(testMemSize)][Strategy.getColumns(testMemSize)]);
		Strategy testStrategy2 = testStrategy.getMutation();
		
		//Test the same memory size
		assertTrue(testStrategy.getMemorySize() == testStrategy2.getMemorySize());
		
		//Test number of common elements
		int testCommon = 0;
		for(int i = 0; i < Strategy.getRows(testMemSize); i++) {
			for(int j = 0; j < Strategy.getColumns(testMemSize); j++) {
				if (testStrategy.getStrategy()[i][j] == testStrategy2.getStrategy()[i][j]) {
					testCommon++;
				}
			}
		}
		assertTrue(testCommon == (Strategy.getRows(testMemSize) * Strategy.getColumns(testMemSize) -
				Strategy.getNumberOfChanges(testMemSize)));
	}
	
	/**
	 * Test mutating a strategy (for memorySize 3).
	 */
	public void testMutation3() {
		int testMemSize = 3;
		Strategy testStrategy = new Strategy(testMemSize,
				new int[Strategy.getRows(testMemSize)][Strategy.getColumns(testMemSize)]);
		Strategy testStrategy2 = testStrategy.getMutation();
		
		//Test the same memory size
		assertTrue(testStrategy.getMemorySize() == testStrategy2.getMemorySize());
		
		//Test number of common elements
		int testCommon = 0;
		for(int i = 0; i < Strategy.getRows(testMemSize); i++) {
			for(int j = 0; j < Strategy.getColumns(testMemSize); j++) {
				if (testStrategy.getStrategy()[i][j] == testStrategy2.getStrategy()[i][j]) {
					testCommon++;
				}
			}
		}
		assertTrue(testCommon == (Strategy.getRows(testMemSize) * Strategy.getColumns(testMemSize) -
				Strategy.getNumberOfChanges(testMemSize)));
	}
	
	/**
	 * Test equals method.
	 */
	@Test
	public void testEquals1() {
		Strategy testStrategy = new Strategy(1);
		Strategy testStrategy2 = new Strategy(1);
		Strategy testStrategy3 = new Strategy(2);
		
		assertTrue(testStrategy.equals(testStrategy2));
		assertFalse(testStrategy.equals(testStrategy3));
		assertFalse(testStrategy.equals(99));
	}
	
	/**
	 * Test equals method.
	 */
	@Test
	public void testEquals2() {
		int[][] testArray2 = {
				{1, 0, 0, 0},
				{0, 1, 2, 1},
				{0, 1, 2, 1}
		};
		Strategy testStrategy = new Strategy(1, testArray2);
		assertTrue(testStrategy.equals(new Strategy(1)));
	}
}
