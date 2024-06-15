package bll.validators;

import model.Product;

public class ProductValidator implements Validator<Product> {

    /**
     * @Author: Cretu Horatiu
     * @Since: may 2024
     */
    @Override
    public boolean validate(Product product) {
        if (product.getPrice() <= 0) {
            throw new IllegalArgumentException("Price value is wrong!");
        }
        if (product.getStock() <= 0) {
            throw new IllegalArgumentException("Stock value is wrong!");
        }
        return false;
    }
}
