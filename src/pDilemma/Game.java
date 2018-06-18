package pDilemma;

import java.util.ArrayList;
import java.util.Random;

import strategies.InformationItem;

/**
 * This class can be used to simulate a two-player game of Iterative Prisoner's Dilemma
 * with or without lost, wrong or delayed information.
 * 
 * @author Stefan
 *
 */
public class Game {

	//Players 
	private Player player1;
	private Player player2;
	
	//Research variables
	private int memorySize;
	private double lostInfo;
	private double wrongInfo;
	private double delayedInfo;
	
	//Research variable: payoff matrix
	private int payT = 5;
	private int payR = 4;
	private int payP = 1;
	private int payS = 0;
	
	//Previous round info to send to players (can be mutated)
	//(ArrayLists should never be longer as memory length, which should match memory length of the participating
	//players)
	private int upcomingRound = 1;
	private ArrayList<Integer> prevChoicesP1toP1 = new ArrayList<Integer>();
	private ArrayList<Integer> prevChoicesP2toP2 = new ArrayList<Integer>();
	private ArrayList<InformationItem> prevChoicesP2toP1 = new ArrayList<InformationItem>();
	private ArrayList<InformationItem> prevChoicesP1toP2 = new ArrayList<InformationItem>();
	
	//GameRecord records the results so far
	private GameRecord rec = new GameRecord();
	
	/**
	 * Construct a game of IPD without any noise on the channel.
	 * Be sure that the memory length provided is the memory length of the strategies the players are employing.
	 * @param player1 the first player participating in the game.
	 * @param player2 the second player participating in the game.
	 * @param memorySize the size of the memory for the strategies used by the players.
	 */
	public Game (Player player1, Player player2, int memorySize) {
		this.player1 = player1;
		this.player2 = player2;
		this.memorySize = memorySize;
		setGameMode(0,0,0);
	}
	
	/**
	 * Construct a game of IPD with noise regarding the moves played by the opponent.
	 * Be sure that the memory length provided is the memory length of the strategies the players are employing.
	 * @param player1 the first player participating in the game.
	 * @param player2 the second player participating in the game.
	 * @param memorySize the size of the memory for the strategies used by the players.
	 * @param lostInfo the rate at which information a player receives on the moves of an opponent is lost (between 0 and 1).
	 * @param wrongInfo the rate at which information a player receives on the moves of an opponent is wrong (between 0 and 1).
	 * @param delayedInfo the rate at which information a player receives on the moves of an opponent is delayed (between 0 and 1).
	 */
	public Game (Player player1, Player player2, int memorySize,
			double lostInfo, double wrongInfo, double delayedInfo) {
		
		this.player1 = player1;
		this.player2 = player2;
		this.memorySize = memorySize;
		setGameMode(lostInfo,wrongInfo,delayedInfo);
	}
	
	/**
	 * Sets the levels of noise for the constructed IPD game as specified.
	 * @param lostInfo the rate at which information a player receives on the moves of an opponent is lost (between 0 and 1).
	 * @param wrongInfo the rate at which information a player receives on the moves of an opponent is wrong (between 0 and 1).
	 * @param delayedInfo the rate at which information a player receives on the moves of an opponent is delayed (between 0 and 1).
	 */
	private void setGameMode(double lostInfo, double wrongInfo, double delayedInfo) {
		this.lostInfo = lostInfo;
		this.wrongInfo = wrongInfo;
		this.delayedInfo = delayedInfo;
	}

	/**
	 * Let the game of IPD progress by one round.
	 */
	public void playRound() {
		
		int currChoiceP1;
		int currChoiceP2;
		
		//Send information players require to make their new decision
		currChoiceP1 = player1.updateMemoryAndMakeMove(upcomingRound, prevChoicesP1toP1, prevChoicesP2toP1);
		currChoiceP2 = player2.updateMemoryAndMakeMove(upcomingRound, prevChoicesP2toP2, prevChoicesP1toP2);
		
		//Determine the payoffs for each player
		int currPayoffP1;
		int currPayoffP2;
		if (currChoiceP1 == 1 && currChoiceP2 == 1) {
			currPayoffP1 = this.payR;
			currPayoffP2 = this.payR;
		} else if (currChoiceP1 == 1 && currChoiceP2 == 2) {
			currPayoffP1 = this.payS;
			currPayoffP2 = this.payT;
		} else if (currChoiceP1 == 2 && currChoiceP2 == 1) {
			currPayoffP1 = this.payT;
			currPayoffP2 = this.payS;
		} else {
			currPayoffP1 = this.payP;
			currPayoffP2 = this.payP;
		}
		
		//Update game information to be send next round and record this round results.
		updateInfo(currChoiceP1, currChoiceP2, currPayoffP1, currPayoffP2);
	}
	
	/**
	 * Registers the outcome for one round of PD.
	 * @param currChoiceP1 the move played by player 1.
	 * @param currChoiceP2 the move played by player 2.
	 * @param currPayoffP1 the payoff player 1 receives.
	 * @param currPayoffP2 the payoff player 2 receives.
	 */
	private void updateInfo(int currChoiceP1, int currChoiceP2, int currPayoffP1, int currPayoffP2) {
		
		//Check for any delay in the information that will be send, and reduce it if there is.
		lowerDelays(this.prevChoicesP2toP1, this.prevChoicesP2toP2);
		lowerDelays(this.prevChoicesP1toP2, this.prevChoicesP1toP1);
		
		//Remove any information that will not longer be send.
		if (this.upcomingRound > this.memorySize) {
			this.prevChoicesP1toP1.remove(0);
			this.prevChoicesP2toP1.remove(0);
			this.prevChoicesP2toP2.remove(0);
			this.prevChoicesP1toP2.remove(0);
		}
		
		//Add new information to send.
		this.prevChoicesP1toP1.add(currChoiceP1);
		this.prevChoicesP2toP2.add(currChoiceP2);
		
		InformationItem sendP2toP1 = informationChannel(currChoiceP2);
		InformationItem sendP1toP2 = informationChannel(currChoiceP1);
		
		
		this.upcomingRound++;
		this.prevChoicesP2toP1.add(sendP2toP1);
		this.prevChoicesP1toP2.add(sendP1toP2);
		
		//Record game results
		this.rec.dumpRound(currPayoffP1, currPayoffP2, currChoiceP1, currChoiceP2);
		
		//Record player results
		this.player1.dumpPayoff(currPayoffP1);
		this.player2.dumpPayoff(currPayoffP2);
	}
	
