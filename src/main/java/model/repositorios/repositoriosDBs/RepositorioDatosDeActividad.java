package model.repositorios.repositoriosDBs;

import model.implementacionCSV.DatoDeActividad;

import java.util.List;

public class RepositorioDatosDeActividad extends GenericRepositoryJPA<DatoDeActividad> {


  private static RepositorioDatosDeActividad instance = null;

  static public RepositorioDatosDeActividad getInstance() {
    if (instance == null) {
      instance = new RepositorioDatosDeActividad();
    }
    instance.settearEntitys();
    return instance;

  }

  public List<DatoDeActividad> getDatosDeActividadByOrganizacion(Long id) {
    return entityManager.createQuery("from Organizacion where id_organizacion=" + id, DatoDeActividad.class).getResultList();
  }
}
