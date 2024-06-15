package model;

/**
 * This class is the one that models the product objects. It is used mainly to take the data from and to put it into the
 * table of products.
 * As attributes, we have a product id, name, price and stock, information that would be important regarding
 * the product identity in the table.
 */

public class Product {
    private int productID;
    private String productName;
    private Double price;
    private int stock;

    public Product() {

    }

    public Product(int productID, String productName, Double price, int stock) {
        this.productID = productID;
        this.productName = productName;
        this.price = price;
        this.stock = stock;
    }

    public String getProductName() {
        return productName;
    }

    public Double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productID='" + productID + '\'' +
                "productName='" + productName + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }
}
