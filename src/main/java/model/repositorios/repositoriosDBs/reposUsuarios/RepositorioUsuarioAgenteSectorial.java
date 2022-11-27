package model.repositorios.repositoriosDBs.reposUsuarios;

import model.repositorios.repositoriosDBs.GenericRepositoryJPA;
import model.usuario.UserAgenteSectorial;
import model.usuario.UserOrganizacion;

import java.util.Optional;

public class RepositorioUsuarioAgenteSectorial extends GenericRepositoryJPA<UserAgenteSectorial> {


  private static RepositorioUsuarioAgenteSectorial instance = null;

  static public RepositorioUsuarioAgenteSectorial getInstance() {
    if (instance == null) {
      instance = new RepositorioUsuarioAgenteSectorial();
    }
    instance.settearEntitys();

    return instance;

  }



  public Optional<UserAgenteSectorial> findByUsername(String nom){
    return entityManager.createQuery("from UserAgenteSectorial where nombre =  ?", UserAgenteSectorial.class).setParameter(1,nom).getResultList().stream().findFirst();
  }

}
