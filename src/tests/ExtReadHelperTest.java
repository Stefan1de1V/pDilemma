package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import simulation.ExtReadHelper;

public class ExtReadHelperTest {
	
	/**
	 * Test reading the correct part of the ext file (CC)
	 */
	@Test
	public void testReadCCFromExt() {
		
		ArrayList<ArrayList<Double>> listsToStore = new ArrayList<ArrayList<Double>>();
		for(int i = 0; i < 30; i++) {
			listsToStore.add(new ArrayList<Double>());
		}
		String expected = "Number of CCs in each simulation, each line is a generation:";
		assertTrue(ExtReadHelper.readCCFromExt(30, 600, "data/testExtHelperRead", listsToStore).equals(expected));
	}

	/**
	 * Test reading the correct part of the ext file (Lowest)
	 */
	@Test
	public void testReadLowestFromExt() {
		
		ArrayList<ArrayList<Double>> listsToStore = new ArrayList<ArrayList<Double>>();
		for(int i = 0; i < 30; i++) {
			listsToStore.add(new ArrayList<Double>());
		}
		String expected = "Lowest payoff per generation, for each simulation:";
		assertTrue(ExtReadHelper.readLowestFromExt(30, 600, "data/testExtHelperRead", listsToStore).equals(expected));
	}
	
	/**
	 * Test reading the correct part of the ext file (Highest)
	 */
	@Test
	public void testReadHighestFromExt() {
		
		ArrayList<ArrayList<Double>> listsToStore = new ArrayList<ArrayList<Double>>();
		for(int i = 0; i < 30; i++) {
			listsToStore.add(new ArrayList<Double>());
		}
		String expected = "Highest payoff per generation, for each simulation:";
		assertTrue(ExtReadHelper.readHighestFromExt(30, 600, "data/testExtHelperRead", listsToStore).equals(expected));
	}
	
	/**
	 * Test reading the correct part of the ext file (Average)
	 */
	@Test
	public void testReadAverageFromExt() {
		
		ArrayList<ArrayList<Double>> listsToStore = new ArrayList<ArrayList<Double>>();
		for(int i = 0; i < 30; i++) {
			listsToStore.add(new ArrayList<Double>());
		}
		String expected = "Average payoff per generation, for each simulation:";
		assertTrue(ExtReadHelper.readAverageFromExt(30, 600, "data/testExtHelperRead", listsToStore).equals(expected));
	}
	
	/**
	 * Test reading the correct part of the ext file (Average)
	 */
	@Test
	public void testReadAgeFromExt() {
		
		ArrayList<ArrayList<Double>> listsToStore = new ArrayList<ArrayList<Double>>();
		for(int i = 0; i < 30; i++) {
			listsToStore.add(new ArrayList<Double>());
		}
		String expected = "The total pool age per generation, for each simulation:";
		assertTrue(ExtReadHelper.readAgeFromExt(30, 600, 2, "data/testExtHelperRead", listsToStore).equals(expected));
	}
}