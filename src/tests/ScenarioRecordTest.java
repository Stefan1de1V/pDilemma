package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import pDilemma.Player;
import pDilemma.Tourney;
import pDilemma.TourneyRecord;
import simulation.Scenario;
import simulation.ScenarioRecord;
import strategies.Strategy;


public class ScenarioRecordTest {

	/**
	 * Test the constructor and adding a record.
	 */
	@Test
	public void testConstructor() {
		int testMemorySize = 2;
		ScenarioRecord testRecord = new ScenarioRecord(testMemorySize);
		
		assertTrue(testRecord.getMemorySize() == 2);
		assertTrue(testRecord.getAnalysedTRecords().size() == 0);
		
		testRecord.addRecord(new TourneyRecord(testMemorySize));
		assertTrue(testRecord.getAnalysedTRecords().size() == 1);
	}
	
	/**
	 * Test averaging the number of CC, CDC (CD + DC) and DD over a number of simulations.
	 */
	@Test
	public void testAnalyse1T1() {
		int testMemorySize = 2;
		int testNumberOfTourneysPerSim = 5;
		ScenarioRecord testRecord = new ScenarioRecord(testMemorySize);
		
		//Create rec1 with necessary data
		TourneyRecord rec1 = new TourneyRecord(testMemorySize);
		ArrayList<Integer> rec1CC = new ArrayList<Integer>();
		for(int i = 1; i <= 5; i++) {
			rec1CC.add(0);
		}
		rec1.setNumberOfCCs(rec1CC);
		ArrayList<Integer> rec1CD = new ArrayList<Integer>();
		for(int i = 1; i <= 5; i++) {
			rec1CD.add(i * 2);
		}
		rec1.setNumberOfCDs(rec1CD);
		ArrayList<Integer> rec1DC = new ArrayList<Integer>();
		for(int i = 1; i <= 5; i++) {
			rec1DC.add(i * 3);
		}
		rec1.setNumberOfDCs(rec1DC);
		ArrayList<Integer> rec1DD = new ArrayList<Integer>();
		for(int i = 1; i <= 5; i++) {
			rec1DD.add(i * 4);
		}
		rec1.setNumberOfDDs(rec1DD);
		testRecord.addRecord(rec1);
		
		//Create rec2 with necessary data
		TourneyRecord rec2 = new TourneyRecord(testMemorySize);
		ArrayList<Integer> rec2CC = new ArrayList<Integer>();
		for(int i = 1; i <= 5; i++) {
			rec2CC.add(i * 5);
		}
		rec2.setNumberOfCCs(rec2CC);
		ArrayList<Integer> rec2CD = new ArrayList<Integer>();
		for(int i = 1; i <= 5; i++) {
			rec2CD.add(i * 6);
		}
		rec2.setNumberOfCDs(rec2CD);
		ArrayList<Integer> rec2DC = new ArrayList<Integer>();
		for(int i = 1; i <= 5; i++) {
			rec2DC.add(i * 7);
		}
		rec2.setNumberOfDCs(rec2DC);
		ArrayList<Integer> rec2DD = new ArrayList<Integer>();
		for(int i = 1; i <= 5; i++) {
			rec2DD.add(i * 8);
		}
		rec2.setNumberOfDDs(rec2DD);
		testRecord.addRecord(rec2);
		
		//Create rec3 with necessary data
		TourneyRecord rec3 = new TourneyRecord(testMemorySize);
		ArrayList<Integer> rec3CC = new ArrayList<Integer>();
		for(int i = 1; i <= 5; i++) {
			rec3CC.add(i * 9);
		}
		rec3.setNumberOfCCs(rec3CC);
		ArrayList<Integer> rec3CD = new ArrayList<Integer>();
		for(int i = 1; i <= 5; i++) {
			rec3CD.add(i * 10);
		}
		rec3.setNumberOfCDs(rec3CD);
		ArrayList<Integer> rec3DC = new ArrayList<Integer>();
		for(int i = 1; i <= 5; i++) {
			rec3DC.add(i * 11);
		}
		rec3.setNumberOfDCs(rec3DC);
		ArrayList<Integer> rec3DD = new ArrayList<Integer>();
		for(int i = 1; i <= 5; i++) {
			rec3DD.add(i * 12);
		}
		rec3.setNumberOfDDs(rec3DD);
		testRecord.addRecord(rec3);
		
		testRecord.analyse1(testNumberOfTourneysPerSim);
		
		//CC
		assertTrue(testRecord.getAverageNumberOfCCs().size() == testNumberOfTourneysPerSim);
		assertEquals((1.0 * (0+5+9)) / 3.0, testRecord.getAverageNumberOfCCs().get(0), 0.01);
		assertEquals((2.0 * (0+5+9)) / 3.0, testRecord.getAverageNumberOfCCs().get(1), 0.01);
		assertEquals((3.0 * (0+5+9)) / 3.0, testRecord.getAverageNumberOfCCs().get(2), 0.01);
		assertEquals((4.0 * (0+5+9)) / 3.0, testRecord.getAverageNumberOfCCs().get(3), 0.01);
		assertEquals((5.0 * (0+5+9)) / 3.0, testRecord.getAverageNumberOfCCs().get(4), 0.01);
		
		//CD + DC
		assertTrue(testRecord.getAverageNumberOfCDCs().size() == testNumberOfTourneysPerSim);
		assertEquals(((1.0 * (2+3+6+7+10+11)) / 3.0), testRecord.getAverageNumberOfCDCs().get(0), 0.01);
		assertEquals(((2.0 * (2+3+6+7+10+11)) / 3.0), testRecord.getAverageNumberOfCDCs().get(1), 0.01);
		assertEquals(((3.0 * (2+3+6+7+10+11)) / 3.0), testRecord.getAverageNumberOfCDCs().get(2), 0.01);
		assertEquals(((4.0 * (2+3+6+7+10+11)) / 3.0), testRecord.getAverageNumberOfCDCs().get(3), 0.01);
		assertEquals(((5.0 * (2+3+6+7+10+11)) / 3.0), testRecord.getAverageNumberOfCDCs().get(4), 0.01);
		
		//DD
		assertTrue(testRecord.getAverageNumberOfDDs().size() == testNumberOfTourneysPerSim);
		assertEquals(((1.0 * (4+8+12)) / 3.0), testRecord.getAverageNumberOfDDs().get(0), 0.01);
		assertEquals(((2.0 * (4+8+12)) / 3.0), testRecord.getAverageNumberOfDDs().get(1), 0.01);
		assertEquals(((3.0 * (4+8+12)) / 3.0), testRecord.getAverageNumberOfDDs().get(2), 0.01);
		assertEquals(((4.0 * (4+8+12)) / 3.0), testRecord.getAverageNumberOfDDs().get(3), 0.01);
		assertEquals(((5.0 * (4+8+12)) / 3.0), testRecord.getAverageNumberOfDDs().get(4), 0.01);
	}

