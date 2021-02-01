package edu.duke.adh39.battleship;

import java.util.HashSet;

public class RectangleShip<T> extends BasicShip<T>{
  
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

  public RectangleShip(Coordinate upperLeft, T data, T onHit) {
    this(upperLeft, 1, 1, new SimpleShipDisplayInfo<T>(data, onHit));
  }

  public RectangleShip(Coordinate upperLeft, int width, int height, T data, T onHit) {
    this(upperLeft, width, height, new SimpleShipDisplayInfo<T>(data, onHit));
  }
  
  public RectangleShip(Coordinate upperLeft, int width, int height, SimpleShipDisplayInfo<T> sim) {
    super(makeCoords(upperLeft, width, height), sim);
  }

}










