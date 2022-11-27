package model.repositorios.repositoriosDBs;

import model.agentesSectoriales.AgenteSectorial;
import model.usuario.Usuario;

import java.util.Optional;

public class RepositorioAgenteSectorial extends GenericRepositoryJPA<AgenteSectorial> {

  private static RepositorioAgenteSectorial instance = null;

  static public RepositorioAgenteSectorial getInstance() {
    if (instance == null) {
      instance = new RepositorioAgenteSectorial();
    }
    instance.settearEntitys();
    return instance;
  }

  public Optional<AgenteSectorial> findByUsername(String nom){
    return entityManager.createQuery("from AgenteSectorial where nombre =  ?", AgenteSectorial.class).setParameter(1,nom).getResultList().stream().findFirst();
  }

}
