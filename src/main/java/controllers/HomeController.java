package controllers;
import model.organizacion.Organizacion;
import model.organizacion.Persona;
import model.organizacion.trayecto.Ubicacion;
import model.repositorios.repositoriosDBs.RepositorioDeOrganizaciones;
import model.repositorios.repositoriosDBs.RepositorioPersona;
import model.repositorios.repositoriosDBs.reposUsuarios.RepositorioUsuario;
import model.usuario.UserMiembro;
import model.usuario.UserOrganizacion;
import model.usuario.Usuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import java.util.ArrayList;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import java.util.List;




public class HomeController  implements WithGlobalEntityManager, TransactionalOps{
  public ModelAndView index(Request request, Response response) {
    return new ModelAndView(null, "vistas_generales/home_mi_impacto_ambiental.hbs");
  }

  public ModelAndView iniciarSesion(Request request, Response response) {
    return new ModelAndView(null, "vistas_generales/IniciarSesion.hbs");
  }

  public ModelAndView guiaDeRecomendaciones(Request request, Response response) {
    return new ModelAndView(null, "vistas_generales/guia_de_recomendaciones.hbs");
  }

  public ModelAndView registrarUsuario(Request request, Response response) {

    RepositorioUsuario.getInstance();
    UserMiembro userMiembro = new UserMiembro();

    String nombreUsuario = request.params("nombre");
    String contrasenia = request.params("registroUsuario");
    System.out.println(contrasenia);
    System.out.println(nombreUsuario);


    return new ModelAndView(null, "vistas_generales/RegistrarUsuario.hbs");
  }

  public Response registrarORG(Request request, Response response) {


    Organizacion organizacion = crearOrganizacion(request);
    UserOrganizacion usuario = creadorUsuarioOrg(request);

    withTransaction(()->{
      RepositorioDeOrganizaciones.getInstance().agregar(organizacion);
      usuario.setOrganizacion_asociada(organizacion);
      RepositorioUsuario.getInstance().agregar(usuario);

    });

    response.redirect("/home");
    return response;
  }

  private UserOrganizacion creadorUsuarioOrg(Request request) {

    return new UserOrganizacion(request.queryParams("user"), request.queryParams("contrasenia"), Usuario.Rol.ORGANIZACION);

  }

  private Organizacion crearOrganizacion(Request request) {

    Ubicacion ubicacionOrg = new Ubicacion(999,request.queryParams("ubicacionOrg"),999);

    Organizacion.Clasificacion clasificacionOrg = Organizacion.Clasificacion.values()[Integer.parseInt(request.queryParams("clasificacionOrg"))];

    Organizacion.Tipo tipoOrg = Organizacion.Tipo.values()[Integer.parseInt(request.queryParams("tipoOrg"))];



    return  new Organizacion(request.queryParams("razonSocial"),tipoOrg,ubicacionOrg,clasificacionOrg,generadorDeImagen());
  }

  private String generadorDeImagen(){

    int numero = (int) (Math.random() * 5 + 0);

    List<String> imagenesDeOrganizacoin = new ArrayList<>();
    imagenesDeOrganizacoin.add("../../imagenes/icons_buildings/box.png");
    imagenesDeOrganizacoin.add( "../../imagenes/icons_buildings/centro.png");
    imagenesDeOrganizacoin.add("../../imagenes/icons_buildings/edificio.png");
    imagenesDeOrganizacoin.add("../../imagenes/icons_buildings/gold.png");
    imagenesDeOrganizacoin.add("../../imagenes/icons_buildings/health.png");
    imagenesDeOrganizacoin.add("../../imagenes/icons_buildings/museo.png");

    return  imagenesDeOrganizacoin.get(numero);
  }

  public Response tomarDatosFormRegistrarUsuario(Request request, Response response) { // NO BORRAR ES LA BASE

    Persona personaCreada = crearPersona(request);

    UserMiembro usuarioCreado = crearUsuario(request);
    usuarioCreado.setPersona_asociada(personaCreada);

    withTransaction(()->{
      RepositorioPersona.getInstance().agregar(personaCreada);
      RepositorioUsuario.getInstance().agregar(usuarioCreado);
    });


    response.redirect("/home");

    return response;
  }

  private UserMiembro crearUsuario(Request request) {

    return  new UserMiembro(request.queryParams("user"),request.queryParams("contrasenia"), Usuario.Rol.MIEMBRO);
  }

  private Persona crearPersona(Request request) {

    Persona.TipoDocumento tipoDocumento =Persona.TipoDocumento.values()[Integer.parseInt(request.queryParams("tipoDocumento"))];

    String documento = request.queryParams("documento");

    return new Persona(request.queryParams("nombre"),request.queryParams("apellido"),new Integer(documento),tipoDocumento);
  }


  public ModelAndView registrarOrg(Request request, Response response) {
    return new ModelAndView(null, "vistas_generales/registrar_organizacion.hbs");
  }

  public ModelAndView post(Request request, Response response) {
    return null;
  }
}
