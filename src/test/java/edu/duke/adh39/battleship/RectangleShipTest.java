package edu.duke.adh39.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class RectangleShipTest {
  @Test
  public void test_ship_coordinates() {
    HashSet<Coordinate> set = new HashSet<Coordinate>();
    set.add(new Coordinate(1, 3));
    set.add(new Coordinate(2, 3));
    set.add(new Coordinate(3, 3));
    HashSet<Coordinate> expected = RectangleShip.makeCoords(new Coordinate(1,3), 1, 3);
    assertEquals(set, expected);
  }
  
  @Test
  public void test_rectangle_ship_constructor() {
    BasicShip<Character> s = new RectangleShip<Character>(new Coordinate(1,3), 1, 3, new SimpleShipDisplayInfo('s', '*'));
    assertEquals(true, s.occupiesCoordinates(new Coordinate(1,3)));
    assertEquals(true, s.occupiesCoordinates(new Coordinate(2,3)));
    assertEquals(true, s.occupiesCoordinates(new Coordinate(3,3)));
    assertEquals(false, s.occupiesCoordinates(new Coordinate(2,5)));
    assertEquals(false, s.occupiesCoordinates(new Coordinate(6,2)));
  }
}










