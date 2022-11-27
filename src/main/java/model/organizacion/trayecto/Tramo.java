package model.organizacion.trayecto;

import model.exceptions.NoEsUnTrayectoCompartido;
import model.implementacionGeoDds.Distancia;
import lombok.Getter;
import lombok.Setter;
import model.mediodetransporte.MedioDeTransporte;
import model.organizacion.Miembro;

import javax.persistence.*;

@Getter
@Setter

@Entity
@Table(name = "tramo")
public class Tramo  {

  @Id@GeneratedValue
  @Column(name = "id_tramo", nullable = false)
  private Long id;

  @Column
  public String nombre;

  @Column(name = "cantidad_miembros")
  public Integer cantidadMiembros;

  //@OneToOne(cascade = CascadeType.ALL)
  //@JoinColumn(name = "id_ubicacionInicial")
  @AttributeOverrides({
      @AttributeOverride(name="localidad",column=@Column(name="localidad_origen")),
      @AttributeOverride(name = "calle", column=@Column(name = "calle_origen")),
      @AttributeOverride(name="altura", column=@Column (name = "altura_origen"))
  })
  @Embedded()
  private Ubicacion origen;

  //@OneToOne(cascade = CascadeType.ALL)
  //@JoinColumn(name = "id_ubicacionFinal")
  @AttributeOverrides({
      @AttributeOverride(name="localidad",column=@Column(name="localidad_destino")),
      @AttributeOverride(name = "calle", column=@Column(name = "calle_destino")),
      @AttributeOverride(name="altura", column=@Column (name = "altura_destino"))
  })
  @Embedded
  private Ubicacion destino;

  //@OneToOne(cascade = CascadeType.ALL)
  //@JoinColumn(name = "id_medioDeTransporte") //todo
  @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
  @JoinColumn(name = "id_medioDeTransporte")
  public MedioDeTransporte medioDeTransporte;


  public Tramo(MedioDeTransporte medioDeTransporte, Ubicacion origen, Ubicacion destino) {
    this.medioDeTransporte = medioDeTransporte;
    this.origen = origen;
    this.destino = destino;
  }
  public Tramo(String nombre, MedioDeTransporte medioDeTransporte, Ubicacion origen, Ubicacion destino) {
    this.nombre = nombre;
    this.medioDeTransporte = medioDeTransporte;
    this.origen = origen;
    this.destino = destino;
  }

  public Tramo() {}

  public void agregarMiembro(Miembro miembro) {
    cantidadMiembros++;
  }

  public void validarTramo() {
    if (this.cantidadMiembros > 1 && !puedeSerCompartido()) {
      throw new NoEsUnTrayectoCompartido("El tramo no es compartido");
    }
  }

  public Boolean puedeSerCompartido() {
    return medioDeTransporte.esCompartible();
  }


  public Double calculoHCxTramo(){
    return this.medioDeTransporte.hcPorDistancia(this.origen,this.destino);
  }


  public Distancia distancia() {
    return this.medioDeTransporte.distancia(this.origen,this.destino);
  }
}