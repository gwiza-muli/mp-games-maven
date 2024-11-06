package edu.grinnell.csc207.util;

/**
 * An implementation of two-dimensional matrices.
 *
 * @author Your Name Here
 * @author Samuel A. Rebelsky
 *
 * @param <T> The type of values stored in the matrix.
 */
public class MatrixV0<T> implements Matrix<T> {
  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+
  /** the matrix as an array of arrays. */
  T[][] matrix;

  /** the width of the matrix. */
  int width;

  /** the height of the matrix. */
  int height;

  /**the default value that will fill the matrix. */
  T defaultVal;
  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new matrix of the specified width and height with the given value as the default.
   *
   * @param width The width of the matrix.
   * @param height The height of the matrix.
   * @param def The default value, used to fill all the cells.
   *
   * @throws NegativeArraySizeException If either the width or height are negative.
   */
  public MatrixV0(int width, int height, T def) {
    this.width = width;
    this.height = height;
    this.defaultVal = def;
    this.matrix = (T[][]) new Object[height][width];

    for (int h = 0; h < height; h++) {
      for (int w = 0; w < width; w++) {
        matrix[h][w] = def;
      } //for
    } //for

  } // MatrixV0(int, int, T)

  /**
   * Create a new matrix of the specified width and height with null as the default value.
   *
   * @param width The width of the matrix.
   * @param height The height of the matrix.
   *
   * @throws NegativeArraySizeException If either the width or height are negative.
   */
  public MatrixV0(int width, int height) {
    this(width, height, null);
    this.width = width;
    this.height = height;
    this.matrix = (T[][]) new Object[height][width];
    for (int h = 0; h < height; h++) {
      for (int w = 0; w < width; w++) {
        matrix[h][w] = null;
      } //for
    } //for
  } // MatrixV0

  // +--------------+------------------------------------------------
  // | Core methods |
  // +--------------+

  /**
   * Get the element at the given row and column.
   *
   * @param row The row of the element.
   * @param col The column of the element.
   *
   * @return the value at the specified location.
   *
   * @throws IndexOutOfBoundsException If either the row or column is out of reasonable bounds.
   */
  public T get(int row, int col) {
    if (row >= this.height || row < 0 || col >= this.width || col < 0) {
      throw new IndexOutOfBoundsException();
    } else {
      return matrix[row][col];
    } // if
  } // get(int, int)

  /**
   * Set the element at the given row and column.
   *
   * @param row The row of the element.
   * @param col The column of the element.
   * @param val The value to set.
   *
   * @throws IndexOutOfBoundsException If either the row or column is out of reasonable bounds.
   */
  public void set(int row, int col, T val) {
    if (row >= this.height || row < 0 || col >= this.width || col < 0) {
      throw new IndexOutOfBoundsException();
    } else {
      matrix[row][col] = val;
    } // if
  } // set(int, int, T)

  /**
   * Determine the number of rows in the matrix.
   *
   * @return the number of rows.
   */
  public int height() {
    return this.height;
  } // height()

  /**
   * Determine the number of columns in the matrix.
   *
   * @return the number of columns.
   */
  public int width() {
    return this.width;
  } // width()

  /**
   * Insert a row filled with the default value.
   *
   * @param row The number of the row to insert.
   *
   * @throws IndexOutOfBoundsException If the row is negative or greater than the height.
   */
  public void insertRow(int row) {
    if (row < 0 || row > this.height) {
      throw new IndexOutOfBoundsException();
    } //if
    T[][] matrixCopy = (T[][]) new Object[this.height + 1][width];
    for (int h = 0; h < matrixCopy.length; h++) {
      if (h < row) {
        matrixCopy[h] = this.matrix[h];
      } else if (h == row) {
        for (int w = 0; w < this.width; w++) {
          matrixCopy[h][w] = this.defaultVal;
        } //for
      } else {
        matrixCopy[h] = this.matrix[h - 1];
      } // if
    } //for
    matrix = matrixCopy;
    this.height++;
  } //insertRow(int)

