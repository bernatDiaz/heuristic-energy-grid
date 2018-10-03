package utils;

import IA.Energia.Central;
import IA.Energia.Cliente;
import IA.Energia.VEnergia;

public class Utils
{
    public static double distance(Central central, Cliente cliente)
    {
        double coordX = central.getCoordX() - cliente.getCoordX();
        double coordY = central.getCoordY() - cliente.getCoordY();

        double coordX2 = coordX * coordX;
        double coordY2 = coordY * coordY;

        return Math.sqrt(coordX2 + coordY2);
    }

    public static double distance(Cliente cliente, Central central)
    {
        return distance(central, cliente);
    }

    public static double distanceFactor(Cliente cliente, Central central)
    {
        double dist = distance(cliente, central);

        return VEnergia.getPerdida(dist);
    }

    public static double distanceFactor(Central central, Cliente cliente)
    {
        return distanceFactor(cliente, central);
    }

    public static double demandByDistance(Cliente cliente, Central central)
    {
        return cliente.getConsumo() * distanceFactor(cliente, central);
    }

    public static double demandByDistance(Central central, Cliente cliente)
    {
        return demandByDistance(cliente, central);
    }
}
