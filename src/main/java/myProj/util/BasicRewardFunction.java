package myProj.util;

import myProj.BasicGridWorld;
import burlap.oomdp.core.objects.ObjectInstance;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.GroundedAction;
import burlap.oomdp.singleagent.RewardFunction;

import java.util.Arrays;

public class BasicRewardFunction implements RewardFunction {

	double[][] rewardMap;

	public BasicRewardFunction(int maxX, int maxY, double defaultR) {
		this.rewardMap = new double[maxX][maxY];
		for (double[] e : this.rewardMap ) {
			Arrays.fill(e, defaultR);
		}
	}

	public void addReward(int x, int y, double r)
	{
		this.rewardMap[x][y] = r;
	}

	public double reward(State s, GroundedAction a, State sprime) {

		// get location of agent in next state
		ObjectInstance agent = sprime.getFirstObjectOfClass(BasicGridWorld.CLASSAGENT);
		int ax = agent.getIntValForAttribute(BasicGridWorld.ATTX);
		int ay = agent.getIntValForAttribute(BasicGridWorld.ATTY);

		return this.rewardMap[ax][ay];
	}

}
