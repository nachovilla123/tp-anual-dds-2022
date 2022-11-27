package implementacionCSV;

import model.repositorios.repositorioCSV.RepositorioFE;
import model.repositorios.repositorioCSV.RepositorioFactorDeEmisionCSV;

class DatoDeActividadTest {

  //---------------------- PREPARACIONES ----------------------//
  TipoDeConsumoTest buildertipoconsumo = new TipoDeConsumoTest();
  RepositorioFE repoFE = new RepositorioFactorDeEmisionCSV("hola");

  // para generar un LOCAL DATE : LocalDate.of(2022, 09, 1)
/*
  public DatoDeActividad datoActividad_01() {return new DatoDeActividad(buildertipoconsumo.tdc_kg_emDir_comb_fij(),15.0,Periodo.ANUAL, LocalDate.of(2021, 10, 1),repoFE); }

  public DatoDeActividad datoActividad_02() {return new DatoDeActividad(buildertipoconsumo.tdc_lts_logs_emindirecta(),15.0,Periodo.ANUAL, LocalDate.of(2021, 9, 1),repoFE); }

  public DatoDeActividad datoActividad_03() {return new DatoDeActividad(buildertipoconsumo.tdc_m3_emDir_comb_fij(),15.0,Periodo.MENSUAL, LocalDate.of(2021, 6, 1),repoFE); }

  public DatoDeActividad datoActividad_04() {return new DatoDeActividad(buildertipoconsumo.tdc_km_combmovil_emDir(),15.0,Periodo.MENSUAL, LocalDate.of(2021, 5, 1),repoFE ); }
 */
}
