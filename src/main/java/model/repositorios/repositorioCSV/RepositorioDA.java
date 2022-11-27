package model.repositorios.repositorioCSV;

import model.implementacionCSV.DatoDeActividad;
import model.implementacionCSV.Periodo;

import java.util.List;

public interface RepositorioDA {

  List<DatoDeActividad> obtenerDatosActividades();

  List<DatoDeActividad> obtenerDatosDeActividadPorPeriodo(Periodo periodo);

}
