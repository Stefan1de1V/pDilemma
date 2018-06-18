package strategies;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
/**
 * This class represents the strategy employed by a player in a two-player game of IPD.
 * @author Stefan
 *
 */
public class Strategy {
	
	private int[][] strategy;
	private int memorySize;
	
	/**
	 * Create a strategy object by providing data structures detailing exactly how it works.
	 * For the entries of the strategy table: 1 is cooperate, 2 is defect.
	 * Make sure the size of the array is appropriate as to the size of the memory you provide.
	 * @param memorySize the length of the memory of the player using the provided strategy.
	 * @param strategy the strategy to be used by the player.
	 */
	public Strategy(int memorySize, int[][] strategy) {
		
		this.memorySize = memorySize;
		this.strategy = strategy;
	}
	
	/**
	 * Create a standard Strategy for a player (choice between 4 strategies of memory size 1.
	 * @param stratNumber which standard strategy to use
	 */
	public Strategy(int stratNumber) {
		this.memorySize = 1;
		createDataStructures();
		setStrategy(stratNumber);
	}
	
	/**
	 * Create data structures of the appropriate sizes given this.memorySize.
	 */
	private void createDataStructures() {
		
		this.strategy = new int[getRows(this.memorySize)][getColumns(this.memorySize)];
	}
	
	/**
	 * Calculates the number of rows a Strategy of a certain memorySize should have.
	 * @param memorySize the memorySize of the Strategy.
	 * @return the number of rows the Strategy table should have.
	 */
	public static int getRows(int memorySize) {
		int rows = 0;
		for (int i = 0; i <= memorySize; i++) {
			rows += (int) Math.pow(2, i); //2 values
		}
		return rows;
		//return (int) Math.pow(2, memorySize);
	}
	
	/**
	 * Calculates the number of columns a Strategy of a certain memorySize should have.
	 * @param memorySize the memorySize of the Strategy.
	 * @return the number of columns the Strategy table should have.
	 */
	public static int getColumns(int memorySize) {
		int columns = 0;
		for (int i = 0; i <= memorySize; i++) {
			columns += (int) Math.pow((2 * memorySize + 1), i); //2^n+1 values
		}
		return columns;
	}

	/**
	 * Determine the next move using this strategy.
	 * The move recently played by the players is the highest entry in the ArrayLists.
	 * @param round the round to be played.
	 * @param prevChoiceMe the previous moves made by the player that uses this strategy.
	 * @param prevChoiceOpponent the previous moves made by the opponent of the player using this strategy.
	 * @return the next move to used be used by the player employing this strategy.
	 */
	public int nextMove(int upcomingRound, ArrayList<Integer> prevChoicesMe,
			ArrayList<InformationItem> prevChoicesOpponent) {
		
		//Determine what entry of the table to use
		int row = lookupRow(upcomingRound, this.memorySize, prevChoicesMe);
		int column = lookupColumn(upcomingRound, this.memorySize, prevChoicesOpponent);
		
		//Return the next move
		return this.strategy[row][column];
	}

	/**
	 * Determine the row of the strategy table to look up.
	 * The move recently played by the players is the highest entry in the ArrayList.
	 * The move recently played move is of the highest significance.
	 * @param upcomingRound the next round to be played.
	 * @param memorySize the memory size of the strategy.
	 * @param previousChoicesMe the most recent moves played by the player.
	 * @return the row number of the entry which tells the next move this player will play.
	 */
	public static int lookupRow(int upcomingRound, int memorySize, ArrayList<Integer> previousChoicesMe) {
		int result = 0;
		
		//Skip to the rows representing the good amount of filled memory
		for(int i = 2; i <= (memorySize + 1); i++) {
			if(upcomingRound >= i) {
				result += (int) Math.pow(2, (i - 2));
			}
		}
		
		//For the filled memory, add rows based on the values in said memory
		for(int i = 0; i < Math.min((upcomingRound - 1), memorySize); i++) {
			result += ((int) Math.pow(2, i)) * (previousChoicesMe.get(i) - 1);
		}
		
		return result;
	}
	
	/**
	 * Determine the column of the strategy table to look up.
	 * The move recently played by the opponent is the highest entry in the ArrayList.
	 * The move recently played move is of the highest significance.
	 * @param upcomingRound the next round to be played.
	 * @param memorySize the memory size of the strategy.
	 * @param previousChoicesMe the most recent moves played by the opponent.
	 * @return the column number of the entry which tells the next move this player will play.
	 */
	public static int lookupColumn(int upcomingRound, int memorySize,
			ArrayList<InformationItem> prevChoicesOpponent) {
		
		int result = 0;
		
		//Skip to the rows representing the good amount of filled memory
		for(int i = 2; i <= (memorySize + 1); i++) {
			if(upcomingRound >= i) {
				result += (int) Math.pow((memorySize * 2 + 1), (i - 2));
			}
		}
				
		//For the filled memory, add columns based on the values in said memory
		for(int i = 0; i < Math.min((upcomingRound - 1), memorySize); i++) {
			int columnValue;
			if(prevChoicesOpponent.get(i).getCoopDefectLost() == -1) {
				columnValue = 2 * memorySize; //Think so
			} else {
				columnValue = prevChoicesOpponent.get(i).getDelay(); //between 0 and upcomingRound (cuz known)
				if(prevChoicesOpponent.get(i).getCoopDefectLost() == 2) {
					columnValue += memorySize; //Think so
				}
			}
			result += (((int) Math.pow((memorySize * 2 + 1), i)) * columnValue);
		}
		return result;
	}

