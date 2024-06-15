package presentation;

import bll.ProductBLL;
import model.Client;
import model.Product;

import javax.swing.*;
import java.util.List;

public class ProductOperationsGUI {
    private JPanel panel1;
    private JTextField productNameField;
    private JTextField productPriceField;
    private JTextField unitsLeft;
    private JTextField productIDField;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton insertButton;
    private JList list1;
    private JButton viewAllProductsButton;
    private JFrame frame;

    public ProductOperationsGUI(ProductBLL productBLL) {
        frame = new JFrame("PRODUCTS");
        frame.setContentPane(panel1);
        frame.pack();
        frame.setVisible(true);
        List<Product> productList = productBLL.getProductDAO().findAll();
        DefaultListModel<Product> productDefaultListModel =  new DefaultListModel<>();
        for (Product currentProduct : productList) {
            productDefaultListModel.addElement(currentProduct);
        }
        list1.setModel(productDefaultListModel);
    }

    public JPanel getPanel1() {
        return panel1;
    }

    public void setPanel1(JPanel panel1) {
        this.panel1 = panel1;
    }

    public JTextField getProductNameField() {
        return productNameField;
    }

    public void setProductNameField(JTextField productNameField) {
        this.productNameField = productNameField;
    }

    public JTextField getProductPriceField() {
        return productPriceField;
    }

    public void setProductPriceField(JTextField productPriceField) {
        this.productPriceField = productPriceField;
    }

    public JTextField getUnitsLeft() {
        return unitsLeft;
    }

    public void setUnitsLeft(JTextField unitsLeft) {
        this.unitsLeft = unitsLeft;
    }

    public JTextField getProductIDField() {
        return productIDField;
    }

    public void setProductIDField(JTextField productIDField) {
        this.productIDField = productIDField;
    }

    public JButton getUpdateButton() {
        return updateButton;
    }

    public void setUpdateButton(JButton updateButton) {
        this.updateButton = updateButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(JButton deleteButton) {
        this.deleteButton = deleteButton;
    }

    public JButton getInsertButton() {
        return insertButton;
    }

    public void setInsertButton(JButton insertButton) {
        this.insertButton = insertButton;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public JList getList1() {
        return list1;
    }

    public void setList1(JList list1) {
        this.list1 = list1;
    }

    public JButton getViewAllProductsButton() {
        return viewAllProductsButton;
    }

    public void setViewAllProductsButton(JButton viewAllProductsButton) {
        this.viewAllProductsButton = viewAllProductsButton;
    }
}
