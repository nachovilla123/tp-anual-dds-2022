package implementacionCSV;

import model.implementacionCSV.FactorDeEmision;
import model.implementacionCSV.tipoDeConsumo.TipoDeConsumo;
import model.repositorios.repositorioCSV.RepositorioDatosDeActividadCSV;
import model.repositorios.repositorioCSV.RepositorioFactorDeEmisionCSV;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GestorCSVTest {


  @Test
  public void leerTodosLosDAEnCSV() {
    RepositorioFactorDeEmisionCSV repoFE = new RepositorioFactorDeEmisionCSV("FECSV"); // se ve que no lo usamos para nada
    RepositorioDatosDeActividadCSV repoDA = new RepositorioDatosDeActividadCSV("DACSV",repoFE);
    System.out.print(repoDA.obtenerDatosActividades().toString());
  }
  @Test
  public void leerTodosLosFEEnCSV() {
    RepositorioFactorDeEmisionCSV repoFE = new RepositorioFactorDeEmisionCSV("FECSV"); // se ve que no lo usamos para nada
    List<FactorDeEmision> factores = repoFE.actualizarFactores();

    assertEquals(factores.stream().allMatch(factor ->factor.getValor() == 1.0 ),true);

  }

}