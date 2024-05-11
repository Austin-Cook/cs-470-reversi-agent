/**
 * Board Index Representation:
 * 56 57 58 59 60 61 62 63
 * 48 49 50 51 52 53 54 55
 * 40 41 42 43 44 45 46 47
 * 32 33 34 35 36 37 38 39
 * 24 25 26 27 28 29 30 31
 * 16 17 18 19 20 21 22 23
 * 8  9  10 11 12 13 14 15
 * 0  1  2  3  4  5  6  7
 */
public class Minimax {
    private int topDepth;
    private int bestMove; // board index representation
    
    public Minimax() {
    }

    public int computeBestMove(final int round, final int[][] state, final int depth, final int player) {
        assert(player == Util.MAXIMIZER || player == Util.MINIMIZER);
        bestMove = -1;
        topDepth = depth;
        
        minimax(round, state, depth, Double.MIN_VALUE, Double.MAX_VALUE, player);

        return bestMove;
    }

    /**
     * @return The score of the best move found
     */
    private double minimax(final int round, final int[][] state, final int depth, double alpha, double beta, final int player) {
        int[] validMoves = new int[64];
        int numValidMoves = Util.getValidMoves(round, state, player, validMoves);
        
        if (depth == 0 || numValidMoves == 0) {
            return staticEvaluation(state, round);
        }

        if (player == Util.MAXIMIZER) {
            double maxEval = Double.MIN_VALUE;
            for (int i = 0; i < numValidMoves; i++) {
                int[][] child = createChildState(state, player, validMoves[i]);
                double eval = minimax(round + 1, child, depth - 1, alpha, beta, Util.MINIMIZER);
                if (depth == topDepth) {
                    if (eval > maxEval) {
                        // save move (best and at top level)
                        bestMove = validMoves[i];
                    }
                }
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha) {
                    break;
                }
            }
            return maxEval;
        } else {
            double minEval = Double.MAX_VALUE;
            for (int i = 0; i < numValidMoves; i++) {
                int[][] child = createChildState(state, player, validMoves[i]);
                double eval = minimax(round + 1, child, depth - 1, alpha, beta, Util.MAXIMIZER);
                if (depth == topDepth) {
                    if (eval < minEval) {
                        // save move (best and at top level)
                        bestMove = validMoves[i];
                    }
                }
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta <= alpha) {
                    break;
                }
            }
            return minEval;
        }        
    }

    private static double staticEvaluation(final int[][] state, final int round) {
        assert(state.length == 8 && state[0].length == 8);
        
        final double tokenWeight = 1.0;
        final double mobilityWeight = 0.80;
        final double cornerWeight = 0.5;
        
        return (((tokenWeight * tokenHeuristic(state)) + 
                (mobilityWeight * mobilityHeuristic(state, round)) + 
                (cornerWeight * cornerHeuristic(state))) / 3);
    }

    /**
     * The value in a state is impacted by the number of tokens a player controls
     */
    private static double tokenHeuristic(final int[][] state) {
        double maximizerTokens = 0;
        double minimizerTokens = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (state[i][j] == Util.MAXIMIZER) {
                    maximizerTokens += 1;
                } else if (state[i][j] == Util.MINIMIZER) {
                    minimizerTokens += 1;
                }
            }
        }

        return (100 * (maximizerTokens - minimizerTokens) / (maximizerTokens + minimizerTokens));
    }

    /**
     * The value in a state is impacted by the number of places a player can move
     */
    private static double mobilityHeuristic(final int[][] state, final int round) {
        int[] temp = new int[64];
        double numMaximizerMoves = Util.getValidMoves(round, state, Util.MAXIMIZER, temp);
        double numMinimizerMoves = Util.getValidMoves(round, state, Util.MINIMIZER, temp);

        if (numMaximizerMoves + numMinimizerMoves != 0) {
            return (100 * (numMaximizerMoves - numMinimizerMoves) / (numMaximizerMoves + numMinimizerMoves));
        }
        return 0;
    }

    /**
     * The value in a state is impacted by the number of corners a player has
     */
    private static double cornerHeuristic(final int[][] state) {
        double numMaximizerCorners = 0;
        double numMinimizerCorners = 0;
        int[] indexes = { 0, 7 };
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                if (state[indexes[i]][indexes[j]] == Util.MAXIMIZER) {
                    numMaximizerCorners += 1;
                } else if (state[indexes[i]][indexes[j]] == Util.MINIMIZER) {
                    numMinimizerCorners += 1;
                }
            }
        }

        if (numMaximizerCorners + numMinimizerCorners != 0) {
            return (100 * (numMaximizerCorners - numMinimizerCorners) / (numMaximizerCorners + numMinimizerCorners));
        }
        return 0;
    }

    /**
     * Not used - was a preliminary heuristic
     * Similar to tokenHeuristic but doesn't weight between -100 and 100
     */
    private static int simpleHeuristic(final int[][] state) {
        int val = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (state[i][j] == Util.MAXIMIZER) {
                    val += 1;
                } else if (state[i][j] == Util.MINIMIZER) {
                    val -= 1;
                }
            }
        }

        return val;
    }

    private static int[][] createChildState(final int[][] state, final int player, final int move) {
        int[][] child = new int[8][8];
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                child[i][j] = state[i][j];
            }
        }
        
        int row = getRow(move);
        int col = getCol(move);
        
        child[row][col] = player;
        Util.flipPieces(child, row, col, player);

        return child;
    }

    private static int getRow(final int indexRepresentation) {
        return indexRepresentation / 8;
    }

    private static int getCol(final int indexRepresentation) {
        return indexRepresentation % 8;
    }
}
