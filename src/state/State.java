package state;

import IA.Energia.Central;
import IA.Energia.Cliente;
import utils.Pair;

import java.util.ArrayList;

public class State
{
    public State()
    {

    }

    public boolean generateInitialState()
    {
        return true;
    }

    private ArrayList<Pair<Central, Cliente>> conections;
}
