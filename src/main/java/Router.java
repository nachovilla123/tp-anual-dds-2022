
import controllers.*;
import controllers.sesion.SigninController;

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


		//PANTALLAS ADMINISTRADOR
		AdministradorController administradorController = new AdministradorController();

		// no sabemos si los transportes ya van a venir en el sistema o alguien los va a poder agregar
		//UserAgregarTransportes userAgregarTransportes = new UserAgregarTransportes();

		DebugScreen.enableDebugScreen();
		Spark.staticFiles.location("public");

		// ETAPA DE PONER RUTAS


		//FILTROS PARA LA SESSION
		Spark.before("/user/*", (req, res) -> {
			System.out.println(req.pathInfo());
			if (req.session().attribute("nombre") == null && req.session().attribute("rol") != "MIEMBRO") {
				res.redirect("/login");

			}
		});

		Spark.before("/org/*", (req, res) -> {
			System.out.println(req.pathInfo());
			if (req.session().attribute("nombre") == null && req.session().attribute("rol") != "ORGANIZACION") {
				res.redirect("/login");
			}
		});

		Spark.before("/ag/*", (req, res) -> {
			System.out.println(req.pathInfo());
			if (req.session().attribute("nombre") == null && req.session().attribute("rol") != "AGENTE_SECTORIAL") {
				res.redirect("/login");
			}
		});


		Spark.before("/admin/*", (req, res) -> {
			System.out.println(req.pathInfo());
			if (req.session().attribute("nombre") == null && req.session().attribute("rol") != "AGENTE_SECTORIAL") {
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


		Spark.after((req,res)->{

			PerThreadEntityManagers.getEntityManager().getEntityManagerFactory().getCache().evictAll();
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
		Spark.get("*/menu/guiarecomendaciones", homeController::guiaDeRecomendaciones, engineTemplate);

		//REGISTRO DE UN USUARIO MIEMBRO
		Spark.get("/register/createuser", homeController::tomarDatosFormRegistrarUsuario);
		Spark.post("/register/createuser/created", homeController::tomarDatosFormRegistrarUsuario);

		//REGISTRO DE UN USUARIO ORGANIZACION
		Spark.get("/registerorg",homeController::registrarOrg,engineTemplate);
		Spark.get("/registerorg/createorg", homeController::registrarORG);
		Spark.post("/registerorg/createorg/created", homeController::registrarORG);




		//------------------------ VISTAS DE ORGANIZACION ---------------------------//

		Spark.get("org/menu", organizacionController::menu, engineTemplate);
		Spark.get("org/home", organizacionController::home, engineTemplate);



		Spark.get("org/menu/menureportes", organizacionController::menuReportes, engineTemplate);


		Spark.get("org/menu/calculoHC", organizacionController::calculoHC, engineTemplate);
		Spark.get("org/menu/valorcalculohc", organizacionController::valorcalculoHC, engineTemplate);
		Spark.get("org/menu/mismiembros", organizacionController::misMiembros, engineTemplate);

		Spark.get("/org/menu/missectores", organizacionController::misSectores, engineTemplate);


		Spark.get("org/menu/registrarfactoresdeemision", organizacionController::registrarFactoresEmision, engineTemplate);

		Spark.get("org/menu/registrarmediciones", organizacionController::registrarMediciones, engineTemplate);

		Spark.post("org/menu/registrarmedicionesCSV", organizacionController::registrarMedicionesCsv, engineTemplate);
		Spark.post("org/menu/registrarmedicionesFORMULARIO", organizacionController::registrarMedicionesFormulario, engineTemplate);


		Spark.get("org/menu/solicitudesdevinculacion", organizacionController::solicitudVinculacion, engineTemplate);
		Spark.post("org/menu/solicitudesdevinculacion/aceptar/:id_solicitud", organizacionController::aceptarVinculacion,engineTemplate);
		Spark.post("org/menu/solicitudesdevinculacion/rechazar/:id_solicitud", organizacionController::rechazarVinculacion,engineTemplate);



		//REPORTES DE UNA ORGANIZACION
		//TODO HAY QUE CAMBIAR LAS RUTAS A ORG/MENU
		Spark.get("org/menu/menureportes/compohcorg", organizacionController::composicionHCOrg, engineTemplate);
		Spark.get("org/menu/menureportes/compohcorgRESULTADO", organizacionController::resultadoComposicionHCOrg, engineTemplate);

		Spark.get("org/menu/menureportes/evolhcorg", organizacionController::evolucionHC, engineTemplate);
		Spark.get("org/menu/menureportes/evolhcorgRESULTADO", organizacionController::evolucionHCRESULTADO, engineTemplate);






		//------------------------ VISTAS DE USUARIO ---------------------------//

		Spark.get("user/menu", usuarioController::menu, engineTemplate);
		Spark.get("user/home", usuarioController::home, engineTemplate);
		Spark.get("user/menu/calculohc", usuarioController::calculoHC, engineTemplate);
		Spark.get("user/menu/valorcalculohc", usuarioController::valorcalculoHC,engineTemplate); //TODO ES DE PRUEBA
		Spark.get("user/menu/misorganizaciones", usuarioController::misOrganizaciones, engineTemplate);
		Spark.get("user/menu/misTrayectos", usuarioController::misTrayectos, engineTemplate);

		Spark.get("user/menu/registrartramo", usuarioController::registrarTramo, engineTemplate);
		Spark.post("/user/menu/registrartramo/created", usuarioController::cargarTramo,engineTemplate);
		Spark.get("/user/menu/registrartramo/created/menu", usuarioController::registrarTramoExistoso, engineTemplate);

		//Spark.get("/registerorg",homeController::registrarOrg,engineTemplate); // treaemos pantalla ,
		//Spark.get("/registerorg/createorg", homeController::registrarORG);
		//Spark.post("/registerorg/createorg/created", homeController::registrarORG);

		Spark.get("user/menu/registrartrayecto", usuarioController::registrarTrayecto, engineTemplate);//pantalla
		Spark.get("user/menu/registrartrayecto/createdtra", usuarioController::cargarTrayecto);
		Spark.post("user/menu/registrartrayecto/createdtra/created", usuarioController::cargarTrayecto);  //cargarlo en el servidor
		Spark.get("user/menu/registrartrayecto/mistrayectos",usuarioController::misTrayectos,engineTemplate);
		Spark.get("/user/menu/registrartrayecto/misTrayectosRESULTADO",usuarioController::resultadomisTrayectos,engineTemplate);

		Spark.get("user/menu/vincularaorganizacion", usuarioController::vincularOrganizacion, engineTemplate);
		Spark.get("user/menu/vincularasector", usuarioController::vincularASector,engineTemplate);
		Spark.get("user/menu/vincularasector/done", usuarioController::vinculacionExitosa,engineTemplate);


		//------------------------ VISTAS DE AGENTE SECTORIAL ---------------------------//

		Spark.get("ag/menu", agenteSectorialController::menu, engineTemplate);
		Spark.get("ag/home", agenteSectorialController::home, engineTemplate);
		//REPORTES DE UNA AGENTE SECTORIAL

		Spark.get("ag/menu/menureportes/hctotalsectorterritorial", agenteSectorialController::hcTotal, engineTemplate);
		Spark.get("ag/menu/menureportes/hctotalsectorterritorialRESULTADO", agenteSectorialController::hcTotalRESULTADO, engineTemplate);

		Spark.get("ag/menu/menureportes/compohcsectorterritorial", agenteSectorialController::composicionHCSectorTerritorial, engineTemplate);
		Spark.get("ag/menu/menureportes/compohcsectorterritorialRESULTADO", agenteSectorialController::composicionHCSectorTerritorialResultado, engineTemplate);


		Spark.get("ag/menu/menureportes/evolhcsectorterritorial", agenteSectorialController::evolHCSectorTerritorial, engineTemplate);
		Spark.get("ag/menu/menureportes/evolhcsectorterritorialRESULTADO", agenteSectorialController::evolHCSectorTerritorialResultado, engineTemplate);



		//------------------------ VISTAS DE ADMINISTRADOR ---------------------------//

		Spark.get("admin/home", administradorController::menu, engineTemplate);

		//REPORTES DE UNA AGENTE SECTORIAL

		Spark.get("admin/reportes/hctotalPORsectorterritorial", administradorController::hcTotalPorSectorTerritorial, engineTemplate);
		Spark.get("admin/reportes/hctotalPORsectorterritorialRESULTADO", administradorController::hcTotalPorSectorTerritorialRESULTADO, engineTemplate);

		Spark.get("admin/reportes/hctotalPORtipoOrg", administradorController::hcTotalPorTipoOrg, engineTemplate);
		Spark.get("admin/reportes/hctotalPORtipoOrgRESULTADO", administradorController::hcTotalPorTipoOrgRESULTADO, engineTemplate);



	}

}
