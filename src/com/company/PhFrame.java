package com.company;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

public class PhFrame extends JFrame {
    private static JTable table1;
    private DefaultTableModel model;
    private static Object[] columnsHeader = new String[]{"ID", "Режим зйомки", "Посилання"};
    private TableColumnModel columnModel;
    private static Object[][] array = new Object[][]{{1, "Портрет", "https://www.meme-arsenal.com/memes/5a6bbdb411444d12607a0ef8b0603917.jpg"}};
    public static int get_size(){
        return array.length;
    }
    public static int getNewId(){
        int id;
        if(array.length != 0)
        id = (Integer) array[array.length-1][0];
        else id = 0;
        return id+1;
    }

    public static void add(Photo ph){
        int length = array.length + 1;
        Object[][] newArray = new Object[length][3];
        for(int i = 0; i < array.length; i++)
            newArray[i]=array[i];
        newArray[length-1] = new Object[]{ph.getId(), ph.getShMode(), ph.getLink()};
        array = newArray.clone();
        new PhFrame();
    }
    public static void edit(Photo ph){

        for(int i = 0; i < array.length; i++)
            if((Integer)array[i][0] == ph.getId()) {
                array[i][1] = ph.getLink();
                array[i][2] = ph.getShMode();
            }
            //newArray[i]=array[i];

        new PhFrame();
    }
    public PhFrame() {
        super("Знімки");

        model = new DefaultTableModel(array, columnsHeader);
        table1 = new JTable(model){
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
            public boolean moveColumnAt(int column, java.util.EventObject e) {
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
        JButton add = new JButton("Додати новий знімок");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PhAdd();
                dispose();
            }
        });
        JButton delete = new JButton("Видалити виділений знімок");
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(table1.getSelectedRow()!=-1) {
                    int length = array.length - 1;
                    Object[][] newArray = new Object[length][3];
                    for (int i = 0, j = 0; i < array.length; i++)
                        if (i != table1.getSelectedRow()) {
                            newArray[j] = array[i];
                            j++;
                        } else model.removeRow(table1.getSelectedRow());
                    array = newArray.clone();
                }else{
                    System.out.println("Помилка");
                    JOptionPane.showMessageDialog(PhFrame.this, "Ви не вибрали рядок, який потрібно видалити",
                            "Помилка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        JButton edit = new JButton("Редагувати виділений знімок");
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int sRow = table1.getSelectedRow();
                if(sRow!=-1) {

                    new PhEdit(array[sRow][0], array[sRow][1], array[sRow][2]);
                    dispose();
                    /*int length = array.length - 1;
                    Object[][] newArray = new Object[length][3];
                    for (int i = 0, j = 0; i < array.length; i++)
                        if (i != table1.getSelectedRow()) {
                            newArray[j] = array[i];
                            j++;
                        } else model.removeRow(table1.getSelectedRow());
                    array = newArray.clone();*/
                }else{
                    System.out.println("Помилка");
                    JOptionPane.showMessageDialog(PhFrame.this, "Ви не вибрали рядок, який потрібно відредагувати",
                            "Помилка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        JPanel pnlButtons = new JPanel();
        pnlButtons.add(add);
        pnlButtons.add(edit);
        pnlButtons.add(delete);
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
        setMinimumSize(new Dimension(600, 300));
        getContentPane().add(contents);
        getContentPane().add(pnlButtons, BorderLayout.SOUTH);
        setMinimumSize(new Dimension(600, 300));
        setSize(600, 300);
        setVisible(true);
    }

}
