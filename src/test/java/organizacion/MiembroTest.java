package organizacion;

import builders.MiembroFactory;
import builders.OrganizacionFactory;
import builders.TrayectoFactory;
import model.organizacion.Miembro;
import model.organizacion.Organizacion;
import org.junit.jupiter.api.Test;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import model.organizacion.trayecto.Trayecto;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MiembroTest {

  //--------------- ALGUNAS ORGANIZACIONES --------------- //

  OrganizacionFactory organizacionFactory = new OrganizacionFactory();

  //---------------- CONSTRUCTORES PARA MIEMBROS ----------------//
  MiembroFactory miembroFactory = new MiembroFactory();

//BUILDER TRAYECTO
  TrayectoFactory trayectoFactory = new TrayectoFactory();
  //---------------- MIEMBROS CON TRAYECTOS Y TRAMOS ----------------//


  //------------------------ CALCULO DE HC POR MIEMBRO ------------------------//

  @Test
  public void miembro_deberia_tener_120_de_hc_en_sus_trayectos(){

    Organizacion org = organizacionFactory.organizacionAppleEmpresa();

    Trayecto trayectocon60hc_01 = trayectoFactory.untrayectoCon3tramos60hc();
    Trayecto trayectocon60hc_02 = trayectoFactory.untrayectoCon3tramos60hc();

    Miembro villa = miembroFactory.miembroIgnacio();
    villa.registrarTrayecto(trayectocon60hc_01);
    villa.registrarTrayecto(trayectocon60hc_02);

    assertEquals(120.0,villa.ejecutarCalculadoraHc());
  }

  @Test
  public void miembro_deberia_tener_120_de_hc_en_sus_trayectos_desdeBD(){

    Organizacion org = organizacionFactory.organizacionAppleEmpresa();
    Miembro villa = this.generarMiembroCon2Trayectos();

    EntityManager em = PerThreadEntityManagers.getEntityManager();
    /*EntityTransaction tx = em.getTransaction();
    tx.begin();
    em.persist(villa);
    tx.commit();*/

    Miembro villaBD = em.find(Miembro.class, 2L);

    assertEquals(120.0,villaBD.ejecutarCalculadoraHc());
  }

  private Miembro generarMiembroCon2Trayectos(){


    Trayecto trayectocon60hc_01 = trayectoFactory.untrayectoCon3tramos60hc();
    Trayecto trayectocon60hc_02 = trayectoFactory.untrayectoCon3tramos60hc();

    Miembro villa = miembroFactory.miembroIgnacio();
    villa.registrarTrayecto(trayectocon60hc_01);
    villa.registrarTrayecto(trayectocon60hc_02);
    return villa;
  }

  @Test
  public void miembro_Agrega_2_Trayectos(){

    Organizacion org = organizacionFactory.organizacionAppleEmpresa();

    Trayecto trayectocon60hc_01 = trayectoFactory.untrayectoCon3tramos60hc();
    Trayecto trayectocon60hc_02 = trayectoFactory.untrayectoCon3tramos60hc();

    Miembro villa = miembroFactory.miembroIgnacio();
    villa.registrarTrayecto(trayectocon60hc_01);
    villa.registrarTrayecto(trayectocon60hc_02);


    assertEquals(2,villa.getRegistroTrayectos().size());

  }




}