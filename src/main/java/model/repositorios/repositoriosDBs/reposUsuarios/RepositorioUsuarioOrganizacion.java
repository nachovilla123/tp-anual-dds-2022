package model.repositorios.repositoriosDBs.reposUsuarios;

import model.repositorios.repositoriosDBs.GenericRepositoryJPA;
import model.usuario.UserOrganizacion;
import model.usuario.Usuario;

import java.util.Optional;

public class RepositorioUsuarioOrganizacion extends GenericRepositoryJPA<UserOrganizacion> {

  private static RepositorioUsuarioOrganizacion instance = null;

  static public RepositorioUsuarioOrganizacion getInstance() {
    if (instance == null) {
      instance = new RepositorioUsuarioOrganizacion();
    }
    instance.settearEntitys();

    return instance;

  }



  public Optional<UserOrganizacion> findByUsername(String nom){
    return entityManager.createQuery("from UserOrganizacion where nombre =  ?", UserOrganizacion.class).setParameter(1,nom).getResultList().stream().findFirst();
  }

}
