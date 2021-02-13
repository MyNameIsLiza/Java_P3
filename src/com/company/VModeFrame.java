package com.company;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

public class VModeFrame extends JFrame {
    private static JTable table1;
    private static Object[] columnsHeader = new String[]{"ID", "Назва", "Модифікатор швидкості"};
    private TableColumnModel columnModel;
    private static Object[][] array = new String[][]{{"1", "Фаст", "3"}};
    public static int get_size(){
        return array.length;
    }
    public static int getNewId(){
        int id = Integer.parseInt((String) array[array.length-1][0]);
        return id+1;
    }
    public static String[] getVModes(){
        String[] vMode = new String[array.length];
        for(int i = 0; i < array.length; i++) {
            vMode[i]= String.valueOf(array[i][1]);
        }
        return vMode;
    }
    public static void add(VideoMode v){
        int length = array.length + 1;
        Object[][] newArray = new Object[length][3];
        for(int i = 0; i < array.length; i++)
            newArray[i]=array[i];
        newArray[length-1] = new Object[]{v.getId(), v.getName(), v.getSpeed()};
        array = newArray.clone();
        for(int i = 0; i < length; i++)
            System.out.println(newArray[i][0] + " " + newArray[i][1] + " " + newArray[i][2]);
        new VModeFrame();
    }
    public VModeFrame() {
        super("Режим відео");
        //setMinimumSize();
        table1 = new JTable(array, columnsHeader){
            public boolean editCellAt(int row, int column, java.util.EventObject e){
                return false;
            }
        };
        columnModel = table1.getColumnModel();
        Enumeration<TableColumn> e = columnModel.getColumns();
        while (e.hasMoreElements()) {
            TableColumn column = (TableColumn) e.nextElement();
            column.setMinWidth(50);
            //column.setMaxWidth(200);
        }
        Box contents = new Box(BoxLayout.Y_AXIS);
        contents.add(new JScrollPane(table1));
        JButton add = new JButton("Додати новий режим відео");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VModeAdd();
                dispose();
            }
        });

        JPanel pnlButtons = new JPanel();
        pnlButtons.add(add);
        columnModel.addColumnModelListener(new TableColumnModelListener() {
            @Override
            public void columnAdded(TableColumnModelEvent arg0) {
                System.out.println("TableColumnModelListener.columnAdded()");
            }

            @Override
            public void columnMarginChanged(ChangeEvent arg0) {
                System.out.println("TableColumnModelListener.columnMarginChanged()");
            }

            @Override
            public void columnMoved(TableColumnModelEvent arg0) {
                System.out.println("TableColumnModelListener.columnMoved()");
            }

            @Override
            public void columnRemoved(TableColumnModelEvent arg0) {
            }

            @Override
            public void columnSelectionChanged(ListSelectionEvent arg0) {
                System.out.println("TableColumnModelListener.columnSelectionChanged()");
            }

        });
        //columnModel.setColumnSelectionAllowed(false);
        table1.setRowSelectionAllowed(false);
        getContentPane().add(contents);
        getContentPane().add(pnlButtons, BorderLayout.SOUTH);
        setSize(480, 300);
        setVisible(true);
    }
}
