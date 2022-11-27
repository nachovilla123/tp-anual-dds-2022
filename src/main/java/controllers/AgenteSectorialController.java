package controllers;

import model.agentesSectoriales.AgenteSectorial;
import model.agentesSectoriales.SectorTerritorial;
import model.implementacionCSV.HuellaDeCarbono;
import model.implementacionCSV.Periodo;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AgenteSectorialController implements WithGlobalEntityManager, TransactionalOps{

  //----------------------------------------- MENU-----------------------------------------//
  public ModelAndView menu(Request request, Response response) {
    return new ModelAndView(null, "vistas_agenteSectorial/menuAG.hbs");
  }

  public ModelAndView home(Request request, Response response) {
    return new ModelAndView(null, "vistas_agenteSectorial/agHome.hbs");
  }

  //----------------------------------------- CALCULO HC-----------------------------------------//

  public ModelAndView hcTotal(Request request, Response response) {

    AgenteSectorial agenteSectorial = buscarAGenDB(request);
    SectorTerritorial sectorAsociado = agenteSectorial.getSectorTerritorialAsignado();

    Map<String, Object> model = new HashMap<>();

    model.put("nombreSector",sectorAsociado.getSectorTerritorial());

    return new ModelAndView(model, "vistas_agenteSectorial/HcTotalSectorTerritorial.hbs");
  }
  public ModelAndView hcTotalRESULTADO(Request request, Response response) {

    AgenteSectorial agenteSectorial = buscarAGenDB(request);

    String fecha= request.queryParams("fecha");
    String periodo = request.queryParams("periodo");

    System.out.println("Este es el periodo encontrado: "+ periodo);
    System.out.println("Esta es la fecha encontrada: "+ fecha);

    Periodo periodoElegido = Periodo.valueOf(periodo);

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    //convert String to LocalDate
    LocalDate fechaConvertida = LocalDate.parse(fecha, formatter);

    SectorTerritorial sectorAsociado = agenteSectorial.getSectorTerritorialAsignado();
    Double valorTotal = sectorAsociado.calculoHc(periodoElegido, fechaConvertida);

    Map<String, Object> model = new HashMap<>();

    model.put("valorTotal", valorTotal);
    model.put("nombreSector",sectorAsociado.getSectorTerritorial());


    return new ModelAndView(model, "vistas_agenteSectorial/HcTotalSectorTerritorialRESULTADO.hbs");
  }

  //----------------------------------------- REPORTES-----------------------------------------//
  private AgenteSectorial buscarAGenDB(Request request) {
    return RepositorioAgenteSectorial.getInstance().findByUsername(request.session().attribute("nombre")).orElse(null);
  }

  public ModelAndView composicionHCSectorTerritorial(Request request, Response response) {

    AgenteSectorial agenteSectorial = buscarAGenDB(request);
    SectorTerritorial sectorTerritorial = agenteSectorial.sectorTerritorialAsignado;

    Map<String, Object> model = new HashMap<>();
    model.put("nombreSector", sectorTerritorial.getSectorTerritorial());

    return new ModelAndView(model, "vistas_agenteSectorial/composicion/composicion_hc_sector_territorial.hbs");
  }

  public ModelAndView composicionHCSectorTerritorialResultado(Request request, Response response) {

    AgenteSectorial agenteSectorial = buscarAGenDB(request);
    SectorTerritorial sectorTerritorial = agenteSectorial.sectorTerritorialAsignado;

    String fecha= request.queryParams("fecha");
    String periodo = request.queryParams("periodo");

    System.out.println("Este es el periodo encontrado: "+ periodo);
    System.out.println("Esta es la fecha encontrada: "+ fecha);

    Periodo periodoElegido = Periodo.valueOf(periodo);

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    //convert String to LocalDate
    LocalDate fechaConvertida = LocalDate.parse(fecha, formatter);

    List<Reporte> reportes =sectorTerritorial.obtenerOrganizaciones().stream()
        .flatMap(organizacion -> organizacion.obtenerReportes(fechaConvertida,periodoElegido).stream()).collect(Collectors.toList());

    Double valorTramosHc = reportes.stream().mapToDouble(reporte->reporte.getActividadesGenerales().valorEnEnKilogramos()).sum();
    Double valorDAHc =  reportes.stream().mapToDouble(reporte->reporte.getTramos().valorEnEnKilogramos()).sum();


    System.out.println("FECHA DE REPORTE:"+periodoElegido.toDateTimeString(fechaConvertida));


    Map<String, Object> model = new HashMap<>();
    model.put("valorDeTramos", valorTramosHc);
    model.put("valorDA",valorDAHc);
    model.put("fechaReporte",periodoElegido.toDateTimeString(fechaConvertida));



    return new ModelAndView(model, "vistas_agenteSectorial/composicion/composicion_hc_sector_territorial_RESULTADO.hbs");
  }

  public ModelAndView evolHCSectorTerritorial(Request request, Response response) {

    AgenteSectorial agenteSectorial = buscarAGenDB(request);
    SectorTerritorial sectorTerritorial = agenteSectorial.sectorTerritorialAsignado;

    Map<String, Object> model = new HashMap<>();
    model.put("nombreSector", sectorTerritorial.getSectorTerritorial());


    return new ModelAndView(model, "vistas_agenteSectorial/evolucion/evol_hc_sector_territorial.hbs");
  }

  public ModelAndView evolHCSectorTerritorialResultado(Request request, Response response) {

    AgenteSectorial agenteSectorial = buscarAGenDB(request);
    SectorTerritorial sectorTerritorial = agenteSectorial.sectorTerritorialAsignado;

    String periodo = request.queryParams("periodo");

    Periodo periodoElegido = Periodo.valueOf(periodo);

    List<Reporte> reportesDelPeriodo =sectorTerritorial.obtenerOrganizaciones().stream()
        .flatMap(organizacion -> organizacion.obtenerReportes(periodoElegido).stream()).collect(Collectors.toList());

    //TODO
/*
    Set<Set<Reporte>> reportesUnificadosPorFecha = reportesDelPeriodo.stream()
                                                 .map(reporte -> reportesDelPeriodo.stream()
                                                  .filter(reporte1 -> reporte1.sonDeLaMismaFecha(reporte.getFecha())).collect(Collectors.toSet()))
                                                    .collect(Collectors.toSet());

    Set<Reporte> reportesFinalesPorFecha = reportesUnificadosPorFecha.stream().
                                          flatMap(reportes -> reportes.stream()).
                                              reduce((reporte, reporte2) ->
                                                  new Reporte(
                                                      new HuellaDeCarbono(reporte.getTramos().valorEnEnKilogramos()+ reporte2.getTramos().valorEnEnKilogramos()),
                                                      new HuellaDeCarbono(reporte.getActividadesGenerales().valorEnEnKilogramos() +reporte2.getActividadesGenerales().valorEnEnKilogramos())
                                                      ,reporte.getPeriodo(), reporte.getFecha() )).get()
                                                  .collect(Collectors.toSet());
*/

    Map<String, Object> model = new HashMap<>();
    model.put("reportes", reportesDelPeriodo);
    model.put("periodo", periodo);

    return new ModelAndView(model, "vistas_agenteSectorial/evolucion/evol_hc_sector_territorial_RESULTADO.hbs");
  }



}
