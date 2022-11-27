package model.usuario;

import lombok.Getter;
import lombok.Setter;
import model.agentesSectoriales.AgenteSectorial;
import model.organizacion.Persona;
import model.repositorios.repositoriosDBs.reposUsuarios.RepositorioUsuario;
import model.repositorios.repositoriosDBs.reposUsuarios.RepositorioUsuarioAgenteSectorial;

import javax.persistence.*;

@Getter
@Setter

@Entity
@Table(name = "user_agente_sectorial")
public class UserAgenteSectorial extends  Usuario{

  @OneToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "id_agente_sectorial")
  AgenteSectorial agenteSectorial;

  public UserAgenteSectorial(){}

  public UserAgenteSectorial(String nombre, String password, Rol rol) {
    this.nombre = nombre;
    this.password = password;
    this.rol = rol;
  }

  public void settearAgenteSectorial(AgenteSectorial agenteSectorial){
    this.agenteSectorial = agenteSectorial;
    RepositorioUsuarioAgenteSectorial.getInstance().actualizar(this);
  }

  public Long getIdElementoAsociado(){
    return agenteSectorial.getId();
  }


}
