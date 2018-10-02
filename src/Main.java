import state.State;
import world.World;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        World.randomInitialize();
        System.out.println(World.clients.size());
        System.out.println(World.plants.size());
    }
}
