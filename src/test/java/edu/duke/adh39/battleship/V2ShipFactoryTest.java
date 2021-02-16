package edu.duke.adh39.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class V2ShipFactoryTest {
  @Test
  public void test_ship_factory_creating_ship() {
    Placement p1 = new Placement("A0D");
    Placement p2 = new Placement("B2R");
    Placement p3 = new Placement("D5L");
    Placement p4 = new Placement("C1R");
    Placement p5 = new Placement("E3T");
    V2ShipFactory<Character> f = new V2ShipFactory <Character>();
    Ship<Character> s1 = f.makeBattleship(p1);
    Ship<Character> s2 = f.makeBattleship(p2);
    Ship<Character> s3 = f.makeCarrier(p3);
    Ship<Character> s4 = f.makeCarrier(p4);
    checkShip(s1, "Battleship", 'b', new Coordinate(0,0), new Coordinate(0,1), new Coordinate(0,2), new Coordinate(1,1));
    checkShip(s2, "Battleship", 'b', new Coordinate(1,2), new Coordinate(2,2), new Coordinate(3,2), new Coordinate(2,3));
    checkShip(s3, "Carrier", 'c', new Coordinate(3,5), new Coordinate(3,6), new Coordinate(3,7), new Coordinate(4,7), new Coordinate(4,8), new Coordinate(4,9));
    checkShip(s4, "Carrier", 'c', new Coordinate(3,1), new Coordinate(3,2), new Coordinate(3,3), new Coordinate(2,3), new Coordinate(2,4), new Coordinate(2,5));
    assertThrows(IllegalArgumentException.class,()->f.makeSubmarine(p5));
    assertThrows(IllegalArgumentException.class,()->f.makeBattleship(p5));
    assertThrows(IllegalArgumentException.class,()->f.makeDestroyer(p5));
    assertThrows(IllegalArgumentException.class,()->f.makeCarrier(p5));
  }

  private void checkShip(Ship<Character> testShip, String expectedName, char expectedLetter, Coordinate... expectedLocs){
    assertEquals(expectedName, testShip.getName());
    assertEquals(expectedLetter, testShip.getDisplayInfoAt(expectedLocs[0], true));
    assertEquals(null, testShip.getDisplayInfoAt(expectedLocs[0], false));
    for (Coordinate c: expectedLocs){
      assertEquals(true, testShip.occupiesCoordinates(c));
    }
  }

}
