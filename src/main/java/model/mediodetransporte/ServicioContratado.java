package model.mediodetransporte;

import model.implementacionCSV.FactorDeEmision;
import model.implementacionCSV.tipoDeConsumo.TipoDeConsumo;
import lombok.Getter;
import lombok.Setter;
import model.organizacion.trayecto.Ubicacion;


import javax.persistence.*;

@Getter
@Setter

@Entity
@Table(name = "servicio_contratado")
public class ServicioContratado extends MedioDeTransporte {

  @Column(name = "tipo_servicio_contratado")
  private String tipoServiciocontratado;

 // @OneToOne(cascade = CascadeType.ALL)
  //@JoinColumn(name = "ubicacion_inicio")
 @AttributeOverrides({
     @AttributeOverride(name="localidad",column=@Column(name="localidad_inicio")),
     @AttributeOverride(name = "calle", column=@Column(name = "calle_inicio")),
     @AttributeOverride(name="altura", column=@Column (name = "altura_inicio"))
 })
 @Embedded()
  private Ubicacion ubicacionInicio;

  //@OneToOne(cascade = CascadeType.ALL)
  //@JoinColumn(name = "ubicacion_final")
  @AttributeOverrides({
      @AttributeOverride(name="localidad",column=@Column(name="localidad_final")),
      @AttributeOverride(name = "calle", column=@Column(name = "calle_final")),
      @AttributeOverride(name="altura", column=@Column (name = "altura_final"))
  })
  @Embedded()
  private Ubicacion ubicacionFinal;

  @Column(name = "cantidad_combustible_xKM")
  private Double cantidadDeCombustiblePorKM;

  public ServicioContratado() {

  }

  public ServicioContratado(String tipoServiciocontratado,
                            Ubicacion ubicacionInicio,
                            Ubicacion ubicacionFinal,
                            Double cantidadDeCombustiblePorKM,
                            FactorDeEmision factorDeEmision) {
    this.tipoServiciocontratado = tipoServiciocontratado;
    this.ubicacionInicio = ubicacionInicio;
    this.ubicacionFinal = ubicacionFinal;
    this.cantidadDeCombustiblePorKM = cantidadDeCombustiblePorKM;
    this.factorDeEmision= factorDeEmision;
  }

  public Boolean esCompartible(){
    return true;
  }


  public Double cantidadDeCombustibleConsumidoPorKM() { // entrega 3, dice q depende  del transporte
    return this.cantidadDeCombustiblePorKM;
  }


}