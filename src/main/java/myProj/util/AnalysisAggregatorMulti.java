package myProj.util;

import java.util.ArrayList;
import java.util.List;

public final class AnalysisAggregatorMulti {
	private static List<Integer> numIterations = new ArrayList<Integer>();
	private static List<List<Integer>> stepsToFinishLearning = new ArrayList<List<Integer>>();
	private static List<List<Integer>> millisecondsToFinishLearning = new ArrayList<List<Integer>>();
	private static List<List<Double>> rewardsForLearning = new ArrayList<List<Double>>();
	private static List<String> nameLearningSession = new ArrayList<String>();

	public static int addLearningSession(String name)
	{
		stepsToFinishLearning.add(new ArrayList<Integer>());
		millisecondsToFinishLearning.add(new ArrayList<Integer>());
		rewardsForLearning.add(new ArrayList<Double>());
		nameLearningSession.add(name);
		return nameLearningSession.size() - 1;
	}

	public static void addNumberOfIterations(Integer numIterations1){
		numIterations.add(numIterations1);
	}
	public static void addStepsToFinishLearning(Integer stepsToFinishLearning1, Integer index){
		stepsToFinishLearning.get(index).add(stepsToFinishLearning1);
	}
	public static void printLearningResults(Integer index){
		System.out.print(nameLearningSession.get(index) + " Steps,");
		printList(stepsToFinishLearning.get(index));
	}

	public static void addMillisecondsToFinishLearning(Integer millisecondsToFinishLearning1, Integer index){
		millisecondsToFinishLearning.get(index).add(millisecondsToFinishLearning1);
	}

	public static void addLearningReward(double reward, Integer index) {
		rewardsForLearning.get(index).add(reward);
	}

	public static void printLearningTimeResults(Integer index){
		System.out.print(nameLearningSession.get(index) + " Time,");
		printList(millisecondsToFinishLearning.get(index));
	}

	public static void printLearningRewards(Integer index){
		System.out.print(nameLearningSession.get(index) + " Rewards,");
		printDoubleList(rewardsForLearning.get(index));
	}

	public static void printNumIterations(){
		System.out.print("Iterations,");	
		printList(numIterations);
	}

	private static void printList(List<Integer> valueList){
		int counter = 0;
		for(int value : valueList){
			System.out.print(String.valueOf(value));
			if(counter != valueList.size()-1){
				System.out.print(",");
			}
			counter++;
		}
		System.out.println();
	}
	private static void printDoubleList(List<Double> valueList){
		int counter = 0;
		for(double value : valueList){
			System.out.print(String.valueOf(value));
			if(counter != valueList.size()-1){
				System.out.print(",");
			}
			counter++;
		}
		System.out.println();
	}

	public static void printAggregateAnalysis(){
		System.out.println("//Aggregate Analysis//\n");
		System.out.println("The data below shows the number of steps/actions the agent required to reach \n"
				+ "the terminal state given the number of iterations the algorithm was run.");
		printNumIterations();
		for (int i=0; i < nameLearningSession.size(); i++)
		{
			printLearningResults(i);
		}
		System.out.println();
		System.out.println("The data below shows the number of milliseconds the algorithm required to generate \n"
				+ "the optimal policy given the number of iterations the algorithm was run.");
		printNumIterations();
		for (int i=0; i < nameLearningSession.size(); i++)
		{
			printLearningTimeResults(i);
		}

		System.out.println("\nThe data below shows the total reward gained for \n"
				+ "the optimal policy given the number of iterations the algorithm was run.");
		printNumIterations();
		for (int i=0; i < nameLearningSession.size(); i++)
		{
			printLearningRewards(i);
		}
	}
}
