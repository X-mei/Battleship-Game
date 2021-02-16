package edu.duke.adh39.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class BattleShipTest {
  @Test
  public void test_battle_ship_construct() {
    ArrayList<Coordinate> set1 = new ArrayList<Coordinate>();
    set1.add(new Coordinate(1,2));
    set1.add(new Coordinate(2,1));
    set1.add(new Coordinate(2,2));
    set1.add(new Coordinate(2,3));
    assertEquals(set1, BattleShip.makeCoords(new Coordinate(1,1), 'U'));
    ArrayList<Coordinate> set2 = new ArrayList<Coordinate>();
    set2.add(new Coordinate(2,2));
    set2.add(new Coordinate(1,1));
    set2.add(new Coordinate(2,1));
    set2.add(new Coordinate(3,1));
    assertEquals(set2, BattleShip.makeCoords(new Coordinate(1,1), 'R'));
    ArrayList<Coordinate> set3 = new ArrayList<Coordinate>();
    set3.add(new Coordinate(2,2));
    set3.add(new Coordinate(1,3));
    set3.add(new Coordinate(1,2));
    set3.add(new Coordinate(1,1));
    assertEquals(set3, BattleShip.makeCoords(new Coordinate(1,1), 'D'));
    ArrayList<Coordinate> set4 = new ArrayList<Coordinate>();
    set4.add(new Coordinate(2,1));
    set4.add(new Coordinate(3,2));
    set4.add(new Coordinate(2,2));
    set4.add(new Coordinate(1,2));
    assertEquals(set4, BattleShip.makeCoords(new Coordinate(1,1), 'L'));
  }

  @Test
  public void test_battle_ship_constructor(){
    BasicShip<Character> s = new BattleShip("Battleship", new Coordinate(1,1), 'U', 'b', '*');
    assertEquals(true, s.occupiesCoordinates(new Coordinate(1,2)));
    assertEquals(true, s.occupiesCoordinates(new Coordinate(2,1)));
    assertEquals(true, s.occupiesCoordinates(new Coordinate(2,2)));
    assertEquals(true, s.occupiesCoordinates(new Coordinate(2,3)));
    assertEquals(false, s.occupiesCoordinates(new Coordinate(2,5)));
    assertEquals(false, s.occupiesCoordinates(new Coordinate(6,2)));
  }
}











