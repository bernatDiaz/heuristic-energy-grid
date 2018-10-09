package world;

import IA.Energia.Central;
import IA.Energia.Centrales;
import IA.Energia.Cliente;
import IA.Energia.Clientes;
import data.Client;
import data.Plant;
import utils.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World
{
    private World()
    {

    }

    public static void load(List<Short> clientPlantIDList)
    {
        for (short i = 0; i < clientPlantIDList.size(); ++i)
        {
            Plant plant = plants.get(clientPlantIDList.get(i));

            clients.get(i).connectTo(plant);
        }
    }

    public static Pair<short[], float[]> save()
    {
        short [] stateClients = new short[clients.size()];
        for (int c = 0; c < clients.size(); c++)
        {
            Client client = clients.get(c);
            stateClients[c] = client.getPlantId();
        }

        float [] statePlants = new float[plants.size()];
        for(int p = 0; p < plants.size(); p++){
            Plant plant = plants.get(p);
            statePlants[p] = plant.getAvailableEnergy();
        }
        return new Pair<>(stateClients, statePlants);
    }

    public static ArrayList<Client> getClients()
    {
        return clients;
    }

    public static ArrayList<Plant> getPlants()
    {
        return plants;
    }

    public static void randomInitialize() throws Exception
    {
        boolean foundError;
        ArrayList<Cliente> clients = new ArrayList<>();
        ArrayList<Central> plants = generatePlants();

        do
        {
            try
            {
                clients = generateClients();
                foundError = false;
            }
            catch (Exception e)
            {
                foundError = true;
            }
        }
        while (foundError);

        if(World.clients!=null) World.clients.clear();
        else World.clients = new ArrayList<>();
        for (short i = 0; i < clients.size(); ++i)
        {
            World.clients.add(new Client(i, clients.get(i)));
        }

        if(World.plants!=null) World.plants.clear();
        else World.plants = new ArrayList<>();
        for (short i = 0; i < plants.size(); ++i)
        {
            World.plants.add(new Plant(i, plants.get(i)));
        }
    }

    private static ArrayList<Central> generatePlants() throws Exception
    {
        Random rand = new Random();

        float propCentrales1 = rand.nextFloat();
        float propCentrales2 = rand.nextFloat();
        float propCentrales3 = rand.nextFloat();
        float propCentralesTotal = propCentrales1 + propCentrales2 + propCentrales3;

        propCentrales1 /= propCentralesTotal;
        propCentrales2 /= propCentralesTotal;
        propCentrales3 /= propCentralesTotal;

        int nCentrales [] = new int[3];
        int nTotalCentrales = rand.nextInt(maxPlants - minPlants + 1) + minPlants;

        nCentrales[0] = Math.round(nTotalCentrales * propCentrales1);
        nCentrales[1] = Math.round(nTotalCentrales * propCentrales2);
        nCentrales[2] = Math.round(nTotalCentrales * propCentrales3);

        return new Centrales(nCentrales, rand.nextInt());
    }

    private static ArrayList<Cliente> generateClients() throws Exception
    {
        Random rand = new Random();
        double propClientes [] = new double [3];

        propClientes[0] = rand.nextDouble();
        propClientes[1] = rand.nextDouble();
        propClientes[2] = rand.nextDouble();

        double propClientesTotal = propClientes[0] + propClientes[1] + propClientes[2];

        propClientes[0] /= propClientesTotal;
        propClientes[1] /= propClientesTotal;
        propClientes[2] = 1.0 - propClientes[0] - propClientes[1];  // This assures they sum exactly 1

        int nClientes = rand.nextInt(maxClients - minClients + 1) + minClients;
        double propGarantizado = rand.nextDouble();

        return new Clientes(nClientes, propClientes, propGarantizado, rand.nextInt());
    }

    public static void printClients()
    {
        for(Client client : clients)
            System.out.println(client.getDemand());
    }

    public static void printPlants()
    {
        for(Plant plant : plants) {
            System.out.println(plant.getProduction());
            System.out.println(plant.getAvailableEnergy());
            System.out.println();
        }
    }

    public static void printAll(){
        printPlants();
        System.out.println();
        printClients();
    }

    static private int minPlants  = 5;
    static private int maxPlants  = 7;
    static private int minClients = 400;
    static private int maxClients = 500;

    static private ArrayList<Plant>  plants;
    static private ArrayList<Client> clients;
}
