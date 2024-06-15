package model;

/**
 * This class is the one that models the order objects. It is used mainly to take create a way to retain the information
 * that is important regarding the orders that were made.
 * As attributes, we have an order id, client id, product id and quantity, information important regarding an order.
 */

public class Order {
    private int orderID;
    private int clientID;
    private int productID;
    private int quantity;

    public Order(int id, int clientID, int productID, int quantity) {
        this.orderID = id;
        this.clientID = clientID;
        this.productID = productID;
        this.quantity = quantity;
    }

    public Order() {

    }

    public int getOrderID() {
        return orderID;
    }

    public int getClientID() {
        return clientID;
    }

    public int getProductID() {
        return productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
