package pDilemma;

import java.util.ArrayList;

import strategies.Strategy;

/**
 * Record the data on the result of one round, meaning one RR-tournament.
 * @author Stefan
 *
 */
public class RoundRecord {

	//Memory size of player strategies participating in the tournament
	private int memorySize;
	
	//Record the decisions for an RR round, using gameRecords
	private int amountOfCC = 0;
	private int amountOfCD = 0;
	private int amountOfDC = 0;
	private int amountOfDD = 0;
	
	//Record the payoff earned by players over a single RR round, using player.getCollectedPayoff()
	//before it is reset
	private int lowestPayoff = Integer.MAX_VALUE;
	private int highestPayoff = Integer.MIN_VALUE;
	private double averagePayoff;
	
	//Record average cooperative value in each player strategy table entry, using player.getStrat()
	private double[][] averageStrategyMat;
	
	//Record total player pool age
	private int totalAge;
	
	/**
	 * Create a RoundRecord.
	 */
	public RoundRecord(int memorySize) {
		this.memorySize = memorySize;
		
		//Set data structure to return in to the correct size.
		this.averageStrategyMat =
				new double[Strategy.getRows(this.memorySize)][Strategy.getColumns(this.memorySize)];
	}
	
	/**
	 * Add information about player payoffs and strategies to this RoundRecord.
	 * @param playerList the list of players that participated in the round that is being recorded.
	 */
	public void recordPlayerBasedInfo(ArrayList<Player> playerList) {
		
		//Record payoff stuff
		int totalPayoff = 0;
		for(int i = 0; i < playerList.size(); i++) {
			totalPayoff += playerList.get(i).getCollectedPayoff();
			if (playerList.get(i).getCollectedPayoff() < this.lowestPayoff) {
				this.lowestPayoff = playerList.get(i).getCollectedPayoff();
			}
			if (playerList.get(i).getCollectedPayoff() > this.highestPayoff) {
				this.highestPayoff = playerList.get(i).getCollectedPayoff();
			}
		}
		this.averagePayoff = (double) totalPayoff / (double) playerList.size();
		
		//Record strategy dependent stuff
		int rows = Strategy.getRows(this.memorySize);
		int columns = Strategy.getColumns(this.memorySize);
		int[][] totalStratMat = new int[rows][columns];
		int tempTotalAge = 0;
		
		//Total strategy values
		for(int i = 0; i < playerList.size(); i++) {
			for(int j = 0; j < rows; j++) {
				for(int k = 0; k < columns; k++) {
					totalStratMat[j][k] += playerList.get(i).getStrat().getStrategy()[j][k];
				}
			}
		}
		
		//Average strategy values
		for(int j = 0; j < rows; j++) {
			for(int k = 0; k < columns; k++) {
				this.averageStrategyMat[j][k] = (double) totalStratMat[j][k] / (double) playerList.size();
			}
		}
		
		//Total pool age
		for(int i = 0; i < playerList.size(); i++) {
			tempTotalAge += playerList.get(i).getAge();
		}
		this.totalAge = tempTotalAge;
		
	}
	
	/**
	 * Add another played game for this round to the record.
	 * @param record the IPD game to add.
	 */
	public void addGameResult(GameRecord record) {
		for (int i = 0; i < record.getNumberOfRoundsPlayed(); i++) {
			addResult(record.getDecisionsP1().get(i), record.getDecisionsP2().get(i));
		}
	}
	
	/**
	 * Add an individual PD-round played during this RR-tournament to the RoundRecord.
	 * @param player1Choice the choice of player 1
	 * @param player2Choice the choice of player 2
	 */
	public void addResult(int player1Choice, int player2Choice) {
		if (player1Choice == 1 && player2Choice == 1) {
			amountOfCC++;
		} else if (player1Choice == 1 && player2Choice == 2) {
			amountOfCD++;
		} else if (player1Choice == 2 && player2Choice == 1) {
			amountOfDC++;
		} else if (player1Choice == 2 && player2Choice == 2) {
			amountOfDD++;
		}
	}
	
	/**
	 * Checks whether two RoundRecord objects are equal
	 */
	public boolean equals(Object other) {
		if(other instanceof RoundRecord) {
			RoundRecord otherRR = (RoundRecord) other;
			if(this.getAmountOfCC() == otherRR.getAmountOfCC() &&
					this.getAmountOfCD() == otherRR.getAmountOfCD() &&
					this.getAmountOfDC() == otherRR.getAmountOfDC() &&
					this.getAmountOfDD() == otherRR.getAmountOfDD() &&
					this.getMemorySize() == otherRR.getMemorySize() &&
					this.getLowestPayoff() == otherRR.getLowestPayoff() &&
					this.getHighestPayoff() == otherRR.getHighestPayoff() &&
					this.getTotalAge() == otherRR.getTotalAge() &&
					(Math.abs(this.getAveragePayoff() - otherRR.getAveragePayoff()) < 0.01)) {
				
				boolean same = true;
				for(int i = 0; i < Strategy.getRows(this.memorySize); i++) {
					for(int j = 0; j < Strategy.getColumns(this.memorySize); j++) {
						if(Math.abs(this.getAverageStrategyMat()[i][j] -
								otherRR.getAverageStrategyMat()[i][j]) > 0.01) {
							same = false;
						}
					}
				}
				return same;
			}
		}
		return false;
	}

	//Getters
	
	/**
	 * 
	 * @return the amount of times is it recorded that both players cooperated.
	 */
	public int getAmountOfCC() {
		return amountOfCC;
	}

	/**
	 * 
	 * @return the amount of times player 1 cooperated but player 2 defected.
	 */
	public int getAmountOfCD() {
		return amountOfCD;
	}

	/**
	 * 
	 * @return the amount of times player 2 cooperated but player 1 defected.
	 */
	public int getAmountOfDC() {
		return amountOfDC;
	}

	/**
	 * 
	 * @return the amount of times both players defected.
	 */
	public int getAmountOfDD() {
		return amountOfDD;
	}

	/**
	 * 
	 * @return the lowest total payoff for one player participating in this RR-tournament.
	 */
	public int getLowestPayoff() {
		return lowestPayoff;
	}

	/**
	 * 
	 * @return the highest total payoff for one player participating in this RR-tournament.
	 */
	public int getHighestPayoff() {
		return highestPayoff;
	}

	/**
	 * 
	 * @return the average total payoff for the players that participated in this RR-tournament.
	 */
	public double getAveragePayoff() {
		return averagePayoff;
	}

	/**
	 * 
	 * @return the average decisions employed by the players for their different states of memory during this
	 * RR-tournament.
	 */
	public double[][] getAverageStrategyMat() {
		return averageStrategyMat;
	}

	/**
	 * 
	 * @return the memorySize of the strategies of the players participating in the RR tournament that
	 * is being recorded.
	 */
	public int getMemorySize() {
		return memorySize;
	}

	/**
	 * 
	 * @return the sum of the ages of all players in the pool.
	 */
	public int getTotalAge() {
		return totalAge;
	}
}