	/**
	 * Test averaging DominantCC, DominantCDC, DominantDD, AmountNoPrevalent, ARoundNoPrevalent.
	 */
	@Test
	public void testAnalyse2T1() {
		int testMemorySize = 2;
		ScenarioRecord testRecord = new ScenarioRecord(testMemorySize);
		
		//Create rec1 with necessary data
		TourneyRecord rec1 = new TourneyRecord(testMemorySize);
		rec1.setDominantCC(25);
		rec1.setDominantCDC(43);
		rec1.setDominantDD(14);
		rec1.setNotPrevalentCount(4);
		rec1.setAverageRoundNoPrevalent(13.32);
		testRecord.addRecord(rec1);
		
		//Create rec2 with necessary data
		TourneyRecord rec2 = new TourneyRecord(testMemorySize);
		rec2.setDominantCC(14);
		rec2.setDominantCDC(3);
		rec2.setDominantDD(30);
		rec2.setNotPrevalentCount(2);
		rec2.setAverageRoundNoPrevalent(90.5);
		testRecord.addRecord(rec2);
		
		//Create rec3 with necessary data
		TourneyRecord rec3 = new TourneyRecord(testMemorySize);
		rec3.setDominantCC(5);
		rec3.setDominantCDC(7);
		rec3.setDominantDD(4);
		rec3.setNotPrevalentCount(0);
		rec3.setAverageRoundNoPrevalent(0.0);
		testRecord.addRecord(rec3);
		
		//Test stuff
		testRecord.analyse2();
		assertEquals(((25.0 + 14.0 + 5.0) / 3.0), testRecord.getAverageDominantCC(), 0.01);
		assertEquals(((43.0 + 3.0 + 7.0) / 3.0), testRecord.getAverageDominantCDC(), 0.01);
		assertEquals(((14.0 + 30.0 + 4.0) / 3.0), testRecord.getAverageDominantDD(), 0.01);
		assertEquals(((4.0 + 2.0 + 0.0) / 3.0), testRecord.getAverageAmountNoPrevalent(), 0.01);
		assertEquals(((13.32 + 90.5) / 2.0), testRecord.getAARoundNoPrevalent(), 0.01);
	}
	
