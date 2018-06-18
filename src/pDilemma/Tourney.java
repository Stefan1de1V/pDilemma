package pDilemma;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Class that simulates an Iterated Round Robin tournament of the Iterated Prisoner's Dilemma.
 * Each game is a two-player game, and it's possible to specify the number of RR-tournaments,
 * the number of games for each IPD game, and varying degrees of noise.
 * @author Stefan
 *
 */
public class Tourney {

	//List of players participating in the next round and the memory size of the tournament
	private ArrayList<Player> playerList;
	private int memorySize;
	
	//Record results
	private TourneyRecord record;
	private RoundRecord roundRecord;
	
	/**
	 * Create the Iterated Round Robin tournament for a given list of players.
	 * @param playerList the list of players participating in the tournament.
	 */
	public Tourney(ArrayList<Player> playerList, int memorySize) {
		this.playerList = playerList;
		this.memorySize = memorySize;
		this.record = new TourneyRecord(this.memorySize);
	}
	
	/**
	 * Play a number of RR tournaments, the pool of players evolves after each of them.
	 * @param numberOfRoundsPerGame the number of rounds in one two-player game of IPD.
	 * @param numberOfTourneys the number of RR-tournament to be played.
	 */
	public void repeatedlyPlayTourney (int numberOfRoundsPerGame, int numberOfTourneys) {
		repeatedlyPlayTourney(numberOfRoundsPerGame, numberOfTourneys, 0, 0, 0);
	}
	
	/**
	 * Play a number of RR tournaments, the pool of players evolves after each of them.
	 * @param numberOfRoundsPerGame the number of rounds in one two-player game of IPD.
	 * @param numberOfTourneys the number of RR-tournament to be played.
	 * @param lostRate the rate at which information about the move an opponent plays is lost (to a player).
	 * @param wrongRate the rate at which information about the move an opponent plays is wrong (to a player).
	 * @param delayRate the rate at which information about the move an opponent plays is delayed (to a player).
	 */
	public void repeatedlyPlayTourney(int numberOfRoundsPerGame, int numberOfTourneys,
			double lostRate, double wrongRate, double delayRate) {
		
		//Play tournaments
		for (int i = 0; i < numberOfTourneys; i++) {
			
			//Reset roundRecord to record results of a new round
			this.roundRecord = new RoundRecord(this.memorySize);
			
			//Play tournament
			playTourney(numberOfRoundsPerGame, lostRate, wrongRate, delayRate);
			
			//Add round results to record
			this.roundRecord.recordPlayerBasedInfo(this.playerList);
			this.record.addRoundResult(roundRecord);
			
			//Change pool of agents
			changePool(this.playerList, 0.50);
			
			//Reset payoffs
			resetPayoffs();
			
			//Print new population of agents
			//printTourneyResults();
		}
	}
	
	/**
	 * Set the accumulated payoff for each player to 0 (used about each RR-tournament).
	 */
	private void resetPayoffs() {
		for(int j = 0; j < this.playerList.size(); j ++) {
			this.playerList.get(j).payoffReset();
		}
	}

	/**
	 * Change the pool of players participating in tournament. The players with the lowest payoff
	 * are kicked out while the most successful agents are allowed to reproduce.
	 * @param playerList the pool of players to change.
	 * @param the maximum percentage of players to kick out (between 0 and 0.5).
	 */
	public static void changePool(ArrayList<Player> playerList, double percentageLost) {

		int numberOfAgentsKicked = (int) Math.floor((double) playerList.size() * percentageLost);
		int numberOfAgentsKept = playerList.size() - numberOfAgentsKicked;
		
		//Sort players based on payoff
		playerList.sort(new Comparator<Player>() {

			@Override
			public int compare(Player player1, Player player2) {
				return Integer.compare(player1.getCollectedPayoff(), player2.getCollectedPayoff());
			}
		});
		
		//Remove players with lowest payoff
		for(int j = 0; j < numberOfAgentsKicked; j++) {
			playerList.remove(0);
		}
		
		//Reproduce agents with highest payoff
		ArrayList<Player> newPlayers = new ArrayList<Player>();
		for(int j = 0; j < numberOfAgentsKicked; j++) {
			newPlayers.add(playerList.get(numberOfAgentsKept - 1 - j).reproduce());
		}
		
		//The provided playerList is now changed!
		playerList.addAll(newPlayers);
	}

