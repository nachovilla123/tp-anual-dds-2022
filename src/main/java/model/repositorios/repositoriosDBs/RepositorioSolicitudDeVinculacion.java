package model.repositorios.repositoriosDBs;

import model.organizacion.SolicitudDeVinculacion;

public class RepositorioSolicitudDeVinculacion extends GenericRepositoryJPA<SolicitudDeVinculacion> {

  private static RepositorioSolicitudDeVinculacion instance = null;

  static public RepositorioSolicitudDeVinculacion getInstance() {
    if (instance == null) {
      instance = new RepositorioSolicitudDeVinculacion();
    }
    instance.settearEntitys();
    return instance;
  }

}
