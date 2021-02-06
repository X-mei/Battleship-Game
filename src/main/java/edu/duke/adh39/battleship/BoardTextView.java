package edu.duke.adh39.battleship;

import java.util.function.Function;

/**
 * This class handles textual display of a Board (i.e., converting it to a
 * string to show to the user). It supports two ways to display the Board: one
 * for the player's own board, and one for the enemy's board.
 */
public class BoardTextView {
  /**
   * The Board to display
   */
  private final Board<Character> toDisplay;

  /**
   * Constructs a BoardView, given the board it will display.
   * 
   * @param toDisplay is the Board to display
   */
  public BoardTextView(Board<Character> toDisplay) {
    this.toDisplay = toDisplay;
    if (toDisplay.getWidth() > 10 || toDisplay.getHeight() > 26) {
      throw new IllegalArgumentException(
          "Board must be no larger than 10x26, but is " + toDisplay.getWidth() + "x" + toDisplay.getHeight());
    }

  }

  /**
   * This is helper to create the board view
   * @param the function to use when creating the body of the text view(self or enemy)
   * @return the board view as a string.
   */
  protected String displayAnyBoard(Function<Coordinate, Character> getSquareFn) {
    String result = makeHeader();
    for (int i = 0; i < toDisplay.getHeight(); i++) {
      result += makeLines(i, getSquareFn);
    }
    result += makeHeader();
    return result;
  }

  /**
   * This create the board of the board of current user
   * @return the board view as a string.
   */
  public String displayMyOwnBoard() {
    return displayAnyBoard((c)->toDisplay.whatIsAtForSelf(c));
  }

  /**
   * This create the board of the board of enemy user
   * @return the board view as a string.
   */
  public String displayEnemyBoard() {
    return displayAnyBoard((c)->toDisplay.whatIsAtForEnemy(c));
  }

  /**
   * This concatenate two board text view into one huge string
   * @param the view of enemie's board, the header for this board and the header to opponent's board
   * @return the combined board view as a string.
   */
  public String displayMyBoardWithEnemyNextToIt(BoardTextView enemyView, String myHeader, String enemyHeader) {
    String myBoard = this.displayMyOwnBoard();
    String [] myLines = myBoard.split("\n");
    String enemyBoard = enemyView.displayEnemyBoard();
    String [] enemyLines = enemyBoard.split("\n");
    assert(myLines.length == enemyLines.length);
    StringBuilder builder = new StringBuilder();
    builder.append("\n");
    builder.append("    ");
    builder.append(myHeader);
    int spaces = 2*toDisplay.getWidth()+27-myHeader.length();
    while (spaces-->0) {
      builder.append(" ");
    }
    builder.append(enemyHeader);
    builder.append("\n");
    for (int i = 0; i < myLines.length; i++){
      builder.append(myLines[i]);
      if (i == 0 || i == myLines.length - 1) {
        builder.append("  ");
      }
      int spaceCount = 26;
      while (spaceCount-->0) {
        builder.append(" ");
      }
      builder.append(enemyLines[i]);
      builder.append("\n");
    }
    return builder.toString();
  }

  /**
   * This makes the header line, e.g. 0|1|2|3|4\n
   * 
   * @return the String that is the header line for the given board
   */
  String makeHeader() {
    StringBuilder ans = new StringBuilder("  "); // README shows two spaces at
    String sep = ""; // start with nothing to separate, then switch to | to separate
    for (int i = 0; i < toDisplay.getWidth(); i++) {
      ans.append(sep);
      ans.append(i);
      sep = "|";
    }
    ans.append("\n");
    return ans.toString();
  }

  /**
   * This makes the data line.
   * @return the row to create and function to call to create it
   */
  private String makeLines(int row, Function<Coordinate, Character> getSquareFn) {
    char Mark = 'A';
    Mark += row;
    StringBuilder ans = new StringBuilder(""); // README shows two spaces at
    String sep = ""; // start with nothing to separate, then switch to | to separate
    ans.append("" + Mark + " ");
    for (int col = 0; col < toDisplay.getWidth(); col++) {
      ans.append(sep);
      Coordinate c = new Coordinate(row, col);
      Character chr = getSquareFn.apply(c);
      if (chr == null){
        ans.append(" ");
      }
      else {
        ans.append(chr);
      }
      sep = "|";
      
    }
    ans.append("" + " " + Mark);
    ans.append("\n");
    return ans.toString();
  }

}







