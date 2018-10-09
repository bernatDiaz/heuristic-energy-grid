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

        initializeNodesBaux(ContractType.GUARANTEED);
        initializeNodesBaux(ContractType.NOT_GUARANTEED);

        state = World.save();
    }

    private void initializeNodesBaux(ContractType contractType){
        int c = 0;
        for (Plant plant : World.getPlants()) {
            while (c < World.getClients().size() && plant.canBeConnectedTo(World.getClients().get(c))) {
                if (World.getClients().get(c).getContract() == contractType) {
                    World.getClients().get(c).connectTo(plant);
                }
                c++;
            }
        }
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

        if(central1 == -1 || central2 == -1 || central1 == central2) return false;

        Plant plant1 = World.getPlants().get(central1);
        Plant plant2 = World.getPlants().get(central2);

        float availableEnergy1 = state.second()[central1];
        float availableEnergy2 = state.second()[central2];

        float demand11 = World.getClients().get(client1).getRealDemand(plant1);
        float demand12 = World.getClients().get(client1).getRealDemand(plant2);
        float demand21 = World.getClients().get(client2).getRealDemand(plant1);
        float demand22 = World.getClients().get(client2).getRealDemand(plant2);

        boolean posible1 = availableEnergy1 + demand11 - demand21 >= 0.0f;
        boolean posible2 = availableEnergy2 + demand22 - demand12 >= 0.0f;

        return posible1 && posible2;
    }

    public void swapClients(int client1, int client2){
        short central1 = state.first()[client1];
        short central2 = state.first()[client2];

        Plant plant1 = World.getPlants().get(central1);
        Plant plant2 = World.getPlants().get(central2);

        float availableEnergy1 = state.second()[central1];
        float availableEnergy2 = state.second()[central2];

        float demand11 = World.getClients().get(client1).getRealDemand(plant1);
        float demand12 = World.getClients().get(client1).getRealDemand(plant2);
        float demand21 = World.getClients().get(client2).getRealDemand(plant1);
        float demand22 = World.getClients().get(client2).getRealDemand(plant2);

        state.first()[client1] = central2;
        state.first()[client2] = central1;
        state.second()[central1] = availableEnergy1 + demand11 - demand21;
        state.second()[central2] = availableEnergy2 + demand22 - demand12;
    }

    public void changeClient(int client, int central){
        Plant plant = World.getPlants().get(central);
        float demand = World.getClients().get(client).getRealDemand(plant);
        state.second()[central] = state.second()[central] - demand;
        short origin = state.first()[client];
        if(origin != -1){
            state.second()[origin] = state.second()[origin] + demand;
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

    public void print(){
        printClients();
        printPlants();
    }

    public void printClients(){
        System.out.println("clients");
        for(int i = 0; i < state.first().length; ++i){
            short plant = state.first()[i];
            float demand = World.getClients().get(i).getDemand();
            boolean garantizado = World.getClients().get(i).isGuaranteed();
            System.out.println(plant + " " + demand + " " + garantizado);
        }
    }

    public void printClient(int i){
        System.out.println("client " + i);
        short plant = state.first()[i];
        float demand = World.getClients().get(i).getDemand();
        boolean garantizado = World.getClients().get(i).isGuaranteed();
        System.out.println(plant + " " + demand + " " + garantizado);
    }

    public void printPlants(){
        System.out.println("plants");
        for(int i = 0; i < state.second().length; ++i){
            System.out.println(World.getPlants().get(i).getProduction());
            System.out.println(state.second()[i]);
        }
    }
    Pair<short[], float[]> state;
    private List<Float> plantEvalFunctionVal;
}
