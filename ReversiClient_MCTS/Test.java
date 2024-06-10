public class Test {
    public static void main(String[] args) {
        int[][] arr = {{0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,1,2,0,0,0},
            {0,0,0,2,1,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0}};

        MCTS mcts = new MCTS();
        System.out.println("Best move: " + mcts.computeBestMove(arr, 4, 1));
        // mcts.printAllStates();
    }

    private static void printGrid(int[][] grid) {
        for (int i = grid.length - 1; i >= 0; i--) {
            for (int j = 0; j < grid[0].length; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println("");
        }
    }

    // Test method within MCTS.java
    /*
    public void test() {
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

    public void printAllStates() {
        printStateTree(rootState, 0);
    }

    public static void printStateTree(State state, int level) {
        printState(state, level);
        for (int i = 0; i < state.childStates.size(); i++) {
            printStateTree(state.childStates.get(i), level + 1);
        }
    }

    public static void printState(State state, int level) {
        System.out.println("State at level: " + level);
        printGrid(state.grid);
        System.out.println("   Score: " + state.w + "/" + state.n);
        System.out.println("   ExpandedChildren: " + state.childStates.size() + "/" + state.numValidMoves);
        System.out.println("\n\n");
    }
    */
}
