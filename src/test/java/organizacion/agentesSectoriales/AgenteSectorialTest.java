package organizacion.agentesSectoriales;

import model.agentesSectoriales.AgenteSectorial;
import model.agentesSectoriales.SectorTerritorial;
import org.junit.jupiter.api.Test;
import model.organizacion.Organizacion;
import model.organizacion.trayecto.Ubicacion;
import model.repositorios.repositoriosDBs.RepositorioDeOrganizaciones;

import static org.junit.jupiter.api.Assertions.*;

class AgenteSectorialTest {

    SectorTerritorial sector7 = new SectorTerritorial("Buenos Aires");
    RepositorioDeOrganizaciones repositorioDeOrganizaciones =  RepositorioDeOrganizaciones.getInstance();

    //------- alta de organizaciones ------
    Ubicacion ubicacion1 = new Ubicacion(1, "Pedro Goyena", 2000);
    Organizacion organizacion_01 = new Organizacion("estas", Organizacion.Tipo.ONG,ubicacion1, Organizacion.Clasificacion.EMPRESA_SECTOR_SECUNDARIO);
    Organizacion organizacion_02 = new Organizacion("leyendo", Organizacion.Tipo.ONG,ubicacion1, Organizacion.Clasificacion.EMPRESA_SECTOR_SECUNDARIO);
    Organizacion organizacion_03 = new Organizacion("esto", Organizacion.Tipo.ONG,ubicacion1, Organizacion.Clasificacion.EMPRESA_SECTOR_SECUNDARIO);

    //------- alta de organizaciones ------

    @Test
    void dar_alta_agenteSectorial() {

        AgenteSectorial agenteSectorial_01 = new AgenteSectorial("SEÑOR VILLA",sector7);

        assertEquals("Buenos Aires",agenteSectorial_01.sectorTerritorialAsignado.getSectorTerritorial());
    }

    @Test
    void agenteSectorialTieneOrganizaciones_en_su_sector() {
        SectorTerritorial BuenosAires = new SectorTerritorial("Buenos Aires");
        SectorTerritorial Salta = new SectorTerritorial("Salta");

        organizacion_01.setSectorTerritorial(BuenosAires);
        organizacion_02.setSectorTerritorial(BuenosAires);
        organizacion_03.setSectorTerritorial(Salta);

      //  repositorioDeOrganizaciones.agregarOrganizacion(organizacion_01);
        //repositorioDeOrganizaciones.agregarOrganizacion(organizacion_02);
       // repositorioDeOrganizaciones.agregarOrganizacion(organizacion_03);


        AgenteSectorial agenteSectorial_01 = new AgenteSectorial("SEÑOR NICOLAS",sector7);
       // agenteSectorial_01.harcodearAgregadoOrgASector(organizacion_01);
        //agenteSectorial_01.harcodearAgregadoOrgASector(organizacion_02);
        //agenteSectorial_01.cargarOrganizacionesDelSector();


        assertEquals(2,agenteSectorial_01.getOrganizaciones().size());

    }


}
