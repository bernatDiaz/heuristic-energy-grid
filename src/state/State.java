package state;

import IA.Energia.Central;
import IA.Energia.Cliente;

import utils.Pair;
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
        int c = 0;
        Nodo node;
        Cliente cliente;
        nodes = new ArrayList<>();
        unassigned = new ArrayList<>();

        // Try to assign each guaranteed client with a plant
        for (int p = 0; p < World.getPlants().size(); ++p)
        {
            node = new Nodo();
            Central plant = World.getPlants().get(p);
            node.setPlant(plant);

            while (c < World.getNumClients() && node.hasAvailableEnergy() && node.posibleClient(World.getClient(c)))
            {
                if (World.getClient(c).getTipo() == Cliente.GARANTIZADO)
                {
                    node.addClient(World.getClient(c));
                }
                else
                {
                    unassigned.add(World.getClient(c));
                }
                ++c;
            }
            nodes.add(node);
        }

        // In case we've run out of plants but there are still clients to assign
        for (int r = c; r < World.getNumClients(); ++r)
        {
            unassigned.add(World.getClient(r));
        }
    }

    public int check()
    {
        int count = 0;
        Cliente cliente;

        for(int i = 0; i < World.getNumClients(); ++i)
        {
            cliente = World.getClient(i);

            for(Nodo node: nodes)
            {
                if(node.hasClient(cliente))
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
        for(Cliente client : unassigned)
        {
            if(client.getTipo() == Cliente.GARANTIZADO)
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

    private ArrayList<Cliente> unassigned;
    private ArrayList<Nodo> nodes;

    private List<Integer> clientPlantIDs;
}
