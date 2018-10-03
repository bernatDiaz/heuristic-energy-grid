package data;

import IA.Energia.Cliente;

public enum ContractType
{
    GUARANTEED(Cliente.GARANTIZADO),
    NOT_GUARANTEED(Cliente.NOGARANTIZADO);

    private ContractType(int type)
    {
        this.type = type;
    }

    public int asInt()
    {
        return type;
    }

    public static ContractType asEnum(int intType)
    {
        ContractType type;

        switch (intType)
        {
            case Cliente.GARANTIZADO:
                type = GUARANTEED;
                break;
            case Cliente.NOGARANTIZADO:
                type = NOT_GUARANTEED;
                break;
            default:
                throw new IllegalArgumentException("Illegal value for type");
        }

        return type;
    }

    private int type;
}
