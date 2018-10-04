package data;

import IA.Energia.Central;

public enum PlantType
{
    A(Central.CENTRALA),
    B(Central.CENTRALB),
    C(Central.CENTRALC);

    private PlantType(int type)
    {
        this.type = (byte)type;
    }

    public byte asInt()
    {
        return type;
    }

    public static PlantType asEnum(int intType)
    {
        PlantType type;

        switch (intType)
        {
            case Central.CENTRALA:
                type = A;
                break;
            case Central.CENTRALB:
                type = B;
                break;
            case Central.CENTRALC:
                type = C;
                break;
            default:
                throw new IllegalArgumentException("Illegal value for intType");
        }

        return type;
    }

    private byte type;
}
