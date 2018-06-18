package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import pDilemma.GameRecord;

public class GameRecordTest {

	/**
	 * Check whether a game record is initialised correctly.
	 */
	@Test
	public void testInitialGameRecord() {
		GameRecord testRecord = new GameRecord();
		
		assertTrue(testRecord.getNumberOfRoundsPlayed() == 0);
		assertTrue(testRecord.getDecisionsP1().size() == 0);
		assertTrue(testRecord.getDecisionsP2().size() == 0);
		assertTrue(testRecord.getPayoffsP1().size() == 0);
		assertTrue(testRecord.getPayoffsP2().size() == 0);
	}

	
	/**
	 * Check whether GameRecord stores results correctly.
	 */
	@Test
	public void testAfterRound1() {
		GameRecord testRecord = new GameRecord();
		testRecord.dumpRound(5, 1, 2, 1);
		
		assertTrue(testRecord.getNumberOfRoundsPlayed() == 1);
		assertTrue(testRecord.getDecisionsP1().get(0) == 2);
		assertTrue(testRecord.getDecisionsP2().get(0) == 1);
		assertTrue(testRecord.getPayoffsP1().get(0) == 5);
		assertTrue(testRecord.getPayoffsP2().get(0) == 1);
	}
	
	/**
	 * Test printing results.
	 */
	@Test
	public void testDisplayResults1() {
		GameRecord testRecord = new GameRecord();
		testRecord.dumpRound(5, 1, 2, 1);
		testRecord.dumpRound(2, 2, 2, 2);
		testRecord.dumpRound(4, 4, 1, 1);
		
		testRecord.displayResultsShort();
	}
	
	/**
	 * Test printing elaborate results.
	 */
	@Test
	public void testDisplayResults2() {
		GameRecord testRecord = new GameRecord();
		testRecord.dumpRound(5, 1, 2, 1);
		testRecord.dumpRound(2, 2, 2, 2);
		testRecord.dumpRound(4, 4, 1, 1);
		
		testRecord.displayResultsLong();
	}
}
