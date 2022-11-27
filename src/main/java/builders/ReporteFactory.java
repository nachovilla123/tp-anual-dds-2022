package builders;

import model.implementacionCSV.HuellaDeCarbono;
import model.implementacionCSV.Periodo;
import model.reporte.Reporte;

public class ReporteFactory {


  private Reporte generarReporte(HuellaDeCarbono hcActividades, HuellaDeCarbono hcTramos, Periodo periodo){
    return  new Reporte(hcActividades, hcTramos, periodo);
  }

  public HuellaDeCarbono generarHuellaDeCarbono(Double valor ){
    return new HuellaDeCarbono(valor);
  }

  //REPORTES ANUALES
  public  Reporte reporteAanual(){
    return this.generarReporte(new HuellaDeCarbono(100.0), new HuellaDeCarbono(200.0),Periodo.ANUAL);}

  public  Reporte reporteBanual(){
    return this.generarReporte(new HuellaDeCarbono(200.0), new HuellaDeCarbono(300.0),Periodo.ANUAL);
  }
  public  Reporte reporteCanual(){
    return this.generarReporte(new HuellaDeCarbono(1000.0), new HuellaDeCarbono(2000.0),Periodo.ANUAL);
  }
  public  Reporte reporteDanual(){
    return this.generarReporte(new HuellaDeCarbono(1500.0), new HuellaDeCarbono(2000.0),Periodo.ANUAL);
  }
  public  Reporte reporteEanual(){
    return this.generarReporte(new HuellaDeCarbono(2000.0), new HuellaDeCarbono(2500.0),Periodo.ANUAL);
  }
  //REPORTES MENSUALES
  public  Reporte reporteAmensual(){
    return this.generarReporte(new HuellaDeCarbono(10.0), new HuellaDeCarbono(20.0),Periodo.MENSUAL);}

  public  Reporte reporteBmensual(){
    return this.generarReporte(new HuellaDeCarbono(20.0), new HuellaDeCarbono(30.0),Periodo.MENSUAL);
  }
  public  Reporte reporteCmensual(){
    return this.generarReporte(new HuellaDeCarbono(100.0), new HuellaDeCarbono(200.0),Periodo.MENSUAL);
  }
  public  Reporte reporteDmensual(){
    return this.generarReporte(new HuellaDeCarbono(150.0), new HuellaDeCarbono(200.0),Periodo.MENSUAL);
  }
  public  Reporte reporteEmensual(){
    return this.generarReporte(new HuellaDeCarbono(200.0), new HuellaDeCarbono(250.0),Periodo.MENSUAL);
  }

}
