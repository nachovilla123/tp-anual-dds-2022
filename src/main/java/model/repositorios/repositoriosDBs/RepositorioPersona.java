package model.repositorios.repositoriosDBs;


import model.organizacion.Persona;

public class RepositorioPersona extends GenericRepositoryJPA<Persona>{

  private static RepositorioPersona instance = null;

  static public RepositorioPersona getInstance() {
    if (instance == null) {
      instance = new RepositorioPersona();
      instance.settearEntitys();
    }
    instance.settearEntitys();
    return instance;
  }

  public Persona findByPersonName(String nom){
    return entityManager.createQuery("from Persona where nombre =  ?", Persona.class).setParameter(1,nom).getSingleResult();
  }
}
