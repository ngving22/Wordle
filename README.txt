=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 1200 Game Project README
PennKey: ngaj
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D Arrays
     A standard Wordle frame is 6 x 5. To represent this, I will use a 2D array of JButtons that will store the current state of each square.
     Each square will represent the respective letter in which the user guessed word (ie. the first block represents the first letter).
     In addition, the blocks, which correspond with the user’s most recent guess, will change colors in this accordance:
     If the guessed letter is in the same spot as the letter in the mystery word, the block will turn green
     If the guessed letter is not in the same spot as the letter in the mystery word, the block will turn yellow
     If the guessed letter is not in the mystery word, the block will turn dark grey

  2. Collections
     I will store the letters/words the user guesses during the game in a collection.
     Most importantly, when the letter is not in the same corresponding spot as the mystery word, a collection is needed to keep track of this letter as we possibly match the remaining letters of the mystery word.
     I will utilize an ArrayList for this purpose (char type).
     The list will store the characters in the order I insert them in (the guess word order) and allows for duplicates, which are a possibility.
     This will help with edge cases as well (ie. guess having multiple same letter, wordle word only contain one of that letter).


  3. File I/O
     My Wordle implementation will use I/O when saving and loading the game state.
     When the user guesses a five-letter word, the game will check if the word is a valid word via a buffered reader.
     If not, the game will throw an error stating the word is in the current word list.
     I will also use a buffered reader to scan through common 5-letter word list to pick the mystery word.
     For saving and loading the game state, I am writing key states to a txt file, such as
     JButton text, label, JButton colors, mystery word, and other necessary components.

  4. JUnit Testable Component
     The main state of my game will be the board (2D JButton array).
     I used JUnit testing to validate that the 2D array is updated correctly,
     as well as if a winner was correctly found.
     I also write JUnit test for edge cases, such as duplicate letters in wrong position and
     letters with incorrect positions followed by the same letter in the correct position.
     In addition, I create JUnit testing for my getWord and wordExists functions, as well
     as checking the game state for different scenarios, such as a user losing or if
     the game is not over yet.

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
Words.java
This class implements File I/O to obtain a random mystery word for the user to guess.
This class contains two functions: getWord() and wordExists().
getWord() uses File I/O to scan through a common 5-letter word txt file and draw one at random.
wordExists() takes the user's guess and uses a BufferedReader to scan through a txt file with all 5-letter english words
to see if the user's guess is a valid English word.

Instructions.java
This class creates a JFrame which serves as an instruction window for the user on how
to play the game.
The pop-up window is closeable and is accessed by the help button located in the lower right
of the game frame.

Frame.java
This class is contains the constructor for the main frame of the game and multiple relevant methods
that makes changes to the state of the game.
The constructor of the main game frame takes in one argument (the number of points the user is on).
From this, frame is created with relevant features, including buttons, panels, labels, and variables.
Aside from the main constructor, the class also contains multiple listener functions, including keyPressed,
keyTyped, keyReleased, and actionPerformed. Each user interaction would dictate different changes in the
game state (ie. keypress on valid key will display appropriate letter in JButton text).
In addition, this class also contains a checkWord (compares user guess vs mystery word, will
change button colors accordingly) and checkWinner (if won or lost, changes label text) functions.
Finally, the last couple of functions are there to save and load the game state for the user.

RunWordle.java
Implements runnable functionality for my frame

Game.java
Acts as the main methods which the game is run for this project




- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

A significant stumbling block when implementing the game was thinking of edge cases and how
to avoid wrongly coloring the JButtons.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

Yes, there is a good separation of functionality between each distinct function and class.
My private state is well encapsulated for what I wanted to encapsulate (such as labels for
the help pop-up window).


========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used 
  while implementing your game.

I used 7ESL.com to get a list of common 5-letter words for my target.txt.
I used ESLForums.com to get a list of all 5-letter English words for my dictionary.txt.
