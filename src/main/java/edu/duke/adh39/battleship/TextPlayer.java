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
  }

  /**
   * This function prompt the user for a input.
   * @return the placement after the conversion.
   * @throws IOException if the input readline fails.
   */
  public Placement readPlacement(String prompt) throws IOException {
    out.println(prompt);
    String s = inputReader.readLine();
    return new Placement(s);
  }

  /**
   * This function place a ship on the board and display the board afterwards.
   * @throws IOException if the input outputing the result fails.
   */
  public void doOnePlacement(String shipName) throws IOException {
    Placement p = readPlacement("Player " + name + " where would you like to place a " + shipName + "?");
    Ship<Character> ship = shipCreationFns.get(shipName).apply(p);
    theBoard.tryAddShip(ship);
    out.println(view.displayMyOwnBoard());
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










