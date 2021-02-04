package edu.duke.adh39.battleship;
/**
 * This class implements the composable chain of rules to check whether a placement is legal
 * generic in typename T, which is the type of information the view needs to
 * display this ship.
 */
public abstract class PlacementRuleChecker<T> {
  protected final PlacementRuleChecker<T> next;
  
  /**
   * Initilize the next rule to check.
   * @param next rule to check, could be null
   */
  public PlacementRuleChecker(PlacementRuleChecker<T> next) {
    this.next = next;
  }
  
  /**
   * This is the abstract method so different rule could be implemented by inheriting this.
   * It checks if the given ship and board confront to the current rule.
   * @param theShip is the ship to be put on the theBoard.
   * @return true if this and all following rule is met.
   */
  protected abstract boolean checkMyRule(Ship<T> theShip, Board<T> theBoard);

  /**
   * This rule checks if the given ship and board confront to the placement restriction.
   * @param theShip is the ship to be put on the theBoard.
   * @return true if this and all following rule is met.
   */
  public boolean checkPlacement (Ship<T> theShip, Board<T> theBoard) {
    //if we fail our own rule: stop the placement is not legal
    if (!checkMyRule(theShip, theBoard)) {
      return false;
    }
    //other wise, ask the rest of the chain.
    if (next != null) {
      return next.checkPlacement(theShip, theBoard); 
    }
    //if there are no more rules, then the placement is legal
    return true;
  }

}
