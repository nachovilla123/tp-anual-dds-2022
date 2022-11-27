package model.usuario;

import lombok.Getter;
import lombok.Setter;
import model.organizacion.Organizacion;
import model.organizacion.Persona;
import model.repositorios.repositoriosDBs.RepositorioDeOrganizaciones;
import model.repositorios.repositoriosDBs.reposUsuarios.RepositorioUsuarioMiembro;
import model.repositorios.repositoriosDBs.reposUsuarios.RepositorioUsuarioOrganizacion;

import javax.persistence.*;

@Getter
@Setter

@Entity
@Table(name = "user_organizacion")
public class UserOrganizacion extends  Usuario{

  @OneToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "id_organizacion")
  Organizacion organizacion_asociada;


  public UserOrganizacion(){}

  public UserOrganizacion(String nombre, String password, Rol rol) {
    this.nombre = nombre;
    this.password = password;
    this.rol = rol;
  }

  public void settearOrganizacion(Organizacion org) {
    this.organizacion_asociada = org;
    RepositorioUsuarioOrganizacion.getInstance().actualizar(this);
  }

  public Long getIdElementoAsociado(){
    return organizacion_asociada.getId();
  }
}
