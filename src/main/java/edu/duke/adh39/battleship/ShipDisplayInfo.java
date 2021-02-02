package edu.duke.adh39.battleship;

/**
 * This class works as the interface class.
 * It display the ship info.
 */
public interface ShipDisplayInfo<T> {
  public T getInfo(Coordinate where, boolean hit);
}

