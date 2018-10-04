package data;

import IA.Energia.VEnergia;
import utils.Utils;

public final class Client
{
    // CONSTRUCTORS
    public Client(final IA.Energia.Cliente client)
    {
        coordX   = (byte)client.getCoordX();
        coordY   = (byte)client.getCoordY();
        demand   = (byte)client.getConsumo();
        contract = ContractType.asEnum(client.getContrato());
        type     = ClientType.asEnum(client.getTipo());
    }

    // GETTERS
    public byte getCoordX()
    {
        return coordX;
    }

    public byte getCoordY()
    {
        return coordY;
    }

    public float getDemand()
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

    public Plant getPlant()
    {
        return plant;
    }

    public float distance(final Plant plant)
    {
        return (float)Math.sqrt(Math.pow(plant.getCoordX() - coordX, 2) + Math.pow(plant.getCoordY() - coordY, 2));
    }

    public float getPaidPrice() throws Exception
    {
        float price;

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

    public float getConsumptionPrice() throws Exception
    {
        return demand * Utils.getUnitaryCost(contract, type);
    }

    public float getIndemnizationCost() throws Exception
    {
        if (contract == ContractType.GUARANTEED) { throw new RuntimeException("A guaranteed contract type must always be served"); }

        return demand * (float)VEnergia.getTarifaClientePenalizacion(type.asInt());
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

    public float getRealDemand(final Plant plant)
    {
        return demand * ((float)VEnergia.getPerdida(distance(plant)) + 1);
    }

    public boolean canBeConnectedTo(final Plant plant)
    {
        return plant.canBeConnectedTo(this);
    }

    public void connectTo(final Plant plant)
    {
        if (this.plant != null) { throw new RuntimeException("Client is already connected to plant"); }

        plant.connectTo(this);
        this.plant = plant;
    }

    public void disconnectFromPlant()
    {
        if (plant == null) { throw new RuntimeException("Client was not connected to any plant"); }

        plant.disconnectFrom(this);
        plant = null;
    }

    private final byte  coordX;
    private final byte  coordY;
    private final float demand;
    private final ContractType contract;
    private final ClientType   type;

    private Plant plant = null;
}
