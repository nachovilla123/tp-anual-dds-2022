package model.repositorios.repositoriosDBs.reposUsuarios;

import model.repositorios.repositoriosDBs.GenericRepositoryJPA;
import model.usuario.Usuario;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Optional;

public class RepositorioUsuario extends GenericRepositoryJPA<Usuario> {

  private static RepositorioUsuario instance = null;

  static public RepositorioUsuario getInstance() {
    if (instance == null) {
      instance = new RepositorioUsuario();
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


  public Optional<Usuario> findByUsername(String nom){
    return entityManager.createQuery("from Usuario where nombre =  ?", Usuario.class).setParameter(1,nom).getResultList().stream().findFirst();
  }

}