	/**
	 * Test averaging first round where only CC and DD occur.
	 */
	@Test
	public void testAnalyse3T1() {
		int testMemorySize = 2;
		ScenarioRecord testRecord = new ScenarioRecord(testMemorySize);
		
		//Create rec1 with necessary data
		TourneyRecord rec1 = new TourneyRecord(testMemorySize);
		rec1.setFirstHitCC(45);
		rec1.setFirstHitDD(2);
		testRecord.addRecord(rec1);
		
		//Create rec2 with necessary data
		TourneyRecord rec2 = new TourneyRecord(testMemorySize);
		rec2.setFirstHitCC(37);
		rec2.setFirstHitDD(66);
		testRecord.addRecord(rec2);
		
		//Create rec3 with necessary data
		TourneyRecord rec3 = new TourneyRecord(testMemorySize);
		rec3.setFirstHitCC(4);
		rec3.setFirstHitDD(0);
		testRecord.addRecord(rec3);
		
		//Create rec4 with necessary data
		TourneyRecord rec4 = new TourneyRecord(testMemorySize);
		rec4.setFirstHitCC(0);
		rec4.setFirstHitDD(0);
		testRecord.addRecord(rec4);
		
		//Test stuff
		testRecord.analyse3();
		assertEquals((86.0 / 3.0), testRecord.getAverageFirstHitCC(), 0.01);
		assertEquals((68.0 / 2.0), testRecord.getAverageFirstHitDD(), 0.01);
	}
	
	/**
	 * Test averaging out the lowest, highest and average payoffs for each round per simulation.
	 */
	@Test
	public void testAnalyse4T1() {
		int testMemorySize = 2;
		int testNumberOfTourneysPerSim = 3;
		ScenarioRecord testRecord = new ScenarioRecord(testMemorySize);
		
		//Create rec1 with necessary data
		TourneyRecord rec1 = new TourneyRecord(testMemorySize);
		ArrayList<Integer> rec1L = new ArrayList<Integer>();
		for(int i = 1; i <= testNumberOfTourneysPerSim; i++) {
			rec1L.add(0 * i);
		}
		rec1.setLowestPayoffs(rec1L);
		
		ArrayList<Integer> rec1H = new ArrayList<Integer>();
		for(int i = 1; i <= testNumberOfTourneysPerSim; i++) {
			rec1H.add(2 * i);
		}
		rec1.setHighestPayoffs(rec1H);
		
		ArrayList<Double> rec1A = new ArrayList<Double>();
		for(int i = 1; i <= testNumberOfTourneysPerSim; i++) {
			rec1A.add(3.0 * i);
		}
		rec1.setAveragePayoffs(rec1A);
		testRecord.addRecord(rec1);
		
		//Create rec2 with necessary data
		TourneyRecord rec2 = new TourneyRecord(testMemorySize);
		ArrayList<Integer> rec2L = new ArrayList<Integer>();
		for(int i = 1; i <= testNumberOfTourneysPerSim; i++) {
			rec2L.add(4 * i);
		}
		rec2.setLowestPayoffs(rec2L);
		
		ArrayList<Integer> rec2H = new ArrayList<Integer>();
		for(int i = 1; i <= testNumberOfTourneysPerSim; i++) {
			rec2H.add(5 * i);
		}
		rec2.setHighestPayoffs(rec2H);
		
		ArrayList<Double> rec2A = new ArrayList<Double>();
		for(int i = 1; i <= testNumberOfTourneysPerSim; i++) {
			rec2A.add(6.0 * i);
		}
		rec2.setAveragePayoffs(rec2A);
		testRecord.addRecord(rec2);
		
		//Test stuff
		testRecord.analyse4(testNumberOfTourneysPerSim);
		assertTrue(testRecord.getAverageLowestPayoffs().size() == testNumberOfTourneysPerSim);
		assertEquals(2.00, testRecord.getAverageLowestPayoffs().get(0), 0.01);
		assertEquals(4.00, testRecord.getAverageLowestPayoffs().get(1), 0.01);
		assertEquals(6.00, testRecord.getAverageLowestPayoffs().get(2), 0.01);
		
		assertTrue(testRecord.getAverageHighestPayoffs().size() == testNumberOfTourneysPerSim);
		assertEquals(3.50, testRecord.getAverageHighestPayoffs().get(0), 0.01);
		assertEquals(7.00, testRecord.getAverageHighestPayoffs().get(1), 0.01);
		assertEquals(10.50, testRecord.getAverageHighestPayoffs().get(2), 0.01);
		
		assertTrue(testRecord.getAverageAveragePayoffs().size() == testNumberOfTourneysPerSim);
		assertEquals(4.50, testRecord.getAverageAveragePayoffs().get(0), 0.01);
		assertEquals(9.00, testRecord.getAverageAveragePayoffs().get(1), 0.01);
		assertEquals(13.50, testRecord.getAverageAveragePayoffs().get(2), 0.01);
		
		//Test standard deviation
		assertTrue(testRecord.getStdDevs().size() == testNumberOfTourneysPerSim);
		assertEquals(1.5, testRecord.getStdDevs().get(0), 0.01);
		assertEquals(3.0, testRecord.getStdDevs().get(1), 0.01);
		assertEquals(4.5, testRecord.getStdDevs().get(2), 0.01);
	}
	
