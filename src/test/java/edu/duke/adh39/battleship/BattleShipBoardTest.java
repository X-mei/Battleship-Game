package edu.duke.adh39.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BattleShipBoardTest {
  @Test
  public void test_width_and_height() {
    Board<Character> b1 = new BattleShipBoard<Character>(10, 20);
    assertEquals(10, b1.getWidth());
    assertEquals(20, b1.getHeight());
  }

  @Test
  public void test_invalid_dimensions() {
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, 0));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(0, 20));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, -5));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(-8, 20));
  }

  @Test
  public void test_try_add_ship() {
    Board<Character> b1 = new BattleShipBoard<Character>(5, 5);
    Character expect[][] = { { null, null, null, null, null }, { null, null, null, null, null },
        { null, null, null, null, null }, { null, null, null, null, null }, { null, null, null, null, null } };
    checkWhatIsAtBoard(b1, expect);
    Coordinate c1 = new Coordinate(1,4);
    Ship<Character> s1 = new RectangleShip<Character>(c1, 's', '*');
    Coordinate c2 = new Coordinate(3,1);
    Ship<Character> s2 = new RectangleShip<Character>(c2, 's', '*');
    Coordinate c3 = new Coordinate(0,2);
    Ship<Character> s3 = new RectangleShip<Character>(c3, 's', '*');
    b1.tryAddShip(s1);
    b1.tryAddShip(s2);
    b1.tryAddShip(s3);
    expect[1][4] = 's';
    expect[3][1] = 's';
    expect[0][2] = 's';
    checkWhatIsAtBoard(b1, expect);
    
  }

  @Test
  public void test_what_is_at() {
    Board<Character> b1 = new BattleShipBoard<Character>(10, 20);
    Coordinate c1 = new Coordinate("B3");
    Coordinate c2 = new Coordinate("D5");
    assertEquals(null, b1.whatIsAt(c1));
    assertEquals(null, b1.whatIsAt(c2));
  }

  private <T> void checkWhatIsAtBoard(Board<T> b, T[][] expected){
    for (int i=0; i<b.getHeight(); i++){
      for (int j=0; j<b.getWidth(); j++){
        Coordinate w = new Coordinate(i,j);
        assertEquals(expected[i][j], b.whatIsAt(w));
      }
    }
  }
}










