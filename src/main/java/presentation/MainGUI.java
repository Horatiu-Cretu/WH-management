package presentation;

import javax.swing.*;
import java.awt.*;

public class MainGUI {
    private JButton clientOperationsButton;
    private JPanel panel1;
    private JButton productOperationsButton;
    private JButton orderOperationsButton;
    private JFrame frame;

    public MainGUI() {
        frame = new JFrame("MAINGUI");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public JButton getClientOperationsButton() {
        return clientOperationsButton;
    }

    public void setClientOperationsButton(JButton clientOperationsButton) {
        this.clientOperationsButton = clientOperationsButton;
    }

    public JPanel getPanel1() {
        return panel1;
    }

    public void setPanel1(JPanel panel1) {
        this.panel1 = panel1;
    }

    public JButton getProductOperationsButton() {
        return productOperationsButton;
    }

    public void setProductOperationsButton(JButton productOperationsButton) {
        this.productOperationsButton = productOperationsButton;
    }

    public JButton getOrderOperationsButton() {
        return orderOperationsButton;
    }

    public void setOrderOperationsButton(JButton orderOperationsButton) {
        this.orderOperationsButton = orderOperationsButton;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }
}
