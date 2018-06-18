package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import pDilemma.Game;
import pDilemma.Player;
import pDilemma.RoundRecord;
import pDilemma.Tourney;
import strategies.Strategy;

public class RoundRecordTest {

	/**
	 * Check whether a RoundRecord correctly updates after being given one round of PD.
	 */
	@Test
	public void testAddResult1() {
		RoundRecord testRecord = new RoundRecord(2);
		
		assertTrue(testRecord.getAmountOfCC() == 0);
		assertTrue(testRecord.getAmountOfCD() == 0);
		assertTrue(testRecord.getAmountOfDC() == 0);
		assertTrue(testRecord.getAmountOfDD() == 0);
		
		
	}
	
	/**
	 * Check whether a RoundRecord correctly updates after being given one round of PD.
	 */
	@Test
	public void testAddResult2() {
		RoundRecord testRecord = new RoundRecord(2);
		
		testRecord.addResult(1, 1);
		assertTrue(testRecord.getAmountOfCC() == 1);
		assertTrue(testRecord.getAmountOfCD() == 0);
		assertTrue(testRecord.getAmountOfDC() == 0);
		assertTrue(testRecord.getAmountOfDD() == 0);
		
		testRecord.addResult(1, 2);
		assertTrue(testRecord.getAmountOfCC() == 1);
		assertTrue(testRecord.getAmountOfCD() == 1);
		assertTrue(testRecord.getAmountOfDC() == 0);
		assertTrue(testRecord.getAmountOfDD() == 0);
	}
	
	/**
	 * Check whether a RoundRecord correctly updates after being given one round of PD.
	 */
	@Test
	public void testAddResult3() {
		RoundRecord testRecord = new RoundRecord(2);
		
		testRecord.addResult(2, 1);
		assertTrue(testRecord.getAmountOfCC() == 0);
		assertTrue(testRecord.getAmountOfCD() == 0);
		assertTrue(testRecord.getAmountOfDC() == 1);
		assertTrue(testRecord.getAmountOfDD() == 0);
		
		testRecord.addResult(2, 2);
		assertTrue(testRecord.getAmountOfCC() == 0);
		assertTrue(testRecord.getAmountOfCD() == 0);
		assertTrue(testRecord.getAmountOfDC() == 1);
		assertTrue(testRecord.getAmountOfDD() == 1);
	}

	/**
	 * Check whether RoundRecord correctly records the outcomes of each round when given a GameRecord object.
	 */
	@Test
	public void testAddGameResult() {
		Player testPlayer = new Player("TFT", 1);
		Player testPlayer2 = new Player("AD", 3);
		Game testGame = new Game(testPlayer, testPlayer2, 1);
		testGame.playRound();
		testGame.playRound();
		testGame.playRound();
		
		RoundRecord testRecord = new RoundRecord(1);
		testRecord.addGameResult(testGame.getRec());
		
		assertTrue(testRecord.getAmountOfCC() == 0);
		assertTrue(testRecord.getAmountOfCD() == 1);
		assertTrue(testRecord.getAmountOfDC() == 0);
		assertTrue(testRecord.getAmountOfDD() == 2);
	}
	
	/**
	 * Check whether RoundRecord correctly records player payoffs.
	 */
	@Test
	public void testRecordPlayerBasedInfo1() {
		Player testPlayer = new Player("TFT", 1);
		Player testPlayer2 = new Player("AD", 3);
		Game testGame = new Game(testPlayer, testPlayer2, 1);
		testGame.playRound();
		testGame.playRound();
		testGame.playRound();
		
		ArrayList<Player> testPlayerList = new ArrayList<Player>();
		testPlayerList.add(testPlayer);
		testPlayerList.add(testPlayer2);
		RoundRecord testRecord = new RoundRecord(1);
		testRecord.recordPlayerBasedInfo(testPlayerList);
		
		//Test recording of payoff information
		int testLowestPayoff = testGame.getPayS() + 2 * testGame.getPayP();
		int testHighestPayoff = testGame.getPayT() + 2 * testGame.getPayP();
		double testAveragePayoff = (double) (testLowestPayoff + testHighestPayoff) / (double) 2;
		assertTrue(testRecord.getLowestPayoff() == testLowestPayoff);
		assertTrue(testRecord.getHighestPayoff() == testHighestPayoff);
		assertTrue(testRecord.getAveragePayoff() > (testAveragePayoff - 0.1));
		assertTrue(testRecord.getAveragePayoff() < (testAveragePayoff + 0.1));
	}
	
