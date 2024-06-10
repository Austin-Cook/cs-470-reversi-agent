public class Util {
  public static final int PLAYER_ONE = 1;
  public static final int PLAYER_TWO = 2;

  public static int[][] createChildGrid(final int[][] grid, final int player, final int move) {
    int[][] child = new int[8][8];
    for (int i = 0; i < 8; i++){
      for (int j = 0; j < 8; j++){
        child[i][j] = grid[i][j];
      }
    }
    
    int row = getRow(move);
    int col = getCol(move);
    
    child[row][col] = player;
    Util.flipPieces(child, row, col, player);

    return child;
  }

  public static boolean didWin(final int[][] grid, int player) {
    int opponent = player == Util.PLAYER_ONE ? Util.PLAYER_TWO : Util.PLAYER_ONE;
    int val = 0;
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        if (grid[i][j] == player) {
          val += 1;
        } else if (grid[i][j] == opponent) {
          val -= 1;
        }
      }
    }

    return val > 0;
  }

  public static int getRow(final int indexRepresentation) {
    return indexRepresentation / 8;
  }

  public static int getCol(final int indexRepresentation) {
    return indexRepresentation % 8;
  }

  public static int changeTurns(int player) {
    return player == Util.PLAYER_ONE ? Util.PLAYER_TWO : Util.PLAYER_ONE;
  }

  // generates the set of valid moves for the player; returns a list of valid moves (validMoves)
  // sets a value at an index (increasing) for each valid move to a number representing a number on the grid
  // returns the number of valid moves
  public static int getValidMoves(final int round, final int grid[][], final int player, final int[] _validMoves) {
    int i, j;
    int _numValidMoves = 0;

    if (round < 4) {
      if (grid[3][3] == 0) {
        _validMoves[_numValidMoves] = 3*8 + 3;
        _numValidMoves ++;
      }
      if (grid[3][4] == 0) {
        _validMoves[_numValidMoves] = 3*8 + 4;
        _numValidMoves ++;
      }
      if (grid[4][3] == 0) {
        _validMoves[_numValidMoves] = 4*8 + 3;
        _numValidMoves ++;
      }
      if (grid[4][4] == 0) {
        _validMoves[_numValidMoves] = 4*8 + 4;
        _numValidMoves ++;
      }
      // System.out.println("Valid Moves:");
      // for (i = 0; i < _numValidMoves; i++) {
      //   System.out.println(_validMoves[i] / 8 + ", " + _validMoves[i] % 8);
      // }
    }
    else {
      // System.out.println("Valid Moves:");
      for (i = 0; i < 8; i++) {
        for (j = 0; j < 8; j++) {
          if (grid[i][j] == 0) {
            if (couldBe(grid, i, j, player)) {
              _validMoves[_numValidMoves] = i*8 + j;
              _numValidMoves ++;
              // System.out.println(i + ", " + j);
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
  public static void flipPieces(int[][] grid, final int row, final int col, final int player) {
    int incx, incy;
    
    for (incx = -1; incx < 2; incx++) {
      for (incy = -1; incy < 2; incy++) {
        if ((incx == 0) && (incy == 0))
          continue;
    
        flipInDirection(grid, row, col, incx, incy, player);
      }
    }
  }
  
  // Checks if a piece could be placed at a location
  // given the pieces from a specific direction from the location
  private static boolean checkDirection(final int grid[][], final int row, final int col, final int incx, final int incy, final int player) {
    int sequence[] = new int[7];
    int seqLen;
    int i, r, c;
    
    seqLen = 0;
    for (i = 1; i < 8; i++) {
      r = row+incy*i;
      c = col+incx*i;
  
      if ((r < 0) || (r > 7) || (c < 0) || (c > 7))
        break;
  
      sequence[seqLen] = grid[r][c];
      seqLen++;
    }
    
    int count = 0;
    for (i = 0; i < seqLen; i++) {
      if (player == PLAYER_ONE) {
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
  private static boolean couldBe(final int grid[][], final int row, final int col, final int player) {
    int incx, incy;
    
    for (incx = -1; incx < 2; incx++) {
      for (incy = -1; incy < 2; incy++) {
        if ((incx == 0) && (incy == 0))
          continue;
    
        if (checkDirection(grid, row, col, incx, incy, player))
          return true;
      }
    }
    
    return false;
  }

  private static void flipInDirection(final int[][] grid, final int row, final int col, final int incx, final int incy, final int player) {
    int sequence[] = new int[7];
    int seqLen;
    int i, r, c;
    
    seqLen = 0;
    for (i = 1; i < 8; i++) {
      r = row+incy*i;
      c = col+incx*i;
  
      if ((r < 0) || (r > 7) || (c < 0) || (c > 7))
        break;
  
      sequence[seqLen] = grid[r][c];
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
        while (grid[r][c] == 2) {
          grid[r][c] = 1;
          i++;
          r = row+incy*i;
          c = col+incx*i;
        }
      }
      else {
        i = 1;
        r = row+incy*i;
        c = col+incx*i;
        while (grid[r][c] == 1) {
          grid[r][c] = 2;
          i++;
          r = row+incy*i;
          c = col+incx*i;
        }
      }
    }
  }
}
