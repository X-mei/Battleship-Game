package edu.duke.adh39.battleship;

/**
 * This interface represents any type of Board in our Battleship game. It is
 * generic in typename T, which is the type of information the view needs to
 * display this ship.
 */
public interface Board<T> {
  /**
   * Getters for the width of board
   * @return width of board.
   */
  public int getWidth();

  /**
   * Getters for the height of board
   * @return height of board.
   */
  public int getHeight();

  /**
   * This function tries to add a ship and determines whether its ok.
   * @param the ship to be added.
   * @return true is its valid to add ship.
   */
  public boolean tryAddShip(Ship<T> toAdd);

  /**
   * This function returns the information at a given coordinate
   * @param the location to check.
   * @return the information at the location in typename T.
   */
  public T whatIsAt(Coordinate where);
}












