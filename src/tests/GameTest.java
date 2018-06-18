package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import pDilemma.Game;
import pDilemma.Player;
import strategies.InformationItem;
import strategies.Strategy;

public class GameTest {

	/**
	 * Tests whether the first constructor correctly registers the participating players and memorySize.
	 */
	@Test
	public void testConstructor1() {
		Player playerP1 = new Player("A", 1);
		Player playerP2 = new Player("B", 1);
		
		Game testGame = new Game(playerP1, playerP2, 1);
		assertTrue(testGame.getPlayer1().equals(playerP1));
		assertTrue(testGame.getPlayer2().equals(playerP2));
		assertTrue(testGame.getMemorySize() == 1);
	}
	
	/**
	 * Test whether the second constructor correctly registers the participating players and memorySize.
	 */
	@Test
	public void testConstructor2() {
		Player playerP1 = new Player("A", 1);
		Player playerP2 = new Player("B", 1);
		
		Game testGame = new Game(playerP1, playerP2, 1, 0, 0, 0);
		assertTrue(testGame.getPlayer1().equals(playerP1));
		assertTrue(testGame.getPlayer2().equals(playerP2));
		assertTrue(testGame.getMemorySize() == 1);
	}

	/**
	 * Test whether the second constructor sets the specified noise levels.
	 */
	@Test
	public void testConstructor3() {
		Player playerP1 = new Player("A", 1);
		Player playerP2 = new Player("B", 1);
		double l = 0.01;
		double w = 0.02;
		double d = 0.03;
		
		Game testGame = new Game(playerP1, playerP2, 1, l, w, d);
		assertTrue(testGame.getLostInfo() == l);
		assertTrue(testGame.getWrongInfo() == w);
		assertTrue(testGame.getDelayedInfo() == d);
	}
	
	/**
	 * Test the initial beliefs regarding previous payoffs and the upcoming round number.
	 */
	@Test
	public void testConstructor4() {
		Player playerP1 = new Player("A", 1);
		Player playerP2 = new Player("B", 1);
		
		Game testGame = new Game(playerP1, playerP2, 1);
		assertTrue(testGame.getUpcomingRound() == 1);
		assertTrue(testGame.getPrevChoicesP1toP1().size() == 0);
		assertTrue(testGame.getPrevChoicesP2toP1().size() == 0);
		assertTrue(testGame.getPrevChoicesP2toP2().size() == 0);
		assertTrue(testGame.getPrevChoicesP1toP2().size() == 0);
	}
	
	/**
	 * Test whether the payoff Matrix satisfies the requirements for the Prisoner's Dilemma.
	 */
	@Test
	public void testPayoffMatrix() {
		Player playerP1 = new Player("A", 1);
		Player playerP2 = new Player("B", 1);
		
		Game testGame = new Game(playerP1, playerP2, 1);
		assertTrue(testGame.getPayT() > testGame.getPayR());
		assertTrue(testGame.getPayP() > testGame.getPayS());
		assertTrue(testGame.getPayR() > testGame.getPayS());
		assertTrue((2 * testGame.getPayR()) > (testGame.getPayT() + testGame.getPayS()));
	}
	
	/**
	 * Test beliefs about upcoming round, previous decisions and previous payoffs after round 1(CD).
	 */
	@Test
	public void testPlayRound1() {
		Player playerP1 = new Player("A", 1); //TFT
		Player playerP2 = new Player("B", 3); //AD
		
		Game testGame = new Game(playerP1, playerP2, 1);
		testGame.playRound();
		
		//Test new Information to send next round.
		assertTrue(testGame.getUpcomingRound() == 2);
		assertTrue(testGame.getPrevChoicesP1toP1().size() == 1);
		assertTrue(testGame.getPrevChoicesP1toP1().get(0) == 1);
		
		assertTrue(testGame.getPrevChoicesP2toP2().size() == 1);
		assertTrue(testGame.getPrevChoicesP2toP2().get(0) == 2);
		
		assertTrue(testGame.getPrevChoicesP2toP1().size() == 1);
		assertTrue(testGame.getPrevChoicesP1toP2().size() == 1);
		assertFalse(testGame.getPrevChoicesP1toP2().equals(testGame.getPrevChoicesP2toP1()));
		
		//Test information recorded.
		assertTrue(testGame.getRec().getNumberOfRoundsPlayed() == 1);
		assertTrue(testGame.getPlayer1().getCollectedPayoff() == testGame.getPayS());
		assertTrue(testGame.getPlayer2().getCollectedPayoff() == testGame.getPayT());
		
	}
	
