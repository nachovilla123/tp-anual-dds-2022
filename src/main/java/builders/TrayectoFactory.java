package builders;

import model.organizacion.Organizacion;
import model.organizacion.trayecto.Tramo;
import model.organizacion.trayecto.Trayecto;
import model.organizacion.trayecto.Ubicacion;

import java.util.ArrayList;
import java.util.List;

public class TrayectoFactory {

  private List<Tramo> tramos = new ArrayList<>();

  private Trayecto generarTrayecto(Ubicacion pi, Ubicacion pf, List<Tramo> tramos) {
    return new Trayecto(pi, pf, tramos);
  }

  //--------------- ALGUNAS UBICACIONES --------------- //
  UbicacionFactory ubicacionFactory = new UbicacionFactory();

  Ubicacion ubicacionPedroGoyena = ubicacionFactory.ubicacionPedroGoyena();
  Ubicacion ubicacionMedrano = ubicacionFactory.ubicacionMedrano();
  Ubicacion ubicacionMozart = ubicacionFactory.ubicacionMozart();

  //Organizacion
  OrganizacionFactory organizacionFactory = new OrganizacionFactory();
  Organizacion orgApple = organizacionFactory.organizacionAppleEmpresa();
  //BUILDER TRAMO
  TramoFactory tramoFactory = new TramoFactory();

  // este trayecto no es compartido
  public Trayecto untrayectoCon3tramos60hc() {

    List<Tramo> tramos = tramoFactory.get3TramosCreados();

    return generarTrayecto(ubicacionPedroGoyena, ubicacionMozart, tramos);
  }

  //Fijarse las ubicaciones del trayecto
  public Trayecto untrayectoCon2tramos40hc(){

    List<Tramo> tramos = tramoFactory.get2TramosCreados();

    return generarTrayecto(ubicacionPedroGoyena, ubicacionMozart, tramos);
  }

}