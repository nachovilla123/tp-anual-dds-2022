package controllers;


import model.implementacionCSV.FactorDeEmision;
import model.implementacionCSV.Periodo;
import model.implementacionCSV.tipoDeConsumo.TipoDeConsumo;
import model.mediodetransporte.EcoFriendly;
import model.mediodetransporte.ServicioContratado;
import model.mediodetransporte.VehiculoParticular;
import model.mediodetransporte.transportepublico.TransportePublico;
import model.organizacion.Miembro;
import model.organizacion.Organizacion;
import model.organizacion.Persona;
import model.organizacion.Sector;
import model.organizacion.trayecto.Tramo;
import model.organizacion.trayecto.Trayecto;
import model.organizacion.trayecto.Ubicacion;
import model.repositorios.repositoriosDBs.RepositorioDeOrganizaciones;
import model.repositorios.repositoriosDBs.RepositorioPersona;
import model.repositorios.repositoriosDBs.RepositorioSector;
import model.repositorios.repositoriosDBs.reposUsuarios.RepositorioUsuario;
import model.repositorios.repositoriosDBs.reposUsuarios.RepositorioUsuarioMiembro;
import model.usuario.UserMiembro;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;


import spark.ModelAndView;
import spark.Request;
import spark.Response;


import javax.jws.soap.SOAPBinding;
import java.awt.image.AreaAveragingScaleFilter;
import java.util.*;
import java.util.stream.Collectors;

public class UsuarioController  implements WithGlobalEntityManager, TransactionalOps {

  //---------------------- MENU-----------------------------------------//
  public ModelAndView menu(Request request, Response response) {
    return new ModelAndView(null, "vistas_usuario/userMenu.hbs");
  }

  public ModelAndView home(Request request, Response response) {
    return new ModelAndView(null, "vistas_usuario/userHome.hbs");
  }


  //---------------------- ORGANIZACIONES POR PARTE DEL USUARIO-----------------------------------------//
  public ModelAndView misOrganizaciones(Request request, Response response) {

    UserMiembro usuarioEncontrado = buscarUsuarioEnDB(request);

    System.out.print("Esta es la persona encontrada: " + usuarioEncontrado.getPersona_asociada().getApellido());

    List<Organizacion> organizaciones = obtenerOrganizacionesDeUnUsuarioMiembro(usuarioEncontrado);

    System.out.print(organizaciones.toString());

    Map<String, Object> model = new HashMap<>();
    model.put("organizaciones", organizaciones);

    return new ModelAndView(model, "vistas_usuario/userMisOrganizaciones.hbs"); //laburar por parte del .hbs
  }

  private UserMiembro buscarUsuarioEnDB(Request request) {
    String usuario = getNombreUsuario(request, "nombre");
    return RepositorioUsuarioMiembro.getInstance().findByUsername(usuario).orElse(null);
  }

  private String getNombreUsuario(Request request, String nombre) {
    return request.session().attribute(nombre);
  }

  private List<Organizacion> obtenerOrganizacionesDeUnUsuarioMiembro(UserMiembro usuarioEncontrado) {
    List<Organizacion> organizaciones = usuarioEncontrado.getPersona_asociada()
        .getTrabajos()
        .stream().
        map(miembro -> miembro.getOrganizacion()).collect(Collectors.toList());
    return organizaciones;
  }

  public List<Sector> obtenerSectoresDeOrg(Request request, Response response) {

    Long id_org= new Long (request.params("id"));

    UserMiembro usuarioEncontrado = buscarUsuarioEnDB(request);
    System.out.print("Esta es la persona encontrada: "+ usuarioEncontrado.getPersona_asociada().getApellido());


    Set<Sector> sectoresEncontrados = RepositorioDeOrganizaciones.getInstance().encontrar(id_org).getSectores();

    if (sectoresEncontrados == null){
      return new ArrayList<>();
    }

    System.out.println(sectoresEncontrados);

    List<Sector> sectores = new ArrayList<>();
    sectores = sectoresEncontrados.stream().collect(Collectors.toList());;

    return sectores;
  }

