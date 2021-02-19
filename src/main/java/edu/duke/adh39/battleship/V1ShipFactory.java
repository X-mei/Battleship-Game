package edu.duke.adh39.battleship;
/**
 * This concrete class implement AbstractShipFactory.
 * generic in typename T, which is the type of information the view needs to
 * display this ship.
 */
public class V1ShipFactory<T> implements AbstractShipFactory<T>{

	@Override
	public Ship<T> makeSubmarine(Placement where) {
		return createShip(where, 1, 2, 's', "Submarine");
	}

	@Override
	public Ship<T> makeBattleship(Placement where) {
		return createShip(where, 1, 4, 'b', "Battleship");
	}

	@Override
	public Ship<T> makeCarrier(Placement where) {
		return createShip(where, 1, 6, 'c', "Carrier");
	}

	@Override
	public Ship<T> makeDestroyer(Placement where) {
		return createShip(where, 1, 3, 'd', "Destroyer");
	}

  protected Ship<T> createShip(Placement where, int w, int h, char letter, String name) throws IllegalArgumentException {
    if (where.getOrientation() == 'V') {
      //Do nothing when input is vertical
    }
    else if (where.getOrientation() == 'H') {
      int temp = h;
      h = w;
      w = temp;
    }
    else {
      throw new IllegalArgumentException("Illegal orientation, have to be vertical/horizontal.");
    }
    return new RectangleShip(name, where.getCoordinate(), w, h, letter, '*');
  }
}













