package controllers.sesion;

import model.repositorios.repositoriosDBs.reposUsuarios.RepositorioUsuario;
import model.usuario.Usuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class SigninController {

    public ModelAndView post(Request request, Response response) {

        System.out.print("entre");
        String usuario = request.queryParams("nombre");
        String password = request.queryParams("password");

        Usuario usuarioEncontrado = RepositorioUsuario.getInstance().findByUsername(usuario).orElse(null);


        if (usuarioEncontrado == null || !usuarioEncontrado.getPassword().equals(password)) {
            System.out.print("usuario null o no match password");
            response.redirect("/login");
            //return new ModelAndView(usuario, "vistas_generales/IniciarSesion.hbs");
        }

        String rol = usuarioEncontrado.getRol().toString();

        String idElementoAsociado = usuarioEncontrado.getIdElementoAsociado().toString();

        System.out.print("usuario registrado");

        request.session().attribute("nombre", usuario);
        request.session().attribute("rol", rol);
        request.session().attribute("idElementoAsociado",idElementoAsociado);


        if (rol.equals("ORGANIZACION")) {

            response.redirect("/org/home");
        } else if (rol.equals("MIEMBRO")) {
            response.redirect("user/home");
        }else if (rol.equals("AGENTE_SECTORIAL")){
            response.redirect("ag/home");
        }else if (rol.equals("ADMINISTRADOR")){
            response.redirect("admin/home");
        }

        return null;
    }


    public ModelAndView delete(Request request, Response response) {
        System.out.print("llegue");
        request.session().removeAttribute("nombre");
        request.session().removeAttribute("rol");
        request.session().attribute("idPersona");
        System.out.print("me estoy por ir");

        response.redirect("/home");
        System.out.print("me fui");
        return null;
    }

}

