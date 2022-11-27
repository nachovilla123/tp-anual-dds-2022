package model.usuario;

import lombok.Getter;
import lombok.Setter;
import model.organizacion.Persona;
import model.repositorios.repositoriosDBs.reposUsuarios.RepositorioUsuario;
import model.repositorios.repositoriosDBs.reposUsuarios.RepositorioUsuarioMiembro;

import javax.persistence.*;

@Getter
@Setter

@Entity
@Table(name = "user_miembro")
public class UserMiembro extends  Usuario{

  @OneToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "id_persona")
  Persona persona_asociada;


  public UserMiembro(){}

  public UserMiembro(String nombre, String password, Rol rol) {
    this.nombre = nombre;
    this.password = password;
    this.rol = rol;
  }

  public void settearPersona(Persona persona){
    this.persona_asociada = persona;
    RepositorioUsuarioMiembro.getInstance().actualizar(this);
  }

  public Long getIdElementoAsociado(){
    return persona_asociada.getId();
  }

}
