package controllers;

//import javafx.util.converter.LocalDateStringConverter;
//import com.sun.org.apache.regexp.internal.RE;
import model.implementacionCSV.DatoDeActividad;
import model.implementacionCSV.FactorDeEmision;
import model.implementacionCSV.Periodo;
import model.implementacionCSV.tipoDeConsumo.TipoDeConsumo;
import model.organizacion.Miembro;
import model.organizacion.Organizacion;
import model.organizacion.Sector;
import model.organizacion.SolicitudDeVinculacion;
import model.reporte.Reporte;
import model.repositorios.repositorioCSV.RepositorioDatosDeActividadCSV;
import model.repositorios.repositoriosDBs.*;
import model.repositorios.repositoriosDBs.reposUsuarios.RepositorioUsuarioOrganizacion;
import model.usuario.UserOrganizacion;
import model.usuario.Usuario;
import org.apache.commons.io.FileUtils;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

//import javax.jws.WebParam;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;

public class OrganizacionController implements WithGlobalEntityManager, TransactionalOps {

  //-----------------------------MENU-----------------------------------------//
  public ModelAndView menu(Request request, Response response) {
    return new ModelAndView(null, "vistas_organizacion/orgMenu.hbs");
  }

  public ModelAndView home(Request request, Response response) {
    return new ModelAndView(null, "vistas_organizacion/orgHome.hbs");
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

  public ModelAndView registrarMedicionesCsv(Request request, Response respone){

    request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
    try (InputStream is = request.raw().getPart("archivoCSV").getInputStream()) {

      UserOrganizacion usuarioEncontrado = buscarUsuarioEnDB(request);
      Organizacion orgAsociada = usuarioEncontrado.getOrganizacion_asociada();

      System.out.print("Esta es la org encontrada: "+ orgAsociada.getRazonSocial());

      RepositorioDatosDeActividadCSV repoDA = new RepositorioDatosDeActividadCSV(orgAsociada.getRazonSocial());

      File archivito = new File(repoDA.crearPath(orgAsociada.getRazonSocial()));

      // Use the input stream to create a file
      FileUtils.copyInputStreamToFile(is,archivito);

      List<DatoDeActividad> datosDeActividadOrg = repoDA.obtenerDatosActividades();
      System.out.println("ANTES de agregar" + orgAsociada.getDatosDeActividades().size());

      orgAsociada.setDatosDeActividades(datosDeActividadOrg);

      archivito.delete();

      withTransaction(()->{
            RepositorioDeOrganizaciones.getInstance().actualizar(orgAsociada);
      });

      System.out.println("DESPUES de actualizar" + orgAsociada.getDatosDeActividades().size());

    } catch (ServletException e) {
      //ACA EN VEZ DE TIRAR UNA EXECPCION DEBERIA REDIRECCIONAR A UNA PAGINA QUE DIGA QUE NO SE PUDO REALIZAR LA CARGA
      throw new RuntimeException(e);
    } catch (IOException e) {
      //ACA EN VEZ DE TIRAR UNA EXECPCION DEBERIA REDIRECCIONAR A UNA PAGINA QUE DIGA QUE NO SE PUDO REALIZAR LA CARGA
      throw new RuntimeException(e);
    }

    respone.redirect("/org/home");
    //esto deberia obtener el archivo para intentar leerlo con el repo csv de da y
    // luego intentar persistirlo para luego redericcioanr o no
    // a una pantalla que diga que se realizo la solicitud

    return null;
  }

  public ModelAndView registrarMedicionesFormulario(Request request, Response response) {
    //esto deberia leer los paramos, luego intentar persistirlos generando un da para luego redericcioanr o no
    // a una pantalla que diga que se realizo la solicitud


    TipoDeConsumo tipoCargado =  TipoDeConsumo.valueOf(request.queryParams("tipo_consumo"));
    System.out.println(request.queryParams("tipo_consumo"));

    Periodo periodo =  Periodo.valueOf(request.queryParams("periodo"));
    System.out.println("el periodo elegido es :"+ periodo);

    Double valorConsumo =  new Double( request.queryParams("valor_consumo"));
    System.out.println("el valor de consumo elegido es :"+ valorConsumo);

    String periodoImputacion = request.queryParams("periodo_imputacion");
    System.out.println("el periodo de imputacion elegido  es :"+ periodoImputacion);


    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    //convert String to LocalDate
    LocalDate fechaConvertida = LocalDate.parse(periodoImputacion, formatter);

    FactorDeEmision ultimoFactorAsociado = RepositorioFactorDeEmision.getInstance().obtenerPorTipoDeConsumo(tipoCargado);

    DatoDeActividad dato = new DatoDeActividad(ultimoFactorAsociado, valorConsumo, periodo, fechaConvertida);


    UserOrganizacion usuarioEncontrado = buscarUsuarioEnDB(request);
    Organizacion orgAsociada = usuarioEncontrado.getOrganizacion_asociada();
    orgAsociada.getDatosDeActividades().add(dato);

    withTransaction(()->{
      RepositorioDeOrganizaciones.getInstance().actualizar(orgAsociada);
    });


    response.redirect("/org/home");

    return null;
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

    Organizacion org = RepositorioDeOrganizaciones.getInstance().encontrar(id_org);

    Map<String, Object> model = new HashMap<>();

    model.put("razonSocial",org.getRazonSocial());

    return new ModelAndView(model, "vistas_organizacion/composiciones/composicion_hc_org.hbs");
  }


  public ModelAndView resultadoComposicionHCOrg(Request request, Response response) {

    Long id_org = getIdElementoAsociado(request);


    String fecha= request.queryParams("fecha");
    String periodo = request.queryParams("periodo");

    System.out.println("Este es el periodo encontrado: "+ periodo);
    System.out.println("Esta es la fecha encontrada: "+ fecha);

    Periodo periodoElegido = Periodo.valueOf(periodo);

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    //convert String to LocalDate
    LocalDate fechaConvertida = LocalDate.parse(fecha, formatter);

    Organizacion org = RepositorioDeOrganizaciones.getInstance().encontrar(id_org);


    List<Reporte> reportes = org.obtenerReportes(fechaConvertida,periodoElegido);


    //Busco el ultimo reporte de la organizacion
    Reporte reporte = reportes.stream().findFirst().orElse(null);

    //System.out.println("FECHA DE REPORTE:"+reporte.getFecha().toString());

    Double valorHcTramos = reporte.getTramos().valorEnEnKilogramos();
    Double valorHcDA = reporte.getActividadesGenerales().valorEnEnKilogramos();
    LocalDate fechareporte = reporte.getFecha();

    Map<String, Object> model = new HashMap<>();
    model.put("valorDeTramos", valorHcTramos);
    model.put("valorDA",valorHcDA);
    model.put("fechaReporte",fechareporte);

    return new ModelAndView(model, "vistas_organizacion/composiciones/composicion_hc_org_RESULTADO.hbs");
  }
  /*
  private Reporte ultimoReporte(Long id_org) {
    List<Reporte> reportes = RepositorioReporte.getInstance().getReportesByOrganizacion(id_org);

    System.out.println(reportes.toString());

    return reportes.get(0);
  }*/


  public ModelAndView evolucionHC(Request request, Response response) {

    Long id_org = getIdElementoAsociado(request);

    Organizacion org = RepositorioDeOrganizaciones.getInstance().encontrar(id_org);

    Map<String, Object> model = new HashMap<>();

    model.put("razonSocial",org.getRazonSocial());


    return new ModelAndView(model, "vistas_organizacion/evolucion/evol_hc_org.hbs");
  }

  public ModelAndView evolucionHCRESULTADO(Request request, Response response) {

    Long id_org = new Long(getIdElementoAsociado(request));

    String periodo = request.queryParams("periodo");

    Periodo periodoElegido = Periodo.valueOf(periodo);

    List<Reporte> reportes = RepositorioReporte.getInstance().getReportesByOrganizacion(id_org);

    List<Reporte> reportesFiltrados = reportes.stream().filter(reporte -> reporte.perteneceAPeriodo(periodoElegido)).collect(Collectors.toList());


    Map<String, Object> model = new HashMap<>();
    model.put("reportes", reportesFiltrados);
    model.put("periodo", periodo);

    return new ModelAndView(model, "vistas_organizacion/evolucion/evol_hc_org_RESULTADO.hbs");

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


  public ModelAndView misSectores(Request request, Response response) {

    Long id_org = new Long(request.session().attribute("idElementoAsociado"));
    Organizacion org = RepositorioDeOrganizaciones.getInstance().encontrar(id_org);
    List<Sector> sectores = org.getSectores().stream().collect(Collectors.toList());

    System.out.println(sectores.toString());

    Map<String, Object> model = new HashMap<>();

    model.put("sectores", sectores);

    return new ModelAndView(model, "vistas_organizacion/orgMisSectores.hbs");
  }
}

