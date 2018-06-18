package simulation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

import pDilemma.Player;
import pDilemma.Tourney;
import strategies.Strategy;


public class Scenario {

	/**
	 * Run a repeated RR-tournament experiment.
	 * @param memorySize the memory size of the strategies used by the players
	 * @param lostRate the frequency of lost information (between 0 and 1)
	 * @param wrongRate the frequency of wrong information (between 0 and 1)
	 * @param delayedRate the frequency of delayed information (between 0 and 1)
	 * @param anaFileName the file to write the analysed data from the experiment to
	 * (though at this time, the ext file is analysed afterwards instead to create graphs and statistical tests for the paper)
	 * @param extFileName the file to write the data recorded on the experiment to
	 */
	public static void scenario1(int memorySize, double lostRate, double wrongRate, double delayedRate,
			String anaFileName, String extFileName) {
		
		int numberOfSimulations = 30;
		int numberOfPlayers = 30;
//		int memorySize = 1;
		int numberOfRoundsPerGame = 150;
		//With 30 players this means 65250 games
		int numberOfTourneys = 600;
		//Player percentage replace: .50
		//Mutation frequency: according to algorithm
		//Payoff matrix: 5,4,1,0
//		double lostRate = 0.05;
//		double wrongRate = 0.05;
//		double delayedRate = 0.0;
		
//		String anaFileName = "exp/anaExp4V30";
//		String extFileName = "exp/extExp4V30";
		
		ScenarioRecord scenarioRecord = new ScenarioRecord(memorySize);
		for (int i = 0; i < numberOfSimulations; i++) {
			System.out.println("Starting simulation: number " + (i + 1));
			
			//Create random players
			ArrayList<Player> playerList = Scenario.createRandomPlayers(numberOfPlayers, memorySize);
			
			//Record initial pool in some file
			//Scenario.writeInitialPool(playerList, memorySize, initialPoolFileName);
			
			//Play tournament repeatedly
			Tourney tourney = new Tourney(playerList, memorySize);
			tourney.repeatedlyPlayTourney(numberOfRoundsPerGame, numberOfTourneys, lostRate, wrongRate, delayedRate);
			tourney.getRecord().analyzeGivenRecords(numberOfPlayers, numberOfRoundsPerGame);
			scenarioRecord.addRecord(tourney.getRecord());
			
			//tourney.getRecord().writeRecordedInfo(tourneyRecordWriteFileName);
		}
		scenarioRecord.extensiveWrite(extFileName, numberOfTourneys);
		scenarioRecord.analyzeTRecords(numberOfTourneys);
		scenarioRecord.writeData(anaFileName, numberOfTourneys);
	}
	
	/**
	 * Creates a pool of players with random strategies
	 * @param numberOfPlayers the number of players to add to the pool
	 * @param memorySize the memory size of the strategies that will be used by the players
	 * @return an ArrayList contains the players
	 */
	public static ArrayList<Player> createRandomPlayers(int numberOfPlayers, int memorySize) {
		ArrayList<Player> result = new ArrayList<Player>();
		Random random = new Random();
		
		//Tables sizes
		int rows = Strategy.getRows(memorySize);
		int columns = Strategy.getColumns(memorySize);
		
		for(int i = 0; i < numberOfPlayers; i++) {
			int[][] randomStrategy = new int[rows][columns]; 
			for(int j = 0; j < rows; j++) {
				for(int k = 0; k < columns; k++) {
					randomStrategy[j][k] = random.nextInt(2) + 1;
				}
			}
			result.add(new Player(Integer.toString(i), new Strategy(memorySize, randomStrategy)));
		}
		return result;
	}
	