	/**
	 * Test beliefs about upcoming round, previous decisions and previous payoffs after round 4(CD).
	 */
	@Test
	public void testPlayRound2() {
		
		//Just hope this goes well for strategies of bigger size.
		Player playerP1 = new Player("A", 1); //TFT
		Player playerP2 = new Player("B", 3); //AD
		
		Game testGame = new Game(playerP1, playerP2, 1);
		for (int i = 0; i < 4; i++) {
			testGame.playRound();
		}
		
		//Test new Information to send next round.
		assertTrue(testGame.getUpcomingRound() == 5);
		System.out.println(testGame.getPrevChoicesP1toP1().size());
		assertTrue(testGame.getPrevChoicesP1toP1().size() == 1);
		assertTrue(testGame.getPrevChoicesP1toP1().get(0) == 2);
		
		assertTrue(testGame.getPrevChoicesP2toP2().size() == 1);
		assertTrue(testGame.getPrevChoicesP2toP2().get(0) == 2);
		
		assertTrue(testGame.getPrevChoicesP1toP2().size() == 1);
		assertTrue(testGame.getPrevChoicesP2toP1().size() == 1);
		
		//Test information recorded.
		assertTrue(testGame.getRec().getNumberOfRoundsPlayed() == 4);
		assertTrue(testGame.getPlayer1().getCollectedPayoff() == (testGame.getPayS() + 3 * testGame.getPayP()));
		assertTrue(testGame.getPlayer2().getCollectedPayoff() == (testGame.getPayT() + 3 * testGame.getPayP()));
		
	}
	
	/**
	 * Test recorded information after round 1(CC).
	 */
	@Test
	public void testPlayRound3() {
		Player playerP1 = new Player("A", 1); //TFT
		Player playerP2 = new Player("B", 1); //TFT
		
		Game testGame = new Game(playerP1, playerP2, 1);
		testGame.playRound();
		
		//Test information recorded.
		assertTrue(testGame.getRec().getNumberOfRoundsPlayed() == 1);
		assertTrue(testGame.getPlayer1().getCollectedPayoff() == testGame.getPayR());
		assertTrue(testGame.getPlayer2().getCollectedPayoff() == testGame.getPayR());
	}
	
	/**
	 * Test recorded information after round 1(DC).
	 */
	@Test
	public void testPlayRound4() {
		Player playerP1 = new Player("A", 3); //Always defect
		Player playerP2 = new Player("B", 1); //TFT
		
		Game testGame = new Game(playerP1, playerP2, 1);
		testGame.playRound();
		
		//Test information recorded.
		assertTrue(testGame.getRec().getNumberOfRoundsPlayed() == 1);
		assertTrue(testGame.getPlayer1().getCollectedPayoff() == testGame.getPayT());
		assertTrue(testGame.getPlayer2().getCollectedPayoff() == testGame.getPayS());
	}
	
	/**
	 * Test recorded information after round 1(DD).
	 */
	@Test
	public void testPlayRound5() {
		Player playerP1 = new Player("A", 3); //Always defect
		Player playerP2 = new Player("B", 3); //Always defect
		
		Game testGame = new Game(playerP1, playerP2, 1);
		testGame.playRound();
		
		//Test information recorded.
		assertTrue(testGame.getRec().getNumberOfRoundsPlayed() == 1);
		assertTrue(testGame.getPlayer1().getCollectedPayoff() == testGame.getPayP());
		assertTrue(testGame.getPlayer2().getCollectedPayoff() == testGame.getPayP());
	}
	
	/**
	 * Tests whether game results are recorded in game record.
	 */
	@Test
	public void testPlayRound6() {
		Player playerP1 = new Player("A", 1); //TFT
		Player playerP2 = new Player("B", 3); //Always defect
		
		Game testGame = new Game(playerP1, playerP2, 1);
		testGame.playRound();
		
		assertTrue(testGame.getRec().getNumberOfRoundsPlayed() == 1);
		assertTrue(testGame.getRec().getDecisionsP1().get(0) == 1);
		assertTrue(testGame.getRec().getPayoffsP1().get(0) == testGame.getPayS());
		assertTrue(testGame.getRec().getDecisionsP2().get(0) == 2);
		assertTrue(testGame.getRec().getPayoffsP2().get(0) == testGame.getPayT());
	}
	
	/**
	 * Test lowering delays.
	 */
	@Test
	public void testLowerDelays1() {
		ArrayList<InformationItem> testMyMemory = new ArrayList<InformationItem>();
		testMyMemory.add(new InformationItem(2, 2, 0));
		testMyMemory.add(new InformationItem(-1, 0, 0));
		testMyMemory.add(new InformationItem(-1, 1, 1));
		ArrayList<Integer> testCorrectMemory = new ArrayList<Integer>();
		testCorrectMemory.add(2);
		testCorrectMemory.add(1);
		testCorrectMemory.add(2);
		
		Game.lowerDelays(testMyMemory, testCorrectMemory);
		assertTrue(testMyMemory.get(0).equals(new InformationItem(2, 2, 0)));
		assertTrue(testMyMemory.get(1).equals(new InformationItem(-1, 0, 0)));
		assertTrue(testMyMemory.get(2).equals(new InformationItem(2, 1, 0)));
	}
	
