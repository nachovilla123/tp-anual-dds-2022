package model.repositorios.repositoriosDBs;

import model.implementacionCSV.tipoDeConsumo.TipoDeConsumo;

public class RepositoriosTiposDeConsumo extends GenericRepositoryJPA<TipoDeConsumo> {

  private static RepositoriosTiposDeConsumo instance = null;

  static public RepositoriosTiposDeConsumo getInstance() {
    if (instance == null) {

      instance = new RepositoriosTiposDeConsumo();

    }
    instance.settearEntitys();
    return instance;

  }
}
