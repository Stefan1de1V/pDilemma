package pDilemma;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import strategies.Strategy;

/**
 * Class for which instances can be used to record all information on a Iterated RR-tournament.
 * @author Stefan
 *
 */
public class TourneyRecord {
	
	private int memorySize;
	private int numberOfRoundsPlayed = 0;
	private ArrayList<RoundRecord> roundsData = new ArrayList<RoundRecord>();
	
	//Store the analysed material for the RoundRecords
	private ArrayList<Integer> numberOfCCs;
	private ArrayList<Integer> numberOfCDs;
	private ArrayList<Integer> numberOfDCs;
	private ArrayList<Integer> numberOfDDs;
	private int dominantCC;
	private int dominantCDC;
	private int dominantDD;
	private int notPrevalentCount;
	private double averageRoundNoPrevalent;
	private int firstHitCC;
	private int firstHitDD;
	private ArrayList<Integer> lowestPayoffs;
	private ArrayList<Integer> highestPayoffs;
	private ArrayList<Double> averagePayoffs;
	private double[][] aveStrategyAfterLRound;
	private ArrayList<Integer> totalAges;
	
	/**
	 * Create a TourneyRecord.
	 */
	public TourneyRecord(int memorySize) {
		this.memorySize = memorySize;
	}
	
	/**
	 * Record another round (RR-tournament) its information.
	 * @param record the RoundRecord to be added to this record.
	 */
	public void addRoundResult(RoundRecord record) {
		this.numberOfRoundsPlayed++;
		this.roundsData.add(record);
	}
	
	/**
	 * Analyse different aspects of the repeated RR-tournament.
	 * @param numberOfPlayers the number of players in the pool.
	 * @param numberOfRoundsPerGame the number of rounds in one game of two-player IPD.
	 */
	public void analyzeGivenRecords(int numberOfPlayers, int numberOfRoundsPerGame) {
		//Outcome based analysis
		analyse1();
		analyse2(numberOfPlayers, numberOfRoundsPerGame);
		analyse3(numberOfPlayers, numberOfRoundsPerGame);
		
		//Player based analysis
		analyse4();
		analyse5();
		analyse6();
		
		//(hopefully) free heap space
		this.roundsData = null;
	}

	/**
	 * Calculate how often CC, CD, DC and DD outcomes occurred for each round.
	 */
	private void analyse1() {
		//Amount of CC, CD, DC, DD
		this.numberOfCCs = new ArrayList<Integer>();
		this.numberOfCDs = new ArrayList<Integer>();
		this.numberOfDCs = new ArrayList<Integer>();
		this.numberOfDDs = new ArrayList<Integer>();
		
		for(int i = 0; i < this.numberOfRoundsPlayed; i++) {
			numberOfCCs.add(this.getRoundsData().get(i).getAmountOfCC());
			numberOfCDs.add(this.getRoundsData().get(i).getAmountOfCD());
			numberOfDCs.add(this.getRoundsData().get(i).getAmountOfDC());
			numberOfDDs.add(this.getRoundsData().get(i).getAmountOfDD());
		}
	}
	
	/**
	 * Calculate how often the frequency of CC, CD, DC and DD outcomes exceeded a determined threshold.
	 * Calculate how often NO frequency of outcomes in a round exceeded a determined threshold.
	 * Calculate the average round number at which the last mentioned occurs.
	 * @param numberOfPlayers the number of player participating in the repeated RR tournament.
	 * @param numberOfRoundsPerGame the number of rounds per game of IPD as played in this repeated
	 * RR tournament.
	 */
	private void analyse2(int numberOfPlayers, int numberOfRoundsPerGame) {
		//Criteria for considering strategies prevalent or dominant
		double prevalentCriteria = (double) Tourney.amountOfGamesPerRound(numberOfPlayers, numberOfRoundsPerGame) * 0.50;
		double dominantCriteria = (double) Tourney.amountOfGamesPerRound(numberOfPlayers, numberOfRoundsPerGame) * 0.65;
		
		//Initialise variables
		this.dominantCC = 0;
		this.dominantCDC = 0;
		this.dominantDD = 0;
		this.notPrevalentCount = 0;
		this.averageRoundNoPrevalent = 0.0;
		
		//Calculate how often no strategy was prevalent.
		for(int i = 0; i < this.numberOfRoundsPlayed; i++) {
			if ((double) this.roundsData.get(i).getAmountOfCC() < prevalentCriteria &&
					((double) this.roundsData.get(i).getAmountOfCD() + (double) this.roundsData.get(i).getAmountOfDC()) < prevalentCriteria &&
					(double) this.roundsData.get(i).getAmountOfDD() < prevalentCriteria) {
				
				this.notPrevalentCount++;
				this.averageRoundNoPrevalent += (i + 1);
			}
		}
		
		if(this.notPrevalentCount == 0) {
			this.averageRoundNoPrevalent = 0;
		} else {
			this.averageRoundNoPrevalent = (double) this.averageRoundNoPrevalent / (double) this.notPrevalentCount;
		}
		
		//Calculate how often no strategy was dominant.
		for(int i = 0; i < this.numberOfRoundsPlayed; i++) {
			if ((double) this.roundsData.get(i).getAmountOfCC() > dominantCriteria) {
				this.dominantCC++;
			} else if (((double) this.roundsData.get(i).getAmountOfCD() + (double) this.roundsData.get(i).getAmountOfDC()) > dominantCriteria) {
				this.dominantCDC++;
			} else if ((double) this.roundsData.get(i).getAmountOfDD() > dominantCriteria) {
				this.dominantDD++;
			}
		}
	}
	
