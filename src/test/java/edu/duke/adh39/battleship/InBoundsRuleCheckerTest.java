package edu.duke.adh39.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class InBoundsRuleCheckerTest {
  @Test
  public void test_in_bound_rule() {
    V1ShipFactory<Character> f = new V1ShipFactory <Character>();
    Board<Character> b = new BattleShipBoard<Character>(5, 5, 'X');
    PlacementRuleChecker<Character> rule = new InBoundsRuleChecker<Character>(null);
    Placement p1 = new Placement("A0H");
    Placement p2 = new Placement("C2v");
    Placement p3 = new Placement("D3h");
    Placement p4 = new Placement(new Coordinate(-1, 4), 'H');
    Placement p5 = new Placement(new Coordinate(1, -3), 'V');
    Ship<Character> s1 = f.makeSubmarine(p1);
    Ship<Character> s2 = f.makeBattleship(p2);
    Ship<Character> s3 = f.makeDestroyer(p3);
    Ship<Character> s4 = f.makeSubmarine(p4);
    Ship<Character> s5 = f.makeSubmarine(p5);
    assertEquals(null,rule.checkPlacement(s1, b));
    assertEquals("That placement is invalid: the ship goes off the bottom of the board.",rule.checkPlacement(s2, b));
    assertEquals("That placement is invalid: the ship goes off the right of the board.",rule.checkPlacement(s3, b));
    assertEquals("That placement is invalid: the ship goes off the top of the board.",rule.checkPlacement(s4, b));
    assertEquals("That placement is invalid: the ship goes off the left of the board.",rule.checkPlacement(s5, b));
    
  }

}








