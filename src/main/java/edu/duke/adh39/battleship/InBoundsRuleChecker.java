package edu.duke.adh39.battleship;

public class InBoundsRuleChecker<T> extends PlacementRuleChecker<T>{

  public InBoundsRuleChecker(PlacementRuleChecker<T> next) {
    super(next);
  }
  
	@Override
	protected String checkMyRule(Ship<T> theShip, Board<T> theBoard) {
		Iterable<Coordinate> set = theShip.getCoordinates();
    int width = theBoard.getWidth();
    int height = theBoard.getHeight();
    String result = null;
    for (Coordinate loc: set){
      if (loc.getRow() < 0) {
        result = "That placement is invalid: the ship goes off the top of the board.";
      }
      else if (loc.getRow() >= height) {
        result = "That placement is invalid: the ship goes off the bottom of the board.";
      }
      if (loc.getColumn() < 0) {
        result = "That placement is invalid: the ship goes off the left of the board.";
      }
      else if (loc.getColumn() >= width) {
        result = "That placement is invalid: the ship goes off the right of the board.";
      }
    }
		return result;
	}
  
}


















