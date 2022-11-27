package model.organizacion;

import model.implementacionCSV.Periodo;
import lombok.Getter;
import lombok.Setter;
import model.organizacion.trayecto.Tramo;
import model.organizacion.trayecto.Trayecto;
import model.usuario.Usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.*;

@Getter
@Setter

@Entity
@Table(name= "miembro")
public class Miembro {

  @Id@GeneratedValue
  @Column(name = "id_miembro", nullable = false)
  private Long id_miembro;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "id_persona")
  private Persona persona;

  @OneToMany(cascade = CascadeType.ALL)
  @OrderColumn
  private Set<Trayecto> registroTrayectos = new HashSet<>();

  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "id_sector")
  public Sector trabajo;

  @Column
  String srcImg;

  public Miembro(Persona persona,
                 Set<Trayecto> trayectos) {
    this.persona = persona;
    this.registroTrayectos= trayectos;
  }

  public Miembro(Persona persona) {
    this.persona = persona;
  }

  public Miembro() {
  }


  //--------------- ENTREGA 1 ---------------//

  private void visualizarReporte() { // TODO lo dieron en entrega 1 , en algun momento va a llegar implementarlo
  }


  public void registrarTrayecto(Trayecto unTrayecto){
   // yaExiste(unTrayecto);
   // unTrayecto.agregarMiembroATramosCompartidos(this); // esto??
    registroTrayectos.add(unTrayecto);
  }

  private void yaExiste(Trayecto unTrayecto) {
    if(registroTrayectos.contains(unTrayecto)){
      throw new RuntimeException("El tramo ya fue cargado anteriormente");
      //throw new YaEstaCargadoElTrayecto("El tramo ya fue cargado anteriormente");
    }
  }

  /*
   public List<Organizacion> getOrganizaciones2() {
    return new ArrayList<>(this.getTrabajos().stream().map(Sector::getOrganizacion).collect(Collectors.toList()));
  }*/


  public Organizacion getOrganizacion(){
    return  trabajo.getOrganizacion();
  }
  //---------------------  CALCULO DE HC ---------------------//

  // requerimiento 1 de entrega 4
  public Double impactoDeHCSobreOrganizacion(Organizacion organizacion, Periodo periodo, LocalDate unaFecha){
    return this.ejecutarCalculadoraHc() / organizacion.calcularHc(periodo,unaFecha);
  }


  public Double ejecutarCalculadoraHc() {
    return this.registroTrayectos.stream().mapToDouble(trayecto -> trayecto.calcularHc()).sum();

  }

}