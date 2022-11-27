import builders.*;

import model.agentesSectoriales.AgenteSectorial;
import model.agentesSectoriales.SectorTerritorial;
import model.implementacionCSV.DatoDeActividad;
import model.implementacionCSV.FactorDeEmision;
import model.implementacionCSV.Periodo;
import model.implementacionCSV.tipoDeConsumo.TipoDeConsumo;
import model.organizacion.*;
import model.organizacion.trayecto.Trayecto;
import model.reporte.Reporte;
import model.repositorios.repositoriosDBs.RepositorioAgenteSectorial;

import model.repositorios.repositoriosDBs.RepositorioFactorDeEmision;
import model.repositorios.repositoriosDBs.RepositorioPersona;
import model.repositorios.repositoriosDBs.RepositorioSectorTerritorial;
import model.repositorios.repositoriosDBs.reposUsuarios.RepositorioUsuario;
import model.usuario.*;

import java.time.LocalDate;
import java.util.*;

public class Bootstrap {


  public static void init() {
    // List<Usuario> usuarios = usuarios();
    // usuarios.forEach((usuario) -> RepositorioUsuario.getInstance().agregar(usuario));
    //  organizaciones().forEach((organizacion -> RepositorioDeOrganizaciones.getInstance().agregar(organizacion)));
    factoresDeEmision();
    sectorTerritorialBsAs();
    nicoAdministrador();

  }

  private static void nicoAdministrador() {
    UserAdministrador nicolas = new UserAdministrador("nico","zxc", Usuario.Rol.ADMINISTRADOR);

    RepositorioUsuario.getInstance().agregarBoostrap(nicolas);
  }

  private static void factoresDeEmision() {
    FactorDeEmision factorDeEmision1 = new FactorDeEmision(1.0,TipoDeConsumo.DIESEL_GASOIL );
    FactorDeEmision factorDeEmision2 = new FactorDeEmision(2.0,TipoDeConsumo.CAMION_DE_CARGA );
    FactorDeEmision factorDeEmision3 = new FactorDeEmision(3.0,TipoDeConsumo.COMBUSTIBLE_CONSUMIDO_GASOIL);
    FactorDeEmision factorDeEmision4 = new FactorDeEmision(4.0,TipoDeConsumo.COMBUSTIBLE_CONSUMIDO_NAFTA);
    FactorDeEmision factorDeEmision5 = new FactorDeEmision(5.0,TipoDeConsumo.GAS_NATURAL);
    FactorDeEmision factorDeEmision6 = new FactorDeEmision(6.0,TipoDeConsumo.UTILITARIO_LIVIANO);
    FactorDeEmision factorDeEmision7 = new FactorDeEmision(7.0,TipoDeConsumo.ELECTRICIDAD);
    FactorDeEmision factorDeEmision8 = new FactorDeEmision(8.0,TipoDeConsumo.DISTANCIA_MEDIA_RECORRIDA);
    FactorDeEmision factorDeEmision9 = new FactorDeEmision(9.0,TipoDeConsumo.NAFTA);


    RepositorioFactorDeEmision.getInstance().agregarBoostrap(factorDeEmision1);
    RepositorioFactorDeEmision.getInstance().agregarBoostrap(factorDeEmision2);
    RepositorioFactorDeEmision.getInstance().agregarBoostrap(factorDeEmision3);
    RepositorioFactorDeEmision.getInstance().agregarBoostrap(factorDeEmision4);
    RepositorioFactorDeEmision.getInstance().agregarBoostrap(factorDeEmision5);
    RepositorioFactorDeEmision.getInstance().agregarBoostrap(factorDeEmision6);
    RepositorioFactorDeEmision.getInstance().agregarBoostrap(factorDeEmision7);
    RepositorioFactorDeEmision.getInstance().agregarBoostrap(factorDeEmision8);
    RepositorioFactorDeEmision.getInstance().agregarBoostrap(factorDeEmision9);

  }

