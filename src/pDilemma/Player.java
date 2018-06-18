package pDilemma;

import java.util.ArrayList;

import strategies.InformationItem;
import strategies.Strategy;

/**
 * This class is a representation of a player that participates in a two-player IPD game.
 * @author Stefan
 *
 */
public class Player {
	
	//Player information
	private String id;
	private Strategy strat;
	private int collectedPayoff = 0;
	private int age = 0;
	
	//The memory of the player (ArrayLists provided will never be longer as strategy.getMemorySize()).
	private int lastRoundPlayed = 0;
	private ArrayList<Integer> lastChoicesMe = new ArrayList<Integer>();
	private ArrayList<InformationItem> lastChoicesOpponent = new ArrayList<InformationItem>();
	
	/**
	 * Create a player object using a standard strategy as defined by the Strategy class.
	 * @param id specifies how the player will be identified.
	 * @param strategy specifies the standard memory size 1 strategy to be used by the player.
	 */
	public Player (String id, int strategyId) {
		this.id = id;
		this.strat = new Strategy(strategyId);
	}

	/**
	 * Create a player object by passing the strategy it employs.
	 * @param id specifies how the player will be identified.
	 * @param memorySize the memorySize of the Strategy given
	 * @param strategy specifies the strategy to be used by the player.
	 */
	public Player(String id, Strategy strategy) {
		this.id = id;
		this.strat = strategy;
	}

	/**
	 * Have the player update its memory and decide on its next move.
	 * @param round the last round played.
	 * @param prevChoicesMe the last move of this player.
	 * @param prevChoicesOpponent the last move played by the opponent.
	 * @return the next move this player wishes to play.
	 */
	public int updateMemoryAndMakeMove(int round, ArrayList<Integer> prevChoicesMe,
			ArrayList<InformationItem> prevChoicesOpponent) {
		
		//Update memory (the ArrayList are not of memory size n for the first few rounds)
		//However the length of the ones given are no longer as memory length specified by the strategy
		//If they were longer, excess info probably will not be used but let us not go there
		this.lastRoundPlayed = round;
		this.lastChoicesMe = prevChoicesMe;
		this.lastChoicesOpponent = prevChoicesOpponent;
		
		//Determine next move
		return strat.nextMove(this.lastRoundPlayed, this.lastChoicesMe, this.lastChoicesOpponent);
	}

	/**
	 * Add payoff to what this player has received so far.
	 * @param currPayoffP1 the payoff to add.
	 */
	public void dumpPayoff(int payoffToAdd) {
		collectedPayoff += payoffToAdd;
	}
	
	/**
	 * Reset the payoff the player has collected (used between RR tournament rounds).
	 */
	public void payoffReset() {
		this.collectedPayoff = 0;
	}
	
	/**
	 * Reset the memory after a game of IPD is finished, so that the player can participate in the next game.
	 */
	public void memoryReset() {
		this.lastRoundPlayed = 0;
		this.lastChoicesMe = new ArrayList<Integer>();
		this.lastChoicesOpponent = new ArrayList<InformationItem>();
		
		//Completing one game of IPD let's the player age
		this.age++;
	}
	
	/**
	 * Have this player object return a new player with the same id and a similar (but not equivalent) strategy.
	 * @return the new player
	 */
	public Player reproduce() {
		Strategy newStrategy = this.strat.getMutation();
		return new Player(this.id, newStrategy);
	}
	
	/**
	 * Determines whether two players are equal.
	 */
	@Override
	public boolean equals(Object other) {
		if (other instanceof Player) {
			Player otherPlayer = (Player) other;
			if (this.id.equals(otherPlayer.getId()) &&
					this.strat.equals(otherPlayer.getStrat()) &&
					this.collectedPayoff == otherPlayer.getCollectedPayoff() &&
					this.lastRoundPlayed == otherPlayer.getLastRoundPlayed() &&
					this.lastChoicesMe.equals(otherPlayer.getLastChoicesMe()) &&
					this.lastChoicesOpponent.equals(otherPlayer.getLastChoicesOpponent()) &&
					this.age == otherPlayer.getAge()) {
				return true;
			}
		}
		return false;
	}
	
	//Getters
	
	/**
	 * 
	 * @return the strategy used by the player
	 */
	public Strategy getStrat() {
		return strat;
	}

	/**
	 * 
	 * @return the player identifier
	 */
	public String getId() {
		return id;
	}

	/**
	 * 
	 * @return the payoff collected by the player
	 */
	public int getCollectedPayoff() {
		return collectedPayoff;
	}

	/**
	 * 
	 * @return the number of the last round played by the player.
	 */
	public int getLastRoundPlayed() {
		return lastRoundPlayed;
	}

	/**
	 * 
	 * @return the last move played by this player.
	 */
	public ArrayList<Integer> getLastChoicesMe() {
		return lastChoicesMe;
	}

	/**
	 * 
	 * @return the last move played by the opponent of this player.
	 */
	public ArrayList<InformationItem> getLastChoicesOpponent() {
		return lastChoicesOpponent;
	}

	/**
	 * 
	 * @return the number of IPD games completed by the player
	 */
	public int getAge() {
		return age;
	}
}
