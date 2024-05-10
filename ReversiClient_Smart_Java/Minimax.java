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
    public int computeBestMove(final int round, final int[][] state, final int depth, final int player) {
        assert(player == Util.MAXIMIZER || player == Util.MINIMIZER);
        bestMove = -1;
        topDepth = depth;
        
        int bestMoveScore = minimax(round, state, depth, player);
        System.out.println("Best possible score: " + bestMoveScore);
        return bestMove;
    }

    // returns the score of the best move
    // sets best
    private int minimax(final int round, final int[][] state, final int depth, final int player) {
        int[] validMoves = new int[64];
        int numValidMoves = Util.getValidMoves(round, state, player, validMoves);
        
        // base case
        if (depth == 0 || numValidMoves == 0) {
            return staticEvaluation(state);
        }

        if (player == Util.MAXIMIZER) {
            int maxEval = Integer.MIN_VALUE;
            for (int i = 0; i < numValidMoves; i++) {
                int[][] child = createChildState(state, player, validMoves[i]); // NOTE can reduce time complexity by some by changing to not duplicate this(MAYBE LATER)
                int eval = minimax(round + 1, child, depth - 1, Util.MINIMIZER);
                if (depth == topDepth) {
                    // move value only needed at the top level (immediate move)
                    if (eval > maxEval) {
                        bestMove = validMoves[i];
                    }
                }
                maxEval = Math.max(maxEval, eval);
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (int i = 0; i < numValidMoves; i++) {
                int[][] child = createChildState(state, player, validMoves[i]);
                int eval = minimax(round + 1, child, depth - 1, Util.MAXIMIZER);
                if (depth == topDepth) {
                    // move value only needed at the top level (immediate move)
                    if (eval < minEval) {
                        bestMove = validMoves[i];
                    }
                }
                minEval = Math.min(minEval, eval);
            }
            return minEval;
        }        
    }

    private int staticEvaluation(final int[][] state) {
        assert(state.length == 8 && state[0].length == 8);
        
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

    // move is (0-63) index representation of a move
    public static int[][] createChildState(final int[][] state, final int player, final int move) {
        int[][] child = new int[8][8];
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                child[i][j] = state[i][j];
            }
        }
        
        int row = getRow(move);
        int col = getCol(move);
        System.out.println("row: " + row + ", col: " + col);
        
        child[row][col] = player;
        Util.flipPieces(child, row, col, player);

        return child;
    }

    // TODO MAKE SURE I'M NOT GETTING ROWS BACKWARDS!!!!!
    private static int getRow(final int indexRepresentation) {
        return indexRepresentation / 8;
    }

    private static int getCol(final int indexRepresentation) {
        return indexRepresentation % 8;
    }
}
