package model.exceptions;

public class CerrarLectura extends RuntimeException{
  public CerrarLectura(String msg){
    super(msg);
  }
}