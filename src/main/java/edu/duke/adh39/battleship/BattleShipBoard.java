package edu.duke.adh39.battleship;

import java.util.ArrayList;
/**
 * This class implements the board class and It is
 * generic in typename T, which is the type of information the view needs to
 * display this ship.
 */
public class BattleShipBoard<T> implements Board<T>{
  private final int width;
  private final int height;
  private final ArrayList<Ship<T>> myShips;
  private final PlacementRuleChecker<T> placementChecker;
  
  public BattleShipBoard(int w, int h) {
    this(w, h, new InBoundsRuleChecker<T>(null));
  }

  public BattleShipBoard(int w, int h, PlacementRuleChecker<T> rule) {
    if (w <= 0) {
      throw new IllegalArgumentException("BattleShipBoard's width must be positive but is " + w);
    }
    if (h <= 0) {
      throw new IllegalArgumentException("BattleShipBoard's height must be positive but is " + h);
    }
    this.width = w;
    this.height = h;
    this.myShips = new ArrayList<Ship<T>>();
    this.placementChecker = rule;
  }
    
  @Override
  public int getWidth(){
    return width;
  }
  
  @Override
  public int getHeight(){
    return height;
  }

  @Override
  public boolean tryAddShip(Ship<T> toAdd){
    myShips.add(toAdd);
    return true;
  }
  
  @Override
  public T whatIsAt(Coordinate where) {
    for (Ship<T> s: myShips) {
      if (s.occupiesCoordinates(where)){
        return s.getDisplayInfoAt(where);
      }
    }
    return null;
  }
}