	/**
	 * Play all games in for one round of RR-tournament.
	 * @param numberOfRounds the number of rounds for one game of IPD
	 * @param lostRate the rate at which information about the move an opponent plays is lost (to a player).
	 * @param wrongRate the rate at which information about the move an opponent plays is wrong (to a player).
	 * @param delayRate the rate at which information about the move an opponent plays is delayed (to a player).
	 */
	private void playTourney(int numberOfRounds, double lostRate, double wrongRate, double delayedRate) {
		
		//Play all games for a single RR tourney
		for (int player1Num = 0; player1Num < this.playerList.size(); player1Num++) {
			for (int player2Num = 0; player2Num < this.playerList.size(); player2Num++) {
				if (player1Num < player2Num) {
					playIPD(player1Num, player2Num, numberOfRounds, lostRate, wrongRate, delayedRate);
				}
			}
		}
	}
	
	/**
	 * Play a single two-player game of IPD and record the results (and reset player memory for next game).
	 * @param player1Num the location of the first participating player in the playerList
	 * @param player2Num the location of the second participating player in the playerList.
	 * @param numberOfRounds the number of rounds in the game.
	 * @param lostRate the rate at which information about the move an opponent plays is lost (to a player).
	 * @param wrongRate the rate at which information about the move an opponent plays is wrong (to a player).
	 * @param delayRate the rate at which information about the move an opponent plays is delayed (to a player).
	 */
	private void playIPD(int player1Num, int player2Num, int numberOfRounds,
			double lostRate, double wrongRate, double delayedRate) {
		
		//Play the IPD game
		Game game = new Game(this.playerList.get(player1Num), this.playerList.get(player2Num),
				this.memorySize, lostRate, wrongRate, delayedRate);
		for (int i = 0; i < numberOfRounds; i++) {
			game.playRound();
		}
		
		//Add results to results for the current round
		this.roundRecord.addGameResult(game.getRec());
		
		//Have players reset their memory in preparation for the next IPD game
		this.playerList.get(player1Num).memoryReset();
		this.playerList.get(player2Num).memoryReset();
	}
	
	/**
	 * Calculate the number of games (rounds) played in one RR tournament.
	 * @param numberOfPlayers the number of participating players.
	 * @param numberOfRoundsPerGame the number of rounds in one game of IPD.
	 * @return the number of Prisoner Dilemma games played in the RR tournament as specified.
	 */
	public static int amountOfGamesPerRound(int numberOfPlayers, int numberOfRoundsPerGame) {
		int result = 0;
		
		for(int i = 1; i < numberOfPlayers; i++) {
			result += i;
		}
		
		return (result * numberOfRoundsPerGame);
	}
	
	/**
	 * Print the players in the playerList and their collectedPayoff.
	 */
	public void printTourneyPlayersWithPayoff() {
		System.out.println("------Print set------");
		for (int i = 0; i < this.playerList.size(); i++) {
			System.out.println(this.playerList.get(i).getId() + ": " +
					this.playerList.get(i).getCollectedPayoff());
		}
	}
	
	//Getters

	/**
	 * 
	 * @return the list of players currently in the playerList. If used outside of the Tourney class, it means
	 * the players participating in the next RR-tournament.
	 */
	public ArrayList<Player> getPlayerList() {
		return playerList;
	}
	
	/**
	 * 
	 * @return the memory size of the players participating in the tournament
	 */
	public int getMemorySize() {
		return memorySize;
	}

	/**
	 * 
	 * @return the data that has been recorded on every played RR-tournament (every round) so far.
	 */
	public TourneyRecord getRecord() {
		return record;
	}
}
