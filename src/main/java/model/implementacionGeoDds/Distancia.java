package model.implementacionGeoDds;

public class Distancia {
  public String valor;
  public String unidad;

  public Double getValor() {
    return Double.parseDouble(valor);
  }

  public Distancia(String valor, String unidad) {
    this.valor = valor;
    this.unidad = unidad;
  }
}
