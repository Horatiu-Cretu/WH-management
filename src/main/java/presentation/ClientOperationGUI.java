package presentation;

import bll.ClientBLL;
import model.Client;

import javax.swing.*;
import java.util.List;

public class ClientOperationGUI {
    private JPanel panel1;
    private JTextField clientIDField;
    private JTextField clientNameField;
    private JTextField clientAddressField;
    private JTextField clientEmailField;
    private JTextField clientAgeField;
    private JButton updateButton;
    private JButton insertButton;
    private JButton deleteButton;
    private JList list1;
    private JButton viewAllCustomersButton;
    private JFrame frame;

    public ClientOperationGUI(ClientBLL clientBLL) {
        frame = new JFrame("CLIENTS");
        frame.setContentPane(panel1);
        frame.pack();
        frame.setVisible(true);
        List<Client> clientList = clientBLL.getClientDAO().findAll();
        DefaultListModel<Client> clientDefaultListModel = new DefaultListModel<>();
        for (Client currentClient : clientList) {
            clientDefaultListModel.addElement(currentClient);
        }
        list1.setModel(clientDefaultListModel);
    }

    public JPanel getPanel1() {
        return panel1;
    }

    public void setPanel1(JPanel panel1) {
        this.panel1 = panel1;
    }

    public JTextField getClientIDField() {
        return clientIDField;
    }

    public void setClientIDField(JTextField clientIDField) {
        this.clientIDField = clientIDField;
    }

    public JTextField getClientNameField() {
        return clientNameField;
    }

    public void setClientNameField(JTextField clientNameField) {
        this.clientNameField = clientNameField;
    }

    public JTextField getClientAddressField() {
        return clientAddressField;
    }

    public void setClientAddressField(JTextField clientAddressField) {
        this.clientAddressField = clientAddressField;
    }

    public JTextField getClientEmailField() {
        return clientEmailField;
    }

    public void setClientEmailField(JTextField clientEmailField) {
        this.clientEmailField = clientEmailField;
    }

    public JTextField getClientAgeField() {
        return clientAgeField;
    }

    public void setClientAgeField(JTextField clientAgeField) {
        this.clientAgeField = clientAgeField;
    }

    public JButton getUpdateButton() {
        return updateButton;
    }

    public void setUpdateButton(JButton updateButton) {
        this.updateButton = updateButton;
    }

    public JButton getInsertButton() {
        return insertButton;
    }

    public void setInsertButton(JButton insertButton) {
        this.insertButton = insertButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(JButton deleteButton) {
        this.deleteButton = deleteButton;
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

    public JButton getViewAllCustomersButton() {
        return viewAllCustomersButton;
    }

    public void setViewAllCustomersButton(JButton viewAllCustomersButton) {
        this.viewAllCustomersButton = viewAllCustomersButton;
    }
}
