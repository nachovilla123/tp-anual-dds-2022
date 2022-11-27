package model.organizacion;

import model.Persistencia.PersistentEntity;
import lombok.Getter;
import lombok.Setter;
import model.organizacion.trayecto.Tramo;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter

@Entity
@Table(name= "persona")
public class Persona extends PersistentEntity {

  @Column
  private String nombre;

  @Column
  private String apellido;

  @Column
  private Integer numeroDocumento;

  @Enumerated(EnumType.STRING)
  private Persona.TipoDocumento tipoDocumento;


  @OneToMany (mappedBy = "persona",cascade = CascadeType.MERGE)
  Set<Miembro> trabajos = new HashSet<>();

  // estamos haciendo esto porque nos dimos cuenta que el usuario crea sus tramos y despues los mete a un  trayecto
  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "id_Persona")
  @OrderColumn
  public Set<Tramo> tramosNoRegistrados = new HashSet<>();


  public enum TipoDocumento {
    DNI,LIBRETA,PASAPORTE
  }

  public Persona() {
  }

  public Persona(String nombre, String apellido, Integer numeroDocumento, Persona.TipoDocumento tipoDocumento) {
    this.nombre = nombre;
    this.apellido = apellido;
    this.numeroDocumento = numeroDocumento;
    this.tipoDocumento = tipoDocumento;
  }

  public void generarSolicitudDeVinculacion(Sector sector){
    SolicitudDeVinculacion solicitud = new SolicitudDeVinculacion(sector,this);
    sector.agregarAlistaDeEspera(solicitud);
  }




//-------------------------------------------COSAS QUE VAN SURGIENDO POR LA ENTREGA --------------------------------//
public void agregarTramoNoRegistrado(Tramo tramoNoRegistradoRecienCreado){
  tramosNoRegistrados.add(tramoNoRegistradoRecienCreado);
}

}