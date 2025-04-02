package models;

import java.util.ArrayList;
import java.util.List;

public class User {
    String dni;
    List<Order> orders = new ArrayList<Order>();
    public User(String dni) {
        this.dni = dni;
        this.orders = new ArrayList<>();
    }

    public List<Order> orders() {
        if(!this.orders.isEmpty())
            return this.orders;
        return null;
    }

    public void setOrder(Order order) {
        this.orders.add(order);
    }

    public String getDni() {
        return this.dni;
    }
}
