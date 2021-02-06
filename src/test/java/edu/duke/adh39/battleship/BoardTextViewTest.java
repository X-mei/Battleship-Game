package edu.duke.adh39.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BoardTextViewTest {
  @Test
  public void test_display_empty_2by2() {
    String Header = "  0|1\n";
    String Body = "A  |  A\n" + "B  |  B\n";
    emptyBoardHelper(2, 2, Header, Body);
  }
  
  @Test
  public void test_display_empty_3by2() {
    String Header = "  0|1\n";
    String Body = "A  |  A\n" + "B  |  B\n" + "C  |  C\n";
    emptyBoardHelper(2, 3, Header, Body);
  }

  @Test
  public void test_display_empty_2by5() {
    String Header = "  0|1|2|3|4\n";
    String Body = "A  | | | |  A\n" + "B  | | | |  B\n";
    emptyBoardHelper(5, 2, Header, Body);
  }

  private void emptyBoardHelper(int w, int h, String expectedHeader, String expectedBody) {
    Board<Character> b1 = new BattleShipBoard<Character>(w, h, 'X');
    BoardTextView view = new BoardTextView(b1);
    assertEquals(expectedHeader, view.makeHeader());
    String expected = expectedHeader + expectedBody + expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }

  @Test
  public void test_invalid_board_size() { 
    Board<Character> wideBoard = new BattleShipBoard<Character>(11, 20, 'X');
    Board<Character> tallBoard = new BattleShipBoard<Character>(10, 27, 'X');
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(wideBoard));
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(tallBoard));
  }

  @Test
  public void test_ship_display_3by2() {
    String Header = "  0|1\n";
    String Body1 = "A s|  A\n" + "B  |s B\n" + "C  |s C\n";
    String Body2 = "A  |  A\n" + "B  |  B\n" + "C  |  C\n";
    Board<Character> b = new BattleShipBoard<Character>(2, 3, 'X');
    Coordinate c1 = new Coordinate(0,0);
    Ship<Character> s1 = new RectangleShip<Character>(c1, 's', '*');
    Coordinate c2 = new Coordinate(1,1);
    Ship<Character> s2 = new RectangleShip<Character>(c2, 's', '*');
    Coordinate c3 = new Coordinate(2,1);
    Ship<Character> s3 = new RectangleShip<Character>(c3, 's', '*');
    b.tryAddShip(s1);
    b.tryAddShip(s2);
    b.tryAddShip(s3);
    BoardTextView view = new BoardTextView(b);
    String expected1 = Header + Body1 + Header;
    String expected2 = Header + Body2 + Header;
    assertEquals(expected1, view.displayMyOwnBoard());
    assertEquals(expected2, view.displayEnemyBoard());
  }
}











