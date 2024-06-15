package bll;

import bll.validators.ProductValidator;
import bll.validators.Validator;
import dao.ProductDAO;
import model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ProductBLL {

    private List<Validator<Product>> validators;
    private ProductDAO productDAO;

    public ProductBLL() {
        validators = new ArrayList<>();
        validators.add(new ProductValidator());
        productDAO = new ProductDAO();
    }

    public Product findProductById(int id) {
        Product auxProduct = new Product();
        Product product = productDAO.findById(auxProduct, id);
        if (product == null) {
            throw new NoSuchElementException("The product with id =" + id + " was not found!");
        }
        return product;
    }

    /**
     *
     * @param product
     * @return inserted product
     */
    public Product insertProduct(Product product) {
        try {
            for (Validator<Product> currentValidator : validators) {
                currentValidator.validate(product);
            }
            productDAO.insert(product);
        } catch (Exception e) {
            System.out.println("Could not insert product!\n");
            e.printStackTrace();
        }
        return product;
    }

    /**
     *
     * @param product
     * @param desiredProductID
     * @return updated product
     */
    public Product updateProduct(Product product, int desiredProductID) {
        try {
            for (Validator<Product> currentValidator : validators) {
                currentValidator.validate(product);
            }
            productDAO.update(product, desiredProductID);
        } catch (Exception e) {
            System.out.println("Could not update product!\n");
            e.printStackTrace();
        }
        return product;
    }

    public ProductDAO getProductDAO() {
        return productDAO;
    }

}
