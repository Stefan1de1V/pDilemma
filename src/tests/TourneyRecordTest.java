package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import pDilemma.Player;
import pDilemma.RoundRecord;
import pDilemma.Tourney;
import pDilemma.TourneyRecord;
import strategies.Strategy;

public class TourneyRecordTest {

	/**
	 * Check whether a TourneyRecord has the appropriate amount of RoundRecords.
	 */
	@Test
	public void testNumberOfRoundRecords() {
		ArrayList<Player> testPlayerList = new ArrayList<Player>();
		for(int i = 0; i < 5; i++) {
			testPlayerList.add(new Player("Charles", 1));
		}
		
		Tourney testTourney = new Tourney(testPlayerList, 1);
		assertTrue(testTourney.getRecord().getNumberOfRoundsPlayed() == 0);
		assertTrue(testTourney.getRecord().getRoundsData().size() == 0);
		testTourney.repeatedlyPlayTourney(20, 4);
		assertTrue(testTourney.getRecord().getNumberOfRoundsPlayed() == 4);
		assertTrue(testTourney.getRecord().getRoundsData().size() == 4);
	}

	/**
	 * Test analyse1 method.
	 */
	@Test
	public void testAnalyse1T1() {
		int testMemorySize = 1;
		int testNumberOfPlayers = 30;
		int testNumberOfRoundsPerGame = 300;
		TourneyRecord testRecord = new TourneyRecord(testMemorySize);
		RoundRecord testRR1 = new RoundRecord(testMemorySize);
		for(int i = 0; i < 4; i++) {
			testRR1.addResult(1, 1);
		}
		testRecord.addRoundResult(testRR1);
		RoundRecord testRR2 = new RoundRecord(testMemorySize);
		for(int i = 0; i < 6; i++) {
			testRR2.addResult(1, 1);
		}
		testRecord.addRoundResult(testRR2);
		testRecord.analyzeGivenRecords(testNumberOfPlayers, testNumberOfRoundsPerGame);
		
		assertTrue(testRecord.getNumberOfCCs().size() == 2);
		assertTrue(testRecord.getNumberOfCDs().size() == 2);
		assertTrue(testRecord.getNumberOfDCs().size() == 2);
		assertTrue(testRecord.getNumberOfDDs().size() == 2);
		
		assertTrue(testRecord.getNumberOfCCs().get(0) == 4);
		assertTrue(testRecord.getNumberOfCCs().get(1) == 6);
		
		assertTrue(testRecord.getNumberOfCDs().get(0) == 0);
		assertTrue(testRecord.getNumberOfCDs().get(1) == 0);
		
		assertTrue(testRecord.getNumberOfDCs().get(0) == 0);
		assertTrue(testRecord.getNumberOfDCs().get(1) == 0);
		
		assertTrue(testRecord.getNumberOfDDs().get(0) == 0);
		assertTrue(testRecord.getNumberOfDDs().get(1) == 0);		
	}
	
	/**
	 * Test analyse1 method.
	 */
	@Test
	public void testAnalyse1T2() {
		int testMemorySize = 1;
		int testNumberOfPlayers = 30;
		int testNumberOfRoundsPerGame = 300;
		TourneyRecord testRecord = new TourneyRecord(testMemorySize);
		RoundRecord testRR1 = new RoundRecord(testMemorySize);
		for(int i = 0; i < 40; i++) {
			testRR1.addResult(1, 2);
		}
		testRecord.addRoundResult(testRR1);
		RoundRecord testRR2 = new RoundRecord(testMemorySize);
		for(int i = 0; i < 62; i++) {
			testRR2.addResult(1, 2);
		}
		testRecord.addRoundResult(testRR2);
		testRecord.analyzeGivenRecords(testNumberOfPlayers, testNumberOfRoundsPerGame);
		
		assertTrue(testRecord.getNumberOfCCs().get(0) == 0);
		assertTrue(testRecord.getNumberOfCCs().get(1) == 0);
		
		assertTrue(testRecord.getNumberOfCDs().get(0) == 40);
		assertTrue(testRecord.getNumberOfCDs().get(1) == 62);
		
		assertTrue(testRecord.getNumberOfDCs().get(0) == 0);
		assertTrue(testRecord.getNumberOfDCs().get(1) == 0);
		
		assertTrue(testRecord.getNumberOfDDs().get(0) == 0);
		assertTrue(testRecord.getNumberOfDDs().get(1) == 0);		
	}
	
