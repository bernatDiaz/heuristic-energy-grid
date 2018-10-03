import state.State;
import world.World;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        World.randomInitialize();
        State state = new State();
        state.initializeNodes();
        state.checkOnlyGuaranted();
        state.checkAllGuaranted();
        World.printClients();
        World.printPlants();
    }
}