	/**
	 * Test lowering delays.
	 */
	@Test
	public void testLowerDelays2() {
		ArrayList<InformationItem> testMyMemory = new ArrayList<InformationItem>();
		testMyMemory.add(new InformationItem(2, 2, 0));
		testMyMemory.add(new InformationItem(1, 1, 0));
		testMyMemory.add(new InformationItem(-1, 2, 2));
		ArrayList<Integer> testCorrectMemory = new ArrayList<Integer>();
		testCorrectMemory.add(2);
		testCorrectMemory.add(1);
		testCorrectMemory.add(1);
		
		Game.lowerDelays(testMyMemory, testCorrectMemory);
		assertTrue(testMyMemory.get(0).equals(new InformationItem(2, 2, 0)));
		assertTrue(testMyMemory.get(1).equals(new InformationItem(1, 1, 0)));
		assertTrue(testMyMemory.get(2).equals(new InformationItem(-1, 2, 1)));
	}
	
	/**
	 * Test sending a player lost information about opponents last move.
	 */
	@Test
	public void testInformationChannel1() {
		Player playerP1 = new Player("A", 1); //TFT
		Player playerP2 = new Player("B", 1); //TFT
		
		Game testGame = new Game(playerP1, playerP2, 1, 1, 0, 0); //100% chance of L
		testGame.playRound();
		testGame.playRound();
		
		assertTrue(playerP1.getLastChoicesMe().get(0) == 1);
		assertTrue(playerP1.getLastChoicesOpponent().get(0).equals(new InformationItem(-1, 0, 0)));
		assertTrue(playerP2.getLastChoicesMe().get(0) == 1);
		assertTrue(playerP2.getLastChoicesOpponent().get(0).equals(new InformationItem(-1, 0, 0)));
	}
	
	/**
	 * Test sending a player wrong information about an opponents last move.
	 */
	@Test
	public void testInformationChannel2() {
		Player playerP1 = new Player("A", 1); //TFT
		Player playerP2 = new Player("B", 3); //AD
		
		Game testGame = new Game(playerP1, playerP2, 1, 0, 1, 0);
		testGame.playRound();
		testGame.playRound();
		
		assertTrue(playerP1.getLastChoicesMe().get(0) == 1);
		assertTrue(playerP1.getLastChoicesOpponent().get(0).equals(new InformationItem(1, 0, 0)));
		assertTrue(playerP2.getLastChoicesMe().get(0) == 2);
		assertTrue(playerP2.getLastChoicesOpponent().get(0).equals(new InformationItem(2, 0, 0)));
	}
	
	/**
	 * Test sending a player delayed information about an opponents last move.
	 */
	@Test
	public void testInformationChannel3() {
		int[][] testStrategyMatrix1 = new int[Strategy.getRows(2)][Strategy.getColumns(2)];
		for(int i = 0; i < Strategy.getRows(2); i++) {
			Arrays.fill(testStrategyMatrix1[i], 1);
		}
		
		int[][] testStrategyMatrix2 = new int[Strategy.getRows(2)][Strategy.getColumns(2)];
		for(int i = 0; i < Strategy.getRows(2); i++) {
			Arrays.fill(testStrategyMatrix2[i], 2);
		}
		
		Strategy testStrategy1 = new Strategy(2, testStrategyMatrix1);
		Strategy testStrategy2 = new Strategy(2, testStrategyMatrix2);
		Player playerP1 = new Player("A", testStrategy1);
		Player playerP2 = new Player("B", testStrategy2);
		
		Game testGame = new Game(playerP1, playerP2, 2, 0, 0, 1);
		testGame.playRound();
		testGame.playRound();
		
		assertTrue(playerP1.getLastChoicesMe().get(0) == 1);
		assertTrue(playerP1.getLastChoicesMe().get(1) == 1);
		assertTrue(playerP1.getLastChoicesOpponent().get(0).equals(new InformationItem(2, 1, 0)));
		assertTrue(playerP1.getLastChoicesOpponent().get(1).equals(new InformationItem(-1, 1, 1)));
		
		assertTrue(playerP2.getLastChoicesMe().get(0) == 2);
		assertTrue(playerP2.getLastChoicesMe().get(1) == 2);
		assertTrue(playerP2.getLastChoicesOpponent().get(0).equals(new InformationItem(1, 1, 0)));
		assertTrue(playerP2.getLastChoicesOpponent().get(1).equals(new InformationItem(-1, 1, 1)));
	}
	
	/**
	 * Test sending a player correct information about an opponents last move.
	 */
	@Test
	public void testInformationChannel4() {
		Player playerP1 = new Player("A", 2); //Coop
		Player playerP2 = new Player("B", 3); //Always defect
		
		Game testGame = new Game(playerP1, playerP2, 1);
		testGame.playRound();
		testGame.playRound();
		
		assertTrue(playerP1.getLastChoicesMe().get(0) == 1);
		assertTrue(playerP1.getLastChoicesOpponent().get(0).equals(new InformationItem(2, 0, 0)));
		assertTrue(playerP2.getLastChoicesMe().get(0) == 2);
		assertTrue(playerP2.getLastChoicesOpponent().get(0).equals(new InformationItem(1, 0, 0)));
	}
	
	/**
	 * Test whether game result so far can be displayed.
	 */
	@Test
	public void testDisplayResults() {
		Player playerP1 = new Player("A", 1); //TFT
		Player playerP2 = new Player("B", 3); //Always defect
		
		Game testGame = new Game(playerP1, playerP2, 1);
		testGame.playRound();
		testGame.displayResults();
	}
}
