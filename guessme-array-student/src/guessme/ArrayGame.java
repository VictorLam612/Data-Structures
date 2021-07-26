package guessme;

/**
 * An Array-based implementation of the Guess-A-Number game.
 */
public class ArrayGame {
  

  // stores the next number to guess
  private int guess; 
  
  int[] priorGuess; // Array of prior guesses to be returned
  int numGuesses; // Counter for number of guesses
  boolean isNumber; // Variable to be true when number is guessed
  boolean[] candidates; // Array holding "flags" for which candidates are still in the running


  // NOTE: only primitive type arrays are allowed, such as int[], boolean[] etc.
  // You MAY NOT use any Collection type (such as ArrayList) provided by Java.

  /********************************************************
   * NOTE: you are allowed to add new methods if necessary,
   * but DO NOT remove any provided method, otherwise your
   * code will fail the JUnit tests.
   * Also DO NOT create any new Java files, as they will
   * be ignored by the autograder!
   *******************************************************/

  // ArrayGame constructor method
  public ArrayGame() {
    priorGuess = new int[18];
    numGuesses = -1;
    isNumber = false;
    candidates = new boolean [9000];
    for (int i = 0; i < 9000; i++) {
      candidates[i] = true;
    }
    guess = 1000;
  }

  /**
   *  Resets data members and game state so we can play again.
   */
  public void reset() {
    priorGuess = new int[20];
    numGuesses = -1;
    isNumber = false;
    for (int i = 0; i < 9000; i++) {
      candidates[i] = true;
    }
    guess = 1000;
  }

  /**
   *  Returns true if n is a prior guess; false otherwise.
   */
  public boolean isPriorGuess(int n) {
    boolean isPrior = false;
    for (int j = 0; j < priorGuesses().length; j++) {
      if (priorGuesses()[j] == n) {
        isPrior = true;
        break;
      }
    }
    return isPrior;
  }

  /**
   *  Returns the number of guesses so far.
   */
  public int numGuesses() {
    return numGuesses + 1;
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
  public static int numMatches(int a, int b) { // DO NOT remove the static qualifier
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
   * or if all candidates have been eliminated.
   */
  public boolean isOver() {
    boolean isDone = true;
    boolean submit = false;
    for (int j = 0; j < 9000; j++) {
      if (candidates[j]) {
        isDone = false;
      }
    }
    if ((isDone) || (isNumber)) {
      submit = true;
    }
    return submit;
  }

  /**
   *  Returns the guess number and adds it to the list of prior guesses.
   */
  public int getGuess() {
    numGuesses++;
    priorGuess[numGuesses] = guess;
    return guess;
  }  

  /**
   * Updates guess based on the number of matches of the previous guess.
   * If nmatches is 4, the previous guess is correct and the game is over.
   * Check project description for implementation details.
   * 
   * <p>Returns true if the update has no error; false if all candidates
   * have been eliminated (indicating a state of error);
   */
  public boolean updateGuess(int nmatches) {
    boolean updateSuccess = false; // Boolean to be returned
 
    if (nmatches == 4) {
      isNumber = true;
    } 
    
    for (int i = 0; i < 9000; i++) {
      if (!(numMatches(guess, i + 1000) == nmatches)) {
        candidates[i] = false;
      } 
    }
    
    nextGuess();

    for (int i = 0; i < 9000; i++) {
      if (candidates[i] == true) {
        updateSuccess = true;
        break;
      }
    }
    return updateSuccess;
  }
  
  /**
   * Returns the list of guesses so far as an integer array.
   * The size of the array must be the number of prior guesses.
   * Returns null if there has been no prior guess
   */
  public int[] priorGuesses() {
    int arraySize = numGuesses + 1;
    int[] listGuess = new int[arraySize];
    for (int j = 0; j < arraySize; j++) {
      listGuess[j] = priorGuess[j];
    }
    if (listGuess.length == 0) {
      listGuess = null;
    }
    return listGuess;
  }

  /**
   * Gets next guess value. Does not return a value
   */
  public void nextGuess() {
    for (int j = 0; j < 9000; j++) {
      if (candidates[j]) {
        guess = j + 1000;
        break;
      }
    }
  }

}