	/**
	 * Test analyse1 method.
	 */
	@Test
	public void testAnalyse1T3() {
		int testMemorySize = 1;
		int testNumberOfPlayers = 30;
		int testNumberOfRoundsPerGame = 300;
		TourneyRecord testRecord = new TourneyRecord(testMemorySize);
		RoundRecord testRR1 = new RoundRecord(testMemorySize);
		for(int i = 0; i < 18; i++) {
			testRR1.addResult(2, 1);
		}
		testRecord.addRoundResult(testRR1);
		RoundRecord testRR2 = new RoundRecord(testMemorySize);
		for(int i = 0; i < 12; i++) {
			testRR2.addResult(2, 1);
		}
		testRecord.addRoundResult(testRR2);
		testRecord.analyzeGivenRecords(testNumberOfPlayers, testNumberOfRoundsPerGame);
		
		assertTrue(testRecord.getNumberOfCCs().get(0) == 0);
		assertTrue(testRecord.getNumberOfCCs().get(1) == 0);
		
		assertTrue(testRecord.getNumberOfCDs().get(0) == 0);
		assertTrue(testRecord.getNumberOfCDs().get(1) == 0);
		
		assertTrue(testRecord.getNumberOfDCs().get(0) == 18);
		assertTrue(testRecord.getNumberOfDCs().get(1) == 12);
		
		assertTrue(testRecord.getNumberOfDDs().get(0) == 0);
		assertTrue(testRecord.getNumberOfDDs().get(1) == 0);		
	}
	
	/**
	 * Test analyse1 method.
	 */
	@Test
	public void testAnalyse1T4() {
		int testMemorySize = 1;
		int testNumberOfPlayers = 30;
		int testNumberOfRoundsPerGame = 300;
		TourneyRecord testRecord = new TourneyRecord(testMemorySize);
		RoundRecord testRR1 = new RoundRecord(testMemorySize);
		for(int i = 0; i < 7; i++) {
			testRR1.addResult(2, 2);
		}
		testRecord.addRoundResult(testRR1);
		RoundRecord testRR2 = new RoundRecord(testMemorySize);
		for(int i = 0; i < 77; i++) {
			testRR2.addResult(2, 2);
		}
		testRecord.addRoundResult(testRR2);
		testRecord.analyzeGivenRecords(testNumberOfPlayers, testNumberOfRoundsPerGame);
		
		assertTrue(testRecord.getNumberOfCCs().get(0) == 0);
		assertTrue(testRecord.getNumberOfCCs().get(1) == 0);
		
		assertTrue(testRecord.getNumberOfCDs().get(0) == 0);
		assertTrue(testRecord.getNumberOfCDs().get(1) == 0);
		
		assertTrue(testRecord.getNumberOfDCs().get(0) == 0);
		assertTrue(testRecord.getNumberOfDCs().get(1) == 0);
		
		assertTrue(testRecord.getNumberOfDDs().get(0) == 7);
		assertTrue(testRecord.getNumberOfDDs().get(1) == 77);		
	}
	
	/**
	 * Test analyse 2 method.
	 */
	@Test
	public void testAnalyse2T1() {
		//int numberOfTestOutcomes1 = (int) Math.ceil((double) Tourney.amountOfGamesPerRound(30, 300) * 0.51);
		int testMemorySize = 2;
		int testNumberOfPlayers = 30;
		int testNumberOfRoundsPerGame = 300;
		TourneyRecord testRecord = new TourneyRecord(testMemorySize);
		testRecord.addRoundResult(new RoundRecord(testMemorySize));
		testRecord.addRoundResult(new RoundRecord(testMemorySize));
		testRecord.analyzeGivenRecords(testNumberOfPlayers, testNumberOfRoundsPerGame);
		
		assertTrue(testRecord.getDominantCC() == 0);
		assertTrue(testRecord.getDominantCDC() == 0);
		assertTrue(testRecord.getDominantDD() == 0);
		assertTrue(testRecord.getNotPrevalentCount() == 2);
		assertTrue(testRecord.getAverageRoundNoPrevalent() < 1.51);
		assertTrue(testRecord.getAverageRoundNoPrevalent() > 1.49);
	}
	
