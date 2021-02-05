package edu.duke.adh39.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class NoCollisionRuleCheckerTest {
  @Test
  public void test_no_collision_rule() {
    V1ShipFactory<Character> f = new V1ShipFactory <Character>();
    Board<Character> b = new BattleShipBoard<Character>(5, 5);
    PlacementRuleChecker<Character> rule = new NoCollisionRuleChecker<Character>(null);
    Placement p1 = new Placement("A0H");
    Placement p2 = new Placement("A4v");
    Placement p3 = new Placement("D0h");
    Placement p4 = new Placement("E3h");
    Ship<Character> s1 = f.makeSubmarine(p1);
    Ship<Character> s2 = f.makeBattleship(p2);
    Ship<Character> s3 = f.makeDestroyer(p3);
    Ship<Character> s4 = f.makeDestroyer(p4);
    assertEquals(null, rule.checkPlacement(s1, b));
    b.tryAddShip(s1);
    b.tryAddShip(s2);
    b.tryAddShip(s3);
    assertEquals("That placement is invalid: the ship overlaps another ship.", rule.checkPlacement(s3, b));
    assertEquals(null, rule.checkPlacement(s4, b));
  }

  @Test
  public void test_no_collision_and_in_bound() {
    Board<Character> b = new BattleShipBoard<Character>(5, 5);
    V1ShipFactory<Character> f = new V1ShipFactory <Character>();
    PlacementRuleChecker<Character> rule = new NoCollisionRuleChecker<Character>(new InBoundsRuleChecker<Character>(null));
    Placement p1 = new Placement("A0H");
    Placement p2 = new Placement("A1v");
    Placement p3 = new Placement("D0h");
    Placement p4 = new Placement("E3h");
    Ship<Character> s1 = f.makeSubmarine(p1);
    Ship<Character> s2 = f.makeBattleship(p2);
    Ship<Character> s3 = f.makeDestroyer(p3);
    Ship<Character> s4 = f.makeDestroyer(p4);
    assertEquals(null, rule.checkPlacement(s1, b));
    b.tryAddShip(s1);
    assertEquals("That placement is invalid: the ship overlaps another ship.", rule.checkPlacement(s1, b));
    assertEquals("That placement is invalid: the ship overlaps another ship.", rule.checkPlacement(s2, b));
    b.tryAddShip(s2);
    assertEquals(null, rule.checkPlacement(s3, b));
    b.tryAddShip(s3);
    assertEquals("That placement is invalid: the ship goes off the right of the board.", rule.checkPlacement(s4, b));
  }
}