  public ModelAndView vincularOrganizacion(Request request, Response response) {

    List<Organizacion> organizaciones = RepositorioDeOrganizaciones.getInstance().buscarTodos();
    System.out.println(organizaciones.toString());

    Map<String, Object> model = new HashMap<>();
    model.put("organizaciones", organizaciones);

    return new ModelAndView(model, "vistas_usuario/userVincularAOrganizacion.hbs");
  }

  public Response tomarDatosVincularOrg(Request request, Response response) {

    String id_org = request.queryParams("id_org");
    System.out.println(id_org);
    String idsector = request.queryParams("ids");

    System.out.println(idsector);

  /*
    int numero = (int) (Math.random() * 5 + 0);

    List<String> imagenesDeOrganizacoin = new ArrayList<>();
    imagenesDeOrganizacoin.add("../../imagenes/icons_buildings/box.png");
    imagenesDeOrganizacoin.add( "../../imagenes/icons_buildings/centro.png");
    imagenesDeOrganizacoin.add("../../imagenes/icons_buildings/edificio.png");
    imagenesDeOrganizacoin.add("../../imagenes/icons_buildings/gold.png");
    imagenesDeOrganizacoin.add("../../imagenes/icons_buildings/health.png");
    imagenesDeOrganizacoin.add("../../imagenes/icons_buildings/museo.png");
    System.out.println(numero);
    String srcImg = imagenesDeOrganizacoin.get(numero);
    System.out.println(srcImg);*/




    return response;
  }



  //---------------------- TRAMOS-----------------------------------------//
  public ModelAndView registrarTramo(Request request, Response response) {
    return new ModelAndView(null, "vistas_usuario/userRegistrarTramo.hbs");
  }

  public ModelAndView registrarTramoExistoso(Request request, Response response) {
    return new ModelAndView(null, "vistas_usuario/userRegistrarTramo_REGISTRO.hbs");
  }



  //en el servidor, esto lo va a tener que llamar mediante el front end de alguna manera (js o redireccionamiento)
  public ModelAndView cargarTramo(Request request, Response response) {

    UserMiembro usuarioEncontrado = buscarUsuarioEnDB(request);

    if (usuarioEncontrado == null ){
      System.out.print("usuario null o no match password");
      //return new ModelAndView(null, "vistas_generales/IniciarSesion.hbs");
    }

    Tramo tramoCreado = creadorDeTramo(request);
    if (tramoCreado == null ){
      System.out.print("No se pudo crear el tramo");
      response.redirect("/user/menu/registrartramo/created/menu"); //DEBERIA SER UN MODEL AND VIEW DE ERROR
    }

    usuarioEncontrado.getPersona_asociada().agregarTramoNoRegistrado(tramoCreado);

    withTransaction(()->{
      RepositorioUsuario.getInstance().actualizar(usuarioEncontrado);
    });


    response.redirect("/user/menu/registrartramo/created/menu");
    return null;//new  ModelAndView(null, "vistas_usuario/userRegistrarTramo_REGISTRO.hbs");

  }


