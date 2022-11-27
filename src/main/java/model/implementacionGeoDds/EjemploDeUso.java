package model.implementacionGeoDds;

import model.organizacion.trayecto.Ubicacion;
import java.io.IOException;

public class EjemploDeUso {

  public static void main(String[] args) throws IOException {

    ServicioGeoDds servicioGeoDds = ServicioGeoDds.instancia();

    System.out.println("ingrese dos ubicaciones para calcular su distancia");

    Ubicacion origen = new Ubicacion(1, "maipu", 100);
    Ubicacion destino = new Ubicacion(456, "O'Higgins", 200);

    Distancia distDevuelta = ServicioGeoDds.instancia()
        .distanciaEntreLasUbicaciones(origen, destino);

    System.out.println(distDevuelta.unidad);
    System.out.println(distDevuelta.valor);
  }

}
