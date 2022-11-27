package builders;

import model.organizacion.trayecto.Ubicacion;

public class UbicacionFactory {

  private Ubicacion generarUbicacion(int localidad, String calle, int altura) {
    return new Ubicacion(localidad, calle, altura);
  }

  public Ubicacion ubicacionPedroGoyena() {
    return this.generarUbicacion(1, "Pedro Goyena", 2000);
  }

  public Ubicacion ubicacionMedrano() {
    return this.generarUbicacion(1, "Medrano", 123);
  }

  public Ubicacion ubicacionMozart() {
    return this.generarUbicacion(1, "Mozart", 2700);
  }


}
