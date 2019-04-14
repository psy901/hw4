package myProj.util;

import myProj.BasicGridWorld;
import burlap.oomdp.core.TerminalFunction;
import burlap.oomdp.core.objects.ObjectInstance;
import burlap.oomdp.core.states.State;

public class BasicTerminalFunction implements TerminalFunction {

	int goalX;
	int goalY;
	int trapX;
	int trapY;

	public BasicTerminalFunction(int goalX, int goalY, int trapX, int trapY) {
		this.goalX = goalX;
		this.goalY = goalY;
		this.trapX = trapX;
		this.trapY = trapY;
	}

	public boolean isTerminal(State s) {

		// get location of agent in next state
		ObjectInstance agent = s.getFirstObjectOfClass(BasicGridWorld.CLASSAGENT);
		int ax = agent.getIntValForAttribute(BasicGridWorld.ATTX);
		int ay = agent.getIntValForAttribute(BasicGridWorld.ATTY);

		// are they at goal location?
		if ((ax == this.goalX && ay == this.goalY) || (ax == this.trapX && ay == this.trapY)){
			return true;
		}

		return false;
	}

}
