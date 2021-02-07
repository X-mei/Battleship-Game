package edu.duke.adh39.battleship;

import java.util.ArrayList;
import java.util.HashSet;
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
  private final HashSet<Coordinate> enemyMisses;
  private final T missInfo;
  
  public BattleShipBoard(int w, int h, T missInfo) {
    this(w, h, new NoCollisionRuleChecker<T>(new InBoundsRuleChecker<T>(null)), missInfo);
  }

  public BattleShipBoard(int w, int h, PlacementRuleChecker<T> rule, T missInfo) {
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
    this.enemyMisses = new HashSet<Coordinate>();
    this.missInfo = missInfo;
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
  public String tryAddShip(Ship<T> toAdd){
    String result = placementChecker.checkPlacement(toAdd, this);
    if (result == null){
      myShips.add(toAdd);
    }
    return result;
  }
  
  @Override
  public T whatIsAtForSelf(Coordinate where) {
    return whatIsAt(where, true);
  }

  @Override
  public T whatIsAtForEnemy(Coordinate where) {
    return whatIsAt(where, false);
  }


  @Override
  public Ship<T> fireAt(Coordinate c) {
    for (Ship<T> s: myShips){
      if (s.occupiesCoordinates(c)){
        s.recordHitAt(c);
        return s;
      }
    }
    enemyMisses.add(c);
    return null;
  }

  @Override
  public Boolean hasLost() {
    for (Ship<T> s : myShips) {
      if (!s.isSunk()) {
        return false;
      }
    }
    return true;
  }
  
  protected T whatIsAt(Coordinate where, boolean isSelf) {
    for (Ship<T> s: myShips) {
      if (s.occupiesCoordinates(where)){
        return s.getDisplayInfoAt(where, isSelf);
      }
    }
    if (!isSelf) {
      if (enemyMisses.contains(where)) {
        return missInfo;
      }
    }
    return null;
  }
}












