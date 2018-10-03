package data;

import IA.Energia.VEnergia;

import java.util.HashSet;
import java.util.Set;

public class Plant
{
    public Plant(final IA.Energia.Central plant)
    {
        coordX     = plant.getCoordX();
        coordY     = plant.getCoordY();
        production = plant.getProduccion();
        type       = PlantType.asEnum(plant.getTipo());

        availableEnergy = production;
    }

    public int getCoordX()
    {
        return coordX;
    }

    public int getCoordY()
    {
        return coordY;
    }

    public double getProduction()
    {
        return production;
    }

    public PlantType getType()
    {
        return type;
    }

    public double getBenefits() throws Exception
    {
        double benefit = -getPlantCost();

        for (final Client client: clients)
        {
            benefit += client.getPaidPrice();
        }

        return benefit;
    }

    public double getPlantCost() throws Exception
    {
        double cost;

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

    public double getRunningCost() throws Exception
    {
        return production * VEnergia.getCosteProduccionMW(type.asInt()) + VEnergia.getCosteMarcha(type.asInt());
    }

    public double getStoppedCost() throws Exception
    {
        return VEnergia.getCosteParada(type.asInt());
    }

    public double distance(final Client client)
    {
        return Math.sqrt(Math.pow(client.getCoordX() - coordX, 2) + Math.pow(client.getCoordY() - coordY, 2));
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

    private final int    coordX;
    private final int    coordY;
    private final double production;
    private final PlantType type;

    private double availableEnergy;
    private Set<Client> clients = new HashSet<>();
}