	/**
	 * Test averaging the average strategies used after each round for all simulations.
	 */
	@Test
	public void testAnalyse5T1() {
		int testMemorySize = 1;
		int testNumberOfTourneysPerSim = 1;
		ScenarioRecord testRecord = new ScenarioRecord(testMemorySize);
		
		//Create rec1 with necessary data
		TourneyRecord rec1 = new TourneyRecord(testMemorySize);
		double[][] rec1AS1 = new double[][] {
			{1.00, 1.25, 1.40, 1.65},
			{1.05, 1.10, 1.35, 1.80},
			{1.10, 1.00, 1.20, 1.40}
		};
		//double[][][] rec1AS = new double[testNumberOfTourneysPerSim][Strategy.getRows(testMemorySize)][Strategy.getColumns(testMemorySize)];
		//rec1AS[0] = rec1AS1;
		rec1.setAveStrategyAfterLRound(rec1AS1);
		testRecord.addRecord(rec1);
		
		//Create rec2 with necessary data
		TourneyRecord rec2 = new TourneyRecord(testMemorySize);
		double[][] rec2AS1 = new double[][] {
			{1.30, 1.65, 2.00, 1.15},
			{1.00, 1.20, 1.25, 1.50},
			{1.30, 1.40, 1.20, 1.75}
		};
		//double[][][] rec2AS = new double[testNumberOfTourneysPerSim][Strategy.getRows(testMemorySize)][Strategy.getColumns(testMemorySize)];
		//rec2AS[0] = rec2AS1;
		rec2.setAveStrategyAfterLRound(rec2AS1);
		testRecord.addRecord(rec2);
		
		//Test stuff
		testRecord.analyse5(testNumberOfTourneysPerSim);
		//assertTrue(testRecord.getAASAER().length == testNumberOfTourneysPerSim);
		
		assertEquals(1.15, testRecord.getAASALR()[0][0], 0.01);
		assertEquals(1.45, testRecord.getAASALR()[0][1], 0.01);
		assertEquals(1.70, testRecord.getAASALR()[0][2], 0.01);
		assertEquals(1.40, testRecord.getAASALR()[0][3], 0.01);
		
		assertEquals(1.025, testRecord.getAASALR()[1][0], 0.01);
		assertEquals(1.15, testRecord.getAASALR()[1][1], 0.01);
		assertEquals(1.30, testRecord.getAASALR()[1][2], 0.01);
		assertEquals(1.65, testRecord.getAASALR()[1][3], 0.01);
		
		assertEquals(1.20, testRecord.getAASALR()[2][0], 0.01);
		assertEquals(1.20, testRecord.getAASALR()[2][1], 0.01);
		assertEquals(1.20, testRecord.getAASALR()[2][2], 0.01);
		assertEquals(1.575, testRecord.getAASALR()[2][3], 0.01);
	}
	