  /**
   * Insert a row filled with the specified values.
   *
   * @param row The number of the row to insert.
   * @param vals The values to insert.
   *
   * @throws IndexOutOfBoundsException If the row is negative or greater than the height.
   * @throws ArraySizeException If the size of vals is not the same as the width of the matrix.
   */
  public void insertRow(int row, T[] vals) throws ArraySizeException {
    if (row < 0 || row > this.height) {
      throw new IndexOutOfBoundsException();
    } // if
    if (vals.length != this.width) {
      throw new ArraySizeException();
    } // if
    insertRow(row);
    for (int w = 0; w < this.width; w++) {
      this.matrix[row][w] = vals[w];
    } //for

  } // insertRow(int, T[])

  /**
   * Insert a column filled with the default value.
   *
   * @param col The number of the column to insert.
   *
   * @throws IndexOutOfBoundsException If the column is negative or greater than the width.
   */
  public void insertCol(int col) {
    if (col < 0 || col > this.width) {
      throw new IndexOutOfBoundsException();
    } // if
    T[][] matrixCopy = (T[][]) new Object[this.height][width + 1];
    for (int h = 0; h < this.height; h++) {
      for (int w = 0; w < matrixCopy[h].length; w++) {
        if (w < col) {
          matrixCopy[h][w] = this.matrix[h][w];
        } else if (w == col) {
          matrixCopy[h][w] = defaultVal;
        } else {
          matrixCopy[h][w] = this.matrix[h][w - 1];
        } // if
      } //for
    } //for
    this.matrix = matrixCopy;
    this.width++;
  } // insertCol(int)

  /**
   * Insert a column filled with the specified values.
   *
   * @param col The number of the column to insert.
   * @param vals The values to insert.
   *
   * @throws IndexOutOfBoundsException If the column is negative or greater than the width.
   * @throws ArraySizeException If the size of vals is not the same as the height of the matrix.
   */
  public void insertCol(int col, T[] vals) throws ArraySizeException {
    if (col < 0 || col > this.width) {
      throw new IndexOutOfBoundsException();
    } //if
    if (vals.length != this.height) {
      throw new ArraySizeException();
    } //if
    insertCol(col);
    for (int h = 0; h < this.height; h++) {
      this.matrix[h][col] = vals[h];
    } //for


  } // insertCol(int, T[])

  /**
   * Delete a row.
   *
   * @param row The number of the row to delete.
   *
   * @throws IndexOutOfBoundsException If the row is negative or greater than or equal to the
   *         height.
   */
  public void deleteRow(int row) {
    if (row < 0 || row > this.height) {
      throw new IndexOutOfBoundsException();
    } //if

    T[][] matrixCopy = (T[][]) new Object[this.height - 1][width];
    for (int h = 0, w = 0; h < matrixCopy.length; h++, w++) {
      if (h == row) {
        w++;
      } //if
      matrixCopy[h] = this.matrix[w];
    } //for
    this.matrix = matrixCopy;
    this.height--;
  } // deleteRow(int)

  /**
   * Delete a column.
   *
   * @param col The number of the column to delete.
   *
   * @throws IndexOutOfBoundsException If the column is negative or greater than or equal to the
   *         width.
   */
  public void deleteCol(int col) {
    if (col < 0 || col >= this.width) {
      throw new IndexOutOfBoundsException();
    } //if
    T[][] matrixCopy = (T[][]) new Object[this.height][this.width - 1];
    for (int h = 0; h < this.height; h++) {
      for (int w = 0; w < this.width; w++) {
        if (w < col) {
          matrixCopy[h][w] = this.matrix[h][w];
        } else if (w > col) {
          matrixCopy[h][w - 1] = this.matrix[h][w];
        } //if
      } //for
    } //for
    this.matrix = matrixCopy;
    this.width--;
  } // deleteCol(int)

