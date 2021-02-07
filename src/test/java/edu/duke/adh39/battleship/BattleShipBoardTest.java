package edu.duke.adh39.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class BattleShipBoardTest {
  @Test
  public void test_width_and_height() {
    Board<Character> b1 = new BattleShipBoard<Character>(10, 20, 'X');
    assertEquals(10, b1.getWidth());
    assertEquals(20, b1.getHeight());
  }

  @Test
  public void test_invalid_dimensions() {
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, 0, 'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(0, 20, 'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, -5, 'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(-8, 20, 'X'));
  }

  @Test
  public void test_try_add_ship() {
    Board<Character> b1 = new BattleShipBoard<Character>(5, 5, 'X');
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
    Board<Character> b = new BattleShipBoard<Character>(10, 20, 'X');
    assertEquals(null, b.whatIsAtForSelf(new Coordinate("B3")));
    assertEquals(null, b.whatIsAtForSelf(new Coordinate("D5")));
    assertEquals(null, b.whatIsAtForEnemy(new Coordinate("B3")));
    assertEquals(null, b.whatIsAtForEnemy(new Coordinate("D5")));
    Placement p1 = new Placement("A0H");
    Placement p2 = new Placement("B2v");
    V1ShipFactory<Character> f = new V1ShipFactory <Character>();
    Ship<Character> s1 = f.makeSubmarine(p1);
    Ship<Character> s2 = f.makeBattleship(p2);
    b.tryAddShip(s1);
    b.tryAddShip(s2);
    b.fireAt(new Coordinate(0, 1));
    b.fireAt(new Coordinate(1, 2));
    assertEquals(null, b.whatIsAtForEnemy(new Coordinate("A3")));
    b.fireAt(new Coordinate(0, 3));
    assertEquals('s', b.whatIsAtForEnemy(new Coordinate("A1")));
    assertEquals('b', b.whatIsAtForEnemy(new Coordinate("B2")));
    assertEquals('X', b.whatIsAtForEnemy(new Coordinate("A3")));
  }

  @Test
  public void test_fire_at() {
    Board<Character> b = new BattleShipBoard<Character>(10, 20, 'X');
    Placement p1 = new Placement("A0H");
    Placement p2 = new Placement("B2v");
    V1ShipFactory<Character> f = new V1ShipFactory <Character>();
    Ship<Character> s1 = f.makeSubmarine(p1);
    Ship<Character> s2 = f.makeBattleship(p2);
    b.tryAddShip(s1);
    b.tryAddShip(s2);
    assertEquals(false, s1.wasHitAt(new Coordinate(0, 0)));
    assertEquals(false, s1.wasHitAt(new Coordinate(0, 1)));
    assertSame(s1, b.fireAt(new Coordinate(0, 1)));
    assertSame(s1, b.fireAt(new Coordinate(0, 1)));
    assertEquals(true, s1.wasHitAt(new Coordinate(0, 1)));
    assertSame(s2, b.fireAt(new Coordinate(3, 2)));
    assertSame(null, b.fireAt(new Coordinate(3, 3)));
    assertEquals(false, s2.wasHitAt(new Coordinate(2, 2)));
    assertEquals(false, s2.wasHitAt(new Coordinate(1, 2)));
    assertEquals(false, s2.wasHitAt(new Coordinate(4, 2)));
    assertSame(s2, b.fireAt(new Coordinate(1, 2)));
    assertSame(s2, b.fireAt(new Coordinate(2, 2)));
    assertSame(s2, b.fireAt(new Coordinate(4, 2)));
    assertEquals(true, s2.isSunk());
    //b.fireAt(new Coordinate(0, 3));
    //b.fireAt(new Coordinate(1, 1));
    //HashSet<Coordinate> set = new HashSet<Coordinate>();
    //set.add(new Coordinate(0, 3));
    //set.add(new Coordinate(1, 1));
    //assertEquals(set, );
  }
  
  @Test
  void test_if_lost() {
    Board<Character> b = new BattleShipBoard<Character>(10, 20, 'X');
    Placement p1 = new Placement("A0H");
    Placement p2 = new Placement("B2v");
    V1ShipFactory<Character> f = new V1ShipFactory <Character>();
    Ship<Character> s1 = f.makeSubmarine(p1);
    Ship<Character> s2 = f.makeBattleship(p2);
    b.tryAddShip(s1);
    b.tryAddShip(s2);
    assertEquals(false, b.hasLost());
    assertSame(s1, b.fireAt(new Coordinate(0, 1)));
    assertSame(s1, b.fireAt(new Coordinate(0, 0)));
    assertEquals(false, b.hasLost());
    assertSame(s2, b.fireAt(new Coordinate(1, 2)));
    assertSame(s2, b.fireAt(new Coordinate(2, 2)));
    assertEquals(false, b.hasLost());
    assertSame(s2, b.fireAt(new Coordinate(3, 2)));
    assertSame(s2, b.fireAt(new Coordinate(4, 2)));
    assertEquals(true, b.hasLost());
  }
  
  private <T> void checkWhatIsAtBoard(Board<T> b, T[][] expected){
    for (int i=0; i<b.getHeight(); i++){
      for (int j=0; j<b.getWidth(); j++){
        Coordinate w = new Coordinate(i,j);
        assertEquals(expected[i][j], b.whatIsAtForSelf(w));
      }
    }
  }
}










