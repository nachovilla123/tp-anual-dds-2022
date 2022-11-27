package builders;

import model.mediodetransporte.MedioDeTransporte;
import model.mediodetransporte.transportepublico.TransportePublico;
import model.organizacion.Organizacion;
import model.organizacion.trayecto.Tramo;
import model.organizacion.trayecto.Ubicacion;

import java.util.ArrayList;
import java.util.List;

public class TramoFactory {

  //--------------- ALGUNAS UBICACIONES --------------- //
  UbicacionFactory ubicacionFactory = new UbicacionFactory();
  Ubicacion ubicacionMedrano = ubicacionFactory.ubicacionMedrano();
  Ubicacion ubicacionMozart = ubicacionFactory.ubicacionMozart();
  Ubicacion ubicacionPedroGoyena = ubicacionFactory.ubicacionPedroGoyena();

  // ORGANIZACION
  OrganizacionFactory organizacionFactory = new OrganizacionFactory();
  Organizacion orgApple = organizacionFactory.organizacionAppleEmpresa();
  //BUILDER MEDIOTRANSPORTE
  MedioDeTransporteFactory medioDeTransporteFactory = new MedioDeTransporteFactory();

  private Tramo generarTramo(MedioDeTransporte medioDeTransporte, Ubicacion origen, Ubicacion destino,String nombre){
    return new Tramo(nombre,medioDeTransporte,origen,destino);
  }


  public Tramo tramoConHCde20() {
    TransportePublico tren = medioDeTransporteFactory.trenDelAmor();
    return generarTramo(tren,ubicacionPedroGoyena,ubicacionMedrano,"tramo20hc");
  }



  public List<Tramo> get3TramosCreados(){
    List<Tramo> tramos = new ArrayList<>();
    tramos.add(this.tramoConHCde20());
    tramos.add(this.tramoConHCde20());
    tramos.add(this.tramoConHCde20());
    //ESTO SON 3 TRAMOS IDENTICOS
    return tramos;
  }

  public List<Tramo> get2TramosCreados(){
    List<Tramo> tramos = new ArrayList<>();
    tramos.add(this.tramoConHCde20());
    tramos.add(this.tramoConHCde20());
    //ESTO SON 2 TRAMOS IDENTICOS
    return tramos;
  }
}
