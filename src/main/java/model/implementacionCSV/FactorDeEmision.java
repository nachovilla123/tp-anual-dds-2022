package model.implementacionCSV;

import lombok.Getter;
import lombok.Setter;
import model.Persistencia.PersistentEntity;
import model.implementacionCSV.tipoDeConsumo.TipoDeConsumo;

import javax.persistence.*;


@Setter
@Getter

@Entity
@Table(name = "factor_de_emision")
public class FactorDeEmision extends PersistentEntity {

  @Column
  private double valor;

  @Enumerated(EnumType.STRING)
  public TipoDeConsumo tipoDeConsumo;

  public FactorDeEmision(double valor,TipoDeConsumo tipoDeConsumo) {
    this.valor = valor;
    this.tipoDeConsumo = tipoDeConsumo;
  }

  public FactorDeEmision(){}



}