	/**
	 * Test averaging the average strategies used after each round for all simulations.
	 */
	@Test
	public void testAnalyse5T2() {

		int testMemorySize = 2;
		int testRows = Strategy.getRows(testMemorySize);
		int testColumns = Strategy.getColumns(testMemorySize);
		int testNumberOfTourneysPerSim = 3;
		ScenarioRecord testRecord = new ScenarioRecord(testMemorySize);
		
		//Create rec1 with necessary data
		TourneyRecord rec1 = new TourneyRecord(testMemorySize);
		double[][] rec1AS1 = new double[testRows][testColumns];
		for(int i = 0; i < testRows; i++) {
			for(int j = 0; j < testColumns; j++) {
				rec1AS1[i][j] = 1.10;
			}
		}
		double[][] rec1AS2 = new double[testRows][testColumns];
		for(int i = 0; i < testRows; i++) {
			for(int j = 0; j < testColumns; j++) {
				rec1AS2[i][j] = 1.20;
			}
		}
		double[][] rec1AS3 = new double[testRows][testColumns];
		for(int i = 0; i < testRows; i++) {
			for(int j = 0; j < testColumns; j++) {
				rec1AS3[i][j] = 1.30;
			}
		}
		//double[][][] rec1AS = new double[testNumberOfTourneysPerSim][testRows][testColumns];
		//rec1AS[0] = rec1AS1;
		//rec1AS[1] = rec1AS2;
		//rec1AS[2] = rec1AS3;
		rec1.setAveStrategyAfterLRound(rec1AS3);
		testRecord.addRecord(rec1);
		
		//Create rec2 with necessary data
		TourneyRecord rec2 = new TourneyRecord(testMemorySize);
		double[][] rec2AS1 = new double[testRows][testColumns];
		for(int i = 0; i < testRows; i++) {
			for(int j = 0; j < testColumns; j++) {
				rec2AS1[i][j] = 1.40;
			}
		}
		double[][] rec2AS2 = new double[testRows][testColumns];
		for(int i = 0; i < testRows; i++) {
			for(int j = 0; j < testColumns; j++) {
				rec2AS2[i][j] = 1.50;
			}
		}
		double[][] rec2AS3 = new double[testRows][testColumns];
		for(int i = 0; i < testRows; i++) {
			for(int j = 0; j < testColumns; j++) {
				rec2AS3[i][j] = 1.60;
			}
		}
//		double[][][] rec2AS = new double[testNumberOfTourneysPerSim][testRows][testColumns];
//		rec2AS[0] = rec2AS1;
//		rec2AS[1] = rec2AS2;
//		rec2AS[2] = rec2AS3;
		rec2.setAveStrategyAfterLRound(rec2AS3);
		testRecord.addRecord(rec2);
		
		//Test stuff
		testRecord.analyse5(testNumberOfTourneysPerSim);
		for(int i = 0; i < testRows; i++) {
			for(int j = 0; j < testColumns; j++) {
//				assertEquals(1.25, testRecord.getAASAER()[0][i][j], 0.01);
//				assertEquals(1.35, testRecord.getAASAER()[1][i][j], 0.01);
				assertEquals(1.45, testRecord.getAASALR()[i][j], 0.01);
			}
		}
	}
	
	/**
	 * Test averaging the total age for each round over a number of simulations.
	 */
	@Test
	public void testAnalyse6T1() {
		int testMemorySize = 3;
		int testNumberOfTourneysPerSim = 3;
		ScenarioRecord testRecord = new ScenarioRecord(testMemorySize);
		
		//Create rec1 with necessary data
		TourneyRecord rec1 = new TourneyRecord(testMemorySize);
		ArrayList<Integer> TA1 = new ArrayList<Integer>();
		TA1.add(15);
		TA1.add(48);
		TA1.add(71);
		rec1.setTotalAges(TA1);
		testRecord.addRecord(rec1);
		
		//Create rec2 with necessary data
		TourneyRecord rec2 = new TourneyRecord(testMemorySize);
		ArrayList<Integer> TA2 = new ArrayList<Integer>();
		TA2.add(155);
		TA2.add(63);
		TA2.add(99);
		rec2.setTotalAges(TA2);
		testRecord.addRecord(rec2);
		
		//Create rec3 with necessary data
		TourneyRecord rec3 = new TourneyRecord(testMemorySize);
		ArrayList<Integer> TA3 = new ArrayList<Integer>();
		TA3.add(44);
		TA3.add(33);
		TA3.add(62);
		rec3.setTotalAges(TA3);
		testRecord.addRecord(rec3);
		
		//Test stuff
		testRecord.analyse6(testNumberOfTourneysPerSim);
		assertTrue(testRecord.getATA().size() == 3);
		assertEquals((214.0 / 3.0), testRecord.getATA().get(0), 0.01);
		assertEquals((144.0 / 3.0), testRecord.getATA().get(1), 0.01);
		assertEquals((232.0 / 3.0), testRecord.getATA().get(2), 0.01);
	}
	