	/**
	 * Write strategies of a player pool to a file
	 * @param playerList the player pool
	 * @param memorySize the memory size of the strategies of the players
	 * @param initialPoolFileName the file to write to
	 */
	public static void writeInitialPool(ArrayList<Player> playerList, int memorySize, String initialPoolFileName) {
		//Record the strategies of the initial pool in an ArrayList<String>
		ArrayList<String> lines = new ArrayList<String>();
		for(int i = 0; i < playerList.size(); i++) {
			StringBuilder builder = new StringBuilder();
			for(int j = 0; j < Strategy.getRows(memorySize); j++) {
				for(int k = 0; k < Strategy.getColumns(memorySize); k++) {
					builder.append(playerList.get(i).getStrat().getStrategy()[j][k]);
					builder.append(" ");
				}
			}
			lines.add(builder.toString());
		}
		
		//Try to write the strategies to a file
		try {
			Charset utf8 = StandardCharsets.UTF_8;
			Files.write(Paths.get(initialPoolFileName), lines, utf8);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Calculate the error lines (confidence intervals) for an ext file (set number of simulations and numberOfTourneys)
	 * @param memorySize determines the size of the strategy tables in the ext file
	 * @param fileToRead the file to read
	 * @param fileToWrite the file to write the results to
	 */
	public static void ma1(int memorySize, String fileToRead, String fileToWrite) {
		int numberOfSimulations = 30;
		int numberOfTourneys = 600;
		Scenario.ma1Deep(numberOfSimulations, numberOfTourneys, memorySize, fileToRead, fileToWrite);
	}
	
	/**
	 * Don't call directly unless testing. Value from t-table based on degrees of freedom is hardcoded, which means
	 * this method doesn't calculate confidence intervals correctly for other numberOfSimulations.
	 * @param numberOfSimulations the data of the data sets
	 * @param numberOfToureys the number of generations
	 * @param memorySize determines the size of the strategy tables in the ext-file
	 * @param fileToRead the file to read
	 * @param fileToWrite the file to write the results to
	 */
	public static void ma1Deep(int numberOfSimulations, int numberOfTourneys, int memorySize, String fileToRead, String fileToWrite) {
		
		//CC, CDC, DD
		ArrayList<Double> confCCL = new ArrayList<Double>();
		ArrayList<Double> confCCMean = new ArrayList<Double>();
		ArrayList<Double> confCCU = new ArrayList<Double>();
		
		ArrayList<Double> confCDCL = new ArrayList<Double>();
		ArrayList<Double> confCDCMean = new ArrayList<Double>();
		ArrayList<Double> confCDCU = new ArrayList<Double>();
		
		ArrayList<Double> confDDL = new ArrayList<Double>();
		ArrayList<Double> confDDMean = new ArrayList<Double>();
		ArrayList<Double> confDDU = new ArrayList<Double>();
		
		//Lowest, Highest and Average payoff
		ArrayList<Double> confLowL = new ArrayList<Double>();
		ArrayList<Double> confLowMean = new ArrayList<Double>();
		ArrayList<Double> confLowU = new ArrayList<Double>();
		
		ArrayList<Double> confHighL = new ArrayList<Double>();
		ArrayList<Double> confHighMean = new ArrayList<Double>();
		ArrayList<Double> confHighU = new ArrayList<Double>();
		
		ArrayList<Double> confAveL = new ArrayList<Double>();
		ArrayList<Double> confAveMean = new ArrayList<Double>();
		ArrayList<Double> confAveU = new ArrayList<Double>();
		
		//Player ages
		ArrayList<Double> confAgeL = new ArrayList<Double>();
		ArrayList<Double> confAgeMean = new ArrayList<Double>();
		ArrayList<Double> confAgeU = new ArrayList<Double>();
		
		//Read stuff (based on ScenarioRecordTest)
		try(BufferedReader buff = new BufferedReader(new FileReader(fileToRead))) {
			
			//95 confidence interval CC, CDC and DD lines
			createConf(numberOfTourneys, numberOfSimulations, buff, true, confCCL, confCCMean, confCCU);
			createConf(numberOfTourneys, numberOfSimulations, buff, true, confCDCL, confCDCMean, confCDCU);
			createConf(numberOfTourneys, numberOfSimulations, buff, true, confDDL, confDDMean, confDDU);
			
			//Skip some stuff
			for(int i = 0; i < 21; i++) {
				buff.readLine();
			}
			
			//95 confidence interval lowest, highest and average payoffs
			createConf(numberOfTourneys, numberOfSimulations, buff, true, confLowL, confLowMean, confLowU);
			createConf(numberOfTourneys, numberOfSimulations, buff, true, confHighL, confHighMean, confHighU);
			createConf(numberOfTourneys, numberOfSimulations, buff, false, confAveL, confAveMean, confAveU);
			
			//Skip some lines
			for(int i = 0; i < (1 + ((Strategy.getRows(memorySize) + 1) * numberOfSimulations)); i++) {
				buff.readLine();
			}
			
			//95 confidence interval average player ages
			createConf(numberOfTourneys, numberOfSimulations, buff, true, confAgeL, confAgeMean, confAgeU);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Add all information to the lines which will be written to a file
		ArrayList<String> lines = new ArrayList<String>();
		
		//CC, CDC, DD
		lines.add("Confidence intervals for CC, each generation:");
		addConf(lines, numberOfTourneys, confCCL, confCCMean, confCCU);
		
		lines.add("Confidence intervals for CDC, each generation:");
		addConf(lines, numberOfTourneys, confCDCL, confCDCMean, confCDCU);
		
		lines.add("Confidence intervals for DD, each generation:");
		addConf(lines, numberOfTourneys, confDDL, confDDMean, confDDU);
		
		//Lowest, Highest, Average
		lines.add("Confidence intervals for Lowest, each generation:");
		addConf(lines, numberOfTourneys, confLowL, confLowMean, confLowU);
		
		lines.add("Confidence intervals for Highest, each generation:");
		addConf(lines, numberOfTourneys, confHighL, confHighMean, confHighU);
		
		lines.add("Confidence intervals for Average, each generation:");
		addConf(lines, numberOfTourneys, confAveL, confAveMean, confAveU);
		
		lines.add("Confidence intervals for Age, each generation:");
		addConf(lines, numberOfTourneys, confAgeL, confAgeMean, confAgeU);
		
		//Also per line add the other information that is necessary to perform the relevant statistical tests
		
		//Write stuff
		try {
			Charset utf8 = StandardCharsets.UTF_8;
			Files.write(Paths.get(fileToWrite), lines, utf8);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Calculate a confidence interval for part of an ext-file.
	 * @param numberOfTourneys the number of generations
	 * @param numberOfSimulations the number of simulations
	 * @param buff the BufferedReader containing the position in the file next to read
	 * @param confLow the ArrayList to store the lower bounds in
	 * @param confMean
	 * @param confHigh the ArrayList to store the upper bounds in
	 * @throws IOException for reading the file, caught by the public method calling this one.
	 */
	private static void createConf(int numberOfTourneys, int numberOfSimulations,
			BufferedReader buff, boolean readInt,
			ArrayList<Double> confLow, ArrayList<Double> confMean, ArrayList<Double> confHigh) throws IOException {
		
		System.out.println("Creating confidence intervals for: " + buff.readLine());
		
		for(int i = 0; i < numberOfTourneys; i++) {
			String[] numbers = buff.readLine().split(" ");
			//assertTrue(numbers.length == numberOfSimulations);
			
			double sum = 0.0;
			for(int j = 0; j < numberOfSimulations; j++) {
				if (readInt) {
					sum += (double) Integer.parseInt(numbers[j]);
				} else {
					sum += Double.parseDouble(numbers[j]);
				}
			}
			double mean = (double) sum / (double) numberOfSimulations;
			
			double sumOfDistances = 0.0;
			for(int j = 0; j < numberOfSimulations; j++) {
				if (readInt) {
					sumOfDistances += Math.pow(((double) Integer.parseInt(numbers[j]) - mean), 2.0);
				} else {
					sumOfDistances += Math.pow((Double.parseDouble(numbers[j]) - mean), 2.0);
				}
			}
			
			double sampleStandardDeviation = Math.sqrt(sumOfDistances / (double) (numberOfSimulations - 1));
			confLow.add(mean - (2.045 * (sampleStandardDeviation / Math.sqrt(numberOfSimulations))));
			confMean.add(mean);
			confHigh.add(mean + (2.045 * (sampleStandardDeviation / Math.sqrt(numberOfSimulations))));
		}
		buff.readLine();
	}
	
	/**
	 * Write the data on a single to confidence interval to the next entry in an ArrayList.
	 * @param lines the ArrayList to write to
	 * @param numberOfTourneys the number of generations in the ext file
	 * @param confLow the lower bound of the confidence interval
	 * @param mean the mean
	 * @param confHigh the upper bound of the confidence interval
	 */
	private static void addConf(ArrayList<String> lines, int numberOfTourneys,
			ArrayList<Double> confLow, ArrayList<Double> mean, ArrayList<Double> confHigh) {
		
		for(int i = 0; i < numberOfTourneys; i++) {
			StringBuilder builder = new StringBuilder();
			builder.append(confLow.get(i) + " " + mean.get(i) + " " + confHigh.get(i));
			lines.add(builder.toString());
		}
		lines.add(" ");
	}
	
	/**
	 * Calculate the metric for each simulation as recorded in an ext file. Set number of simulations and generations.
	 * @param fileToRead the ext file to read
	 * @param fileToWrite the file to stored the result
	 */
	public static void ma3(String fileToRead, String fileToWrite) {
		int numberOfSimulations = 30;
		int numberOfTourneys = 600;
		Scenario.ma3Deep(numberOfSimulations, numberOfTourneys, fileToRead, fileToWrite);
	}
	
	/**
	 * Implementation of ma3, allows you to specify the number of simulations and number of generations.
	 * @param numberOfSimulations the number of simulations
	 * @param numberOfTourneys the number of generations each simulation goes on for
	 * @param fileToRead the ext file to read
	 * @param fileToWrite the file to write the result to
	 */
	public static void ma3Deep(int numberOfSimulations, int numberOfTourneys, String fileToRead, String fileToWrite) {
		
		//Create data structures
		ArrayList<ArrayList<Integer>> listsForSims = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Double>> listsForMovAves = new ArrayList<ArrayList<Double>>();
		ArrayList<ArrayList<Double>> listsForFirstDiris = new ArrayList<ArrayList<Double>>();
		ArrayList<Integer> metricAnswers = new ArrayList<Integer>();
		
		for(int i = 0; i < numberOfSimulations; i++) {
			listsForSims.add(new ArrayList<Integer>());
			listsForMovAves.add(new ArrayList<Double>());
			listsForFirstDiris.add(new ArrayList<Double>());
		}
		
		//Read all necessary data
		try(BufferedReader buff = new BufferedReader(new FileReader(fileToRead))) {
			buff.readLine();
			
			for(int i = 0; i < numberOfTourneys; i++) {
				String[] numbers = buff.readLine().split(" ");
				for(int j = 0; j < numberOfSimulations; j++) {
					listsForSims.get(j).add(Integer.parseInt(numbers[j]));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Calculate moving average (not first 9 and last 9)
		int meanLength = 19;
		
		for(int i  = ((meanLength - 1) / 2); i <= ((numberOfTourneys - 1) - ((meanLength - 1) / 2)); i++) {
			for(int j = 0; j < numberOfSimulations; j++) {
				double sum = 0.0;
				for(int k = i - ((meanLength - 1) / 2); k <= i + ((meanLength - 1) / 2); k++) {
					sum += listsForSims.get(j).get(k);
				}
				double result = sum / (double) meanLength;
				listsForMovAves.get(j).add(result);
			}
		}
		
		//Calculate first derivative (not first and last movAve)
		int lengthMovAve = (numberOfTourneys - meanLength) + 1;
		for(int i = 1; i < (lengthMovAve - 1); i++) {
			for(int j = 0; j < numberOfSimulations; j++) {
				double result = listsForMovAves.get(j).get(i + 1) - listsForMovAves.get(j).get(i - 1);
				listsForFirstDiris.get(j).add(result);
			}
		}
		
		//For each simulation
		//Calculate the number of the first round N for which firstDiri(N) is positive and firstDiri(N+1) is negative
		int lengthFirstDiri = lengthMovAve - 2;
		for(int j = 0; j < numberOfSimulations; j++) {
			int firstOcc = -1;
			int happen = 0;
			
			boolean positiveOrigin = false;
			
			//positiveOrigin for when first dir passes exactly zero going from positive to negative
			for(int i = 0; i < (lengthFirstDiri - 1); i ++) {
				//boolean option1 = listsForFirstDiris.get(j).get(i) > 0.0 && listsForFirstDiris.get(j).get(i+1) < 0.0;
				
				if (listsForFirstDiris.get(j).get(i) > 0.0) {
					positiveOrigin = true;
				}
				if (listsForFirstDiris.get(j).get(i) < 0.0) {
					positiveOrigin = false;
				}
				boolean option1 = positiveOrigin && listsForFirstDiris.get(j).get(i+1) < 0.0;
				
				if (happen == 0 && option1) {
					firstOcc = i + ((meanLength - 1) / 2) + 2; //1 skip start at zero, 9 skip mov ave, 1 skip first diri
					happen++;
				}
			}
			metricAnswers.add(firstOcc);
		}
		
		//Write data: visual
		ArrayList<String> lines = new ArrayList<String>();
		lines.add("Below here are the results for the metric in each simulation:");
		
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < numberOfSimulations; i++) {
			builder.append(metricAnswers.get(i)+ " ");
		}
		lines.add(builder.toString());
		
		//Write stuff
		try {
			Charset utf8 = StandardCharsets.UTF_8;
			Files.write(Paths.get(fileToWrite), lines, utf8);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Choose an attribute from an ext file. This method will read the relevant data from two ext files and this method will
	 * perform one-tail two-sample t-test for unequal variance, for each generation
	 * @param testAttribute the attribute for which statistical significance is to be tested
	 * @param numberOfSimulations the size of each sample
	 * @param numberOfTourneys the amount of statistical tests (one for each generation)
	 * @param memorySize neccesary to correctly read data from file
	 * @param fileToReadLower the data from the first experiment (alternative hypothesis expects this to be lower)
	 * @param fileToReadHigher the data from the second experiment (alternative hypothesis expects this to be higher)
	 * @param fileToWrite the file to write the results from the statistical tests to
	 * @param storeTStat arrayList provided will contain the calculated t stat for each statistical test (testing purposes)
	 * @param storeTValue arrayList provided will contain the calculated t value from table for each statistical test (testing purposes)
	 */
	public static void statisticalTest1(String testAttribute, int numberOfSimulations, int numberOfTourneys, int memorySize,
			String fileToReadLower, String fileToReadHigher, String fileToWrite,
			ArrayList<Double> storeTStat, ArrayList<Double> storeTValue) {
		
		//Create necessary data structures
		ArrayList<ArrayList<Double>> listsForSimsLower = new ArrayList<ArrayList<Double>>();
		ArrayList<ArrayList<Double>> listsForSimsHigher = new ArrayList<ArrayList<Double>>();
		
		for(int i = 0; i < numberOfSimulations; i++) {
			listsForSimsLower.add(new ArrayList<Double>());
			listsForSimsHigher.add(new ArrayList<Double>());
		}
		
		//Determine which information to test
		switch(testAttribute) {
		case "CC":
			ExtReadHelper.readCCFromExt(numberOfSimulations, numberOfTourneys, fileToReadLower, listsForSimsLower);
			ExtReadHelper.readCCFromExt(numberOfSimulations, numberOfTourneys, fileToReadHigher, listsForSimsHigher);
			break;
		case "Lowest":
			ExtReadHelper.readLowestFromExt(numberOfSimulations, numberOfTourneys, fileToReadLower, listsForSimsLower);
			ExtReadHelper.readLowestFromExt(numberOfSimulations, numberOfTourneys, fileToReadHigher, listsForSimsHigher);
			break;
		case "Highest":
			ExtReadHelper.readHighestFromExt(numberOfSimulations, numberOfTourneys, fileToReadLower, listsForSimsLower);
			ExtReadHelper.readHighestFromExt(numberOfSimulations, numberOfTourneys, fileToReadHigher, listsForSimsHigher);
			break;
		case "Average":
			ExtReadHelper.readAverageFromExt(numberOfSimulations, numberOfTourneys, fileToReadLower, listsForSimsLower);
			ExtReadHelper.readAverageFromExt(numberOfSimulations, numberOfTourneys, fileToReadHigher, listsForSimsHigher);
			break;
		case "Age":
			ExtReadHelper.readAgeFromExt(numberOfSimulations, numberOfTourneys, memorySize, fileToReadLower, listsForSimsLower);
			ExtReadHelper.readAgeFromExt(numberOfSimulations, numberOfTourneys, memorySize, fileToReadHigher, listsForSimsHigher);
			break;
		default:
			System.out.println("You are asking to test for something that doesn't exist");
			throw new java.lang.Error();
		}
		
		//Perform one-tail two-sample t-test for unequal variance, for each generation
		ArrayList<Integer> results = statTestEachGen(numberOfSimulations, numberOfTourneys,
				listsForSimsLower, listsForSimsHigher, storeTStat, storeTValue);
		
		//Write to ArrayList
		ArrayList<String> lines = new ArrayList<String>();
		
		StringBuilder builder = new StringBuilder();
		builder.append("1 " + results.get(0));
		lines.add(builder.toString());
		
		int last = results.get(0);
		for(int i = 1; i < numberOfTourneys; i++) {
			if(last != results.get(i)) {
				
				StringBuilder builder2 = new StringBuilder();
				builder2.append(i + 1);
				builder2.append(" ");
				builder2.append(results.get(i));
				lines.add(builder2.toString());
				
				last = results.get(i);
			}
		}
		
		//Extra
		int total = 0;
		for(int i = 0; i < numberOfTourneys; i++) {
			if (results.get(i) == 1) {
				total++;
			}
		}
		
		StringBuilder builder3 = new StringBuilder();
		builder3.append("Total yes: " + total);
		lines.add(builder3.toString());
		
		//Write result from ArrayList to File
		try {
			Charset utf8 = StandardCharsets.UTF_8;
			Files.write(Paths.get(fileToWrite), lines, utf8);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Runs a statistical test for each generation of data, looking if the first dataSet is significantly lower as the second dataSet.
	 * @param numberOfSimulations the size of each data set
	 * @param numberOfTourneys the number of generations, each generation means one test for statistical significance
	 * @param dataExp1 the data for experiment 1 (alternative hypothesis expects this to be lower)
	 * @param dataExp2 the data for experiment 2 (alternative hypothesis expects this to be higher)
	 * @param storeTStat arrayList provided will contain the calculated t stat for each statistical test (testing purposes)
	 * @param storeTValue arrayList provided will contain the calculated t value from table for each statistical test (testing purposes)
	 * @return an ArrayList containing zeros and ones representing whether each generation there was no significance or significance, respectively
	 */
	private static ArrayList<Integer> statTestEachGen(int numberOfSimulations, int numberOfTourneys,
			ArrayList<ArrayList<Double>> dataExp1, ArrayList<ArrayList<Double>> dataExp2,
			ArrayList<Double> storeTStat, ArrayList<Double> storeTValue) {
		
		ArrayList<Integer> result = new ArrayList<Integer>();
		
		for(int i = 0; i < numberOfTourneys; i++) {
			
			double sampleVarianceExp1;
			double sampleVarianceExp2;
			
			//Calculate sample mean for both experiments
			double meanExp1 = 0.0;
			double meanExp2 = 0.0;
			
			for(int j = 0; j < numberOfSimulations; j++) {
				meanExp1 += dataExp1.get(j).get(i);
				meanExp2 += dataExp2.get(j).get(i);
			}
			meanExp1 = meanExp1 / (double) numberOfSimulations;
			meanExp2 = meanExp2 / (double) numberOfSimulations;
			
			//System.out.println("What are the calculated means? " + meanExp1 + " " + meanExp2);
			
			//Calculate sample variance for both experiments
			double sumOfSDistancesExp1 = 0.0;
			double sumOfSDistancesExp2 = 0.0;
			for(int j = 0; j < numberOfSimulations; j++) {
				sumOfSDistancesExp1 += Math.pow((dataExp1.get(j).get(i) - meanExp1), 2.0);
				sumOfSDistancesExp2 += Math.pow((dataExp2.get(j).get(i) - meanExp2), 2.0);
			}

			sampleVarianceExp1 = sumOfSDistancesExp1 / (double) (numberOfSimulations - 1);
			sampleVarianceExp2 = sumOfSDistancesExp2 / (double) (numberOfSimulations - 1);
			
			//System.out.println("What are the sample variances? " + sampleVarianceExp1 + " " + sampleVarianceExp2);
			
			//Calculate estimated degrees of freedom
			double part1 = Math.pow(((sampleVarianceExp1 / (double) numberOfSimulations) + (sampleVarianceExp2 / (double) numberOfSimulations)), 2.0);
			double part2 = Math.pow((sampleVarianceExp1 / numberOfSimulations), 2.0) / (double) (numberOfSimulations - 1);
			double part3 = Math.pow((sampleVarianceExp2 / numberOfSimulations), 2.0) / (double) (numberOfSimulations - 1);
			double estimatedDF = part1 / (part2 + part3);
			
			//System.out.println("What is the estimated df? " + estimatedDF);
			
			//Look up in table (column argument passed implies the selected level of confidence)
			double tValue = lookupT(1, estimatedDF);
			
			//T-statistic formula
			double tStatPart1 = meanExp1 - meanExp2;
			double tStatPart2 = Math.sqrt((sampleVarianceExp1 / numberOfSimulations) + (sampleVarianceExp2 / numberOfSimulations));
			double tStat = tStatPart1 / tStatPart2;
			
			//Write to ArrayList (implies the selected alternative hypothesis)
			storeTStat.add(tStat);
			storeTValue.add(tValue);
			
			if(tStat < tValue) {
				result.add(1);
			} else {
				result.add(0);
			}
		}
		return result;
	}
	
	/**
	 * Lookup a t-value from a file, given the column (chosen with a significance in mind) and the degrees of freedom
	 * @param column the column to get the t-value from
	 * @param estimatedDF determines the row to get the t-value from
	 * @return the t-value
	 */
	private static double lookupT(int column, double estimatedDF) {
		
		double prevTValue = 0.0; //Should never stay like that
		try(BufferedReader buff = new BufferedReader(new FileReader("data/tTable"))) {
			
			boolean found = false;
			int prevValue = 0;
			
			while(!found) {
				String[] numbers = buff.readLine().split(" ");
				
				//End of table already reached
				if (numbers[0].equals("inf")) {
					found = true;
					prevTValue = Double.parseDouble(numbers[column]);
				} else {
					
					//If new value is closer update prevTValue, otherwise we have found the best t value
					int distancePrevious = Math.abs((int) Math.floor(estimatedDF) - prevValue);
					int distanceThis = Math.abs((int) Math.floor(estimatedDF) - Integer.parseInt(numbers[0]));
					if (distanceThis <= distancePrevious || prevValue == 0) { //Added equals
						
						//Read the one that corresponds to one-tail 0.05
						prevValue = Integer.parseInt(numbers[0]);
						prevTValue = Double.parseDouble(numbers[column]);
					} else {
						found = true;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return prevTValue;
	}

	/**
	 * Take an ana file and calculate the first round in which the average amount of CC games is equal or higher
	 * as this number averaged over the last 300 generations.
	 * @param fileToRead the file to read.
	 * @param fileToWrite the file to write the result to.
	 * @deprecated ma3 is a superior metric
	 */
	@Deprecated
	public static void ma2(String fileToRead, String fileToWrite) {
		
		//Parameters
		int numberOfTourneys = 600;
		
		//(Partial) Results
		double averageSH = 0.0;
		double result = 601;
		
		//Read stuff (based on ma1)
		try(BufferedReader buff = new BufferedReader(new FileReader(fileToRead))) {
			
			double sum = 0;
			for(int i = 0; i < numberOfTourneys; i++) {
				String[] numbers = buff.readLine().split(" ");
				if (i >= (numberOfTourneys / 2)) {
					sum += Double.parseDouble(numbers[0]);
				}
			}
			averageSH = sum / (double) (numberOfTourneys / 2);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Read the same stuff again
		try(BufferedReader buff = new BufferedReader(new FileReader(fileToRead))) {
			
			int encounter = 0;
			for(int i = 0; i < numberOfTourneys; i++) {
				String[] numbers = buff.readLine().split(" ");
				if (Double.parseDouble(numbers[0]) >= averageSH && encounter == 0) {
					result = i + 1;
					encounter++;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Add all information to the lines which will be written
		ArrayList<String> lines = new ArrayList<String>();
		StringBuilder builder = new StringBuilder();
		builder.append(result);
		lines.add(builder.toString());
		
		//Write stuff
		try {
			Charset utf8 = StandardCharsets.UTF_8;
			Files.write(Paths.get(fileToWrite), lines, utf8);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
