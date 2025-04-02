package edu.upc.dsa.models;

import java.util.HashMap;

public class Order {
    String dni;
    HashMap<Integer, String> contenido;

    public Order(String dni) {
        this.dni = dni;
        this.contenido = new HashMap<>();
    }

    public void addLP(int i, String s) {
        this.contenido.put(i,s);

    }
    public HashMap<Integer, String> getContenido() {
        return contenido;
    }
    public String getUser() {
        if(this.dni != null)
            return this.dni;
        return null;
    }
}
