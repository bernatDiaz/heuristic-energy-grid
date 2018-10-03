package state;

import data.Client;
import data.ContractType;
import data.Plant;
import utils.Nodo;
import world.World;

import java.util.ArrayList;
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

        clientPlantList = World.save();
    }

    public int check()
    {
        int count = 0;
        Client cliente;

        for(Client client: World.getClients())
        {
            for(Nodo node: nodes)
            {
                if(node.hasClient(client))
                {
                    ++count;
                }
            }
        }

        return count;
    }

    public void checkOnlyGuaranted()
    {
        int garantizados = 0;
        int total = 0;
        for(Nodo node: nodes)
        {
            garantizados += node.checkGuaranted();
            total += node.numberClients();
        }

        System.out.println("Num clientes total en nodes");
        System.out.println(total);
        System.out.println("Num clientes garantizado en nodes");
        System.out.println(garantizados);
    }

    public void checkAllGuaranted()
    {
        int garantizados = 0;
        int noGarantizados = 0;
        for(Client client : unassigned)
        {
            if(client.getContract() == ContractType.GUARANTEED)
            {
                garantizados++;
            }
            else
            {
                noGarantizados++;
            }
        }

        System.out.println("Num clientes garantizado en unassigned");
        System.out.println(garantizados);
        System.out.println("Num clientes no garantizado en unassigned");
        System.out.println(noGarantizados);
    }

    public int checkSize()
    {
        System.out.println(checkNodesSize());
        System.out.println(checkUnasignedSize());

        return checkNodesSize() + checkUnasignedSize();
    }

    private int checkNodesSize()
    {
        int suma = 0;

        for(Nodo node : nodes)
        {
            suma += node.numberClients();
        }

        return suma;
    }

    private int checkUnasignedSize()
    {
        return unassigned.size();
    }

    private ArrayList<Client> unassigned;
    private ArrayList<Nodo> nodes;

    private List<Plant> clientPlantList;
}
