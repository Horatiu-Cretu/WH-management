package bll;

import bll.validators.StockValidator;
import bll.validators.Validator;
import dao.OrderDAO;
import model.Order;
import model.Product;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class OrderBLL {
    private List<Validator<Order>> validators;
    private OrderDAO orderDAO;

    public OrderBLL() {
        this.validators = new ArrayList<>();
        validators.add(new StockValidator());
        this.orderDAO = new OrderDAO();
    }

    /**
     * this method inserts an order in the db
     * @param order
     * @param productBLL
     * @return order to be inserted
     * @throws IllegalArgumentException
     */
    public Order insertOrder(Order order, ProductBLL productBLL) throws IllegalArgumentException{

            for (Validator<Order> currentValidator : validators) {
                currentValidator.validate(order);
            }

                checkIfAvailable(order, productBLL);
                Product product = productBLL.findProductById(order.getProductID());
                product.setStock(product.getStock() - order.getQuantity());
                productBLL.updateProduct(product, product.getProductID());
                orderDAO.insert(order);
                createBill(order, product);
        return order;
    }

    /**
     *
     * @param order
     * @param productBLL
     * @return true of false if the product is available
     * @throws IllegalArgumentException
     */
    public boolean checkIfAvailable(Order order, ProductBLL productBLL) throws IllegalArgumentException{
        if (productBLL.findProductById(order.getProductID()).equals(null)) {
            throw new IllegalArgumentException("Ordered product does not exist!");
        }
        Product searchedProduct = productBLL.findProductById(order.getProductID());
        if (searchedProduct.getStock() < order.getQuantity()) {
            throw new IllegalArgumentException("Not enough in stock!");
        }
        return true;
    }

    /**
     * this method writes in a file the order
     * @param order
     * @param product
     */
    public void createBill(Order order, Product product) {
        String billName = "order_no_" + order.getOrderID() +  "_" + LocalDate.now().toString();
        File file = new File(billName);
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.append(LocalDateTime.now().toString());
            fileWriter.append("\n");
            fileWriter.append(order.getProductID() + " " + product.getProductName() + " ... " + product.getPrice() * order.getQuantity() + "\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public OrderDAO getOrderDAO() {
        return orderDAO;
    }

}