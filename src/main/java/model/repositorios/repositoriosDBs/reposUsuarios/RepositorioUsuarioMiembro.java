package model.repositorios.repositoriosDBs.reposUsuarios;

import model.repositorios.repositoriosDBs.GenericRepositoryJPA;
import model.usuario.UserMiembro;
import model.usuario.Usuario;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Optional;

public class RepositorioUsuarioMiembro extends GenericRepositoryJPA<UserMiembro> {

  private static RepositorioUsuarioMiembro instance = null;

  static public RepositorioUsuarioMiembro getInstance() {
    if (instance == null) {
      instance = new RepositorioUsuarioMiembro();
    }
    instance.settearEntitys();

    return instance;

  }

/*
  private static RepositorioUsuario get() {
    RepositorioUsuario repoUsuario = new RepositorioUsuario();
    EntityManager em = PerThreadEntityManagers.getEntityManager();
    EntityTransaction tx = em.getTransaction();
    repoUsuario.setEntityManager( em);
    repoUsuario.setEntityTransaction(tx);
    return repoUsuario;
  }
*/

  public Optional<UserMiembro> findByUsername(String nom){
    return entityManager.createQuery("from UserMiembro where nombre =  ?", UserMiembro.class).setParameter(1,nom).getResultList().stream().findFirst();
  }


}
