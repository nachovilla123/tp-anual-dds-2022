package model;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import javax.persistence.EntityManager;

public class Runner {

  public static void main(String[] args) {
    System.out.println("Hola");

    EntityManager em = PerThreadEntityManagers.getEntityManager();
/*
    //TESTEO DE PERSISTENCIA: CREACION DE INSTANCIAS PARA GUARDAR//

    //------------------------- UBICACIONES  ------------------------- //
    Ubicacion ubicacion1 = new Ubicacion(1, "Pedro Goyena", 2000);
    Ubicacion ubicacion2 = new Ubicacion(1, "Medrano", 123);
    Ubicacion ubicacion3 = new Ubicacion(1, "Mozart", 2700);
    Ubicacion ubicacion4 = new Ubicacion(1, "corrientes", 4500);
    Ubicacion ubicacion5 = new Ubicacion(1, "cordoba", 2500);
    Ubicacion ubicacion6 = new Ubicacion(1, "callao", 2700);
    Ubicacion ubicacion7 = new Ubicacion(1, "Belgrano", 1753);
    Ubicacion ubicacion8 = new Ubicacion(1, "Medrano", 123);
    Ubicacion ubicacion9 = new Ubicacion(1, "armenia", 710);
    Ubicacion ubicacion10 = new Ubicacion(1, "rio de janeiro", 850);

    //--------------------------- ORGANIZACION --------------------------- //

    Organizacion organizacionApple = organizacionAppleEmpresa(ubicacion5);
    Organizacion organizacionMedranoUTN = organizacionMedranoUTN(ubicacion2);
    Organizacion organizacionCampusUTN = organizacionCampusUTN(ubicacion3);

    //------------------------- MEDIO DE TRANSPORTE   ------------------------- //
    List<Parada> listaDeEstacionesFerrocarril = getEstacionesCreadas();
    TransportePublico trenDelAmor = new TransportePublico(TransportePublico.TipoTransporte.FERROCARRIL, "Trendelamor", listaDeEstacionesFerrocarril);


    List<Parada> listaDeEstacionesSubteA = getEstacionesCreadas();
    TransportePublico subteB = new TransportePublico(TransportePublico.TipoTransporte.SUBTE, "subteA", listaDeEstacionesSubteA);


    //------------------------- TRAMOS   ------------------------- //

    // apple
    Tramo tramo_01 = new Tramo(trenDelAmor,ubicacion1,ubicacion2);
    Tramo tramo_02 = new Tramo(trenDelAmor,ubicacion2,ubicacion3);
    Tramo tramo_03 = new Tramo(trenDelAmor,ubicacion3,ubicacion4);

    Tramo tramo_09 = new Tramo(trenDelAmor,ubicacion1,ubicacion4);

    // campus utn
    Tramo tramo_04 = new Tramo(subteB,ubicacion4,ubicacion5);
    Tramo tramo_05 = new Tramo(subteB,ubicacion5,ubicacion6);

    Tramo tramo_06 = new Tramo(subteB, ubicacion6,ubicacion7);

    // medrano utn
    Tramo tramo_07 = new Tramo(subteB,ubicacion7,ubicacion8);

    Tramo tramo_08 = new Tramo(subteB,ubicacion9,ubicacion10);


    //------------------------- TRAYECTOS   ------------------------- //
    // apple
    Trayecto trayecto_01_apple = new Trayecto(ubicacion1,ubicacion4,organizacionApple);

    trayecto_01_apple.agregarTramo(tramo_01);
    trayecto_01_apple.agregarTramo(tramo_02);
    trayecto_01_apple.agregarTramo(tramo_03);

    Trayecto trayecto_02_apple = new Trayecto(ubicacion5,ubicacion6,organizacionCampusUTN);
    trayecto_02_apple.agregarTramo(tramo_09);

    // campus utn
    Trayecto trayecto_03_campus = new Trayecto(ubicacion4,ubicacion6,organizacionCampusUTN);
    trayecto_02_apple.agregarTramo(tramo_04);
    trayecto_02_apple.agregarTramo(tramo_05);

    Trayecto trayecto_04_campus = new Trayecto(ubicacion6,ubicacion7,organizacionCampusUTN);
    trayecto_02_apple.agregarTramo(tramo_06);

    // medrano utn
    Trayecto trayecto_05_medrano = new Trayecto(ubicacion7,ubicacion8,organizacionCampusUTN);
    trayecto_02_apple.agregarTramo(tramo_07);

    Trayecto trayecto_06_medrano = new Trayecto(ubicacion9,ubicacion10,organizacionCampusUTN);
    trayecto_02_apple.agregarTramo(tramo_08);
    //------------------------- MIEMBROS   ------------------------- //

    Miembro ignacio = miembroIgnacio();
    //ignacio.registrarTrayecto(trayecto_01_apple);

    Miembro ruso = miembroAlex();
    ruso.registrarTrayecto(trayecto_02_apple);



    Miembro mateo = miembroMateo();
    mateo.registrarTrayecto(trayecto_03_campus);

    Miembro occhi = miembroNicoO();
    occhi.registrarTrayecto(trayecto_04_campus);

    Miembro pipe = miembroPipe();
    pipe.registrarTrayecto(trayecto_05_medrano);

    Miembro nico = miembroNicoG();
    nico.registrarTrayecto(trayecto_06_medrano);

    //-------------------
    Sector sectorContaduria_apple = new Sector(Sector.SectorDeTrabajo.CONTADURIA);
    sectorContaduria_apple.agregarMiembro(ignacio);

    Sector sectorRRHH_apple = new Sector(Sector.SectorDeTrabajo.RRHH);
    sectorRRHH_apple.agregarMiembro(ruso);

//-------------------
    Sector sectorContaduria_campus = new Sector(Sector.SectorDeTrabajo.CONTADURIA);
    sectorContaduria_campus.agregarMiembro(mateo);

    Sector sectorRRHH_campus= new Sector(Sector.SectorDeTrabajo.RRHH);
    sectorRRHH_campus.agregarMiembro(occhi);
    //.......
    Sector sectorAdministracion_medrano= new Sector(Sector.SectorDeTrabajo.ADMINSTRACION);
    sectorAdministracion_medrano.agregarMiembro(pipe);

    Sector sectorContaduria_medrano = new Sector(Sector.SectorDeTrabajo.CONTADURIA);
    sectorAdministracion_medrano.agregarMiembro(nico);

//-------------------
    organizacionApple.agregarSector(sectorContaduria_apple);
    organizacionApple.agregarSector(sectorRRHH_apple);

    organizacionCampusUTN.agregarSector(sectorContaduria_campus);
    organizacionCampusUTN.agregarSector(sectorRRHH_campus);

    organizacionMedranoUTN.agregarSector(sectorAdministracion_medrano);
    organizacionMedranoUTN.agregarSector(sectorContaduria_medrano);


    // TERMINA ACA ///

    // villa y organizacion que tiene 40 de hc
    Ubicacion ubicacionMedrano =  new Ubicacion(1, "Medrano", 123);
    Organizacion org40hc = new Organizacion("org40", Organizacion.Tipo.EMPRESA_INSTITUCION,ubicacionMedrano, Organizacion.Clasificacion.EMPRESA_SECTOR_SECUNDARIO);;

    List<Parada> estaciones = new ArrayList<>();

    Parada parada_1 = new Parada(10, null, null, ubicacionMedrano);

    Parada parada_2 = new Parada(10, parada_1, null, ubicacion2);

    Parada parada_3 = new Parada(0, parada_2, null, ubicacion3);

    parada_2.setParadaSiguiente(parada_3); // primero seteamos esta porque sino la parada 2 en siguiente tendria NULL
    parada_1.setParadaSiguiente(parada_2);

    estaciones.add(parada_1);
    estaciones.add(parada_2);
    estaciones.add(parada_3);

    List<Parada> listaDeEstacionesFerrocarril1 = estaciones;

    TransportePublico trenPeronista = new TransportePublico(TransportePublico.TipoTransporte.FERROCARRIL, "Trendelamor", listaDeEstacionesFerrocarril1);

    Tramo tramo1_siu = new Tramo(trenPeronista, ubicacion1, ubicacion2);
    Tramo tramo2_siu = new Tramo(trenPeronista, ubicacion2, ubicacion3);

    Trayecto trayecto_siu = new Trayecto(ubicacion1, ubicacionMedrano,org40hc);
    trayecto_siu.agregarTramo(tramo1_siu);
    trayecto_siu.agregarTramo(tramo2_siu);

    Miembro cristianosiuu = new Miembro("KAKA","SIUU",43500500, Miembro.TipoDocumento.DNI);
    cristianosiuu.registrarTrayecto(trayecto_siu);

    Sector rhhSIUUU = new Sector(Sector.SectorDeTrabajo.RRHH);
    rhhSIUUU.agregarMiembro(cristianosiuu);

    org40hc.agregarSector(rhhSIUUU);



    EntityTransaction tx = em.getTransaction();
    tx.begin();
    //em.persist(organizacionApple);
    //em.persist(organizacionCampusUTN);
    //em.persist(organizacionMedranoUTN);
    em.persist(org40hc);
    tx.commit();

 Organizacion organizacion2 = em.find(Organizacion.class,2L);
    Organizacion organizacion3 = em.find(Organizacion.class,3L);
    Sector sector = em.find(Sector.class,1L);
    Miembro don = em.find(Miembro.class,2L);
    System.out.print("EN Base de datos");



    Organizacion org40hcPrueba = em.find(Organizacion.class,1L);

    System.out.print(org40hcPrueba.calcularHc(Periodo.ANUAL));



    Double hcDeRusoEnMemoria = ruso.ejecutarCalculadoraHc(organizacionApple);
    System.out.println(" memoria ");
      System.out.print(hcDeRusoEnMemoria);
    System.out.println(" fin memoria");

    Miembro ruso1 = em.find(Miembro.class,1L);

    System.out.print(" hc de rusito: ");
   System.out.print(ruso1.ejecutarCalculadoraHc(organizacion1).toString());

    System.out.println(" cantidad registros trayecto: ");
    System.out.println(ruso1.getRegistroTrayectos().size());

    System.out.print(organizacion1.getRazonSocial());
    System.out.print(organizacion2.getRazonSocial());
    System.out.print(organizacion3.getRazonSocial());

*/


  }


/*
  public static Miembro miembroIgnacio() {return new Miembro("Ignacio","Villarruel",43500500, Miembro.TipoDocumento.DNI);}
  public static Miembro miembroAlex() {return new Miembro("Alex","Kalinin",43400400, Miembro.TipoDocumento.DNI);}
  public static Miembro miembroMateo() {return new Miembro("Mateo","Silva",43400400, Miembro.TipoDocumento.DNI);}
  public static Miembro miembroNicoG() {return new Miembro("Nico","Galfione",43400400, Miembro.TipoDocumento.DNI);}
  public static Miembro miembroNicoO() {return new Miembro("Nicolas","Occhi",43400400, Miembro.TipoDocumento.DNI);}
  public static Miembro miembroPipe() {return new Miembro("Pipe","Nani",43400400, Miembro.TipoDocumento.DNI);}


  public static List<Parada> getEstacionesCreadas() {
    List<Parada> estaciones = new ArrayList<>();
    Ubicacion ubicacion1 = new Ubicacion(1, "Pedro Goyena", 2000);
    Ubicacion ubicacion2 = new Ubicacion(1, "Medrano", 123);
    Ubicacion ubicacion3 = new Ubicacion(1, "Mozart", 2700);
    Parada parada1 = new Parada(10, null, null, ubicacion1);
    Parada parada2 = new Parada(10, parada1, null, ubicacion2);
    Parada parada3 = new Parada(0, parada2, null, ubicacion3);

    parada2.setParadaSiguiente(parada3); // primero seteamos esta porque sino la parada 2 en siguiente tendria NULL
    parada1.setParadaSiguiente(parada2);

    estaciones.add(parada1);
    estaciones.add(parada2);
    estaciones.add(parada3);
    return estaciones;





  public static Organizacion organizacionAppleEmpresa(Ubicacion ubi){
    return new Organizacion("Apple", Organizacion.Tipo.EMPRESA_INSTITUCION,ubi, Organizacion.Clasificacion.EMPRESA_SECTOR_SECUNDARIO);
  }
  public static Organizacion organizacionMedranoUTN(Ubicacion ubi){
    return new Organizacion("UTN_medrano", Organizacion.Tipo.GUBERNAMENTAL,ubi, Organizacion.Clasificacion.UNIVERSIDAD);
  }

  public static Organizacion organizacionCampusUTN(Ubicacion ubi){
    return new Organizacion("UTN_Campus", Organizacion.Tipo.ONG,ubi, Organizacion.Clasificacion.MINISTERIO);
  }
*/
}
