package com.company;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;

public class VModeFrame extends JFrame {
    private static JTable table1;
    private static String path = "video modes.ser";
    private DefaultTableModel model;
    private static Object[] columnsHeader = new String[]{"ID", "Назва", "Модифікатор швидкості"};
    private TableColumnModel columnModel;
    private static Object[][] array = new Object[][]{{1, "Фаст", 3.0}};
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
    public static void edit(VideoMode vm){

        for(int i = 0; i < array.length; i++)
            if((Integer)array[i][0] == vm.getId()) {
                array[i][1] = vm.getName();
                array[i][2] = vm.getSpeed();
            }
        new VModeFrame();
    }
    public VModeFrame() {
        super("Режим відео");
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
        add.setBackground( new Color(172, 227, 132) );
        JButton delete = new JButton("Видалити виділений режим");
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int count = table1.getSelectedRows().length;
                System.out.println("Selected rows count = " + count);
                if(count != -1) {
                    for (int s = 0; s < count; s++)
                        System.out.println(table1.getSelectedRows()[s]+1);
                    int length = array.length - count;
                    if (length == 0) {
                        Object[][] newArray = new Object[length][3];
                        array = newArray.clone();
                        for (int i = 0; i < array.length; i++)
                            model.removeRow(0);
                        dispose();
                        new VModeFrame();
                        return;
                    }
                    Object[][] newArray = new Object[length][3];
                    boolean leo;
                    for (int i = array.length - 1, j = length - 1; j >= 0; i--) {
                        leo = false;
                        for (int s = count-1; s >= 0; s--) {
                            System.out.print("s = " + table1.getSelectedRows()[s] + ", ");
                            if (i != table1.getSelectedRows()[s]) {
                            } else {
                                leo = true;
                                break;
                            }
                        }
                        if (!leo) {
                            newArray[j] = array[i];
                            j--;
                            leo = false;
                        }
                    }
                    array = newArray.clone();
                    dispose();
                    new VModeFrame();
                }else{
                    System.out.println("Помилка");
                    JOptionPane.showMessageDialog(VModeFrame.this, "Ви не вибрали рядок, який потрібно видалити",
                            "Помилка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        delete.setBackground( new Color(251, 165, 150) );
        JButton edit = new JButton("Редагувати виділений режим");
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int sRow = table1.getSelectedRow();
                if(sRow!=-1) {
                    new VModeEdit(array[sRow][0], array[sRow][1], array[sRow][2]);
                    dispose();
                }else{
                    System.out.println("Помилка");
                    JOptionPane.showMessageDialog(VModeFrame.this, "Ви не вибрали рядок, який потрібно відредагувати",
                            "Помилка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        edit.setBackground( new Color(172, 227, 255) );
        JButton serialize = new JButton("Записати в файл");
        serialize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ArrayList<VideoMode> arr = new ArrayList<>();
                    for (int i = 0; i < array.length; i++) {
                        VideoMode temp = new VideoMode((Integer)array[i][0], (String) array[i][1],(Double) array[i][2]);
                        arr.add(temp);
                    }
                    System.out.println(arr.size());
                    FileOutputStream fos = new FileOutputStream(path);
                    ObjectOutputStream out = new ObjectOutputStream(fos);

                    for (int i = 0; i < arr.size(); i++) {
                        System.out.println(arr.get(i).getId() + " " + arr.get(i).getName() + " " + arr.get(i).getSpeed());
                    }
                    for (int i = 0; i < arr.size(); i++) {
                        out.writeObject(arr.get(i));
                    }

                    out.close();
                    fos.close();
                    JOptionPane.showMessageDialog(VModeFrame.this, "Запис в файл пройшов успішно",
                            "Важливо", JOptionPane.INFORMATION_MESSAGE);
                }catch (IOException i){
                    System.out.println("Помилка серіалізації");
                    JOptionPane.showMessageDialog(VModeFrame.this, "Помилка серіалізації",
                            "Помилка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        serialize.setBackground( new Color(255, 173, 93) );
        JButton deserialize = new JButton("Зчитати з файла");
        deserialize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    FileInputStream fis = new FileInputStream(path);
                    ObjectInputStream in = new ObjectInputStream(fis);
                    ArrayList<VideoMode> arr = new ArrayList<>();
                    VideoMode vMode = (VideoMode)in.readObject();
                    do{
                        arr.add(vMode);
                        try {
                            System.out.println("+"+vMode.getId());
                            vMode = (VideoMode) in.readObject();
                        }catch (IOException ee){
                            Object[][] newArray = new Object[arr.size()][3];
                            for (int i = 0;i < arr.size(); i++) {
                                newArray[i][0] = arr.get(i).getId();
                                newArray[i][1] = arr.get(i).getName();
                                newArray[i][2] = arr.get(i).getSpeed();
                            }
                            array = newArray.clone();
                            JOptionPane.showMessageDialog(VModeFrame.this, "Читання з в файлу пройшло успішно",
                                    "Важливо", JOptionPane.INFORMATION_MESSAGE);
                            dispose();
                            new VModeFrame();
                            in.close();
                            fis.close();

                            return;
                        }
                    }while (vMode != null);


                }catch (IOException | ClassNotFoundException ee){
                    System.out.println("Помилка десеріалізації");
                    JOptionPane.showMessageDialog(VModeFrame.this, "Помилка десеріалізації",
                            "Помилка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        deserialize.setBackground( new Color(255, 215, 93) );
        JButton clearFile = new JButton("Очистити файл");
        clearFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    File file = new File(path);
                    file.delete();
                    JOptionPane.showMessageDialog(VModeFrame.this, "Файл " + file.getName() + " очищено",
                            "Важливо", JOptionPane.INFORMATION_MESSAGE);
                }catch (Exception i){
                    System.out.println("Помилка відкриття файлу");
                }

            }
        });
        clearFile.setBackground( new Color(215, 149, 130) );
        table1.setBackground(new Color(219, 209, 236));
        ////////////////////////
        JPanel pnlButtons = new JPanel();
        pnlButtons.add(add);
        ////////////////////////
        pnlButtons.add(edit);
        pnlButtons.add(delete);
        pnlButtons.setBackground(new Color(219, 209, 236));
        JPanel pnlSerialization = new JPanel();
        pnlSerialization.add(serialize);
        pnlSerialization.add(deserialize);
        pnlSerialization.add(clearFile);
        pnlSerialization.setBackground(new Color(238, 209, 196));
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
        getContentPane().add(contents);
        getContentPane().add(pnlSerialization, BorderLayout.NORTH);
        getContentPane().add(pnlButtons, BorderLayout.SOUTH);
        setMinimumSize(new Dimension(600, 300));
        setSize(635, 300);
        setVisible(true);
    }
}