  /**
   * Fill a rectangular region of the matrix.
   *
   * @param startRow The top edge / row to start with (inclusive).
   * @param startCol The left edge / column to start with (inclusive).
   * @param endRow The bottom edge / row to stop with (exclusive).
   * @param endCol The right edge / column to stop with (exclusive).
   * @param val The value to store.
   *
   * @throw IndexOutOfBoundsException If the rows or columns are inappropriate.
   */
  public void fillRegion(int startRow, int startCol, int endRow, int endCol, T val) {
    for (int h = startRow; h < endRow; h++) {
      for (int w = startCol; w < endCol; w++) {
        if (h >= 0 && h < this.height && w >= 0 && w < this.width) {
          this.matrix[h][w] = val;
        } //if
      } //for
    } //for
  } // fillRegion(int, int, int, int, T)

  /**
   * Fill a line (horizontal, vertical, diagonal).
   *
   * @param startRow The row to start with (inclusive).
   * @param startCol The column to start with (inclusive).
   * @param deltaRow How much to change the row in each step.
   * @param deltaCol How much to change the column in each step.
   * @param endRow The row to stop with (exclusive).
   * @param endCol The column to stop with (exclusive).
   * @param val The value to store.
   *
   * @throw IndexOutOfBoundsException If the rows or columns are inappropriate.
   */
  public void fillLine(int startRow, int startCol, int deltaRow, int deltaCol, int endRow,
      int endCol, T val) {
    int curRow = startRow;
    int curCol = startCol;

    while (curRow != endRow && curCol != endCol) {
      this.set(curRow, curCol, val);
      curRow = curRow + deltaRow;
      curCol = curCol + deltaCol;
    } //while
  } // fillLine(int, int, int, int, int, int, T)

  /**
   * A make a copy of the matrix. May share references (e.g., if individual elements are mutable,
   * mutating them in one matrix may affect the other matrix) or may not.
   *
   * @return a copy of the matrix.
   */

  public Matrix clone() {
    Matrix<T> copyMatrix = new MatrixV0(height(), width(), null);
    for (int h = 0; h < this.height(); h++) {
      for (int w = 0; w < this.width(); w++) {
        copyMatrix.set(h, w, this.get(h, w));
      } //for
    } //for
    return copyMatrix;
  } // clone()

  /**
   * Determine if this object is equal to another object.
   *
   * @param other The object to compare.
   *
   * @return true if the other object is a matrix with the same width, height, and equal elements;
   *         false otherwise.
   */
  public boolean equals(Object other) {
    if (other instanceof Matrix) {
      Matrix otherMatrix = (Matrix) other;
      if (this.height() != otherMatrix.height() || this.width() != otherMatrix.width()) {
        return false;
      } //if
      for (int h = 0; h < this.height(); h++) {
        for (int w = 0; w < this.width(); w++) {
          T val1 = this.get(h, w);
          T val2 = (T) otherMatrix.get(h, w);
          if (val1 == null) {
            if (val2 != null) {
              return false;
            } //if
          } else if (!val1.equals(val2)) {
            return false;
          } //if
        } //for
      } //for
      return true;
    } else {
      // If it's not a matrix, it's not equal.
      return false;
    } //if
  } // equals(Object)

  /**
   * Compute a hash code for this matrix. Included because any object that implements `equals` is
   * expected to implement `hashCode` and ensure that the hash codes for two equal objects are the
   * same.
   *
   * @return the hash code.
   */
  public int hashCode() {
    int multiplier = 7;
    int code = this.width() + multiplier * this.height();
    for (int row = 0; row < this.height(); row++) {
      for (int col = 0; col < this.width(); col++) {
        T val = this.get(row, col);
        if (val != null) {
          // It's okay if the following computation overflows, since
          // it will overflow uniformly.
          code = code * multiplier + val.hashCode();
        } //if
      } // for col
    } // for row
    return code;
  } // hashCode()
} // class MatrixV0
