package edu.duke.adh39.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.function.Function;

/**
 * This class initialize one single user that consists of its own board, view, name, ships to 
 * place, creation function with respect to ship type, input and output source(sharing for now), a shared factory.
 */
public class TextPlayer {
  final Board<Character> theBoard;
  final BoardTextView view;
  final BufferedReader inputReader;
  final PrintStream out;
  final AbstractShipFactory<Character> factory;
  final String name;
  final ArrayList<String> shipsToPlace;
  final HashMap<String, Function<Placement, Ship<Character>>> shipCreationFns;
  final Integer moveCount;
  final Integer sonarCount;

  /**
   * This constructor initialize all the field in the textplayer class.
   * @param name of the player, the board that belongs to the player, the place to read input from and the place to dump output to, finally the class to create different ships.
   */
  public TextPlayer(String name, Board<Character> theBoard, BufferedReader inputSource, PrintStream out, AbstractShipFactory<Character> factory) {
    this.name = name;
    this.theBoard = theBoard;
    this.view = new BoardTextView(theBoard);
    this.inputReader = inputSource;
    this.out = out;
    this.factory = factory;
    this.shipsToPlace = new ArrayList<String>();
    this.shipCreationFns = new HashMap<String, Function<Placement, Ship<Character>>>();
    setupShipCreationMap();
    setupShipCreationList();
    this.moveCount = 3;
    this.sonarCount = 3;
  }

  /**
   * This function prompt the user for a placement input.
   * @param things to prompt the user for.
   * @return the placement after the conversion.
   * @throws IOException if the input readline fails, IllegalArgumentException if the input is invalid.
   */
  public Placement readPlacement(String prompt) throws IOException, IllegalArgumentException {
    out.println(prompt);
    String s = inputReader.readLine();
    if (s == null) {
      throw new IOException();
    }
    return new Placement(s);
  }

  /**
   * This function prompt the user for a Coordinate input.
   * @param things to prompt the user for.
   * @return the coordinate after the conversion.
   * @throws IOException if the input readline fails, IllegalArgumentException if the input is invalid.
   */
  public Coordinate readCoordinate(String prompt) throws IOException, IllegalArgumentException {
    out.println(prompt);
    String s = inputReader.readLine();
    if (s == null) {
      throw new IOException();
    }
    return new Coordinate(s);
  }

  /**
   * This function prompt the user for a action input.
   * @param things to prompt the user for.
   * @return the coordinate after the conversion.
   * @throws IOException if the input readline fails, IllegalArgumentException if the input is invalid.
   */
  public String readAction(String prompt) throws IOException, IllegalArgumentException {
    out.println(prompt);
    String s = inputReader.readLine();
    if (s == null) {
      throw new IOException();
    }
    s = s.toUpperCase();
    if (s.length() != 1) {
      throw new IllegalArgumentException("Illegal argument length, have to be a single character.");
    }
    if (s != "F" || s != "M" || s != "S") {
      throw new IllegalArgumentException("Illegal argument, can be either F, M or S.");
    }
    return s;
  }

  /**
   * This function place a ship on the board and display the board afterwards.
   * It also handles all the previous IllegalArgumentException.
   * @throws IOException if the input outputing the result fails.
   */
  public Ship<Character> doOnePlacement(String shipName) throws IOException {
    Boolean success = false;
    Ship<Character> ship = null;
    while (!success) {
      try {
        Placement p = readPlacement(name + ", where would you like to place a " + shipName + "?");
        ship = shipCreationFns.get(shipName).apply(p);
        String result = theBoard.tryAddShip(ship);
        if (result != null) {
          throw new IllegalArgumentException(result);
        }
        out.println(view.displayMyOwnBoard());
        success = true;
      }
      catch (IllegalArgumentException ilg){
        out.println(ilg.getMessage());
      }
    }
    return ship;
  }
  
  /**
   * This function checks if the player have lost by calling the function of the board.
   * @return true if the player have lost, false other wise.
   */
  public Boolean checkIfLost() {
    return theBoard.hasLost();
  }

  /**
   * This function realize one turn of the attacking phase of a player in version 1.
   * It calls two helper function to realize the functionalities.
   * Illegal coordinate input is also handled at this level.
   * @param enemy's board and the view of that board.
   * @throws IOException if the input readline fails.
   */
  public void playOneTurnV1(Board<Character> enemyBoard, BoardTextView enemyView) throws IOException {
    displayBothBoard(enemyView);
    fireAction(enemyBoard);
  }

