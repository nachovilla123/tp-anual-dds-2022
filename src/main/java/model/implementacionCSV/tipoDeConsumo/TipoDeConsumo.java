package model.implementacionCSV.tipoDeConsumo;

import model.implementacionCSV.FactorDeEmision;
import model.implementacionCSV.tipoDeConsumo.componentes.Actividad;
import model.implementacionCSV.tipoDeConsumo.componentes.Alcance;
import model.implementacionCSV.tipoDeConsumo.componentes.Unidad;
import lombok.Getter;

import javax.persistence.*;

@Getter


public enum TipoDeConsumo {

  GAS_NATURAL(Unidad.M3, Actividad.COMBUSTION_FIJA, Alcance.EMISION_DIRECTA),
  DIESEL_GASOIL(Unidad.LT, Actividad.COMBUSTION_FIJA, Alcance.EMISION_DIRECTA),
  NAFTA(Unidad.LT, Actividad.COMBUSTION_FIJA, Alcance.EMISION_DIRECTA),
  COMBUSTIBLE_CONSUMIDO_GASOIL(Unidad.LTS, Actividad.COMBUSTION_MOVIL, Alcance.EMISION_DIRECTA),
  COMBUSTIBLE_CONSUMIDO_NAFTA(Unidad.LTS, Actividad.COMBUSTION_MOVIL, Alcance.EMISION_DIRECTA),
  ELECTRICIDAD(Unidad.KWH, Actividad.ELECTRICIDAD_ADQUIRIDA_Y_CONSUMIDA, Alcance.EMISION_INDIRECTA_ASOCIADA_A_ELECTRICIDAD),
  CAMION_DE_CARGA(null, Actividad.LOGISTICA_DE_PRODUCTOS_Y_RESIDUOS, Alcance.EMISION_INDIRECTA_NO_CONTROLADA_POR_ORGANIZACION),
  UTILITARIO_LIVIANO(null, Actividad.LOGISTICA_DE_PRODUCTOS_Y_RESIDUOS, Alcance.EMISION_INDIRECTA_NO_CONTROLADA_POR_ORGANIZACION),
  DISTANCIA_MEDIA_RECORRIDA(Unidad.KM, Actividad.LOGISTICA_DE_PRODUCTOS_Y_RESIDUOS, Alcance.EMISION_INDIRECTA_NO_CONTROLADA_POR_ORGANIZACION);


  private Unidad unidad;

  private Actividad actividadConsumo;


  private Alcance alcanceConsumo;



  TipoDeConsumo(Unidad unidad, Actividad actConsumo, Alcance alcanceConsumo) {
    this.unidad = unidad;
    this.actividadConsumo = actConsumo;
    this.alcanceConsumo = alcanceConsumo;
  }


}





