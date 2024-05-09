public class Test { // DELETEME
    public static void main(String[] args) {
        int[][] arr = {{0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,2,1,2,0,0,2},
            {0,0,0,2,1,0,1,0},
            {0,0,0,1,0,1,0,0},
            {0,0,0,1,1,0,0,0},
            {2,1,1,2,0,0,0,0}};
        Util.flipPieces(arr, 7, 3, 2);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println("");
        }
    }  
}
