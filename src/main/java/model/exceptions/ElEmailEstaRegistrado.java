package model.exceptions;

public class ElEmailEstaRegistrado extends RuntimeException {

    public ElEmailEstaRegistrado(String message) {
        super(message);
    }
}