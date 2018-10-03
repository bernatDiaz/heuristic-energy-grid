package data;

import IA.Energia.Cliente;

public enum ClientType
{
    XG(Cliente.CLIENTEXG),
    MG(Cliente.CLIENTEMG),
    G(Cliente.CLIENTEG);

    private ClientType(int type)
    {
        this.type = type;
    }

    public int asInt()
    {
        return type;
    }

    public static ClientType asEnum(int intType)
    {
        ClientType type;

        switch (intType)
        {
            case Cliente.CLIENTEXG:
                type = XG;
                break;
            case Cliente.CLIENTEMG:
                type = MG;
                break;
            case Cliente.CLIENTEG:
                type = G;
                break;
            default:
                throw new IllegalArgumentException("Illegal value for type");
        }

        return type;
    }

    private int type;
}
