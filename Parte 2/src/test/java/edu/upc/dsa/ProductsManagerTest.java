package edu.upc.dsa;

import edu.upc.dsa.exceptions.ProductNotFoundException;
import edu.upc.dsa.models.Product;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ProductsManagerTest {
    ProductsManager tm;

    @Before
    public void setUp() {
        this.tm = ProductsManagerImpl.getInstance();
        this.tm.addProduct("T1", "Chupa Chups", 2.5);
        this.tm.addProduct("T2", "Coca Cola", 1);
        this.tm.addProduct("T3", "Pringles", 3);
    }

    @After
    public void tearDown() {
        // És un Singleton
        this.tm.clear();
    }

    @Test
    public void addProductTest() {
        Assert.assertEquals(3, tm.size());

        this.tm.addProduct("T4", "Agua");

        Assert.assertEquals(4, tm.size());

    }

    @Test
    public void getProductTest() throws Exception {
        Assert.assertEquals(3, tm.size());

        Product t = this.tm.getProduct("T2");
        Assert.assertEquals("Coca Cola", t.getName());
        Assert.assertEquals( 1, t.getPrice(), 0);

        t = this.tm.getProduct2("T2");
        Assert.assertEquals("Coca Cola", t.getName());
        Assert.assertEquals(1, t.getPrice(), 0);

        Assert.assertThrows(ProductNotFoundException.class, () ->
                this.tm.getProduct2("XXXXXXX"));

    }

    @Test
    public void getProductsTest() {
        Assert.assertEquals(3, tm.size());
        List<Product> products  = tm.findAll();

        Product t = products.get(0);
        Assert.assertEquals("Chupa Chups", t.getName());
        Assert.assertEquals(2.5, t.getPrice(), 0);

        t = products.get(1);
        Assert.assertEquals("Coca Cola", t.getName());
        Assert.assertEquals(1, t.getPrice(), 0);

        t = products.get(2);
        Assert.assertEquals("Pringles", t.getName());
        Assert.assertEquals(3, t.getPrice(), 0);

        Assert.assertEquals(3, tm.size());

    }

    @Test
    public void updateProductTest() {
        Assert.assertEquals(3, tm.size());
        Product t = this.tm.getProduct("T1");
        Assert.assertEquals("Chupa Chups", t.getName());
        Assert.assertEquals(2.5, t.getPrice(), 0);

        t.setName("Chupa-Chups");
        this.tm.updateProduct(t);

        t = this.tm.getProduct("T1");
        Assert.assertEquals("Chupa-Chups", t.getName());
        Assert.assertEquals(2.5, t.getPrice(), 0);
    }


    @Test
    public void deleteProductTest() {
        Assert.assertEquals(3, tm.size());
        this.tm.deleteProduct("T3");
        Assert.assertEquals(2, tm.size());

        Assert.assertThrows(ProductNotFoundException.class, () ->
                this.tm.getProduct2("T3"));

    }
}
