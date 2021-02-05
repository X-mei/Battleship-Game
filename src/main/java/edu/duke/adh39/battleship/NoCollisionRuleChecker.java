package edu.duke.adh39.battleship;

public class NoCollisionRuleChecker<T> extends PlacementRuleChecker<T> {
  
  public NoCollisionRuleChecker(PlacementRuleChecker<T> next) {
    super(next);
  }

  @Override
	protected String checkMyRule(Ship<T> theShip, Board<T> theBoard){
    Iterable<Coordinate> set = theShip.getCoordinates();
    String result = null;
    for (Coordinate loc : set){
      if (theBoard.whatIsAt(loc) != null){
        result = "That placement is invalid: the ship overlaps another ship.";
        break;
      }
    }
    return result;
  }
}





