package edu.duke.adh39.battleship;

public class Placement {
  private final Coordinate where;
  private final char orientation;

  public Placement(Coordinate w, char o){
    this.where = w;
    char ori = Character.toUpperCase(o);
    if (ori != 'V' && ori != 'H'){
      throw new IllegalArgumentException("Invalid placement orientation.");
    }
    this.orientation = ori;
  }

  public Placement(String desc){
    if (desc.length() != 3){
      throw new IllegalArgumentException("Invalid placement length.");
    }
    this.where = new Coordinate(desc.substring(0,2));
    char ori = Character.toUpperCase(desc.charAt(2));
    if (ori != 'V' && ori != 'H'){
      throw new IllegalArgumentException("Invalid placement orientation.");
    }
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













