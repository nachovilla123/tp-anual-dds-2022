package model.implementacionCSV;


import javax.persistence.Column;
import javax.persistence.Embeddable;


//@Entity
//@Table(name= "huella_carbono")
@Embeddable
public class HuellaDeCarbono {

  @Column
  private Double valor; //Por defecto esta en kg

  public HuellaDeCarbono(Double valor) {
    this.valor = valor;
  }
  public HuellaDeCarbono(){}

  public Double valorEnEnToneladas() {return this.valor / 1000;}

  public Double valorEnEnKilogramos() {
    return this.valor;
  }

  public Double valorGramos() {
    return this.valor * 1000;
  }

}
