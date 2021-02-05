package edu.duke.adh39.battleship;

/**
 * This class record the 2D position that could represent a coordinate on the board
 * It is able to discriminate from valid and invalid input and parse them to corresponding
 * coordinate. It also override some basic method so we can have a uniform interface.
 */
public class Coordinate {
  private int row;
  private int column;

  /**
   * Initilize the coordinate based on straight input.
   * @param row and column
   */
  public Coordinate(int r, int c){
    this.row = r;
    this.column = c;
  }

  /**
   * Initilize the coordinate based on string text.
   * @param input string to parse
   * @throws IllegalArgumentException when invalid input encountered
   */
  public Coordinate(String descr) throws IllegalArgumentException {
    if (descr.length() != 2){
      throw new IllegalArgumentException("Invalid coordinate length.");
    }
    descr = descr.toUpperCase();
    char rowC = descr.charAt(0);
    char columnC = descr.charAt(1);
    if (rowC < 'A' || rowC > 'Z'){
      throw new IllegalArgumentException("Invalid coordinate row.");
    }
    if (columnC < '0' || columnC > '9'){
      throw new IllegalArgumentException("Invalid coordinate column.");
    }
    this.row = rowC - 'A';
    this.column = columnC - '0';
  }

  
  @Override
  public boolean equals(Object o) {
    if (o.getClass().equals(getClass())) {
      Coordinate c = (Coordinate) o;
      return row == c.row && column == c.column;
    }
    return false;
  }

  @Override
  public String toString() {
    return "("+row+", " + column+")";
  }
  @Override
  public int hashCode() {
    return toString().hashCode();
  }

  public int getRow() {
    return row;
  }

  public int getColumn() {
    return column;
  }

}












