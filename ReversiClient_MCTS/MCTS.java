import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * Board Index Representation:
 * 56 57 58 59 60 61 62 63      7
 * 48 49 50 51 52 53 54 55      6
 * 40 41 42 43 44 45 46 47      5
 * 32 33 34 35 36 37 38 39      4
 * 24 25 26 27 28 29 30 31      3
 * 16 17 18 19 20 21 22 23      2
 * 8  9  10 11 12 13 14 15      1
 * 0  1  2  3  4  5  6  7       0
 *                              ^-- rows
 * 0  1  2  3  4  5  6  7  <------- cols
 */
public class MCTS {
    private static final int NUM_ITERATIONS = 100000;
    private static final double c = 1.41; // exploration parameter: sqrt(2)
    private State rootState;
    private int me; // 1|2, the player's number
    
    public MCTS() {
    }

    public int computeBestMove(final int[][] grid, final int round, final int me) {
        this.me = me;
        rootState = new State(grid, round, me);
        for (int i = 0; i < NUM_ITERATIONS; i++) {
            mcts(rootState);
        }

        return extractBestMove(rootState); // TODO
    }

    /**
     * Includes the steps (1) Select, (2) Expand, (3) Simulate, (4) Back Up
     * 
     * @return [numWins, numSimulations]
     */
    private int[] mcts(State state) {
        if (state.numValidMoves == 0) {
            // game over state reached: use actual score instead of simulation score
            System.out.println("Child state reached!!!!!!!!!"); // TODO deleteme
            int numWins = Util.didWin(state.grid, me) ? 1 : 0;
            int numGames = 1;
            int[] results = new int[] { numWins, numGames };
            state.addResults(results);
            return results; // TODO FIXME am I doing this right
        } else if (state.childStates.size() > 0) {
            // child states already expanded: recurse further down the tree
            assert (state.numValidMoves == state.childStates.size());
            State bestChild = state.getBestChild();
            int[] childResults = mcts(bestChild);
            state.addResults(childResults);
            return childResults;
        } else {
            // child states not yet expanded: expand children
            int[] validMoves = new int[64];
            int numValidMoves = Util.getValidMoves(state.round, state.grid, state.nextTurn, validMoves);
            assert (numValidMoves == state.numValidMoves); // DELETEME
            for (int i = 0; i < state.numValidMoves; i++) {
                int childNextPlayer = Util.changeTurns(state.nextTurn);
                State child = new State(Util.createChildGrid(state.grid, state.nextTurn, validMoves[i]), state.round + 1, childNextPlayer);
                // TODO start here
            }
        }
    

        // TODO update t: every simulation n will reach root, so make sure that you update it when you simulate

        // TODO
        return new int[] {2, 2};
    }

    private class State {
        private final int[][] grid;
        private final int round;
        private final int nextTurn;
        private final int numValidMoves;
        private List<State> childStates;
        private int w;
        private int n;

        public State(int[][] grid, int round, int nextTurn) {
            this.grid = grid;
            this.round = round;
            this.nextTurn = nextTurn;
            this.numValidMoves = Util.getValidMoves(round, grid, nextTurn, new int[64]); // FIXME, is it referencing the wrong player
            this.childStates = new ArrayList<State>();
            this.w = simulateResult();
            this.n = 1;
        }

        public void addResults(int[] results) {
            this.w += results[0];
            this.n += results[1];
        }

        /**
         * Gets the child state with highest UCB.
         */
        public State getBestChild() {
            assert (childStates.size() > 0);
            Comparator<State> ucbComparator = Comparator.comparingDouble(State::upperConfidenceBound);
            return childStates.stream().max(ucbComparator).get();
        }

        /**
         * Computes the upper confidence bound for the current node. This represents the priority
         * for visiting, which is compared relative to the UCB of sibling states.
         *
         * UCB = w_i/n_i + c * sqrt(ln(t)/n_i)
         * c: The exploration parameter. As this grows, the priority of choosing this term grows 
         * if it is not yet well explored.
         * w: number of wins for the current node
         * n: number of simulations for the current node
         * t: total number of simulations reaching the root node
         */
        private double upperConfidenceBound() {
            double exploitationTerm = ((double) w / (double) n);
            double explorationTerm = c * Math.sqrt(Math.log((double) rootState.n) / (double) n);
            return exploitationTerm + explorationTerm;
        }

        /**
         * @return 0 for player loss, 1 for win
         */
        private int simulateResult() {
            Random random = new Random();
            int[][] sGrid = grid;
            int sRound = round;
            int sTurn = nextTurn;
            int[] sValidMoves = new int[64];
            int sNumValidMoves = Util.getValidMoves(sRound, sGrid, sTurn, sValidMoves);
            
            // simulate to an ending state
            while (numValidMoves > 0) {
                // simulate one state
                int move = random.nextInt(sNumValidMoves);
                sGrid = Util.createChildGrid(sGrid, sTurn, move);
                sRound += 1;
                sTurn = Util.changeTurns(sTurn);
                sNumValidMoves = Util.getValidMoves(sRound, sGrid, sTurn, sValidMoves);
            }

            return Util.didWin(sGrid, me) ? 1 : 0;
        }
    }

    private int extractBestMove(State state) {
        assert (state.numValidMoves > 0 && state.childStates.size() > 0);
        // TODO


        // DELETEME (This is a State method)
        // // get child state with highest UCB
        // double maxUCB = 0.0;
        // int indexMaxUCBChild = 0;
        // for (int i = 0; i < state.childStates.size(); i++) {
        //     State child = state.childStates.get(i);
        //     double ucb = upperConfidenceBound(child.w, child.n);
        //     if (ucb > maxUCB) { // TODO ensure this always grabs a state
        //         maxUCB = ucb;
        //         indexMaxUCBChild = i;
        //     }
        // }
    }
}
