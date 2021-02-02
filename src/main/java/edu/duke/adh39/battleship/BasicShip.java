package edu.duke.adh39.battleship;

import java.util.HashMap;

/**
 * This class implement the interface class ship, supporting different method on ship.
 */
public abstract class BasicShip<T> implements Ship<T> {
  protected HashMap<Coordinate, Boolean> myPieces;
  protected ShipDisplayInfo<T> myDisplayInfo;

  /**
   * Constructs a Basic ship, given the coordinate and display info.
   * @param where is the location of the ship, DisplayInfo is the info
   * needed when printing a ship on the board.
   */
  public BasicShip(Iterable<Coordinate> where, ShipDisplayInfo<T> DisplayInfo) {
    myPieces = new HashMap<Coordinate, Boolean>();
    for (Coordinate c: where){
      myPieces.put(c, false);
    }
    myDisplayInfo = DisplayInfo;
  }

	@Override
	public boolean occupiesCoordinates(Coordinate where) {
		// TODO Auto-generated method stub
    return myPieces.containsKey(where);
	}

	@Override
	public boolean isSunk() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void recordHitAt(Coordinate where) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean wasHitAt(Coordinate where) {
		// TODO Auto-generated method stub
		return false;
	}

  
	@Override
	public T getDisplayInfoAt(Coordinate where) {
    //TODO this is not right.  We need to
    //look up the hit status of this coordinate
    return myDisplayInfo.getInfo(where, false);
  }

  
}
