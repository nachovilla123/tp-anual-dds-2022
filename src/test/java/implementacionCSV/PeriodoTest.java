package implementacionCSV;

import model.implementacionCSV.Periodo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class PeriodoTest { // ESTOS TEST ANDAN TODOS


  @Test
  public void formatoFechaPeriodicidadMensual() {
    Assertions.assertEquals("MM/yyyy", Periodo.MENSUAL.getFormato());
  }

  @Test
  public void formatoMensualToDate() {
    Assertions.assertEquals(LocalDate.of(2022, 1, 1),
        Periodo.MENSUAL.toLocalDate("01/2022"));
  }

  @Test
  public void formatoMensualToString() {
    Assertions.assertEquals("05/2022",
        Periodo.MENSUAL.toDateTimeString(LocalDate.of(2022, 5, 1)));
  }

  @Test
  public void formatoFechaPeriodicidadAnual() {
    Assertions.assertEquals("yyyy", Periodo.ANUAL.getFormato());
  }

  @Test
  public void formatoAnualToDate() {
    Assertions.assertEquals(LocalDate.of(2020, 1, 1),
        Periodo.ANUAL.toLocalDate("2020"));
  }

  @Test
  public void formatoAnualToString() {
    Assertions.assertEquals("2021",
        Periodo.ANUAL.toDateTimeString(LocalDate.of(2021, 1, 1)));
  }
}