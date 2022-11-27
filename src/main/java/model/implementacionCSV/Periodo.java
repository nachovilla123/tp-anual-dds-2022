package model.implementacionCSV;


import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

//@Entity
//@Table(name = "periodo")


public enum Periodo {
  MENSUAL("MM/yyyy"),
  ANUAL("yyyy");


  private final String formato;


  private final DateTimeFormatter formateador;

  Periodo(String formatDate) {
    this.formato = formatDate;
    this.formateador = new DateTimeFormatterBuilder()
        .appendPattern(this.formato)
        .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
        .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
        .toFormatter();
  }

  public LocalDate toLocalDate(String fecha) {
    return LocalDate.parse(fecha, this.formateador);
  }

  public String toDateTimeString(LocalDate fecha) {
    return this.formateador.format(fecha);
  }

  public String getFormato() {
    return this.formato;
  }

  public Boolean sonDeLaMismaFecha(LocalDate unaFecha, LocalDate otraFecha) {

    return this.toDateTimeString(unaFecha).equals(this.toDateTimeString(otraFecha));
  }


}