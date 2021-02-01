package edu.duke.adh39.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SimpleShipDisplayInfoTest {
  @Test
  public void test_get_Info() {
    Character mData = 'M';
    Character oHit = 'O';
    SimpleShipDisplayInfo<Character> shipDisplay = new SimpleShipDisplayInfo<Character>(mData, oHit);
    assertEquals('O', shipDisplay.getInfo(new Coordinate(1,3), true));//check with reandom coordinate
    assertEquals('M', shipDisplay.getInfo(new Coordinate(1,3), false));
    assertNotEquals('O', shipDisplay.getInfo(new Coordinate(1,3), false));
  }

}
