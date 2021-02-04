package edu.duke.adh39.battleship;

public class NoCollisionRuleChecker<T> extends PlacementRuleChecker<T> {
  
  public NoCollisionRuleChecker(PlacementRuleChecker<T> next) {
    super(next);
  }

  @Override
	protected boolean checkMyRule(Ship<T> theShip, Board<T> theBoard){
    Iterable<Coordinate> set = theShip.getCoordinates();
    for (Coordinate loc : set){
      if (theBoard.whatIsAt(loc) != null){
        return false;
      }
    }
    return true;
  }
}


