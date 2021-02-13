package edu.duke.adh39.battleship;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * This class implement the interface class ship, supporting different method on ship.
 */
public abstract class BasicShip<T> implements Ship<T> {
  protected LinkedHashMap<Coordinate, Boolean> myPieces;
  protected ShipDisplayInfo<T> myDisplayInfo;
  protected ShipDisplayInfo<T> enemyDisplayInfo;
  protected final String name;
  /**
   * Constructs a Basic ship, given the coordinate and display info.
   * @param where is the location of the ship, DisplayInfo is the info
   * needed when printing a ship on the board.
   */
  public BasicShip(Iterable<Coordinate> where, ShipDisplayInfo<T> DisplayInfoMe, ShipDisplayInfo<T> DisplayInfoEnemy, String name) {
    myPieces = new LinkedHashMap<Coordinate, Boolean>();
    for (Coordinate c: where){
      myPieces.put(c, false);
    }
    myDisplayInfo = DisplayInfoMe;
    enemyDisplayInfo = DisplayInfoEnemy;
    this.name = name;
  }

	@Override
	public boolean occupiesCoordinates(Coordinate where) {
		// TODO Auto-generated method stub
    return myPieces.containsKey(where);
	}

	@Override
	public boolean isSunk() {
    for (Coordinate c: myPieces.keySet()){
      if (!wasHitAt(c)) {
        return false;
      }
    }
		return true;
	}

	@Override
	public void recordHitAt(Coordinate where) {
		checkCoordinateInThisShip(where);
    myPieces.put(where, true);
	}

	@Override
	public boolean wasHitAt(Coordinate where) {
    checkCoordinateInThisShip(where);
    return myPieces.get(where);
	}

  
	@Override
	public T getDisplayInfoAt(Coordinate where, Boolean myShip) {
    checkCoordinateInThisShip(where);
    if (myShip) {
      return myDisplayInfo.getInfo(where, myPieces.get(where));
    }
    else {
      return enemyDisplayInfo.getInfo(where, myPieces.get(where));
    }
  }

  protected void checkCoordinateInThisShip(Coordinate c) {
    if (!occupiesCoordinates(c)){
      throw new IllegalArgumentException("This coordinate does not belong to this ship.");
    }
  }

  @Override
  public Iterable<Coordinate> getCoordinates(){
    return myPieces.keySet();
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public LinkedHashMap<Coordinate, Boolean> getPieces() {
    return myPieces;
  }

  @Override
  public void changeCoordinate(Ship<T> oldShip) {
    LinkedHashMap<Coordinate, Boolean> oldPieces = oldShip.getPieces();
    Iterator<Map.Entry<Coordinate, Boolean>> it = oldPieces.entrySet().iterator();
    Set<Coordinate> keys = myPieces.keySet();
    for (Coordinate c: keys){
      myPieces.put(c, it.next().getValue());
    }
  }
}












