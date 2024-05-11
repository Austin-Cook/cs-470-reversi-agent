public class Test {
    public static void main(String[] args) {
        int[][] arr = {{1,0,0,0,0,0,0,2},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,1,1,0,0,0},
            {0,0,0,2,1,0,0,0},
            {0,0,2,0,0,0,0,0},
            {0,2,0,0,0,0,0,0},
            {2,0,0,0,0,0,0,2}};
        // Util.flipPieces(arr, 7, 3, 2);
        // int[][] child = Minimax.createChildState(arr, 2, 21);
        // double tokenScore = Minimax.tokenHeuristic(arr);
        // System.out.println("Token heuristic: " + tokenScore);
        // double mobilityScore = Minimax.mobilityHeuristic(arr, 5);
        // System.out.println("Mobility heuristic: " + mobilityScore);
        // double cornerScore = Minimax.cornerHeuristic(arr);
        // System.out.println("Corner heuristic: " + cornerScore);


        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println("");
        }
    }  
}
