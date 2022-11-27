package model.reporte;

import model.Persistencia.PersistentEntity;
import model.implementacionCSV.HuellaDeCarbono;
import lombok.Getter;
import lombok.Setter;
import model.implementacionCSV.Periodo;
import org.uqbarproject.jpa.java8.extras.convert.LocalDateConverter;

import javax.persistence.*;
import java.time.LocalDate;


@Getter
@Setter

@Entity
@Table(name= "reporte")
public class Reporte extends PersistentEntity {

  @AttributeOverrides({
      @AttributeOverride(name="valor",column=@Column(name="hc_actividad_general"))
  })
  @Embedded
  HuellaDeCarbono actividadesGenerales;
  @AttributeOverrides({
      @AttributeOverride(name="valor",column=@Column(name="hc_transporte"))
  })
  @Embedded
  HuellaDeCarbono tramos; // solo de los tramos de los miembros

  @Convert(converter = LocalDateConverter.class)
  @Column(name = "fecha_reporte")
  LocalDate fecha;

  @Column
  Double valorTotal;

  @Enumerated(EnumType.STRING)
  Periodo periodo;

  public  Reporte (){}

  public  Reporte (HuellaDeCarbono hcActividades, HuellaDeCarbono hcTramos, Periodo periodo){
    this.actividadesGenerales= hcActividades;
    this.tramos = hcTramos;
    this.fecha= LocalDate.now();
    this.periodo = periodo;
  }

  public Double getValorTotalHc(){
    return this.actividadesGenerales.valorEnEnKilogramos()+this.tramos.valorEnEnKilogramos();
  }

  public HuellaDeCarbono obtenerTotalHc(){
    return new HuellaDeCarbono(this.getValorTotalHc());
  }

  public boolean perteneceAPeriodo(Periodo periodo) {
    return this.periodo.equals(periodo);
  }

  public Boolean sonDeLaMismaFecha(LocalDate unaFecha) {
    return this.periodo.sonDeLaMismaFecha(this.fecha,unaFecha);
  }



}
