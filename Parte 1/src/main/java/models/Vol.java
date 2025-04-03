package models;

import java.util.ArrayList;
import java.util.List;

public class Vol {
    String id;
    String horaSortida;
    String horaArribada;
    Avio avio;
    String origen;
    String desti;
    List<Maleta> equipatge;

    public Vol(String id, String horaSortida, String horaArribada, Avio avio, String origen, String desti) {
        this.id = id;
        this.horaSortida = horaSortida;
        this.horaArribada = horaArribada;
        this.avio = avio;
        this.origen = origen;
        this.desti = desti;
        equipatge = new ArrayList<>();
    }
    public Vol(){}

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getHoraSortida() {
        return horaSortida;
    }
    public void setHoraSortida(String horaSortida) {
        this.horaSortida = horaSortida;
    }
    public String getHoraArribada() {
        return horaArribada;
    }
    public void setHoraArribada(String horaArribada) {
        this.horaArribada = horaArribada;
    }
    public Avio getAvio() {
        return avio;
    }
    public void setAvio(Avio avio) {
        this.avio = avio;
    }
    public String getOrigen() {
        return origen;
    }
    public void setOrigen(String origen) {
        this.origen = origen;
    }
    public String getDesti() {
        return desti;
    }
    public void setDesti(String desti) {
        this.desti = desti;
    }
    public List<Maleta> getEquipatge() {
        return equipatge;
    }
    public void setEquipatge(List<Maleta> equipatge) {
        this.equipatge = equipatge;
    }
    public void afegirMaleta(Maleta maleta) {
        equipatge.add(0, maleta);
    }
}
