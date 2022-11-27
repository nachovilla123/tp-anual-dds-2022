package model.usuario;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidadorContra {

  List<String> contraseniasFrecuentes = new ArrayList<>();

  public Boolean esValida(String password) {

    if (!this.matcheaRegex(password) && noEstaEnElTop10mil(password)) {
      throw new RuntimeException("La contreseña no es valida");
    }

    return true;
  }

  public ValidadorContra() {
    this.subirContrasMem();
  }


  //DEJAR COMENTADO HASTA TERMINAR TEST PORQUE AL CORRERLO POR EL SCANNER TIRA ERROR Y NO DEJA
  private void subirContrasMem() {

    try {
      File peoresContras = new File("/Peores10Mil.txt");
      //tira error pero esta bien hecho (ES POR EL PATH QUE NO LO AGARRA)
      Scanner contrasenias = new Scanner(peoresContras);
      while (contrasenias.hasNext()) {
        contraseniasFrecuentes.add(contrasenias.nextLine());
      }

    } catch (FileNotFoundException ex) {
      // insert code to run when exception occurs
    }

  }

  private Boolean noEstaEnElTop10mil(String password) {

    return !contraseniasFrecuentes.contains(password);
  }


  private Boolean matcheaRegex(String password) {
    // Regex to check valid password.
    String regex = "^(?=.*[0-9])"
        + "(?=.*[a-z])(?=.*[A-Z])"
        + "(?=.*[@#$%^&+=])"
        + "(?=\\S+$).{8,20}$";

    // Compile the ReGex
    Pattern p = Pattern.compile(regex);

    // If the password is empty
    // return false
    if (password == null) {
      return false;
    }

    // Pattern class contains matcher() method
    // to find matching between given password
    // and regular expression.
    Matcher m = p.matcher(password);

    // Return if the password
    // matched the ReGex
    return m.matches();
  }

}