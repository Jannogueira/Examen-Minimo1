package edu.upc.dsa;

import edu.upc.dsa.exceptions.VolNotFoundException;
import edu.upc.dsa.models.Product;

import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;

public class VolManagerImpl implements VolManager {
    private static VolManager instance;
    protected List<Product> products;
    final static Logger logger = Logger.getLogger(VolManagerImpl.class);

    private VolManagerImpl() {
        this.products = new LinkedList<>();
    }

    public static VolManager getInstance() {
        if (instance==null) instance = new VolManagerImpl();
        return instance;
    }

    public int size() {
        int ret = this.products.size();
        logger.info("size " + ret);

        return ret;
    }

    public Product addProduct(Product t) {
        logger.info("new Product " + t);

        this.products.add (t);
        logger.info("new Product added");
        return t;
    }

    public Product addProduct(String id, String name){
        return this.addProduct(id, name, 0);
    }

    public Product addProduct(String id, String name, double price) {
        return this.addProduct(new Product(id, name, price));
    }

    public Product getProduct(String id) {
        logger.info("getProduct("+id+")");

        for (Product t: this.products) {
            if (t.getId().equals(id)) {
                logger.info("getProduct("+id+"): "+t);

                return t;
            }
        }

        logger.warn("not found " + id);
        return null;
    }

    public Product getProduct2(String id) throws VolNotFoundException {
        Product t = getProduct(id);
        if (t == null) throw new VolNotFoundException();
        return t;
    }


    public List<Product> findAll() {
        return this.products;
    }

    @Override
    public void deleteProduct(String id) {

        Product t = this.getProduct(id);
        if (t==null) {
            logger.warn("not found " + t);
        }
        else logger.info(t+" deleted ");

        this.products.remove(t);

    }

    @Override
    public Product updateProduct(Product p) {
        Product t = this.getProduct(p.getId());

        if (t!=null) {
            logger.info(p+" rebut!!!! ");

            t.setPrice(p.getPrice());
            t.setName(p.getName());

            logger.info(t+" updated ");
        }
        else {
            logger.warn("not found "+p);
        }

        return t;
    }

    public void clear() {
        this.products.clear();
    }
}