	/**
	 * Test analyse 2 method.
	 */
	@Test
	public void testAnalyse2T2() {
		int numberOfTestOutcomeP = (int) Math.ceil((double) Tourney.amountOfGamesPerRound(30, 300) * 0.51);
		int numberOfTestOutcomeD = (int) Math.ceil((double) Tourney.amountOfGamesPerRound(30, 300) * 0.70);
		int testMemorySize = 2;
		int testNumberOfPlayers = 30;
		int testNumberOfRoundsPerGame = 300;
		TourneyRecord testRecord = new TourneyRecord(testMemorySize);
		RoundRecord testR1 = new RoundRecord(testMemorySize);
		for(int i = 0; i < numberOfTestOutcomeP; i++) {
			testR1.addResult(1, 1);
		}
		testRecord.addRoundResult(testR1);
		RoundRecord testR2 = new RoundRecord(testMemorySize);
		for(int i = 0; i < numberOfTestOutcomeD; i++) {
			testR2.addResult(1, 1);
		}
		testRecord.addRoundResult(testR2);
		testRecord.analyzeGivenRecords(testNumberOfPlayers, testNumberOfRoundsPerGame);
		
		assertTrue(testRecord.getDominantCC() == 1);
		assertTrue(testRecord.getDominantCDC() == 0);
		assertTrue(testRecord.getDominantDD() == 0);
		assertTrue(testRecord.getNotPrevalentCount() == 0);
		assertTrue(testRecord.getAverageRoundNoPrevalent() < 0.01);
		assertTrue(testRecord.getAverageRoundNoPrevalent() > -0.01);
	}
	
	/**
	 * Test analyse 2 method.
	 */
	@Test
	public void testAnalyse2T3() {
		int numberOfTestOutcomeP = (int) Math.ceil((double) Tourney.amountOfGamesPerRound(30, 300) * 0.51);
		int numberOfTestOutcomeD = (int) Math.ceil((double) Tourney.amountOfGamesPerRound(30, 300) * 0.35);
		int testMemorySize = 3;
		int testNumberOfPlayers = 30;
		int testNumberOfRoundsPerGame = 300;
		TourneyRecord testRecord = new TourneyRecord(testMemorySize);
		RoundRecord testR1 = new RoundRecord(testMemorySize);
		for(int i = 0; i < numberOfTestOutcomeD; i++) {
			testR1.addResult(1, 2);
		}
		for(int i = 0; i < numberOfTestOutcomeD; i++) {
			testR1.addResult(2, 1);
		}
		testRecord.addRoundResult(testR1);
		RoundRecord testR2 = new RoundRecord(testMemorySize);
		for(int i = 0; i < numberOfTestOutcomeP; i++) {
			testR2.addResult(1, 2);
		}
		testRecord.addRoundResult(testR2);
		testRecord.analyzeGivenRecords(testNumberOfPlayers, testNumberOfRoundsPerGame);
		
		assertTrue(testRecord.getDominantCC() == 0);
		assertTrue(testRecord.getDominantCDC() == 1);
		assertTrue(testRecord.getDominantDD() == 0);
		assertTrue(testRecord.getNotPrevalentCount() == 0);
		assertTrue(testRecord.getAverageRoundNoPrevalent() < 0.01);
		assertTrue(testRecord.getAverageRoundNoPrevalent() > -0.01);
	}
	
	/**
	 * Test analyse 2 method.
	 */
	@Test
	public void testAnalyse2T4() {
		int numberOfTestOutcomeD = (int) Math.ceil((double) Tourney.amountOfGamesPerRound(30, 300) * 0.70);
		int testMemorySize = 3;
		int testNumberOfPlayers = 30;
		int testNumberOfRoundsPerGame = 300;
		TourneyRecord testRecord = new TourneyRecord(testMemorySize);
		RoundRecord testR1 = new RoundRecord(testMemorySize);
		for(int i = 0; i < numberOfTestOutcomeD; i++) {
			testR1.addResult(2, 2);
		}
		testRecord.addRoundResult(testR1);
		RoundRecord testR2 = new RoundRecord(testMemorySize);
		for(int i = 0; i < numberOfTestOutcomeD; i++) {
			testR2.addResult(2, 2);
		}
		testRecord.addRoundResult(testR2);
		testRecord.analyzeGivenRecords(testNumberOfPlayers, testNumberOfRoundsPerGame);
		
		assertTrue(testRecord.getDominantCC() == 0);
		assertTrue(testRecord.getDominantCDC() == 0);
		assertTrue(testRecord.getDominantDD() == 2);
		assertTrue(testRecord.getNotPrevalentCount() == 0);
		assertTrue(testRecord.getAverageRoundNoPrevalent() < 0.01);
		assertTrue(testRecord.getAverageRoundNoPrevalent() > -0.01);
	}
	
