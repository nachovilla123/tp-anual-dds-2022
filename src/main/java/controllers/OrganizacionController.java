package controllers;

//import javafx.util.converter.LocalDateStringConverter;
import model.implementacionCSV.Periodo;
import model.organizacion.Miembro;
import model.organizacion.Organizacion;
import model.organizacion.Sector;
import model.organizacion.SolicitudDeVinculacion;
import model.reporte.Reporte;
import model.repositorios.repositoriosDBs.RepositorioDeOrganizaciones;
import model.repositorios.repositoriosDBs.RepositorioReporte;
import model.repositorios.repositoriosDBs.RepositorioSector;
import model.repositorios.repositoriosDBs.RepositorioSolicitudDeVinculacion;
import model.repositorios.repositoriosDBs.reposUsuarios.RepositorioUsuarioOrganizacion;
import model.usuario.UserOrganizacion;
import model.usuario.Usuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

public class OrganizacionController implements WithGlobalEntityManager, TransactionalOps {

  //-----------------------------MENU-----------------------------------------//
  public ModelAndView menu(Request request, Response response) {
    return new ModelAndView(null, "vistas_organizacion/orgMenu.hbs");
  }
  //------------------------------------- MIEMBROS-----------------------------------------//
  public ModelAndView misMiembros(Request request, Response response) {

    Usuario usuarioEncontrado = buscarUsuarioEnDB(request);

    //System.out.print("Esta es la org encontrada: "+ usuarioEncontrado.getOrgAsociada().getRazonSocial());

    //List<Miembro> miembros = usuarioEncontrado.getOrgAsociada().getMiembros();

    //TODO ESTO ESTA HARCODEADO PORQUE EL USUARIO TODAVIA NO ESTA DEFINIDO Y NO TIENE ORG ASOCIADA, VOY A PONER LA Q TIENE ID 1
    //List<Miembro> miembros = RepositorioDeOrganizaciones.getInstance().encontrar(new Long(1)).obtenerMiembros();
    List<Miembro> miembros = RepositorioDeOrganizaciones.getInstance().encontrar(getIdElementoAsociado(request)).obtenerMiembros();

    System.out.print(miembros.toString());

    Map<String, Object> model = new HashMap<>();
    model.put("miembros", miembros);



    return new ModelAndView(model, "vistas_organizacion/orgMisMiembros.hbs");
  }

  private UserOrganizacion buscarUsuarioEnDB(Request request) {
    String usuario= getNombreUsuario(request, "nombre");

    return RepositorioUsuarioOrganizacion.getInstance().findByUsername(usuario).orElse(null);
  }

  private String getNombreUsuario(Request request, String nombre) {
    return request.session().attribute(nombre);
  }

  //------------------------------------- FACTOR DE EMISION-----------------------------------------//
  public ModelAndView registrarFactoresEmision(Request request, Response response) {
    return new ModelAndView(null, "vistas_organizacion/orgRegistratFactoresEmision.hbs");
  }
  //------------------------------------- MEDICIONES-----------------------------------------//
  public ModelAndView registrarMediciones(Request request, Response response) {
    return new ModelAndView(null, "vistas_organizacion/orgRegistrarMediciones.hbs");
  }

  //------------------------------------- VINCULACIONES-----------------------------------------//
  public ModelAndView solicitudVinculacion(Request request, Response response) {

    UserOrganizacion usuarioEncontrado = buscarUsuarioEnDB(request);

    System.out.print("Esta es la org encontrada: "+ usuarioEncontrado.getOrganizacion_asociada().getRazonSocial());

    List<SolicitudDeVinculacion> solicitudes = buscarSolicitudesPendientesDeVinculacion(usuarioEncontrado);

    System.out.print(solicitudes.toString());

    Map<String, Object> model = new HashMap<>();
    model.put("solicitudes", solicitudes);

    return new ModelAndView(model, "vistas_organizacion/orgSolicitudesDeVinculacion.hbs");
  }

  private List<SolicitudDeVinculacion> buscarSolicitudesPendientesDeVinculacion(UserOrganizacion usuarioEncontrado) {
    return usuarioEncontrado.getOrganizacion_asociada().obtenerMiembrosEnEspera().stream().filter(solicitud -> solicitud.estado == SolicitudDeVinculacion.EstadoSolicitud.PENDIENTE).collect(Collectors.toList());
  }

  public ModelAndView post(Request request, Response response) {

    return null;
  }

  public ModelAndView aceptarVinculacion(Request request, Response response) {

    Long id_solicitud=  new Long(request.params("id_solicitud"));
    System.out.println("id de la solicitud :"+ id_solicitud);

    SolicitudDeVinculacion solicitudElegida = RepositorioSolicitudDeVinculacion.getInstance().encontrar(id_solicitud);

    solicitudElegida.aceptar();

    withTransaction(()->{

      RepositorioSolicitudDeVinculacion.getInstance().actualizar(solicitudElegida);
      RepositorioSector.getInstance().actualizar(solicitudElegida.sectorAvincular);
    });

    //RepositorioSector.getInstance().actualizarBoostrap(sectorElegido);

    response.redirect("/org/menu/solicitudesdevinculacion");

    return null;
  }

