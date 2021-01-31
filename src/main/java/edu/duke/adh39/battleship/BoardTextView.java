package edu.duke.adh39.battleship;

/**
 * This class handles textual display of a Board (i.e., converting it to a
 * string to show to the user). It supports two ways to display the Board: one
 * for the player's own board, and one for the enemy's board.
 */
public class BoardTextView {
  /**
   * The Board to display
   */
  private final Board toDisplay;

  /**
   * Constructs a BoardView, given the board it will display.
   * 
   * @param toDisplay is the Board to display
   */
  public BoardTextView(Board toDisplay) {
    this.toDisplay = toDisplay;
    if (toDisplay.getWidth() > 10 || toDisplay.getHeight() > 26) {
      throw new IllegalArgumentException(
          "Board must be no larger than 10x26, but is " + toDisplay.getWidth() + "x" + toDisplay.getHeight());
    }

  }

  public String displayMyOwnBoard() {
    String result = makeHeader();
    for (int i = 0; i < toDisplay.getHeight(); i++) {
      result += makeLines(i);
    }
    result += makeHeader();
    return result;
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

  private String makeLines(int row) {
    char Mark = 'A';
    Mark += row;
    StringBuilder ans = new StringBuilder(""); // README shows two spaces at
    String sep = ""; // start with nothing to separate, then switch to | to separate
    ans.append("" + Mark + " ");
    for (int i = 0; i < toDisplay.getWidth(); i++) {
      ans.append(sep);
      ans.append(" ");
      sep = "|";
    }
    ans.append("" + " " + Mark);
    ans.append("\n");
    return ans.toString();
  }

}