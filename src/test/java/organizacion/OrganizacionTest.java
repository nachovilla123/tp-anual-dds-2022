package organizacion;

import builders.*;
import model.implementacionCSV.Periodo;
import model.mediodetransporte.transportepublico.TransportePublico;
import model.organizacion.Miembro;
import model.organizacion.Organizacion;
import model.organizacion.Persona;
import model.organizacion.Sector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import model.organizacion.trayecto.Trayecto;
import model.organizacion.trayecto.Ubicacion;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


class OrganizacionTest {

  //--------------- ALGUNAS UBICACIONES --------------- //
  UbicacionFactory ubicacionFactory = new UbicacionFactory();

    Ubicacion ubicacionMedrano = ubicacionFactory.ubicacionMedrano();
    Ubicacion ubicacionMozart = ubicacionFactory.ubicacionMozart();
    Ubicacion ubicacionPedroGoyena = ubicacionFactory.ubicacionPedroGoyena();

  //--------------- ALGUNOS SECTORES --------------- //
  SectorFactory sectorFactory = new SectorFactory();

  Sector sectorRRHH = sectorFactory.SectorRRHH();
  Sector sectorAdminstracion = sectorFactory.SectorAdministracion();
  Sector sectorContaduria = sectorFactory.SectorContaduria();

  //--------------- ALGUNAS ORGANIZACIONES --------------- //

  OrganizacionFactory organizacionFactory = new OrganizacionFactory();

  //--------------- ALGUNOS MIEMBROS --------------- //

  MiembroFactory miembroFactory = new MiembroFactory();

  //BUILDER PARADAS
  ParadaFactory paradaFactory = new ParadaFactory();

  //BUILDER TRAYECTO
  TrayectoFactory trayectoFactory = new TrayectoFactory();

  //--------------- ALGUNOS MEDIOS DE TRANSPORTE --------------- //

   MedioDeTransporteFactory medioDeTransporteFactory = new MedioDeTransporteFactory();
  //--------------- TESTS --------------- //

  @Test   // Se debe permitir el alta de Organizaciones y de sectores dentro de cada una de estas.
  public void Organizacion_Agrega_3_Sectores(){
    Organizacion org = organizacionFactory.organizacionAppleEmpresa();
    org.agregarSector(sectorAdminstracion);
    org.agregarSector(sectorRRHH);
    org.agregarSector(sectorContaduria);
    assertEquals(3,org.getSectores().size());
  }



  //  Se debe permitir que un Miembro se vincule con un sector de la organización.
  //  Las Organizaciones deben aceptar esta vinculación para que las mediciones brindadas por dicho miembro (como los trayectos) tengan impacto en la Organización.
  @Test
  public void miembroSeQuerieVincularYLaOrganizacionLoAcepta() {
    Organizacion org = organizacionFactory.organizacionAppleEmpresa();
    org.agregarSector(sectorAdminstracion);

    PersonaFactory personaFactory = new PersonaFactory();

    Persona personaAVincular = personaFactory.personaAlex();


    personaAVincular.generarSolicitudDeVinculacion(sectorAdminstracion); //miembro se quiere vincular
    sectorAdminstracion.aceptarVinculacionTodosLosMiembro();

    assertTrue(sectorAdminstracion.miembros.contains(personaAVincular));
  }


//----------------- TESTS HC -----------------//

  @Test
  public void organizacionApple_con_60hc() {

    Organizacion org = organizacionFactory.organizacionAppleEmpresa();
    Trayecto trayeco60hc = trayectoFactory.untrayectoCon3tramos60hc();
    Miembro miembroIgnacio = miembroFactory.miembroIgnacio();

    miembroIgnacio.registrarTrayecto(trayeco60hc);
    sectorRRHH.agregarTrabajador(miembroIgnacio);
    org.agregarSector(sectorRRHH);

    Assertions.assertEquals(60,org.calcularHc(Periodo.ANUAL, LocalDate.now()));
  }

  @Test
  public void testHC_desde_BD() {

    Organizacion org = generarOrganizacioncon60Hc();
    EntityManager em = PerThreadEntityManagers.getEntityManager();
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    em.persist(org);
    tx.commit();

    Organizacion orgBD = em.find(Organizacion.class, 1L);

    Assertions.assertEquals(60,orgBD.calcularHc(Periodo.ANUAL, LocalDate.now()));
  }

  private Organizacion generarOrganizacioncon60Hc(){
    Organizacion org = organizacionFactory.organizacionAppleEmpresa();

    Trayecto trayeco60hc = trayectoFactory.untrayectoCon3tramos60hc();
    Miembro miembroIgnacio = miembroFactory.miembroIgnacio();

    miembroIgnacio.registrarTrayecto(trayeco60hc);
    sectorRRHH.agregarTrabajador(miembroIgnacio);
    org.agregarSector(sectorRRHH);
    return org;
  }
  @Test
  void ferrocarril_recorre_DosParadas_Con_DistanciaCorrecta() {
    TransportePublico trenDelAmor = medioDeTransporteFactory.trenDelAmor();

    assertEquals("20", trenDelAmor.distancia(ubicacionPedroGoyena, ubicacionMozart).valor);
  }



  }