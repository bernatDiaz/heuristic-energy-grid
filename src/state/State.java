package state;

import IA.Energia.Central;
import data.Client;
import data.ContractType;
import data.Plant;
import utils.Pair;
import world.World;

import java.util.List;

public class State implements Cloneable
{
    public State()
    {

    }

    public boolean generateInitialState()
    {
        return true;
    }

    public void initializeNodesB() {
        int c = 0;
        // Try to assign each guaranteed client with a plant
        for (Plant plant : World.getPlants()) {
            while (c < World.getClients().size() && plant.canBeConnectedTo(World.getClients().get(c))) {
                if (World.getClients().get(c).getContract() == ContractType.GUARANTEED) {
                    World.getClients().get(c).connectTo(plant);
                }
                c++;
            }
        }

        state = World.save();
    }

    public int check()
    {
        int count = 0;
        Client cliente;

        for(Client client: World.getClients())
        {
        }

        return count;
    }

    public void checkOnlyGuaranted()
    {
        int garantizados = 0;
        int total = 0;

        System.out.println("Num clientes total en nodes");
        System.out.println(total);
        System.out.println("Num clientes garantizado en nodes");
        System.out.println(garantizados);
    }

    public void checkAllGuaranted()
    {
        int garantizados = 0;
        int noGarantizados = 0;

        System.out.println("Num clientes garantizado en unassigned");
        System.out.println(garantizados);
        System.out.println("Num clientes no garantizado en unassigned");
        System.out.println(noGarantizados);
    }

    public short checkSize()
    {
        System.out.println(checkNodesSize());
        System.out.println(checkUnasignedSize());

        return (short)(checkNodesSize() + checkUnasignedSize());
    }

    private short checkNodesSize()
    {
        return 0;
    }

    private short checkUnasignedSize()
    {
        return 0;
    }

    public int getNCentrals(){
        return state.second().length;
    }

    public float getAvailableEnergy(int i){
        return state.second()[i];
    }

    public boolean swapAvailible(int client1, int client2){
        short central1 = state.first()[client1];
        short central2 = state.first()[client2];

        if(central1 == -1 || central2 == -1) return false;

        float availableEnergy1 = state.second()[central1];
        float availableEnergy2 = state.second()[central2];

        float demand1 = World.getClients().get(client1).getDemand();
        float demand2 = World.getClients().get(client2).getDemand();

        boolean posible1 = availableEnergy1 + demand1 - demand2 >= 0.0f;
        boolean posible2 = availableEnergy2 + demand2 - demand1 >= 0.0f;

        return posible1 && posible2;
    }

    public void swapClients(int client1, int client2){
        short central1 = state.first()[client1];
        float availableEnergy1 = state.second()[central1];
        float demand1 = World.getClients().get(client1).getDemand();

        short central2 = state.first()[client2];
        float availableEnergy2 = state.second()[central2];
        float demand2 = World.getClients().get(client2).getDemand();

        state.first()[client1] = central2;
        state.first()[client2] = central1;
        state.second()[central1] = availableEnergy1 + demand1 - demand2;
        state.second()[central2] = availableEnergy2 + demand2 - demand1;
    }

    public void changeClient(int client, int central){
        float demand = World.getClients().get(client).getDemand();
        state.second()[central] = state.second()[central] + demand;
        short origin = state.first()[client];
        if(origin != -1){
            state.second()[origin] = state.second()[origin] - demand;
        }
        state.first()[client] = (short)central;
    }

    public State clone(){
        try {
            State stateNew = new State();
            short[] clients = state.first().clone();
            float[] centrals = state.second().clone();
            stateNew.state = new Pair<>(clients, centrals);
            super.clone();
            return stateNew;
        }
        catch (CloneNotSupportedException e){return null;}
    }
    Pair<short[], float[]> state;
    private List<Float> plantEvalFunctionVal;
}
