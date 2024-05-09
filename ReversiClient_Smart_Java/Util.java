public class Util {
  public static final int MAXIMIZER = 1;
  public static final int MINIMIZER = 2;
  
  // generates the set of valid moves for the player; returns a list of valid moves (validMoves)
  // sets a value at an index (increasing) for each valid move to a number representing a number on the grid
  // returns the number of valid moves
  public static int getValidMoves(int round, int state[][], int player, int[] _validMoves) {
    assert (player == MAXIMIZER || player == MINIMIZER);
    assert (_validMoves.length == 64);

    int i, j;
    int _numValidMoves = 0;

    if (round < 4) {
      if (state[3][3] == 0) {
        _validMoves[_numValidMoves] = 3*8 + 3;
        _numValidMoves ++;
      }
      if (state[3][4] == 0) {
        _validMoves[_numValidMoves] = 3*8 + 4;
        _numValidMoves ++;
      }
      if (state[4][3] == 0) {
        _validMoves[_numValidMoves] = 4*8 + 3;
        _numValidMoves ++;
      }
      if (state[4][4] == 0) {
        _validMoves[_numValidMoves] = 4*8 + 4;
        _numValidMoves ++;
      }
      System.out.println("Valid Moves:");
      for (i = 0; i < _numValidMoves; i++) {
        System.out.println(_validMoves[i] / 8 + ", " + _validMoves[i] % 8);
      }
    }
    else {
      System.out.println("Valid Moves:");
      for (i = 0; i < 8; i++) {
        for (j = 0; j < 8; j++) {
          if (state[i][j] == 0) {
            if (couldBe(state, i, j, player)) {
              _validMoves[_numValidMoves] = i*8 + j;
              _numValidMoves ++;
              System.out.println(i + ", " + j);
            }
          }
        }
      }
    }

    return _numValidMoves;
    
    //if (round > 3) {
    //    System.out.println("checking out");
    //    System.exit(1);
    //}
  }

  // flip sandwiched pieces after a player takes a turn
  public static void flipPieces(int[][] state, int row, int col, int player) {
    int incx, incy;
    
    for (incx = -1; incx < 2; incx++) {
      for (incy = -1; incy < 2; incy++) {
        if ((incx == 0) && (incy == 0))
          continue;
    
        flipInDirection(state, row, col, incx, incy, player);
      }
    }
  }
  
  // Checks if a piece could be placed at a location
  // given the pieces from a specific direction from the location
  private static boolean checkDirection(int state[][], int row, int col, int incx, int incy, int player) {
    assert (player == MAXIMIZER || player == MINIMIZER);

    int sequence[] = new int[7];
    int seqLen;
    int i, r, c;
    
    seqLen = 0;
    for (i = 1; i < 8; i++) {
      r = row+incy*i;
      c = col+incx*i;
  
      if ((r < 0) || (r > 7) || (c < 0) || (c > 7))
        break;
  
      sequence[seqLen] = state[r][c];
      seqLen++;
    }
    
    int count = 0;
    for (i = 0; i < seqLen; i++) {
      if (player == MAXIMIZER) {
        if (sequence[i] == 2)
          count ++;
        else {
          if ((sequence[i] == 1) && (count > 0))
            return true;
          break;
        }
      }
      else {
        if (sequence[i] == 1)
          count ++;
        else {
          if ((sequence[i] == 2) && (count > 0))
            return true;
          break;
        }
      }
    }
    
    return false;
  }
  
  // Can I place a piece in this location?
  // Checks in a circle around the placement location
  private static boolean couldBe(int state[][], int row, int col, int player) {
    assert (player == MAXIMIZER || player == MINIMIZER);

    int incx, incy;
    
    for (incx = -1; incx < 2; incx++) {
      for (incy = -1; incy < 2; incy++) {
        if ((incx == 0) && (incy == 0))
          continue;
    
        if (checkDirection(state, row, col, incx, incy, player))
          return true;
      }
    }
    
    return false;
  }

  private static void flipInDirection(int[][] state, int row, int col, int incx, int incy, int player) {
    assert(state.length == 8 && state[0].length == 8);

    int sequence[] = new int[7];
    int seqLen;
    int i, r, c;
    
    seqLen = 0;
    for (i = 1; i < 8; i++) {
      r = row+incy*i;
      c = col+incx*i;
  
      if ((r < 0) || (r > 7) || (c < 0) || (c > 7))
        break;
  
      sequence[seqLen] = state[r][c];
      seqLen++;
    }
    
    int count = 0;
    for (i = 0; i < seqLen; i++) {
      if (player == 1) {
        if (sequence[i] == 2)
          count ++;
        else {
          if ((sequence[i] == 1) && (count > 0))
            count = 20;
          break;
        }
      }
      else {
        if (sequence[i] == 1)
          count ++;
        else {
          if ((sequence[i] == 2) && (count > 0))
            count = 20;
          break;
        }
      }
    }
    
    if (count > 10) {
      if (player == 1) {
        i = 1;
        r = row+incy*i;
        c = col+incx*i;
        while (state[r][c] == 2) {
          state[r][c] = 1;
          i++;
          r = row+incy*i;
          c = col+incx*i;
        }
      }
      else {
        i = 1;
        r = row+incy*i;
        c = col+incx*i;
        while (state[r][c] == 1) {
          state[r][c] = 2;
          i++;
          r = row+incy*i;
          c = col+incx*i;
        }
      }
    }
  }
}
