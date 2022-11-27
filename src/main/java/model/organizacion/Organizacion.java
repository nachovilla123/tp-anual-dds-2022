package model.organizacion;

import model.agentesSectoriales.SectorTerritorial;
import model.implementacionCSV.DatoDeActividad;
import model.implementacionCSV.HuellaDeCarbono;
import model.implementacionCSV.Periodo;
import lombok.Getter;
import lombok.Setter;
import model.organizacion.trayecto.Ubicacion;
import model.reporte.Reporte;
import model.repositorios.repositoriosDBs.RepositorioDatosDeActividad;
import model.repositorios.repositoriosDBs.RepositorioReporte;
import model.usuario.Usuario;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Getter
@Setter

@Entity
@Table(name= "organizacion")
public class Organizacion {

  @Id
  @GeneratedValue
  @Column(name = "id_organizacion", nullable = false)
  private Long id;

  @Column(name = "razon_social")
  private String razonSocial;

  @Enumerated(EnumType.STRING)
  private Tipo tipoDeOrganizacion;

  @Enumerated(EnumType.STRING)
  private Clasificacion clasificacion;

  //@OneToOne(cascade = CascadeType.ALL)
  //@JoinColumn(name = "ubicacion")
  @Embedded()
  private Ubicacion ubicacionGeografica;

  @Transient //El sector territorial conoce a las organizaciones,esto no iria
  private SectorTerritorial sectorTerritorial; // este es por el agente sectorial entrega 3


  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "id_organizacion")
  private Set<Sector> sectores = new HashSet<Sector>();

  @Transient
  private Set<Contacto> contactos = new HashSet<Contacto>();

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "id_organizacion")
  private List<DatoDeActividad> datosDeActividades = new ArrayList<>();

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "id_organizacion")
  @OrderColumn
  private Set<Reporte> reportes = new HashSet<>();

  @Column
  String srcImg;


  public enum Clasificacion {
    MINISTERIO, UNIVERSIDAD, ESCUELA, EMPRESA_SECTOR_PRIMARIO, EMPRESA_SECTOR_SECUNDARIO
  }

  public enum Tipo {
    GUBERNAMENTAL, ONG, EMPRESA_INSTITUCION
  }


  public Organizacion(String razonSocial,
                      Tipo tipoDeOrg,
                      Ubicacion ubicacion,
                      Clasificacion empresaSectorSecundario
                      ) {
    this.razonSocial = razonSocial;
    this.tipoDeOrganizacion = tipoDeOrg;
    this.ubicacionGeografica = ubicacion;
    this.clasificacion = empresaSectorSecundario;

  }

  public Organizacion(String razonSocial,
                      Tipo tipoDeOrg,
                      Ubicacion ubicacion,
                      Clasificacion empresaSectorSecundario,
                      String srcImg
  ) {
    this.razonSocial = razonSocial;
    this.tipoDeOrganizacion = tipoDeOrg;
    this.ubicacionGeografica = ubicacion;
    this.clasificacion = empresaSectorSecundario;
    this.srcImg = srcImg;
  }


  public Organizacion() {
  }

  //---------- CODIGO ----------//

  public void agregarSector(Sector sector) {
    sectores.add(sector);
  }


  private List<DatoDeActividad> getDatosDeActividades(RepositorioDatosDeActividad repositorioDatosDeActividad) {
    return repositorioDatosDeActividad.getDatosDeActividadByOrganizacion(this.id);
  }


  // <-------------------------------- CONTACTOS ------------------------------------------->

  public void agregarContacto(Contacto unContacto) {
    validarExistenciaDeContacto(unContacto);
    contactos.add(unContacto);
  }


  public List<Miembro> obtenerMiembros() {
    return sectores.stream().flatMap(sector -> sector.miembros.stream()).collect(Collectors.toList());
  }

  public List<SolicitudDeVinculacion> obtenerMiembrosEnEspera() {
    return sectores.stream().flatMap(sector -> sector.trabajadoresEnEspera.stream()).collect(Collectors.toList());
  }
  public void validarExistenciaDeContacto(Contacto unContacto) {
    if (unContacto.yaExiste(contactos)) {
      throw new RuntimeException("El correo o wpp del contacto ya esta registrado");
    }
  }

  public boolean tieneContactos() {
    return contactos.size() != 0;
  }


  // <-------------------------------- HUELLA DE CARBONO ------------------------------------------->

  public Double calcularHc(Periodo unPeriodo,LocalDate unaFecha) {
    Double hsSectores = this.calculoHcSectores(unPeriodo);
    Double hcDatosDeActividad=this.calculodeHCActividadesGenerales(unPeriodo, unaFecha);
    return this.calculoHcSectores(unPeriodo) + this.calculodeHCActividadesGenerales(unPeriodo, unaFecha);
  }

  public Double calculoHcSectores(Periodo periodo) {

      if(periodo== Periodo.ANUAL){
        return this.calculoHcSinPeriodo()*365;
      }
       return this.calculoHcSinPeriodo()*30;
  }

  public Double calculoHcSinPeriodo(){
    return
        sectores.stream()
        .flatMap(sector -> sector.tramosMiembros())
        .collect(Collectors.toSet()).stream()
        .mapToDouble(tramo -> tramo.calculoHCxTramo()).sum();
  }

 /* public Double calculodeHCActividadesGenerales(Periodo unPeriodo) {
    return datosDeActividades.stream()
        .filter(datoDeActividad -> datoDeActividad.perteneceAPeriodo(unPeriodo))
        .collect(Collectors.toSet())
        .stream().mapToDouble(datoDeAct -> datoDeAct.calcularHC()).sum();
  }*/

  public Double calculodeHCActividadesGenerales(Periodo unPeriodo, LocalDate unaFecha) {
    //validarPeriodoYFecha(unPeriodo,unaFecha)
    return datosDeActividades.stream()
        .filter(datoDeActividad -> datoDeActividad.perteneceAPeriodo(unPeriodo))
        .filter(datoDeActividad -> datoDeActividad.sonDeLaMismaFecha(unaFecha))
        // compara fechas por mes o año dependiendo el periodo
        .collect(Collectors.toSet())
        .stream().mapToDouble(datoDeAct -> datoDeAct.calcularHC()).sum();
  }

  //---- reportes
  public List<Reporte> getReportes(){
    return RepositorioReporte.getInstance().getReportesByOrganizacion(this.getId());
  }
  public void agregarReporte(RepositorioReporte repositorioReportes,Periodo periodo){

    LocalDate fechaActual = LocalDate.now();

    //TODO los reportes son solo mensuales o tambien anuales??
    HuellaDeCarbono daGenerales = new HuellaDeCarbono(this.calculodeHCActividadesGenerales(periodo,fechaActual));
    HuellaDeCarbono daTransporte = new HuellaDeCarbono(this.calculoHcSectores(periodo));

    Reporte reporteAguardar = new Reporte(daGenerales,daTransporte);

    repositorioReportes.agregar(reporteAguardar);
    this.reportes.add(reporteAguardar);

  }

  public Reporte verUltimoReporte(){
    return  RepositorioReporte.getInstance().getReportesByOrganizacion(this.getId()).get(0);
  }




}


