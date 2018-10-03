package utils;

import IA.Energia.Central;
import IA.Energia.Cliente;
import java.util.ArrayList;

import static utils.Utils.distanceUse;

public class Nodo {
    private double remaining;
    private Central plant;
    private ArrayList<Cliente> clients;

    public Central getCentral(){return plant;}
    public Cliente getClient(int i){return clients.get(i);}
    public Nodo(){ }
    public void setPlant(Central central){
        plant = central;
        clients = new ArrayList<>();
        remaining = plant.getProduccion();
    }
    public boolean posibleClient(Cliente client){
        double request = client.getConsumo() * distanceUse(client, plant);
        return request <= remaining;
    }
    public void addClient(Cliente client){
        double request = client.getConsumo() * distanceUse(client, plant);
        clients.add(client);
        remaining -= request;
    }
    public boolean hasClient(Cliente client){
        return clients.contains(client);
    }
    public void printInfo(){
        System.out.println(remaining);
        System.out.println(plant.getProduccion());
        System.out.println(clients.size());
    }
    public int numberClients(){
        return clients.size();
    }
    public int checkGuaranted(){
        int count = 0;
        for(Cliente client : clients){
            if(client.getTipo() == Cliente.GARANTIZADO) count++;
        }
        return count;
    }
}
