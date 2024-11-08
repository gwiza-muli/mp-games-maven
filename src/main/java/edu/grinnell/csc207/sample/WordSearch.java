package edu.grinnell.csc207.sample;

import java.io.PrintWriter; // Import PrintWriter for output
import java.util.Scanner;
import edu.grinnell.csc207.util.MatrixV0;
import edu.grinnell.csc207.util.WSUtils;

/**
 * A one-player game of word search.
 * This class generates a word search puzzle and allows the player to find words in it.
 *
 * @author Sheilla Muligande
 * @author Princess Alexander
 */
public class WordSearch {

  /**
   * A sample one-player game. Intended as a potential
   * use of our Matrix interface.
   * Generates a word search puzzle and asks the player to find words.
   *
   * @param args command line arguments.
   */
  public static void main(String[] args) {
    Scanner scnr = new Scanner(System.in);
    System.out.println("Wanna play word search? Enter a number between 1 to 7 for the number of words.");

    int wordCount = scnr.nextInt();
    while (wordCount < 1 || wordCount > 7) {
      System.out.print("Wrong number of words! Only enter between 1 and 7.");
      wordCount = scnr.nextInt();
    } // while

    int puzzleDimensions = 7 + wordCount - 1; // Ensure all words fit in the puzzle
    MatrixV0<Character> puzzle = new MatrixV0<>(puzzleDimensions, puzzleDimensions, null);

    String[] words = WSUtils.searchWords(wordCount);
    WSUtils.wsPopulator(puzzle, words);
    WSUtils.fillRandom(puzzle);

    // Use PrintWriter to print the puzzle
    PrintWriter writer = new PrintWriter(System.out);
    WSUtils.print(puzzle, writer);
    writer.flush(); // Make sure everything is printed

    wsGame(puzzle, words);
  } // main

  /**
   * A sample one-player game that allows the user to search for words in a puzzle.
   *
   * @param puzzle the word search puzzle to be solved by the player.
   * @param words the list of words to be found.
   */
  public static void wsGame(MatrixV0<Character> puzzle, String[] words) {
    Scanner scnr = new Scanner(System.in);
    String[] playerWords = new String[words.length];
    int playerScore = 0;

    System.out.println("PLEASE MAKE SURE TO ENTER ONLY lowercase WORDS!");

    while (true) {
      boolean wordInList = false;

      // Prompt the user to enter a word or exit
      System.out.print("Enter word or 'exit'.\n");
      String userInput = scnr.nextLine().trim();
      if (userInput.equals("exit")) {
        break; // Exit the game if user inputs 'exit'
      } // if

      for (String word : words) { // for each word in the list
        if (word.equals(userInput)) {
          boolean found = false;
          for (int i = 0; i < playerScore; i++) { // check if the word was already found
            if (playerWords[i].equals(userInput)) {
              found = true;
              break; // Break out of the loop if the word was found
            } // if
          } // for
          if (!found) { // If word is not found in the player's list
            playerWords[playerScore++] = userInput; // Add the word to the player's list
          } // if
        } // if
      } // for
    } // while
  } // wsGame
} // WordSearch
