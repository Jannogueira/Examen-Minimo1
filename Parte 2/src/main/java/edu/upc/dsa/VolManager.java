package edu.upc.dsa;

import edu.upc.dsa.exceptions.VolNotFoundException;
import edu.upc.dsa.models.Product;

import java.util.List;

public interface VolManager {


    public Product addProduct(String id, String name, double price);
    public Product addProduct(String id, String name);
    public Product addProduct(Product t);
    public Product getProduct(String id);
    public Product getProduct2(String id) throws VolNotFoundException;

    public List<Product> findAll();
    public void deleteProduct(String id);
    public Product updateProduct(Product t);

    public void clear();
    public int size();
}
