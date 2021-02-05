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
    expected[0] = "  0|1|2\n" + "A  | |  A\n" + "B  | |  B\n" + "C s|s|s C\n" + "  0|1|2\n";
    expected[1] = "  0|1|2\n" + "A s|s|s A\n" + "B  | |  B\n" + "C s|s|s C\n" + "  0|1|2\n";
    player.doOnePlacement("Destroyer");
    assertEquals("Player A where would you like to place a Destroyer?\nThat placement is invalid: the ship goes off the bottom of the board.\nPlayer A where would you like to place a Destroyer?\n" + expected[0] + "\n", bytes.toString());
    bytes.reset();
    player.doOnePlacement("Destroyer");
    assertEquals("Player A where would you like to place a Destroyer?\n" + expected[1] + "\n", bytes.toString());
    bytes.reset();
  }

  @Test
  void test_IO_exception() throws IOException{
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(3, 3, "", bytes);
    assertThrows(IOException.class, ()->player.doOnePlacement("Destroyer"));
  }
  
  private TextPlayer createTextPlayer(int w, int h, String inputData, OutputStream bytes) {
    BufferedReader input = new BufferedReader(new StringReader(inputData));
    PrintStream output = new PrintStream(bytes, true);
    Board<Character> board = new BattleShipBoard<Character>(w, h);
    V1ShipFactory shipFactory = new V1ShipFactory();
    return new TextPlayer("A", board, input, output, shipFactory);
  }

}







