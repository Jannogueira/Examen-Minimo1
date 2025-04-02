package models;

public class Product {
    String id;
    String name;
    double price;
    int sales;

    public Product(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.sales = 0;
    }
    public Product() {}

    public int sales() {
        return this.sales;
    }
    public void addSales(int sales) {
        this.sales += sales;
    }
    public String getId(){
        return this.id;
    }

    public double getPrice() {
        return this.price;
    }
}
