package pDilemma;

import java.util.ArrayList;

/**
 * This class records all of the decisions and payoffs for a single two-player IPD game.
 * @author Stefan
 *
 */
public class GameRecord {

	private int numberOfRoundsPlayed = 0;
	private ArrayList<Integer> payoffsP1 = new ArrayList<Integer>();
	private ArrayList<Integer> payoffsP2 = new ArrayList<Integer>();
	private ArrayList<Integer> decisionsP1 = new ArrayList<Integer>();
	private ArrayList<Integer> decisionsP2 = new ArrayList<Integer>();
	
	/**
	 * Create a game record.
	 */
	public GameRecord() {
		
	}
	
	/**
	 * Record a new round of IPD played.
	 * @param payoffP1 the payoff received by player 1.
	 * @param payoffP2 the payoff received by player 2.
	 * @param decisionP1 the decision made by player 1.
	 * @param decisionP2 the decision made by player 2.
	 */
	public void dumpRound(int payoffP1, int payoffP2, int decisionP1, int decisionP2) {
		this.numberOfRoundsPlayed++;
		this.payoffsP1.add(payoffP1);
		this.payoffsP2.add(payoffP2);
		this.decisionsP1.add(decisionP1);
		this.decisionsP2.add(decisionP2);
	}
	
	/**
	 * Get the result of the IPD game recorded.
	 * @return a string representing the result of the IPD game so far.
	 */
	public String displayResultsShort() {
		
		int totalPayoffP1 = 0;
		int totalPayoffP2 = 0;
		for (int j = 0; j < this.numberOfRoundsPlayed; j++) {
			totalPayoffP1 += this.payoffsP1.get(j);
			totalPayoffP2 += this.payoffsP2.get(j);
		}
		
		StringBuilder result = new StringBuilder();
		result.append(totalPayoffP1);
		result.append(" - ");
		result.append(totalPayoffP2);
		
		return result.toString();
	}
	
	/**
	 * Print the results of the recorded game in an elaborate manner.
	 */
	public void displayResultsLong() {
		for (int i = 1; i <= this.numberOfRoundsPlayed; i++) {
			displayResultsRound(i);
		}
	}
	
	/**
	 * Print the results of one round of the recorded game in an elaborate manner.
	 * @param roundNumber the round for which the results are to be printed.
	 */
	private void displayResultsRound(int roundNumber) {
		
		//Get player decisions for this round
		String p1Decision;
		if (this.decisionsP1.get(roundNumber-1) == 1) {
			p1Decision = "cooperate";
		} else {
			p1Decision = "defect";
		}
		
		String p2Decision;
		if (this.decisionsP2.get(roundNumber-1) == 1) {
			p2Decision = "cooperate";
		} else {
			p2Decision = "defect";
		}
		
		//Get player payoff up until this round
		int totalPayoffP1soFar = 0;
		int totalPayoffP2soFar = 0;
		for (int j = 0; j < roundNumber; j++) {
			totalPayoffP1soFar += this.payoffsP1.get(j);
			totalPayoffP2soFar += this.payoffsP2.get(j);
		}
		
		//Print text
		System.out.println("------Round " + roundNumber + " results");
		System.out.println("Player 1 decision: " + p1Decision);
		System.out.println("Player 2 decision: " + p2Decision);
		System.out.println("Player 1 payoff this round: " + + this.payoffsP1.get(roundNumber-1) + ", Player 1 total payoff: " + totalPayoffP1soFar);
		System.out.println("Player 2 payoff this round: " + + this.payoffsP2.get(roundNumber-1) + ", Player 2 total payoff: " + totalPayoffP2soFar);
	}	

	//Getters
	
	/**
	 * 
	 * @return the number of rounds played
	 */
	public int getNumberOfRoundsPlayed() {
		return numberOfRoundsPlayed;
	}

	/**
	 * 
	 * @return an ArrayList of payoffs received by player 1.
	 */
	public ArrayList<Integer> getPayoffsP1() {
		return payoffsP1;
	}

	/**
	 * 
	 * @return an ArrayList of payoffs received by player 2.
	 */
	public ArrayList<Integer> getPayoffsP2() {
		return payoffsP2;
	}

	/**
	 * 
	 * @return an ArrayList of the decisions made by player 1.
	 */
	public ArrayList<Integer> getDecisionsP1() {
		return decisionsP1;
	}

	/**
	 * 
	 * @return an ArrayList of the decisions made by player 2.
	 */
	public ArrayList<Integer> getDecisionsP2() {
		return decisionsP2;
	}
}
