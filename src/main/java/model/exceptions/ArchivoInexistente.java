package model.exceptions;

public class ArchivoInexistente extends RuntimeException{
  public ArchivoInexistente(String msg){
    super(msg);
  }
}