	/**
	 * Test analyse 2 method.
	 */
	@Test
	public void testAnalyse2T5() {
		int numberOfTestOutcomeN = (int) Math.ceil((double) Tourney.amountOfGamesPerRound(30, 300) * 0.20);
		int numberOfTestOutcomeD = (int) Math.ceil((double) Tourney.amountOfGamesPerRound(30, 300) * 0.70);
		int testMemorySize = 3;
		int testNumberOfPlayers = 30;
		int testNumberOfRoundsPerGame = 300;
		TourneyRecord testRecord = new TourneyRecord(testMemorySize);
		RoundRecord testR1 = new RoundRecord(testMemorySize);
		for(int i = 0; i < numberOfTestOutcomeN; i++) {
			testR1.addResult(1, 1);
		}
		for(int i = 0; i < numberOfTestOutcomeN; i++) {
			testR1.addResult(1, 2);
		}
		for(int i = 0; i < numberOfTestOutcomeN; i++) {
			testR1.addResult(2, 1);
		}
		testRecord.addRoundResult(testR1);
		RoundRecord testR2 = new RoundRecord(testMemorySize);
		for(int i = 0; i < numberOfTestOutcomeD; i++) {
			testR2.addResult(2, 1);
		}
		testRecord.addRoundResult(testR2);
		testRecord.analyzeGivenRecords(testNumberOfPlayers, testNumberOfRoundsPerGame);
		
		assertTrue(testRecord.getDominantCC() == 0);
		assertTrue(testRecord.getDominantCDC() == 1);
		assertTrue(testRecord.getDominantDD() == 0);
		assertTrue(testRecord.getNotPrevalentCount() == 1);
		assertTrue(testRecord.getAverageRoundNoPrevalent() < 1.01);
		assertTrue(testRecord.getAverageRoundNoPrevalent() > 0.99);
	}
	
	/**
	 * Test analyse 3 method.
	 */
	@Test
	public void testAnalyse3T1() {
		int testMemorySize = 3;
		int testNumberOfPlayers = 30;
		int testNumberOfRoundsPerGame = 300;
		
		TourneyRecord testRecord = new TourneyRecord(testMemorySize);
		RoundRecord testR1 = new RoundRecord(testMemorySize);
		testRecord.addRoundResult(testR1);
		RoundRecord testR2 = new RoundRecord(testMemorySize);
		testRecord.addRoundResult(testR2);
		testRecord.analyzeGivenRecords(testNumberOfPlayers, testNumberOfRoundsPerGame);
		
		assertTrue(testRecord.getFirstHitCC() == 0);
		assertTrue(testRecord.getFirstHitDD() == 0);
	}
	
	/**
	 * Test analyse 3 method.
	 */
	@Test
	public void testAnalyse3T2() {
		int testMemorySize = 3;
		int testNumberOfPlayers = 30;
		int testNumberOfRoundsPerGame = 300;
		
		TourneyRecord testRecord = new TourneyRecord(testMemorySize);
		RoundRecord testR1 = new RoundRecord(testMemorySize);
		for(int i = 0; i < Tourney.amountOfGamesPerRound(testNumberOfPlayers, testNumberOfRoundsPerGame); i++) {
			testR1.addResult(1, 1);
		}
		testRecord.addRoundResult(testR1);
		RoundRecord testR2 = new RoundRecord(testMemorySize);
		testRecord.addRoundResult(testR2);
		testRecord.analyzeGivenRecords(testNumberOfPlayers, testNumberOfRoundsPerGame);
		
		assertTrue(testRecord.getFirstHitCC() == 1);
		assertTrue(testRecord.getFirstHitDD() == 0);
	}
	
