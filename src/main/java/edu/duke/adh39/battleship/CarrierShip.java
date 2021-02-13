package edu.duke.adh39.battleship;

import java.util.ArrayList;

public class CarrierShip<T> extends BasicShip<T> {
  /**
   * This is a helper function to create a hashset of all the coordinate
   * of a battleship with given topLeft coordinate and orientation.
   * @param middle is the anchor of the ship(in the middle), ori is the orientation of the ship.
   * @return a hashset of all the coordinate to represent the ship.
   */
  static ArrayList<Coordinate> makeCoords(Coordinate topLeft, Character ori) {
    ArrayList<Coordinate> set = new ArrayList<Coordinate>();
    if (ori == 'U'){
      Coordinate c1 = new Coordinate(topLeft.getRow(), topLeft.getColumn());
      set.add(c1);
      Coordinate c2 = new Coordinate(topLeft.getRow() + 1, topLeft.getColumn());
      set.add(c2);
      Coordinate c3 = new Coordinate(topLeft.getRow() + 2, topLeft.getColumn());
      set.add(c3);
      Coordinate c4 = new Coordinate(topLeft.getRow() + 2, topLeft.getColumn() + 1);
      set.add(c4);
      Coordinate c5 = new Coordinate(topLeft.getRow() + 3, topLeft.getColumn() + 1);
      set.add(c5);
      Coordinate c6 = new Coordinate(topLeft.getRow() + 4, topLeft.getColumn() + 1);
      set.add(c6);
    }
    else if (ori == 'L'){
      Coordinate c1 = new Coordinate(topLeft.getRow() + 1, topLeft.getColumn() + 4);
      set.add(c1);
      Coordinate c2 = new Coordinate(topLeft.getRow() + 1, topLeft.getColumn() + 3);
      set.add(c2);
      Coordinate c3 = new Coordinate(topLeft.getRow() + 1, topLeft.getColumn() + 2);
      set.add(c3);
      Coordinate c4 = new Coordinate(topLeft.getRow(), topLeft.getColumn() + 2);
      set.add(c4);
      Coordinate c5 = new Coordinate(topLeft.getRow(), topLeft.getColumn() + 1);
      set.add(c5);
      Coordinate c6 = new Coordinate(topLeft.getRow(), topLeft.getColumn());
      set.add(c6);
    }
    else if (ori == 'R'){
      Coordinate c1 = new Coordinate(topLeft.getRow() + 1, topLeft.getColumn());
      set.add(c1);
      Coordinate c2 = new Coordinate(topLeft.getRow() + 1, topLeft.getColumn() + 1);
      set.add(c2);
      Coordinate c3 = new Coordinate(topLeft.getRow() + 1, topLeft.getColumn() + 2);
      set.add(c3);
      Coordinate c4 = new Coordinate(topLeft.getRow(), topLeft.getColumn() + 2);
      set.add(c4);
      Coordinate c5 = new Coordinate(topLeft.getRow(), topLeft.getColumn() + 3);
      set.add(c5);
      Coordinate c6 = new Coordinate(topLeft.getRow(), topLeft.getColumn() + 4);
      set.add(c6);
    }
    else {
      Coordinate c1 = new Coordinate(topLeft.getRow() + 4, topLeft.getColumn());
      set.add(c1);
      Coordinate c2 = new Coordinate(topLeft.getRow() + 3, topLeft.getColumn());
      set.add(c2);
      Coordinate c3 = new Coordinate(topLeft.getRow() + 2, topLeft.getColumn());
      set.add(c3);
      Coordinate c4 = new Coordinate(topLeft.getRow() + 2, topLeft.getColumn() + 1);
      set.add(c4);
      Coordinate c5 = new Coordinate(topLeft.getRow() + 1, topLeft.getColumn() + 1);
      set.add(c5);
      Coordinate c6 = new Coordinate(topLeft.getRow(), topLeft.getColumn() + 1);
      set.add(c6);
    }
    return set;
  }

  /**
   * This is a constructor that calls another
   * constructor of this class and pass it to its parent class to initilize the class
   * @param upperLeft is the anchor of the ship. Data and onHit is the information needed
   * when trying to display.Width and height is the size of the ship.
   */
  public CarrierShip(String name, Coordinate topLeft, Character Ori, T data, T onHit) {
    this(name, topLeft, Ori, new SimpleShipDisplayInfo<T>(data, onHit), new SimpleShipDisplayInfo<T>(null, data));
  }
  
   /**
   * This is a constructor to call its parent class to initilize the class.
   * @param upperLeft is the anchor of the ship. SimpleShipDisplayInfo incorporate the information needed when displaying the ship.
   */
  public CarrierShip(String name, Coordinate topLeft, Character Ori , SimpleShipDisplayInfo<T> me, SimpleShipDisplayInfo<T> enemy) {
    super(makeCoords(topLeft, Ori), me, enemy, name);
  }
}



