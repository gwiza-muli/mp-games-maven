package edu.grinnell.csc207.sample;

import java.util.Scanner;
import edu.grinnell.csc207.util.MatrixV0;
import edu.grinnell.csc207.util.WSUtils;

/**
 * A one-player game of word search.
 *
 * @author Sheilla Muligande
 * @author Princess Alexander
 */
public class WordSearch {

  /**
 * A sample one-player game. Intended as a potential
 * use of our Matrix interface.
 *
 * @param args command line arguments. 
 */
  public static void main(String[] args) {
    Scanner scnr = new Scanner(System.in);
    System.out.println("Wanna play word search? enter a number between 1 to 7 for the number of words.");

    int wordCount = scnr.nextInt();
    while (wordCount < 1 || wordCount > 7) {
      System.out.print("Wrong number of words! only enter between 1 and 7");
      wordCount = scnr.nextInt();
    }

    int puzzleDimensions = 7 + wordCount - 1; // MAKE SURE ALL WORDS ARE 7 OR FEWER // CHARACTERS!!!!!!!
    MatrixV0<Character> puzzle = new MatrixV0<>(puzzleDimensions, puzzleDimensions, null);

    String[] words = WSUtils.searchWords(wordCount);
    WSUtils.WSpopulator(puzzle, words);
    WSUtils.fillRandom(puzzle);
    WSUtils.print(puzzle);

    WSGame(puzzle, words);
  }

  /**
 * A sample one-player game (is that a puzzle?). Intended as a potential
 * use of our Matrix interface.
 *
 * @param puzzle the puzzle we use as our wordsearch.
 * @param words the words that the user will look for.
 */
  public static void WSGame(MatrixV0<Character> puzzle, String[] words) {
    Scanner scnr = new Scanner(System.in);
    String[] playerWords = new String[words.length];
    int playerScore = 0;

    System.out.println("PLEASE MAKE SURE TO ENTER ONLY lowercase WORDS!");

    while (true) {
      boolean wordInList = false;
      
      //WSUtils.print(puzzle);
      System.out.print("Enter word or 'exit'.\n");
      String userInput = scnr.nextLine().trim();
      if (userInput.equals("exit")) {
        break;
      }

      for (String word : words) {
        if (word.equals(userInput)) {
          boolean found = false;
          for (int i = 0; i < playerScore; i++) {
            if (playerWords[i].equals(userInput)) {
              found = true;
              break;
            }
          }
          if (!found) {
            playerWords[playerScore++] = userInput;
            System.out.println("You're good at this.");
            wordInList = true;
            break;
          }
        }
      }

      if (!wordInList) {
        System.out.println("already entered or not valid word");
      }

      if (playerScore == words.length) {
        System.out.println("Word Search Completed!");
        break;
      }

    }
    scnr.close();
  }

}
