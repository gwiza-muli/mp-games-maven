package edu.grinnell.csc207.sample;

import edu.grinnell.csc207.util.ArrayUtils;
import edu.grinnell.csc207.util.IOUtils;
import edu.grinnell.csc207.util.Matrix;
import edu.grinnell.csc207.util.MatrixV0;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * A sample one-player game demonstrating the use of the Matrix interface.
 * The game allows for simple manipulations of the board to achieve goals.
 *
 * @author Samuel A. Rebelsky
 */
public class Game1P {
  // +-----------+---------------------------------------------------
  // | Constants |
  // +-----------+

  /** The default width of the game board. */
  static final int DEFAULT_WIDTH = 10;

  /** The default height of the game board. */
  static final int DEFAULT_HEIGHT = 8;

  // +----------------+----------------------------------------------
  // | Helper methods |
  // +----------------+

  /**
   * Print the game instructions to the given PrintWriter.
   *
   * @param pen The printwriter used to print the instructions.
   */
  public static void printInstructions(PrintWriter pen) {
    pen.println("""
                Welcome to the sample one-player game.

                Command-line arguments:

                * -w width - set up the width of the board
                * -h height - set up the height of the board
                * -s game-number - choose the game setup number (useful if
                  you want to play the same setup multiple times).

                Your game board is a grid of X's, O's, *'s, and blanks.

                Your goal is to eliminate as many X's as possible while
                keeping as many O's as possible.

                You have four basic moves. You can do each move up to
                three times. You can also SKIP a step and just let the
                *'s destroy things.

                * RR: remove a row
                * RC: remove a column
                * IR: insert a blank row
                * IC: insert a blank column
                * SKIP: Do nothing, just let the *'s move.

                After each move, any *'s eliminate one neighboring piece
                and move over that piece, using the following priority grid.

                    1|6|7
                    -+-+-
                    5|*|4
                    -+-+-
                    8|3|2
                """);
  } // printInstructions(PrintWriter)

  /**
   * Print the final results of the game, displaying remaining Xs and Os.
   *
   * @param pen   The PrintWriter to use for output.
   * @param board The game board at the end.
   */
  static void printResults(PrintWriter pen, Matrix<String> board) {
    int xs = 0;
    int os = 0;
    for (int row = 0; row < board.height(); row++) {
      for (int col = 0; col < board.width(); col++) {
        String cell = board.get(row, col);
        if ("O".equals(cell)) {
          os++;
        } else if ("X".equals(cell)) {
          xs++;
        } //elseif
      } //for
    } //for
    pen.println();
    pen.println("Xs remaining: " + xs);
    pen.println("Os remaining: " + os);
    pen.println("Score: " + (os - xs));
  } // printResults

  /**
   * Process the board, simulating the elimination of cells based on game rules.
   *
   * @param board The board to process.
   */
  static void process(Matrix<String> board) {
    int[][] offsets = {{-1, -1}, {1, 1}, {1, 0}, {0, 1}, {0, -1}, {-1, 0}, {-1, 1}, {1, -1}};
    String newStar = ".";
    String oldStar = ":";
    String repStar = "@";

    // Search for *'s and process neighboring cells
    for (int row = 0; row < board.height(); row++) {
      for (int col = 0; col < board.width(); col++) {
        String cell = board.get(row, col);
        if ("*".equals(cell) || repStar.equals(cell)) {
          for (int[] offset : offsets) {
            int newRow = row + offset[0];
            int newCol = col + offset[1];
            try {
              String neighbor = board.get(newRow, newCol);
              if ("X".equals(neighbor) || "O".equals(neighbor)) {
                board.set(row, col, oldStar);
                board.set(newRow, newCol, newStar);
                break;
              } //if
            } catch (IndexOutOfBoundsException e) {
              // Continue if the neighboring cell is out of bounds.
            } //try
          } //for
        } //if
      } //for
    } //for

    // Clean up temporary markers and finalize the board state
    for (int row = 0; row < board.height(); row++) {
      for (int col = 0; col < board.width(); col++) {
        String cell = board.get(row, col);
        if (oldStar.equals(cell)) {
          board.set(row, col, " ");
        } else if (newStar.equals(cell) || repStar.equals(cell)) {
          board.set(row, col, "*");
        } //elseif
      } //for
    } //for
  } // process(Matrix<String>)

  /**
   * Set up a new game board with the given dimensions and game number.
   *
   * @param width  The width of the board.
   * @param height The height of the board.
   * @param game   The game number used for randomness.
   * @return The initialized game board.
   */
  static Matrix<String> setupBoard(int width, int height, int game) {
    Random setup = new Random(game);
    Matrix<String> board = new MatrixV0<>(width, height, " ");
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        double rand = setup.nextDouble();
        if (rand < 0.10) {
          board.set(row, col, "*");
        } else if (rand < 0.25) {
          board.set(row, col, "X");
        } else if (rand < 0.4) {
          board.set(row, col, "O");
        } //for
      } //for
    } //for
    return board;
  } // setupBoard(int, int, int)

  // +------+--------------------------------------------------------
  // | Main |
  // +------+

  /**
   * Main method to run the game.
   *
   * @param args Command-line arguments for configuring the game.
   */
  public static void main(String[] args) throws IOException {
    PrintWriter pen = new PrintWriter(System.out, true);
    BufferedReader eyes = new BufferedReader(new InputStreamReader(System.in));

    int rrRemaining = 2;
    int rcRemaining = 2;
    int icRemaining = 2;
    int irRemaining = 2;
    int width = DEFAULT_WIDTH;
    int height = DEFAULT_HEIGHT;
    Random rand = new Random();
    int game = rand.nextInt();

    // Process the command-line arguments
    for (int i = 0; i < args.length; i++) {
      switch (args[i]) {
        case "-w":
          try {
            width = Integer.parseInt(args[++i]);
          } catch (Exception e) {
            System.err.printf("Invalid width: %s (not an integer)\n", args[i]);
            return;
          } //catch
          if (width < 4) {
            System.err.printf("Invalid width: %s (less than 4)\n", width);
            return;
          } //if
          break;

        case "-h":
          try {
            height = Integer.parseInt(args[++i]);
          } catch (Exception e) {
            System.err.printf("Invalid height: %s (not an integer)\n", args[i]);
            return;
          } //catch
          if (height < 4) {
            System.err.printf("Invalid height: %s (less than 4)\n", height);
            return;
          } //if
          break;

        case "-s":
          try {
            game = Integer.parseInt(args[++i]);
          } catch (Exception e) {
            System.err.printf("Invalid game number: %s\n", args[i]);
            return;
          } //catch
          break;

        default:
          System.err.printf("Invalid command-line flag: %s\n", args[i]);
          return;
      } //switch
    } //for

    // Display instructions and prompt the user
    printInstructions(pen);
    pen.print("Hit return to continue");
    pen.flush();
    eyes.readLine();

    // Set up the board and start the game
    Matrix<String> board = setupBoard(width, height, game);
    pen.println("Game setup number " + game);
    pen.println();

    // Main game loop (omitted for brevity; should be further refined)
    // ...
  } // main(String[])
} //Game1P
