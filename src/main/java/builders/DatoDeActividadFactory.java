package builders;

import model.implementacionCSV.DatoDeActividad;
import model.implementacionCSV.FactorDeEmision;
import model.implementacionCSV.Periodo;
import model.implementacionCSV.tipoDeConsumo.TipoDeConsumo;

import java.time.LocalDate;

public class DatoDeActividadFactory {

  private DatoDeActividad generarDatoDeActividad(Double valor,TipoDeConsumo tipoDeConsumo, Double valorConsumo, Periodo periodo, LocalDate periodoImputacion){
    return  new DatoDeActividad(this.factorDeEmision(valor,tipoDeConsumo),valorConsumo,periodo,periodoImputacion);
  }

  private FactorDeEmision factorDeEmision( Double valor, TipoDeConsumo tipoDeConsumo){
    return new FactorDeEmision(valor,tipoDeConsumo);
  }

}
