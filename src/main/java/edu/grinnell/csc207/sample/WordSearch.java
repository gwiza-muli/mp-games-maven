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
  * A sample one-player game that allows the user to search for words in a word search puzzle.
  * The player enters words to find in the puzzle and receives feedback on whether the word
  * is correct, already found, or not in the word list.
  *
  * @param puzzle the word search puzzle to be solved by the player.
  * @param words the list of words to be found in the puzzle.
  */

  public static void wsGame(MatrixV0<Character> puzzle, String[] words) {
    Scanner scnr = new Scanner(System.in);
    String[] playerWords = new String[words.length]; // Array to track words found by the player
    int playerScore = 0; // Keeps track of the number of correct words found

    System.out.println("PLEASE MAKE SURE TO ENTER ONLY lowercase WORDS!");

    while (true) {
      boolean wordInList = false; // Flag to check if the word entered is in the word list

      // Prompt the user to enter a word or exit the game
      System.out.print("Enter word or 'exit'.\n");
      String userInput = scnr.nextLine().trim();

      // Exit condition for the game if the player types 'exit'
      if (userInput.equals("exit")) {
        break; // Exit the game loop
      } // if

      // Check if the word entered by the user is part of the words list
      for (String word : words) { // Loop through each word in the words list
        if (word.equals(userInput)) { // If the word matches an entry in the word list
          wordInList = true; // Set the flag to true indicating the word is valid
          boolean found = false; // Flag to check if the word has already been found

          // Check if the word was already found by the player
          for (int i = 0; i < playerScore; i++) {
            if (playerWords[i].equals(userInput)) { // If the word was already found
              found = true; // Set the flag to true
              break; // Exit the loop as the word has been found before
            } // if
          } // for

          // If the word was not found before, add it to the list of found words
          if (!found) {
            playerWords[playerScore++] = userInput; // Add the word to playerWords[] and increment score
            System.out.println("Correct! Keep going!"); // Inform the player they guessed correctly
          } else {
            System.out.println("You already found that word!"); // tell user word was found
          } // if-else

          break; // Exit the loop after processing a valid word
        } // if
      } // for

      // If the word wasn't found in the list, inform the player
      if (!wordInList) {
        System.out.println("That word is not in the word search! Try again."); // Invalid word feedback
      } // if
    } // while
  } // wsGame
} // WordSearch
