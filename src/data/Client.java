package data;

import IA.Energia.VEnergia;
import utils.Utils;

public final class Client
{
    // CONSTRUCTORS
    public Client(final IA.Energia.Cliente client)
    {
        coordX   = client.getCoordX();
        coordY   = client.getCoordY();
        demand   = client.getConsumo();
        contract = ContractType.asEnum(client.getContrato());
        type     = ClientType.asEnum(client.getTipo());
    }

    // GETTERS
    public int getCoordX()
    {
        return coordX;
    }

    public int getCoordY()
    {
        return coordY;
    }

    public double getDemand()
    {
        return demand;
    }

    public ContractType getContract()
    {
        return contract;
    }

    public ClientType getType()
    {
        return type;
    }

    public double distance(final Plant plant)
    {
        return Math.sqrt(Math.pow(plant.getCoordX() - coordX, 2) + Math.pow(plant.getCoordY() - coordY, 2));
    }

    public double getPaidPrice() throws Exception
    {
        double price;

        if (isSupplied())
        {
            price = getConsumptionPrice();
        }
        else
        {
            price = -getIndemnizationCost();
        }

        return price;
    }

    public double getConsumptionPrice() throws Exception
    {
        return demand * Utils.getUnitaryCost(contract, type);
    }

    public double getIndemnizationCost() throws Exception
    {
        if (contract == ContractType.GUARANTEED) { throw new RuntimeException("A guaranteed contract type must always be served"); }

        return demand * VEnergia.getTarifaClientePenalizacion(type.asInt());
    }

    // OTHER METHODS
    public boolean isGuaranteed()
    {
        return contract == ContractType.GUARANTEED;
    }

    public boolean isSupplied()
    {
        return plant != null;
    }

    public double getRealDemand(final Plant plant)
    {
        return demand * (VEnergia.getPerdida(distance(plant)) + 1);
    }

    public boolean canBeConnectedTo(final Plant plant)
    {
        return plant.canBeConnectedTo(this);
    }

    public void connectTo(final Plant plant)
    {
        if (plant != null) { throw new RuntimeException("Client is already connected to plant"); }

        plant.connectTo(this);
        this.plant = plant;
    }

    public void disconnectFromPlant()
    {
        if (plant == null) { throw new RuntimeException("Client was not connected to any plant"); }

        plant.disconnectFrom(this);
        plant = null;
    }

    private final int    coordX;
    private final int    coordY;
    private final double demand;
    private final ContractType contract;
    private final ClientType   type;

    private Plant plant = null;
}
