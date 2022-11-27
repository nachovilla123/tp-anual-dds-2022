package model.implementacionGeoDds;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface GeoDds {
  @Headers("Authorization: Bearer 6Fqwtb4v2ALrf7YvDGglSAqHR8cQAjA6VvXOj87XBw4=")
  @GET("distancia")
  Call<Distancia> distanciaEntreUbicaciones(@Query("localidadOrigenId") int localidadOrigenId,
                                            @Query("calleOrigen") String calleOrigen,
                                            @Query("alturaOrigen") int alturaOrigen,
                                            @Query("localidadDestinoId") int localidadDestinoId,
                                            @Query("calleDestino") String calleDestino,
                                            @Query("alturaDestino") int alturaDestino);

  //amoestamateriaamuerte@example.com
  //TOKEN: 6Fqwtb4v2ALrf7YvDGglSAqHR8cQAjA6VvXOj87XBw4=
}







