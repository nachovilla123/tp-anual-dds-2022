package builders;

import model.implementacionCSV.HuellaDeCarbono;
import model.reporte.Reporte;

public class ReporteFactory {


  private Reporte generarReporte(HuellaDeCarbono hcActividades, HuellaDeCarbono hcTramos ){
    return  new Reporte(hcActividades, hcTramos);
  }

  public HuellaDeCarbono generarHuellaDeCarbono(Double valor ){
    return new HuellaDeCarbono(valor);
  }

  public  Reporte reporteA(){return this.generarReporte(new HuellaDeCarbono(100.0), new HuellaDeCarbono(200.0));}
  public  Reporte reporteB(){
    return this.generarReporte(new HuellaDeCarbono(200.0), new HuellaDeCarbono(300.0));
  }
  public  Reporte reporteC(){
    return this.generarReporte(new HuellaDeCarbono(1000.0), new HuellaDeCarbono(2000.0));
  }
  public  Reporte reporteD(){
    return this.generarReporte(new HuellaDeCarbono(1500.0), new HuellaDeCarbono(2000.0));
  }
  public  Reporte reporteE(){
    return this.generarReporte(new HuellaDeCarbono(2000.0), new HuellaDeCarbono(2500.0));
  }
}
