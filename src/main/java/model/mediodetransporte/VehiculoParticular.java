package model.mediodetransporte;

import model.implementacionCSV.FactorDeEmision;
import model.implementacionCSV.tipoDeConsumo.TipoDeConsumo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter

@Entity
@Table(name = "vehiculo_particular")
public class VehiculoParticular extends MedioDeTransporte {

  @Enumerated(EnumType.STRING)
  private TipoCombustible tipoCombustible;

  @Enumerated(EnumType.STRING)
  private TipotransporteParticular tipotransporteParticular;

  @Column(name = "cantidad_combustible_xKM")
  private double cantidadDeCombustiblePorKM;

  public VehiculoParticular() {

  }

  public VehiculoParticular(TipoCombustible tipoCombustible, TipotransporteParticular tipotransporteParticular, double cantidadDeCombustiblePorKM, FactorDeEmision factorDeEmision) {
    this.tipoCombustible = tipoCombustible;
    this.tipotransporteParticular = tipotransporteParticular;
    this.cantidadDeCombustiblePorKM = cantidadDeCombustiblePorKM;
    this.factorDeEmision = factorDeEmision;
  }

  public enum TipotransporteParticular {
    MOTO, AUTO, CAMIONETA
  }

  public enum TipoCombustible {
    GNC, NAFTA, ELECTRICO, GASOIL
  }

  @Override
  public Double cantidadDeCombustibleConsumidoPorKM() {
    return this.cantidadDeCombustiblePorKM;
  }

  public Boolean esCompartible(){
    return true;
  }

}