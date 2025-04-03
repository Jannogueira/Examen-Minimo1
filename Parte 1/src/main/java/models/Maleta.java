package models;

import java.util.ArrayList;
import java.util.List;

public class Maleta {

    int id = 0;
    String propietari;
    public Maleta(String propietari) {
        this.id = id++;
        this.propietari = propietari;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getPropietari() {
        return propietari;
    }
    public void setPropietari(String propietari) {
        this.propietari = propietari;
    }
}
