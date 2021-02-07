package edu.duke.adh39.battleship;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class TextPlayerTest {
  @Test
    void test_read_placement() throws IOException {
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      TextPlayer player = createTextPlayer(10, 20, "B2V\nC8H\na4v\n", bytes);
      Placement[] expected = new Placement[3];
      expected[0] = new Placement(new Coordinate(1, 2), 'V');
      expected[1] = new Placement(new Coordinate(2, 8), 'H');
      expected[2] = new Placement(new Coordinate(0, 4), 'V');
      String prompt = "Please enter a location for a ship:";
      for (int i = 0; i < expected.length; i++) {
        Placement p = player.readPlacement(prompt);
        assertEquals(p, expected[i]); //did we get the right Placement back
        assertEquals(prompt + "\n", bytes.toString()); //should have printed prompt and newline
        bytes.reset(); //clear out bytes for next time around
      }
    }

  @Test
  void test_do_one_placement() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(3, 3, "B2V\nC0H\na0H\n", bytes);
    String[] expected = new String[2];
    expected[0] = "  0|1|2\n" + "A  | |  A\n" + "B  | |  B\n" + "C d|d|d C\n" + "  0|1|2\n";
    expected[1] = "  0|1|2\n" + "A d|d|d A\n" + "B  | |  B\n" + "C d|d|d C\n" + "  0|1|2\n";
    player.doOnePlacement("Destroyer");
    assertEquals("A, where would you like to place a Destroyer?\nThat placement is invalid: the ship goes off the bottom of the board.\nA, where would you like to place a Destroyer?\n" + expected[0] + "\n", bytes.toString());
    bytes.reset();
    player.doOnePlacement("Destroyer");
    assertEquals("A, where would you like to place a Destroyer?\n" + expected[1] + "\n", bytes.toString());
    bytes.reset();
  }

  @Test
  void test_if_lost() throws IOException{
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(3, 3, "B2V\nC0H\na0H\n", bytes);
    player.doOnePlacement("Destroyer");
    player.doOnePlacement("Destroyer");
    assertEquals(false, player.checkIfLost());
    player.theBoard.fireAt(new Coordinate(2, 0));
    player.theBoard.fireAt(new Coordinate(2, 1));
    player.theBoard.fireAt(new Coordinate(2, 2));
    assertEquals(false, player.checkIfLost());
    player.theBoard.fireAt(new Coordinate(0, 0));
    player.theBoard.fireAt(new Coordinate(0, 1));
    player.theBoard.fireAt(new Coordinate(0, 2));
    assertEquals(true, player.checkIfLost());
  }

  @Test
  void test_IO_exception() throws IOException{
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player1 = createTextPlayer(3, 3, "", bytes);
    TextPlayer player2 = createTextPlayer(3, 3, "", bytes);
    assertThrows(IOException.class, ()->player1.doOnePlacement("Destroyer"));
    assertThrows(IOException.class, ()->player2.playOneTurn(player1.theBoard, player1.view));
  }

  @Test
  void test_play_one_turn() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player1 = createTextPlayer(3, 3, "C0H\na0H\nBB\nA0\nB1\nB0\nC0\nA2\nB2\nC2\n", bytes);
    TextPlayer player2 = createTextPlayer(3, 3, "A0V\nA2V\n", bytes);
    player1.doOnePlacement("Destroyer");
    player1.doOnePlacement("Destroyer");
    player2.doOnePlacement("Destroyer");
    player2.doOnePlacement("Destroyer");
    bytes.reset();
    String expected1 =
          
          "A, where would you like to fire at?\n"+
          "Invalid coordinate column.\n"+
          "A, where would you like to fire at?\n"+
          "\n"+
          "    "+"Your Ocean"+"                       "+"Opponent's Ocean\n"+
          "  0|1|2                              0|1|2\n" +
          "A d|d|d A                          A d| |  A\n"+
          "B  | |  B                          B  | |  B\n"+
          "C d|d|d C                          C  | |  C\n"+
          "  0|1|2                              0|1|2\n\n";
    String expected2 =
          "A, where would you like to fire at?\n"+
          "\n"+
          "    "+"Your Ocean"+"                       "+"Opponent's Ocean\n"+
          "  0|1|2                              0|1|2\n" + 
          "A d|d|d A                          A d| |  A\n"+
          "B  | |  B                          B  |X|  B\n"+
          "C d|d|d C                          C  | |  C\n"+
          "  0|1|2                              0|1|2\n\n";
    String expected3 =
          "A, where would you like to fire at?\n"+
          "\n"+
          "    "+"Your Ocean"+"                       "+"Opponent's Ocean\n"+
          "  0|1|2                              0|1|2\n" + 
          "A d|d|d A                          A d| |d A\n"+
          "B  | |  B                          B d|X|d B\n"+
          "C d|d|d C                          C d| |d C\n"+
          "  0|1|2                              0|1|2\n\n"+
          "A Won!\n";
      
    player1.playOneTurn(player2.theBoard, player2.view);
    assertEquals(expected1, bytes.toString());
    bytes.reset();
    player1.playOneTurn(player2.theBoard, player2.view);
    assertEquals(expected2, bytes.toString());
    bytes.reset();
    player1.playOneTurn(player2.theBoard, player2.view);
    player1.playOneTurn(player2.theBoard, player2.view);
    player1.playOneTurn(player2.theBoard, player2.view);
    player1.playOneTurn(player2.theBoard, player2.view);
    bytes.reset();
    player1.playOneTurn(player2.theBoard, player2.view);
    assertEquals(expected3, bytes.toString());
    bytes.reset();
  }

  @Test
  void test_intro_message() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(3, 3, "A0V\nA2V\n", bytes);
    player.displayExplainMessage();
    String expected =
          "  0|1|2\n" + 
          "A  | |  A\n"+
          "B  | |  B\n"+
          "C  | |  C\n"+
          "  0|1|2\n\n"+
          player.name+
          " you are going to place the following ships (which are all\n"+
          "rectangular). For each ship, type the coordinate of the upper left\n"+
          "side of the ship, followed by either H (for horizontal) or V (for\n"+
          "vertical).  For example M4H would place a ship horizontally starting\n"+
          "at M4 and going to the right.  You have\n\n"+
          "2 Submarines ships that are 1x2\n"+
          "3 Destroyers that are 1x3\n"+
          "3 Battleships that are 1x4\n"+
          "2 Carriers that are 1x6\n\n";
    assertEquals(expected, bytes.toString());
  }
  
  private TextPlayer createTextPlayer(int w, int h, String inputData, OutputStream bytes) {
    BufferedReader input = new BufferedReader(new StringReader(inputData));
    PrintStream output = new PrintStream(bytes, true);
    Board<Character> board = new BattleShipBoard<Character>(w, h, 'X');
    V1ShipFactory shipFactory = new V1ShipFactory();
    return new TextPlayer("A", board, input, output, shipFactory);
  }

}







