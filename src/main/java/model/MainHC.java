package model;

public class MainHC {
/*
  public static void main(String[] args) {

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

    TransportePublico trenPeronista = new TransportePublico(TransportePublico.TipoTransporte.FERROCARRIL, "Trendelamor", listaDeEstacionesFerrocarril1, TipoDeConsumo.DIESEL_GASOIL);

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

    System.out.print("EN MEMORIA");
    System.out.println(org40hc.calcularHc(Periodo.ANUAL));
    System.out.print("EN MEMORIA");
  }*/
}
