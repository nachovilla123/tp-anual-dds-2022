package model.agentesSectoriales;

import lombok.Getter;
import lombok.Setter;
import model.Persistencia.PersistentEntity;
import model.implementacionCSV.HuellaDeCarbono;
import model.implementacionCSV.Periodo;
import model.organizacion.Organizacion;
import model.reporte.Reporte;
import model.repositorios.repositoriosDBs.RepositorioDeOrganizaciones;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter

@Entity
@Table(name = "agente_sectorial")
public class AgenteSectorial extends PersistentEntity { // nace x TPA3

    @Column
    public String nombre;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_sectorTerritorial")
    public SectorTerritorial sectorTerritorialAsignado;

    @Transient
    private RepositorioDeOrganizaciones repositorioDeOrganizaciones;

    public AgenteSectorial(String nombre,SectorTerritorial sectorTerritorialAsignado) {
        this.nombre=nombre;
        this.sectorTerritorialAsignado = sectorTerritorialAsignado;

    }

    public AgenteSectorial(){}

    public Set<Organizacion> getOrganizaciones(){
        return sectorTerritorialAsignado.getOrganizaciones();
    }

    public Double calculoHc(Periodo periodo, LocalDate fecha){
        return sectorTerritorialAsignado.calculoHc(periodo,fecha);
    }


/*
    public Double calcularHc(){
        return this.sectorTerritorialAsignado.calculoHc();
    }
*/
}