  //TODO
  private Tramo creadorDeTramo(Request request) {
    String tipoTransporte = request.queryParams("tipoTransporte");


    String transporte = request.queryParams("preg1");
    System.out.println(transporte);
    String nombreIdentificadorDelTramo = request.queryParams("nombreTramo");
    System.out.println(nombreIdentificadorDelTramo);

    if (transporte.equalsIgnoreCase("3")) {

      Ubicacion ubicacionDestino = new Ubicacion(new Integer (request.queryParams("localidadInicialEco")),request.queryParams("ubicacionInicioEco"),new Integer (request.queryParams("alturaInicialEco")));
      Ubicacion ubicacionFinal = new Ubicacion(new Integer (request.queryParams("localidadFinalEco")),request.queryParams("ubicacionFinalEco"), new Integer(request.queryParams("alturaFinalEco")));

      //Constructor: nada
      EcoFriendly ecoFriendlyCreado = new EcoFriendly(null);
      return new Tramo(nombreIdentificadorDelTramo, ecoFriendlyCreado, ubicacionDestino, ubicacionFinal);

    } else if (transporte.equalsIgnoreCase("2")) {
      System.out.println("ES UN SERIVICIO CONTRATADO");


      Ubicacion ubicacionDestino = new Ubicacion(new Integer (request.queryParams("localidadInicialServico")),request.queryParams("ubicacionInicioServicio"),new Integer(request.queryParams("alturaInicialServicio")));
      Ubicacion ubicacionFinal = new Ubicacion(new Integer (request.queryParams("localidadFinalServicio")),request.queryParams("ubicacionFinalServicio"), new Integer(request.queryParams("alturaFinalServicio")));

      //Constructor: ServicioContratado(String tipoServiciocontratado,Ubicacion ubicacionInicio,Ubicacion ubicacionFinal,Double cantidadDeCombustiblePorKM,TipoDeConsumo
      ServicioContratado servicioContratadoCreado = new ServicioContratado(request.queryParams("nameServicio"),
          ubicacionDestino,
          ubicacionFinal,
          2.0,
          new FactorDeEmision(2.0,TipoDeConsumo.DIESEL_GASOIL));

      return new Tramo(nombreIdentificadorDelTramo, servicioContratadoCreado, ubicacionDestino, ubicacionFinal);

    }else if (transporte.equalsIgnoreCase("4")) {
      System.out.println("Es un Vehiculo Particular");

      String tipoVehiculo = request.queryParams("tipoVehiculo");
      VehiculoParticular.TipotransporteParticular tipo = VehiculoParticular.TipotransporteParticular.valueOf(tipoVehiculo);

      String combustibleVehiculo = request.queryParams("combustibleVehiculo");
      VehiculoParticular.TipoCombustible cumbustible = VehiculoParticular.TipoCombustible.valueOf(combustibleVehiculo);

      Ubicacion ubicacionDestino = new Ubicacion(new Integer (request.queryParams("localidadInicialVehiculo")),request.queryParams("ubicacionInicioVehiculo"),new Integer (request.queryParams("alturaInicialVehiculo")));
      Ubicacion ubicacionFinal = new Ubicacion(new Integer (request.queryParams("localidadFinalVehiculo")),request.queryParams("ubicacionFinalVehiculo"), new Integer(request.queryParams("alturaFinalVehiculo")));

      //Constructor: Enum->TipoCombustible , Enum-> TipotransporteParticular , double cantidadDeCombustiblePorKM,enum->TipoDeConsumo
      VehiculoParticular vehiculoParticularCreado = new VehiculoParticular(cumbustible,
          tipo, 2.0,
          new FactorDeEmision(2.0,TipoDeConsumo.DIESEL_GASOIL));

      return new Tramo(nombreIdentificadorDelTramo, vehiculoParticularCreado, ubicacionDestino, ubicacionFinal);

    } else if (transporte.equalsIgnoreCase("1")){
      System.out.println("ES UN TRANPOSRTE PUBLICO");
      //Este es un caso particular porq lo deberias buscar en la base de datos, ya deberia estar cargado

      //Este es un caso particular porq lo deberias buscar en la base de datos, ya deberia estar cargado
      String lineaTransporte = request.queryParams("lineaTransporte");
      TransportePublico transportePublicoTraidodeLaDB = new TransportePublico();

      Ubicacion ubicacionDestino = new Ubicacion(new Integer (request.queryParams("localidadInicialPublico")),request.queryParams("paradaInicio"), new Integer(request.queryParams("alturaInicial")));
      Ubicacion ubicacionFinal = new Ubicacion(new Integer (request.queryParams("localidadFinalPublico")),request.queryParams("paradaFin"), new Integer (request.queryParams("alturaFinal")));

      return new Tramo(nombreIdentificadorDelTramo, transportePublicoTraidodeLaDB,ubicacionDestino, ubicacionFinal);
    }


    return null;
  }




