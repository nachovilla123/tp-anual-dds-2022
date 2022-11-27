package model.repositorios.repositoriosDBs;

import model.implementacionCSV.FactorDeEmision;
import model.implementacionCSV.tipoDeConsumo.TipoDeConsumo;
import model.organizacion.Persona;

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
    return entityManager.createQuery("from FactorDeEmision where tipoDeConsumo =  ?", FactorDeEmision.class).setParameter(1,tipoConsumo).getResultList().get(0);

  }
}
