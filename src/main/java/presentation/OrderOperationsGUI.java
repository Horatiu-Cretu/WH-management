package presentation;

import bll.ClientBLL;
import bll.OrderBLL;
import bll.ProductBLL;
import model.Client;
import model.Product;

import javax.swing.*;
import java.util.List;

public class OrderOperationsGUI {
    private JPanel panel1;
    private JList clientList;
    private JList productList;
    private JButton createOrderButton;
    private JTextField quantityTextField;
    private JButton viewAllOrdersButton;
    private JTextField orderIDTextField;
    private JFrame frame;

    public OrderOperationsGUI(ClientBLL clientBLL, ProductBLL productBLL, OrderBLL orderBLL) {
        frame = new JFrame("ORDERS");
        frame.setContentPane(panel1);
        frame.pack();
        frame.setVisible(true);
        List<Client> clientList = clientBLL.getClientDAO().findAll();
        List<Product> productList = productBLL.getProductDAO().findAll();
        DefaultListModel<Client> clientDefaultListModel =  new DefaultListModel<>();
        DefaultListModel<Product> productDefaultListModel =  new DefaultListModel<>();
        for (Client currentClient : clientList) {
            clientDefaultListModel.addElement(currentClient);
        }
        for (Product currentProduct : productList) {
            productDefaultListModel.addElement(currentProduct);
        }
        this.clientList.setModel(clientDefaultListModel);
        this.productList.setModel(productDefaultListModel);

    }

    public JPanel getPanel1() {
        return panel1;
    }

    public void setPanel1(JPanel panel1) {
        this.panel1 = panel1;
    }

    public JList getClientList() {
        return clientList;
    }

    public void setClientList(JList clientList) {
        this.clientList = clientList;
    }

    public JList getProductList() {
        return productList;
    }

    public void setProductList(JList productList) {
        this.productList = productList;
    }

    public JButton getCreateOrderButton() {
        return createOrderButton;
    }

    public void setCreateOrderButton(JButton createOrderButton) {
        this.createOrderButton = createOrderButton;
    }

    public JTextField getQuantityTextField() {
        return quantityTextField;
    }

    public void setQuantityTextField(JTextField quantityTextField) {
        this.quantityTextField = quantityTextField;
    }

    public JButton getViewAllOrdersButton() {
        return viewAllOrdersButton;
    }

    public void setViewAllOrdersButton(JButton viewAllOrdersButton) {
        this.viewAllOrdersButton = viewAllOrdersButton;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public JTextField getOrderIDTextField() {
        return orderIDTextField;
    }

    public void setOrderIDTextField(JTextField orderIDTextField) {
        this.orderIDTextField = orderIDTextField;
    }
}
