package adapters;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
import data.Plant;
import state.State;
import world.World;

import java.util.ArrayList;
import java.util.List;

public class SuccessorFunctionOpen implements SuccessorFunction {
    public List getSuccessors(Object aState) {
        ArrayList retVal = new ArrayList();
        State state = (State)aState;
        State stateNew;
        for(int i = 0; i < World.getPlants().size(); ++i){
            Plant central = World.getPlants().get(i);
            if(central.isClosed(state.getAvailableEnergy(i))){
                for(int j = 0; j < World.getClients().size(); ++j){
                    stateNew = state.clone();
                    stateNew.changeClient(j, i);
                    retVal.add(new Successor(new String("open " + i + "insert " + j),stateNew));
                }
            }
        }
        return retVal;
    }
}