  private static void sectorTerritorialBsAs() {

    //SECTOR TERRITORIAL
    SectorTerritorial sectorTerritorial = new SectorTerritorial("Buenos_Aires");

    //CREACION DE ORGANIZACIONES
    Set<Organizacion> organizaciones = new HashSet<>();

    //ORGANIZACION APPLE
    Organizacion orgApple = organizacionCompletaApple();
    organizaciones.add(orgApple);

    //ORGANIZACION UTN
    Organizacion orgUTN = organizacionUTN();
    organizaciones.add(orgUTN);

    sectorTerritorial.setOrganizaciones(organizaciones);


    //PERSISENTENCIA DEL SECTOR TERRITORIAL
    RepositorioSectorTerritorial.getInstance().agregarBoostrap(sectorTerritorial);


    AgenteSectorial agenteSectorial = new AgenteSectorial("Alex",sectorTerritorial);
    RepositorioAgenteSectorial.getInstance().agregarBoostrap(agenteSectorial);


    //CREACION DE USUARIOS
    Persona personaIgnacio = RepositorioPersona.getInstance().findByPersonName("ignacio");
    Persona personaPipe = RepositorioPersona.getInstance().findByPersonName("pipe");


    usuariosApple(personaIgnacio,orgApple);
    usuriosUTN(personaPipe,orgUTN);

    usuarioAgenteSectorialBsAs(agenteSectorial);
  }

  private static void usuriosUTN(Persona personaPipe, Organizacion orgUTN) {
    UserMiembro userPipe = new UserMiembro("Felipe", "zxc", Usuario.Rol.MIEMBRO);
    UserOrganizacion userNicolas = new UserOrganizacion("Nicolas", "123", Usuario.Rol.ORGANIZACION);

    userPipe.setPersona_asociada(personaPipe);
    userNicolas.setOrganizacion_asociada(orgUTN);

    RepositorioUsuario.getInstance().agregarBoostrap(userPipe);
    RepositorioUsuario.getInstance().agregarBoostrap(userNicolas);

  }


  private static void usuariosApple(Persona persona, Organizacion org) {
    UserMiembro userignacio = new UserMiembro("Ignacio", "123", Usuario.Rol.MIEMBRO);
    UserOrganizacion usermateo = new UserOrganizacion("Mateo", "qwe", Usuario.Rol.ORGANIZACION);

    userignacio.setPersona_asociada(persona);
    usermateo.setOrganizacion_asociada(org);

    RepositorioUsuario.getInstance().agregarBoostrap(userignacio);
    RepositorioUsuario.getInstance().agregarBoostrap(usermateo);
    //userignacio.settearPersona(miembroIgnacio.getPersona());
    // usermateo.settearOrganizacion(org);
  }

