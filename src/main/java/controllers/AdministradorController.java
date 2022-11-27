package controllers;

import model.agentesSectoriales.SectorTerritorial;
import model.implementacionCSV.Periodo;
import model.organizacion.Organizacion;
import model.repositorios.repositoriosDBs.RepositorioDeOrganizaciones;
import model.repositorios.repositoriosDBs.RepositorioSectorTerritorial;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AdministradorController {


  public ModelAndView menu(Request request, Response response) {

    return new ModelAndView(null, "vistas_administrador/menuAdministrador.hbs");
  }

  public ModelAndView hcTotalPorTipoOrg(Request request, Response response) {


    return new ModelAndView(null, "vistas_administrador/hcPorTipoOrg/hcTotalPorTipoOrg.hbs");
  }

  public ModelAndView hcTotalPorTipoOrgRESULTADO(Request request, Response response) {

    Organizacion.Tipo tipoOrg = Organizacion.Tipo.valueOf(request.queryParams("tipo_org"));
    System.out.println("El tipo de la organizacion es: "+ tipoOrg);
    Periodo periodo = Periodo.valueOf(request.queryParams("periodo"));

    String fecha = request.queryParams("fecha");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    //convert String to LocalDate
    LocalDate fechaConvertida = LocalDate.parse(fecha, formatter);


    List<Organizacion> organizaciones = RepositorioDeOrganizaciones.getInstance().buscarTodos().stream().filter(organizacion -> organizacion.getTipoDeOrganizacion() == tipoOrg).collect(Collectors.toList());

    System.out.println(organizaciones);
    Double resultadoHc = organizaciones.stream().mapToDouble(org->org.calcularHc(periodo,fechaConvertida)).sum();

    Map<String, Object> model = new HashMap<>();
    model.put("resultado", resultadoHc);
    model.put("tipo", tipoOrg);

    return new ModelAndView(model, "vistas_administrador/hcPorTipoOrg/hcTotalPorTipoOrgRESULTADO.hbs");

  }

  public ModelAndView hcTotalPorSectorTerritorial(Request request, Response response) {

    List<SectorTerritorial> sectoresTerritoriales = RepositorioSectorTerritorial.getInstance().buscarTodos();

    Map<String, Object> model = new HashMap<>();
    model.put("sectores", sectoresTerritoriales);

    return new ModelAndView(model, "vistas_administrador/hcPorSectorTerritorial/hcTotalPorSectorTerritorial.hbs");
  }

  public ModelAndView hcTotalPorSectorTerritorialRESULTADO(Request request, Response response) {

    Periodo periodo = Periodo.valueOf(request.queryParams("periodo"));
    String fecha = request.queryParams("fecha");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    //convert String to LocalDate
    LocalDate fechaConvertida = LocalDate.parse(fecha, formatter);

    SectorTerritorial sectorTerritorial = RepositorioSectorTerritorial.getInstance().findBySectorName(request.queryParams("nombre_sector"));

    System.out.println(sectorTerritorial);
    Double resultadoHc = sectorTerritorial.calculoHc(periodo,fechaConvertida);

    Map<String, Object> model = new HashMap<>();
    model.put("valorTotal", resultadoHc);
    model.put("nombreSector", sectorTerritorial.getSectorTerritorial());

    return new ModelAndView(model, "vistas_administrador/hcPorSectorTerritorial/hcTotalPorSectorTerritorialRESULTADO.hbs");

  }


}
