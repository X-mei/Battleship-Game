package edu.duke.adh39.battleship;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;

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
      TextPlayer player = createTextPlayerV1(10, 20, "B2V\nC8H\na4v\n", bytes);
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
    TextPlayer player = createTextPlayerV1(3, 3, "B2V\nC0H\na0H\n", bytes);
    String[] expected = new String[2];
    expected[0] = "  0|1|2\n" + "A  | |  A\n" + "B  | |  B\n" + "C d|d|d C\n" + "  0|1|2\n";
    expected[1] = "  0|1|2\n" + "A d|d|d A\n" + "B  | |  B\n" + "C d|d|d C\n" + "  0|1|2\n";
    player.doOnePlacement("Destroyer", true);
    assertEquals("A, where would you like to place a Destroyer?\nThat placement is invalid: the ship goes off the bottom of the board.\nA, where would you like to place a Destroyer?\n" + expected[0] + "\n", bytes.toString());
    bytes.reset();
    player.doOnePlacement("Destroyer", true);
    assertEquals("A, where would you like to place a Destroyer?\n" + expected[1] + "\n", bytes.toString());
    bytes.reset();
  }

  @Test
  void test_if_lost() throws IOException{
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayerV1(3, 3, "B2V\nC0H\na0H\n", bytes);
    player.doOnePlacement("Destroyer", true);
    player.doOnePlacement("Destroyer", true);
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
    TextPlayer player1 = createTextPlayerV1(3, 3, "", bytes);
    TextPlayer player2 = createTextPlayerV1(3, 3, "", bytes);
    assertThrows(IOException.class, ()->player1.doOnePlacement("Destroyer", true));
    assertThrows(IOException.class, ()->player2.playOneTurnV1(player1.theBoard, player1.view));
  }

  @Test
  void test_play_one_turn() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player1 = createTextPlayerV1(3, 3, "C0H\na0H\nBB\nA0\nB1\nB0\nC0\nA2\nB2\nC2\n", bytes);
    TextPlayer player2 = createTextPlayerV1(3, 3, "A0V\nA2V\n", bytes);
    player1.doOnePlacement("Destroyer", true);
    player1.doOnePlacement("Destroyer", true);
    player2.doOnePlacement("Destroyer", true);
    player2.doOnePlacement("Destroyer", true);
    bytes.reset();
    String expected1 =
          "\n"+
          "    "+"Your Ocean"+"                       "+"Opponent's Ocean\n"+
          "  0|1|2                              0|1|2\n" +
          "A d|d|d A                          A  | |  A\n"+
          "B  | |  B                          B  | |  B\n"+
          "C d|d|d C                          C  | |  C\n"+
          "  0|1|2                              0|1|2\n\n"+
          "A, where would you like to fire at?\n"+
          "Invalid coordinate column.\n"+
          "A, where would you like to fire at?\n"+
          "You hit a Destroyer!\n";
          
    String expected2 =
          "\n"+
          "    "+"Your Ocean"+"                       "+"Opponent's Ocean\n"+
          "  0|1|2                              0|1|2\n" + 
          "A d|d|d A                          A d| |  A\n"+
          "B  | |  B                          B  | |  B\n"+
          "C d|d|d C                          C  | |  C\n"+
          "  0|1|2                              0|1|2\n\n"+
          "A, where would you like to fire at?\n"+
          "You missed.\n";
          
    String expected3 =
          "\n"+
          "    "+"Your Ocean"+"                       "+"Opponent's Ocean\n"+
          "  0|1|2                              0|1|2\n" + 
          "A d|d|d A                          A d| |d A\n"+
          "B  | |  B                          B d|X|d B\n"+
          "C d|d|d C                          C d| |  C\n"+
          "  0|1|2                              0|1|2\n\n"+
          "A, where would you like to fire at?\n"+
          "You hit a Destroyer!\n"+
          "A Won!\n";
      
    player1.playOneTurnV1(player2.theBoard, player2.view);
    assertEquals(expected1, bytes.toString());
    bytes.reset();
    player1.playOneTurnV1(player2.theBoard, player2.view);
    assertEquals(expected2, bytes.toString());
    bytes.reset();
    player1.playOneTurnV1(player2.theBoard, player2.view);
    player1.playOneTurnV1(player2.theBoard, player2.view);
    player1.playOneTurnV1(player2.theBoard, player2.view);
    player1.playOneTurnV1(player2.theBoard, player2.view);
    bytes.reset();
    player1.playOneTurnV1(player2.theBoard, player2.view);
    assertEquals(expected3, bytes.toString());
    bytes.reset();
  }

  @Test
  void test_intro_message() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayerV1(3, 3, "A0V\nA2V\n", bytes);
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

  @Test
  void test_read_action() throws IOException{
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayerV2(3, 3, "aa\nA\nF\nM\nS\n", bytes);
    TextPlayer player2 = createTextPlayerV2(3, 3, "", bytes);
    assertThrows(IllegalArgumentException.class, ()->player.readAction("A what would you like to do?"));
    assertThrows(IllegalArgumentException.class, ()->player.readAction("A what would you like to do?"));
    assertEquals('F', player.readAction("A what would you like to do?"));
    assertEquals('M', player.readAction("A what would you like to do?"));
    assertEquals('S', player.readAction("A what would you like to do?"));
    assertThrows(IOException.class, ()->player2.readAction("A what would you like to do?"));
  }

  //@Disabled
  @Test
  void test_move_action() throws IOException{
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayerV2(3, 3, "A0V\nA2V\nB1\nA0\nA1V\n", bytes);
    player.doOnePlacement("Destroyer", true);
    player.doOnePlacement("Destroyer", true);
    bytes.reset();
    player.moveAction();
    String expected =
        "A, select the ship to move?\n"+
        "No ship at this location.\n"+
        "A, select the ship to move?\n"+
        "A, where would you like to place a Destroyer?\n"+
        "  0|1|2\n"+
        "A  |d|d A\n"+
        "B  |d|d B\n"+
        "C  |d|d C\n"+
        "  0|1|2\n\n";
    assertEquals(expected, bytes.toString());
  }

  @Test
  void test_scan_action() throws IOException{
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player1 = createTextPlayerV2(3, 3, "A0V\nA2V\n", bytes);
    TextPlayer player2 = createTextPlayerV2(3, 3, "AA\nB1\n", bytes);
    player1.doOnePlacement("Destroyer", true);
    player1.doOnePlacement("Submarine", true);
    bytes.reset();
    player2.scanAction(player1.theBoard);
    String expected =
      "A, where do you want to scan?\n"+
      "Invalid coordinate column.\n"+
      "A, where do you want to scan?\n"+
      "Submarine occupies 2 squares.\n"+
      "Destroyer occupies 3 squares.\n"+
      "Carrier occupies 0 squares.\n"+
      "Battleship occupies 0 squares.\n";
    assertEquals(expected, bytes.toString());
  }

  @Test
  void test_all_random() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayerV2(3, 3, "", bytes);
    assertEquals('F', player.randomAction());
    assertEquals('F', player.randomAction());
    assertEquals('F', player.randomAction());
    assertEquals('S', player.randomAction());
    assertEquals('M', player.randomAction());
    assertEquals('M', player.randomAction());
    assertEquals('F', player.randomAction());
    assertEquals(new Placement("C2V"), player.randomPlacement("Submarine"));
    assertEquals(new Placement("A1V"), player.randomPlacement("Submarine"));
    assertEquals(new Placement("B1H"), player.randomPlacement("Submarine"));
    assertEquals(new Placement("A1U"), player.randomPlacement("Carrier"));
    assertEquals(new Placement("B0R"), player.randomPlacement("Carrier"));
    assertEquals(new Placement("B1D"), player.randomPlacement("Carrier"));
    assertEquals(new Coordinate("C0"), player.randomCoordinate());
    assertEquals(new Coordinate("A2"), player.randomCoordinate());
    assertEquals(new Coordinate("C0"), player.randomCoordinate());
  }

  @Test
  void test_play_one_turn_V2() throws IOException{
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player1 = createTextPlayerV2(3, 3, "C0H\na0H\nA\nM\nA0\nB2V\nA0V\n"+"M\nc2\na2v\n"+"m\nb0\nb1v\n"+"M\nF\nb0\n"+"s\nc0\n"+"F\nb1\ns\nc0\n"+"F\na1\n"+"s\nb1\n"+"S\nF\nc1\n", bytes);
    TextPlayer player2 = createTextPlayerV2(3, 3, "b1V\na0U\n"+"m\na0\nb0\nb0d\n", bytes);
    player1.doOnePlacement("Destroyer", true);
    player1.doOnePlacement("Submarine", true);
    player2.doOnePlacement("Battleship", true);
    bytes.reset();
    String expected1 =
          "\n"+
          "    "+"Your Ocean"+"                       "+"Opponent's Ocean\n"+
          "  0|1|2                              0|1|2\n" +
          "A s|s|  A                          A  | |  A\n"+
          "B  | |  B                          B  | |  B\n"+
          "C d|d|d C                          C  | |  C\n"+
          "  0|1|2                              0|1|2\n\n"+
          "A, what would you like to do?\n"+
          "Illegal argument, can be either F, M or S.\n"+
          "A, what would you like to do?\n"+
          "A, select the ship to move?\n"+
          "A, where would you like to place a Submarine?\n"+
          "That placement is invalid: the ship overlaps another ship.\n"+
          "A, where would you like to place a Submarine?\n"+
          "  0|1|2\n"+
          "A s| |  A\n"+
          "B s| |  B\n"+
          "C d|d|d C\n"+
          "  0|1|2\n\n"+
          "\n"+
          "    "+"Your Ocean"+"                       "+"Opponent's Ocean\n"+
          "  0|1|2                              0|1|2\n" +
          "A s| |  A                          A  | |  A\n"+
          "B s| |  B                          B  | |  B\n"+
          "C d|d|d C                          C  | |  C\n"+
          "  0|1|2                              0|1|2\n\n"+
          "A, what would you like to do?\n"+
          "A, select the ship to move?\n"+
          "A, where would you like to place a Destroyer?\n"+
          "  0|1|2\n"+
          "A s| |d A\n"+
          "B s| |d B\n"+
          "C  | |d C\n"+
          "  0|1|2\n\n"+
          "\n"+
          "    "+"Your Ocean"+"                       "+"Opponent's Ocean\n"+
          "  0|1|2                              0|1|2\n" +
          "A s| |d A                          A  | |  A\n"+
          "B s| |d B                          B  | |  B\n"+
          "C  | |d C                          C  | |  C\n"+
          "  0|1|2                              0|1|2\n\n"+
          "A, what would you like to do?\n"+
          "A, select the ship to move?\n"+
          "A, where would you like to place a Submarine?\n"+
          "  0|1|2\n"+
          "A  | |d A\n"+
          "B  |s|d B\n"+
          "C  |s|d C\n"+
          "  0|1|2\n\n";
    String expected2 =
          "\n"+
          "    "+"Your Ocean"+"                       "+"Opponent's Ocean\n"+
          "  0|1|2                              0|1|2\n" +
          "A  | |d A                          A  | |  A\n"+
          "B  |s|d B                          B  | |  B\n"+
          "C  |s|d C                          C  | |  C\n"+
          "  0|1|2                              0|1|2\n\n"+
          "A, what would you like to do?\n"+
          "You have run out of usage of Move Ship.\n"+
          "A, what would you like to do?\n" +
          "A, where would you like to fire at?\n"+
            "You hit a Battleship!\n";
    String expected3 =
          "\n"+
          "    "+"Your Ocean"+"                       "+"Opponent's Ocean\n"+
          "  0|1|2                              0|1|2\n" +
          "A  | |d A                          A  | |  A\n"+
          "B  |s|d B                          B b| |  B\n"+
          "C  |s|d C                          C  | |  C\n"+
          "  0|1|2                              0|1|2\n\n"+
          "A, what would you like to do?\n"+
          "A, where do you want to scan?\n"+
          "Submarine occupies 0 squares.\n"+
          "Destroyer occupies 0 squares.\n"+
          "Carrier occupies 0 squares.\n"+
          "Battleship occupies 4 squares.\n";
    String expected4 =
          "\n"+
          "    "+"Your Ocean"+"                       "+"Opponent's Ocean\n"+
          "  0|1|2                              0|1|2\n" +
          "A  | |d A                          A  | |  A\n"+
          "B  |s|d B                          B b|b|  B\n"+
          "C  |s|d C                          C  | |  C\n"+
          "  0|1|2                              0|1|2\n\n"+
          "A, what would you like to do?\n"+
          "A, where do you want to scan?\n"+
          "Submarine occupies 0 squares.\n"+
          "Destroyer occupies 0 squares.\n"+
          "Carrier occupies 0 squares.\n"+
          "Battleship occupies 4 squares.\n";
    String expected5 =
          "\n"+
          "    "+"Your Ocean"+"                       "+"Opponent's Ocean\n"+
          "  0|1|2                              0|1|2\n" +
          "A  | |d A                          A  | |  A\n"+
          "B  |s|d B                          B b|b|  B\n"+
          "C  |s|d C                          C  | |  C\n"+
          "  0|1|2                              0|1|2\n\n"+
          "A, what would you like to do?\n"+
          "A, where would you like to fire at?\n"+
          "You missed.\n";
    String expected6 =
          "\n"+
          "    "+"Your Ocean"+"                       "+"Opponent's Ocean\n"+
          "  0|1|2                              0|1|2\n" +
          "A  | |d A                          A  |X|  A\n"+
          "B  |s|d B                          B b|b|  B\n"+
          "C  |s|d C                          C  | |  C\n"+
          "  0|1|2                              0|1|2\n\n"+
          "A, what would you like to do?\n"+
          "You have run out of usage of Sonar Scan.\n"+
          "A, what would you like to do?\n" +
          "A, where would you like to fire at?\n"+
          "You hit a Battleship!\n";
    player1.playOneTurnV2(player2.theBoard, player2.view);
    player1.playOneTurnV2(player2.theBoard, player2.view);
    player1.playOneTurnV2(player2.theBoard, player2.view);
    assertEquals(expected1, bytes.toString());
    bytes.reset();
    player1.playOneTurnV2(player2.theBoard, player2.view);
    assertEquals(expected2, bytes.toString());
    bytes.reset();
    player1.playOneTurnV2(player2.theBoard, player2.view);
    assertEquals(expected3, bytes.toString());
    player1.playOneTurnV2(player2.theBoard, player2.view);
    bytes.reset();
    player1.playOneTurnV2(player2.theBoard, player2.view);
    assertEquals(expected4, bytes.toString());
    player2.playOneTurnV2(player1.theBoard, player1.view);
    bytes.reset();
    player1.playOneTurnV2(player2.theBoard, player2.view);
    assertEquals(expected5, bytes.toString());

    player1.playOneTurnV2(player2.theBoard, player2.view);
    bytes.reset();
    player1.playOneTurnV2(player2.theBoard, player2.view);
    assertEquals(expected6, bytes.toString());
  }
  
  private TextPlayer createTextPlayerV1(int w, int h, String inputData, OutputStream bytes) {
    BufferedReader input = new BufferedReader(new StringReader(inputData));
    PrintStream output = new PrintStream(bytes, true);
    Board<Character> board = new BattleShipBoard<Character>(w, h, 'X');
    V1ShipFactory shipFactory = new V1ShipFactory();
    return new TextPlayer("A", board, input, output, shipFactory, false);
  }


  private TextPlayer createTextPlayerV2(int w, int h, String inputData, OutputStream bytes) {
    BufferedReader input = new BufferedReader(new StringReader(inputData));
    PrintStream output = new PrintStream(bytes, true);
    Board<Character> board = new BattleShipBoard<Character>(w, h, 'X');
    V1ShipFactory shipFactory = new V2ShipFactory();
    return new TextPlayer("A", board, input, output, shipFactory, false);
  }
}







