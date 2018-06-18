package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import pDilemma.Player;
import strategies.InformationItem;
import strategies.Strategy;

public class PlayerTest {

	/**
	 * Test whether constructor 1 functions correctly.
	 */
	@Test
	public void testConstructor() {
		Player testPlayer = new Player("George", 1);
		
		assertTrue(testPlayer.getId().equals("George"));
		assertTrue(testPlayer.getStrat().equals(new Strategy(1)));
		assertTrue(testPlayer.getCollectedPayoff() == 0);
		
		assertTrue(testPlayer.getLastRoundPlayed() == 0);
		assertTrue(testPlayer.getLastChoicesMe().size() == 0);
		assertTrue(testPlayer.getLastChoicesOpponent().size() == 0);
	}
	
	/**
	 * Test whether constructor 2 functions correctly.
	 */
	@Test
	public void testConstructor2() {
		Player testPlayer = new Player("George", new Strategy(3));
		
		assertTrue(testPlayer.getId().equals("George"));
		assertTrue(testPlayer.getStrat().equals(new Strategy(3)));
		assertTrue(testPlayer.getCollectedPayoff() == 0);
		assertTrue(testPlayer.getLastRoundPlayed() == 0);
		assertTrue(testPlayer.getLastChoicesMe().size() == 0);
		assertTrue(testPlayer.getLastChoicesOpponent().size() == 0);
		assertTrue(testPlayer.getAge() == 0);
	}
	
	/**
	 * Test whether constructor 2 functions correctly.
	 */
	@Test
	public void testConstructor3() {
		Player testPlayer2 = new Player("Claudia", new Strategy(1));
		
		assertTrue(testPlayer2.getId().equals("Claudia"));
		assertTrue(testPlayer2.getStrat().equals(new Strategy(1)));
		assertTrue(testPlayer2.getCollectedPayoff() == 0);
		assertTrue(testPlayer2.getLastRoundPlayed() == 0);
		assertTrue(testPlayer2.getLastChoicesMe().size() == 0);
		assertTrue(testPlayer2.getLastChoicesOpponent().size() == 0);
		assertTrue(testPlayer2.getAge() == 0);
	}
	
	/**
	 * Check that a strategy correctly updates its memory
	 */
	@Test
	public void testUpdateMemoryAndMakeMove() {
		Player testPlayer = new Player("Claudia", new Strategy(1));
		ArrayList<Integer> testPrevChoicesMe = new ArrayList<Integer>();
		testPrevChoicesMe.add(1);
		ArrayList<InformationItem> testPrevChoicesOpponent = new ArrayList<InformationItem>();
		testPrevChoicesOpponent.add(new InformationItem(2,0,0));
		
		testPlayer.updateMemoryAndMakeMove(24, testPrevChoicesMe, testPrevChoicesOpponent);
		assertTrue(testPlayer.getLastRoundPlayed() == 24);
		assertTrue(testPlayer.getLastChoicesMe().equals(testPrevChoicesMe));
		assertTrue(testPlayer.getLastChoicesOpponent().equals(testPrevChoicesOpponent));
	}
	
	/**
	 * Check whether a player can accurately keep track of their collected payoff.
	 */
	@Test
	public void testDumpPayoff() {
		Player testPlayer = new Player("George", 1);
		
		testPlayer.dumpPayoff(14);
		assertTrue(testPlayer.getCollectedPayoff() == 14);
		
		testPlayer.dumpPayoff(5);
		assertTrue(testPlayer.getCollectedPayoff() == 19);
	}
	
	/**
	 * Check whether a player can properly reset their collected payoff.
	 */
	@Test
	public void testPayoffReset() {
		Player testPlayer = new Player("George", 1);
		testPlayer.dumpPayoff(14);
		testPlayer.payoffReset();
		
		assertTrue(testPlayer.getCollectedPayoff() == 0);
	}
	
	/**
	 * Test whether a player can reset their memory correctly.
	 */
	@Test
	public void testMemoryReset() {
		Player testPlayer = new Player("Claudia", new Strategy(3));
		ArrayList<Integer> testPrevChoicesMe = new ArrayList<Integer>();
		testPrevChoicesMe.add(2);
		ArrayList<InformationItem> testPrevChoicesOpponent = new ArrayList<InformationItem>();
		testPrevChoicesOpponent.add(new InformationItem(-1,0,0));
		
		testPlayer.updateMemoryAndMakeMove(13, testPrevChoicesMe, testPrevChoicesOpponent);
		testPlayer.memoryReset();
		
		assertTrue(testPlayer.getLastRoundPlayed() == 0);
		assertTrue(testPlayer.getLastChoicesMe().equals(new ArrayList<Integer>()));
		assertTrue(testPlayer.getLastChoicesOpponent().equals(new ArrayList<InformationItem>()));
	}
	
	/**
	 * Test whether a player can reset their memory correctly.
	 */
	@Test
	public void testMemoryReset2() {
		Player testPlayer = new Player("Claudia", new Strategy(3));
		
		assertTrue(testPlayer.getAge() == 0);
		testPlayer.memoryReset();
		
		assertTrue(testPlayer.getAge() == 1);
		testPlayer.memoryReset();
		
		assertTrue(testPlayer.getAge() == 2);
		testPlayer.memoryReset();
	}
	