	/**
	 * Test analyse 3 method.
	 */
	@Test
	public void testAnalyse3T3() {
		int testMemorySize = 3;
		int testNumberOfPlayers = 30;
		int testNumberOfRoundsPerGame = 300;
		
		TourneyRecord testRecord = new TourneyRecord(testMemorySize);
		RoundRecord testR1 = new RoundRecord(testMemorySize);
		testRecord.addRoundResult(testR1);
		RoundRecord testR2 = new RoundRecord(testMemorySize);
		for(int i = 0; i < Tourney.amountOfGamesPerRound(testNumberOfPlayers, testNumberOfRoundsPerGame); i++) {
			testR2.addResult(2, 2);
		}
		testRecord.addRoundResult(testR2);
		testRecord.analyzeGivenRecords(testNumberOfPlayers, testNumberOfRoundsPerGame);
		
		assertTrue(testRecord.getFirstHitCC() == 0);
		assertTrue(testRecord.getFirstHitDD() == 2);
	}
	
	/**
	 * Test analyse 3 method.
	 */
	@Test
	public void testAnalyse3T4() {
		int testMemorySize = 3;
		int testNumberOfPlayers = 30;
		int testNumberOfRoundsPerGame = 300;
		
		TourneyRecord testRecord = new TourneyRecord(testMemorySize);
		RoundRecord testR1 = new RoundRecord(testMemorySize);
		for(int i = 0; i < Tourney.amountOfGamesPerRound(testNumberOfPlayers, testNumberOfRoundsPerGame); i++) {
			testR1.addResult(2, 1);
		}
		testRecord.addRoundResult(testR1);
		RoundRecord testR2 = new RoundRecord(testMemorySize);
		for(int i = 0; i < Tourney.amountOfGamesPerRound(testNumberOfPlayers, testNumberOfRoundsPerGame); i++) {
			testR2.addResult(1, 2);
		}
		testRecord.addRoundResult(testR2);
		testRecord.analyzeGivenRecords(testNumberOfPlayers, testNumberOfRoundsPerGame);
		
		assertTrue(testRecord.getFirstHitCC() == 0);
		assertTrue(testRecord.getFirstHitDD() == 0);
	}
	
	/**
	 * Test analyse 4 method.
	 */
	@Test
	public void testAnalyse4T1() {
		int testMemorySize = 3;
		TourneyRecord testRecord = new TourneyRecord(testMemorySize);
		int irrelevant = 0;
		
		RoundRecord testR1 = new RoundRecord(testMemorySize);
		Player testPlayer1 = new Player("name", (new Strategy(testMemorySize, new int[Strategy.getRows(testMemorySize)][Strategy.getColumns(testMemorySize)])));
		Player testPlayer2 = new Player("name2", (new Strategy(testMemorySize, new int[Strategy.getRows(testMemorySize)][Strategy.getColumns(testMemorySize)])));
		Player testPlayer3 = new Player("name3", (new Strategy(testMemorySize, new int[Strategy.getRows(testMemorySize)][Strategy.getColumns(testMemorySize)])));
		ArrayList<Player> testPlayerList1 = new ArrayList<Player>();
		testPlayer1.dumpPayoff(6);
		testPlayer2.dumpPayoff(22);
		testPlayer3.dumpPayoff(0);
		testPlayerList1.add(testPlayer1);
		testPlayerList1.add(testPlayer2);
		testPlayerList1.add(testPlayer3);
		testR1.recordPlayerBasedInfo(testPlayerList1);
		testRecord.addRoundResult(testR1);
		
		RoundRecord testR2 = new RoundRecord(testMemorySize);
		//For this test applies that we do not need to resetPayoff players if you make new ones
		Player testPlayer4 = new Player("name4", (new Strategy(testMemorySize, new int[Strategy.getRows(testMemorySize)][Strategy.getColumns(testMemorySize)])));
		Player testPlayer5 = new Player("name5", (new Strategy(testMemorySize, new int[Strategy.getRows(testMemorySize)][Strategy.getColumns(testMemorySize)])));
		Player testPlayer6 = new Player("name6", (new Strategy(testMemorySize, new int[Strategy.getRows(testMemorySize)][Strategy.getColumns(testMemorySize)])));
		ArrayList<Player> testPlayerList2 = new ArrayList<Player>();
		testPlayer4.dumpPayoff(37);
		testPlayer5.dumpPayoff(4);
		testPlayer6.dumpPayoff(14);
		testPlayerList2.add(testPlayer4);
		testPlayerList2.add(testPlayer5);
		testPlayerList2.add(testPlayer6);
		testR2.recordPlayerBasedInfo(testPlayerList2);
		testRecord.addRoundResult(testR2);
		
		testRecord.analyzeGivenRecords(irrelevant, irrelevant);
		assertTrue(testRecord.getLowestPayoffs().size() == 2);
		assertTrue(testRecord.getLowestPayoffs().get(0) == 0);
		assertTrue(testRecord.getLowestPayoffs().get(1) == 4);
		
		assertTrue(testRecord.getHighestPayoffs().size() == 2);
		assertTrue(testRecord.getHighestPayoffs().get(0) == 22);
		assertTrue(testRecord.getHighestPayoffs().get(1) == 37);
		
		assertTrue(testRecord.getAveragePayoffs().size() == 2);
		assertTrue(testRecord.getAveragePayoffs().get(0) > 9.33);
		assertTrue(testRecord.getAveragePayoffs().get(0) < 9.34);
		assertTrue(testRecord.getAveragePayoffs().get(1) > 18.33);
		assertTrue(testRecord.getAveragePayoffs().get(1) < 18.34);
	}
	
