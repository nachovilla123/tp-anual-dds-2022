package model.repositorios.repositorioCSV;

import model.implementacionCSV.DatoDeActividad;
import model.implementacionCSV.FactorDeEmision;
import model.implementacionCSV.GestorCSV;
import model.implementacionCSV.tipoDeConsumo.TipoDeConsumo;

import java.util.ArrayList;
import java.util.List;

public class RepositorioFactorDeEmisionCSV implements RepositorioFE{

  private GestorCSV gestorCSV;

  public RepositorioFactorDeEmisionCSV(String nombreArchivo) {

    this.gestorCSV = new GestorCSV(this.crearPath(nombreArchivo));

  }


  //ESTO HACE UN UPDATE EN LA BD SIEMPRE Y CUANDO YA EXISTA UN FE CON ESE TIPO DE CONSUMO
  @Override
  public List<FactorDeEmision> actualizarFactores() {

    List<FactorDeEmision> factores = new ArrayList<>();
    List<String[]> factoresCSV = this.gestorCSV.obtenerRegistros();


    for (String[] factorCSV : factoresCSV) {

      //TODO FALTA VALIDAR??
      TipoDeConsumo tipoConsumo = TipoDeConsumo.valueOf(factorCSV[0]);
      Double valor = Double.valueOf(Long.parseLong(factorCSV[1]));

     FactorDeEmision factor = new FactorDeEmision(valor,tipoConsumo);

      factores.add(factor);

    }

    return factores;

  }


  private String crearPath(String nombreArchivo) {
    return "./src/main/resources/archivosCSV/"
        .concat(nombreArchivo + ".csv"); // o  "./src/main/"+nombreArchivo + ".csv"
  }

}