	/**
	 * Calculate in which round there were only CC outcomes and DD outcomes.
	 * @param numberOfPlayers the number of player participating in the repeated RR tournament.
	 * @param numberOfRoundsPerGame the number of rounds per game of IPD as played in the repeated
	 * RR tournament.
	 */
	private void analyse3(int numberOfPlayers, int numberOfRoundsPerGame) {
		this.firstHitCC = 0;
		this.firstHitDD = 0;
		for(int i = 0; i < this.numberOfRoundsPlayed; i++) {
			if (this.firstHitCC == 0 && this.roundsData.get(i).getAmountOfCC() == Tourney.amountOfGamesPerRound(numberOfPlayers, numberOfRoundsPerGame)) {
				this.firstHitCC = i + 1;
			}
			if (this.firstHitDD == 0 && this.roundsData.get(i).getAmountOfDD() == Tourney.amountOfGamesPerRound(numberOfPlayers, numberOfRoundsPerGame)) {
				this.firstHitDD = i + 1;
			}
		}
	}
	
	/**
	 * Calculate the lowest, highest and average payoff for each round of the repeated RR tournament.
	 */
	private void analyse4() {
		this.lowestPayoffs = new ArrayList<Integer>();
		this.highestPayoffs = new ArrayList<Integer>();
		this.averagePayoffs = new ArrayList<Double>();
		for(int i = 0; i < this.numberOfRoundsPlayed; i++) {
			this.lowestPayoffs.add(this.roundsData.get(i).getLowestPayoff());
			this.highestPayoffs.add(this.roundsData.get(i).getHighestPayoff());
			this.averagePayoffs.add(this.roundsData.get(i).getAveragePayoff());
		}
	}
	
	/**
	 * Calculate the average strategy employed by the agents after the last round of the repeated RR tournament.
	 */
	private void analyse5() {
		//Get average strategy for the last RoundRecord (assume all roundRecords same memory size)
		int rows = Strategy.getRows(this.memorySize);
		int columns = Strategy.getColumns(this.memorySize);
		
		//this.averageStrategiesAfterEachRound = new double[this.numberOfRoundsPlayed][rows][columns];
		//for(int i = 0; i < this.numberOfRoundsPlayed; i++) {
		
		this.aveStrategyAfterLRound = new double[rows][columns];
		for(int j = 0; j < rows; j++) {
			for(int k = 0; k < columns; k++) {
				this.aveStrategyAfterLRound[j][k] = this.roundsData.get(this.numberOfRoundsPlayed - 1).getAverageStrategyMat()[j][k];
			}
		}
		
		//this.averageStrategiesAfterEachRound[i] = averageStrat;
	}
	
	/**
	 * Calculate the total age for each round of the repeated RR tournament.
	 */
	private void analyse6() {
		this.totalAges = new ArrayList<Integer>();
		for(int i = 0; i < this.numberOfRoundsPlayed; i++) {
			this.totalAges.add(this.roundsData.get(i).getTotalAge());
		}
	}

