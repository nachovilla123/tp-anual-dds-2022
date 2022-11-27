package model.organizacion.trayecto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
//@Entity
//@Table(name = "ubicacion")
@Embeddable
public class Ubicacion {

  @Column
  public int localidad;
  @Column
  public String calle;
  @Column
  public int altura;


  public Ubicacion(int localidad, String calle, int altura) {
    this.localidad = localidad;
    this.calle = calle;
    this.altura = altura;
  }

  public Ubicacion() {

  }


  public boolean esIgual(Ubicacion ubicacion) {
    return this.localidad == ubicacion.getLocalidad()
        && this.calle.equals(ubicacion.getCalle())
        && this.altura == ubicacion.getAltura();
  }
}
