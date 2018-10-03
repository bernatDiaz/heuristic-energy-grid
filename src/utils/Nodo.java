package utils;

import IA.Energia.Central;
import IA.Energia.Cliente;
import data.Client;
import data.ContractType;
import data.Plant;
import world.World;

import java.util.ArrayList;

import static utils.Utils.distanceFactor;

public class Nodo
{
    private double remaining;
    private Plant plant;
    private ArrayList<Client> clients;

    public Nodo()
    {

    }

    public Plant getCentral()
    {
        return plant;
    }

    public Client getClient(int i)
    {
        return clients.get(i);
    }

    public void setPlant(Plant central)
    {
        plant = central;
        clients = new ArrayList<>();
        remaining = plant.getProduction();
    }

    public boolean hasAvailableEnergy()
    {
        return true;
    }

    public boolean posibleClient(Client client)
    {
        double request = Utils.demandByDistance(client, plant);

        return request <= remaining;
    }

    public void addClient(Client client)
    {
        double request = Utils.demandByDistance(client, plant);

        clients.add(client);
        remaining -= request;
    }

    public boolean hasClient(Client client)
    {
        return clients.contains(client);
    }

    public void printInfo()
    {
        System.out.println(remaining);
        System.out.println(plant.getProduction());
        System.out.println(clients.size());
    }

    public int numberClients()
    {
        return clients.size();
    }

    public int checkGuaranted()
    {
        int count = 0;

        for (final Client client: clients)
        {
            if (client.getContract() == ContractType.GUARANTEED)
            {
                count++;
            }
        }

        return count;
    }
}
