package model.organizacion;

import lombok.Getter;
import lombok.Setter;
import model.Persistencia.PersistentEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

@Entity
@Table(name = "solicitud_vinculacion")
public class SolicitudDeVinculacion extends PersistentEntity {

  @OneToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "id_sector")
  public Sector sectorAvincular;

  @OneToOne(cascade = CascadeType.MERGE)
  public Persona solicitante;

  @Enumerated(EnumType.STRING)
  public EstadoSolicitud estado;

  public SolicitudDeVinculacion(Sector sector, Persona persona) {
    this.sectorAvincular = sector;
    this.solicitante = persona;
    this.estado = EstadoSolicitud.PENDIENTE;
  }

  public SolicitudDeVinculacion() {
  }

  public enum EstadoSolicitud {
    ACEPTADA, RECHAZADA, PENDIENTE
  }


  public void aceptar() {
    this.estado = EstadoSolicitud.ACEPTADA;
    Miembro nuevoMiembro = new Miembro(solicitante);
    nuevoMiembro.setTrabajo(sectorAvincular);
    String imagenPerfil = this.generarFotoPerfilMiembro();
    nuevoMiembro.setSrcImg(imagenPerfil); // linea algoritmo villa
    sectorAvincular.agregarTrabajador(nuevoMiembro);
  }

  public String generarFotoPerfilMiembro(){
    int pickeadorImagenAleatorio = (int) (Math.random() * 9 + 0);
    List<String> imagenesParaUsuario = new ArrayList<>();
    imagenesParaUsuario.add("../../imagenes/personas/persona1.png");
    imagenesParaUsuario.add( "../../imagenes/personas/persona2.png");
    imagenesParaUsuario.add("../../imagenes/personas/persona3.png");
    imagenesParaUsuario.add("../../imagenes/personas/persona4.png");
    imagenesParaUsuario.add("../../imagenes/personas/persona5.png");
    imagenesParaUsuario.add("../../imagenes/personas/persona6.png");
    imagenesParaUsuario.add("../../imagenes/personas/persona7.png");
    imagenesParaUsuario.add("../../imagenes/personas/persona8.png");
    imagenesParaUsuario.add("../../imagenes/personas/persona9.png");
    imagenesParaUsuario.add("../../imagenes/personas/persona10.png");
    imagenesParaUsuario.add("../../imagenes/personas/persona11.png");

    return imagenesParaUsuario.get(pickeadorImagenAleatorio);
  }


  public void rechazar() {
    this.estado=EstadoSolicitud.RECHAZADA;
  }

}
