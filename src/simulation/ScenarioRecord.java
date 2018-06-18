package simulation;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import pDilemma.TourneyRecord;
import strategies.Strategy;

/**
 * This class can take a number of TourneyRecord objects and average out their data.
 * @author Stefan
 *
 */
public class ScenarioRecord {

	private int memorySize;
	private ArrayList<TourneyRecord> analysedTRecords = new ArrayList<TourneyRecord>();
	
	private ArrayList<Double> averageNumberOfCCs = new ArrayList<Double>();
	private ArrayList<Double> averageNumberOfCDCs = new ArrayList<Double>();
	private ArrayList<Double> averageNumberOfDDs = new ArrayList<Double>();
	private double averageDominantCC;
	private double averageDominantCDC;
	private double averageDominantDD;
	private double averageAmountNoPrevalent;
	private double AARoundNoPrevalent;
	private double averageFirstHitCC;
	private double averageFirstHitDD;
	private ArrayList<Double> averageLowestPayoffs = new ArrayList<Double>();
	private ArrayList<Double> averageHighestPayoffs = new ArrayList<Double>();
	private ArrayList<Double> averageAveragePayoffs = new ArrayList<Double>(); //This name though
	private ArrayList<Double> stdDevs = new ArrayList<Double>();
	private double[][] AASALR;
	private ArrayList<Double> ATA = new ArrayList<Double>();
	
	/**
	 * Create a ScenarioRecord
	 * @param memorySize the memorySize associated with certain data structures stored in the TourneyRecord
	 * objects this class aims to analyse.
	 */
	public ScenarioRecord(int memorySize) {
		this.memorySize = memorySize;
	}
	
	/**
	 * Add a TourneyRecord to the collection of TourneyRecord objects to be analysed by this object.
	 * @param record the TourneyRecord to add.
	 */
	public void addRecord(TourneyRecord record) {
		this.analysedTRecords.add(record);
	}
	
	/**
	 * Analyses the given TourneyRecord objects and stores the results in parameters of this object.
	 * @param numberOfTourneysPerSim the number of rounds played in each of the TourneyRecord objects.
	 */
	public void analyzeTRecords(int numberOfTourneysPerSim) {
		
		analyse1(numberOfTourneysPerSim);
		analyse2();
		analyse3();
		
		analyse4(numberOfTourneysPerSim);
		analyse5(numberOfTourneysPerSim);
		analyse6(numberOfTourneysPerSim);
		
		//(Hopefully) free heap space
		this.analysedTRecords = null;
	}

	/**
	 * Calculate the average number of CC, (CD + DC) and DD outcome games for all rounds of the
	 * TourneyRecords objects given for this object.
	 * Please call analyzeTRecords instead unless testing.
	 * @param numberOfTourneysPerSim the number of rounds played in each of the TourneyRecord objects.
	 */
	public void analyse1(int numberOfTourneysPerSim) {
		int[] totalCCs = new int[numberOfTourneysPerSim];
		int[] totalCDCs = new int[numberOfTourneysPerSim];
		int[] totalDDs = new int[numberOfTourneysPerSim];
		
		for(int i = 0; i < this.analysedTRecords.size(); i++) {
			for(int j = 0; j < numberOfTourneysPerSim; j++) {
				totalCCs[j] += this.analysedTRecords.get(i).getNumberOfCCs().get(j);
				totalCDCs[j] += this.analysedTRecords.get(i).getNumberOfCDs().get(j);
				totalCDCs[j] += this.analysedTRecords.get(i).getNumberOfDCs().get(j);
				totalDDs[j] += this.analysedTRecords.get(i).getNumberOfDDs().get(j);
			}
		}
		
		for(int i = 0; i < numberOfTourneysPerSim; i++) {
			this.averageNumberOfCCs.add((double) totalCCs[i] / (double) this.analysedTRecords.size());
			this.averageNumberOfCDCs.add((double) totalCDCs[i] / (double) this.analysedTRecords.size());
			this.averageNumberOfDDs.add((double) totalDDs[i] / (double) this.analysedTRecords.size());
		}
	}
	
