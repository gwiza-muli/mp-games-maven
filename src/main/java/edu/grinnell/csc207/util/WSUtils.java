package edu.grinnell.csc207.util;

import java.util.Random;

public class WSUtils {

  // dictionary array
   public static String[] WORDS = {"hey", "this", "needs", "to", "grow"};
  //public static String[] WORDS = {"HEY", "THIS", "NEEDS", "TO", "GROW"};

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
      boolean size = false;
      while (size == true) {
        int startRow = rand.nextInt(puzzle.height());
        int startCol = rand.nextInt(puzzle.width());

        int path = rand.nextInt(3);

        if (lengthChecker(puzzle, word, startRow, startCol, path)) {

          System.out.println("Placing word '" + word + "' at (" + startRow + ", " + startCol
              + ") in direction " + path); // debug

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


  public static boolean lengthChecker(MatrixV0<Character> puzzle, String word, int row, int col,int path) {

    System.out.println("Checking if word '" + word + "' can be placed at (" + row + ", " + col + ") in direction " + path);

    if (path == 0) {
      if (col + word.length() > puzzle.width()) {

        System.out.println("Word can't fit horizontally at position (" + row + ", " + col + ")");

        return false;
      }
      for (int i = 0; i < word.length(); i++) {
        if (puzzle.get(row, col + i) != null) {
          System.out.println("Position (" + row + ", " + (col + i) + ") is already occupied.");

          return false;
        }
      }
    } else if (path == 1) {
      if (row + word.length() > puzzle.height()) {
        System.out.println("Word can't fit vertically at position (" + row + ", " + col + ")");
        return false;
      }
      for (int i = 0; i < word.length(); i++) {
        if (puzzle.get(row + i, col) != null) {

          System.out.println("Position (" + (row + i) + ", " + col + ") is already occupied.");
          return false;
        }
      }
    } else if (path == 2) {
      if ((col + word.length() > puzzle.width()) || (row + word.length() > puzzle.height())) {
        System.out.println("Word can't fit diagonally at position (" + row + ", " + col + ")");
        return false;
      }
      for (int i = 0; i < word.length(); i++) {
        if (puzzle.get(row + i, col + i) != null) {
          System.out.println("Position (" + (row + i) + ", " + (col + i) + ") is already occupied.");
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
          System.out
              .println("Filling empty spot (" + i + ", " + j + ") with letter: " + randomChar); // Debug

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
