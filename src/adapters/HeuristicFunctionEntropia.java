package adapters;

import aima.search.framework.HeuristicFunction;
import data.Plant;
import state.State;
import world.World;

public class HeuristicFunctionEntropia implements HeuristicFunction {
    public double getHeuristicValue(Object n) {
        State state = (State)n;
        float value = 0.0f;
        Plant central;
        for(int i = 0; i < state.getNCentrals(); ++i){
            central = World.getPlants().get(i);
            float total = central.getProduction();
            float percent = state.getAvailableEnergy(i) / total;
            value -= percent * Math.log(percent);
        }
        return value;
    }
}