	/**
	 * Calculate the average number of times the CC, (CD + DC) and DD outcomes were dominant for all
	 * of the TourneyRecord objects provided. Calculate the average number of rounds where no particular
	 * outcome was prevalent and the average of the average round number at which this occurs.
	 * Please call analyzeTRecords instead unless testing.
	 */
	public void analyse2() {
		
		//Average number of times CC, CDC and DD were dominant
		int totalDominantCC = 0;
		int totalDominantCDC = 0;
		int totalDominantDD = 0;
		for(int i = 0; i < this.analysedTRecords.size(); i++) {
			totalDominantCC += this.analysedTRecords.get(i).getDominantCC();
			totalDominantCDC += this.analysedTRecords.get(i).getDominantCDC();
			totalDominantDD += this.analysedTRecords.get(i).getDominantDD();
		}
		this.averageDominantCC = (double) totalDominantCC / (double) this.analysedTRecords.size();
		this.averageDominantCDC = (double) totalDominantCDC / (double) this.analysedTRecords.size();
		this.averageDominantDD = (double) totalDominantDD / (double) this.analysedTRecords.size();
		
		//Amount of rounds (RR-tournaments) where no CC/CDC/DD outcome was prevalent
		//and the average round number in which this occurred
		int totalNoPrevalent = 0;
		double totalAverageNoPrevalentRNumber = 0.0;
		int happening = 0;
		for(int i = 0; i < this.analysedTRecords.size(); i++) {
			totalNoPrevalent += this.analysedTRecords.get(i).getNotPrevalentCount();
			
			//NEW1: only add average if based on data.
			if (this.analysedTRecords.get(i).getNotPrevalentCount() > 0) {
				totalAverageNoPrevalentRNumber += this.analysedTRecords.get(i).getAverageRoundNoPrevalent();
				happening++;
			}
		}
		this.averageAmountNoPrevalent = (double) totalNoPrevalent / (double) this.analysedTRecords.size();
		this.AARoundNoPrevalent = (double) totalAverageNoPrevalentRNumber / (double) happening;
	}
	
	/**
	 * Calculate the average first round at which only the CC and DD outcomes occur, for the given
	 * TourneyRecord objects.
	 * Please call analyzeTRecords instead unless testing.
	 */
	public void analyse3() {
		//Average first round in which there is 100% CC and DD
		int totalRFirstCC = 0;
		int totalRFirstDD = 0;
		int happenCC = 0;
		int happenDD = 0;
		for(int i = 0; i < this.analysedTRecords.size(); i++) {
			
			//NEW2: only add if actually occurred.
			if(this.analysedTRecords.get(i).getFirstHitCC() > 0) {
				totalRFirstCC += this.analysedTRecords.get(i).getFirstHitCC();
				happenCC++;
			}
			if(this.analysedTRecords.get(i).getFirstHitDD() > 0) {
				totalRFirstDD += this.analysedTRecords.get(i).getFirstHitDD();
				happenDD++;
			}
		}
		
		if(happenCC == 0) {
			this.averageFirstHitCC = 0.0;
		} else {
			this.averageFirstHitCC = (double) totalRFirstCC / (double) happenCC;
		}
		
		if(happenDD == 0) {
			this.averageFirstHitDD = 0.0;
		} else {
			this.averageFirstHitDD = (double) totalRFirstDD / (double) happenDD;
		}
	}

	/**
	 * Calculate the average lowest payoff, highest payoff and average payoff for all rounds for the
	 * provided TourneyRecord objects.
	 * Please call analyzeTRecords instead unless testing.
	 * @param numberOfTourneysPerSim the number of rounds played in each of the TourneyRecord objects.
	 */
	public void analyse4(int numberOfTourneysPerSim) {
		int[] totalLPs = new int[numberOfTourneysPerSim];
		int[] totalHPs = new int[numberOfTourneysPerSim];
		int[] totalAPs = new int[numberOfTourneysPerSim];
		
		for(int i = 0; i < this.analysedTRecords.size(); i++) {
			for(int j = 0; j < numberOfTourneysPerSim; j++) { //Is the number of rounds per simulation
				totalLPs[j] += this.analysedTRecords.get(i).getLowestPayoffs().get(j);
				totalHPs[j] += this.analysedTRecords.get(i).getHighestPayoffs().get(j);
				totalAPs[j] += this.analysedTRecords.get(i).getAveragePayoffs().get(j);
			}
		}
		
		for(int i = 0; i < numberOfTourneysPerSim; i++) {
			this.averageLowestPayoffs.add((double) totalLPs[i] / (double) this.analysedTRecords.size());
			this.averageHighestPayoffs.add((double) totalHPs[i] / (double) this.analysedTRecords.size());
			this.averageAveragePayoffs.add((double) totalAPs[i] / (double) this.analysedTRecords.size());
		}
		
		//Calculate standard deviations
		for(int i = 0; i < numberOfTourneysPerSim; i++) {
			double squaredSum = 0;
			
			for(int j = 0; j < this.analysedTRecords.size(); j++) {
				squaredSum += Math.pow((this.averageAveragePayoffs.get(i) - 
						this.analysedTRecords.get(j).getAveragePayoffs().get(i)), 2.0);
			}
			
			squaredSum = squaredSum / (double) this.analysedTRecords.size();
			squaredSum = Math.sqrt(squaredSum);
			this.stdDevs.add(squaredSum);
		}
	}
	
