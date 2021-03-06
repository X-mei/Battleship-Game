package edu.duke.adh39.battleship;
/**
 * This concrete class extends V1ShipFactory. It inherit method to create submarine and destroyer but override the method to create battleship and carrier.
 * generic in typename T, which is the type of information the view needs to
 * display this ship.
 */
public class V2ShipFactory<T> extends V1ShipFactory<T> {
  
	@Override
	public Ship<T> makeBattleship(Placement where) {
		return createIrregularShip(where, 'b', "Battleship");
	}

	@Override
	public Ship<T> makeCarrier(Placement where) {
		return createIrregularShip(where, 'c', "Carrier");
	}

  protected Ship<T> createIrregularShip(Placement where, char letter, String name) throws IllegalArgumentException {
    
    if (where.getOrientation() != 'U' && where.getOrientation() != 'D' && where.getOrientation() != 'L' && where.getOrientation() != 'R'){
      throw new IllegalArgumentException("Illegal orientation, have to either face up/down/left/right.");
    }
    if (letter == 'b'){
      return new BattleShip(name, where.getCoordinate(), where.getOrientation(), letter, '*');
    }
    else {
      return new CarrierShip(name, where.getCoordinate(), where.getOrientation(), letter, '*');
    }
  }
}
