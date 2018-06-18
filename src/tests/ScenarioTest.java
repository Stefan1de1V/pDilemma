package tests;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import pDilemma.Player;
import simulation.Scenario;
import strategies.Strategy;

public class ScenarioTest {

	/**
	 * Test creating players with random strategies
	 */
	@Test
	public void testCreateRandomPlayers() {
		int testMemorySize = 2;
		int testPlayerAmount = 30;
		ArrayList<Player> testPList = Scenario.createRandomPlayers(testPlayerAmount, testMemorySize);
		
		assertTrue(testPList.size() == testPlayerAmount);
		
		Player first = testPList.get(0);
		assertTrue(first.getStrat().getMemorySize() == testMemorySize);
		
		//Test contents of two-dimensional array (loops I know, but such clean code)
		for(int i = 0; i < Strategy.getRows(testMemorySize); i++) {
			for(int j = 0; j < Strategy.getColumns(testMemorySize); j++) {
				assertTrue(first.getStrat().getStrategy()[i][j] == 1 || first.getStrat().getStrategy()[i][j] == 2);
			}
		}
	}
	
	/**
	 * Test taking an extFile and creating 'confidence intervals' (not real ones because ma1deep instead of ma1) (memory size 1)
	 */
	@Test
	public void testMa1Deep() {
		Scenario.ma1Deep(4, 2, 1, "data/testFileConfRead", "data/testFileConfWrite");
		
		double expected1L = 8.0 - 2.045 * (Math.sqrt(122.0 / (4 - 1)) / Math.sqrt(4.0));
		double expected1Mean = 8.0;
		double expected1H = 8.0 + 2.045 * (Math.sqrt(122.0 / (4 - 1)) / Math.sqrt(4.0));
		
		double expected2L = 12.5 - 2.045 * (Math.sqrt(133.0 / (4 - 1)) / Math.sqrt(4.0));
		double expected2Mean = 12.5;
		double expected2H = 12.5 + 2.045 * (Math.sqrt(133.0 / (4 - 1)) / Math.sqrt(4.0));
		
		double expected3L = 7.35 - 2.045 * (Math.sqrt(44.77 / (4 - 1)) / Math.sqrt(4.0));
		double expected3Mean = 7.35;
		double expected3H = 7.35 + 2.045 * (Math.sqrt(44.77 / (4 - 1)) / Math.sqrt(4.0));
		
		double expected4L = 5.275 - 2.045 * (Math.sqrt(30.0275 / (4 - 1)) / Math.sqrt(4.0));
		double expected4Mean = 5.275;
		double expected4H = 5.275 + 2.045 * (Math.sqrt(30.0275 / (4 - 1)) / Math.sqrt(4.0));
		
		try(BufferedReader buff = new BufferedReader(new FileReader("data/testFileConfWrite"))) {
			
			//CC checker
			buff.readLine();
			String[] numbers = buff.readLine().split(" ");
			assertEquals(expected1L, Double.parseDouble(numbers[0]), 0.01);
			assertEquals(expected1Mean, Double.parseDouble(numbers[1]), 0.01);
			assertEquals(expected1H, Double.parseDouble(numbers[2]), 0.01);
			
			numbers = buff.readLine().split(" ");
			assertEquals(expected2L, Double.parseDouble(numbers[0]), 0.01);
			assertEquals(expected2Mean, Double.parseDouble(numbers[1]), 0.01);
			assertEquals(expected2H, Double.parseDouble(numbers[2]), 0.01);
			
			//Skip some stuff
			for(int i = 0; i < (1 + (2 + 2) * 4 + 1); i++) {
				buff.readLine();
			}
			
			//Average payoff checker
			numbers = buff.readLine().split(" ");
			assertEquals(expected3L, Double.parseDouble(numbers[0]), 0.01);
			assertEquals(expected3Mean, Double.parseDouble(numbers[1]), 0.01);
			assertEquals(expected3H, Double.parseDouble(numbers[2]), 0.01);
			
			numbers = buff.readLine().split(" ");
			assertEquals(expected4L, Double.parseDouble(numbers[0]), 0.01);
			assertEquals(expected4Mean, Double.parseDouble(numbers[1]), 0.01);
			assertEquals(expected4H, Double.parseDouble(numbers[2]), 0.01);
			
			buff.readLine();
			buff.readLine();
			
			buff.readLine();
			buff.readLine();
			
			//End of File
			buff.readLine();
			assertNull(buff.readLine());
			
		} catch (IOException e) {
			e.printStackTrace();
			fail("Didn't read file.");
		}
	}
	
	/**
	 * Test read an ext-file and calculating the metric result for each of the simulations.
	 */
	@Test
	public void testMa3Deep() {
		Scenario.ma3Deep(4, 30, "data/testFileMetricRead", "data/testFileMetricWrite");
		
		try(BufferedReader buff = new BufferedReader(new FileReader("data/testFileMetricWrite"))) {
			
			//Outcome checker
			buff.readLine();
			String[] numbers = buff.readLine().split(" ");
			assertTrue(Integer.parseInt(numbers[0]) == 19);
			assertTrue(Integer.parseInt(numbers[1]) == 12);
			assertTrue(Integer.parseInt(numbers[2]) == 11);
			assertTrue(Integer.parseInt(numbers[3]) == 12);
			
		} catch (IOException e) {
			e.printStackTrace();
			fail("Didn't read file.");
		}
	}
	
	/**
	 * Test performing statistical tests
	 */
	@Test
	public void testStatTestEachGen() {
		
		ArrayList<Double> calculatedTStats = new ArrayList<Double>();
		ArrayList<Double> calculatedTValues = new ArrayList<Double>();
		
		Scenario.statisticalTest1("CC", 30, 1, 1, "data/testStat1LRead", "data/testStat1HRead", "data/testStat1Write",
				calculatedTStats, calculatedTValues);
		
		//Test the tStat and tValue
		assertTrue(calculatedTStats.size() == 1);
		assertEquals(-3.322274408, calculatedTStats.get(0), 0.01);
		
		assertTrue(calculatedTValues.size() == 1);
		assertEquals(1.688, calculatedTValues.get(0), 0.01);
		
		//Test what has been written to the file
		try(BufferedReader buff = new BufferedReader(new FileReader("data/testStat1Write"))) {
			
			String[] numbers = buff.readLine().split(" ");
			assertTrue(Integer.parseInt(numbers[0]) == 1);
			assertTrue(Integer.parseInt(numbers[1]) == 1);
			
		} catch (IOException e) {
			e.printStackTrace();
			fail("Didn't read file.");
		}
	}
}