	/**
	 * Write some of the data recorded on the Iterated RR-tournament to a file.
	 * Only call when all given RoundRecord objects are analysed by the analyzeGivenRecords function.
	 */
	public void writeRecordedInfo(String txtName) {
		
		//Data to write
		ArrayList<String> lines = new ArrayList<String>();
		
		//Part 1: Frequency of each of the 4 results
		for(int i = 0; i < this.numberOfRoundsPlayed; i++) {
			
			StringBuilder resultString = new StringBuilder();
			resultString.append(this.numberOfCCs.get(i));
			resultString.append(" ");
			resultString.append(this.numberOfCDs.get(i));
			resultString.append(" ");
			resultString.append(this.numberOfDCs.get(i));
			resultString.append(" ");
			resultString.append(this.numberOfDDs.get(i));
			
			lines.add(resultString.toString());
		}
		
		//Separate
		lines.add(" ");
		
		//Part 2: Lowest, highest and average payoff per round
		for(int i = 0; i < this.numberOfRoundsPlayed; i++) {
			
			StringBuilder resultString = new StringBuilder();
			resultString.append(this.lowestPayoffs.get(i));
			resultString.append(" ");
			resultString.append(this.highestPayoffs.get(i));
			resultString.append(" ");
			resultString.append(this.averagePayoffs.get(i));
			
			lines.add(resultString.toString());
		}
		
		//Separate
		lines.add(" ");
		
		// Part 3: Average strategy values after the final round (assume all rounds same memorySize)
		//for(int i = 0; i < this.numberOfRoundsPlayed; i++) {

		for(int j = 0; j < Strategy.getRows(this.memorySize); j++) {
			StringBuilder resultString = new StringBuilder();
			
			for(int k = 0; k < Strategy.getColumns(this.memorySize); k++) {
				resultString.append(" ");
				resultString.append(this.aveStrategyAfterLRound[j][k]);
			}
			lines.add(resultString.toString());
		}
		
		//Attempt write
		try {
			Charset utf8 = StandardCharsets.UTF_8;
			Files.write(Paths.get(txtName), lines, utf8);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Check whether this TourneyRecord is equal to another Object.
	 */
	@Override
	public boolean equals(Object other) {
		if (other instanceof TourneyRecord) {
			TourneyRecord otherTourneyRecord = (TourneyRecord) other;
			if (this.numberOfRoundsPlayed == otherTourneyRecord.numberOfRoundsPlayed &&
					this.roundsData.equals(otherTourneyRecord.getRoundsData()) &&
					this.memorySize == otherTourneyRecord.getMemorySize()) {
				return true;
			}
		}
		return false;
	}

	//Getters
	
	/**
	 * 
	 * @return the number of RR-tournament played as recorded.
	 */
	public int getNumberOfRoundsPlayed() {
		return numberOfRoundsPlayed;
	}

	/**
	 * 
	 * @return the recorded information on all rounds, per round, in an ArrayList.
	 */
	public ArrayList<RoundRecord> getRoundsData() {
		return roundsData;
	}
	
	public int getMemorySize() {
		return memorySize;
	}

	//Data getters

	/**
	 * 
	 * @return the number of CC game outcomes for each generation.
	 */
	public ArrayList<Integer> getNumberOfCCs() {
		return numberOfCCs;
	}

	/**
	 * 
	 * @return the number of CD game outcomes for each generation.
	 */
	public ArrayList<Integer> getNumberOfCDs() {
		return numberOfCDs;
	}

	/**
	 * 
	 * @return the number of DC game outcomes for each generation.
	 */
	public ArrayList<Integer> getNumberOfDCs() {
		return numberOfDCs;
	}

	/**
	 * 
	 * @return the number of DD game outcomes for each generation.
	 */
	public ArrayList<Integer> getNumberOfDDs() {
		return numberOfDDs;
	}

	/**
	 * 
	 * @return the number of generations in which CC was the dominant game outcome.
	 */
	public int getDominantCC() {
		return dominantCC;
	}

	/**
	 * 
	 * @return the number of generations in which (CD + DC) was the dominant game outcome.
	 */
	public int getDominantCDC() {
		return dominantCDC;
	}

	/**
	 * 
	 * @return the number of generations in which DD was the dominant game outcome.
	 */
	public int getDominantDD() {
		return dominantDD;
	}

	/**
	 * 
	 * @return the number of generations in which no game outcome was prevalent.
	 */
	public int getNotPrevalentCount() {
		return notPrevalentCount;
	}

	/**
	 * 
	 * @return the average round number for the rounds in which no game outcome was prevalent.
	 * Returns 0 if there were no such rounds.
	 */
	public double getAverageRoundNoPrevalent() {
		return averageRoundNoPrevalent;
	}

	/**
	 * 
	 * @return the number of the first round in which there were only CC outcomes.
	 */
	public int getFirstHitCC() {
		return firstHitCC;
	}

	/**
	 * 
	 * @return the number of the first round in which there were only DD outcomes.
	 */
	public int getFirstHitDD() {
		return firstHitDD;
	}

	/**
	 * 
	 * @return the lowest payoff for each generation.
	 */
	public ArrayList<Integer> getLowestPayoffs() {
		return lowestPayoffs;
	}

	/**
	 * 
	 * @return the highest payoff for each generation.
	 */
	public ArrayList<Integer> getHighestPayoffs() {
		return highestPayoffs;
	}

	/**
	 * 
	 * @return the average payoff for each generation.
	 */
	public ArrayList<Double> getAveragePayoffs() {
		return averagePayoffs;
	}

	/**
	 * 
	 * @return the average strategy table for a player after the last round.
	 */
	public double[][] getAveStrategyAfterLRound() {
		return aveStrategyAfterLRound;
	}

	/**
	 * 
	 * @return the total age of the player pool for each round.
	 */
	public ArrayList<Integer> getTotalAges() {
		return totalAges;
	}
	
	//Generated data setters (should ONLY be used for testing purposes and when reading)

	/**
	 * 
	 * @param numberOfCCs the number of CC game outcomes for each generation.
	 */
	public void setNumberOfCCs(ArrayList<Integer> numberOfCCs) {
		this.numberOfCCs = numberOfCCs;
	}

	/**
	 * 
	 * @param numberOfCDs the number of CD game outcomes for each generation.
	 */
	public void setNumberOfCDs(ArrayList<Integer> numberOfCDs) {
		this.numberOfCDs = numberOfCDs;
	}

	/**
	 * 
	 * @param numberOfDCs the number of DC game outcomes for each generation.
	 */
	public void setNumberOfDCs(ArrayList<Integer> numberOfDCs) {
		this.numberOfDCs = numberOfDCs;
	}

	/**
	 * 
	 * @param numberOfDDs the number of DD game outcomes for each generation.
	 */
	public void setNumberOfDDs(ArrayList<Integer> numberOfDDs) {
		this.numberOfDDs = numberOfDDs;
	}

	/**
	 * 
	 * @param dominantCC the number of rounds in which the CC game outcome was dominant.
	 */
	public void setDominantCC(int dominantCC) {
		this.dominantCC = dominantCC;
	}

	/**
	 * 
	 * @param dominantCDC the number of rounds in which the (CD + DC) game outcome was dominant.
	 */
	public void setDominantCDC(int dominantCDC) {
		this.dominantCDC = dominantCDC;
	}

	/**
	 * 
	 * @param dominantDD the number of rounds in which the DD game outcome was dominant.
	 */
	public void setDominantDD(int dominantDD) {
		this.dominantDD = dominantDD;
	}

	/**
	 * 
	 * @param notPrevalentCount the number of rounds in which there was no dominant game outcome.
	 */
	public void setNotPrevalentCount(int notPrevalentCount) {
		this.notPrevalentCount = notPrevalentCount;
	}

	/**
	 * 
	 * @param averageRoundNoPrevalent the average round number of the rounds in which there
	 * was no dominant game outcome.
	 */
	public void setAverageRoundNoPrevalent(double averageRoundNoPrevalent) {
		this.averageRoundNoPrevalent = averageRoundNoPrevalent;
	}

	/**
	 * 
	 * @param firstHitCC the first round in which there were only CC game outcomes.
	 */
	public void setFirstHitCC(int firstHitCC) {
		this.firstHitCC = firstHitCC;
	}

	/**
	 * 
	 * @param firstHitDD the first round in which there were only DD game outcomes.
	 */
	public void setFirstHitDD(int firstHitDD) {
		this.firstHitDD = firstHitDD;
	}

	/**
	 * 
	 * @param lowestPayoffs the lowest player payoff for each generation.
	 */
	public void setLowestPayoffs(ArrayList<Integer> lowestPayoffs) {
		this.lowestPayoffs = lowestPayoffs;
	}

	/**
	 * 
	 * @param highestPayoffs the highest player payoff for each generation.
	 */
	public void setHighestPayoffs(ArrayList<Integer> highestPayoffs) {
		this.highestPayoffs = highestPayoffs;
	}

	/**
	 * 
	 * @param averagePayoffs the average player payoff for each generation.
	 */
	public void setAveragePayoffs(ArrayList<Double> averagePayoffs) {
		this.averagePayoffs = averagePayoffs;
	}

	/**
	 * 
	 * @param aveStrategyAfterLRound the average strategy table of the players after the last round.
	 */
	public void setAveStrategyAfterLRound(double[][] aveStrategyAfterLRound) {
		this.aveStrategyAfterLRound = aveStrategyAfterLRound;
	}

	/**
	 * 
	 * @param totalAges the total age of the player pool for each generation.
	 */
	public void setTotalAges(ArrayList<Integer> totalAges) {
		this.totalAges = totalAges;
	}
}
