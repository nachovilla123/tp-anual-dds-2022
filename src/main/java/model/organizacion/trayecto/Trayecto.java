package model.organizacion.trayecto;

import lombok.Getter;
import lombok.Setter;
import model.organizacion.Miembro;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Getter
@Setter

@Entity
@Table(name = "trayecto")
public class Trayecto{ // un trayecto es una lista de tramos.

  @Id@GeneratedValue
  @Column(name = "id_trayecto", nullable = false)
  private Long id;

  //@OneToOne(cascade = CascadeType.ALL)
  //@JoinColumn(name = "id_ubicacionInicial")
  @AttributeOverrides({
      @AttributeOverride(name="localidad",column=@Column(name="localidad_inicial")),
      @AttributeOverride(name = "calle", column=@Column(name = "calle_inicial")),
      @AttributeOverride(name="altura", column=@Column (name = "altura_inicial"))
  })
  @Embedded
  private Ubicacion puntoInicial;

  //@OneToOne(cascade = CascadeType.ALL)
  //@JoinColumn(name = "id_ubicacionFinal")
  @AttributeOverrides({
      @AttributeOverride(name="localidad",column=@Column(name="localidad_final")),
      @AttributeOverride(name = "calle", column=@Column(name = "calle_final")),
      @AttributeOverride(name="altura", column=@Column (name = "altura_final"))
  })
  @Embedded
  private Ubicacion puntoFinal;

  //@OneToMany(cascade = CascadeType.ALL)
  @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
  @OrderColumn
  private List<Tramo> tramosDelTrayecto = new ArrayList<Tramo>();


  public Trayecto(Ubicacion pi, Ubicacion pf, List<Tramo> tramos) {
    this.puntoInicial = pi;
    this.puntoFinal = pf;
    this.tramosDelTrayecto = tramos;
  }

  public Trayecto() {
  }


  public void agregarTramo(Tramo nuevoTramo) {
    //nuevoTramo.validarTramo(); //todo reparar
    tramosDelTrayecto.add(nuevoTramo);
  }
  public List<Tramo> getTramos(){return tramosDelTrayecto;}


  //todo revisar
  private boolean esCompartido() {
    return getTramosDelTrayecto().stream().anyMatch(tramo -> tramo.medioDeTransporte.esCompartible());
  }

  public void agregarMiembroATramosCompartidos(Miembro miebro) { //todo esto me hace ruido
    tramosDelTrayecto.stream().filter(tramo -> tramo.puedeSerCompartido()).forEach(tramo -> tramo.agregarMiembro(miebro));
  }


  public Double calcularDistanciaEnKm() {
    return tramosDelTrayecto.stream().mapToDouble(tramo -> tramo.distancia().getValor()).sum();
  }

  //----------------- CALCULO DE HUELLA DE CARBONO -----------------//
  public double calcularHc() {
    return tramosDelTrayecto.stream().mapToDouble(tramo -> tramo.calculoHCxTramo()).sum();
  }
}