	/**
	 * Test writing analysed data to a file: read the file and compare what you find to what
	 * you wanted there to be written.
	 * 
	 * Very extensive test, to be sure. Doing it in one go is beneficial because
	 * "before" would mean having to run a simulation before each test.
	 */
	@Test
	public void testWrite() {
		
		//Number of simulations
		int numberOfSimulations = 4;
		
		//Parameters
		int numberOfPlayers = 5;
		int memorySize = 2;
		int numberOfRoundsPerGame = 20;
		int numberOfTourneys = 20;
		double lostRate = 0.05;
		double wrongRate = 0.05;
		double delayedRate = 0.05;
		
		//Create file with data (actual)
		ScenarioRecord scenarioRecord = new ScenarioRecord(memorySize);
		for (int i = 0; i < numberOfSimulations; i++) {
			
			//Create random players
			ArrayList<Player> playerList = Scenario.createRandomPlayers(numberOfPlayers, memorySize);
			
			//Play tournament repeatedly
			Tourney tourney = new Tourney(playerList, memorySize);
			tourney.repeatedlyPlayTourney(numberOfRoundsPerGame, numberOfTourneys, lostRate, wrongRate, delayedRate);
			tourney.getRecord().analyzeGivenRecords(numberOfPlayers, numberOfRoundsPerGame);
			scenarioRecord.addRecord(tourney.getRecord());
		}
		scenarioRecord.analyzeTRecords(numberOfTourneys);
		scenarioRecord.writeData("data/testFileAnaWrite", numberOfTourneys);
		
		//Read the created file and compare what is written to the variables in scenarioRecord
		//you expected to be written
		try(BufferedReader buff = new BufferedReader(new FileReader("data/testFileAnaWrite"))) {
			
			//Analyse 1 related write
			for(int i = 0; i < numberOfTourneys; i++) {
				String line = buff.readLine();
				String[] numbers = line.split(" ");
				
				assertTrue(numbers.length == 3);
				assertEquals(scenarioRecord.getAverageNumberOfCCs().get(i), Double.parseDouble(numbers[0]), 0.01);
				assertEquals(scenarioRecord.getAverageNumberOfCDCs().get(i), Double.parseDouble(numbers[1]), 0.01);
				assertEquals(scenarioRecord.getAverageNumberOfDDs().get(i), Double.parseDouble(numbers[2]), 0.01);
			}
			
			//Analyse (2 + 3) related write
			buff.readLine();
			
			String[] ana23 = buff.readLine().split(" ");
			assertTrue(ana23.length == 1);
			assertEquals(scenarioRecord.getAverageDominantCC(), Double.parseDouble(ana23[0]), 0.01);
			
			ana23 = buff.readLine().split(" ");
			assertTrue(ana23.length == 1);
			assertEquals(scenarioRecord.getAverageDominantCDC(), Double.parseDouble(ana23[0]), 0.01);
			
			ana23 = buff.readLine().split(" ");
			assertTrue(ana23.length == 1);
			assertEquals(scenarioRecord.getAverageDominantDD(), Double.parseDouble(ana23[0]), 0.01);
			
			ana23 = buff.readLine().split(" ");
			assertTrue(ana23.length == 1);
			assertEquals(scenarioRecord.getAverageAmountNoPrevalent(), Double.parseDouble(ana23[0]), 0.01);
			
			ana23 = buff.readLine().split(" ");
			assertTrue(ana23.length == 1);
			assertEquals(scenarioRecord.getAARoundNoPrevalent(), Double.parseDouble(ana23[0]), 0.01);
			
			ana23 = buff.readLine().split(" ");
			assertTrue(ana23.length == 1);
			assertEquals(scenarioRecord.getAverageFirstHitCC(), Double.parseDouble(ana23[0]), 0.01);
			
			ana23 = buff.readLine().split(" ");
			assertTrue(ana23.length == 1);
			assertEquals(scenarioRecord.getAverageFirstHitDD(), Double.parseDouble(ana23[0]), 0.01);
			
			//Analyse 4 related write
			buff.readLine();
			for(int i = 0; i < numberOfTourneys; i++) {
				String[] numbers = buff.readLine().split(" ");
				
				assertTrue(numbers.length == 4);
				assertEquals(scenarioRecord.getAverageLowestPayoffs().get(i), Double.parseDouble(numbers[0]), 0.01);
				assertEquals(scenarioRecord.getAverageHighestPayoffs().get(i), Double.parseDouble(numbers[1]), 0.01);
				assertEquals(scenarioRecord.getAverageAveragePayoffs().get(i), Double.parseDouble(numbers[2]), 0.01);
				assertEquals(scenarioRecord.getStdDevs().get(i), Double.parseDouble(numbers[3]), 0.01);
			}
			
			//Analyse 5 related write
			buff.readLine();
			for(int i = 0; i < Strategy.getRows(memorySize); i++) {
				String[] numbers = buff.readLine().split(" ");
				assertTrue(numbers.length == Strategy.getColumns(memorySize));
				for(int j = 0; j < Strategy.getColumns(memorySize); j++) {
					assertEquals(scenarioRecord.getAASALR()[i][j], Double.parseDouble(numbers[j]), 0.01);
				}
			}
			
			//Analyse 6 related write
			buff.readLine();
			for(int i = 0; i < numberOfTourneys; i++) {
				String[] numbers = buff.readLine().split(" ");
				
				assertTrue(numbers.length == 1);
				assertEquals(scenarioRecord.getATA().get(i), Double.parseDouble(numbers[0]), 0.01);
			}
			
			//End of file
			assertNull(buff.readLine());
			
		} catch (IOException e) {
			e.printStackTrace();
			
			//Test failed
			fail("Didn't read file");
		}
	}
	
