package organizacion.agentesSectoriales;

import model.agentesSectoriales.SectorTerritorial;
import builders.MiembroFactory;
import builders.OrganizacionFactory;
import builders.SectorFactory;
import builders.TrayectoFactory;
import model.implementacionCSV.Periodo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import model.organizacion.Miembro;
import model.organizacion.Organizacion;
import model.organizacion.Sector;
import model.organizacion.trayecto.Trayecto;
import model.repositorios.repositoriosDBs.RepositorioDeOrganizaciones;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class SectorTerritorialTest {

    //BUILDERS
    OrganizacionFactory organizacionFactory = new OrganizacionFactory();
    TrayectoFactory trayectoFactory = new TrayectoFactory();
    MiembroFactory miembroFactory = new MiembroFactory();
    SectorFactory sectorFactory = new SectorFactory();

    @Test
    void dar_de_alta_unSector_territorial() {

        SectorTerritorial sector7 = new SectorTerritorial("Buenos Aires");

        assertEquals("Buenos Aires",sector7.getSectorTerritorial());
    }


    @Test
    void calculo_de_hc_total_de_un_sector_con_dos_organizaciones(){

        SectorTerritorial unSector = creacionSectorTerritorialBsAs();
      //  Organizacion organizacionApple = creacionOrganizacionAppleCon60Hc();
        Organizacion organizacionUtn = creacionOrganizacionorganizacionUTNCon60Hc();

       // unSector.getRepoOrganizaciones().agregar(organizacionApple);
        RepositorioDeOrganizaciones.getInstance().agregar(organizacionUtn);

        Assertions.assertEquals(120,unSector.calculoHc(Periodo.ANUAL, LocalDate.now()));
    }

    private Organizacion creacionOrganizacionAppleCon60Hc() {
        Organizacion org = organizacionFactory.organizacionAppleEmpresa();
        Trayecto trayeco60hc = trayectoFactory.untrayectoCon3tramos60hc();
        Miembro miembroIgnacio = miembroFactory.miembroIgnacio();
        Sector sectorRRHH = sectorFactory.SectorRRHH();

        miembroIgnacio.registrarTrayecto(trayeco60hc);

        sectorRRHH.agregarTrabajador(miembroIgnacio);
        org.agregarSector(sectorRRHH);

        return org;
    }

    private Organizacion creacionOrganizacionorganizacionUTNCon60Hc() {
        Organizacion org = organizacionFactory.organizacionUTN();
        Trayecto trayeco60hc = trayectoFactory.untrayectoCon3tramos60hc();
        Miembro miembroPipe = miembroFactory.miembroPipe();
        Sector sectorRRHH = sectorFactory.SectorContaduria();

        miembroPipe.registrarTrayecto(trayeco60hc);
        sectorRRHH.agregarTrabajador(miembroPipe);
        org.agregarSector(sectorRRHH);

        return org;
    }

    private SectorTerritorial creacionSectorTerritorialBsAs() {
        //Creacion del sector
        SectorTerritorial sector=new SectorTerritorial("Buenoas Aires");
        //Creacion del repo
        RepositorioDeOrganizaciones repoOrganizaciones = new RepositorioDeOrganizaciones();
        EntityManager em = PerThreadEntityManagers.getEntityManager();
        repoOrganizaciones.setEntityManager(em);
        EntityTransaction tx = em.getTransaction();
        repoOrganizaciones.setEntityTransaction(tx);

        return sector;
    }



}