  private static void usuarioAgenteSectorialBsAs(AgenteSectorial agenteSectorial) {
    UserAgenteSectorial userAlex = new UserAgenteSectorial("Alex", "asd", Usuario.Rol.AGENTE_SECTORIAL);
    userAlex.setAgenteSectorial(agenteSectorial);

    RepositorioUsuario.getInstance().agregarBoostrap(userAlex);
  }
  private static Organizacion organizacionUTN(){
    OrganizacionFactory organizacionFactory = new OrganizacionFactory();
    SectorFactory sectorFactory = new SectorFactory();
    MiembroFactory miembroFactory = new MiembroFactory();
    TrayectoFactory trayectoFactory = new TrayectoFactory();
    PersonaFactory personaFactory = new PersonaFactory();
    TramoFactory tramoFactory =  new TramoFactory();

    //ORANIZACION
    Organizacion orgUTN = organizacionFactory.organizacionUTN();
    orgUTN.setSrcImg("../../imagenes/icons_buildings/edificio.png");

    //SECTORES
    Sector sectoreCont = sectorFactory.SectorContaduria();
    Sector sectorRRHH = sectorFactory.SectorRRHH();

    //SETT DE SECTORES A ORGANIZACION
    orgUTN.agregarSector(sectoreCont);
    orgUTN.agregarSector(sectorRRHH);

    //MIEMBROS
    Miembro miembroPipe = miembroFactory.miembroPipe();


    //TRAYECTOS
    Trayecto trayeco60hcPipe = trayectoFactory.untrayectoCon3tramos60hc();


    //SETTEO DE IGNACIO
    Persona pipe = personaFactory.personaPipe();

    pipe.agregarTramoNoRegistrado(tramoFactory.tramoConHCde20());


    Set membresiasDePipe = new HashSet<>();
    membresiasDePipe.add(miembroPipe);
    pipe.setTrabajos(membresiasDePipe);

    miembroPipe.setTrabajo(sectoreCont);
    miembroPipe.setPersona(pipe);
    miembroPipe.registrarTrayecto(trayeco60hcPipe);
    miembroPipe.setSrcImg("../../imagenes/personas/persona3.png");
    sectoreCont.agregarTrabajador(miembroPipe);



    //DATOS DE ACTIVIDAD

    DatoDeActividad datoDeActividad = new DatoDeActividad(new FactorDeEmision(800.0,TipoDeConsumo.DIESEL_GASOIL),2.0, Periodo.ANUAL,LocalDate.of(2017,2,3));


    System.out.println("HC DATO DE ACTIVIDAD"+ datoDeActividad.calcularHC());
    List<DatoDeActividad> datosDeActividad = new ArrayList<>();
    datosDeActividad.add(datoDeActividad);
    orgUTN.setDatosDeActividades(datosDeActividad);

    //REPORTES
    List<Reporte> reportesApple = reportesUTN();
    orgUTN.setReportes(reportesApple);

    return orgUTN;
  }


  private static Organizacion organizacionCompletaApple() {
    OrganizacionFactory organizacionFactory = new OrganizacionFactory();
    TrayectoFactory trayectoFactory = new TrayectoFactory();
    MiembroFactory miembroFactory = new MiembroFactory();
    SectorFactory sectorFactory = new SectorFactory();
    PersonaFactory personaFactory = new PersonaFactory();
    TramoFactory tramoFactory = new TramoFactory();

    //ORANIZACION
    Organizacion org = organizacionFactory.organizacionAppleEmpresa();
    org.setSrcImg("../../imagenes/icons_buildings/5452468_holidays_buildings_hotel_vacations.png");

    //CONTACTO
    org.setContactos(contactosApple());

    //SECTORES
    Sector sectorRRHH = sectorFactory.SectorRRHH();
    Sector sectorAdministracion = sectorFactory.SectorAdministracion();
    sectorRRHH.setOrganizacion(org);
    sectorAdministracion.setOrganizacion(org);

    //SETT DE SECTORES A ORGANIZACION
    org.agregarSector(sectorRRHH);
    org.agregarSector(sectorAdministracion);

    //MIEMBROS
    Miembro miembroIgnacio = miembroFactory.miembroIgnacio();
    Miembro miembroMateo = miembroFactory.miembroMateo();

    //TRAYECTOS
    Trayecto trayeco60hcIgnacio = trayectoFactory.untrayectoCon3tramos60hc();
    Trayecto trayeco60hcMateo = trayectoFactory.untrayectoCon3tramos60hc();

    //SETTEO DE IGNACIO
    Persona ignacio = personaFactory.personaIgnacio();

    ignacio.agregarTramoNoRegistrado(tramoFactory.tramoConHCde20());
    ignacio.agregarTramoNoRegistrado(tramoFactory.tramoConHCde20());

    Set membresiasDeIgnacio = new HashSet<>();
    membresiasDeIgnacio.add(miembroIgnacio);
    ignacio.setTrabajos(membresiasDeIgnacio);

    miembroIgnacio.setTrabajo(sectorRRHH);
    miembroIgnacio.setPersona(ignacio);
    miembroIgnacio.registrarTrayecto(trayeco60hcIgnacio);
    miembroIgnacio.setSrcImg("../../imagenes/personas/persona2.png");

    sectorRRHH.agregarTrabajador(miembroIgnacio);
    sectorAdministracion.agregarTrabajador(miembroMateo);

    //SETTEO DE MATEO
    Persona mateo = personaFactory.personaMateo();

    Set membresiasDeMateo = new HashSet<>();
    membresiasDeMateo.add(miembroMateo);
    mateo.setTrabajos(membresiasDeMateo);

    miembroMateo.setTrabajo(sectorAdministracion);
    miembroMateo.setPersona(mateo);
    miembroMateo.registrarTrayecto(trayeco60hcMateo);
    miembroMateo.setSrcImg("../../imagenes/personas/persona4.png");


    //DATOS DE ACTIVIDAD
    DatoDeActividad datoDeActividad = new DatoDeActividad(new FactorDeEmision(500.0,TipoDeConsumo.COMBUSTIBLE_CONSUMIDO_NAFTA),5.0, Periodo.ANUAL,LocalDate.of(2017,2,3));


    System.out.println("HC DATO DE ACTIVIDAD"+ datoDeActividad.calcularHC());
    List<DatoDeActividad> datosDeActividad = new ArrayList<>();
    datosDeActividad.add(datoDeActividad);
    org.setDatosDeActividades(datosDeActividad);

    //REPORTES
    List<Reporte> reportesApple = reportesApple();
    org.setReportes(reportesApple);

    //PERSISTENCIA DE APPLE
    // RepositorioDeOrganizaciones.getInstance().agregarBoostrap(org);
    // usuariosApple(miembroIgnacio, org);
    //

    return  org;


  }

private static  Set<Contacto> contactosApple(){
  Contacto jorge = new Contacto("jorge@gmail.com.ar",1132658788, "Jorge");
  Contacto matias = new Contacto("matias@gmail.com.ar",1123695736, "Matias");
  Contacto pedro = new Contacto("pedro@gmail.com.ar", 1142874934, "Pedro");
  Set<Contacto> contactosApple = new HashSet<>();
  contactosApple.add(jorge);
  contactosApple.add(matias);
  contactosApple.add(pedro);
  return contactosApple;
}

