package world;

import IA.Energia.Central;
import IA.Energia.Centrales;
import IA.Energia.Cliente;
import IA.Energia.Clientes;
import state.State;

import java.util.ArrayList;

public class World
{
    private World()
    {

    }

    public static void randomInitialize() throws Exception
    {
        plants  = generatePlants();
        clients = generateClients();
    }

    private static ArrayList<Central> generatePlants() throws Exception
    {
        int[] cent = {0, 0, 0};
        int seed = 0;

        return new Centrales(cent, seed);
    }

    private static ArrayList<Cliente> generateClients() throws Exception
    {
        int ncl = 0;
        double[] propc = {0, 0, 0};
        double propg = 0;
        int seed = 0;

        return new Clientes(ncl, propc, propg, seed);
    }

    static private ArrayList<Central> plants;
    static private ArrayList<Cliente> clients;
}
