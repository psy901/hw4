package myProj;

import myProj.util.AnalysisAggregator;
import myProj.util.AnalysisRunner;
import myProj.util.BasicRewardFunction;
import myProj.util.BasicTerminalFunction;
import myProj.util.MapPrinter;
import burlap.oomdp.core.Domain;
import burlap.oomdp.core.TerminalFunction;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.RewardFunction;
import burlap.oomdp.singleagent.environment.SimulatedEnvironment;
import burlap.oomdp.singleagent.explorer.VisualExplorer;
import burlap.oomdp.visualizer.Visualizer;

public class EasyGridWorldLauncher {
    //These are some boolean variables that affect what will actually get executed
    private static boolean visualizeInitialGridWorld = true; //Loads a GUI with the agent, walls, and goal

    //runValueIteration, runPolicyIteration, and runQLearning indicate which algorithms will run in the experiment
    private static boolean runValueIteration = true;
    private static boolean runPolicyIteration = true;
    private static boolean runQLearning = true;

    //showValueIterationPolicyMap, showPolicyIterationPolicyMap, and showQLearningPolicyMap will open a GUI
    //you can use to visualize the policy maps. Consider only having one variable set to true at a time
    //since the pop-up window does not indicate what algorithm was used to generate the map.
    private static boolean showValueIterationPolicyMap = true;
    private static boolean showPolicyIterationPolicyMap = true;
    private static boolean showQLearningPolicyMap = true;

    private static Integer MAX_ITERATIONS = 100;
    private static Integer NUM_INTERVALS = 100;

    protected static int[][] userMap = new int[][]{
            {0, 0, 0, 0, 0},
            {0, 1, 1, 0, 0},
            {0, 1, 1, 0, 0},
            {0, 1, 1, 0, 0},
            {0, 0, 0, 0, 0},};

//	private static Integer mapLen = map.length-1;

    public static void main(String[] args) {
        // convert to BURLAP indexing
        int[][] map = MapPrinter.mapToMatrix(userMap);
        int maxX = map.length - 1;
        int maxY = map[0].length - 1;
        //

        BasicGridWorld gen = new BasicGridWorld(map, maxX, maxY); //0 index map is 11X11
        Domain domain = gen.generateDomain();

        State initialState = BasicGridWorld.getExampleState(domain);

        RewardFunction rf = new BasicRewardFunction(map.length, map[0].length, -1.0); //Goal is at the top right grid
        ((BasicRewardFunction) rf).addReward(maxX, maxY, 100);
        ((BasicRewardFunction) rf).addReward(maxX, maxY-1, -100);

        TerminalFunction tf = new BasicTerminalFunction(maxX, maxY, maxX, maxY-1); //Goal is at the top right grid

        SimulatedEnvironment env = new SimulatedEnvironment(domain, rf, tf,
                initialState);
        //Print the map that is being analyzed
        System.out.println("/////Easy Grid World Analysis/////\n");
        MapPrinter.printMap(MapPrinter.matrixToMap(map));

        if (visualizeInitialGridWorld) {
            visualizeInitialGridWorld(domain, gen, env);
        }

        AnalysisRunner runner = new AnalysisRunner(MAX_ITERATIONS, NUM_INTERVALS);
        long startTime, endTime, totalTime;

        if (runValueIteration) {
            startTime = System.nanoTime();
            runner.runValueIteration(gen, domain, initialState, rf, tf, showValueIterationPolicyMap);
            endTime = System.nanoTime();
            totalTime = (endTime - startTime) / 1000000;
            System.out.println("Time (ms) for Value Iteration: " + totalTime);
        }
        if (runPolicyIteration) {
            startTime = System.nanoTime(); 
            runner.runPolicyIteration(gen, domain, initialState, rf, tf, showPolicyIterationPolicyMap);
            endTime = System.nanoTime();
            totalTime = (endTime - startTime) / 1000000;
            System.out.println("Time (ms) for Policy Iteration: " + totalTime);
        }
        if (runQLearning) {
            startTime = System.nanoTime(); 
            runner.runQLearning(gen, domain, initialState, rf, tf, env, showQLearningPolicyMap);
            endTime = System.nanoTime();
            totalTime = (endTime - startTime) / 1000000;
            System.out.println("Time (ms) for Q Learning: " + totalTime);
        }
        AnalysisAggregator.printAggregateAnalysis();
    }


    private static void visualizeInitialGridWorld(Domain domain,
                                                  BasicGridWorld gen, SimulatedEnvironment env) {
        Visualizer v = gen.getVisualizer();
        VisualExplorer exp = new VisualExplorer(domain, env, v);

        exp.addKeyAction("w", BasicGridWorld.ACTIONNORTH);
        exp.addKeyAction("s", BasicGridWorld.ACTIONSOUTH);
        exp.addKeyAction("d", BasicGridWorld.ACTIONEAST);
        exp.addKeyAction("a", BasicGridWorld.ACTIONWEST);

        exp.setTitle("Easy Grid World");
        exp.initGUI();

    }


}
