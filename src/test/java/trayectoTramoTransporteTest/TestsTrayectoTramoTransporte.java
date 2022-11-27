package trayectoTramoTransporteTest;


//------------------------------------- comienzo tests ---------------------------------//

public class TestsTrayectoTramoTransporte {
/*

  //------------------- UBICACIONES ---------------------
  Ubicacion ubicacion1 = new Ubicacion(1, "Pedro Goyena", 2000);
  Ubicacion ubicacion2 = new Ubicacion(1, "Medrano", 123);
  Ubicacion ubicacion3 = new Ubicacion(1, "Mozart", 2700);
  Ubicacion ubicacionMedrano = new Ubicacion(1, "Medrano", 123);


  // INSTANCIANDO COSAS PARA QUE NO ROMPA 13/09
  public  Organizacion organizacionAppleEmpresa(){
    return new Organizacion("Apple", Organizacion.Tipo.EMPRESA_INSTITUCION,ubicacionMedrano, Organizacion.Clasificacion.EMPRESA_SECTOR_SECUNDARIO);
  }


  @Test  //Se debe permitir el alta de trayectos teniendo en cuenta que cada uno de éstos puede contener varios tramos.
  public void altaDeTrayectoConUnTramoDeServicioContratado() {
    Organizacion org = this.organizacionAppleEmpresa();
    ServicioContratado servicioTest = new ServicioContratado("TAXI", ubicacion1, ubicacion3,0.0);
    Tramo tramoTest = tramoConServicioContratado(servicioTest);

    Trayecto trayectoTest = new Trayecto(ubicacion1, ubicacion3,org);

    trayectoTest.agregarTramo(tramoTest);

    assertEquals(trayectoTest.getPuntoInicial(), ubicacion1);
    assertEquals(trayectoTest.getPuntoFinal(), ubicacion3);
    assertTrue(trayectoTest.getTramosDelTrayecto().contains(tramoTest));
    assertEquals(trayectoTest.getTramosDelTrayecto().size(), 1);
  }


  //  Se debe permitir el alta de nuevas líneas férreas, subterráneas y de colectivos;
  //  así como también el alta de paradas/estaciones de cada una de ellas.

  @Test
  public void altaDeFerrocarril() {
    List<Parada> listaDeEstacionesFerrocarril = this.getEstacionesCreadas();
    TransportePublico trenROCA = new TransportePublico(TransportePublico.TipoTransporte.FERROCARRIL, "roca", listaDeEstacionesFerrocarril);
    assertEquals(trenROCA.getLinea(), "roca");
  }

  @Test
  void ferrocarrilRecorreDosParadasConDistanciaCorrecta() {
    List<Parada> listaDeEstacionesFerrocarril = this.getEstacionesCreadas();
    TransportePublico trenDelAmor = new TransportePublico(TransportePublico.TipoTransporte.FERROCARRIL, "Trendelamor", listaDeEstacionesFerrocarril);

    assertEquals("20", trenDelAmor.distancia(ubicacion1, ubicacion3).valor);
  }


  @Test
  public void dar_alta_un_subte_y_verificar_distancia_recorrida() {
    List<Parada> listaDeEstacionesSubte = this.getEstacionesCreadas();
    TransportePublico subte = new TransportePublico(TransportePublico.TipoTransporte.SUBTE, "A", listaDeEstacionesSubte);

    assertEquals("10", subte.distancia(ubicacion1, ubicacion2).valor);
  }

  @Test
  public void dar_alta_un_subte_y_verificar_linea() {
    List<Parada> listaDeEstacionesSubte = this.getEstacionesCreadas();
    TransportePublico subte = new TransportePublico(TransportePublico.TipoTransporte.SUBTE, "A", listaDeEstacionesSubte);

    assertEquals(subte.getLinea(), "A");
  }

  @Test
  public void dar_altaColectivo_y_verificar_distancia_recorrida() {
    List<Parada> listaDeEstacionesColectivo = this.getEstacionesCreadas();
    TransportePublico colectivo = new TransportePublico(TransportePublico.TipoTransporte.COLECTIVO, "132", listaDeEstacionesColectivo);

    assertEquals("20", colectivo.distancia(ubicacion1, ubicacion3).valor);

  }

  @Test
  public void dar_alta_un_colectivo_y_verificar_linea() {
    List<Parada> listaDeEstacionesColectivo = this.getEstacionesCreadas();
    TransportePublico colectivo = new TransportePublico(TransportePublico.TipoTransporte.COLECTIVO, "132", listaDeEstacionesColectivo);

    assertEquals(colectivo.getLinea(), "132");
  }

  //  Se debe permitir el alta de nuevos servicios de transporte contratados.
  @Test
  public void altaDeServicioDeTransporteContratado() {
    ServicioContratado servicioContratado = new ServicioContratado("TAXI", ubicacion1, ubicacion3, 0.0);

    assertEquals(servicioContratado.getTipoServiciocontratado(), "TAXI");
    assertEquals(servicioContratado.getUbicacionInicio(), ubicacion1);
    assertEquals(servicioContratado.getUbicacionFinal(), ubicacion3);
  }
/*
  @Test
  public void altaDeTrayectoCompartido() {
    Organizacion organizacionCompartida = this.organizacionAppleEmpresa();
    Miembro miembro1 = new Miembro("pepitoBueno", "gallardo", 10101010, Miembro.TipoDocumento.DNI);
    Miembro miembro2 = new Miembro("pepitoMalvado", "sapardo", 66666666, Miembro.TipoDocumento.DNI);
    ServicioContratado servicioCompartido = new ServicioContratado("TAXI", ubicacion1, ubicacion2, 2.0);
    Tramo tramoCompartido1 = tramoConServicioContratado(servicioCompartido);
    tramoCompartido1.agregarMiembro(miembro1);
    tramoCompartido1.agregarMiembro(miembro2);

    ServicioContratado servicioCompartido2 = new ServicioContratado("UBER", ubicacion2, ubicacion3, 2.0);
    Tramo tramoCompartido2 = tramoConServicioContratado(servicioCompartido);
    tramoCompartido2.agregarMiembro(miembro1);
    tramoCompartido2.agregarMiembro(miembro2);

    Trayecto trayectoCompartido = new Trayecto(ubicacion1, ubicacion3, organizacionCompartida);

  }






  /*@Test  TODO reparar test
  public void trayectoCompartidoEntreMiembros() {
    Set<Miembro> miembrosDeUnMismoTrayecto = new HashSet<>();

    VehiculoParticular vehiculoParticular = new VehiculoParticular(VehiculoParticular.TipotransporteParticular.AUTO);
    Trayecto trayecto = new Trayecto(ubicacion1, ubicacion3);
    miembro1.registrarTrayecto(trayecto);
    miembrosDeUnMismoTrayecto.add(miembro1);
    miembro2.registrarTrayecto(trayecto);
    miembrosDeUnMismoTrayecto.add(miembro2);
    assertEquals(trayecto.getMiembros(), miembrosDeUnMismoTrayecto);
  }

  @Test //Se debe permitir dar a conocer la distancia total de un trayecto, así como también la distancia entre sus puntos intermedios en el caso de que éstos existan.
  public void distanciaTotalDeTrayectoConPuntosIntermedios() {
    Organizacion org = this.organizacionAppleEmpresa();
    VehiculoParticular vehiculoParticular = new VehiculoParticular(VehiculoParticular.TipoCombustible.GASOIL,VehiculoParticular.TipotransporteParticular.AUTO,1.0);
    Tramo tramo1 = new Tramo(vehiculoParticular, ubicacion1, ubicacion2);
    Tramo tramo2 = new Tramo(vehiculoParticular, ubicacion2, ubicacion3);
    Trayecto trayecto = new Trayecto(ubicacion1, ubicacion3,org);
    trayecto.agregarTramo(tramo1);
    trayecto.agregarTramo(tramo2);

    assertNotNull(trayecto.calcularDistanciaEnKm());
  }


  @Test
  public void distanciaTotalDeTrayectoSinPuntosIntermedios() {
    Organizacion org = this.organizacionAppleEmpresa();
    VehiculoParticular vehiculoParticular = new VehiculoParticular(VehiculoParticular.TipoCombustible.GASOIL,VehiculoParticular.TipotransporteParticular.AUTO,1.0);
    Trayecto trayecto = new Trayecto(ubicacion1, ubicacion3,org);
    Tramo tramo = new Tramo(vehiculoParticular, ubicacion1, ubicacion3);
    trayecto.agregarTramo(tramo);

    assertNotNull(trayecto.calcularDistanciaEnKm());
  }

// ------------------------------------- fin tests ---------------------------------//


//-------------------------------------metodos  de creacion de objetos ---------------------------------//


  private Tramo tramoConServicioContratado(ServicioContratado servicio) {
    return new Tramo(servicio, ubicacion1, ubicacion3);
  }

  private List<Parada> getEstacionesCreadas() {
    List<Parada> estaciones = new ArrayList<>();

    Parada parada1 = new Parada(10, null, null, ubicacion1);

    Parada parada2 = new Parada(10, parada1, null, ubicacion2);

    Parada parada3 = new Parada(0, parada2, null, ubicacion3);

    parada2.setParadaSiguiente(parada3); // primero seteamos esta porque sino la parada 2 en siguiente tendria NULL
    parada1.setParadaSiguiente(parada2);

    estaciones.add(parada1);
    estaciones.add(parada2);
    estaciones.add(parada3);
    return estaciones;
  }

 */

}