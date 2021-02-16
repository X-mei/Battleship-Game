package edu.duke.adh39.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class RectangleShipTest {
  @Test
  public void test_ship_coordinates() {
    ArrayList<Coordinate> set = new ArrayList<Coordinate>();
    set.add(new Coordinate(1, 3));
    set.add(new Coordinate(2, 3));
    set.add(new Coordinate(3, 3));
    ArrayList<Coordinate> expected = RectangleShip.makeCoords(new Coordinate(1,3), 1, 3);
    assertEquals(set, expected);
  }
  
  @Test
  public void test_rectangle_ship_constructor() {
    BasicShip<Character> s = new RectangleShip<Character>("Rectangle Ship", new Coordinate(1,3), 1, 3, new SimpleShipDisplayInfo<Character>('s', '*'), new SimpleShipDisplayInfo<Character>(null, 's'));
    assertEquals(true, s.occupiesCoordinates(new Coordinate(1,3)));
    assertEquals(true, s.occupiesCoordinates(new Coordinate(2,3)));
    assertEquals(true, s.occupiesCoordinates(new Coordinate(3,3)));
    assertEquals(false, s.occupiesCoordinates(new Coordinate(2,5)));
    assertEquals(false, s.occupiesCoordinates(new Coordinate(6,2)));
  }

  @Test
  public void test_record_was_hit_at() {
    BasicShip<Character> s = new RectangleShip<Character>("Rectangle Ship", new Coordinate(1,3), 1, 3, new SimpleShipDisplayInfo<Character>('s', '*'), new SimpleShipDisplayInfo<Character>(null, 's'));
    s.wasHitAt(new Coordinate(1, 3));
    assertThrows(IllegalArgumentException.class, ()->s.recordHitAt(new Coordinate(1,4)));
    assertThrows(IllegalArgumentException.class, ()->s.recordHitAt(new Coordinate(4,3)));
    assertThrows(IllegalArgumentException.class, ()->s.wasHitAt(new Coordinate(4,3)));
    assertThrows(IllegalArgumentException.class, ()->s.wasHitAt(new Coordinate(1,4)));
  }
  
  @Test
  public void test_is_sunk() {
    BasicShip<Character> s = new RectangleShip<Character>("Rectangle Ship", new Coordinate(1,3), 1, 3, 's', '*');
    assertEquals(false, s.isSunk());
    assertEquals(false, s.wasHitAt(new Coordinate(1, 3)));
    s.recordHitAt(new Coordinate(1, 3));
    assertEquals(true, s.wasHitAt(new Coordinate(1, 3)));
    assertEquals(false, s.isSunk());
    s.recordHitAt(new Coordinate(2, 3));
    assertEquals(true, s.wasHitAt(new Coordinate(2, 3)));
    assertEquals(false, s.isSunk());
    s.recordHitAt(new Coordinate(3, 3));
    assertEquals(true, s.wasHitAt(new Coordinate(3, 3)));
    assertEquals(true, s.isSunk());
    assertEquals("Rectangle Ship", s.getName());
  }

  @Test
  public void test_getCoordinates() {
    HashSet<Coordinate> set = new HashSet<Coordinate>();
    set.add(new Coordinate(1, 3));
    set.add(new Coordinate(2, 3));
    set.add(new Coordinate(3, 3));
    BasicShip<Character> s = new RectangleShip<Character>("Rectangle Ship", new Coordinate(1,3), 1, 3, 's', '*');
    assertEquals(set, s.getCoordinates());
  }

  @Test
  public void test_change_position() {
    BasicShip<Character> s1 = new RectangleShip<Character>("Rectangle Ship", new Coordinate(1,3), 1, 3, 's', '*');
    BasicShip<Character> s2 = new RectangleShip<Character>("Rectangle Ship", new Coordinate(2,4), 3, 1, 's', '*');
    s1.recordHitAt(new Coordinate(1, 3));
    s2.changeCoordinate(s1);
    assertEquals(true, s1.wasHitAt(new Coordinate(1, 3)));
    assertEquals(false, s1.wasHitAt(new Coordinate(2, 3)));
    assertEquals(false, s1.wasHitAt(new Coordinate(3, 3)));
    assertEquals(true, s2.wasHitAt(new Coordinate(2, 4)));
    assertEquals(false, s2.wasHitAt(new Coordinate(2, 5)));
    assertEquals(false, s2.wasHitAt(new Coordinate(2, 6)));
    s1.recordHitAt(new Coordinate(2, 3));
    s1.recordHitAt(new Coordinate(3, 3));
    s2.changeCoordinate(s1);
    assertEquals(true, s1.isSunk());
    assertEquals(true, s2.isSunk());
    assertEquals(false, s2.occupiesCoordinates(new Coordinate(1,3)));
    assertEquals(false, s2.occupiesCoordinates(new Coordinate(2,3)));
    assertEquals(false, s2.occupiesCoordinates(new Coordinate(3,3)));
  }
}












