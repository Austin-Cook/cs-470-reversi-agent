The supplied code contains the following four folders/programs:
1. ReversiServer – Java code that acts as the Server for two client programs. Client programs (e.g., 
your algorithm) connect to the server so that they can play games. You shouldn’t have to change 
this code.
2. ReversiHuman – Java code for a human client. It displays a GUI that a user can click on to make 
moves.
3. ReversiClient_Random_Java – Java code that is a random client. If you want to code your program in 
It connects to the server and makes random moves when it is its turn.
4. ReversiClient_Java - Java code for a smarter client agent. This uses the minimax algorithm with alpha-beta pruning and smart heuristics.
NOTE - You’ll need to compile the code in each folder (`javac *.java`) before running it.


Suppose that I wanted to have a human player play against a computer player, all running on the same computer. Then I would do the following:
1. Start the server (in folder `ReversiServer`): `java Reversi 10`  
    - Note that the parameter 10 specifies the number of minutes that each player has of move 
throughout the game. 
2. Start player 1 (the Human player in this case - in folder `ReversiHuman`): `java Human localhost 1`  
    - See the files for descriptions of the parameters
3. Start player 2 
    - The Random player (in folder `ReversiClient_Random_Java`): `java RandomGuy localhost 2`  
    OR
    - Strategic Agent (in folder `ReversiClient_Smart_Java`): `java SmartGuy localhost 2`
