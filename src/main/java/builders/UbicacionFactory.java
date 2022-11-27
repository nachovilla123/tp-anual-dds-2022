package builders;

import model.organizacion.trayecto.Ubicacion;

public class UbicacionFactory {

  private Ubicacion generarUbicacion( String calle, int altura) {
    return new Ubicacion(1, calle, altura);
  }

  public Ubicacion ubicacionPedroGoyena() {
    return this.generarUbicacion( "Pedro Goyena", 2000);
  }

  public Ubicacion ubicacionMedrano() {
    return this.generarUbicacion( "Medrano", 123);
  }

  public Ubicacion ubicacionMozart() {
    return this.generarUbicacion( "Mozart", 2700);
  }


}
