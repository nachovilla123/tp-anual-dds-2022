package builders;

import model.organizacion.Organizacion;
import model.organizacion.trayecto.Ubicacion;

public class OrganizacionFactory {

  UbicacionFactory ubicacionFactory = new UbicacionFactory();

  Ubicacion ubicacionMedrano = ubicacionFactory.ubicacionMedrano();
  Ubicacion ubicacionMozart = ubicacionFactory.ubicacionMozart();
  Ubicacion ubicacionPedroGoyena = ubicacionFactory.ubicacionPedroGoyena();



  private Organizacion generarOrganizacion(String razonSocial, Organizacion.Tipo tipoDeOrg, Ubicacion ubicacion, Organizacion.Clasificacion empresaSectorSecundario){
    return new Organizacion(razonSocial,tipoDeOrg,ubicacion,empresaSectorSecundario);
  }
  public Organizacion organizacionAppleEmpresa(){
    return this.generarOrganizacion("Apple", Organizacion.Tipo.EMPRESA_INSTITUCION,ubicacionMedrano, Organizacion.Clasificacion.EMPRESA_SECTOR_SECUNDARIO);
  }

  public  Organizacion organizacionUTN(){
    return this.generarOrganizacion("UTN", Organizacion.Tipo.GUBERNAMENTAL,ubicacionMozart, Organizacion.Clasificacion.UNIVERSIDAD);
  }

  public  Organizacion organizacionCALCON(){
    return this.generarOrganizacion("Calcon", Organizacion.Tipo.ONG,ubicacionPedroGoyena, Organizacion.Clasificacion.MINISTERIO);
  }

}
