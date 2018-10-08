package adapters;

import IA.Energia.Central;
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
import data.Plant;
import state.State;
import world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SuccessorFunctionOpenSA implements SuccessorFunction {
    public List getSuccessors(Object aState) {
        ArrayList retVal = new ArrayList();
        State state = (State) aState;
        State stateNew;
        Random random = new Random();
        Plant central;
        int centralId;
        do {
            centralId = random.nextInt(World.getClients().size() + 1);
            central = World.getPlants().get(centralId);
        }
        while (!central.isClosed(state.getAvailableEnergy(centralId)));
        int clientId = random.nextInt(World.getClients().size() + 1);
        stateNew = state.clone();
        stateNew.changeClient(clientId, centralId);
        retVal.add(new Successor(new String("open " + centralId + "insert " + clientId),stateNew));
        return retVal;
    }
}
