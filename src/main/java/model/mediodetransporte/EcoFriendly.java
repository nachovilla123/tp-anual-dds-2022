package model.mediodetransporte;

import lombok.Getter;
import lombok.Setter;
import model.organizacion.trayecto.Ubicacion;


import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter

@Entity
@Table(name = "ecofriendly")
public class EcoFriendly extends MedioDeTransporte {
  // Estos eran bicicleta o caminando
  public EcoFriendly(Object obj){
    this.settearTipoDeConsumo();
  }

  public Double cantidadDeCombustibleConsumidoPorKM() {
    return 0.0;
  }

  public Boolean esCompartible(){
    return false;
  }

  public EcoFriendly(){}


  @Override
  public double hcPorDistancia(Ubicacion origen, Ubicacion destino) {
    return 0;
  }

  public void settearTipoDeConsumo(){
    //settear con la bd
  }
}