package edu.grinnell.csc207.util;

import java.util.Random;


/**
 * the util methods for the word search game.
 * @author Sheilla Muligande
 * @author Princess Alexander
 */
public class WSUtils {
  public static final int MAX_WORDS = 10;  // Maximum number of words in the puzzle
  public static final int WORD_LENGTH = 5; // Word length constraint
  public static final int MAX_ATTEMPTS = 100;  // Max number of attempts to find space for a word
  
  /**
   * the array of words to add to our puzzle.
   */
  public static String[] WORDS = {"hello", "this", "needs", "to", "grow", "hi", "good", "night"};

  /**
   * search words randomly picks words from our WORDS dictionary
   * which we will use as the word search words.
   * @param wordCount the number of words the user chooses.
   * @return the words we will use in the word search.
   */
   public static String[] searchWords(int wordCount) {
    // Ensure wordCount doesn't exceed MAX_WORDS
    if (wordCount > MAX_WORDS) {
        throw new IllegalArgumentException("Word count exceeds the maximum limit.");
    }

    String[] WSWords = new String[wordCount];
    Random rand = new Random();
    int words = 0;

    while (words < wordCount) {
        String word = WORDS[rand.nextInt(WORDS.length)];
        boolean dup = false;
        for (int i = 0; i < words; i++) {
            if (WSWords[i].equals(word)) {
                dup = true;
            }
        }
        if (!dup) {
            WSWords[words] = word;
            words++;
        }
    }
    return WSWords;
}


/**
 * Method to add our word search words to the puzzle.
 * @param puzzle the empty puzzle to which we add the words.
 * @param words the words we add to the puzzle.
 */
  public static void WSpopulator(MatrixV0<Character> puzzle, String[] words) {

    Random rand = new Random();

    for (String word : words) {
      boolean size = false;
      while (!size) {
        int startRow = rand.nextInt(puzzle.height());
        int startCol = rand.nextInt(puzzle.width());

        int path = rand.nextInt(3);

        if (lengthChecker(puzzle, word, startRow, startCol, path)) {

        

          for (int i = 0; i < word.length(); i++) {
            if (path == 0) { // horiz
              puzzle.set(startRow, startCol + i, word.charAt(i));
            } else if (path == 1) { //vertic
              puzzle.set(startRow + i, startCol, word.charAt(i));
            } else if (path == 2) { //diagonal
              puzzle.set(startRow + i, startCol + i, word.charAt(i));
            }
          }
          size = true;
        }
      }
    }
  }

  /**
   * checks if we will be misplacing a word by putting it in a certain location.
   * @param puzzle the puzzle/word search where we are placing the word.
   * @param word  the word we are placing into the puzzle
   * @param row the row at which we place the first character of the word.
   * @param col the column at which we place the first character of the word.
   * @param path if it is going to be placed horizontally (0) vertically (2) or diagonally(3).
   * @return true if we place that word at the specified row,column and path, without cutting it, and false otherwise.
   */
  public static boolean lengthChecker(MatrixV0<Character> puzzle, String word, int row, int col,int path) {

  //  System.out.println("does this work" + word + path); 

    if (path == 0) {
      if (col + word.length() > puzzle.width()) {
        return false;
      }
      for (int i = 0; i < word.length(); i++) {
        if (puzzle.get(row, col + i) != null) {
          return false;
        }
      }
    } else if (path == 1) {
      if (row + word.length() > puzzle.height()) {
        return false;
      }
      for (int i = 0; i < word.length(); i++) {
        if (puzzle.get(row + i, col) != null) {
          return false;
        }
      }
    } else if (path == 2) {
      if ((col + word.length() > puzzle.width()) || (row + word.length() > puzzle.height())) {
        return false;
      }
      for (int i = 0; i < word.length(); i++) {
        if (puzzle.get(row + i, col + i) != null) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * fills the empty remaining squares of the wordsearch puzzle with random letters.
   * @param puzzle the puzzle which, at this point, only contains the words.
   */

  public static void fillRandom(MatrixV0<Character> puzzle) {
    Random rand = new Random();
    for (int i = 0; i < puzzle.height(); i++) {
      for (int j = 0; j < puzzle.width(); j++) {
        if (puzzle.get(i, j) == null) {
          char randomChar = (char) ('a' + rand.nextInt(26));
          puzzle.set(i, j, randomChar);
        }
      }
    }
    return;
  }
/**
 * prints the word search puzzle.
 * @param puzzle the word search puzzle we want to print.
 */
  public static void print(MatrixV0<Character> puzzle) {
    for (int i = 0; i < puzzle.height(); i++) {
      for (int j = 0; j < puzzle.width(); j++) {
        System.out.print(puzzle.get(i, j) + " ");
      }
      System.out.println();
    }
  }
}
