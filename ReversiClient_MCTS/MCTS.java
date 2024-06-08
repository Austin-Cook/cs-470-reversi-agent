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
    private static final int NUM_ITERATIONS = 1000;
    private static final double c = 1.41; // exploration parameter: sqrt(2)
    private static final int W_INDEX = 0;
    private static final int N_INDEX = 1;
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
     * @return [numWins, numPlayouts]
     * Note: A playout can be either a simulation, or the game for a terminal state
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
            // child states not yet expanded: expand all children
            int[] validMoves = new int[64];
            int numValidMoves = Util.getValidMoves(state.round, state.grid, state.nextTurn, validMoves);
            assert (numValidMoves == state.numValidMoves); // DELETEME
            int[] childrenResults = new int[] {0, 0};
            for (int i = 0; i < state.numValidMoves; i++) {
                int childNextPlayer = Util.changeTurns(state.nextTurn);
                State child = new State(Util.createChildGrid(state.grid, state.nextTurn, validMoves[i]), state.round + 1, childNextPlayer);
                childrenResults[W_INDEX] += child.w;
                childrenResults[N_INDEX] += child.n;
                state.childStates.add(child);
            }
            state.addResults(childrenResults);
            return childrenResults;
        }
    }

    private class State { // TODO make private again
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
            while (sNumValidMoves > 0) {
                // simulate one state
                int move = sValidMoves[random.nextInt(sNumValidMoves)];
                sGrid = Util.createChildGrid(sGrid, sTurn, move);
                sRound += 1;
                sTurn = Util.changeTurns(sTurn);
                sNumValidMoves = Util.getValidMoves(sRound, sGrid, sTurn, sValidMoves);
            }
            return Util.didWin(sGrid, me) ? 1 : 0;
        }
    }

    /**
     * Returns the move that creates the best child state from the parent state.
     */
    private int extractBestMove(State state) {
        assert (state.numValidMoves > 0 && state.childStates.size() > 0);
        State bestChild = state.getBestChild();

        for (int row = 0; row < state.grid.length; row++) {
            for (int col = 0; col < state.grid[0].length; col++) {
                if (state.grid[row][col] != bestChild.grid[row][col]) {
                    return (8 * row) + col;
                }
            }
        }

        System.out.println("ERROR, unable to extractBestMove()");
        return -1;
    }

    public void test() { // DELETEMe
        int[][] arr = {{0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,1,2,0,0,0},
            {0,0,0,2,1,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0}};
        me = 2;
        
        System.out.println("Beginning test");
        State state = new State(arr, 4, 1);

    }

    private static void printGrid(int[][] grid) { // DELETEME!!!
        for (int i = grid.length - 1; i >= 0; i--) {
            for (int j = 0; j < grid[0].length; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println("");
        }
    }
}
