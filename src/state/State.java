package state;

import data.Client;
import data.ContractType;
import data.Plant;
import world.World;

import java.util.List;

public class State
{
    public State()
    {

    }

    public boolean generateInitialState()
    {
        return true;
    }

    public void initializeNodes()
    {
        // Try to assign each guaranteed client with a plant
        for (final Plant plant: World.getPlants())
        {
            for (final Client client: World.getClients())
            {
                if (!plant.canBeConnectedTo(client)) { break; }

                if (client.getContract() == ContractType.GUARANTEED)
                {
                    client.connectTo(plant);
                }
            }
        }

        clientPlantIDList = World.save();
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

    private List<Short> clientPlantIDList;
    private List<Float> plantEvalFunctionVal;
}
