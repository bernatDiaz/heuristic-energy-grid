package data;

import IA.Energia.VEnergia;

import java.util.HashSet;
import java.util.Set;

public class Plant
{
    public Plant(final IA.Energia.Central plant)
    {
        coordX     = (byte)plant.getCoordX();
        coordY     = (byte)plant.getCoordY();
        production = (float)plant.getProduccion();
        type       = PlantType.asEnum(plant.getTipo());

        availableEnergy = production;
    }

    public byte getCoordX()
    {
        return coordX;
    }

    public byte getCoordY()
    {
        return coordY;
    }

    public float getProduction()
    {
        return production;
    }

    public PlantType getType()
    {
        return type;
    }

    public Set<Client> getClients()
    {
        return clients;
    }

    public float getBenefits() throws Exception
    {
        float benefit = -getPlantCost();

        for (final Client client: clients)
        {
            benefit += client.getPaidPrice();
        }

        return benefit;
    }

    public float getPlantCost() throws Exception
    {
        float cost;

        if (isOn())
        {
            cost = getRunningCost();
        }
        else
        {
            cost = getStoppedCost();
        }

        return cost;
    }

    public float getRunningCost() throws Exception
    {
        return production * (float)VEnergia.getCosteProduccionMW(type.asInt()) + (float)VEnergia.getCosteMarcha(type.asInt());
    }

    public float getStoppedCost() throws Exception
    {
        return (float)VEnergia.getCosteParada(type.asInt());
    }

    public float distance(final Client client)
    {
        return (float)Math.sqrt(Math.pow(client.getCoordX() - coordX, 2) + Math.pow(client.getCoordY() - coordY, 2));
    }

    // OTHER METHODS
    public boolean isOn()
    {
        return !clients.isEmpty();
    }

    public boolean isAvailable()
    {
        return availableEnergy > 0;
    }

    public boolean canBeConnectedTo(final Client client)
    {
        return availableEnergy >= client.getRealDemand(this);
    }

    public void connectTo(final Client client)
    {
        if (!canBeConnectedTo(client)) { throw new RuntimeException("Plant does not have enough energy to satisfy the demands of the client"); }

        availableEnergy -= client.getRealDemand(this);
        clients.add(client);
    }

    public void disconnectFrom(final Client client)
    {
        boolean removed = clients.remove(client);
        if (!removed) { throw new RuntimeException("Client is not connected to plant"); }

        availableEnergy += client.getRealDemand(this);
    }

    private final byte  coordX;
    private final byte  coordY;
    private final float production;
    private final PlantType type;

    private float availableEnergy;
    private Set<Client> clients = new HashSet<>();
}
