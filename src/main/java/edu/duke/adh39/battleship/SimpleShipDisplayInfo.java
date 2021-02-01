package edu.duke.adh39.battleship;

public class SimpleShipDisplayInfo<T> implements ShipDisplayInfo<T> {
  private final T myData;
  private final T onHit;
    
  public SimpleShipDisplayInfo(T m, T o){
    this.myData = m;
    this.onHit = o;
  }
      
    @Override
	public T getInfo(Coordinate where, boolean hit) {
    if (hit) {
      return onHit;
    }
    else {
      return myData;
    }
	}

}






