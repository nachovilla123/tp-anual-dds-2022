package contraseniasTest;

import model.usuario.ValidadorContra;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestContrasenias {

  // Registrar administradores //  Por el momento, sólo se requiere guardar usuario y contraseña. // TEST DE SEGURIDAD
  @Test
  public void contraseniaValida() {
    // deberia funcionar, cumple todos requisitos
    assertEquals(validadorContraGenerico().esValida("disenioDeSistem@s13"), true);
  }

  @Test
  public void contraseniaComun() {
    //falso, contraseña comun del top 10.000

    assertThrows(RuntimeException.class, () -> {
      validadorContraGenerico().esValida("baseball");
    });

  }

  @Test
  public void contraseniaComunTodosNumeros() {
    //falso, contraseña comun del top 10.000
    assertThrows(RuntimeException.class, () -> {
      validadorContraGenerico().esValida("7777777");
    });
  }

  @Test
  public void contraseniaMuyLarga() {
    // falso, excede la cantidad de caracteres permitidos
    assertThrows(RuntimeException.class, () -> {
      validadorContraGenerico().esValida("1234567891011121314151617181920");
    });
  }

  @Test
  public void contraseniaSinMayuscula() {
    // falso, no tiene mayuscula
    assertThrows(RuntimeException.class, () -> {
      validadorContraGenerico().esValida("geeks@portal20");
    });
  }

  @Test
  public void contraseniaSinCaracterEspecial() {
    //falso, no tiene caracter especial
    assertThrows(RuntimeException.class, () -> {
      validadorContraGenerico().esValida("disenioDeSistemas13");
    });
  }

  @Test
  public void contraseniaConEspacios() {
    //falso, tiene espacios la contraseña
    assertThrows(RuntimeException.class, () -> {
      validadorContraGenerico().esValida("The@ best02");
    });
  }

  private ValidadorContra validadorContraGenerico() {
    return new ValidadorContra();
  }

}