	/**
	 * Checks whether the reproduce() method returns a new player with a similar strategy and the same id.
	 */
	@Test
	public void testReproduce() {
		Player testPlayer = new Player("George", new Strategy(3));
		Player testPlayer2 = testPlayer.reproduce();
		
		assertTrue(testPlayer.getId().equals(testPlayer2.getId()));
		
		//TODO: for different memory lengths
		int testCommon = 0;
		int rows = Strategy.getRows(testPlayer.getStrat().getMemorySize());
		int columns = Strategy.getColumns(testPlayer.getStrat().getMemorySize());
		
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				if (testPlayer.getStrat().getStrategy()[i][j] == testPlayer2.getStrat().getStrategy()[i][j]) {
					testCommon++;
				}
			}
		}
		assertTrue(testCommon == rows * columns - 1);
	}
	
	/**
	 * Test player equals method.
	 */
	@Test
	public void testEquals1() {
		Player testPlayer = new Player("George", 1);
		Player testPlayer2 = new Player("George", 1);
		
		assertTrue(testPlayer.equals(testPlayer2));
	}
	
	/**
	 * Test player equals method (different id).
	 */
	@Test
	public void testEquals2() {
		Player testPlayer = new Player("George", 1);
		Player testPlayer2 = new Player("Tom", 1);
		
		assertFalse(testPlayer.equals(testPlayer2));
	}
	
	/**
	 * Test player equals method (different strategy).
	 */
	@Test
	public void testEquals3() {
		Player testPlayer = new Player("George", 1);
		Player testPlayer2 = new Player("George", 2);
		
		assertFalse(testPlayer.equals(testPlayer2));
	}
	
	/**
	 * Test player equals method (different collected payoff).
	 */
	@Test
	public void testEquals4() {
		Player testPlayer = new Player("George", 1);
		Player testPlayer2 = new Player("George", 1);
		
		testPlayer.dumpPayoff(5);
		assertFalse(testPlayer.equals(testPlayer2));
		testPlayer2.dumpPayoff(3);
		testPlayer2.dumpPayoff(2);
		assertTrue(testPlayer.equals(testPlayer2));
	}
	
	/**
	 * Test player equals method (different info about last round).
	 */
	@Test
	public void testEquals5() {
		Player testPlayer = new Player("George", 1);
		Player testPlayer2 = new Player("George", 1);
		
		ArrayList<Integer> testPrevChoicesMeP1 = new ArrayList<Integer>();
		ArrayList<InformationItem> testPrevChoicesOpponentP1 = new ArrayList<InformationItem>();
		ArrayList<Integer> testPrevChoicesMeP2 = new ArrayList<Integer>();
		ArrayList<InformationItem> testPrevChoicesOpponentP2 = new ArrayList<InformationItem>();
		
		testPrevChoicesMeP1.add(1);
		testPrevChoicesOpponentP1.add(new InformationItem(2,0,0));
		
		testPlayer.updateMemoryAndMakeMove(13, testPrevChoicesMeP1, testPrevChoicesOpponentP1);
		assertFalse(testPlayer.equals(testPlayer2));
		
		testPrevChoicesMeP2.add(1);
		testPrevChoicesOpponentP2.add(new InformationItem(2,0,0));
		
		testPlayer2.updateMemoryAndMakeMove(13, testPrevChoicesMeP2, testPrevChoicesOpponentP2);
		assertTrue(testPlayer.equals(testPlayer2));
		
		testPlayer2.updateMemoryAndMakeMove(12, testPrevChoicesMeP2, testPrevChoicesOpponentP2);
		assertFalse(testPlayer.equals(testPlayer2));
		
		testPrevChoicesMeP1.remove(0);
		testPrevChoicesMeP1.add(2);
		testPrevChoicesOpponentP1.remove(0);
		testPrevChoicesOpponentP1.add(new InformationItem(1, 0, 0));
		
		testPlayer.updateMemoryAndMakeMove(12, testPrevChoicesMeP1, testPrevChoicesOpponentP1);
		assertFalse(testPlayer.equals(testPlayer2));
		
		testPrevChoicesMeP1.remove(0);
		testPrevChoicesMeP1.add(1);
		
		testPlayer.updateMemoryAndMakeMove(12, testPrevChoicesMeP1, testPrevChoicesOpponentP1);
		assertFalse(testPlayer.equals(testPlayer2));
	}
	
	/**
	 * Test player equals method (comparing with an Object that is not a Strategy).
	 */
	@Test
	public void testEquals6() {
		Player testPlayer = new Player("George", 1);
		assertFalse(testPlayer.equals(6));
	}
	
	/**
	 * Test player equals method (different ages).
	 */
	@Test
	public void testEquals7() {
		Player testPlayer = new Player("George", 1);
		Player testPlayer2 = new Player("George", 1);
		
		assertTrue(testPlayer.equals(testPlayer2));
		testPlayer.memoryReset();
		assertFalse(testPlayer.equals(testPlayer2));
	}
}