	/**
	 * Change some of the parameters in this strategy (1 to 2 or 2 to 1).
	 * @return offspring of this strategy that is different for some of the entries in its table.
	 */
	public Strategy getMutation() {
		
		//Create data structure
		int rows = getRows(this.memorySize);
		int columns = getColumns(this.memorySize);		
		int[][] newStrategy = new int[rows][columns];
		
		//Copy old values into new data structure
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				newStrategy[i][j] = this.strategy[i][j];
			}
		}
		
		//For memory size 1: Same as change 1 random entry
		//For higher memory sizes: More changes to compensate for lower change of getting used table entry
		
		//Select which entries to change
		ArrayList<Integer> listOfEntries = new ArrayList<Integer>();
		for(int i = 0; i < (rows * columns); i++) {
			listOfEntries.add(i);
		}
		Collections.shuffle(listOfEntries);
		
		//Change entries
		for(int i = 0; i < Strategy.getNumberOfChanges(this.memorySize); i++) {
			int entry = listOfEntries.get(i);
			int rowToChange  = 0;
			while (entry > (columns - 1)) {
				entry -= columns;
				rowToChange++;
			}
			int columnToChange = entry;
			
			//Change selected entry (1 to 2 or 2 to 1)
			if (newStrategy[rowToChange][columnToChange] == 1) {
				newStrategy[rowToChange][columnToChange] = 2;
			} else {
				//if(newStrategy[rowToChange][columnToChange] == 2) no condition cuz than
				//it's certain something changes (initialised at 0... if I understand correctly)
				newStrategy[rowToChange][columnToChange] = 1;
			}
		}
		
		return new Strategy(this.memorySize, newStrategy);
	}
	
	/**
	 * Determine how many entries of the strategy table to change upon mutation.
	 * @param memorySize the memory length of the strategy.
	 * @return the number of entries to be mutated.
	 */
	public static int getNumberOfChanges(int memorySize) {
		//Some entries are not used because we only study cases where memory of both participating players
		//Is of equal length. There are MANY places in the code with memorySize is assumed constant
		//for every Player in an object or list
		
		int totalUsedEntries = 0;
		int totalEntries = 0;
		
		for (int j = 0; j <= memorySize; j++) {
			for(int k = 0; k <= memorySize; k++) {
				int entries = (int) Math.pow(2, j) * (int) Math.pow((2 * memorySize + 1), k);
				totalEntries += entries;
				if(j == k) {
					totalUsedEntries += entries;
				}
			}
		}
		
		//Memory table size 1 constants
		double totalEntriesMem1 = 12.0;
		double shareOfHitsMem1 = 7.0 / 12.0;
		
		//The rate for change with 1 mutation memorySize 1 table
		double normalChangeRate = 1.0 / totalEntriesMem1;
		
		//Less chance of hitting for higher memorySize compensated by increased number of mutations
		double shareUsed = (double) totalUsedEntries / (double) totalEntries;
		double inverse = Math.pow((Math.pow(shareOfHitsMem1, -1) / Math.pow(shareUsed, -1)), -1);
		double numberOfEntriesToChange = (double) totalEntries * normalChangeRate * inverse;
		return (int) Math.floor(numberOfEntriesToChange);
	}

	/**
	 * Determine whether two strategies are equal to each other.
	 */
	@Override
	public boolean equals(Object other) {
		if (other instanceof Strategy) {
			Strategy otherStrat = (Strategy) other;
			
			//I think this array comparison is correct?
			//Arrays.equals(this.firstRounds, otherStrat.getFirstRounds())
			if (this.memorySize == otherStrat.getMemorySize() &&
				Arrays.deepEquals(this.strategy, otherStrat.getStrategy())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Set the strategy to one of the standard memory size 1 strategies.
	 * @param stratNumber the identifier of the standard strategy to use.
	 */
	private void setStrategy(int stratNumber) {
		switch(stratNumber) {
		case 1: //TFT
			this.strategy[1][1] = 1;
			this.strategy[1][2] = 2;
			this.strategy[1][3] = 1;
			this.strategy[2][1] = 1;
			this.strategy[2][2] = 2;
			this.strategy[2][3] = 1;
			this.strategy[0][0] = 1;
			break;
		case 2: //Always cooperate
			this.strategy[1][1] = 1;
			this.strategy[1][2] = 1;
			this.strategy[1][3] = 1;
			this.strategy[2][1] = 1;
			this.strategy[2][2] = 1;
			this.strategy[2][3] = 1;
			this.strategy[0][0] = 1;
			break;
		case 3: //Always defect
			this.strategy[1][1] = 2;
			this.strategy[1][2] = 2;
			this.strategy[1][3] = 2;
			this.strategy[2][1] = 2;
			this.strategy[2][2] = 2;
			this.strategy[2][3] = 2;
			this.strategy[0][0] = 2;
			break;
		default: //Unforgiving
			this.strategy[1][1] = 1;
			this.strategy[1][2] = 2;
			this.strategy[1][3] = 1;
			this.strategy[2][1] = 2;
			this.strategy[2][2] = 2;
			this.strategy[2][3] = 2;
			this.strategy[0][0] = 1;
			break;
		}
	}

	//Getters
	
	/**
	 * 
	 * @return the memory size of the strategy.
	 */
	public int getMemorySize() {
		return memorySize;
	}
	
	/**
	 * 
	 * @return the table representing this strategy.
	 */
	public int[][] getStrategy() {
		return strategy;
	}

}