	/**
	 * Calculate the average strategy played after the last round for the given TourneyRecord objects.
	 * Please call analyzeTRecords instead unless testing.
	 * @param numberOfTourneysPerSim the number of rounds played in each of the TourneyRecord objects.
	 */
	public void analyse5(int numberOfTourneysPerSim) {
		int rows = Strategy.getRows(this.memorySize);
		int columns = Strategy.getColumns(this.memorySize);
		this.AASALR = new double[rows][columns];
		
		for(int i = 0; i < this.analysedTRecords.size(); i++) {			
			for(int k = 0; k < rows; k++) {
				for(int l = 0; l < columns; l++) {						
					this.AASALR[k][l] += this.analysedTRecords.get(i).getAveStrategyAfterLRound()[k][l];
				}
			}
		}
		
		for(int k = 0; k < rows; k++) {
			for(int l = 0; l < columns; l++) {
				this.AASALR[k][l] = this.AASALR[k][l] / (double) this.analysedTRecords.size();
			}
		}
	}
	
	/**
	 * Calculate the average total age after each round for the provided TourneyRecord objects.
	 * Please call analyzeTRecords instead unless testing.
	 * @param numberOfTourneysPerSim the number of rounds played in each of the TourneyRecord objects.
	 */
	public void analyse6(int numberOfTourneysPerSim) {
		int[] totalAges = new int[numberOfTourneysPerSim];
		
		for(int i = 0; i < this.analysedTRecords.size(); i++) {
			for(int j = 0; j < numberOfTourneysPerSim; j++) {
				totalAges[j] += this.analysedTRecords.get(i).getTotalAges().get(j);
			}
		}
		for(int i = 0; i < numberOfTourneysPerSim; i++) {
			this.ATA.add((double) totalAges[i] / (double) this.analysedTRecords.size());
		}
	}
	
	
	
