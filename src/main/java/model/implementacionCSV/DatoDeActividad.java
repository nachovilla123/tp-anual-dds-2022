package model.implementacionCSV;

import model.Persistencia.PersistentEntity;
import model.implementacionCSV.tipoDeConsumo.TipoDeConsumo;
import lombok.Getter;
import lombok.Setter;
import org.uqbarproject.jpa.java8.extras.convert.LocalDateConverter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter

@Entity
@Table(name = "dato_de_actividad")
public class DatoDeActividad extends PersistentEntity {

  @OneToOne(cascade = CascadeType.ALL)
  FactorDeEmision factorDeEmision;

  @Enumerated(EnumType.STRING)
  Periodo periodo;

  @Column
  Double valorConsumo;

  @Convert(converter = LocalDateConverter.class)
  @Column(name = "periodoImputacion")
  LocalDate periodoImputacion;


  public DatoDeActividad(FactorDeEmision factorDeEmision, Double valorConsumo,
                         Periodo periodo, LocalDate periodoImputacion) {
    this.factorDeEmision = factorDeEmision;
    this.valorConsumo = valorConsumo;
    this.periodo = periodo;
    this.periodoImputacion = periodoImputacion;
  }

  public DatoDeActividad(){}


  public boolean perteneceAPeriodo(Periodo periodo) {
    return this.periodo.equals(periodo);
  }

  //Si yo hago que el fe este en kg unifico el valor final del calculo
  public Double calcularHC() {
    //return this.valorConsumo * this.getFactorDeEmision().getValor();
    return this.valorConsumo * factorDeEmision.getValor();
  }



  public Boolean sonDeLaMismaFecha(LocalDate unaFecha) {
    return this.periodo.sonDeLaMismaFecha(this.periodoImputacion,unaFecha);
  }



}