  //---------------------- TRAYECTOS-----------------------------------------//
  public ModelAndView registrarTrayecto(Request request, Response response) {

    Long idPersona = new Long(request.session().attribute("idElementoAsociado"));

    Set<Tramo> tramosNoRegistradosDelUsuario = RepositorioPersona.getInstance().encontrar(idPersona).getTramosNoRegistrados();
    System.out.println(tramosNoRegistradosDelUsuario.toString());

    UserMiembro usuarioEncontrado = buscarUsuarioEnDB(request);


    List<Organizacion> orgsTrabajaUsuario = obtenerOrganizacionesDeUnUsuarioMiembro(usuarioEncontrado);
    List<Sector> sectoresDeorgTrabajaUsuario = obtenerSectoresEnDondeTrabaja(orgsTrabajaUsuario, usuarioEncontrado);



    Map<String, Object> model = new HashMap<>();
    model.put("tramos", tramosNoRegistradosDelUsuario);
    model.put("sectoresDeorgTrabajaUsuario", sectoresDeorgTrabajaUsuario);


    return new ModelAndView(model, "vistas_usuario/userRegistrarTrayecto.hbs");
  }

  public List<Sector> obtenerSectoresEnDondeTrabaja(List<Organizacion> orgsTrabajaUsuario, UserMiembro usuario){
      List<Sector> sectores = new ArrayList<>();
      Set<Miembro> trabajos = usuario.getPersona_asociada().getTrabajos();

      List<Long> id_sectores_trabajo_user = trabajos.stream().map(trabajo -> trabajo.getTrabajo().getId()).collect(Collectors.toList());

      for (Organizacion org: orgsTrabajaUsuario){ // para cada organizacion de las que trabaja el usuario
        for (Sector sector: org.getSectores()){ // para cada sector de las organizaciones en las que trabaja
          if (id_sectores_trabajo_user.contains(sector.getId())){
            sectores.add(sector);
          }
        }
      }

      return sectores;
  }


  public List<Sector> sectoresDeLasOrganizacionesQueTrabaja(List<Organizacion> organizaciones, Miembro miembroEnSector){

    return organizaciones.stream().flatMap(organizacion -> organizacion.getSectores().stream()).filter(sector -> sector.getMiembros().stream().collect(Collectors.toList()).contains(miembroEnSector)).collect(Collectors.toList());
  }

  // tenemos una lista de organizaciones para las que un usuario trabaja
  // de esa lista de organizaciones tenemos que traer para que sector trabaja ese usuario para cada organizacion






