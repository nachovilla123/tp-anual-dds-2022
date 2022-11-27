package organizacion;

import builders.TramoFactory;
import org.junit.jupiter.api.Test;
import model.organizacion.trayecto.Tramo;


import static org.junit.jupiter.api.Assertions.*;

class TramoTest {

  //BUILDER TRAMO
  TramoFactory tramoF = new TramoFactory();

  @Test
  public void calculo_huellacarbono_en_un_tramo() {

    Tramo tramoCon20Hc = tramoF.tramoConHCde20();
    Double hc = tramoCon20Hc.calculoHCxTramo();

    assertEquals(20.0, tramoCon20Hc.calculoHCxTramo());
  }


}