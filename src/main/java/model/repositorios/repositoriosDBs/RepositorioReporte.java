package model.repositorios.repositoriosDBs;

import model.organizacion.Organizacion;
import model.reporte.Reporte;

import java.util.List;

public class RepositorioReporte extends  GenericRepositoryJPA<Reporte>{

  private static RepositorioReporte instance = null;

  static public RepositorioReporte getInstance() {
    if (instance == null) {
      instance = new RepositorioReporte();
    }
    instance.settearEntitys();
    return instance;
  }


  public List<Reporte> getReportesByOrganizacion(Long id) {
    return entityManager.createQuery("from Reporte where id_organizacion=" + id, Reporte.class).getResultList();
  }
}
