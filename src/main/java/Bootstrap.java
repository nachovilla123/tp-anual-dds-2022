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

import model.repositorios.repositoriosDBs.RepositorioPersona;
import model.repositorios.repositoriosDBs.RepositorioSectorTerritorial;
import model.repositorios.repositoriosDBs.reposUsuarios.RepositorioUsuario;
import model.usuario.UserAgenteSectorial;
import model.usuario.UserMiembro;
import model.usuario.UserOrganizacion;
import model.usuario.Usuario;

import java.time.LocalDate;
import java.util.*;

public class Bootstrap {


  public static void init() {
    // List<Usuario> usuarios = usuarios();
    // usuarios.forEach((usuario) -> RepositorioUsuario.getInstance().agregar(usuario));
    //  organizaciones().forEach((organizacion -> RepositorioDeOrganizaciones.getInstance().agregar(organizacion)));
    sectorTerritorialBsAs();


  }

  private static void sectorTerritorialBsAs() {

    //SECTOR TERRITORIAL
    SectorTerritorial sectorTerritorial = new SectorTerritorial("Buenos Aires");

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
    Persona personaIgnacio = RepositorioPersona.getInstance().findByUsername("ignacio");
    Persona personaPipe = RepositorioPersona.getInstance().findByUsername("pipe");


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
    Set<Reporte> reportesApple = reportesUTN();
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
    Set<Reporte> reportesApple = reportesApple();
    org.setReportes(reportesApple);

    //PERSISTENCIA DE APPLE
    // RepositorioDeOrganizaciones.getInstance().agregarBoostrap(org);
    // usuariosApple(miembroIgnacio, org);
    //

    return  org;


  }



  private static Set<Reporte> reportesApple() {
    ReporteFactory reporteFactory = new ReporteFactory();

    Reporte reporteA = reporteFactory.reporteA();
    reporteA.setFecha( LocalDate.of(2018,1,2));
    reporteA.setValorTotal(reporteA.getValorTotalHc());

    Reporte reporteB =reporteFactory.reporteB();
    reporteB.setFecha( LocalDate.of(2019,1,2));
    reporteB.setValorTotal(reporteB.getValorTotalHc());

    Reporte reporteC = reporteFactory.reporteC();
    reporteC.setFecha( LocalDate.of(2020,1,2));
    reporteC.setValorTotal(reporteC.getValorTotalHc());

    Reporte reporteD = reporteFactory.reporteD();
    reporteD.setFecha( LocalDate.of(2021,1,2));
    reporteD.setValorTotal(reporteD.getValorTotalHc());

    Reporte reporteE = reporteFactory.reporteE();
    reporteE.setFecha( LocalDate.of(2022,1,2));
    reporteE.setValorTotal(reporteE.getValorTotalHc());

    Set<Reporte> reportes = new HashSet<>();
    reportes.add(reporteA);
    reportes.add(reporteB);
    reportes.add(reporteC);
    reportes.add(reporteD);
    reportes.add(reporteE);

    return reportes;
  }

  private static Set<Reporte> reportesUTN() {

    ReporteFactory reporteFactory = new ReporteFactory();

    Reporte reporteA = reporteFactory.reporteA();
    reporteA.setFecha( LocalDate.of(2018,2,3));
    reporteA.setValorTotal(reporteA.getValorTotalHc());

    Reporte reporteB =reporteFactory.reporteB();
    reporteB.setFecha( LocalDate.of(2019,2,3));
    reporteB.setValorTotal(reporteB.getValorTotalHc());

    Reporte reporteC = reporteFactory.reporteC();
    reporteC.setFecha( LocalDate.of(2020,2,3));
    reporteC.setValorTotal(reporteC.getValorTotalHc());

    Reporte reporteD = reporteFactory.reporteD();
    reporteD.setFecha( LocalDate.of(2021,2,3));
    reporteD.setValorTotal(reporteD.getValorTotalHc());

    Reporte reporteE = reporteFactory.reporteE();
    reporteE.setFecha( LocalDate.of(2022,2,3));
    reporteE.setValorTotal(reporteE.getValorTotalHc());

    Set<Reporte> reportes = new HashSet<>();
    reportes.add(reporteA);
    reportes.add(reporteB);
    reportes.add(reporteC);
    reportes.add(reporteD);
    reportes.add(reporteE);

    return reportes;
  }


}
