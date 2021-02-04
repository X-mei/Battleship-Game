package edu.duke.adh39.battleship;

public class InBoundsRuleChecker<T> extends PlacementRuleChecker<T>{

  public InBoundsRuleChecker(PlacementRuleChecker<T> next) {
    super(next);
  }
  
	@Override
	protected boolean checkMyRule(Ship<T> theShip, Board<T> theBoard) {
		Iterable<Coordinate> set = theShip.getCoordinates();
    int width = theBoard.getWidth();
    int height = theBoard.getHeight();
    for (Coordinate loc: set){
      if (0 <= loc.getRow() && loc.getRow() < height) {
        //do nothing
      }
      else {
        return false;
      }
      if (0 <= loc.getColumn() && loc.getColumn() < width) {
        //do nothing
      }
      else {
        return false;
      }
    }
		return true;
	}
  
}


