	/**
	 * After the data of the provided TourneyRecord objects has been analysed, this method can write
	 * the results to a file.
	 * @param fileName the name of the file the write to.
	 * @param numberOfTourneys the number of rounds played in each of the TourneyRecord objects.
	 */
	public void writeData(String fileName, int numberOfTourneys) {
		//Data to write
		ArrayList<String> lines = new ArrayList<String>();
		
		//Average CCs, CDCs, DDs (analyse 1)
		for(int i = 0; i < numberOfTourneys; i++) {
			StringBuilder builder = new StringBuilder();
			builder.append(this.averageNumberOfCCs.get(i) + " ");
			builder.append(this.averageNumberOfCDCs.get(i) + " ");
			builder.append(this.averageNumberOfDDs.get(i));
			lines.add(builder.toString());
		}
		
		//Average dominantCC, dominantCDC, dominantDD, AmountNoPrevalent, RoundNoPrevalent,
		//first hit CC, first hit DD (analyse 2 + 3)
		lines.add(" ");
		lines.add(((new StringBuilder()).append(this.averageDominantCC)).toString());
		lines.add(((new StringBuilder()).append(this.averageDominantCDC)).toString());
		lines.add(((new StringBuilder()).append(this.averageDominantDD)).toString());
		lines.add(((new StringBuilder()).append(this.averageAmountNoPrevalent)).toString());
		lines.add(((new StringBuilder()).append(this.AARoundNoPrevalent)).toString());
		lines.add(((new StringBuilder()).append(this.averageFirstHitCC)).toString());
		lines.add(((new StringBuilder()).append(this.averageFirstHitDD)).toString());
		
		//Average Lowest, Highest, Average Payoff and standard deviation (analyse 4)
		lines.add(" ");
		for(int i = 0; i < numberOfTourneys; i++) {
			StringBuilder builder = new StringBuilder();
			builder.append(this.averageLowestPayoffs.get(i) + " ");
			builder.append(this.averageHighestPayoffs.get(i) + " ");
			builder.append(this.averageAveragePayoffs.get(i) + " ");
			builder.append(this.stdDevs.get(i) + " ");
			lines.add(builder.toString());
		}
		
		//Average Strategy table values after each round (analyse 5)
		lines.add(" ");
		//for(int i = 0; i < numberOfTourneys; i++) {
		for(int j = 0; j < Strategy.getRows(this.memorySize); j++) {
			StringBuilder builder = new StringBuilder();
			for(int k = 0; k < Strategy.getColumns(this.memorySize); k++) {
				builder.append(this.AASALR[j][k]);
				builder.append(" ");
			}
			lines.add(builder.toString());
		}
		
		//Average total ages player pool (analyse6)
		lines.add(" ");
		for(int i = 0; i < numberOfTourneys; i++) {
			StringBuilder builder = new StringBuilder();
			builder.append(this.getATA().get(i));
			lines.add(builder.toString());
		}
		
		//Write stuff to file
		try {
			Charset utf8 = StandardCharsets.UTF_8;
			Files.write(Paths.get(fileName), lines, utf8);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Write collected data (not analysed data).
	 * @param fileName the name of the file to write to.
	 * @param numberOfTourneysPerSim the number of rounds played in each of the TourneyRecord objects.
	 */
	public void extensiveWrite(String fileName, int numberOfTourneysPerSim) {
		ArrayList<String> lines = new ArrayList<String>();
		
		//Number of CCs in each generation for every simulation
		lines.add("Number of CCs in each simulation, each line is a generation:");
		
		for(int j = 0; j < numberOfTourneysPerSim; j++) {
			StringBuilder builder = new StringBuilder();
			for(int i = 0; i < this.analysedTRecords.size(); i++) {
				builder.append(this.analysedTRecords.get(i).getNumberOfCCs().get(j));
				builder.append(" ");
			}
			lines.add(builder.toString());
		}
		lines.add(" ");
		
		//Number of CDCs in each generation for every simulation
		lines.add("Number of CDCs in each simulation, each line is a generation:");
		
		for(int j = 0; j < numberOfTourneysPerSim; j++) {
			StringBuilder builder = new StringBuilder();
			for(int i = 0; i < this.analysedTRecords.size(); i++) {
				int cdcTemp = 0;
				cdcTemp += this.analysedTRecords.get(i).getNumberOfCDs().get(j);
				cdcTemp += this.analysedTRecords.get(i).getNumberOfDCs().get(j);
				builder.append(cdcTemp);
				builder.append(" ");
			}
			lines.add(builder.toString());
		}
		lines.add(" ");
		
		//Number of DDs in each generation for every simulation
		lines.add("Number of DDs in each simulation, each line is a generation:");
		
		for(int j = 0; j < numberOfTourneysPerSim; j++) {
			StringBuilder builder = new StringBuilder();
			for(int i = 0; i < this.analysedTRecords.size(); i++) {
				builder.append(this.analysedTRecords.get(i).getNumberOfDDs().get(j));
				builder.append(" ");
			}
			lines.add(builder.toString());
		}
		lines.add(" ");
		
		//Number of times CC was dominant for every simulation
		lines.add("Number of times CC was dominant for every simulation:");
		StringBuilder builderDCC = new StringBuilder();
		for(int i = 0; i < this.analysedTRecords.size(); i++) {
			builderDCC.append(this.analysedTRecords.get(i).getDominantCC());
			builderDCC.append(" ");
		}
		lines.add(builderDCC.toString());
		lines.add(" ");
		
		//Number of times CDC was dominant in every simulation
		lines.add("Number of times CDC was dominant for every simulation:");
		StringBuilder builderDCDC = new StringBuilder();
		for(int i = 0; i < this.analysedTRecords.size(); i++) {
			builderDCDC.append(this.analysedTRecords.get(i).getDominantCDC());
			builderDCDC.append(" ");
		}
		lines.add(builderDCDC.toString());
		lines.add(" ");
		
		//Number of times DD was dominant in every simulation
		lines.add("Number of times DD was dominant for every simulation:");
		StringBuilder builderDDD = new StringBuilder();
		for(int i = 0; i < this.analysedTRecords.size(); i++) {
			builderDDD.append(this.analysedTRecords.get(i).getDominantDD());
			builderDDD.append(" ");
		}
		lines.add(builderDDD.toString());
		lines.add(" ");
		
		//Number of generations in which no outcome was prevalent
		lines.add("Number of generations in which no outcome was prevalent, for each simulation:");
		StringBuilder builderNOP = new StringBuilder();
		for(int i = 0; i < this.analysedTRecords.size(); i++) {
			builderNOP.append(this.analysedTRecords.get(i).getNotPrevalentCount());
			builderNOP.append(" ");
		}
		lines.add(builderNOP.toString());
		lines.add(" ");
		
		//Average generation number in which no outcome was prevalent, for each simulation
		lines.add("Average generation number in which no outcome was prevalent, for each simulation:");
		StringBuilder builderAGNOP = new StringBuilder();
		for(int i = 0; i < this.analysedTRecords.size(); i++) {
			builderAGNOP.append(this.analysedTRecords.get(i).getAverageRoundNoPrevalent());
			builderAGNOP.append(" ");
		}
		lines.add(builderAGNOP.toString());
		lines.add(" ");
		
		//First generation number in which there was only CC outcomes, for each simulation
		lines.add("First generation number in which there was only CC outcomes, for each simulation:");
		StringBuilder builderFHCC = new StringBuilder();
		for(int i = 0; i < this.analysedTRecords.size(); i++) {
			builderFHCC.append(this.analysedTRecords.get(i).getFirstHitCC());
			builderFHCC.append(" ");
		}
		lines.add(builderFHCC.toString());
		lines.add(" ");
		
		//First generation number in which there was only DD outcomes, for each simulation
		lines.add("First generation number in which there was only DD outcomes, for each simulation:");
		StringBuilder builderFHDD = new StringBuilder();
		for(int i = 0; i < this.analysedTRecords.size(); i++) {
			builderFHDD.append(this.analysedTRecords.get(i).getFirstHitDD());
			builderFHDD.append(" ");
		}
		lines.add(builderFHDD.toString());
		lines.add(" ");
		
		//Lowest payoff per generation, for each simulation
		lines.add("Lowest payoff per generation, for each simulation:");
		for(int j = 0; j < numberOfTourneysPerSim; j++) {
			StringBuilder builder = new StringBuilder();
			for(int i = 0; i < this.analysedTRecords.size(); i++) {
				builder.append(this.analysedTRecords.get(i).getLowestPayoffs().get(j));
				builder.append(" ");
			}
			lines.add(builder.toString());
		}
		lines.add(" ");
		
		//Highest payoff per generation, for each simulation
		lines.add("Highest payoff per generation, for each simulation:");
		for(int j = 0; j < numberOfTourneysPerSim; j++) {
			StringBuilder builder = new StringBuilder();
			for(int i = 0; i < this.analysedTRecords.size(); i++) {
				builder.append(this.analysedTRecords.get(i).getHighestPayoffs().get(j));
				builder.append(" ");
			}
			lines.add(builder.toString());
		}
		lines.add(" ");
		
		//Average payoff per generation, for each simulation
		lines.add("Average payoff per generation, for each simulation:");
		for(int j = 0; j < numberOfTourneysPerSim; j++) {
			StringBuilder builder = new StringBuilder();
			for(int i = 0; i < this.analysedTRecords.size(); i++) {
				builder.append(this.analysedTRecords.get(i).getAveragePayoffs().get(j));
				builder.append(" ");
			}
			lines.add(builder.toString());
		}
		lines.add(" ");
		
		//Standard deviation per generation is only found upon analysis
		
		//Average strategy last round for each simulation (beneath each other)
		lines.add("Average strategy last round for each simulation (beneath each other)");
		int rows = Strategy.getRows(this.memorySize);
		int columns = Strategy.getColumns(this.memorySize);
		
		for(int i = 0; i < this.analysedTRecords.size(); i++) {			
			for(int k = 0; k < rows; k++) {
				StringBuilder builder = new StringBuilder();
				for(int l = 0; l < columns; l++) {		
					builder.append(this.analysedTRecords.get(i).getAveStrategyAfterLRound()[k][l]);
					builder.append(" ");
				}
				lines.add(builder.toString());
			}
			lines.add(" ");
		}
		
		//The total pool age per generation, for each simulation
		lines.add("The total pool age per generation, for each simulation:");
		for(int j = 0; j < numberOfTourneysPerSim; j++) {
			StringBuilder builder = new StringBuilder();
			for(int i = 0; i < this.analysedTRecords.size(); i++) {
				builder.append(this.analysedTRecords.get(i).getTotalAges().get(j));
				builder.append(" ");
			}
			lines.add(builder.toString());
		}
		
		//Write stuff to file
		try {
			Charset utf8 = StandardCharsets.UTF_8;
			Files.write(Paths.get(fileName), lines, utf8);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//Getters
	
	/**
	 * 
	 * @return the TourneyRecord objects this class has been given to analyse.
	 */
	public ArrayList<TourneyRecord> getAnalysedTRecords() {
		return analysedTRecords;
	}

	/**
	 * 
	 * @return an ArrayList contained the average number of CC outcomes for each round for the
	 * analysed TourneyRecord objects.
	 */
	public ArrayList<Double> getAverageNumberOfCCs() {
		return averageNumberOfCCs;
	}

	/**
	 * 
	 * @return an ArrayList contained the average number of (CD + DC) outcomes for each round for the
	 * analysed TourneyRecord objects.
	 */
	public ArrayList<Double> getAverageNumberOfCDCs() {
		return averageNumberOfCDCs;
	}

	/**
	 * 
	 * @return an ArrayList contained the average number of DD outcomes for each round for the
	 * analysed TourneyRecord objects.
	 */
	public ArrayList<Double> getAverageNumberOfDDs() {
		return averageNumberOfDDs;
	}

	/**
	 * 
	 * @return the average number of times the CC outcome was dominant for the for the analysed
	 * TourneyRecord objects.
	 */
	public double getAverageDominantCC() {
		return averageDominantCC;
	}

	/**
	 * 
	 * @return the average number of times the (CD + DC) outcome was dominant for the for the analysed
	 * TourneyRecord objects.
	 */
	public double getAverageDominantCDC() {
		return averageDominantCDC;
	}

	/**
	 * 
	 * @return the average number of times the DD outcome was dominant for the for the analysed
	 * TourneyRecord objects.
	 */
	public double getAverageDominantDD() {
		return averageDominantDD;
	}

	/**
	 * 
	 * @return the average number of rounds in which none of the game outcomes was prevalent for the
	 * analysed TourneyRecord objects.
	 */
	public double getAverageAmountNoPrevalent() {
		return averageAmountNoPrevalent;
	}

	/**
	 * 
	 * @return the average round number for which there was no prevalent game outcome (in case there
	 * was at least 1) given the analysed TourneyRecord objects.
	 */
	public double getAARoundNoPrevalent() {
		return AARoundNoPrevalent;
	}

	/**
	 * 
	 * @return the average number of the first round in which there were only CC outcomes (in case
	 * this occurred) for the given TourneyRecord objects.
	 */
	public double getAverageFirstHitCC() {
		return averageFirstHitCC;
	}

	/**
	 * 
	 * @return the average number of the first round in which there were only DD outcomes (in case
	 * this occurred) for the given TourneyRecord objects.
	 */
	public double getAverageFirstHitDD() {
		return averageFirstHitDD;
	}

	/**
	 * 
	 * @return an ArrayList containing the average lowest payoff after each round for the given
	 * TourneyRecord objects.
	 */
	public ArrayList<Double> getAverageLowestPayoffs() {
		return averageLowestPayoffs;
	}

	/**
	 * 
	 * @return an ArrayList containing the average highest payoff after each round for the given
	 * TourneyRecord objects.
	 */
	public ArrayList<Double> getAverageHighestPayoffs() {
		return averageHighestPayoffs;
	}

	/**
	 * 
	 * @return an ArrayList containing the average average payoff after each round for the given
	 * TourneyRecord objects.
	 */
	public ArrayList<Double> getAverageAveragePayoffs() {
		return averageAveragePayoffs;
	}

	/**
	 * 
	 * @return the average strategies played by the player in the last round for the analysed TourneyRecord
	 * objects.
	 */
	public double[][] getAASALR() {
		return AASALR;
	}

	/**
	 * 
	 * @return an ArrayList containing the average total ages after each round for the analysed
	 * TourneyRecord objects.
	 */
	public ArrayList<Double> getATA() {
		return ATA;
	}

	/**
	 * 
	 * @return the memorySize this object associates with certain data structures in itself and the
	 * TourneyRecord it aims to analyse.
	 */
	public int getMemorySize() {
		return memorySize;
	}

	/**
	 * 
	 * @return the standard deviation to the average payoff found in each generation of a simulation.
	 */
	public ArrayList<Double> getStdDevs() {
		return stdDevs;
	}
}
