package model.exceptions;

public class CerrarCSV extends RuntimeException{
  public CerrarCSV(String msg){
    super(msg);
  }
}