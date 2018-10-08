package adapters;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
import state.State;
import world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SuccessorFunctionSwapSA implements SuccessorFunction {
    public List getSuccessors(Object aState) {
        ArrayList retVal = new ArrayList();
        State state = (State) aState;
        Random random = new Random();
        int i, j;
        do {
            i = random.nextInt(World.getClients().size());
            do {
                j = random.nextInt(World.getClients().size());
            }
            while (i == j);
        }
        while(!state.swapAvailible(i, j));
        State stateNew = state.clone();
        stateNew.swapClients(i, j);
        retVal.add(new Successor(new String("swap " + i + " " + j),stateNew));
        return retVal;
    }
}
