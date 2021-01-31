package edu.duke.adh39.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PlacementTest {
  @Test
  public void test_equals() {
    Coordinate c1 = new Coordinate(1, 2);
    Placement p1 = new Placement(c1, 'v');
    Coordinate c2 = new Coordinate(1, 2);
    Placement p2 = new Placement(c2, 'V');
    Coordinate c3 = new Coordinate(1, 3);
    Placement p3 = new Placement(c3, 'v');
    assertEquals(p1, p1);   //equals should be reflexsive
    assertEquals(p1, p2);   //different objects bu same contents
    assertNotEquals(p1, p3);  //different contents
    assertNotEquals(p1, "(1, 2)(V)"); //different types
  }

  @Test
  public void test_hashCode() {
    Coordinate c1 = new Coordinate(1, 2);
    Placement p1 = new Placement(c1, 'v');
    Coordinate c2 = new Coordinate(1, 2);
    Placement p2 = new Placement(c2, 'V');
    Coordinate c3 = new Coordinate(1, 3);
    Placement p3 = new Placement(c3, 'v');
    assertEquals(p1.hashCode(), p2.hashCode());
    assertNotEquals(p1.hashCode(), p3.hashCode());
  }

  @Test
  void test_string_constructor_valid_cases() {
    Placement p1 = new Placement("A0V");
    assertEquals("(0, 0)(V)", p1.toString());
    Placement p2 = new Placement("D5V");
    assertEquals("(3, 5)(V)", p2.toString());
  }
  
  @Test
  public void test_string_constructor_error_cases() {
    assertThrows(IllegalArgumentException.class, () -> new Placement("00V"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("AAa"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("@0v"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("[0r"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("A/v"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("A:w"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("Ac"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("A12z"));
  }
  
}