	/**
	 * Lower delay for delayed information. Possibly have that information arrive.
	 * Changes the first ArrayList you provide it.
	 * @param myMemory the player their memory of events.
	 * @param correctMemory the correct memory of past events.
	 */
	public static void lowerDelays(ArrayList<InformationItem> myMemory, ArrayList<Integer> correctMemory) {
		for(int i = 0; i < myMemory.size(); i++) {
			if(myMemory.get(i).getSecretDelay() > 1) {
				myMemory.get(i).decreaseSecretDelay();
			} else if (myMemory.get(i).getSecretDelay() == 1) {
				myMemory.set(i, new InformationItem(correctMemory.get(i), myMemory.get(i).getDelay(), 0));
			}
		}
	}

	/**
	 * Have information be corrupted with the chances as specified upon the creation of this Game object.
	 * @param choiceToSent the original information.
	 * @return the information to be send to the player, which is possibly corrupted.
	 */
	private InformationItem informationChannel(int choiceToSent) {
		InformationItem resultItem = new InformationItem(choiceToSent, 0, 0);
		
		Random randomP1 = new Random();
		double randomI1 = randomP1.nextDouble();
		
		if (randomI1 <= this.lostInfo) {
			
			//Information is lost
			resultItem = new InformationItem(-1, 0, 0);
			
		} else if (randomI1 <= (this.lostInfo + this.wrongInfo)) {
			
			//Information is wrong
			if (choiceToSent == 1) {
				resultItem = new InformationItem(2, 0, 0);
			} else if(choiceToSent == 2) {
				resultItem = new InformationItem(1, 0, 0);
			}
			
		} else if (randomI1 <= (this.lostInfo + this.wrongInfo + this.delayedInfo)) {
			
			//Information is delayed (can only happen when memory size is larger than 1)
			if (this.memorySize > 1) {
				int deterMinedDelay = 1; //if size 2
				
				if (this.memorySize > 2) {
					deterMinedDelay = 1 + randomP1.nextInt(this.memorySize - 2); //Think this is correct
				}
				resultItem = new InformationItem(-1, deterMinedDelay, deterMinedDelay);
			}
		}
		
		return resultItem;
	}

	/**
	 * Print the results of the IPD game so far.
	 */
	public void displayResults() {
		StringBuilder result = new StringBuilder();
		result.append(this.player1.getId());
		result.append(" - ");
		result.append(this.player2.getId());
		result.append(": ");
		result.append(this.rec.displayResultsShort());
		System.out.println(result.toString());
	}

	//Getters
	
	/**
	 * 
	 * @return player 1
	 */
	public Player getPlayer1() {
		return player1;
	}

	/**
	 * 
	 * @return player 2
	 */
	public Player getPlayer2() {
		return player2;
	}

	/**
	 *
	 * @return the memory length for strategies supported by this game of IPD.
	 */
	public int getMemorySize() {
		return memorySize;
	}

	/**
	 * 
	 * @return the rate at which information about the move played by the opponent that is sent to a player is lost
	 */
	public double getLostInfo() {
		return lostInfo;
	}

	/**
	 * 
	 * @return the rate at which information about the move played by the opponent that is sent to a player is wrong
	 */
	public double getWrongInfo() {
		return wrongInfo;
	}

	/**
	 * 
	 * @return the rate at which information about the move played by the opponent that is sent to a player is delayed
	 */
	public double getDelayedInfo() {
		return delayedInfo;
	}

	/**
	 * 
	 * @return payoff T
	 */
	public int getPayT() {
		return payT;
	}

	/**
	 * 
	 * @return payoff R
	 */
	public int getPayR() {
		return payR;
	}

	/**
	 * 
	 * @return payoff P
	 */
	public int getPayP() {
		return payP;
	}

	/**
	 * 
	 * @return payoff S
	 */
	public int getPayS() {
		return payS;
	}

	/**
	 * 
	 * @return the number of the next round to be played.
	 */
	public int getUpcomingRound() {
		return upcomingRound;
	}

	/**
	 * 
	 * @return the GameRecord that has recorded this game of IPD so far.
	 */
	public GameRecord getRec() {
		return rec;
	}

	/**
	 * 
	 * @return the previous choices of player 1 as send to player 1.
	 */
	public ArrayList<Integer> getPrevChoicesP1toP1() {
		return prevChoicesP1toP1;
	}

	/**
	 * 
	 * @return the previous choices of player 2 as send to player 2.
	 */
	public ArrayList<Integer> getPrevChoicesP2toP2() {
		return prevChoicesP2toP2;
	}

	/**
	 * 
	 * @return the previous choices of player 2 as send to player 1.
	 */
	public ArrayList<InformationItem> getPrevChoicesP2toP1() {
		return prevChoicesP2toP1;
	}

	/**
	 * 
	 * @return the previous choices of player 1 as send to player 2.
	 */
	public ArrayList<InformationItem> getPrevChoicesP1toP2() {
		return prevChoicesP1toP2;
	}
}
