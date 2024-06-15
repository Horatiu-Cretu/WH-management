package bll.validators;

import model.Order;

public class StockValidator implements Validator<Order> {
    /**
     * @Author: Cretu Horatiu
     * @Since: may 2024
     */
    @Override
    public boolean validate(Order order) {
        if (order.getQuantity() <= 0) {
            throw new IllegalArgumentException("The quantity value is not appropriate!");
        }
        return true;
    }
}
