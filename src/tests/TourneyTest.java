package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import pDilemma.Player;
import pDilemma.Tourney;
import pDilemma.TourneyRecord;

public class TourneyTest {

	/**
	 * Check whether the constructor stores the supplied players correctly.
	 */
	@Test
	public void testConstructor() {
		ArrayList<Player> playerList = new ArrayList<Player>();
		Tourney testTourney = new Tourney(playerList, 1);
		
		assertTrue(testTourney.getPlayerList().size() == 0);
		assertTrue(testTourney.getMemorySize() == 1);
		assertTrue(testTourney.getRecord().equals(new TourneyRecord(1)));
	}
	
	/**
	 * Check whether the constructor passes memorySize correctly to its record.
	 */
	@Test
	public void testConstructor2() {
		int testMemorySize = 2;
		Tourney testTourney = new Tourney((new ArrayList<Player>()), testMemorySize);
		
		assertTrue(testTourney.getMemorySize() == testMemorySize);
		assertTrue(testTourney.getRecord().getMemorySize() == testMemorySize);
	}

	/**
	 * Check whether the correct agents reproduce (3 players).
	 */
	@Test
	public void testChangePool1() {
		ArrayList<Player> testPlayerList = new ArrayList<Player>();
		Player testPlayer1 = new Player("TFT", 1);
		Player testPlayer2 = new Player("Always coop", 2);
		Player testPlayer3 = new Player("Always defect", 3);
		testPlayerList.add(testPlayer1);
		testPlayerList.add(testPlayer2);
		testPlayerList.add(testPlayer3);
		
		Tourney testTourney = new Tourney(testPlayerList , 1);
		testTourney.repeatedlyPlayTourney(3, 1);
		
		//Expect TFT AC: (3*payR) - (3*parR)
		//Expect TFT AD: (payS + 2*payP) - (payT + 2*payP)
		//Expect AC AD: (3*payS) - (3*payT)
		//AD will reproduce, AC will be kicked out
		assertTrue(testTourney.getPlayerList().size() == 3);
		
		int numberOfAD = 0;
		for(int i = 0; i < 3; i ++) {
			if (testTourney.getPlayerList().get(i).getId().equals("Always defect")) {
				numberOfAD++;
			}
		}
		assertTrue(numberOfAD == 2);
		//Player reproduction has already been tested in playerTest.
	}
	
	/**
	 * Check whether the correct agents reproduce (4 players).
	 */
	@Test
	public void testChangePool2() {
		ArrayList<Player> testPlayerList = new ArrayList<Player>();
		Player testPlayer1 = new Player("TFT", 1);
		Player testPlayer2 = new Player("Always coop", 2);
		Player testPlayer3 = new Player("Always defect", 3);
		Player testPlayer4 = new Player("Always defect 2", 3);
		testPlayerList.add(testPlayer1);
		testPlayerList.add(testPlayer2);
		testPlayerList.add(testPlayer3);
		testPlayerList.add(testPlayer4);
		
		Tourney testTourney = new Tourney(testPlayerList, 1);
		testTourney.repeatedlyPlayTourney(3, 1);
		
		//Expect the 2 AD to reproduce
		assertTrue(testTourney.getPlayerList().size() == 4);
		
		int numberOfAD = 0;
		int numberOfAD2 = 0;
		for(int i = 0; i < 4; i ++) {
			if (testTourney.getPlayerList().get(i).getId().equals("Always defect")) {
				numberOfAD++;
			}
			if (testTourney.getPlayerList().get(i).getId().equals("Always defect 2")) {
				numberOfAD2++;
			}
		}
		assertTrue(numberOfAD == 2);
		assertTrue(numberOfAD2 == 2);
	}
	
	/**
	 * Check whether the correct agents reproduce (directly calling the method).
	 */
	@Test
	public void testChangePool3() {
		ArrayList<Player> testPlayerList = new ArrayList<Player>();
		
		Player testPlayer1 = new Player("1", 1);
		testPlayer1.dumpPayoff(1);
		testPlayerList.add(testPlayer1);
		
		Player testPlayer2 = new Player("2", 1);
		testPlayer2.dumpPayoff(2);
		testPlayerList.add(testPlayer2);
		
		Player testPlayer3 = new Player("3", 1);
		testPlayer3.dumpPayoff(3);
		testPlayerList.add(testPlayer3);
		
		Player testPlayer4 = new Player("4", 1);
		testPlayer4.dumpPayoff(4);
		testPlayerList.add(testPlayer4);
		
		Player testPlayer5 = new Player("5", 1);
		testPlayer5.dumpPayoff(5);
		testPlayerList.add(testPlayer5);
		
		Player testPlayer6 = new Player("6", 1);
		testPlayer6.dumpPayoff(6);
		testPlayerList.add(testPlayer6);
		
		Tourney.changePool(testPlayerList, 0.40);
		assertTrue(testPlayerList.size() == 6);
		assertTrue(testPlayerList.get(0).getId().equals("3"));
		assertTrue(testPlayerList.get(1).getId().equals("4"));
		assertTrue(testPlayerList.get(2).getId().equals("5"));
		assertTrue(testPlayerList.get(3).getId().equals("6"));
		assertTrue(testPlayerList.get(4).getId().equals("6"));
		assertTrue(testPlayerList.get(5).getId().equals("5"));
	}
	
	/**
	 * Check that printTourney can print the players in the pool.
	 */
	@Test
	public void testPrintTourney() {
		ArrayList<Player> testPlayerList = new ArrayList<Player>();
		testPlayerList.add(new Player("TFT", 1));
		testPlayerList.add(new Player("Always coop", 2));
		testPlayerList.add(new Player("Always defect", 3));
		
		Tourney testTourney = new Tourney(testPlayerList, 1);
		
		testTourney.printTourneyPlayersWithPayoff();
		testTourney.repeatedlyPlayTourney(3, 1);
		testTourney.printTourneyPlayersWithPayoff();
	}
	
	/**
	 * Check that information recorded on the tournament is actually recorded.
	 */
	@Test
	public void testTourneyRecord() {
		ArrayList<Player> testPlayerList = new ArrayList<Player>();
		testPlayerList.add(new Player("TFT", 1));
		testPlayerList.add(new Player("Always coop", 2));
		testPlayerList.add(new Player("Always defect", 3));
		
		Tourney testTourney = new Tourney(testPlayerList, 1);
		assertTrue(testTourney.getRecord().getNumberOfRoundsPlayed() == 0);
		assertTrue(testTourney.getRecord().getRoundsData().size() == 0);
		
		testTourney.repeatedlyPlayTourney(3, 1);
		assertTrue(testTourney.getRecord().getNumberOfRoundsPlayed() == 1);
		assertTrue(testTourney.getRecord().getRoundsData().size() == 1);
		
		//Test two record attributes (one player based, one GameRecord based and one player based
		//This so we know both entities have been added.
		assertTrue(testTourney.getRecord().getRoundsData().get(0).getAverageStrategyMat()[0][0] < 1.34);
		assertTrue(testTourney.getRecord().getRoundsData().get(0).getAverageStrategyMat()[0][0] > 1.33);
		assertTrue(testTourney.getRecord().getRoundsData().get(0).getAmountOfCC() == 3);
	}
	
	/**
	 * Test static method that returns number of games per RR-tournament
	 */
	@Test
	public void testAmountOfGamesPerRound() {
		assertTrue(Tourney.amountOfGamesPerRound(4, 20) == (6 * 20));
		assertTrue(Tourney.amountOfGamesPerRound(30, 300) == 130500);
	}
}
