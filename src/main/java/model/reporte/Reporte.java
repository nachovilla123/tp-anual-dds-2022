package model.reporte;

import model.Persistencia.PersistentEntity;
import model.implementacionCSV.HuellaDeCarbono;
import lombok.Getter;
import lombok.Setter;
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

  public  Reporte (){}

  public  Reporte (HuellaDeCarbono hcActividades, HuellaDeCarbono hcTramos){
    this.actividadesGenerales= hcActividades;
    this.tramos = hcTramos;
    this.fecha= LocalDate.now();
  }

  public Double getValorTotalHc(){
    return this.actividadesGenerales.valorEnEnKilogramos()+this.tramos.valorEnEnKilogramos();
  }

  public HuellaDeCarbono obtenerTotalHc(){
    return new HuellaDeCarbono(this.getValorTotalHc());
  }

}
