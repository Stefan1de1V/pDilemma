package simulation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import strategies.Strategy;

/**
 * This class can be used to read parts of an ext file and store the data in ArrayLists.
 * @author Stefan
 *
 */
public class ExtReadHelper {

	/**
	 * Read CC data from ext file.
	 * @param numberOfSimulations the number of simulations in the ext file
	 * @param numberOfTourneys the number of generations in the ext file
	 * @param extFileToRead the ext file to read from
	 * @param listsToStore where the read data is stored
	 * @return the header of the part of the file being read (mainly for testing purposes)
	 */
	public static String readCCFromExt(int numberOfSimulations, int numberOfTourneys,
			String extFileToRead, ArrayList<ArrayList<Double>> listsToStore) {
		
		String result = "";
		
		try(BufferedReader buff = new BufferedReader(new FileReader(extFileToRead))) {
			
			//Read good stuff
			result = buff.readLine();
			standardPartExtRead(false, numberOfSimulations, numberOfTourneys, buff, listsToStore);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Read lowest payoffs data from ext file.
	 * @param numberOfSimulations the number of simulations in the ext file
	 * @param numberOfTourneys the number of generations in the ext file
	 * @param fileToRead the ext file to read from
	 * @param listsToStore where the read data is stored
	 * @return the header of the part of the file being read (mainly for testing purposes)
	 */
	public static String readLowestFromExt(int numberOfSimulations, int numberOfTourneys, String fileToRead,
			ArrayList<ArrayList<Double>> listsToStore) {
		
		String result = "";
		
		try(BufferedReader buff = new BufferedReader(new FileReader(fileToRead))) {
			
			//Skip CC, CDC, DD lines
			for(int i = 0; i < 3; i++) {
				buff.readLine();
				for(int j = 0; j < numberOfTourneys; j++) {
					buff.readLine();
				}
				buff.readLine();
			}
			
			//Skip single lines
			for(int i = 0; i < (7 * 3); i++) {
				buff.readLine();
			}
			
			//Read good stuff
			result = buff.readLine();
			standardPartExtRead(false, numberOfSimulations, numberOfTourneys, buff, listsToStore);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Read highest payoffs data from ext file.
	 * @param numberOfSimulations the number of simulations in the ext file
	 * @param numberOfTourneys the number of generations in the ext file
	 * @param fileToRead the ext file to read from
	 * @param listsToStore where the read data is stored
	 * @return the header of the part of the file being read (mainly for testing purposes)
	 */
	public static String readHighestFromExt(int numberOfSimulations, int numberOfTourneys, String fileToRead,
			ArrayList<ArrayList<Double>> listsToStore) {
		
		String result = "";
		
		try(BufferedReader buff = new BufferedReader(new FileReader(fileToRead))) {
			
			//Skip CC, CDC, DD lines
			for(int i = 0; i < 3; i++) {
				buff.readLine();
				for(int j = 0; j < numberOfTourneys; j++) {
					buff.readLine();
				}
				buff.readLine();
			}
			
			//Skip single lines
			for(int i = 0; i < (7 * 3); i++) {
				buff.readLine();
			}
			
			//Skip Lowest
			buff.readLine();
			for(int j = 0; j < numberOfTourneys; j++) {
				buff.readLine();
			}
			buff.readLine();
			
			//Read good stuff
			result = buff.readLine();
			standardPartExtRead(false, numberOfSimulations, numberOfTourneys, buff, listsToStore);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Read average payoffs data from ext file.
	 * @param numberOfSimulations the number of simulations in the ext file
	 * @param numberOfTourneys the number of generations in the ext file
	 * @param fileToRead the ext file to read from
	 * @param listsToStore where the read data is stored
	 * @return the header of the part of the file being read (mainly for testing purposes)
	 */
	public static String readAverageFromExt(int numberOfSimulations, int numberOfTourneys, String fileToRead,
			ArrayList<ArrayList<Double>> listsToStore) {
		
		String result = "";
		
		try(BufferedReader buff = new BufferedReader(new FileReader(fileToRead))) {
			
			//Skip CC, CDC, DD lines
			for(int i = 0; i < 3; i++) {
				buff.readLine();
				for(int j = 0; j < numberOfTourneys; j++) {
					buff.readLine();
				}
				buff.readLine();
			}
			
			//Skip single lines
			for(int i = 0; i < (7 * 3); i++) {
				buff.readLine();
			}
			
			//Skip Lowest and Highest lines
			for(int i = 0; i < 2; i++) {
				buff.readLine();
				for(int j = 0; j < numberOfTourneys; j++) {
					buff.readLine();
				}
				buff.readLine();
			}
			
			//Read good stuff
			result = buff.readLine();
			standardPartExtRead(true, numberOfSimulations, numberOfTourneys, buff, listsToStore);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Read pool age data from ext file.
	 * @param numberOfSimulations the number of simulations in the ext file
	 * @param numberOfTourneys the number of generations in the ext file
	 * @param memorySize the memory size of the player strategies recorded by the ext file
	 * @param fileToRead the ext file to read from
	 * @param listsToStore where the read data is stored
	 * @return the header of the part of the file being read (mainly for testing purposes)
	 */
	public static String readAgeFromExt(int numberOfSimulations, int numberOfTourneys, int memorySize, String fileToRead,
			ArrayList<ArrayList<Double>> listsToStore) {
		
		String result = "";
		
		try(BufferedReader buff = new BufferedReader(new FileReader(fileToRead))) {
			
			//Skip CC, CDC, DD lines
			for(int i = 0; i < 3; i++) {
				buff.readLine();
				for(int j = 0; j < numberOfTourneys; j++) {
					buff.readLine();
				}
				buff.readLine();
			}
			
			//Skip single lines
			for(int i = 0; i < (7 * 3); i++) {
				buff.readLine();
			}
			
			//Skip Lowest and Highest, Average lines
			for(int i = 0; i < 3; i++) {
				buff.readLine();
				for(int j = 0; j < numberOfTourneys; j++) {
					buff.readLine();
				}
				buff.readLine();
			}
			
			//Skip tables
			buff.readLine();
			for(int i = 0; i < numberOfSimulations; i++) {
				for(int j = 0; j < (Strategy.getRows(memorySize) + 1); j++) {
					buff.readLine();
				}
			}
			
			//Read good stuff
			result = buff.readLine();
			standardPartExtRead(false, numberOfSimulations, numberOfTourneys, buff, listsToStore); // SHould carsh
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Helper method to read relevant part of ext file and store the result in the provided list (which is assumed to be of the correct make up).
	 * @param doub should be false if integer, true if double
	 * @param numberOfSimulations the number of simulations
	 * @param numberOfTourneys the number of generations
	 * @param buff the file that is currently being read
	 * @param listsToStore the list to store all read results in
	 * @throws IOException
	 */
	private static void standardPartExtRead(boolean doub, int numberOfSimulations, int numberOfTourneys, BufferedReader buff, ArrayList<ArrayList<Double>> listsToStore) throws IOException {
		
		for(int i = 0; i < numberOfTourneys; i++) {
			String[] numbers = buff.readLine().split(" ");
			for(int j = 0; j < numberOfSimulations; j++) {
				if (doub) {
					listsToStore.get(j).add(Double.parseDouble(numbers[j]));
				} else {
					listsToStore.get(j).add((double) Integer.parseInt(numbers[j]));
				}
			}
		}
	}
}
