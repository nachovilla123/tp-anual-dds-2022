package model.repositorios.repositoriosDBs;

import model.implementacionCSV.FactorDeEmision;
import model.implementacionCSV.tipoDeConsumo.TipoDeConsumo;

import java.util.List;

public class RepositorioFactorDeEmision extends GenericRepositoryJPA<FactorDeEmision> {


  private static RepositorioFactorDeEmision instance = null;

  static public RepositorioFactorDeEmision getInstance() {
    if (instance == null) {
      instance = new RepositorioFactorDeEmision();
    }
    instance.settearEntitys();
    return instance;
  }


  //TODO
  public FactorDeEmision obtenerPorTipoDeConsumo(TipoDeConsumo tipoConsumo) {
    return  null;
  }
}
