import IA.Marenostrum.LocalSearchGoalTest;
import adapters.CentralGoalTest;
import adapters.HeuristicFunctionEntropia;
import adapters.SuccerssorFunctionSwap;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import state.State;
import world.World;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        World.randomInitialize();
        State state = new State();
        state.initializeNodesB();
        Problem problem = new Problem(state, new SuccerssorFunctionSwap(),
                new CentralGoalTest(), new HeuristicFunctionEntropia());
        Search alg = new HillClimbingSearch();
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
}
