package organizacion;

import builders.TrayectoFactory;
import org.junit.jupiter.api.Test;
import model.organizacion.trayecto.Trayecto;

import static org.junit.jupiter.api.Assertions.*;

class TrayectoTest {

  //BUILDER TRAYECTO
  TrayectoFactory trayectoFactory = new TrayectoFactory();

  //-------------------------------- TESTS --------------------------------
  @Test
  public void hc_de_un_trayecto(){
  Trayecto trayectodeprueba = trayectoFactory.untrayectoCon3tramos60hc();
  Double hc = trayectodeprueba.calcularHc();

  assertEquals(60.0,trayectodeprueba.calcularHc());

  }
}