  public ModelAndView rechazarVinculacion(Request request, Response response) {

    Long id_solicitud=  new Long(request.params("id_solicitud"));
    System.out.println("id de la solicitud :"+ id_solicitud);

    SolicitudDeVinculacion solicitudElegida = RepositorioSolicitudDeVinculacion.getInstance().encontrar(id_solicitud);

    solicitudElegida.rechazar();


    withTransaction(()->{
      RepositorioSolicitudDeVinculacion.getInstance().actualizar(solicitudElegida);
    });

    //RepositorioSector.getInstance().actualizarBoostrap(sectorElegido);

    response.redirect("/org/menu/solicitudesdevinculacion");

    return null;
  }


  //----------------------------------------- REPORTES-----------------------------------------//

  public ModelAndView menuReportes(Request request, Response response) {
    return new ModelAndView(null, "vistas_organizacion/orgMenuReportes.hbs");
  }

  public ModelAndView composicionHCOrg(Request request, Response response) {

    Long id_org = getIdElementoAsociado(request);

    //Busco el ultimo reporte de la organizacion
    Reporte reporte = ultimoReporte(id_org);
    System.out.println("FECHA DE REPORTE:"+reporte.getFecha().toString());

    Double valorHcTramos = reporte.getTramos().valorEnEnKilogramos();
    Double valorHcDA = reporte.getActividadesGenerales().valorEnEnKilogramos();
    LocalDate fecha = reporte.getFecha();

    Map<String, Object> model = new HashMap<>();
    model.put("valorDeTramos", valorHcTramos);
    model.put("valorDA",valorHcDA);
    model.put("fechaReporte",fecha);

    return new ModelAndView(model, "vistas_unknown/reportes/composiciones/composicion_hc_org.hbs");
  }

  private Reporte ultimoReporte(Long id_org) {
    List<Reporte> reportes = RepositorioReporte.getInstance().getReportesByOrganizacion(id_org);

    System.out.println(reportes.toString());

    return reportes.get(0);
  }

  public ModelAndView evolHCOrg(Request request, Response response) {

    Long id_org = new Long(getIdElementoAsociado(request));

    List<Reporte> reportes = RepositorioReporte.getInstance().getReportesByOrganizacion(id_org);

    Map<String, Object> model = new HashMap<>();
    model.put("reportes", reportes);

    return new ModelAndView(model, "vistas_unknown/reportes/evolucion/evol_hc_org.hbs");
  }

  public ModelAndView hcTotalTipoOrg(Request request, Response response) {
    Long id_org = getIdElementoAsociado(request);

    Reporte reporte = ultimoReporte(id_org);
    System.out.println("FECHA DE REPORTE:"+reporte.getFecha().toString());

    Map<String, Object> model = new HashMap<>();
    model.put("valorHc", reporte.getValorTotalHc());
    //TODO FIJARSE SI ES ES EL VALOR O OTRO
    return new ModelAndView(model, "vistas_unknown/reportes/hcTotalTipoORG.hbs");
  }


  //------------------------------------- CALCULO HC-----------------------------------------//

  public ModelAndView calculoHC(Request request, Response response) {
    return new ModelAndView(null, "vistas_organizacion/orgCalculoHC.hbs");
  }
  public ModelAndView valorcalculoHC(Request request, Response response) {

    String fecha= request.queryParams("fecha");
    String periodo = request.queryParams("periodo");

    System.out.println("Este es el periodo encontrado: "+ periodo);
    System.out.println("Esta es la fecha encontrada: "+ fecha);

    Periodo periodoElegido = Periodo.valueOf(periodo);

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    //convert String to LocalDate
    LocalDate fechaConvertida = LocalDate.parse(fecha, formatter);



    Long id_org = new Long(request.session().attribute("idElementoAsociado"));

    Usuario usuarioEncontrado = buscarUsuarioEnDB(request);

    System.out.println("Esta es el usuario encontrado: "+ usuarioEncontrado.getNombre());

    Organizacion organizacion = RepositorioDeOrganizaciones.getInstance().encontrar(id_org);


    Double resultadoHc= organizacion.calcularHc(periodoElegido,fechaConvertida);

     Map<String, Object> model = new HashMap<>();
    model.put("resultado",resultadoHc.toString());
    model.put("razonSocial", organizacion.getRazonSocial());

    return new ModelAndView(model, "vistas_organizacion/resultadoCalculoHC.hbs");
  }


  //-------metodos para no repetir logica

  private Long getIdElementoAsociado(Request request){
    return new Long(request.session().attribute("idElementoAsociado"));
  }



}

