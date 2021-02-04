package edu.duke.adh39.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;

/**
 * This class initialize one single user that consists of its own board, view, name, input and output source(sharing for now), a shared factory.
 */
public class TextPlayer {
  final Board<Character> theBoard;
  final BoardTextView view;
  final BufferedReader inputReader;
  final PrintStream out;
  final AbstractShipFactory<Character> factory;
  final String name;

  public TextPlayer(String name, Board<Character> theBoard, BufferedReader inputSource, PrintStream out, AbstractShipFactory<Character> factory) {
    this.name = name;
    this.theBoard = theBoard;
    this.view = new BoardTextView(theBoard);
    this.inputReader = inputSource;
    this.out = out;
    this.factory = factory;
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
  public void doOnePlacement() throws IOException {
    Placement p = readPlacement("Player " + name + " where would you like to put a Destroyer?");
    Ship<Character> s  = factory.makeDestroyer(p);
    theBoard.tryAddShip(s);
    out.println(view.displayMyOwnBoard());
  }
}


