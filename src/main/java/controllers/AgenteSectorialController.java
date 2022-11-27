package controllers;

import model.agentesSectoriales.AgenteSectorial;
import model.agentesSectoriales.SectorTerritorial;
import model.organizacion.Organizacion;
import model.reporte.Reporte;
import model.repositorios.repositoriosDBs.RepositorioAgenteSectorial;
import model.repositorios.repositoriosDBs.RepositorioDeOrganizaciones;
import model.repositorios.repositoriosDBs.RepositorioReporte;
import model.repositorios.repositoriosDBs.RepositorioSectorTerritorial;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AgenteSectorialController implements WithGlobalEntityManager, TransactionalOps{

  //----------------------------------------- MENU-----------------------------------------//
  public ModelAndView menu(Request request, Response response) {
    return new ModelAndView(null, "vistas_agenteSectorial/menuAG.hbs");
  }

  //----------------------------------------- CALCULO HC-----------------------------------------//

  public ModelAndView hcTotalPorSectorTerritorial(Request request, Response response) {

    AgenteSectorial agenteSectorial = buscarAGenDB(request);
    System.out.println(agenteSectorial.getNombre());

    SectorTerritorial sectorAsociado = agenteSectorial.getSectorTerritorialAsignado();

    //SectorTerritorial sectorAsociado = RepositorioSectorTerritorial.getInstance().encontrar(1L);

    List<Organizacion> orgs =RepositorioDeOrganizaciones.getInstance().getOrganizacionesBySectorTerritorial(sectorAsociado.getId());
    List<Reporte> ultimosReportes = orgs.stream().map(organizacion -> organizacion.verUltimoReporte()).collect(Collectors.toList());

    Double valorTotal = ultimosReportes.stream().mapToDouble(reporte->reporte.getValorTotal()).sum();
    Map<String, Object> model = new HashMap<>();

    model.put("valorTotal", valorTotal);
    model.put("nombreSector",sectorAsociado.getSectorTerritorial());


    return new ModelAndView(model, "vistas_agenteSectorial/HcTotalPorSectorTerritorial.hbs");
  }

  //----------------------------------------- REPORTES-----------------------------------------//
  private AgenteSectorial buscarAGenDB(Request request) {
    return RepositorioAgenteSectorial.getInstance().findByUsername(request.session().attribute("nombre")).orElse(null);
  }

  public ModelAndView composicionHCSectorTerritorial(Request request, Response response) {
    AgenteSectorial agenteSectorial = buscarAGenDB(request);

    return new ModelAndView(agenteSectorial.composicionHc(), "vistas_agenteSectorial/composicion_hc_sector_territorial.hbs");
  }


  public ModelAndView evolHCSectorTerritorial(Request request, Response response) {

    AgenteSectorial agenteSectorial = buscarAGenDB(request);

    Map<String, Object> composicionHc = new HashMap<>();
    composicionHc.put("composicionHc", agenteSectorial.obtenerReportesEvolucion());

    return new ModelAndView(composicionHc, "vistas_agenteSectorial/evol_hc_sector_territorial.hbs");
  }


}
