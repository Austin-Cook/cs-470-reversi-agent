public class Minimax {
    // we only save the immediate next move
    // recursing o further depths is only for heuristics
    private int topDepth;
    // 0-63 representing chosen space to place game piece
    private int bestMove;
    
    public Minimax() {
        // do nothing
    }
    
    // 56 57 58 59 60 61 62 63
    // 48 49 50 51 52 53 54 55
    // 40 41 42 43 44 45 46 47
    // 32 33 34 35 36 37 38 39
    // 24 25 26 27 28 29 30 31
    // 16 17 18 19 20 21 22 23
    // 8  9  10 11 12 13 14 15
    // 0  1  2  3  4  5  6  7
    // returns the grid representation 0-63 of the best move
    public int computeBest(int round, int[][] state, int depth, int player) {
        assert(player == Util.MAXIMIZER || player == Util.MINIMIZER);
        bestMove = -1;
        topDepth = depth;
        
        int bestMoveScore = minimax(round, state, depth, player);
        System.out.println("Best possible score: " + bestMoveScore);
        return bestMove;
    }

    private int minimax(int round, int[][] state, int depth, int player) {
        int[] validMoves = new int[64];
        int numValidMoves = Util.getValidMoves(round, state, player, validMoves);
        
        if (depth == 0 || numValidMoves == 0) {
            return staticEvaluation(state);
        }

        return 0; // todo change

        // TODO make sure to increase round at each call
        
    }

    private int staticEvaluation(int[][] state) {
        // assert(state.length == 8 && state[0].length == 8);
        
        int val = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (state[i][j] == Util.MAXIMIZER) {
                    val += 1;
                } else if (state[i][j] == Util.MINIMIZER) {
                    val -= 2;
                }
            }
        }

        return val;
    }

    private int[][] copyState() {
        // TODO
        return new int[8][8];
    }
}
