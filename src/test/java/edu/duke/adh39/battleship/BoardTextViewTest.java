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
    Board<Character> b1 = new BattleShipBoard<Character>(w, h);
    BoardTextView view = new BoardTextView(b1);
    assertEquals(expectedHeader, view.makeHeader());
    String expected = expectedHeader + expectedBody + expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }

  
  @Test
  public void test_invalid_board_size() { 
    Board<Character> wideBoard = new BattleShipBoard<Character>(11,20);
    Board<Character> tallBoard = new BattleShipBoard<Character>(10,27);
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(wideBoard));
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(tallBoard));
  }
}