	/**
	 * Test analyse 5 method.
	 */
	@Test
	public void testAnalyse5T1() {
		int testMemorySize = 1;
		TourneyRecord testRecord = new TourneyRecord(testMemorySize);
		int irrelevant = 0;
		
		//Strategy tables
		int[][] testTable1 = {
				{1, 0, 0, 0},
				{0, 1, 1, 1},
				{0, 1, 1, 1}
		};
		int[][] testTable2 = {
				{1, 0, 0, 0},
				{0, 2, 2, 1},
				{0, 1, 1, 2}
		};
		int[][] testTable3 = {
				{2, 1, 0, 0},
				{0, 1, 2, 1},
				{0, 1, 1, 2},
		};
		int[][] testTable4 = {
				{2, 0, 0, 0},
				{0, 2, 2, 2},
				{0, 2, 2, 2},
		};
//		double[][] result1 = {
//				{1.33, 0.33, 0.00, 0.00},
//				{0.00, 1.33, 1.67, 1.00},
//				{0.00, 1.00, 1.00, 1.67}
//		};
		double[][] result2 = {
				{1.67, 0.00, 0.00, 0.00},
				{0.00, 1.67, 1.67, 1.67},
				{0.00, 1.67, 1.67, 1.67}
		};
		
		//Round 1 testPlayer list
		Player testPlayer1 = new Player("name", (new Strategy(testMemorySize, testTable1)));
		Player testPlayer2 = new Player("name2", (new Strategy(testMemorySize, testTable2)));
		Player testPlayer3 = new Player("name3", (new Strategy(testMemorySize, testTable3)));
		ArrayList<Player> testPlayerList1 = new ArrayList<Player>();

		testPlayerList1.add(testPlayer1);
		testPlayerList1.add(testPlayer2);
		testPlayerList1.add(testPlayer3);
		
		//Round 2 testPlayer list
		Player testPlayer4 = new Player("name4", (new Strategy(testMemorySize, testTable1)));
		Player testPlayer5 = new Player("name5", (new Strategy(testMemorySize, testTable4)));
		Player testPlayer6 = new Player("name6", (new Strategy(testMemorySize, testTable4)));
		ArrayList<Player> testPlayerList2 = new ArrayList<Player>();
		
		testPlayerList2.add(testPlayer4);
		testPlayerList2.add(testPlayer5);
		testPlayerList2.add(testPlayer6);
		
		//Fill testRecord
		RoundRecord testR1 = new RoundRecord(testMemorySize);
		testR1.recordPlayerBasedInfo(testPlayerList1);
		testRecord.addRoundResult(testR1);
		
		RoundRecord testR2 = new RoundRecord(testMemorySize);
		testR2.recordPlayerBasedInfo(testPlayerList2);
		testRecord.addRoundResult(testR2);
		testRecord.analyzeGivenRecords(irrelevant, irrelevant);
		
		//Test stuff (the return of the for loop)
		for(int i = 0; i < Strategy.getRows(testMemorySize); i++) {
			for(int j = 0; j < Strategy.getColumns(testMemorySize); j++) {
				//assertTrue(Math.abs(testRecord.getAverageStrategiesAfterEachRound()[0][i][j] - result1[i][j]) < 0.01);
				assertEquals(result2[i][j], testRecord.getAveStrategyAfterLRound()[i][j], 0.01);
			}
		}
	}
	