	/**
	 * Test extensively writing about your TourneyRecords to a file.
	 */
	@Test
	public void testExtensiveWrite() {
		//Number of simulations
		int numberOfSimulations = 4;
		
		//Parameters
		int numberOfPlayers = 5;
		int memorySize = 2;
		int numberOfRoundsPerGame = 25;
		int numberOfTourneys = 18;
		double lostRate = 0.05;
		double wrongRate = 0.05;
		double delayedRate = 0.05;
		
		//Create file with data (actual)
		ScenarioRecord scenarioRecord = new ScenarioRecord(memorySize);
		for (int i = 0; i < numberOfSimulations; i++) {
			
			//Create random players
			ArrayList<Player> playerList = Scenario.createRandomPlayers(numberOfPlayers, memorySize);
			
			//Play tournament repeatedly
			Tourney tourney = new Tourney(playerList, memorySize);
			tourney.repeatedlyPlayTourney(numberOfRoundsPerGame, numberOfTourneys, lostRate, wrongRate, delayedRate);
			tourney.getRecord().analyzeGivenRecords(numberOfPlayers, numberOfRoundsPerGame);
			scenarioRecord.addRecord(tourney.getRecord());
		}
		scenarioRecord.extensiveWrite("data/testFileExtWrite", numberOfTourneys);
		
		//Read the created file and compare what is written to the variables in scenarioRecord
		//you expected to be written
		try(BufferedReader buff = new BufferedReader(new FileReader("data/testFileExtWrite"))) {
			
			//Analyse 1 related write
			buff.readLine();
			for(int i = 0; i < numberOfTourneys; i++) {
				String[] numbers = buff.readLine().split(" ");
				assertTrue(numbers.length == numberOfSimulations);
				
				for(int j = 0; j < numberOfSimulations; j++) {
						assertTrue(Integer.parseInt(numbers[j]) ==
								scenarioRecord.getAnalysedTRecords().get(j).getNumberOfCCs().get(i));
				}
			}
			
			buff.readLine();
			buff.readLine();
			
			for(int i = 0; i < numberOfTourneys; i++) {
				String[] numbers = buff.readLine().split(" ");
				assertTrue(numbers.length == numberOfSimulations);
				
				for(int j = 0; j < numberOfSimulations; j++) {
						assertTrue(Integer.parseInt(numbers[j]) ==
								(scenarioRecord.getAnalysedTRecords().get(j).getNumberOfCDs().get(i)) +
								scenarioRecord.getAnalysedTRecords().get(j).getNumberOfDCs().get(i));
				}
			}
			
			buff.readLine();
			buff.readLine();
			
			for(int i = 0; i < numberOfTourneys; i++) {
				String[] numbers = buff.readLine().split(" ");
				assertTrue(numbers.length == numberOfSimulations);
				
				for(int j = 0; j < numberOfSimulations; j++) {
						assertTrue(Integer.parseInt(numbers[j]) ==
								scenarioRecord.getAnalysedTRecords().get(j).getNumberOfDDs().get(i));
				}
			}
			
			//Analyse (2 + 3) related write
			buff.readLine();
			buff.readLine();
			
			String[] ana23 = buff.readLine().split(" ");
			assertTrue(ana23.length == numberOfSimulations);
			for(int i = 0; i < numberOfSimulations; i++) {
				assertTrue(Integer.parseInt(ana23[i]) ==
						scenarioRecord.getAnalysedTRecords().get(i).getDominantCC());
			}
			
			buff.readLine();
			buff.readLine();
			ana23 = buff.readLine().split(" ");
			assertTrue(ana23.length == numberOfSimulations);
			for(int i = 0; i < numberOfSimulations; i++) {
				assertTrue(Integer.parseInt(ana23[i]) ==
						scenarioRecord.getAnalysedTRecords().get(i).getDominantCDC());
			}
			
			buff.readLine();
			buff.readLine();
			ana23 = buff.readLine().split(" ");
			assertTrue(ana23.length == numberOfSimulations);
			for(int i = 0; i < numberOfSimulations; i++) {
				assertTrue(Integer.parseInt(ana23[i]) ==
						scenarioRecord.getAnalysedTRecords().get(i).getDominantDD());
			}
			
			buff.readLine();
			buff.readLine();
			ana23 = buff.readLine().split(" ");
			assertTrue(ana23.length == numberOfSimulations);
			for(int i = 0; i < numberOfSimulations; i++) {
				assertTrue(Integer.parseInt(ana23[i]) ==
						scenarioRecord.getAnalysedTRecords().get(i).getNotPrevalentCount());
			}
			
			buff.readLine();
			buff.readLine();
			ana23 = buff.readLine().split(" ");
			assertTrue(ana23.length == numberOfSimulations);
			for(int i = 0; i < numberOfSimulations; i++) {
				assertEquals(scenarioRecord.getAnalysedTRecords().get(i).getAverageRoundNoPrevalent(),
						Double.parseDouble(ana23[i]), 0.01);
			}
			
			buff.readLine();
			buff.readLine();
			ana23 = buff.readLine().split(" ");
			assertTrue(ana23.length == numberOfSimulations);
			for(int i = 0; i < numberOfSimulations; i++) {
				assertTrue(Integer.parseInt(ana23[i]) ==
						scenarioRecord.getAnalysedTRecords().get(i).getFirstHitCC());
			}
			
			buff.readLine();
			buff.readLine();
			ana23 = buff.readLine().split(" ");
			assertTrue(ana23.length == numberOfSimulations);
			for(int i = 0; i < numberOfSimulations; i++) {
				assertTrue(Integer.parseInt(ana23[i]) ==
						scenarioRecord.getAnalysedTRecords().get(i).getFirstHitDD());
			}
			
			//Analyse 4 related write
			buff.readLine();
			buff.readLine();
			
			for(int i = 0; i < numberOfTourneys; i++) {
				String[] numbers = buff.readLine().split(" ");
				assertTrue(numbers.length == numberOfSimulations);
				
				for(int j = 0; j < numberOfSimulations; j++) {
						assertTrue(Integer.parseInt(numbers[j]) ==
								scenarioRecord.getAnalysedTRecords().get(j).getLowestPayoffs().get(i));
				}
			}
			
			buff.readLine();
			buff.readLine();
			
			for(int i = 0; i < numberOfTourneys; i++) {
				String[] numbers = buff.readLine().split(" ");
				assertTrue(numbers.length == numberOfSimulations);
				
				for(int j = 0; j < numberOfSimulations; j++) {
						assertTrue(Integer.parseInt(numbers[j]) ==
								scenarioRecord.getAnalysedTRecords().get(j).getHighestPayoffs().get(i));
				}
			}
			
			buff.readLine();
			buff.readLine();
			
			for(int i = 0; i < numberOfTourneys; i++) {
				String[] numbers = buff.readLine().split(" ");
				assertTrue(numbers.length == numberOfSimulations);
				
				for(int j = 0; j < numberOfSimulations; j++) {
						assertEquals(scenarioRecord.getAnalysedTRecords().get(j).getAveragePayoffs().get(i),
								Double.parseDouble(numbers[j]), 0.01);
				}
			}
			
			//Analyse 5 related write
			buff.readLine();
			buff.readLine();
			
			for(int k = 0; k < numberOfSimulations; k++) {
				for(int i = 0; i < Strategy.getRows(memorySize); i++) {
					String[] numbers = buff.readLine().split(" ");
					assertTrue(numbers.length == Strategy.getColumns(memorySize));
					
					for(int j = 0; j < Strategy.getColumns(memorySize); j++) {
						assertEquals(scenarioRecord.getAnalysedTRecords().get(k).getAveStrategyAfterLRound()[i][j],
								Double.parseDouble(numbers[j]), 0.01);
					}
				}
				buff.readLine();
			}
			
			//Analyse 6 related write
			buff.readLine();
			
			for(int i = 0; i < numberOfTourneys; i++) {
				String[] numbers = buff.readLine().split(" ");
				assertTrue(numbers.length == numberOfSimulations);
				
				for(int j = 0; j < numberOfSimulations; j++) {
						assertTrue(Integer.parseInt(numbers[j]) ==
								scenarioRecord.getAnalysedTRecords().get(j).getTotalAges().get(i));
				}
			}
			
			//End of File
			assertNull(buff.readLine());
			
			System.out.println("Succes333");
			
		} catch (IOException e) {
			e.printStackTrace();
			fail("Didn't read file.");
		}
	}
}
