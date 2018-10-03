package world;

import IA.Energia.Central;
import IA.Energia.Centrales;
import IA.Energia.Cliente;
import IA.Energia.Clientes;
import state.State;
import java.util.ArrayList;
import java.util.Random;

public class World
{

    static private int minCentrales = 5;
    static private int maxCentrales = 10;
    static private int minClientes = 500;
    static private int maxClientes = 600;

    private World()
    {

    }

    public static void randomInitialize() throws Exception
    {
        plants  = generatePlants();
        boolean foundError = false;
        while(!foundError) {
            try {
                clients = generateClients();
            } catch (java.lang.Exception e) {
                foundError = true;
            }
        }
    }

    public static int getNumPlants(){return plants.size();}

    public static int getNumClients(){return clients.size();}

    public static Central getPlant(int i){return plants.get(i);}

    public static Cliente getClient(int i){return clients.get(i);}

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
        int nTotalCentrales = rand.nextInt(maxCentrales - minCentrales + 1) + minCentrales;
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
        propClientes[2] = 1.0 - propClientes[0] - propClientes[1];

        int nClientes = rand.nextInt(maxClientes - minClientes + 1) + minClientes;
        double propGarantizado = rand.nextDouble();
        return new Clientes(nClientes, propClientes, propGarantizado, rand.nextInt());
    }

    public static void printClients(){
        System.out.println("Num clientes total en world");
        System.out.println(clients.size());
    }

    public static void printPlants(){
        System.out.println("Num centrales total en world");
        System.out.println(plants.size());
    }

    static private ArrayList<Central> plants;
    static private ArrayList<Cliente> clients;
}
