package myProj.util;

import burlap.behavior.policy.BoltzmannQPolicy;
import burlap.behavior.policy.EpsilonGreedy;
import burlap.behavior.policy.Policy;
import burlap.behavior.policy.RandomPolicy;
import burlap.behavior.singleagent.EpisodeAnalysis;
import burlap.behavior.singleagent.learning.tdmethods.QLearning;
import burlap.behavior.valuefunction.ValueFunction;
import burlap.oomdp.core.Domain;
import burlap.oomdp.core.TerminalFunction;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.RewardFunction;
import burlap.oomdp.singleagent.environment.SimulatedEnvironment;
import myProj.BasicGridWorld;

public class AnalysisRunnerQTuning<increment> extends AnalysisRunner {

    public AnalysisRunnerQTuning(int MAX_ITERATIONS, int NUM_INTERVALS) {
        super(MAX_ITERATIONS, NUM_INTERVALS);

        int increment = MAX_ITERATIONS/NUM_INTERVALS;

        for (int numIterations = increment; numIterations <= MAX_ITERATIONS; numIterations += increment){
            AnalysisAggregatorMulti.addNumberOfIterations(numIterations);

        }
    }


    public void runQLearningEpsilonGreedy(BasicGridWorld gen, Domain domain,
                                          State initialState, RewardFunction rf, TerminalFunction tf,
                                          SimulatedEnvironment env, boolean showPolicyMap, double epsilon) {
        String name = "Epsilon E: " + epsilon;
        System.out.println("//Q Learning (" + name + ") Analysis//");
        Integer index = AnalysisAggregatorMulti.addLearningSession(name);

        QLearning agent = null;
        Policy p = null;
        EpisodeAnalysis ea = null;
        int increment = MAX_ITERATIONS / NUM_INTERVALS;
        for (int numIterations = increment; numIterations <= MAX_ITERATIONS; numIterations += increment) {
            long startTime = System.nanoTime();

            agent = new QLearning(
                    domain,
                    0.99,
                    hashingFactory,
                    0.99, 0.99);
            EpsilonGreedy policy = new EpsilonGreedy(agent, epsilon);
            agent.setLearningPolicy(policy);

            for (int i = 0; i < numIterations; i++) {
                ea = agent.runLearningEpisode(env);
                env.resetEnvironment();
            }
            agent.initializeForPlanning(rf, tf, 1);
            p = agent.planFromState(initialState);
            AnalysisAggregatorMulti.addLearningReward(calcRewardInEpisode(ea), index);
            AnalysisAggregatorMulti.addMillisecondsToFinishLearning(
                    (int) (System.nanoTime() - startTime) / 1000000, index);
            AnalysisAggregatorMulti.addStepsToFinishLearning(ea.numTimeSteps(), index);

        }
        AnalysisAggregatorMulti.printLearningResults(index);
        MapPrinter.printPolicyMap(getAllStates(domain, rf, tf, initialState), p, gen.getMap());
        System.out.println("\n\n");

        //visualize the value function and policy.
        if (showPolicyMap) {
            simpleValueFunctionVis((ValueFunction) agent, p, initialState, domain, hashingFactory, "Q-Learning");
        }

    }

    public void runQLearningBoltzmann(BasicGridWorld gen, Domain domain,
                                      State initialState, RewardFunction rf, TerminalFunction tf,
                                      SimulatedEnvironment env, boolean showPolicyMap, double temperature) {
        String name = "Boltzmann T: " + temperature;
        System.out.println("//Q Learning (" + name + ") Analysis//");
        Integer index = AnalysisAggregatorMulti.addLearningSession(name);

        QLearning agent = null;
        Policy p = null;
        EpisodeAnalysis ea = null;
        int increment = MAX_ITERATIONS / NUM_INTERVALS;
        for (int numIterations = increment; numIterations <= MAX_ITERATIONS; numIterations += increment) {
            long startTime = System.nanoTime();

            agent = new QLearning(
                    domain,
                    0.99,
                    hashingFactory,
                    0.99, 0.99);
            BoltzmannQPolicy policy = new BoltzmannQPolicy(agent, temperature);
            agent.setLearningPolicy(policy);

            for (int i = 0; i < numIterations; i++) {
                ea = agent.runLearningEpisode(env);
                env.resetEnvironment();
            }
            agent.initializeForPlanning(rf, tf, 1);
            p = agent.planFromState(initialState);
            AnalysisAggregatorMulti.addLearningReward(calcRewardInEpisode(ea), index);
            AnalysisAggregatorMulti.addMillisecondsToFinishLearning(
                    (int) (System.nanoTime() - startTime) / 1000000, index);
            AnalysisAggregatorMulti.addStepsToFinishLearning(ea.numTimeSteps(), index);

        }
        AnalysisAggregatorMulti.printLearningResults(index);
        MapPrinter.printPolicyMap(getAllStates(domain, rf, tf, initialState), p, gen.getMap());
        System.out.println("\n\n");

        //visualize the value function and policy.
        if (showPolicyMap) {
            simpleValueFunctionVis((ValueFunction) agent, p, initialState, domain, hashingFactory, "Q-Learning");
        }

    }

    public void runQLearningRandom(BasicGridWorld gen, Domain domain,
                                   State initialState, RewardFunction rf, TerminalFunction tf,
                                   SimulatedEnvironment env, boolean showPolicyMap) {
        String name = "Random";
        System.out.println("//Q Learning (" + name + ") Analysis//");
        Integer index = AnalysisAggregatorMulti.addLearningSession(name);

        QLearning agent = null;
        Policy p = null;
        EpisodeAnalysis ea = null;
        int increment = MAX_ITERATIONS / NUM_INTERVALS;
        for (int numIterations = increment; numIterations <= MAX_ITERATIONS; numIterations += increment) {
            long startTime = System.nanoTime();

            agent = new QLearning(
                    domain,
                    0.99,
                    hashingFactory,
                    0.99, 0.99);
            RandomPolicy policy = new RandomPolicy(domain);
            agent.setLearningPolicy(policy);

            for (int i = 0; i < numIterations; i++) {
                ea = agent.runLearningEpisode(env);
                env.resetEnvironment();
            }
            agent.initializeForPlanning(rf, tf, 1);
            p = agent.planFromState(initialState);
            AnalysisAggregatorMulti.addLearningReward(calcRewardInEpisode(ea), index);
            AnalysisAggregatorMulti.addMillisecondsToFinishLearning(
                    (int) (System.nanoTime() - startTime) / 1000000, index);
            AnalysisAggregatorMulti.addStepsToFinishLearning(ea.numTimeSteps(), index);

        }
        AnalysisAggregatorMulti.printLearningResults(index);
        MapPrinter.printPolicyMap(getAllStates(domain, rf, tf, initialState), p, gen.getMap());
        System.out.println("\n\n");

        //visualize the value function and policy.
        if (showPolicyMap) {
            simpleValueFunctionVis((ValueFunction) agent, p, initialState, domain, hashingFactory, "Q-Learning");
        }

    }
}
