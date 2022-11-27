package model.agentesSectoriales;

import model.implementacionCSV.Periodo;
import lombok.Getter;
import lombok.Setter;
import model.organizacion.Organizacion;
import model.reporte.Reporte;
import model.repositorios.repositoriosDBs.RepositorioDeOrganizaciones;
import model.repositorios.repositoriosDBs.RepositorioReporte;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter

@Entity
@Table(name = "sector_Territorial ")
public class SectorTerritorial  {

  @Id@GeneratedValue
  @Column(name = "id_sectorTerritorial", nullable = false)
  private Long id;

  @Column(name = "nombre_sector")
  public String sectorTerritorial;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "id_sectorTerritorial")
  private Set<Organizacion> organizaciones = new HashSet<Organizacion>();

  public SectorTerritorial(String sectorTerritorial) {
    this.sectorTerritorial = sectorTerritorial;
  }

  public SectorTerritorial() {}

  public List<Organizacion> obtenerOrganizaciones(){
    return RepositorioDeOrganizaciones.getInstance().getOrganizacionesBySectorTerritorial(this.getId());
  }

  public void agregarOrganizacion(Organizacion org){
    organizaciones.add(org);
  }
  //Requerimiento de la entrega 4

  public Double calculoHc(Periodo unPeriodo, LocalDate unaFecha){
    return RepositorioDeOrganizaciones.getInstance().buscarTodos().stream().mapToDouble(organizacion->organizacion.calcularHc(unPeriodo,unaFecha)).sum();
  }

  public List<Reporte> obtenerReportesDeUnSectorT(){
    return this.obtenerOrganizaciones().stream().flatMap(organizacion -> organizacion.getReportes().stream()).collect(Collectors.toList());
  }
}
