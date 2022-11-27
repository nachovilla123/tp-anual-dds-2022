package model.usuario;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter

@Entity
@Table(name = "user_administrador")
public class UserAdministrador extends  Usuario{


  public UserAdministrador(){}

  public UserAdministrador(String nombre, String password, Rol rol) {
    this.nombre = nombre;
    this.password = password;
    this.rol = rol;
  }

  public Long getIdElementoAsociado(){
    return new Long(-1);
  }

}