	/**
	 * Check whether RoundRecord correctly records player strategies.
	 */
	@Test
	public void testRecordPlayerBasedInfo2() {
		ArrayList<Player> testPlayerList = new ArrayList<Player>();
		testPlayerList.add(new Player("TFT", 1));
		testPlayerList.add(new Player("AD", 3));
		RoundRecord testRecord = new RoundRecord(1);
		testRecord.recordPlayerBasedInfo(testPlayerList);
		
		//Expected average strategy values
		double[][] expectedV = new double[][] {
			{1.5, 0, 0, 0},
			{0, 1.5, 2, 1.5},
			{0, 1.5, 2, 1.5}
		};
		
		//Test recording strategy based information (yes this is a while loop, I know)
		for(int i = 0; i < Strategy.getRows(1); i++) {
			for(int j = 0; j < Strategy.getColumns(1); j++) {
				assertTrue(Math.abs(testRecord.getAverageStrategyMat()[i][j] - expectedV[i][j]) < 0.01);
			}
		}
	}
	
	/**
	 * Check whether RoundRecord correctly records player ages.
	 */
	@Test
	public void testRecordPlayerBasedInfo3() {
		ArrayList<Player> testPlayerList = new ArrayList<Player>();
		testPlayerList.add(new Player("TFT", 1));
		testPlayerList.add(new Player("AD", 3));
		Tourney testTourney = new Tourney(testPlayerList, 1);
		testTourney.repeatedlyPlayTourney(5, 1);
		assertTrue(testTourney.getRecord().getRoundsData().get(0).getTotalAge() == 2);
		testTourney.repeatedlyPlayTourney(5, 1);
		assertTrue(testTourney.getRecord().getRoundsData().get(1).getTotalAge() == 3);
	}
	
	/**
	 * Check whether RoundRecord correctly records player ages.
	 */
	@Test
	public void testRecordPlayerBasedInfo4() {
		ArrayList<Player> testPlayerList = new ArrayList<Player>();
		testPlayerList.add(new Player("TFT", 1));
		testPlayerList.add(new Player("AD", 3));
		testPlayerList.add(new Player("AD", 3));
		testPlayerList.add(new Player("AD", 3));
		Tourney testTourney = new Tourney(testPlayerList, 1);
		testTourney.repeatedlyPlayTourney(5, 1);
		assertTrue(testTourney.getRecord().getRoundsData().get(0).getTotalAge() == 12);
		testTourney.repeatedlyPlayTourney(5, 1);
		assertTrue(testTourney.getRecord().getRoundsData().get(1).getTotalAge() == 18);
	}
	
	/**
	 * Test equals method
	 */
	@Test
	public void testEquals1() {
		RoundRecord testRoundRecord = new RoundRecord(1);
		RoundRecord testRoundRecord2 = new RoundRecord(1);
		assertTrue(testRoundRecord.equals(testRoundRecord2));
		
		//Different object
		assertFalse(testRoundRecord.equals(0));
	}
	
	/**
	 * Test equals method (different CC, CD, DC, DD)
	 */
	@Test
	public void testEquals2() {
		RoundRecord testRoundRecord = new RoundRecord(1);
		RoundRecord testRoundRecord2 = new RoundRecord(1);
		
		testRoundRecord.addResult(1, 1);
		assertFalse(testRoundRecord.equals(testRoundRecord2));
		testRoundRecord2.addResult(1, 1);
		assertTrue(testRoundRecord.equals(testRoundRecord2));
		
		testRoundRecord.addResult(1, 2);
		assertFalse(testRoundRecord2.equals(testRoundRecord));
		testRoundRecord2.addResult(1, 2);
		assertTrue(testRoundRecord2.equals(testRoundRecord));
		
		testRoundRecord.addResult(2, 1);
		assertFalse(testRoundRecord.equals(testRoundRecord2));
		testRoundRecord2.addResult(2, 1);
		assertTrue(testRoundRecord.equals(testRoundRecord2));
		
		testRoundRecord.addResult(2, 2);
		assertFalse(testRoundRecord2.equals(testRoundRecord));
		testRoundRecord2.addResult(2, 2);
		assertTrue(testRoundRecord2.equals(testRoundRecord));
	}
}