  private static List<Reporte> reportesApple() {
    ReporteFactory reporteFactory = new ReporteFactory();

    //ANUALES
    Reporte reporteAanual = reporteFactory.reporteAanual();
    reporteAanual.setFecha( LocalDate.of(2018,1,2));
    reporteAanual.setValorTotal(reporteAanual.getValorTotalHc());

    Reporte reporteBanual =reporteFactory.reporteBanual();
    reporteBanual.setFecha( LocalDate.of(2019,1,2));
    reporteBanual.setValorTotal(reporteBanual.getValorTotalHc());

    Reporte reporteCanual = reporteFactory.reporteCanual();
    reporteCanual.setFecha( LocalDate.of(2020,1,2));
    reporteCanual.setValorTotal(reporteCanual.getValorTotalHc());

    Reporte reporteDanual = reporteFactory.reporteDanual();
    reporteDanual.setFecha( LocalDate.of(2021,1,2));
    reporteDanual.setValorTotal(reporteDanual.getValorTotalHc());

    Reporte reporteEanual = reporteFactory.reporteEanual();
    reporteEanual.setFecha( LocalDate.of(2022,1,2));
    reporteEanual.setValorTotal(reporteEanual.getValorTotalHc());

    //MENSUALES
    Reporte reporteAmensual = reporteFactory.reporteAmensual();
    reporteAmensual.setFecha( LocalDate.of(2018,6,2));
    reporteAmensual.setValorTotal(reporteAanual.getValorTotalHc());

    Reporte reporteBmensual =reporteFactory.reporteBmensual();
    reporteBmensual.setFecha( LocalDate.of(2019,6,2));
    reporteBmensual.setValorTotal(reporteBanual.getValorTotalHc());

    Reporte reporteCmensual = reporteFactory.reporteCmensual();
    reporteCmensual.setFecha( LocalDate.of(2020,6,2));
    reporteCmensual.setValorTotal(reporteCanual.getValorTotalHc());

    Reporte reporteDmensual = reporteFactory.reporteDmensual();
    reporteDmensual.setFecha( LocalDate.of(2021,6,2));
    reporteDmensual.setValorTotal(reporteDanual.getValorTotalHc());

    Reporte reporteEmensual = reporteFactory.reporteEmensual();
    reporteEmensual.setFecha( LocalDate.of(2022,6,2));
    reporteEmensual.setValorTotal(reporteEanual.getValorTotalHc());


    List<Reporte> reportes = new ArrayList<>();
    reportes.add(reporteAanual);
    reportes.add(reporteAmensual);

    reportes.add(reporteBanual);
    reportes.add(reporteBmensual);

    reportes.add(reporteCanual);
    reportes.add(reporteCmensual);

    reportes.add(reporteDanual);
    reportes.add(reporteDmensual);

    reportes.add(reporteEanual);
    reportes.add(reporteEmensual);

    return reportes;
  }

