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
   * @return null if its valid to add ship, return a string description otherwise.
   */
  public String tryAddShip(Ship<T> toAdd);
  
  /**
   * This function returns the information at a given coordinate(self view)
   * @param the location to check.
   * @return the information at the location in typename T.
   */
  public T whatIsAtForSelf(Coordinate where);

  /**
   * This function returns the information at a given coordinate(enemy view)
   * @param the location to check.
   * @return the information at the location in typename T.
   */
  public T whatIsAtForEnemy(Coordinate where);

  /**
   * This function search for any ship that occupies coordinate c and return the corresponding ship after recording it has been hit.
   * @param the location to check.
   * @return the ship that has been hit after beeing hit. If not found, record hit misses and return null.
   */
  public Ship<T> fireAt(Coordinate c);

  /**
   * This function checks if all the ship on this board is sunk.
   * @return true if yes/false if not.
   */
  public Boolean hasLost();
}
