import models.Order;
import models.Product;
import models.User;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class ProductManagerImpl implements ProductManager {
    private List<Product> productList;
    private Queue<Order> orderQueue;
    private List<User> userList;


    public ProductManagerImpl() {
        productList = new ArrayList<>();
        orderQueue = new LinkedList<>();
        userList = new ArrayList<>();
    }

    @Override
    public void addProduct(String id, String name, double price) {
        if (productList.stream().noneMatch(p -> p.getId().equals(id)))
            productList.add(new Product(id, name, price));
    }

    @Override
    public List<Product> getProductsByPrice() {
        List<Product> ordProducts = new ArrayList<>();
        ordProducts.addAll(productList);
        Collections.sort(ordProducts, Comparator.comparingDouble(Product::getPrice).reversed());
        return ordProducts;
    }

    @Override
    public void addOrder(Order order) {
        AtomicBoolean encontrado = new AtomicBoolean(false);
        orderQueue.add(order);
        Order o = new Order(order.getUser());
        order.getContenido().forEach((cantidad, id) -> {
            if (getProduct(id) != null) {
                o.addLP(cantidad, id);
            }
        });

        if (userList != null && !userList.isEmpty()) {
            userList.forEach(user -> {
                if (user != null && user.getDni().equals(order.getUser())) {
                    user.setOrder(o);
                    encontrado.set(true);
                }
            });
        }
        if (!encontrado.get()) {
            User nuevoUsuario = new User(o.getUser());
            nuevoUsuario.setOrder(o);
            userList.add(nuevoUsuario);
        }
    }


    @Override
    public int numOrders() {
        if (!orderQueue.isEmpty())
            return orderQueue.size();
        return 0;
    }

    @Override
    public Order deliverOrder() {
        Order order = orderQueue.poll();
        if (order == null) {
            return null;
        }
        order.getContenido().forEach((cantidad, id) -> {
            Product p = getProduct(id);
            if(p!=null)
                p.addSales(cantidad);
        });
        return order;
    }

    @Override
    public Product getProduct(String c1) {
        return productList.stream()
                .filter(p -> p.getId().equals(c1))
                .findFirst()
                .orElse(null);
    }

    @Override
    public User getUser(String number) {
        return userList.stream()
                .filter(usuario -> usuario.getDni().equals(number))
                .findFirst()
                .orElse(null);
    }
}
