package adapters;

import aima.search.framework.GoalTest;
import state.State;

public class CentralGoalTest implements GoalTest {
    public boolean isGoalState(Object aState) {
        State state = (State) aState;
        return false;
    }
}
