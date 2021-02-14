package edu.duke.adh39.battleship;

/**
 * This class builds on coordinate and have a extra field to represent the orientation.
 * It checks illegal input and support several basic getters as well as overwriting
 * several functions.
 */
public class Placement {
  private final Coordinate where;
  private final char orientation;

  /**
   * Initilize the placement based on straight coordinate and orientation.
   * @param coordinate and a character orientation
   */
  public Placement(Coordinate w, char o){
    this.where = w;
    char ori = Character.toUpperCase(o);
    this.orientation = ori;
  }

  /**
   * Initilize the placement based on the input string.
   * @param the string to be parsed into a placement
   * @throws IllegalArgumentException when invalid input encountered
   * could originally be thrown by the constructor of coordinate object.
   */
  public Placement(String desc) throws IllegalArgumentException {
    if (desc.length() != 3){
      throw new IllegalArgumentException("Invalid placement length.");
    }
    this.where = new Coordinate(desc.substring(0,2));
    char ori = Character.toUpperCase(desc.charAt(2));
    /*
    if (ori != 'V' && ori != 'H'){
      throw new IllegalArgumentException("Invalid placement orientation.");
    }
    */
    this.orientation = ori;
  }

  public Coordinate getCoordinate(){
    return where;
  }
  
  public char getOrientation(){
    return orientation;
  }

  @Override
  public String toString(){
    return where.toString()+"("+orientation+")";
  }

  @Override
  public int hashCode(){
    return toString().hashCode();
  }

  @Override
  public boolean equals(Object o){
    if (o.getClass().equals(getClass())){
      Placement p = (Placement) o;
      return where.equals(p.getCoordinate()) && orientation == p.getOrientation();   
    }
    return false;
  }

}













