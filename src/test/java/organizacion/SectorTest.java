package organizacion;

import builders.MiembroFactory;
import builders.OrganizacionFactory;
import builders.SectorFactory;
import model.organizacion.Miembro;
import model.organizacion.Organizacion;
import model.organizacion.Sector;
import org.junit.jupiter.api.Test;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import static org.junit.jupiter.api.Assertions.*;



class SectorTest {

  //BUILDER SECTOR//
  SectorFactory sectorFactory = new SectorFactory();

  //BUILDER MIEMBRO///

  MiembroFactory miembroFactory = new MiembroFactory();

  // ORGANIZACION

  OrganizacionFactory organizacionFactory = new OrganizacionFactory();
  Organizacion orgApple = organizacionFactory.organizacionAppleEmpresa();


   @Test
  public void testeandoHC(){

     Sector sector = sectorFactory.SectorRRHH();

     Miembro miembro = miembroFactory.miembroCon60dehc();

     sector.agregarTrabajador(miembro);

     //quiero los trayectos de la org apple, en este caso todos los trayectos
     //del miembro con 60 de hc son de apple

     double hcmiembro =miembro.ejecutarCalculadoraHc(); //para debug
     double hcorg = sector.calcularHC(); //para debug

     assertEquals(60.0,sector.calcularHC());
   }


  @Test
  public void testeandoHC2(){
    Miembro miembro = miembroFactory.miembroCon60dehc();
    Miembro miembro1 = miembroFactory.miembroCon40dehc();
    Sector sector = sectorFactory.SectorRRHH();

    sector.agregarTrabajador(miembro);
    sector.agregarTrabajador(miembro1);

    double hcmiembro =miembro.ejecutarCalculadoraHc(); //para debug
    double hcorg =sector.calcularHC();//para debug

    assertEquals(100.0,sector.calcularHC());
  }

  @Test
  public  void testeandoHC2_desdeBD(){

    Sector sector = generarSector100HC();
    EntityManager em = PerThreadEntityManagers.getEntityManager();
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    em.persist(sector);
    tx.commit();

    Sector sectorBD = em.find(Sector.class, 2L);

    assertEquals(100.0,sector.calcularHC());
  }

  private Sector generarSector100HC(){


    Miembro miembro = miembroFactory.miembroCon60dehc();
    Miembro miembro1 = miembroFactory.miembroCon40dehc();
    Sector sector = sectorFactory.SectorRRHH();

    sector.agregarTrabajador(miembro);
    sector.agregarTrabajador(miembro1);

    return sector;
  }
}