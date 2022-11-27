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

  public SectorTerritorial findBySectorName(String nom){
    return entityManager.createQuery("from SectorTerritorial where sectorTerritorial =  ?", SectorTerritorial.class).setParameter(1,nom).getSingleResult();
  }
}
