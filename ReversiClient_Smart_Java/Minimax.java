public class Minimax {
    // we only save the immediate next move
    // recursing o further depths is only for heuristics
    private int topDepth;
    // 0-63 representing where to move
    private int bestMove;
    
    public Minimax() {
        bestMove = -1;
    }
    
    // public int ComputeBest(int[][] state, int depth) {
    //     topDepth = depth;
        
    //     int bestMoveScore = minimax()
    //     System.out.println("Best possible score: " + bestMoveScore);
    //     return bestMove;
    // }

    private int minimax(int[][] state, int depth, int player) {
        
        
        return 0;
    }



    private int[][] copyState() {
        // TODO
        return new int[8][8];
    }
}