  private static List<Reporte> reportesUTN() {

    ReporteFactory reporteFactory = new ReporteFactory();

    Reporte reporteAanual = reporteFactory.reporteAanual();
    reporteAanual.setFecha( LocalDate.of(2018,2,3));
    reporteAanual.setValorTotal(reporteAanual.getValorTotalHc());

    Reporte reporteBanual =reporteFactory.reporteBanual();
    reporteBanual.setFecha( LocalDate.of(2019,2,3));
    reporteBanual.setValorTotal(reporteBanual.getValorTotalHc());

    Reporte reporteCanual = reporteFactory.reporteCanual();
    reporteCanual.setFecha( LocalDate.of(2020,2,3));
    reporteCanual.setValorTotal(reporteCanual.getValorTotalHc());

    Reporte reporteDanual = reporteFactory.reporteDanual();
    reporteDanual.setFecha( LocalDate.of(2021,2,3));
    reporteDanual.setValorTotal(reporteDanual.getValorTotalHc());

    Reporte reporteEanual = reporteFactory.reporteEanual();
    reporteEanual.setFecha( LocalDate.of(2022,2,3));
    reporteEanual.setValorTotal(reporteEanual.getValorTotalHc());

    //MENSUALES
    Reporte reporteAmensual = reporteFactory.reporteAmensual();
    reporteAmensual.setFecha( LocalDate.of(2018,6,2));
    reporteAmensual.setValorTotal(reporteAanual.getValorTotalHc());

    Reporte reporteBmensual =reporteFactory.reporteBmensual();
    reporteBmensual.setFecha( LocalDate.of(2019,6,2));
    reporteBmensual.setValorTotal(reporteBanual.getValorTotalHc());

    Reporte reporteCmensual = reporteFactory.reporteCmensual();
    reporteCmensual.setFecha( LocalDate.of(2020,6,2));
    reporteCmensual.setValorTotal(reporteCanual.getValorTotalHc());

    Reporte reporteDmensual = reporteFactory.reporteDmensual();
    reporteDmensual.setFecha( LocalDate.of(2021,6,2));
    reporteDmensual.setValorTotal(reporteDanual.getValorTotalHc());

    Reporte reporteEmensual = reporteFactory.reporteEmensual();
    reporteEmensual.setFecha( LocalDate.of(2022,6,2));
    reporteEmensual.setValorTotal(reporteEanual.getValorTotalHc());


    List<Reporte> reportes = new ArrayList<>();
    reportes.add(reporteAanual);
    reportes.add(reporteAmensual);

    reportes.add(reporteBanual);
    reportes.add(reporteBmensual);

    reportes.add(reporteCanual);
    reportes.add(reporteCmensual);

    reportes.add(reporteDanual);
    reportes.add(reporteDmensual);

    reportes.add(reporteEanual);
    reportes.add(reporteEmensual);

    return reportes;
  }


}
