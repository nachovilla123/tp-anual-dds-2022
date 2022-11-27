package model.repositorios.repositoriosDBs;

import model.organizacion.Sector;

public class RepositorioSector extends GenericRepositoryJPA<Sector> {

  private static RepositorioSector instance = null;

  static public RepositorioSector getInstance() {
    if (instance == null) {
      instance = new RepositorioSector();
    }
    instance.settearEntitys();
    return instance;
  }


}
