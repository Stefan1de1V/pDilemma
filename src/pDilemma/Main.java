package pDilemma;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import simulation.Scenario;

@SuppressWarnings("unused")
public class Main {

	/**
	 * Used to simulate experiments, produce data used for graphs and analyse the results
	 * @param args
	 */
	public static void main(String[] args) {
		
		//simulateExperiments();
		generateVisualAndStatData1();
		generateVisualAndStatData2();
		generateVisualAndStatData3();
	}

	/**
	 * For memory size 1, produce data for graphs and perform statistical tests.
	 */
	private static void generateVisualAndStatData1() {
		//Ma1 visual files
		Scenario.ma1(1, "exp/extExp1V30", "week7statistics/vis1Exp1");
		Scenario.ma1(1, "exp/extExp2V30", "week7statistics/vis1Exp2");
		Scenario.ma1(1, "exp/extExp3V30", "week7statistics/vis1Exp3");
		Scenario.ma1(1, "exp/extExp4V30", "week7statistics/vis1Exp4");
		
		//Ma3 visual file + data for statistical test
		Scenario.ma3("exp/extExp1V30", "week7statistics/vis3Exp1");
		Scenario.ma3("exp/extExp2V30", "week7statistics/vis3Exp2");
		Scenario.ma3("exp/extExp3V30", "week7statistics/vis3Exp3");
		Scenario.ma3("exp/extExp4V30", "week7statistics/vis3Exp4");
		
		//Statistical tests: metric comparison (LW increase expected)
		ArrayList<Double> tStats = new ArrayList<Double>();
		ArrayList<Double> tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("CC", 30, 1, 1, "week7statistics/vis3Exp1", "week7statistics/vis3Exp2",
				"week7statistics/Metric1B2", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("CC", 30, 1, 1, "week7statistics/vis3Exp1", "week7statistics/vis3Exp3",
				"week7statistics/Metric1B3", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("CC", 30, 1, 1, "week7statistics/vis3Exp2", "week7statistics/vis3Exp4",
				"week7statistics/Metric2B4", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("CC", 30, 1, 1, "week7statistics/vis3Exp3", "week7statistics/vis3Exp4",
				"week7statistics/Metric3B4", tStats, tValues);
		
		//Statistical tests: age comparison (LW increase expected)
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Age", 30, 600, 1, "exp/extExp1V30", "exp/extExp2V30",
				"week7statistics/Age1B2", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Age", 30, 600, 1, "exp/extExp1V30", "exp/extExp3V30",
				"week7statistics/Age1B3", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Age", 30, 600, 1, "exp/extExp1V30", "exp/extExp4V30",
				"week7statistics/Age1B4", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Age", 30, 600, 1, "exp/extExp2V30", "exp/extExp4V30",
				"week7statistics/Age2B4", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Age", 30, 600, 1, "exp/extExp3V30", "exp/extExp4V30",
				"week7statistics/Age3B4", tStats, tValues);
		
		//Statistical tests: Amount CC games (W decrease expected, L nothing)
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("CC", 30, 600, 1, "exp/extExp4V30", "exp/extExp3V30",
				"week7statistics/CC4B3", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("CC", 30, 600, 1, "exp/extExp4V30", "exp/extExp2V30",
				"week7statistics/CC4B2", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("CC", 30, 600, 1, "exp/extExp3V30", "exp/extExp1V30",
				"week7statistics/CC3B1", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("CC", 30, 600, 1, "exp/extExp2V30", "exp/extExp1V30",
				"week7statistics/CC2B1", tStats, tValues);
		
		//Statistical tests: Average (also lowest, highest payoff later if time) (W expected, L not)
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Average", 30, 600, 1, "exp/extExp4V30", "exp/extExp3V30",
				"week7statistics/Average4B3", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Average", 30, 600, 1, "exp/extExp4V30", "exp/extExp2V30",
				"week7statistics/Average4B2", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Average", 30, 600, 1, "exp/extExp3V30", "exp/extExp1V30",
				"week7statistics/Average3B1", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Average", 30, 600, 1, "exp/extExp2V30", "exp/extExp1V30",
				"week7statistics/Average2B1", tStats, tValues);
	}
	
	/**
	 * For memory size 2, produce data for graphs and perform statistical tests.
	 */
	private static void generateVisualAndStatData2() {
		//Ma1 visual files
		Scenario.ma1(2, "exp2/extExp5", "week7statistics2/vis1Exp5");
		Scenario.ma1(2, "exp2/extExp6", "week7statistics2/vis1Exp6");
		Scenario.ma1(2, "exp2/extExp7", "week7statistics2/vis1Exp7");
		Scenario.ma1(2, "exp2/extExp8", "week7statistics2/vis1Exp8");
		Scenario.ma1(2, "exp2/extExp9", "week7statistics2/vis1Exp9");
		Scenario.ma1(2, "exp2/extExp10", "week7statistics2/vis1Exp10");
		Scenario.ma1(2, "exp2/extExp11", "week7statistics2/vis1Exp11");
		Scenario.ma1(2, "exp2/extExp12", "week7statistics2/vis1Exp12");
		
		//Ma3 visual file + data for statistical test
		Scenario.ma3("exp2/extExp5", "week7statistics2/vis3Exp5");
		Scenario.ma3("exp2/extExp6", "week7statistics2/vis3Exp6");
		Scenario.ma3("exp2/extExp7", "week7statistics2/vis3Exp7");
		Scenario.ma3("exp2/extExp8", "week7statistics2/vis3Exp8");
		Scenario.ma3("exp2/extExp9", "week7statistics2/vis3Exp9");
		Scenario.ma3("exp2/extExp10", "week7statistics2/vis3Exp10");
		Scenario.ma3("exp2/extExp11", "week7statistics2/vis3Exp11");
		Scenario.ma3("exp2/extExp12", "week7statistics2/vis3Exp12");
		
		//Statistical tests: CC games (W decrease expected, LD nothing expected)
		ArrayList<Double> tStats = new ArrayList<Double>();
		ArrayList<Double> tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("CC", 30, 600, 2, "exp2/extExp6", "exp2/extExp5",
				"week7statistics2/CC6B5", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("CC", 30, 600, 2, "exp2/extExp7", "exp2/extExp5",
				"week7statistics2/CC7B5", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("CC", 30, 600, 2, "exp2/extExp9", "exp2/extExp5",
				"week7statistics2/CC9B5", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("CC", 30, 600, 2, "exp2/extExp8", "exp2/extExp6",
				"week7statistics2/CC8B6", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("CC", 30, 600, 2, "exp2/extExp10", "exp2/extExp6",
				"week7statistics2/CC10B6", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("CC", 30, 600, 2, "exp2/extExp8", "exp2/extExp7",
				"week7statistics2/CC8B7", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("CC", 30, 600, 2, "exp2/extExp11", "exp2/extExp7",
				"week7statistics2/CC11B7", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("CC", 30, 600, 2, "exp2/extExp10", "exp2/extExp9",
				"week7statistics2/CC10B9", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("CC", 30, 600, 2, "exp2/extExp11", "exp2/extExp9",
				"week7statistics2/CC11B9", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("CC", 30, 600, 2, "exp2/extExp12", "exp2/extExp8",
				"week7statistics2/CC12B8", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("CC", 30, 600, 2, "exp2/extExp12", "exp2/extExp10",
				"week7statistics2/CC12B10", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("CC", 30, 600, 2, "exp2/extExp12", "exp2/extExp11",
				"week7statistics2/CC12B11", tStats, tValues);
		
		//Statistical tests: Average payoffs (W decrease expected, LD nothing expected)
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Average", 30, 600, 2, "exp2/extExp6", "exp2/extExp5",
				"week7statistics2/Average6B5", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Average", 30, 600, 2, "exp2/extExp7", "exp2/extExp5",
				"week7statistics2/Average7B5", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Average", 30, 600, 2, "exp2/extExp9", "exp2/extExp5",
				"week7statistics2/Average9B5", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Average", 30, 600, 2, "exp2/extExp8", "exp2/extExp6",
				"week7statistics2/Average8B6", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Average", 30, 600, 2, "exp2/extExp10", "exp2/extExp6",
				"week7statistics2/Average10B6", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Average", 30, 600, 2, "exp2/extExp8", "exp2/extExp7",
				"week7statistics2/Average8B7", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Average", 30, 600, 2, "exp2/extExp11", "exp2/extExp7",
				"week7statistics2/Average11B7", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Average", 30, 600, 2, "exp2/extExp10", "exp2/extExp9",
				"week7statistics2/Average10B9", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Average", 30, 600, 2, "exp2/extExp11", "exp2/extExp9",
				"week7statistics2/Average11B9", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Average", 30, 600, 2, "exp2/extExp12", "exp2/extExp8",
				"week7statistics2/Average12B8", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Average", 30, 600, 2, "exp2/extExp12", "exp2/extExp10",
				"week7statistics2/Average12B10", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Average", 30, 600, 2, "exp2/extExp12", "exp2/extExp11",
				"week7statistics2/Average12B11", tStats, tValues);
		
		//Statistical tests: Metric (LWD, increase expected)
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("CC", 30, 1, 2, "week7statistics2/vis3Exp5", "week7statistics2/vis3Exp6",
				"week7statistics2/Metric5B6", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("CC", 30, 1, 2, "week7statistics2/vis3Exp5", "week7statistics2/vis3Exp7",
				"week7statistics2/Metric5B7", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("CC", 30, 1, 2, "week7statistics2/vis3Exp5", "week7statistics2/vis3Exp9",
				"week7statistics2/Metric5B9", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("CC", 30, 1, 2, "week7statistics2/vis3Exp6", "week7statistics2/vis3Exp8",
				"week7statistics2/Metric6B8", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("CC", 30, 1, 2, "week7statistics2/vis3Exp6", "week7statistics2/vis3Exp10",
				"week7statistics2/Metric6B10", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("CC", 30, 1, 2, "week7statistics2/vis3Exp7", "week7statistics2/vis3Exp8",
				"week7statistics2/Metric7B8", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("CC", 30, 1, 2, "week7statistics2/vis3Exp7", "week7statistics2/vis3Exp11",
				"week7statistics2/Metric7B11", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("CC", 30, 1, 2, "week7statistics2/vis3Exp9", "week7statistics2/vis3Exp10",
				"week7statistics2/Metric9B10", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("CC", 30, 1, 2, "week7statistics2/vis3Exp9", "week7statistics2/vis3Exp11",
				"week7statistics2/Metric9B11", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("CC", 30, 1, 2, "week7statistics2/vis3Exp8", "week7statistics2/vis3Exp12",
				"week7statistics2/Metric8B12", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("CC", 30, 1, 2, "week7statistics2/vis3Exp10", "week7statistics2/vis3Exp12",
				"week7statistics2/Metric10B12", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("CC", 30, 1, 2, "week7statistics2/vis3Exp11", "week7statistics2/vis3Exp12",
				"week7statistics2/Metric11B12", tStats, tValues);
		
		//Statistical test: age
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Age", 30, 600, 2, "exp2/extExp5", "exp2/extExp6",
				"week7statistics2/Age5B6", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Age", 30, 600, 2, "exp2/extExp5", "exp2/extExp7",
				"week7statistics2/Age5B7", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Age", 30, 600, 2, "exp2/extExp5", "exp2/extExp9",
				"week7statistics2/Age5B9", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Age", 30, 600, 2, "exp2/extExp5", "exp2/extExp8",
				"week7statistics2/Age5B8", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Age", 30, 600, 2, "exp2/extExp5", "exp2/extExp10",
				"week7statistics2/Age5B10", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Age", 30, 600, 2, "exp2/extExp5", "exp2/extExp11",
				"week7statistics2/Age5B11", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Age", 30, 600, 2, "exp2/extExp5", "exp2/extExp12",
				"week7statistics2/Age5B12", tStats, tValues);
		
			//Higher
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Age", 30, 600, 2, "exp2/extExp6", "exp2/extExp8",
				"week7statistics2/Age6B8", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Age", 30, 600, 2, "exp2/extExp6", "exp2/extExp10",
				"week7statistics2/Age6B10", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Age", 30, 600, 2, "exp2/extExp7", "exp2/extExp8",
				"week7statistics2/Age7B8", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Age", 30, 600, 2, "exp2/extExp7", "exp2/extExp11",
				"week7statistics2/Age7B11", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Age", 30, 600, 2, "exp2/extExp9", "exp2/extExp10",
				"week7statistics2/Age9B10", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Age", 30, 600, 2, "exp2/extExp9", "exp2/extExp11",
				"week7statistics2/Age9B11", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Age", 30, 600, 2, "exp2/extExp8", "exp2/extExp12",
				"week7statistics2/Age8B12", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Age", 30, 600, 2, "exp2/extExp10", "exp2/extExp12",
				"week7statistics2/Age10B12", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Age", 30, 600, 2, "exp2/extExp11", "exp2/extExp12",
				"week7statistics2/Age11B12", tStats, tValues);
	}
	
	/**
	 * For memory size 3, produce data for graphs and perform statistical tests.
	 */
	private static void generateVisualAndStatData3() {
		//Ma1 visual files
		Scenario.ma1(3, "exp3/extExp13", "week7statistics3/vis1Exp13");
		Scenario.ma1(3, "exp3/extExp14", "week7statistics3/vis1Exp14");
		Scenario.ma1(3, "exp3/extExp15", "week7statistics3/vis1Exp15");
		Scenario.ma1(3, "exp3/extExp16", "week7statistics3/vis1Exp16");
		Scenario.ma1(3, "exp3/extExp17", "week7statistics3/vis1Exp17");
		Scenario.ma1(3, "exp3/extExp18", "week7statistics3/vis1Exp18");
		Scenario.ma1(3, "exp3/extExp19", "week7statistics3/vis1Exp19");
		Scenario.ma1(3, "exp3/extExp20", "week7statistics3/vis1Exp20");
		
		//Ma3 visual file + data for statistical test
		Scenario.ma3("exp3/extExp13", "week7statistics3/vis3Exp13");
		Scenario.ma3("exp3/extExp14", "week7statistics3/vis3Exp14");
		Scenario.ma3("exp3/extExp15", "week7statistics3/vis3Exp15");
		Scenario.ma3("exp3/extExp16", "week7statistics3/vis3Exp16");
		Scenario.ma3("exp3/extExp17", "week7statistics3/vis3Exp17");
		Scenario.ma3("exp3/extExp18", "week7statistics3/vis3Exp18");
		Scenario.ma3("exp3/extExp19", "week7statistics3/vis3Exp19");
		Scenario.ma3("exp3/extExp20", "week7statistics3/vis3Exp20");
		
		//Statistical tests: CC games (W decrease expected, LD nothing expected)
		ArrayList<Double> tStats = new ArrayList<Double>();
		ArrayList<Double> tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("CC", 30, 600, 3, "exp3/extExp14", "exp3/extExp13",
				"week7statistics3/CC14B13", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("CC", 30, 600, 3, "exp3/extExp15", "exp3/extExp13",
				"week7statistics3/CC15B13", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("CC", 30, 600, 3, "exp3/extExp17", "exp3/extExp13",
				"week7statistics3/CC17B13", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("CC", 30, 600, 3, "exp3/extExp16", "exp3/extExp14",
				"week7statistics3/CC16B14", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("CC", 30, 600, 3, "exp3/extExp18", "exp3/extExp14",
				"week7statistics3/CC18B14", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("CC", 30, 600, 3, "exp3/extExp16", "exp3/extExp15",
				"week7statistics3/CC16B15", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("CC", 30, 600, 3, "exp3/extExp19", "exp3/extExp15",
				"week7statistics3/CC19B15", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("CC", 30, 600, 3, "exp3/extExp18", "exp3/extExp17",
				"week7statistics3/CC18B17", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("CC", 30, 600, 3, "exp3/extExp19", "exp3/extExp17",
				"week7statistics3/CC19B17", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("CC", 30, 600, 3, "exp3/extExp20", "exp3/extExp16",
				"week7statistics3/CC20B16", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("CC", 30, 600, 3, "exp3/extExp20", "exp3/extExp18",
				"week7statistics3/CC20B18", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("CC", 30, 600, 3, "exp3/extExp20", "exp3/extExp19",
				"week7statistics3/CC20B19", tStats, tValues);
		
		//Statistical tests: Average payoffs (W decrease expected, LD nothing expected)
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Average", 30, 600, 3, "exp3/extExp14", "exp3/extExp13",
				"week7statistics3/Average14B13", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Average", 30, 600, 3, "exp3/extExp15", "exp3/extExp13",
				"week7statistics3/Average15B13", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Average", 30, 600, 3, "exp3/extExp17", "exp3/extExp13",
				"week7statistics3/Average17B13", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Average", 30, 600, 3, "exp3/extExp16", "exp3/extExp14",
				"week7statistics3/Average16B14", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Average", 30, 600, 3, "exp3/extExp18", "exp3/extExp14",
				"week7statistics3/Average18B14", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Average", 30, 600, 3, "exp3/extExp16", "exp3/extExp15",
				"week7statistics3/Average16B15", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Average", 30, 600, 3, "exp3/extExp19", "exp3/extExp15",
				"week7statistics3/Average19B15", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Average", 30, 600, 3, "exp3/extExp18", "exp3/extExp17",
				"week7statistics3/Average18B17", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Average", 30, 600, 3, "exp3/extExp19", "exp3/extExp17",
				"week7statistics3/Average19B17", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Average", 30, 600, 3, "exp3/extExp20", "exp3/extExp16",
				"week7statistics3/Average20B16", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Average", 30, 600, 3, "exp3/extExp20", "exp3/extExp18",
				"week7statistics3/Average20B18", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Average", 30, 600, 3, "exp3/extExp20", "exp3/extExp19",
				"week7statistics3/Average20B19", tStats, tValues);
		
		//Statistical tests: Metric (LWD, increase expected)
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("CC", 30, 1, 3, "week7statistics3/vis3Exp13", "week7statistics3/vis3Exp14",
				"week7statistics3/Metric13B14", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("CC", 30, 1, 3, "week7statistics3/vis3Exp13", "week7statistics3/vis3Exp15",
				"week7statistics3/Metric13B15", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("CC", 30, 1, 3, "week7statistics3/vis3Exp13", "week7statistics3/vis3Exp17",
				"week7statistics3/Metric13B17", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("CC", 30, 1, 3, "week7statistics3/vis3Exp14", "week7statistics3/vis3Exp16",
				"week7statistics3/Metric14B16", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("CC", 30, 1, 3, "week7statistics3/vis3Exp14", "week7statistics3/vis3Exp18",
				"week7statistics3/Metric14B18", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("CC", 30, 1, 3, "week7statistics3/vis3Exp15", "week7statistics3/vis3Exp16",
				"week7statistics3/Metric15B16", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("CC", 30, 1, 3, "week7statistics3/vis3Exp15", "week7statistics3/vis3Exp19",
				"week7statistics3/Metric15B19", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("CC", 30, 1, 3, "week7statistics3/vis3Exp17", "week7statistics3/vis3Exp18",
				"week7statistics3/Metric17B18", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("CC", 30, 1, 3, "week7statistics3/vis3Exp17", "week7statistics3/vis3Exp19",
				"week7statistics3/Metric17B19", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("CC", 30, 1, 3, "week7statistics3/vis3Exp16", "week7statistics3/vis3Exp20",
				"week7statistics3/Metric16B20", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("CC", 30, 1, 3, "week7statistics3/vis3Exp18", "week7statistics3/vis3Exp20",
				"week7statistics3/Metric18B20", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("CC", 30, 1, 3, "week7statistics3/vis3Exp19", "week7statistics3/vis3Exp20",
				"week7statistics3/Metric19B20", tStats, tValues);
		
		//Statistical test: age
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Age", 30, 600, 3, "exp3/extExp13", "exp3/extExp14",
				"week7statistics3/Age13B14", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Age", 30, 600, 3, "exp3/extExp13", "exp3/extExp15",
				"week7statistics3/Age13B15", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Age", 30, 600, 3, "exp3/extExp13", "exp3/extExp17",
				"week7statistics3/Age13B17", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Age", 30, 600, 3, "exp3/extExp13", "exp3/extExp16",
				"week7statistics3/Age13B16", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Age", 30, 600, 3, "exp3/extExp13", "exp3/extExp18",
				"week7statistics3/Age13B18", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Age", 30, 600, 3, "exp3/extExp13", "exp3/extExp19",
				"week7statistics3/Age13B19", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Age", 30, 600, 3, "exp3/extExp13", "exp3/extExp20",
				"week7statistics3/Age13B20", tStats, tValues);
		
			//Higher
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Age", 30, 600, 3, "exp3/extExp14", "exp3/extExp16",
				"week7statistics3/Age14B16", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Age", 30, 600, 3, "exp3/extExp14", "exp3/extExp18",
				"week7statistics3/Age14B18", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Age", 30, 600, 3, "exp3/extExp15", "exp3/extExp16",
				"week7statistics3/Age15B16", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Age", 30, 600, 3, "exp3/extExp15", "exp3/extExp19",
				"week7statistics3/Age15B19", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Age", 30, 600, 3, "exp3/extExp17", "exp3/extExp18",
				"week7statistics3/Age17B18", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Age", 30, 600, 3, "exp3/extExp17", "exp3/extExp19",
				"week7statistics3/Age17B19", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Age", 30, 600, 3, "exp3/extExp16", "exp3/extExp20",
				"week7statistics3/Age16B20", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Age", 30, 600, 3, "exp3/extExp18", "exp3/extExp20",
				"week7statistics3/Age18B20", tStats, tValues);
		
		tStats = new ArrayList<Double>();
		tValues = new ArrayList<Double>();
		Scenario.statisticalTest1("Age", 30, 600, 3, "exp3/extExp19", "exp3/extExp20",
				"week7statistics3/Age19B20", tStats, tValues);
	}

	/**
	 * Perform simulations and store results in files.
	 * Currently only has experiments of memory size 3 (commented out).
	 */
	private static void simulateExperiments() {
//		Scenario.scenario1(3, 0.00, 0.00, 0.00, "exp3/anaExp13", "exp3/extExp13");
//		System.out.println("Finished simulating exp 13");
//		
//		Scenario.scenario1(3, 0.00, 0.00, 0.05, "exp3/anaExp14", "exp3/extExp14");
//		System.out.println("Finished simulating exp 14");
//		
//		Scenario.scenario1(3, 0.00, 0.05, 0.00, "exp3/anaExp15", "exp3/extExp15");
//		System.out.println("Finished simulating exp 15");
//		
//		Scenario.scenario1(3, 0.00, 0.05, 0.05, "exp3/anaExp16", "exp3/extExp16");
//		System.out.println("Finished simulating exp 16");
//		
//		Scenario.scenario1(3, 0.05, 0.00, 0.00, "exp3/anaExp17", "exp3/extExp17");
//		System.out.println("Finished simulating exp 17");
//		
//		Scenario.scenario1(3, 0.05, 0.00, 0.05, "exp3/anaExp18", "exp3/extExp18");
//		System.out.println("Finished simulating exp 18");
//		
//		Scenario.scenario1(3, 0.05, 0.05, 0.00, "exp3/anaExp19", "exp3/extExp19");
//		System.out.println("Finished simulating exp 19");
//		
//		Scenario.scenario1(3, 0.05, 0.05, 0.05, "exp3/anaExp20", "exp3/extExp20");
//		System.out.println("Finished simulating exp 20");
	}

	/**
	 * Used to test how small things work, please ignore.
	 */
	private static void testStuff() {

//		//Generate random int in range
//		for (int i = 0; i < 100; i++) {
//			System.out.println(random.nextInt(3));
//		}
		
		int[][] testStrat1 = {
				{2, 3, 4},
				{5, 6, 7}
		};
		
		int[][] testStrat2 = {
				{200, 300, 400},
				{500, 600, 700}
		};
		int[][][] strategyStorage = new int[2][2][3];
		strategyStorage[0] = testStrat1;
		strategyStorage[1] = testStrat2;
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < 2; j++) {
				for(int k = 0; k < 3; k++) {
					System.out.print(strategyStorage[i][j][k] + " ");
				}
			}
			System.out.println();
		}
	}
}