  //en el servidor
  public Response cargarTrayecto(Request request, Response response) {

    //Fijarse como recibir x cantidad de tramos
    //Crear el tramo y cargarselo a un trayecto ¿A que miembro se lo agregamos, tener en cuenta esto?
    //Que la pantalla puede visualizar que organizacion pertenece el usuario y que este eliga para asi cuando se tenga que cargar
    //En la parte del servidor ya se sepa en el caso que el usuario/persona tenga varios miembros (es decir trabajos -> organizaciones
    String cant = request.queryParams("cantidadTramosAgregados");

    String nombreTrayecto = request.queryParams("nombreTrayecto");

    String idSector = request.queryParams("sectorSeleccionado"); // esto todavia no esta hecho
    System.out.println("---------------- ID SECTOR ES---------------------------------");
    System.out.println(idSector);
    System.out.println("-------------------------------------------------");



    int cantidadTramos = Integer.parseInt(cant);


    String[] ids = request.queryParams("idTramosAgregados").split(",");
    Long idTramoRegistrado;
    Set<Tramo> tramosParaTrayecto = new HashSet<>();
    Set<Tramo> tramosSacar = new HashSet<>();
    ArrayList<Tramo> tramosDelTrayectoFinal = new ArrayList<>();
    int i=0;
    UserMiembro usuarioEncontrado = buscarUsuarioEnDB(request);

    tramosParaTrayecto = usuarioEncontrado.getPersona_asociada().getTramosNoRegistrados();

    while(cantidadTramos>i){

      idTramoRegistrado= new Long(ids[i]);

      for(Tramo tramo:tramosParaTrayecto){

        if (idTramoRegistrado.equals(tramo.getId())){
          tramosDelTrayectoFinal.add(tramo);
          Tramo tramoSacar = usuarioEncontrado.getPersona_asociada().getTramosNoRegistrados().stream().filter( t -> t.getId().equals(tramo.getId()) ).collect(Collectors.toList()).get(0);
          tramosSacar.add(tramoSacar);
        }

      }
      i++;


    }






    Ubicacion origen = tramosDelTrayectoFinal.get(0).getOrigen();
    Ubicacion destino = tramosDelTrayectoFinal.get(cantidadTramos-1).getDestino();
    Trayecto trayectoAPersistir = new Trayecto(origen,destino,tramosDelTrayectoFinal,nombreTrayecto);

    //aca agarro cada trabajo del usuario y si en el trabajo (sector) coincide su id con el idSector, se registra el trayecto
    for(Miembro trabajo: usuarioEncontrado.getPersona_asociada().getTrabajos()){
      if (Long.toString(trabajo.getTrabajo().getId()).equals(idSector)){
        trabajo.registrarTrayecto(trayectoAPersistir);
      }
    }
    tramosSacar.forEach(t->usuarioEncontrado.getPersona_asociada().getTramosNoRegistrados().remove(t));

    withTransaction(()->{
      RepositorioUsuario.getInstance().actualizar(usuarioEncontrado);
    });

    response.redirect("/user/menu/registrartrayecto");
    return response;
  }

  //---------------------- CALCULO DE HC-----------------------------------------//
  public ModelAndView calculoHC(Request request, Response response) {

    UserMiembro usuarioEncontrado = buscarUsuarioEnDB(request);
    System.out.print("Esta es la persona encontrada: "+ usuarioEncontrado.getPersona_asociada().getApellido());

    List<Organizacion> organizaciones = obtenerOrganizacionesDeUnUsuarioMiembro(usuarioEncontrado);

    System.out.print(organizaciones.toString());

    //List<Organizacion> orgsAsocidas = RepositorioDeOrganizaciones.getInstance().findByIdPersona(idPersona);
    Map<String, Object> model = new HashMap<>();
    model.put("organizaciones", organizaciones);

    return new ModelAndView(model, "vistas_usuario/userCalculoHC.hbs");
  }

  public ModelAndView valorcalculoHC(Request request, Response response) {

    Long id_org= new Long(request.queryParams("id_org"));
    Periodo periodo = Periodo.valueOf(request.queryParams("periodo"));

    System.out.println("Esta es el id de la org encontrada: "+ id_org.toString());

    UserMiembro usuarioEncontrado = buscarUsuarioEnDB(request);
    System.out.println("Esta es el usuario encontrado: "+ usuarioEncontrado.getNombre());

    System.out.println("Esta es la persona encontrada: "+ usuarioEncontrado.getPersona_asociada().getApellido());

    Organizacion organizacion = RepositorioDeOrganizaciones.getInstance().encontrar(id_org);

    //deberia traerse la membresia que esta en esa organizacion
    Miembro miembraso = usuarioEncontrado.getPersona_asociada().getTrabajos().stream().filter(miembro -> miembro.getOrganizacion().getId().equals(organizacion.getId())).findFirst().get();
    Map<String, Object> model = new HashMap<>();

    Double resultado = miembraso.ejecutarCalculadoraHc();


    // TODO ARREGLAR PARA QUE ESTA LOGICA ESTE DENTRO DEL CALCULO DE HC DE UN MIEMBRO

    if(periodo == Periodo.ANUAL){
      resultado = resultado * 365;
    }
    else{
      resultado =resultado * 30;
    }


    model.put("resultado", resultado.toString());
    model.put("razonSocial", organizacion.getRazonSocial());

    return new ModelAndView(model, "vistas_usuario/resultadoUserCalculoHC.hbs");
  }


