package builders;

import model.organizacion.Miembro;
import model.organizacion.Sector;

import java.util.HashSet;
import java.util.Set;

public class SectorFactory {

  private Sector generarSector(Set<Miembro> miembros , Sector.SectorDeTrabajo sectorDeTrabajo){
    return  new Sector(miembros, sectorDeTrabajo);
  }


  public  Sector SectorRRHH(){
    return this.generarSector(new HashSet<>(),Sector.SectorDeTrabajo.RRHH);
  }

  public  Sector SectorAdministracion(){
    return this.generarSector(new HashSet<>(),Sector.SectorDeTrabajo.ADMINSTRACION);
  }

  public   Sector SectorContaduria(){
    return this.generarSector(new HashSet<>(),Sector.SectorDeTrabajo.CONTADURIA);
  }


}
