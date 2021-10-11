import java.util.ArrayList;
import java.util.HashSet;

public class SpaceSearcher
{
    /**Priority Queue p acting as an open set*/
    private MinPQ p;
    private HashSet<Bridge> closedSet;

    SpaceSearcher()
    {
        this.p = null;
        this.closedSet = null;
    }

    //A* Algorithm Implementation with Closed Set
    public Bridge AstarClosedSet(Bridge initialState)
    {
        this.p = new MinPQ(new IntegerComparator());
        this.closedSet = new HashSet<>();
        this.p.add(initialState);
        while(this.p.peek()!=null)
        {
            /*Remove from the priority queue, the state with the smallest total score (MovingTime + HeuristicTime)*/
            Bridge currentState = this.p.getMin();
            if(currentState.isTerminal())
            {
                return currentState;
            }

            /*Check to see if the closed set contains the state
            * if not, create all the states that derive from it
            * add them to "states"
            * add them to Minimum Priority Queue "p" and add every state
            * The PQ has the minimum state at the top, based on total score (MovingTime + HeuristicTime) */
            if(!closedSet.contains(currentState))
            {
                this.closedSet.add(currentState);
                ArrayList<Bridge> states = new ArrayList<>();
                states.addAll(currentState.getChild());
                p = new MinPQ(new IntegerComparator());
                for (Bridge state : states) {
                    p.add(state);
                }
            }
        }
        return null;
    }
}