  public ModelAndView vincularASector(Request request, Response response) {

    Long id_org= new Long(request.queryParams("id_org"));
    System.out.print("Esta es el id de la org encontrada: "+ id_org.toString());



    Organizacion org =RepositorioDeOrganizaciones.getInstance().encontrar(id_org);
    List<Sector> sectores = org.getSectores().stream().collect(Collectors.toList());
    System.out.println(sectores.toString());

    Map<String, Object> model = new HashMap<>();

    model.put("sectores", sectores);
    model.put("organizacion", org);

    return new ModelAndView(model, "vistas_usuario/userVincularASector.hbs");
  }

  public ModelAndView realizarPedidoDeVinculacion(Request request, Response response) {

    /*  ACA YA TENEMOS LOS DATOS   */
    String[] ids = request.queryParams("ids").split("," );
    Long id_sector= new Long(ids[0]);
    Long id_org = new Long(ids[1]);



    System.out.println("Esta es el id de la organizacion encontrado: "+ id_org);
    System.out.println(" Este es el id del sector encontrado: "+ id_sector);

    Organizacion org = RepositorioDeOrganizaciones.getInstance().encontrar(id_org);
    Sector sector = org.getSectores().stream().filter(sector1 -> sector1.getId().equals(id_sector)).findFirst().get();

    UserMiembro usuarioEncontrado = buscarUsuarioEnDB(request);
    System.out.println("Esta es el usuario encontrado: "+ usuarioEncontrado.getNombre());

    Persona personaAsociada = usuarioEncontrado.getPersona_asociada();
    System.out.println("Esta es la persona encontrada: "+ personaAsociada.getApellido());


    try { //------------------- ACA SE PERSISTE --------------------//

      personaAsociada.generarSolicitudDeVinculacion(sector);

      withTransaction(()->{
        RepositorioSector.getInstance().actualizar(sector);
      });

     //RepositorioSector.getInstance().actualizarBoostrap(sector);

      // TODO redirecciono a pantalla de pedido realizado

      response.redirect("../user/menu/vincularasector/done");

    }
    catch(Exception e) {
      //  falla la insercion
      //redirecciono a pantalla de pedido no realizado
    }

    return null;

  }

  public ModelAndView vinculacionExitosa(Request request, Response response) {

    return new ModelAndView(null, "vistas_usuario/userVincularASector_RESULTADO.hbs");
  }

  public ModelAndView misTrayectos(Request request, Response response) {

    List<Organizacion> organiazciones = buscarUsuarioEnDB(request).getPersona_asociada().getTrabajos().stream().map(miembro -> miembro.getOrganizacion()).collect(Collectors.toList());

    Map<String, Object> model = new HashMap<>();
    model.put("organizaciones", organiazciones);

    return new ModelAndView(model, "vistas_usuario/userMisTrayectos.hbs");
  }

  public ModelAndView resultadomisTrayectos(Request request, Response response) {

    Long id_org= new Long (request.queryParams("id_org"));
    UserMiembro usuarioEncontrado = buscarUsuarioEnDB(request);

    List<Trayecto> trayectos = usuarioEncontrado.getPersona_asociada().getTrabajos().stream().filter(miembro -> miembro.getOrganizacion().getId().equals(id_org)).flatMap(miembro->miembro.getRegistroTrayectos().stream()).collect(Collectors.toList());
  System.out.println(trayectos.toString());

  Map<String, Object> model = new HashMap<>();
    model.put("trayectos", trayectos);

    return new ModelAndView(model, "vistas_usuario/userMisTrayectosRESULTADO.hbs");
  }

}
