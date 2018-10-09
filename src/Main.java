import IA.Marenostrum.LocalSearchGoalTest;
import adapters.*;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.framework.Successor;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;
import state.State;
import world.World;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class Main
{
    static int MAX_ITERATIONS_SA = 2000;
    static int ITERATIONS_HEAT = 100;
    static int K = 10;
    static double LLAMBDA = 10.0;

    public static void main(String[] args) throws Exception
    {
        hillClimbing();
    }

    private static void hillClimbing() throws Exception{
        World.randomInitialize();
        State state = new State();
        state.initializeNodesB();
        Problem problem = new Problem(state, new SuccessorFunctionSwapHC(),
                new CentralGoalTest(), new HeuristicFunctionEntropia());
        Search alg = new HillClimbingSearch();
        SearchAgent agent = new SearchAgent(problem, alg);
        printActions(agent.getActions());
        printInstrumentation(agent.getInstrumentation());
    }

    private static void simulatedAnnealing() throws Exception{
        World.randomInitialize();
        State state = new State();
        state.initializeNodesB();
        Problem problem = new Problem(state, new SuccessorFunctionSwapSA(),
                new CentralGoalTest(), new HeuristicFunctionEntropia());
        Search alg = new SimulatedAnnealingSearch(MAX_ITERATIONS_SA, ITERATIONS_HEAT, K, LLAMBDA);
        SearchAgent agent = new SearchAgent(problem, alg);
        printActions(agent.getActions());
        printInstrumentation(agent.getInstrumentation());
    }

    private static void printInstrumentation(Properties properties) {
        Iterator keys = properties.keySet().iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            String property = properties.getProperty(key);
            System.out.println(key + " : " + property);
        }

    }

    private static void printActions(List actions) {
        for (int i = 0; i < actions.size(); i++) {
            String action = (String) actions.get(i);
            System.out.println(action);
        }
    }

    private static void TestSwap() throws Exception{
        int client1 = 1;
        int client2 = 400;
        World.randomInitialize();
        State state = new State();
        state.initializeNodesB();
        state.printPlants();
        state.printClient(client1);
        state.printClient(client2);
        state.swapClients(client1, client2);
        state.printPlants();
    }

    private static void TestOpen() throws Exception{
        int central = 4;
        int client = 0;
        World.randomInitialize();
        State state = new State();
        state.initializeNodesB();
        state.printPlants();
        state.printClient(client);
        state.changeClient(client, central);
        state.printPlants();
    }
}
