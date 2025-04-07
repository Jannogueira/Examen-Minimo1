package edu.upc.dsa.models;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

public class Maleta {
    static int contador = 0;
    int id = 0;
    String propietari;
    public Maleta(String propietari) {
        this.id = contador++;
        this.propietari = propietari;
    }
    public Maleta(){}

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
