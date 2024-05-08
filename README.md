The supplied code contains the following four folders/programs:
1. ReversiServer – Java code that acts as the Server for two client programs. Client programs (e.g., 
your algorithm) connect to the server so that they can play games. You shouldn’t have to change 
this code. But you need to compile it (command-line: `javac *.java`) on your computer, meaning 
you’ll need to have Java installed.
2. ReversiHuman – Java code for a human client. It displays a GUI that a user can click on to make 
moves. You’ll need to compile this code on your computer (`javac *.java`) before running it.
3. ReversiRandom_Java – Java code that is a random client. If you want to code your program in 
Java, then add to this code. Currently, it connects to the server and makes random moves when it 
is its turn. You’ll need to compile this code on your computer (`javac *.java`) before running it.

Suppose that I wanted to have a human player play against a random player, all running on the same 
computer. Then I would do the following:
1. Start the server: `java Reversi 10`  
    - Note that the parameter 10 specifies the number of minutes that each player has of move 
throughout the game. 
2. Start player 1 (the Human player in this case): `java Human localhost 1`  
    - See the files for descriptions of the parameters
3. Start player 2 (the Random player): `java RandomGuy localhost 2`  
    - See the files for descriptions of the parameters