  /**
   * This function have the current player to fire at a certain location on the enemy board.
   * It will display information telling whether the fire was a hit and what type of ship if hit.
   * Illegal coordinate input is also handled at this level.
   * @param enemy's board and the view of that board.
   * @throws IOException if the input readline fails.
   */
  private void fireAction(Board<Character> enemyBoard) throws IOException
  {
    Boolean success = false;
    while (!success) {
      try{
        Coordinate c = readCoordinate(name + ", where would you like to fire at?");
        Ship<Character> s = enemyBoard.fireAt(c);
        success = true;
        if (s == null) {
          out.println("You missed.");
        }
        else {
          out.println("You hit a "+s.getName()+"!");
        }
      }
      catch(IllegalArgumentException ilg) {
        out.println(ilg.getMessage());
      }
    }
    if (enemyBoard.hasLost()) {
      out.println(name+" Won!"); 
    }
  }

  /**
   * This function have the current player move a ship to another location.
   * Illegal coordinate input is handled at this level.
   * @throws IOException if the input readline fails.
   */
  private void moveAction() throws IOException {
    Boolean success = false;
    while (!success) {
      try {
        Coordinate c = readCoordinate(name + ", select the ship to move?");
        Ship<Character> sOld = theBoard.getShipAt(c);
        if (sOld == null) {
          throw new IllegalArgumentException("No ship at this location.");
        }
        Ship<Character> sNew = doOnePlacement(sOld.getName());
        sNew.changeCoordinate(sOld);
        theBoard.removeShip(sOld);
      }
      catch (IllegalArgumentException ilg) {
        out.println(ilg.getMessage());
      }
    }
  }

  /**
   * This function have the current player scan a portion of the enemy's board.
   * It will display how many squares each type of ship occupies.
   * Illegal coordinate input is also handled at this level.
   * @param enemy's board.
   * @throws IOException if the input readline fails.
   */
  private void scanAction(Board<Character> enemyBoard) throws IOException {
    Boolean success = false;
    while (!success) {
      try {
        Coordinate c = readCoordinate(name + ", where do you want to scan?");
        HashMap<String, Integer> scanRes = enemyBoard.sonarScan(c);
        scanRes.entrySet().forEach(entry->{System.out.println(entry.getKey() + " occupies " + entry.getValue() + " squares.");});
      }
      catch (IllegalArgumentException ilg) {
        out.println(ilg.getMessage());
      }
    }
  }
  /**
   * This function realize one turn of the attacking phase of a player in version 2.
   * It calls two helper function to realize the functionalities.
   * Illegal coordinate input is also handled at this level.
   * @param enemy's board and the view of that board.
   * @throws IOException if the input readline fails.
   */
  public void playOneTurnV2(Board<Character> enemyBoard, BoardTextView enemyView) throws IOException {
    displayBothBoard(enemyView);
    Boolean success = false;
    while (!success) {
      try {
        String action = readAction(name + ", what would you like to do?");
        if (action == "F") {
          fireAction(enemyBoard);
        }
        else if (action == "M") {
          moveAction();
        }
        else {//Scan
          scanAction(enemyBoard);
        }
      }
      catch (IllegalArgumentException ilg) {
        out.println(ilg.getMessage());
      }
    }
    
  }
  
  /**
   * This function display the message explaining basic rules on placing the ships along with a empty board.
   */
  public void displayExplainMessage() {
    out.println(view.displayMyOwnBoard());
    out.println(name+" you are going to place the following ships (which are all\n"+
                   "rectangular). For each ship, type the coordinate of the upper left\n"+
                   "side of the ship, followed by either H (for horizontal) or V (for\n"+
                   "vertical).  For example M4H would place a ship horizontally starting\n"+
                   "at M4 and going to the right.  You have\n\n"+
                   "2 Submarines ships that are 1x2\n"+
                   "3 Destroyers that are 1x3\n"+
                   "3 Battleships that are 1x4\n"+
                   "2 Carriers that are 1x6\n");
  }

  /**
   * This function display the current board.
   */
  public void simpleDisplayBoard() {
    out.println(view.displayMyOwnBoard());
  }

  /**
   * This function display the current board of you and the enemy.
   */
  private void displayBothBoard(BoardTextView enemyView) {
    out.println(view.displayMyBoardWithEnemyNextToIt(enemyView, "Your Ocean", "Opponent's Ocean"));
  }
  
  /**
   * This function set up the mapping between ship type and corresponding ship creation function.
   */
  protected void setupShipCreationMap() {
    shipCreationFns.put("Submarine", (p) -> factory.makeSubmarine(p));
    shipCreationFns.put("Battleship", (p) -> factory.makeBattleship(p));
    shipCreationFns.put("Destroyer", (p) -> factory.makeDestroyer(p));
    shipCreationFns.put("Carrier", (p) -> factory.makeCarrier(p));
  }

  /**
   * This function set up the list of ship to be createtd by the user, add it to a ArrayList in collections.
   */
  protected void setupShipCreationList() {
    shipsToPlace.addAll(Collections.nCopies(2, "Submarine"));
    shipsToPlace.addAll(Collections.nCopies(3, "Destroyer"));
    shipsToPlace.addAll(Collections.nCopies(3, "Battleship"));
    shipsToPlace.addAll(Collections.nCopies(2, "Carrier"));

  }
}










