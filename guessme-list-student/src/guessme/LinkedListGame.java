package guessme;

/**
 * A LinkedList-based implementation of the Guess-A-Number game.
 */
public class LinkedListGame {
 
  private int guess;
  boolean gameOver;
  int guessCount;
  LLIntegerNode origin;
  LLIntegerNode terminal;
  LLIntegerNode candidates;
  LLIntegerNode head;
  LLIntegerNode tail;
  LLIntegerNode guesses;

  /********************************************************
   * NOTE: for this project you must use linked lists
   * implemented by yourself. You are NOT ALLOWED to use
   * Java arrays of any type, or any class in the java.util
   * package (such as ArrayList).
   *******************************************************/

  /********************************************************
   * NOTE: you are allowed to add new methods if necessary,
   * but DO NOT remove any provided method, and do NOT add
   * new files (as they will be ignored by the autograder).
   *******************************************************/

  // LinkedListGame constructor method
  public LinkedListGame() {
    
    reset();
  }

  /** Resets data members and game state so we can play again.
   * 
   */
  public void reset() {
    guess = 1000;
    gameOver = false;
    guessCount = 0;
    origin = new LLIntegerNode(-1);
    terminal = new LLIntegerNode(-1);
    candidates = new LLIntegerNode(1000);
    head = new LLIntegerNode(-1);
    tail = new LLIntegerNode(-1);
    
    candidates.setLast(origin);
    origin.setLink(candidates);
    terminal.setLast(candidates);
    for (int i = 1001; i < 10000; i++) {
      candidates = new LLIntegerNode(i);
      candidates.setLast(terminal.getLast());
      terminal.getLast().setLink(candidates);
      terminal.setLast(candidates);
      if (i == 9999) {
        candidates.setLink(terminal);
      }
    }
  }

  /** Returns true if n is a prior guess; false otherwise.
   * 
   */
  public boolean isPriorGuess(int n) { 
    boolean isGuess = false;
    LLIntegerNode temp = head.getLink(); 
    while (temp != null) { 
      if (temp.getInfo() == n) { 
        isGuess = true;
        break;
      }
      temp = temp.getLink();
    }
    return isGuess;
  }

  /** Returns the number of guesses so far.
   * 
   */
  public int numGuesses() {
    return guessCount;
  }

  /**
   * Returns the number of matches between integers a and b.
   * You can assume that both are 4-digits long (i.e. between 1000 and 9999).
   * The return value must be between 0 and 4.
   * 
   * <p>A match is the same digit at the same location. For example:
   *   1234 and 4321 have 0 match;
   *   1234 and 1114 have 2 matches (1 and 4);
   *   1000 and 9000 have 3 matches (three 0's).
   */
  public static int numMatches(int a, int b) {
    int count = 0;
    int guessN = a;
    int actualN = b;
    for (int j = 0; j < 4; j++) {
      guessN = a % 10;
      actualN = b % 10;
      if (guessN == actualN) {
        count++;
      }
      a /= 10;
      b /= 10;
    }
    return count;
  }

  /**
   * Returns true if the game is over; false otherwise.
   * The game is over if the number has been correctly guessed
   * or if no candidate is left.
   */
  public boolean isOver() {
    if (origin.getLink() == terminal) {
      return true;
    }
    return gameOver;
  }

  /**
   * Returns the guess number and adds it to the list of prior guesses.
   * The insertion should occur at the end of the prior guesses list,
   * so that the order of the nodes follow the order of prior guesses.
   */
  public int getGuess() {
    guessCount++;
    if (head.getLink() == null) {
      guesses = new LLIntegerNode(guess);
      head.setLink(guesses);
      tail.setLink(guesses);
    } else {
      guesses = new LLIntegerNode(guess);
      tail.getLink().setLink(guesses);
      tail.setLink(guesses);
    }
    return guess;
  }

  /**
   * Updates guess based on the number of matches of the previous guess.
   * If nmatches is 4, the previous guess is correct and the game is over.
   * Check project description for implementation details.
   * 
   * <p>Returns true if the update has no error; false if no candidate 
   * is left (indicating a state of error);
   */
  public boolean updateGuess(int nmatches) { 
    LLIntegerNode temp = origin.getLink();
    
    
    if (nmatches == 4) {
      gameOver = true;
    }
    
    while (temp.getLink() != null) {
      if (numMatches(guess, temp.getInfo()) != nmatches) {
        temp.getLast().setLink(temp.getLink());
        temp.getLink().setLast(temp.getLast());
      }
      temp = temp.getLink();
    }
    
    if (origin.getLink() == terminal) {
      return false;
    } else {
      guess = origin.getLink().getInfo();
    }
    return true;
  }

  /**
   *  Returns the head of the prior guesses list.
   *  Returns null if there hasn't been any prior guess
   */
  public LLIntegerNode priorGuesses() {
    if (head.getLink() == null) {
      return null;
    } else {
      return head.getLink();
    }
  }

  /**
   * Returns the list of prior guesses as a String. For example,
   * if the prior guesses are 1000, 2111, 3222, in that order,
   * the returned string should be "1000, 2111, 3222", in the same order,
   * with every two numbers separated by a comma and space, except the
   * last number (which should not be followed by either comma or space).
   *
   * <p>Returns an empty string if here hasn't been any prior guess
   */
  public String priorGuessesString() {
    String guessList = "";
    
    if (guessCount == 0) {
      return guessList;
    } 
      
    LLIntegerNode temp = head.getLink();
    while (temp.getLink() != null) {
      guessList += temp.getInfo() + ", ";
      temp = temp.getLink();
    }
    guessList += temp.getInfo();
    return guessList;
  }
}
