package utils;

import IA.Energia.Central;
import IA.Energia.Cliente;
import world.World;

import java.util.ArrayList;

import static utils.Utils.distanceFactor;

public class Nodo
{
    private double remaining;
    private Central plant;
    private ArrayList<Cliente> clients;

    public Nodo()
    {

    }

    public Central getCentral()
    {
        return plant;
    }

    public Cliente getClient(int i)
    {
        return clients.get(i);
    }

    public void setPlant(Central central)
    {
        plant = central;
        clients = new ArrayList<>();
        remaining = plant.getProduccion();
    }

    public boolean hasAvailableEnergy()
    {
        return remaining >= World.getMinDemand();
    }

    public boolean posibleClient(Cliente client)
    {
        double request = Utils.demandByDistance(client, plant);

        return request <= remaining;
    }

    public void addClient(Cliente client)
    {
        double request = Utils.demandByDistance(client, plant);

        clients.add(client);
        remaining -= request;
    }

    public boolean hasClient(Cliente client)
    {
        return clients.contains(client);
    }

    public void printInfo()
    {
        System.out.println(remaining);
        System.out.println(plant.getProduccion());
        System.out.println(clients.size());
    }

    public int numberClients()
    {
        return clients.size();
    }

    public int checkGuaranted()
    {
        int count = 0;

        for (final Cliente client : clients)
        {
            if (client.getTipo() == Cliente.GARANTIZADO)
            {
                count++;
            }
        }

        return count;
    }
}
