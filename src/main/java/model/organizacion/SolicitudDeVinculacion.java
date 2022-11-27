package model.organizacion;

import lombok.Getter;
import lombok.Setter;
import model.Persistencia.PersistentEntity;

import javax.persistence.*;
@Getter
@Setter

@Entity
@Table(name = "solicitud_vinculacion")
public class SolicitudDeVinculacion extends PersistentEntity {

  @OneToOne(cascade = CascadeType.MERGE)
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
    sectorAvincular.agregarTrabajador(nuevoMiembro);

  }


  public void rechazar() {
    this.estado=EstadoSolicitud.RECHAZADA;
  }

}
