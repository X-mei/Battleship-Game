package edu.duke.adh39.battleship;

public class Coordinate {
  private int row;
  
  private int column;
  public Coordinate(int r, int c){
    this.row = r;
    this.column = c;
  }

  public Coordinate(String descr){
    if (descr.length() != 2){
      throw new IllegalArgumentException("Invalid coordinate input.");
    }
    descr.toUpperCase();
    char rowC = descr.charAt(0);
    char columnC = descr.charAt(1);
    if (rowC < 'A' || rowC > 'Z'){
      throw new IllegalArgumentException("Invalid coordinate input.");
    }
    if (columnC < '0' || columnC > '9'){
      throw new IllegalArgumentException("Invalid coordinate input.");
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

  public void setRow(int row) {
    this.row = row;
  }

  public int getColumn() {
    return column;
  }

  public void setColumn(int column) {
    this.column = column;
  }


}












