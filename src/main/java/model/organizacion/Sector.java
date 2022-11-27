package model.organizacion;

import lombok.Getter;
import lombok.Setter;
import model.organizacion.trayecto.Tramo;
import model.organizacion.trayecto.Trayecto;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.*;

@Getter
@Setter

@Entity
@Table(name = "sector")
public class Sector {

  @Id@GeneratedValue
  @Column(name = "id_sector", nullable = false)
  private Long id;

  @Enumerated(EnumType.STRING)
  SectorDeTrabajo sectorDeTrabajo;

  //@OneToMany(cascade = CascadeType.ALL)
  @OneToMany(mappedBy = "trabajo",cascade = CascadeType.ALL)
  public Set<Miembro> miembros = new HashSet<Miembro>();

  @OneToMany(mappedBy = "sectorAvincular",cascade = CascadeType.ALL)
  public Set<SolicitudDeVinculacion> trabajadoresEnEspera = new HashSet<>();

  @OneToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "id_organizacion")
  public Organizacion organizacion;



  public Sector(Set<Miembro> miembros , SectorDeTrabajo sectorDeTrabajo) {
    this.miembros = miembros;
    this.sectorDeTrabajo = sectorDeTrabajo;

  }

  public Sector(SectorDeTrabajo sector){
    this.sectorDeTrabajo = sector;
  }

  public Sector() {

  }



  public enum SectorDeTrabajo {
    ADMINSTRACION,
    CONTADURIA,
    RRHH,
    FINANZAS,
    COMERCIAL,
    MARKETING,
    LOGÍSTICA,
    IT,
    DIRECTIVO

  }
  //------------- CODIGO -------------//


  public void aceptarVinculacionTodosLosMiembro() {
    this.trabajadoresEnEspera.forEach(solicitud -> solicitud.aceptar());
  }



  public void agregarAlistaDeEspera(SolicitudDeVinculacion solicitudes) {
    trabajadoresEnEspera.add(solicitudes);
  }

  public int cantidadDeMiembrosEnSector(){
    return miembros.toArray().length;
  }


  public Double calcularHC() {
    return this.tramosMiembros()
        .collect(Collectors.toSet())
        .stream()
        .mapToDouble(tramo -> tramo.calculoHCxTramo()).sum();
  }

  public Stream <Trayecto> trayectosMiembros() {
    return miembros.stream().map(miembro->miembro.getRegistroTrayectos()).flatMap(Collection::stream);
  }

  public Stream <Tramo> tramosMiembros() {
    return this.trayectosMiembros()
        .map(miembro->miembro.getTramosDelTrayecto())
        .flatMap(Collection::stream);
  }

  //requerimiento 2 de la entrega 4
  public Double indicadorDeHCPorMiembrosDeOrganizacion(){
    return this.calcularHC() / this.cantidadDeMiembrosEnSector();
  }


  //------------ PRIMERA ENTREGA NO ESPECIFICADO SU DESARROLLO ------------
  public void visualizarReportes() {
  }

  public void visualizarRecomendaciones() {
  }

  public void agregarTrabajador(Miembro miembro){
    miembros.add(miembro);
  }

}