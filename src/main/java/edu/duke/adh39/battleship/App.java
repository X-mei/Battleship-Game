/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.adh39.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * This class initialize the Game App.
 * It generates two players.
 */
public class App {
  final TextPlayer p1;
  final TextPlayer p2;

  /**
   * Constructs a Game app which consists of two players.
   * @param theBoard1/theBoard2 is the board for two players to act on, inputSource/out is the place to read/write input from/to. 
   */
  public App(Board<Character> theBoard1, Board<Character> theBoard2, BufferedReader inputSource, PrintStream out, V1ShipFactory factory, Boolean isCom1, Boolean isCom2) {
    this.p1 = new TextPlayer("Player1", theBoard1, inputSource, out, factory, isCom1);
    this.p2 = new TextPlayer("Player2", theBoard2, inputSource, out, factory, isCom2);
  }

  /**
   * This method execute one placement phase where ships for both player is put on the board of each player's board.
   * @throws IOException if the doOnePlacement throws a IOException.
   */
  public void doPlacementPhase() throws IOException {
    p1.displayExplainMessage();
    for (String str : p1.shipsToPlace) {
      p1.doOnePlacement(str, true);
    }
    p1.simpleDisplayBoard();
    p2.displayExplainMessage();
    for (String str : p2.shipsToPlace) {
      p2.doOnePlacement(str, true);
    }
    p2.simpleDisplayBoard();
  }

  /**
   * This method execute the attack phase of version 1.
   * The phase is ended when one player lost
   * @throws IOException if the playOneTurn throws a IOException.
   */
  public void doAttackingPhaseV1() throws IOException {
    while (true) {
      p1.playOneTurnV1(p2.theBoard, p2.view);
      if (p2.checkIfLost()) {
        break;
      }
      p2.playOneTurnV1(p1.theBoard, p1.view);
      if (p1.checkIfLost()) {
        break;
      }
    }
  }

  /**
   * This method execute the attack phase of version 2.
   * The phase is ended when one player lost
   * @throws IOException if the playOneTurn throws a IOException.
   */
  public void doAttackingPhaseV2() throws IOException {
    while (true) {
      p1.playOneTurnV2(p2.theBoard, p2.view);
      if (p2.checkIfLost()) {
        break;
      }
      p2.playOneTurnV2(p1.theBoard, p1.view);
      if (p1.checkIfLost()) {
        break;
      }
    }
  }
  
  /**
   * This function runs the main program setting up two players and runs two phase, placement phase and attacking phase.
   */
  public static void main(String[] args) throws IOException {
    
    Board<Character> b1 = new BattleShipBoard<Character>(10, 20, 'X');
    Board<Character> b2 = new BattleShipBoard<Character>(10, 20, 'X');
    BufferedReader inputSource = new BufferedReader(new InputStreamReader(System.in));
    PrintStream outputDestination = System.out;
    Character version = null;
    outputDestination.println("Welcome to the game of BattleShip!\n\n"+"#################################################################\n\n"+"Please select version(please type 1 or 2):");
    while (true) {
      String s = inputSource.readLine();
      version = s.charAt(0);
      if (version == '1' || version == '2') {
        break;
      }
    }
    Character p1IsCom = null;
    Character p2IsCom = null;
    while (true) {
      outputDestination.println("Is player 1 a computer?(1 for true/0 for false)");
      String s = inputSource.readLine();
      p1IsCom = s.charAt(0);
      if (p1IsCom == '0' || p1IsCom == '1') {
        break;
      }
    }
    while (true) {
      outputDestination.println("Is player 2 a computer?(1 for true/0 for false)");
      String s = inputSource.readLine();
      p2IsCom = s.charAt(0);
      if (p2IsCom == '0' || p2IsCom == '1') {
        break;
      }
    }
    Boolean C1 = (p1IsCom == 1) ? true:false;
    Boolean C2 = (p2IsCom == 1) ? true:false;
    if (version == 1) {
      App app = new App(b1, b2, inputSource, System.out, new V1ShipFactory<Character>(), C1, C2);
      app.doPlacementPhase();
      app.doAttackingPhaseV1();
    }
    else {
      App app = new App(b1, b2, inputSource, System.out, new V2ShipFactory<Character>(), C1, C2);
      app.doPlacementPhase();
      app.doAttackingPhaseV2();
    }
  }
}











