package builders;

import model.implementacionCSV.FactorDeEmision;
import model.implementacionCSV.tipoDeConsumo.TipoDeConsumo;
import model.mediodetransporte.ServicioContratado;
import model.mediodetransporte.VehiculoParticular;
import model.mediodetransporte.transportepublico.Parada;
import model.mediodetransporte.transportepublico.TransportePublico;
import model.organizacion.trayecto.Ubicacion;
import model.repositorios.repositoriosDBs.RepositorioFactorDeEmision;

import java.util.List;

public class MedioDeTransporteFactory {

  private ServicioContratado generarVehiculoContratado(String tipoServiciocontratado, Ubicacion ubicacionInicio, Ubicacion ubicacionFinal, Double cantidadDeCombustiblePorKM, FactorDeEmision factorDeEmision){
    return new ServicioContratado(tipoServiciocontratado,ubicacionInicio,ubicacionFinal,cantidadDeCombustiblePorKM,factorDeEmision);
  }

  private VehiculoParticular generarVehiculoParticular(VehiculoParticular.TipoCombustible tipoCombustible, VehiculoParticular.TipotransporteParticular tipotransporteParticular, double cantidadDeCombustiblePorKM, FactorDeEmision factorDeEmision){
    return new VehiculoParticular(tipoCombustible, tipotransporteParticular,cantidadDeCombustiblePorKM, factorDeEmision);
  }

  private TransportePublico generarTransportePublico(TransportePublico.TipoTransporte tipoTransportePublico,String linea, List<Parada> paradas, FactorDeEmision factorDeEmision){
    return new TransportePublico(tipoTransportePublico, linea,paradas, factorDeEmision);
  }

  public TransportePublico trenDelAmor(){
    ParadaFactory paradaF = new ParadaFactory();
    List<Parada> listaDeEstacionesFerrocarril = paradaF.getEstacionesCreadas();
       //TipoDeConsumo.DIESEL_GASOIL)
    return generarTransportePublico(TransportePublico.TipoTransporte.FERROCARRIL, "Trendelamor", listaDeEstacionesFerrocarril, this.tcDieselFE1());
  }
  private FactorDeEmision tcDieselFE1() {

    //TipoDeConsumo.DIESEL_GASOIL.setFactorDeEmision(new FactorDeEmision(1));
    //return  new FactorDeEmision(1.0,TipoDeConsumo.DIESEL_GASOIL);

    return RepositorioFactorDeEmision.getInstance().obtenerPorTipoDeConsumo(TipoDeConsumo.DIESEL_GASOIL);
  }

}
