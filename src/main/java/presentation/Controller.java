package presentation;

import bll.ClientBLL;
import bll.OrderBLL;
import bll.ProductBLL;
import model.Client;
import model.Order;
import model.Product;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * The Controller class binds together everything by including all the BLL's and by controlling all the GUI's.
 * It sends the information that is inserted by the user into the model classes and then into the tables from the
 * database and also extracts them. As a conclusion it binds everything and provides the full functionality that the
 * classes are capable of.
 * As attributes, there are: the mainGUI, the BLL's that provide the availability of usage functionalities regarding
 * the work with the database. The current order id is an integer that is basically keeping track of the current orders,
 * such that each order has an id that is always right, not needing to enter it manually for each order.
 * This class controls the GUI's by providing action listeners for the buttons from them.
 */

public class Controller {
    public MainGUI mainGUI = new MainGUI();
    public ClientBLL clientBLL = new ClientBLL();
    public ProductBLL productBLL = new ProductBLL();
    public OrderBLL orderBLL= new OrderBLL();
    public int currentOrderID;

    public Controller() {
        currentOrderID = getCurrentOrderID();
        mainGUI.getClientOperationsButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientOperationGUI clientOperationGUI = new ClientOperationGUI(clientBLL);
                clientOperationGUI.getUpdateButton().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            Client clientToUpdate = (Client) clientOperationGUI.getList1().getSelectedValue();
                            Integer newClientID = Integer.parseInt(clientOperationGUI.getClientIDField().getText());
                            String newClientName = clientOperationGUI.getClientNameField().getText();
                            String newClientAddress = clientOperationGUI.getClientAddressField().getText();
                            String newClientEmail = clientOperationGUI.getClientEmailField().getText();
                            Integer newClientAge = Integer.parseInt(clientOperationGUI.getClientAgeField().getText());
                            Client updatedClient = new Client(newClientID, newClientName, newClientAddress, newClientEmail, newClientAge);
                            clientBLL.updateClient(updatedClient, clientToUpdate.getId());
                            updateClientList(clientOperationGUI.getList1());
                        } catch (IllegalArgumentException exception) {
                            clientOperationGUI.getClientIDField().setText("invalid arguments");
                        }
                    }
                });

                clientOperationGUI.getViewAllCustomersButton().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        List<Client> clientArrayList = clientBLL.getClientDAO().findAll();
                        String[][] clientData = new String[clientArrayList.size()][5];
                        String[] columnNames = new String[5];
                        columnNames[0] = "ID";
                        columnNames[1] = "Name";
                        columnNames[2] = "Address";
                        columnNames[3] = "Email";
                        columnNames[4] = "Age";
                        for (int i = 0; i < clientArrayList.size(); i++) {
                            clientData[i][0] = clientArrayList.get(i).getId() + "";
                            clientData[i][1] = clientArrayList.get(i).getName();
                            clientData[i][2] = clientArrayList.get(i).getAdress();
                            clientData[i][3] = clientArrayList.get(i).getEmail();
                            clientData[i][4] = clientArrayList.get(i).getAge() + "";
                        }
                        //ClientTable clientTable = new ClientTable(clientData, columnNames);
                        ClientTable clientTable = new ClientTable(clientBLL.getClientDAO());
                    }
                });

                clientOperationGUI.getInsertButton().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            Integer newClientID = Integer.parseInt(clientOperationGUI.getClientIDField().getText());
                            String newClientName = clientOperationGUI.getClientNameField().getText();
                            String newClientAddress = clientOperationGUI.getClientAddressField().getText();
                            String newClientEmail = clientOperationGUI.getClientEmailField().getText();
                            Integer newClientAge = Integer.parseInt(clientOperationGUI.getClientAgeField().getText());
                            Client clientToBeAdded = new Client(newClientID, newClientName, newClientAddress, newClientEmail, newClientAge);
                            clientBLL.insertClient(clientToBeAdded);
                            clientOperationGUI.getList1().removeAll();
                            updateClientList(clientOperationGUI.getList1());

                        } catch (IllegalArgumentException exception) {
                            clientOperationGUI.getClientIDField().setText("invalid arguments");
                        }
                    }
                });

                clientOperationGUI.getDeleteButton().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Integer idToDelete = Integer.parseInt(clientOperationGUI.getClientIDField().getText());
                        clientBLL.getClientDAO().deleteById(idToDelete);
                        updateClientList(clientOperationGUI.getList1());
                    }
                });
            }
        });

        mainGUI.getProductOperationsButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductOperationsGUI productOperationsGUI = new ProductOperationsGUI(productBLL);
                productOperationsGUI.getUpdateButton().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            Product productToUpdate = (Product) productOperationsGUI.getList1().getSelectedValue();
                            Integer newProductID = Integer.parseInt(productOperationsGUI.getProductIDField().getText());
                            String newProductName = productOperationsGUI.getProductNameField().getText();
                            Double newProductPrice = Double.parseDouble(productOperationsGUI.getProductPriceField().getText());
                            Integer newProductStock = Integer.parseInt(productOperationsGUI.getUnitsLeft().getText());
                            Product updatedProduct = new Product(newProductID, newProductName, newProductPrice, newProductStock);
                            productBLL.updateProduct(updatedProduct, productToUpdate.getProductID());
                            updateProductList(productOperationsGUI.getList1());
                        } catch (IllegalArgumentException exception) {
                            productOperationsGUI.getProductIDField().setText("invalid arguments");
                        }
                    }
                });

                productOperationsGUI.getViewAllProductsButton().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        List<Product> productList = productBLL.getProductDAO().findAll();
                        String[][] productData = new String[productList.size()][4];
                        String[] columnNames = new String[4];
                        columnNames[0] = "ID";
                        columnNames[1] = "Name";
                        columnNames[2] = "Price";
                        columnNames[3] = "Units Left";
                        for (int i = 0; i < productList.size(); i++) {
                            productData[i][0] = productList.get(i).getProductID() + "";
                            productData[i][1] = productList.get(i).getProductName();
                            productData[i][2] = productList.get(i).getPrice() + "";
                            productData[i][3] = productList.get(i).getStock() + "";
                        }
                        ClientTable productTable = new ClientTable(productBLL.getProductDAO());
                    }
                });

                productOperationsGUI.getInsertButton().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            Integer newProductID = Integer.parseInt(productOperationsGUI.getProductIDField().getText());
                            String newProductName = productOperationsGUI.getProductNameField().getText();
                            Double newProductPrice = Double.parseDouble(productOperationsGUI.getProductPriceField().getText());
                            Integer newProductStock = Integer.parseInt(productOperationsGUI.getUnitsLeft().getText());
                            Product productToBeAdded = new Product(newProductID, newProductName, newProductPrice, newProductStock);
                            productBLL.insertProduct(productToBeAdded);
                            productOperationsGUI.getList1().removeAll();
                            updateProductList(productOperationsGUI.getList1());

                        } catch (IllegalArgumentException exception) {
                            productOperationsGUI.getProductIDField().setText("invalid arguments");
                        }
                    }
                });

                productOperationsGUI.getDeleteButton().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Integer idToDelete = Integer.parseInt(productOperationsGUI.getProductIDField().getText());
                        productBLL.getProductDAO().deleteById(idToDelete);
                        updateProductList(productOperationsGUI.getList1());
                    }
                });
            }
        });

        mainGUI.getOrderOperationsButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OrderOperationsGUI orderOperationsGUI = new OrderOperationsGUI(clientBLL, productBLL, orderBLL);
                orderOperationsGUI.getCreateOrderButton().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Client currentClient = (Client) orderOperationsGUI.getClientList().getSelectedValue();
                        Product currentProduct = (Product) orderOperationsGUI.getProductList().getSelectedValue();
                        Integer desiredQuantity = Integer.parseInt(orderOperationsGUI.getQuantityTextField().getText());
                        //Integer desiredID = Integer.parseInt(orderOperationsGUI.getOrderIDTextField().getText());
                        Order desiredOrder = new Order(currentOrderID, currentClient.getId(), currentProduct.getProductID(), desiredQuantity);
                        try {
                            orderBLL.insertOrder(desiredOrder, productBLL);
                            currentOrderID++;
                            updateCurrentOrderID(currentOrderID);
                            updateProductList(orderOperationsGUI.getProductList());
                            updateClientList(orderOperationsGUI.getClientList());
                        } catch(IllegalArgumentException exception) {
                            orderOperationsGUI.getQuantityTextField().setText("Stock can not cover your order!");
                            exception.printStackTrace();
                        }
                    }
                });

                orderOperationsGUI.getViewAllOrdersButton().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        List<Order> orderList = orderBLL.getOrderDAO().findAll();
                        String[][] orderData = new String[orderList.size()][4];
                        String[] columnNames = new String[4];
                        columnNames[0] = "ID";
                        columnNames[1] = "Client ID";
                        columnNames[2] = "Product ID";
                        columnNames[3] = "Quantity";
                        for (int i = 0; i < orderList.size(); i++) {
                            orderData[i][0] = orderList.get(i).getOrderID() + "";
                            orderData[i][1] = orderList.get(i).getClientID() + "";
                            orderData[i][2] = orderList.get(i).getProductID() + "";
                            orderData[i][3] = orderList.get(i).getQuantity() + "";
                        }
                        ClientTable orderTable = new ClientTable(orderBLL.getOrderDAO());
                    }
                });
            }
        });
    }

    public void updateClientList(JList<Client> clientJList) {
        DefaultListModel<Client> clientDefaultListModel = new DefaultListModel<>();
        List<Client> clientList = clientBLL.getClientDAO().findAll();
        for (Client currentClient : clientList) {
            clientDefaultListModel.addElement(currentClient);
        }
        clientJList.removeAll();
        clientJList.setModel(clientDefaultListModel);
    }

    public void updateProductList(JList<Product> productJList) {
        DefaultListModel<Product> productDefaultListModel = new DefaultListModel<>();
        List<Product> productList = productBLL.getProductDAO().findAll();
        for (Product currentProduct : productList) {
            productDefaultListModel.addElement(currentProduct);
        }
        productJList.removeAll();
        productJList.setModel(productDefaultListModel);
    }

    /**
     *<p>
     *     The method restores the current order id from a file at the begining of the application, as serialization
     *     has not been used.
     *</p>
     * @return the stored id for orders in the file
     */
    public int getCurrentOrderID() {
        File file = new File("order_id_file.txt");
        try {
            Scanner scanner = new Scanner(file);
            Integer currentOrderID = Integer.parseInt(scanner.nextLine());
            scanner.close();
            return currentOrderID;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * <p>
     *     The method takes as parameter the updated current order id from the Controller and stores it into a file, to
     *     be later recovered by the method from above.
     * </p>
     * @param currentOrderID the updated current order id from the Controller to be stored for later
     */
    public void updateCurrentOrderID(int currentOrderID) {
        File file = new File("order_id_file.txt");
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(currentOrderID + "");
            fileWriter.close();
        } catch (IOException exception) {
            System.out.println("Can not write the current order ID!");
            exception.printStackTrace();
        }
    }

}
