package edu.duke.adh39.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class V1ShipFactoryTest {
  @Test
  public void test_ship_factory_creating_ship() {
    Placement p1 = new Placement("A0H");
    Placement p2 = new Placement("B2v");
    Placement p3 = new Placement("D5h");
    Placement p4 = new Placement("C1i");
    V1ShipFactory<Character> f = new V1ShipFactory <Character>();
    Ship<Character> s1 = f.makeSubmarine(p1);
    Ship<Character> s2 = f.makeBattleship(p2);
    Ship<Character> s3 = f.makeDestroyer(p3);
    checkShip(s1, "Submarine", 's', new Coordinate(0,0), new Coordinate(0,1));
    checkShip(s2, "Battleship", 'b', new Coordinate(1,2), new Coordinate(2,2), new Coordinate(3,2), new Coordinate(4,2));
    checkShip(s3, "Destroyer", 'd', new Coordinate(3,5), new Coordinate(3,6), new Coordinate(3,7));
    assertThrows(IllegalArgumentException.class,()->f.makeSubmarine(p4));
    assertThrows(IllegalArgumentException.class,()->f.makeBattleship(p4));
    assertThrows(IllegalArgumentException.class,()->f.makeDestroyer(p4));
    assertThrows(IllegalArgumentException.class,()->f.makeCarrier(p4));
  }

  private void checkShip(Ship<Character> testShip, String expectedName, char expectedLetter, Coordinate... expectedLocs){
    assertEquals(expectedName, testShip.getName());
    assertEquals(expectedLetter, testShip.getDisplayInfoAt(expectedLocs[0],true));
    assertEquals(null, testShip.getDisplayInfoAt(expectedLocs[0], false));
    for (Coordinate c: expectedLocs){
      assertEquals(true, testShip.occupiesCoordinates(c));
    }
  }

}











