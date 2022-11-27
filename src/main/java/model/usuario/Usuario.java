package model.usuario;

import lombok.Getter;
import lombok.Setter;
import model.Persistencia.PersistentEntity;

import javax.persistence.*;

@Setter
@Getter

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Usuario extends PersistentEntity {

  @Column
  String nombre;
  @Column
  String password;
  @Enumerated(EnumType.STRING)
  Rol rol;

  public enum Rol {
    ADMINISTRADOR ,
    MIEMBRO ,
    ORGANIZACION ,
    AGENTE_SECTORIAL;


  }

  public void resetPassword(String password) {
    this.password = password;
  }

    @Override
    public String toString() {
      return "Usuario [usuario=" + this.nombre + "]";
    }

  public Rol getRol() {
    return rol;
  }


  public abstract Long getIdElementoAsociado();


}
