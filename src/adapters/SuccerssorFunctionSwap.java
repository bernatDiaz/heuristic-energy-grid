package adapters;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
import data.Client;
import state.State;
import world.World;

import java.util.ArrayList;
import java.util.List;

public class SuccerssorFunctionSwap implements SuccessorFunction {
    public List getSuccessors(Object aState) {
        ArrayList retVal = new ArrayList();
        State state = (State)aState;
        State stateNew;
        for(int i = 0; i < World.getClients().size() - 1; ++i){
            for(int j = i + 1; j < World.getClients().size(); ++j){
                if(state.swapAvailible(i, j)) {
                    stateNew = state.clone();
                    stateNew.swapClients(i, j);
                    retVal.add(new Successor(new String("swap " + i + " " + j),stateNew));
                }
            }
        }
        return retVal;
    }
}