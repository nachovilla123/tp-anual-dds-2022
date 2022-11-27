package model.implementacionGeoDds;

import model.organizacion.trayecto.Ubicacion;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class ServicioGeoDds {
  private static ServicioGeoDds instancia = null;
  private static final String urlApi = "https://ddstpa.com.ar/api/";
  private Retrofit retrofit;

  private ServicioGeoDds() {
    this.retrofit = new Retrofit.Builder()
        .baseUrl(urlApi)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
  }

  public static ServicioGeoDds instancia() {
    if (instancia == null) {
      instancia = new ServicioGeoDds();
    }
    return instancia;
  }

  public Distancia distanciaEntreLasUbicaciones(Ubicacion origen,
                                                Ubicacion destino) throws IOException {
    GeoDds geoDdsService = this.retrofit.create(GeoDds.class);
    Call<Distancia> requestdistanciaEntreLasUbicaciones = geoDdsService
        .distanciaEntreUbicaciones(origen.localidad,
            origen.calle,
            origen.altura,
            destino.localidad,
            destino.calle,
            destino.altura);
    Response<Distancia> responsedistanciaEntreLasUbicaciones = requestdistanciaEntreLasUbicaciones
        .execute();
    return responsedistanciaEntreLasUbicaciones.body();
  }
}