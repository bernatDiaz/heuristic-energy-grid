package state;

import IA.Energia.Central;
import IA.Energia.Cliente;

import utils.Pair;
import utils.Nodo;
import world.World;

import java.util.ArrayList;

public class State
{
    public State()
    {

    }

    public boolean generateInitialState()
    {
        return true;
    }

    public void initializeNodes(){
        nodes = new ArrayList<>();
        unasigned = new ArrayList<>();
        int c = 0;
        Nodo node;
        Cliente cliente;
        for(int p = 0; p < World.getNumPlants(); ++p) {
            node = new Nodo();
            Central plant = World.getPlant(p);
            node.setPlant(plant);
            while (c < World.getNumClients() && node.posibleClient(World.getClient(c))) {
                if ((World.getClient(c)).getTipo() == Cliente.GARANTIZADO) node.addClient((World.getClient(c)));
                else unasigned.add((World.getClient(c)));
                c++;
            }
            nodes.add(node);
        }
        for(int r = c; r < World.getNumClients(); ++r) unasigned.add(World.getClient(r));
    }

    public int check(){
        int count = 0;
        Cliente cliente;
        for(int i = 0; i < World.getNumClients(); ++i){
            cliente = World.getClient(i);
            for(Nodo node: nodes){
                if(node.hasClient(cliente)) ++count;
            }
        }
        return count;
    }

    public void checkOnlyGuaranted(){
        int garantizados = 0;
        int total = 0;
        for(Nodo node : nodes){
            garantizados += node.checkGuaranted();
            total += node.numberClients();
        }
        System.out.println("Num clientes total en nodes");
        System.out.println(total);
        System.out.println("Num clientes garantizado en nodes");
        System.out.println(garantizados);
    }

    public void checkAllGuaranted(){
        int garantizados = 0;
        int noGarantizados = 0;
        for(Cliente client : unasigned){
            if(client.getTipo() == Cliente.GARANTIZADO) {
                garantizados++;
            }
            else noGarantizados++;
        }
        System.out.println("Num clientes garantizado en unasigned");
        System.out.println(garantizados);
        System.out.println("Num clientes no garantizado en unasigned");
        System.out.println(noGarantizados);
    }

    public int checkSize(){
        System.out.println(checkNodesSize());
        System.out.println(checkUnasignedSize());
        return checkNodesSize() + checkUnasignedSize();
    }

    private int checkNodesSize(){
        int suma = 0;
        for(Nodo node : nodes){
            suma += node.numberClients();
        }
        return suma;
    }

    private int checkUnasignedSize(){
        return unasigned.size();
    }
    private ArrayList<Cliente> unasigned;
    private ArrayList<Nodo> nodes;
    private ArrayList<Pair<Central, Cliente>> conections;
}
