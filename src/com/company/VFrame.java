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

public class VFrame extends JFrame {
    private static JTable table1;
    private static String path = "videos.ser";
    private DefaultTableModel model;
    private static Object[] columnsHeader = new String[]{"ID", "Режим відео", "Посилання"};
    private TableColumnModel columnModel;
    private static Object[][] array = new Object[][]{{1, "Фаст", "https://www.meme-arsenal.com/memes/5a6bbdb411444d12607a0ef8b0603917.jpg"}};
    public static Object[][] getArr(){
        return array;
    }
    public static int getNewId(){
        int id;
        if(array.length != 0)
            id = (Integer) array[array.length-1][0];
        else id = 0;
        return id+1;
    }
    public static void add(Video v){
        int length = array.length + 1;
        Object[][] newArray = new Object[length][3];
        for(int i = 0; i < array.length; i++)
            newArray[i]=array[i];
        newArray[length-1] = new Object[]{v.getId(), v.getVMode(), v.getLink()};
        array = newArray.clone();
        for(int i = 0; i < length; i++)
            System.out.println(newArray[i][0] + " " + newArray[i][1] + " " + newArray[i][2]);
        new VFrame();
    }

    public static void edit(Video v){

        for(int i = 0; i < array.length; i++)
            if((Integer)array[i][0] == v.getId()) {
                array[i][2] = v.getLink();
                array[i][1] = v.getVMode();
            }
        new VFrame();
    }

    public VFrame() {
        super("Відео");
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
        JButton add = new JButton("Додати нове відео");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VAdd();
                dispose();
            }
        });

        add.setBackground( new Color(172, 227, 132) );

        JButton delete = new JButton("Видалити виділене відео");
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
                        new VFrame();
                        return;
                    }
                    Object[][] newArray = new Object[length][3];
                    boolean leo;
                    for (int i = array.length - 1, j = length - 1; j >= 0; i--) {
                        leo = false;
                        for (int s = count-1; s >= 0; s--) {
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
                    new VFrame();
                }else{
                    System.out.println("Помилка");
                    JOptionPane.showMessageDialog(VFrame.this, "Ви не вибрали рядок, який потрібно видалити",
                            "Помилка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        delete.setBackground( new Color(251, 165, 150) );
        JButton edit = new JButton("Редагувати виділене відео");
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int sRow = table1.getSelectedRow();
                if(sRow!=-1) {
                    new VEdit(array[sRow][0], array[sRow][1], array[sRow][2]);
                    dispose();
                }else{
                    System.out.println("Помилка");
                    JOptionPane.showMessageDialog(VFrame.this, "Ви не вибрали рядок, який потрібно відредагувати",
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
                    ArrayList<Video> arr = new ArrayList<>();
                    for (int i = 0; i < array.length; i++) {
                        Video temp = new Video((Integer)array[i][0], (String) array[i][1],(String) array[i][2]);
                        arr.add((temp) );
                    }
                    System.out.println(arr.size());
                    FileOutputStream fos = new FileOutputStream(path);
                    ObjectOutputStream out = new ObjectOutputStream(fos);

                    for (int i = 0; i < arr.size(); i++) {
                        System.out.println(arr.get(i).getId() + " " + arr.get(i).getVMode() + " " + arr.get(i).getLink());
                    }
                    for (int i = 0; i < arr.size(); i++) {
                        out.writeObject(arr.get(i));
                    }

                    out.close();
                    fos.close();
                    JOptionPane.showMessageDialog(VFrame.this, "Запис в файл пройшов успішно",
                            "Важливо", JOptionPane.INFORMATION_MESSAGE);
                }catch (IOException i){
                    System.out.println("Помилка серіалізації");
                    JOptionPane.showMessageDialog(VFrame.this, "Помилка серіалізації",
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
                    ArrayList<Video> arr = new ArrayList<>();
                    Video v = (Video)in.readObject();
                    do{
                        arr.add(v);
                        try {
                            System.out.println("+"+v.getId());
                            v = (Video) in.readObject();
                        }catch (IOException ee){
                            Object[][] newArray = new Object[arr.size()][3];
                            for (int i = 0;i < arr.size(); i++) {
                                newArray[i][0] = arr.get(i).getId();
                                newArray[i][1] = arr.get(i).getVMode();
                                newArray[i][2] = arr.get(i).getLink();
                            }
                            array = newArray.clone();
                            JOptionPane.showMessageDialog(VFrame.this, "Читання з в файлу пройшло успішно",
                                    "Важливо", JOptionPane.INFORMATION_MESSAGE);
                            dispose();
                            new VFrame();
                            in.close();
                            fis.close();

                            return;
                        }
                    }while (v != null);


                }catch (IOException | ClassNotFoundException ee){
                    System.out.println("Помилка десеріалізації");
                    JOptionPane.showMessageDialog(VFrame.this, "Помилка десеріалізації",
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
                    JOptionPane.showMessageDialog(VFrame.this, "Файл " + file.getName() + " очищено",
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
        setSize(600, 300);
        setVisible(true);
    }
}
