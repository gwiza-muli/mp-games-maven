package edu.grinnell.csc207.util;

import java.util.Random;

public class WSUtils {

  // dictionary array
  public static String[] WORDS = {"hey", "this", "needs", "to", "grow"};


  public static String[] searchWords(int wordCount) {
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
      if (dup == false) {
        WSWords[words] = word;
        words++;
      }

    }
    return WSWords;
  }


  public static void WSpopulator(MatrixV0<Character> puzzle, String[] words) {

    Random rand = new Random();

    for (String word : words) {
      // int length = word.length();
      boolean size = false;
      while (size == true) {
        int startRow = rand.nextInt(puzzle.height());
        int startCol = rand.nextInt(puzzle.width());

        int path = rand.nextInt(3);

        if (lengthChecker(puzzle, word, startRow, startCol, path)) {
          for (int i = 0; i < word.length(); i++) {
            if (path == 0) {
              puzzle.set(startRow, startCol + i, word.charAt(i));
            } else if (path == 1) {
              puzzle.set(startRow + i, startCol, word.charAt(i));
            } else if (path == 2) {
              puzzle.set(startRow + i, startCol + i, word.charAt(i));
            }
          }
          size = true;
        }
      }
    }
  }


  public static boolean lengthChecker(MatrixV0<Character> puzzle, String word, int row, int col,
      int path) {
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

  public static void print(MatrixV0<Character> puzzle) {
    for (int i = 0; i < puzzle.height(); i++) {
      for (int j = 0; j < puzzle.width(); j++) {
        System.out.print(puzzle.get(i, j) + " ");
      }
      System.out.println();
    }
  }
}
