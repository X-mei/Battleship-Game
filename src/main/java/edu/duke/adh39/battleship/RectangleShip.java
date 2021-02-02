package edu.duke.adh39.battleship;

import java.util.HashSet;

/**
 * This class extends the basic ship class.
 */
public class RectangleShip<T> extends BasicShip<T>{
  /**
   * This is a helper function to create a hashset of all the coordinate
   * of a ship with the given specification.
   * @param upperLeft is the anchor of the ship(at top left corner), width and height.
   * @return a hashset of all the coordinate to represent the ship.
   */
  static HashSet<Coordinate> makeCoords(Coordinate upperLeft, int width, int height) {
    HashSet<Coordinate> set = new HashSet<Coordinate>();
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Coordinate c = new Coordinate(upperLeft.getRow() + i, upperLeft.getColumn() + j);
        set.add(c);
      }
    }
    return set;
  }
  
  /**
   * This is a simple constructor when the ship have unit width and height, it calls another
   * constructor of this class and pass it to its parent class to initilize the class
   * @param upperLeft is the anchor of the ship. Data and onHit is the information needed
   * when trying to display.
   */
  public RectangleShip(Coordinate upperLeft, T data, T onHit) {
    this(upperLeft, 1, 1, new SimpleShipDisplayInfo<T>(data, onHit));
  }

  /**
   * This is a constructor when the ship given specification, it calls another
   * constructor of this class and pass it to its parent class to initilize the class
   * @param upperLeft is the anchor of the ship. Data and onHit is the information needed
   * when trying to display.Width and height is the size of the ship.
   */
  public RectangleShip(Coordinate upperLeft, int width, int height, T data, T onHit) {
    this(upperLeft, width, height, new SimpleShipDisplayInfo<T>(data, onHit));
  }

  /**
   * This is a constructor to call its parent class to initilize the class.
   * @param upperLeft is the anchor of the ship. SimpleShipDisplayInfo incorporate the information needed when displaying the ship.
   */
  public RectangleShip(Coordinate upperLeft, int width, int height, SimpleShipDisplayInfo<T> sim) {
    super(makeCoords(upperLeft, width, height), sim);
  }

}










