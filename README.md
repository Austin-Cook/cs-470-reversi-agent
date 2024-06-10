A server to facilitate games of Reversi, and 5 clients, each with a unique strategy to play the game.

# Compile and Run

## Compile

To compile the server, or any of the clients, move to the respective folder and run:

`javac *.java`

Note: `ReversiClient_DrCrandall_MCTS` is just a `.jar` file, and does not need to be compiled.

## Run

### 1. Start the Server

In folder `ReversiServer`, run:

`java Reversi 10`  

Note: The parameter 10 specifies the number of minutes that each player has of move 
throughout the game.

### 2. Connect Two Clients (Players) to the Server

In the respective client folders, run:

`java CLIENT_NAME localhost 1` for player 1  
`java CLIENT_NAME localhost 2` for player 2

Note: Replace `CLIENT_NAME` with the respective client name in the folder:
- `HumanClient`
- `RandomClient`
- `MinimaxClient`
- `MCTSClient`

Note: For `ReversiClient_DrCrandall_MCTS, simply run:

`java -jar ReversiClient_DrCrandall_MCTS.jar localhost [1|2]`

from the root folder.

# Notes

There are 5 clients:

1. ReversiClient_Human
    - This client allows a human to play. Running it opens a Reversi UI where the user can click on their selected action.
2. ReversiClient_Random
    - Makes random moves.
3. ReversiClient_Minimax
    - Uses the Minimax algorithm, alpha-beta pruning (for efficiency), and smart heuristics.
    - Heuristics are weighted, account for both the player (maximizer) and opponent (minimizer), and normalized to zero.
    - Heuristics used:
        - Number of tokens
        - Number of moves
        - Number of corners
4. ReversiClient_MCTS
    - Uses Monte Carlo Tree Search, an alternative to the Minimax algorithm.
    - MCTS is less computationally expensive than Minimax, and doesn't require the use of heuristics. It works by running for a set amount of time. While time is left, it expands one new child state, simulate random play from the child state to termination, and percolates the resulting score up. While Minimax and MCTS are both viable options for Reversi, MCTS shines in games with larger state spaces where an exhaustive search is not possible.
5. ReversiClient_DrCrandall_MCTS
    - This also uses Monte Carlo Tree Search, but was created by Dr. Crandall. Only a `.jar` file is provided to not disclose his code. It is notoriously good at Reversi.

Implementation Note and Issue:
- In my implementation, during the `expand` stage of MCTS (stages are `select`, `expand`, `simulate`, and `back up `), I decided to expand all child states (possible moves of the children) at once instead of just one at a time. I believe this altered, to some extent, the efficacy of the Upper Confidence Bound algorithm for future `select` stages where the tree is traversed to a new leaf node to expand. This is due trickling up the results of around 6 expanded children simulations, rather than just one at a time. Changing this would require considerable restructuring in my implementaiton, though I believe it would greatly improve the skill of my MCTS. If you implement MCTS, I would recommend taking this route and only expand one possible move at a time.