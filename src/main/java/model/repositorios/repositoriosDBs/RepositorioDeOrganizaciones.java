package model.repositorios.repositoriosDBs;

import model.agentesSectoriales.SectorTerritorial;
import model.organizacion.Contacto;
import model.organizacion.Organizacion;
import model.organizacion.Sector;
import model.usuario.Usuario;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RepositorioDeOrganizaciones extends GenericRepositoryJPA<Organizacion> {


  private static RepositorioDeOrganizaciones instance = null;

  static public RepositorioDeOrganizaciones getInstance() {
    if (instance == null) {
      instance = new RepositorioDeOrganizaciones();
    }

    instance.settearEntitys();

    return instance;

  }


  public List<Contacto> obtenerTodosLosContactosDelSistema() {

    return this.buscarTodos().stream().flatMap(organizacion -> organizacion.getContactos().stream()).collect(Collectors.toList());
  }

  public List<Organizacion> traerOrganizacionesDe(SectorTerritorial sectorTerritorialAsignado) {
    return this.buscarTodos().stream().
        filter(organizacion -> organizacion.getSectorTerritorial().getId() == sectorTerritorialAsignado.getId())
        .collect(Collectors.toList());
  }


  public List<Organizacion> getOrganizacionesBySectorTerritorial(Long id) {
    return entityManager.createQuery("from Organizacion where id_sectorTerritorial=" + id, Organizacion.class).getResultList();
  }

  //HC total por tipo de Organización (según la clasificación de la Organización)
  public List<Organizacion> getOrganizacionesByClasificacion(String clasificacion) {
    return entityManager.createQuery("from Organizacion where razon_social LIKE" + clasificacion, Organizacion.class).getResultList();
  }


  public List<Organizacion> findByIdPersona2(String idPersona) {
    /*return entityManager.createNativeQuery("from Organizacion o inner join Sector s on ( o.id_organizacion = s.id_organizacion) " +
        " inner join sector_miembro sm on (s.id_sector = sm.sector_id_sector) " +
        "inner join Miembro m on (sm.miembros_id_miembro = m.id_miembro) " +
        "inner join Persona p on (m.id_miembro = p.id)" +
        " where p.id = ?", Organizacion.class).setParameter(1, idPersona).getResultList();*/
    RepositorioDeOrganizaciones repo = getInstance();
    repo.settearEntitys();
    return repo.buscarTodos();
  }

  public List<Organizacion> findByIdPersona(String idPersona) {
    return entityManager.createQuery("from Organizacion as org join org.sectores as sector join  where sector.miembros as miembro join miembro.persona as persona where persona.id = ?", Organizacion.class).setParameter(1, idPersona).getResultList();
  }

}
/*
select * from Organizacion o inner join sector s on ( o.id_organizacion = s.id_organizacion)
    inner join sector_miembro sm on (s.id_sector = sm.sector_id_sector)
    inner join miembro m on (sm.miembros_id_miembro = m.id_miembro)
    inner join persona p on (m.id_miembro = p.id)
    where p.id = */
//     return entityManager.createQuery("from Organizacion o inner join sector s on (s.id_organizacion = o.id_organizacion) inner join sector_miembro sm on ( sm.sector_id_sector = s.id_sector) inner join miembro m on (m.id_miembro = sm.miembros_id_miembro) inner join persona p on (m.id_persona = p.id_miembro) where m.id_miembro = ?", Organizacion.class).setParameter(1, idPersona).getResultList();