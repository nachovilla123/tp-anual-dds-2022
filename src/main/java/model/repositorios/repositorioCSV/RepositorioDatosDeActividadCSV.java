package model.repositorios.repositorioCSV;

import model.implementacionCSV.DatoDeActividad;
import model.implementacionCSV.FactorDeEmision;
import model.implementacionCSV.GestorCSV;
import model.implementacionCSV.Periodo;
import model.implementacionCSV.tipoDeConsumo.TipoDeConsumo;
import model.repositorios.repositoriosDBs.RepositorioFactorDeEmision;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioDatosDeActividadCSV implements RepositorioDA {

  private GestorCSV gestorCSV;
  private RepositorioFE repoFE;

  public RepositorioDatosDeActividadCSV(String razonSocial,RepositorioFE repositorioFE) {
    this.gestorCSV = new GestorCSV(this.crearPath(razonSocial));
    this.repoFE = repositorioFE;
  }

  @Override
  public List<DatoDeActividad> obtenerDatosActividades() {

    List<DatoDeActividad> datos = new ArrayList<>();
    List<String[]> datosCSV = this.gestorCSV.obtenerRegistros();

    for (String[] datoCSV : datosCSV) {

      TipoDeConsumo tipoConsumo = TipoDeConsumo.valueOf(datoCSV[0]);
      Double valorConsumo = Double.valueOf(Long.parseLong(datoCSV[1]));
      Periodo periodo = Periodo.valueOf(datoCSV[2]);
      LocalDate periodoImputacion = periodo.toLocalDate(datoCSV[3]);

      //TODO
      FactorDeEmision ultimoFactorAsociado = RepositorioFactorDeEmision.getInstance().obtenerPorTipoDeConsumo(tipoConsumo);

      DatoDeActividad dato = new DatoDeActividad(ultimoFactorAsociado, valorConsumo, periodo, periodoImputacion);

      datos.add(dato);
    }

    return datos;
  }

  public List<DatoDeActividad> obtenerDatosDeActividadPorPeriodo(Periodo periodo) {
    return this.obtenerDatosActividades().stream()
        .filter(datoActividad -> datoActividad.perteneceAPeriodo(periodo))
        .collect(Collectors.toList());
  }

  private String crearPath(String razonSocial) {
    return "./src/main/resources/archivosCSV/"
        .concat(razonSocial + ".csv"); // o  "./src/main/"+razonSocial + ".csv"
  }
}