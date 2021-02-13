package edu.duke.adh39.battleship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.lang.Math;
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
  private final HashMap<Coordinate, T> enemyHits;
  private final T missInfo;

  /**
   * This constructor construct a battleshipboard with a given size and a information to display when misses. It will use default rule checking
   * @param width and height as well as information to display when miss.
   */
  public BattleShipBoard(int w, int h, T missInfo) {
    this(w, h, new NoCollisionRuleChecker<T>(new InBoundsRuleChecker<T>(null)), missInfo);
  }

  /**
   * This constructor construct a battleshipboard with a given size and a information to display when misses. It use custimize rule checking.
   * @param width and height, rule to apply when checking, as well as information to display when miss.
   */
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
    this.enemyHits = new HashMap<Coordinate,T>();
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
        enemyHits.put(c, s.getDisplayInfoAt(c, false));
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

  @Override
  public HashMap<String, Integer> sonarScan(Coordinate c){
    HashMap<String, Integer> result = new HashMap<String, Integer>();
    result.put("Submarine", 0);
    result.put("Destroyer", 0);
    result.put("Battleship", 0);
    result.put("Carrier", 0);
    for (int i = c.getRow() - 3; i < c.getRow() + 4; ++i) {
      Integer diff = Math.abs(c.getRow()-i);
      for (int j = c.getColumn() - 3 + diff; j < c.getColumn() + 4 - diff; ++j) {
        String sName = this.getNameAt(new Coordinate(i, j));
        if (sName == null) {
          continue;
        }
        else {
          result.put(sName, result.get(sName)+1);
        }
      }
    }
    
    return result;
  }
  
  /**
   * This a function that determine what to print at given coordinate.
   * @param where is the coordinate to check, isSelf determines who is trying to see this board.
   */
  protected T whatIsAt(Coordinate where, boolean isSelf) {
    if (isSelf) {
      for (Ship<T> s: myShips) {
        if (s.occupiesCoordinates(where)){
          return s.getDisplayInfoAt(where, true);
        }
      }
    }
    else {
      if (enemyMisses.contains(where)) {
        return missInfo;
      }
      if (enemyHits.containsKey(where)) {
        return enemyHits.get(where);
      }
    }
    return null;
  }

  /**
   * This a helper function that determine the name of ship at given coordinate.
   * @param where is the coordinate to check.
   * @return the name of the ship, or null if no ship at given coordinate.
   */
  protected String getNameAt(Coordinate where) {
    for (Ship<T> s : myShips) {
      if (s.occupiesCoordinates(where)) {
        return s.getName();
      }
    }
    return null;
  }
}












