
import controllers.AgenteSectorialController;
import controllers.OrganizacionController;
import controllers.UsuarioController;
import controllers.sesion.SigninController;
import controllers.HomeController;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import spark.Spark;
import spark.debug.DebugScreen;

import spark.template.handlebars.HandlebarsTemplateEngine;

public class Router {

	public static void configure() {

		//para transformar las responses a json
		JsonTransformer jsonTransformer = new JsonTransformer();
		// PANTALLAS A DEFINIR A QUIEN PERTENECE SU VISTA
		HandlebarsTemplateEngine engineTemplate = new HandlebarsTemplateEngine();
		SigninController signinController = new SigninController();

		// PANTALLAS VISTA GENERAL
		HomeController homeController = new HomeController();

		//PANTALLAS VISTAS DE ORGANIZACION
		OrganizacionController organizacionController = new OrganizacionController();

		// PANTALLAS VISTA DEL USUARIO
		UsuarioController usuarioController = new UsuarioController();

		//PANTALLAS AGENTE SECTORIAL
		AgenteSectorialController agenteSectorialController = new AgenteSectorialController();

		// no sabemos si los transportes ya van a venir en el sistema o alguien los va a poder agregar
		//UserAgregarTransportes userAgregarTransportes = new UserAgregarTransportes();

		DebugScreen.enableDebugScreen();
		Spark.staticFiles.location("public");

		// ETAPA DE PONER RUTAS


		//FILTROS PARA LA SESSION
		Spark.before("/user/*", (req, res) -> {
			System.out.println(req.pathInfo());
			if (req.session().attribute("nombre") == null) {
				res.redirect("/login");
			}
		});

		Spark.before("/org/*", (req, res) -> {
			System.out.println(req.pathInfo());
			if (req.session().attribute("nombre") == null) {
				res.redirect("/login");
			}
		});

		//FILTROS RELACIONADOS A LA PERSISTENCIA
		Spark.before(((req,res)->{
			if(req.requestMethod() == "POST"){
				PerThreadEntityManagers.getEntityManager().getTransaction();
			}
		}));

		Spark.after(((req,res)->{

				if(PerThreadEntityManagers.getEntityManager().getTransaction().isActive()){
					PerThreadEntityManagers.getEntityManager().getTransaction().commit();
					System.out.println("ACABO DE COMMITEAR");
				};

		}));
/*
		Spark.exception( Exception.class, (e,req,es)->{
			PerThreadEntityManagers.getEntityManager().getTransaction().rollback();
		});
*/
		Spark.after((req,res)->{
			/*
			if(req.requestMethod() == "POST"){
				PerThreadEntityManagers.getEntityManager().close();
			}
				*/
			PerThreadEntityManagers.getEntityManager().getEntityManagerFactory().getCache().evictAll();
			//PerThreadEntityManagers.getEntityManager().clear();
			PerThreadEntityManagers.closeEntityManager();
			System.out.println("ACABO DE BORRAR LA CACHE EN LA TRANSACTION");
		});
		/////////


		//peticiones que hace el usuario en el navegador //PARA TESTING
		Spark.before((req,res)->{
			System.out.println(req.requestMethod()+"-"+ req.pathInfo());
		});


		//UTILIDADES DEL SECTOR
		Spark.get("/sectores/:id", usuarioController::obtenerSectoresDeOrg,new JsonTransformer());
		Spark.post("/sectores/pedidoDeVinculacion", usuarioController::realizarPedidoDeVinculacion,engineTemplate);

		// PANTALLAS VISTA GENERAL (PARA CUALQUIERA QUE ENTRE A LA WEB) homeController

		//SESSION AND HOME
		Spark.post("/session", signinController::post, engineTemplate);
		Spark.post("/session/delete",signinController::delete, engineTemplate);

		Spark.get("/", homeController::index, engineTemplate);
		Spark.get("/home", homeController::index, engineTemplate);
		Spark.get("/login", homeController::iniciarSesion, engineTemplate);
		Spark.get("/register", homeController::registrarUsuario, engineTemplate);
		Spark.get("/home/guiarecomendaciones", homeController::guiaDeRecomendaciones, engineTemplate);

		//REGISTRO DE UN USUARIO MIEMBRO
		Spark.get("/register/createuser", homeController::tomarDatosFormRegistrarUsuario);
		Spark.post("/register/createuser/created", homeController::tomarDatosFormRegistrarUsuario);

		//REGISTRO DE UN USUARIO ORGANIZACION
		Spark.get("/registerorg",homeController::registrarOrg,engineTemplate);
		Spark.get("/registerorg/createorg", homeController::registrarORG);
		Spark.post("/registerorg/createorg/created", homeController::registrarORG);

		//PANTALLAS VISTAS DE ORGANIZACION organizacionController
		Spark.get("org/menu", organizacionController::menu, engineTemplate);
		//Spark.post("org/menu", organizacionController::post, engineTemplate);
		Spark.get("org/menu/calculoHC", organizacionController::calculoHC, engineTemplate);
		Spark.get("org/menu/valorcalculohc", organizacionController::valorcalculoHC, engineTemplate);
		Spark.get("org/menu/mismiembros", organizacionController::misMiembros, engineTemplate);
		Spark.get("org/menu/registrarfactoresdeemision", organizacionController::registrarFactoresEmision, engineTemplate);
		Spark.get("org/menu/registrarmediciones", organizacionController::registrarMediciones, engineTemplate);
		Spark.get("org/menu/solicitudesdevinculacion", organizacionController::solicitudVinculacion, engineTemplate);

		//REPORTES DE UNA ORGANIZACION
		Spark.get("org/menu/menureportes/compohcorg", organizacionController::composicionHCOrg, engineTemplate);
		Spark.get("org/menu/menureportes/evolhcorg", organizacionController::evolHCOrg, engineTemplate);
		//TODO ESTE ES DE ADMINISTRADOR
		//Spark.get("org/menu/menureportes/hctotalorg", organizacionController::hcTotalTipoOrg, engineTemplate);
		Spark.get("/menureportes", organizacionController::menuReportes, engineTemplate);
		Spark.get("/evoluciondehuelladecarbonodeunaorganizacion", organizacionController::evolHCOrg, engineTemplate);
		Spark.get("/huelladecarbonototalpororganizacion", organizacionController::hcTotalTipoOrg, engineTemplate);

		//TODO FALTA QUE LES LLEGUE EL ID DEL SECTOR
		Spark.post("org/menu/solicitudesdevinculacion/aceptar/:id_solicitud", organizacionController::aceptarVinculacion,engineTemplate);
		Spark.post("org/menu/solicitudesdevinculacion/rechazar/:id_solicitud", organizacionController::rechazarVinculacion,engineTemplate);
		Spark.get("org/menu/menureportes", organizacionController::menuReportes, engineTemplate);


		// PANTALLAS VISTA DEL USUARIO
		Spark.get("user/menu", usuarioController::menu, engineTemplate);
		//Spark.post("user/menu", usuarioController::post, engineTemplate);


		Spark.get("user/menu/calculohc", usuarioController::calculoHC, engineTemplate);
		Spark.get("user/menu/valorcalculohc", usuarioController::valorcalculoHC,engineTemplate); //TODO ES DE PRUEBA
		Spark.get("user/menu/misorganizaciones", usuarioController::misOrganizaciones, engineTemplate);
		Spark.get("user/menu/registrartramo", usuarioController::registrarTramo, engineTemplate);
		Spark.post("/user/menu/registrartramo/created", usuarioController::cargarTramo,engineTemplate);
		Spark.get("/user/menu/registrartramo/created/menu", usuarioController::registrarTramoExistoso, engineTemplate);

		//Spark.get("/registerorg",homeController::registrarOrg,engineTemplate); // treaemos pantalla ,
		//Spark.get("/registerorg/createorg", homeController::registrarORG);
		//Spark.post("/registerorg/createorg/created", homeController::registrarORG);

		Spark.get("user/menu/registrartrayecto", usuarioController::registrarTrayecto, engineTemplate);//pantalla
		Spark.get("user/menu/registrartrayecto/createdtra", usuarioController::cargarTrayecto);
		Spark.post("user/menu/registrartrayecto/createdtra/created", usuarioController::cargarTrayecto);  //cargarlo en el servidor

		Spark.get("user/menu/vincularaorganizacion", usuarioController::vincularOrganizacion, engineTemplate);
		Spark.get("user/menu/vincularasector", usuarioController::vincularASector,engineTemplate);
		Spark.get("user/menu/vincularasector/done", usuarioController::vinculacionExitosa,engineTemplate);


		//------------------------ VISTAS DE AGENTE SECTORIAL ---------------------------//

		//------------------------ hice esto pero creo que ya esta hecho arriba :(  ---------------------------//
		Spark.get("ag/menu", agenteSectorialController::menu, engineTemplate);
		//REPORTES DE UNA ORGANIZACION

		Spark.get("ag/menu/menureportes/compohcsectorterritorial", agenteSectorialController::composicionHCSectorTerritorial, engineTemplate);
		Spark.get("ag/menu/menureportes/evolhcsectorterritorial", agenteSectorialController::evolHCSectorTerritorial, engineTemplate);
		Spark.get("ag/menu/menureportes/hctotalsectorterritorial", agenteSectorialController::hcTotalPorSectorTerritorial, engineTemplate);


		//Spark.get("ag/villatestea", agenteSectorialController::villatestea, engineTemplate);
		//Spark.get("/menureportes", organizacionController::menuReportes, engineTemplate);
		//BORRAR A FUTURO
		//Spark.get("/composiciondehuelladecarbonodeunsectorterritorial", agenteSectorialController::composicionHCSectorTerritorial, engineTemplate);
		//Spark.get("/evoluciondehuelladecarbonodeunsectorterritorial", agenteSectorialController::evolHCSectorTerritorial, engineTemplate);
		//Spark.get("/huelladecarbonototalporsectorterritorial", agenteSectorialController::hcTotalPorSectorTerritorial, engineTemplate);
		//Spark.get("org/menu/menureportes/hctotalsectorterritorial", agenteSectorialController::hcTotalPorSectorTerritorial, engineTemplate);
		//Spark.get("org/menu/menureportes/evolhcsectorterritorial", agenteSectorialController::evolHCSectorTerritorial, engineTemplate);
		//Spark.get("org/menu/menureportes/compohcsectorterritorial", agenteSectorialController::composicionHCSectorTerritorial, engineTemplate);
		//Spark.get("/composiciondehuelladecarbonodeunaorganizacion", organizacionController::composicionHCOrg, engineTemplate);

	}

}
