package utils;

import IA.Energia.Central;
import IA.Energia.Cliente;
import IA.Energia.VEnergia;
import data.ClientType;
import data.ContractType;

public class Utils
{
    public static float getUnitaryCost(final ContractType contract, final ClientType type) throws Exception
    {
        float cost;

        switch (contract)
        {
            case GUARANTEED:
                cost = (float)VEnergia.getTarifaClienteGarantizada(type.asInt());
                break;
            case NOT_GUARANTEED:
                cost = (float)VEnergia.getTarifaClienteNoGarantizada(type.asInt());
                break;
            default:
                throw new IllegalArgumentException("Invalid value for contract");
        }

        return cost;
    }
}
