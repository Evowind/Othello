
# Objective

  

[Learn about Othello's rules here.](https://en.wikipedia.org/wiki/Reversi)

  

<ol>

<li>Othello

<ol>

<li>Making the game</li>

<li>Graphical interface</li>

</ol>

</li>

<li>Random AI</li>

<li>MinMax AI

<ol>

<li>Theory</li>

<li>Implementation</li>

</ol>

</li>

<li>Alpha-Beta pruning

<ol>

<li>Theory</li>

<li>Implementation</li>

</ol>

</li>

</ol>

  

# 1. Programming the Othello game

  

### Making the game

  

This project is a student project to create an Othello game in console. The goal of the project is to design and build a playable version of the classic board game, Othello. Using basic programming concepts such as loops, conditionals, functions, and data structures to create a two-player turn-based strategy game with full user interaction through the console. This project will also involve working with artificial intelligence such as Min-Max. With this project, students will gain experience in object-oriented programming (OOP), debugging techniques, and software development best practices while creating something fun!

  

## Graphical interface

  

Creating a Graphical User Interface (GUI) for an Othello game student project can be time-consuming and complex. It is also not essential to the overall functionality of the game, so it may be best to focus on achieving other important parts of the project first. The GUI would require additional coding, graphics design, and testing that could take away from other elements such as artificial intelligence or gameplay mechanics.

  

# 2. Random AI

  

The making of a basic random IA in an Othello Game student project involves creating an algorithm that will generate moves for the AI player. The algorithm should consider available board positions, evaluate each move and select one at random. It is important to ensure that the generated moves are valid according to the game rules and do not result in immediate win or loss for either side. Additionally, some advanced features such as prioritizing certain pieces over others can be added if desired.

  

# 3. Making the MinMax AI

  

### Theory

  

The Min-Max algorithm is an artificial intelligence (AI) technique used to create optimal strategies in two-player, zero-sum games such as Othello. 
The goal of the Min-Max algorithm is to minimize the maximum loss that can be incurred by either player. This means that each move made by one player must be optimized so as not to give their opponent a better chance at winning the game.  
To implement the Min-Max algorithm for an Othello student project, it would first be necessary to define a score function which assigns numerical values for different board positions. This score function should take into account factors such as mobility, number of pieces on the board, and relative position of pieces on both sides. 
After this step is complete, all available moves can then be evaluated according to this scoring system and ranked from best to worst based on their respective scores. Once these rankings are established, a search tree or graph can then be constructed using these rankings as nodes in order to find the most optimal strategy for any given situation in Othello. 
Finally, once an optimal strategy has been found through this search tree or graph exploration process, it will then need to be implemented with code so that it may actually work within the game environment of Othello when played against another human or computer opponent.

  

## Implementation

  

To implement our AI, we first wrote code for basic rules such as capturing pieces and validating moves according to standard Othello rules. 
We then developed functions that would generate all legal moves from any given position on the board, evaluate them using predetermined heuristics such as mobility and stability scores and store them in a linked list called ‘game tree’ which represents all possible future states of gameplay up to some depth limit set by us (in our case it was 5).  We tested our AI multiple times against both novice human opponents and the random IA playing either black or white pieces with surprising results.
Overall, we are very pleased with how well this turned out despite facing various technical difficulties throughout the implementation process due to the complexity involved in developing such algorithms from scratch.

  

# 4. Improving MinMax AI with Alpha-Beta pruning

  

### Theory

  

Alpha-Beta pruning is an important technique when using MinMax. Alpha-Beta pruning is a search algorithm that reduces the number of nodes to be searched by limiting the search tree and cutting off branches that cannot possibly lead to a better result than what has already been found.
  

## Implementation

  

The implementation process was relatively simple and straightforward. First, we needed to set up a basic version of our Othello game with all necessary components (board, pieces, players). 
Then, we implemented a basic minimax algorithm without any pruning so that it could evaluate every possible path on each turn and choose the best one according to its evaluation function. Once this was done, we applied alpha-beta pruning by introducing two parameters: alpha and beta. Whenever evaluating any node during searching down the tree structure of moves on each turn, if ever both these values were equal or exceeded, then that particular branch can be discarded as no longer being worth considering as part of our search – thus reducing greatly how many nodes need to be evaluated overall.  
Overall, implementing alpha-beta pruning into our already existing Min-Max AI for an Othello game gave us much better results in terms of performance and efficiency compared with just running plain minimax alone; thus making it well worth incorporating into our project!
