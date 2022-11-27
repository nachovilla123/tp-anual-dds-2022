package model.repositorios.repositoriosDBs;

import model.agentesSectoriales.SectorTerritorial;

public class RepositorioSectorTerritorial extends GenericRepositoryJPA<SectorTerritorial> {

  private static RepositorioSectorTerritorial instance = null;

  static public RepositorioSectorTerritorial getInstance() {
    if (instance == null) {
      instance = new RepositorioSectorTerritorial();
    }
    instance.settearEntitys();
    return instance;
  }

}
