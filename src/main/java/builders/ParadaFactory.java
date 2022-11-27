package builders;

import model.mediodetransporte.transportepublico.Parada;
import model.organizacion.trayecto.Ubicacion;

import java.util.ArrayList;
import java.util.List;

public class ParadaFactory {

  //--------------- ALGUNAS UBICACIONES --------------- //
  UbicacionFactory ubicacionFactory = new UbicacionFactory();

  Ubicacion ubicacionMedrano = ubicacionFactory.ubicacionMedrano();
  Ubicacion ubicacionMozart = ubicacionFactory.ubicacionMozart();
  Ubicacion ubicacionPedroGoyena = ubicacionFactory.ubicacionPedroGoyena();



  private Parada generarParada(int distProxima, Parada paradaAnterior, Parada paradaSiguiente, Ubicacion ubicacion){
    return new Parada(distProxima, paradaAnterior, paradaSiguiente,ubicacion);
  }

  public List<Parada> getEstacionesCreadas() {
    List<Parada> estaciones = new ArrayList<>();

    Parada parada1 = new Parada(10, null, null, ubicacionPedroGoyena);
    Parada parada2 = new Parada(10, parada1, null, ubicacionMedrano);
    Parada parada3 = new Parada(0, parada2, null, ubicacionMozart);

    parada2.setParadaSiguiente(parada3); // primero seteamos esta porque sino la parada 2 en siguiente tendria NULL
    parada1.setParadaSiguiente(parada2);

    estaciones.add(parada1);
    estaciones.add(parada2);
    estaciones.add(parada3);
    return estaciones;
  }
}
