package presentation;

import dao.AbstractDAO;

import javax.swing.*;
import java.lang.reflect.Field;
import java.util.List;

/**
 * This class models the custom table that is displayed in the GUI for all the three types of operations: client,
 * product and order. It uses a reflection method that is inserting the column names into the table and also the data,
 * by iterating over the field of each row from the table, identify either the field name, or the value, depending on
 * the situation, wanting a header or not.
 * As attributes, we have an JFrame and a JTable that would represent the data that we want to present.
 */

public class ClientTable {
    JFrame frame;
    JTable jTable;

    public ClientTable(AbstractDAO abstractDAO) {
        List<Object> objectList = abstractDAO.findAll();
        if (objectList.equals(null)) {
            throw new IllegalArgumentException("The table is null!");
        }
        Class type = objectList.get(0).getClass();
        String[] namesForColumns = new String[type.getDeclaredFields().length];
        String[][] dataForTable = new String[objectList.size()][type.getDeclaredFields().length];

        int i = 0;

        for (Field currentField : type.getDeclaredFields()) {
            namesForColumns[i] = currentField.getName();
            i++;
        }

        i = 0;
        int j;
        for (Object currentObject : objectList) {
            j = 0;
            for (Field field : type.getDeclaredFields()) {
                try {
                    field.setAccessible(true);
                    dataForTable[i][j] = field.get(currentObject).toString();
                } catch (IllegalAccessException exception) {
                    System.out.println("Problem when generating the table!");
                    exception.printStackTrace();
                }
                j++;
            }
            i++;
        }
        frame = new JFrame();
        frame.setTitle("CLIENT_TABLE");
        //jTable = new JTable(clientData, columnNames);
        jTable = new JTable(dataForTable, namesForColumns);
        JScrollPane jScrollPane = new JScrollPane(jTable);
        frame.add(jScrollPane);
        frame.setSize(500, 200);
        frame.setVisible(true);

    }
}
