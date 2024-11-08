package edu.grinnell.csc207.util;
import java.io.PrintWriter;

import java.util.Random;

/**
 * Utility methods for the word search game.
 * Provides methods for generating and populating word search puzzles.
 *
 * @author Sheilla Muligande
 * @author Princess Alexander
 */
public class WSUtils {
  /**
   * Maximum number of words allowed in the word search puzzle.
   */
  public static final int MAX_WORDS = 10;

  /**
   * The maximum length of a word that can be placed in the word search puzzle.
   */
  public static final int WORD_LENGTH = 5;

  /**
   * Maximum number of attempts to find space for a word in the puzzle.
   */
  public static final int MAX_ATTEMPTS = 100;

  /**
   * Size of the English alphabet used for generating random letters.
   */
  public static final int ALPHABET_SIZE = 26;

  /**
   * Array of words to add to the word search puzzle.
   */
  public static String[] wordsList = {"hello", "this", "needs", "to", "grow", "hi", "good", "night"};

  /**
   * Selects words randomly from the wordsList array to be used in the word search.
   *
   * @param wordCount the number of words to include in the word search.
   * @return an array of words selected for the word search.
   * @throws IllegalArgumentException if the wordCount exceeds MAX_WORDS.
   */
  public static String[] searchWords(int wordCount) {
    // Ensure wordCount doesn't exceed MAX_WORDS
    if (wordCount > MAX_WORDS) {
      throw new IllegalArgumentException("Word count exceeds the maximum limit.");
    } //if

    String[] wsWords = new String[wordCount];
    Random rand = new Random();
    int words = 0;

    while (words < wordCount) {
      String word = wordsList[rand.nextInt(wordsList.length)];
      boolean dup = false;

      for (int i = 0; i < words; i++) {
        if (wsWords[i].equals(word)) {
          dup = true;
        } // for
      } // end for

      if (!dup) {
        wsWords[words] = word;
        words++;
      } // if
    } // while

    return wsWords;
  } // searchWords

  /**
   * Adds the selected words to the puzzle.
   *
   * @param puzzle the empty puzzle grid to which words will be added.
   * @param words the words to be added to the puzzle.
   */
  public static void wsPopulator(MatrixV0<Character> puzzle, String[] words) {
    Random rand = new Random();

    for (String word : words) {
      boolean size = false;
      while (!size) {
        int startRow = rand.nextInt(puzzle.height());
        int startCol = rand.nextInt(puzzle.width());
        int path = rand.nextInt(3); // 0 = horizontal, 1 = vertical, 2 = diagonal

        if (lengthChecker(puzzle, word, startRow, startCol, path)) {
          for (int i = 0; i < word.length(); i++) {
            if (path == 0) {
              puzzle.set(startRow, startCol + i, word.charAt(i)); // Horizontal placement
            } else if (path == 1) {
              puzzle.set(startRow + i, startCol, word.charAt(i)); // Vertical placement
            } else if (path == 2) {
              puzzle.set(startRow + i, startCol + i, word.charAt(i)); // Diagonal placement
            } // if-else
          } // for
          size = true;
        } // if
      } // while
    } // for
  } // wsPopulator

  /**
   * Checks if a word can be placed in the specified location of the puzzle.
   *
   * @param puzzle the word search grid.
   * @param word the word to place.
   * @param row the starting row for placement.
   * @param col the starting column for placement.
   * @param path the path for placement: 0 (horizontal), 1 (vertical), 2 (diagonal).
   * @return true if the word can be placed, false otherwise.
   */
  public static boolean lengthChecker(MatrixV0<Character> puzzle, String word, int row, int col, int path) {
    if (path == 0) { // Horizontal
      if (col + word.length() > puzzle.width()) {
        return false;
      } //if
      for (int i = 0; i < word.length(); i++) {
        if (puzzle.get(row, col + i) != null) {
          return false;
        } // if
      } // for
    } else if (path == 1) { // Vertical
      if (row + word.length() > puzzle.height()) {
        return false;
      } //else if
      for (int i = 0; i < word.length(); i++) {
        if (puzzle.get(row + i, col) != null) {
          return false;
        } // if
      } // for
    } else if (path == 2) { // Diagonal
      if ((col + word.length() > puzzle.width()) || (row + word.length() > puzzle.height())) {
        return false;
      } //if
      for (int i = 0; i < word.length(); i++) {
        if (puzzle.get(row + i, col + i) != null) {
          return false;
        } // if
      } // for
    } // if-else

    return true;
  } // lengthChecker

  /**
   * Fills the empty squares of the puzzle with random letters.
   *
   * @param puzzle the word search puzzle to fill.
   */
  public static void fillRandom(MatrixV0<Character> puzzle) {
    Random rand = new Random();
    for (int i = 0; i < puzzle.height(); i++) {
      for (int j = 0; j < puzzle.width(); j++) {
        if (puzzle.get(i, j) == null) {
          char randomChar = (char) ('a' + rand.nextInt(ALPHABET_SIZE));
          puzzle.set(i, j, randomChar);
        } // if
      } // for
    } // for
  } // fillRandom

  /**
   * Prints the word search puzzle using PrintWriter.
   *
   * @param puzzle the word search puzzle to print.
   * @param writer the PrintWriter object to handle output.
   */
  public static void print(MatrixV0<Character> puzzle, PrintWriter writer) {
    for (int i = 0; i < puzzle.height(); i++) {
      for (int j = 0; j < puzzle.width(); j++) {
        writer.print(puzzle.get(i, j) + " "); // Using writer.print instead of System.out.println
      } //for
      writer.println(); // Adds a new line at the end of each row
    } //for
  } //print
} // WSUtils