	/**
	 * Test analyse 6 method.
	 */
	@Test
	public void testAnalyse6T1() {
		int testMemorySize = 1; //Otherwise we can't use the standard strategies
		TourneyRecord testRecord = new TourneyRecord(testMemorySize);
		int irrelevant = 0;
		
		RoundRecord testR1 = new RoundRecord(testMemorySize);
		Player testPlayer1 = new Player("name", new Strategy(1));
		Player testPlayer2 = new Player("name2", new Strategy(1));
		Player testPlayer3 = new Player("name3", new Strategy(1));
		ArrayList<Player> testPlayerList1 = new ArrayList<Player>();
		testPlayerList1.add(testPlayer1);
		testPlayerList1.add(testPlayer2);
		testPlayerList1.add(testPlayer3);
		testR1.recordPlayerBasedInfo(testPlayerList1);
		testRecord.addRoundResult(testR1);
		
		RoundRecord testR2 = new RoundRecord(testMemorySize);
		Player testPlayer4 = new Player("name4", new Strategy(3));
		Player testPlayer5 = new Player("name5", new Strategy(3));
		Player testPlayer6 = new Player("name6", new Strategy(3));
		ArrayList<Player> testPlayerList2 = new ArrayList<Player>();
		for(int i = 0; i < 5; i++) {
			testPlayer4.memoryReset();
		}
		for(int i = 0; i < 17; i++) {
			testPlayer5.memoryReset();
		}
		for(int i = 0; i < 3; i++) {
			testPlayer6.memoryReset();
		}
		testPlayerList2.add(testPlayer4);
		testPlayerList2.add(testPlayer5);
		testPlayerList2.add(testPlayer6);
		testR2.recordPlayerBasedInfo(testPlayerList2);
		testRecord.addRoundResult(testR2);
		
		//Test stuff
		testRecord.analyzeGivenRecords(irrelevant, irrelevant);
		assertTrue(testRecord.getTotalAges().size() == 2);
		assertTrue(testRecord.getTotalAges().get(0) == 0);
		assertTrue(testRecord.getTotalAges().get(1) == 25);
	}
	
	/**
	 * Check whether a TourneyRecord can write to the testFile without blowing up.
	 */
	@Test
	public void testAnalyzingAndWritingData() {
		ArrayList<Player> testPlayerList = new ArrayList<Player>();
		testPlayerList.add(new Player("TFT", 1));
		testPlayerList.add(new Player("AC", 2));
		testPlayerList.add(new Player("AD", 3));
		testPlayerList.add(new Player("Unfor", 4));
		
		Tourney testTourney = new Tourney(testPlayerList, 1);
		testTourney.repeatedlyPlayTourney(20, 4);
		testTourney.getRecord().analyzeGivenRecords(4, 20);
		testTourney.getRecord().writeRecordedInfo("data/testFile");
	}
	
	/**
	 * Test equals method.
	 */
	@Test
	public void testEquals1() {
		int testMemorySize = 3;
		TourneyRecord testRecord = new TourneyRecord(testMemorySize);
		TourneyRecord testRecord2 = new TourneyRecord(testMemorySize);
		
		assertTrue(testRecord.equals(testRecord2));
		assertFalse(testRecord.equals(44));
	}
	
	/**
	 * Test equals method.
	 */
	@Test
	public void testEquals2() {
		TourneyRecord testRecord = new TourneyRecord(1);
		TourneyRecord testRecord2 = new TourneyRecord(2);
		TourneyRecord testRecord3= new TourneyRecord(1);
		testRecord3.addRoundResult(new RoundRecord(1));
		
		assertFalse(testRecord.equals(testRecord2));
		assertFalse(testRecord.equals(testRecord3));
		
		testRecord.addRoundResult(new RoundRecord(1));
		assertTrue(testRecord.equals(testRecord3));
	}
}
