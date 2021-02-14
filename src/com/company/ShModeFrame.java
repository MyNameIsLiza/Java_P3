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

import static java.lang.System.out;

public class ShModeFrame extends JFrame {
    private static JTable table1;
    private static String path = "shooting modes.ser";
    private DefaultTableModel model;
    private static Object[] columnsHeader = new Object[]{"ID", "Назва", "Фіксація"};
    // Модель столбцов таблицы
    private TableColumnModel columnModel;
    // Данные для таблиц
    private static Object[][] array = new Object[][]{{1, "Портрет", 1},
            {2, "Панорама", 0},
            {3, "Звичайне фото", 0}};
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
    public static String[] getShModes(){
        String[] shMode = new String[array.length];
        for(int i = 0; i < array.length; i++) {
            shMode[i]= String.valueOf(array[i][1]);
        }
        return shMode;
    }

    public static void add(ShootingMode sh){
        int length = array.length + 1;
        Object[][] newArray = new Object[length][3];
        for(int i = 0; i < array.length; i++)
            newArray[i]=array[i];
        //newArray = array.clone();
        newArray[length-1] = new Object[]{sh.getId(), sh.getName(), sh.isFixation()?1:0};
        //newArray[length] = new Object[]{sh.getId(), sh.getName(), sh.isFixation()};
        //System.out.println(newArray[length-1][0] + " " + newArray[length-1][1] + " " + newArray[length-1][2]);
        array = newArray.clone();
        for(int i = 0; i < length; i++)
            out.println(newArray[i][0] + " " + newArray[i][1] + " " + newArray[i][2]);
        new ShModeFrame();
    }
    public static void edit(ShootingMode shm){

        for(int i = 0; i < array.length; i++)
            if((Integer)array[i][0] == shm.getId()) {
                array[i][1] = shm.getName();
                array[i][2] = shm.isFixation()?1:0;
            }
        new ShModeFrame();
    }
    public ShModeFrame() {
        super("Режим зйомки");
        //setDefaultCloseOperation();
        // Создание таблицы
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

        // Определение минимального и максимального размеров столбцов
        Enumeration<TableColumn> e = columnModel.getColumns();
        while (e.hasMoreElements()) {
            TableColumn column = (TableColumn) e.nextElement();
            column.setMinWidth(50);
            //column.setMaxWidth(200);
        }
        // Таблица с автонастройкой размера последней колонки
        //JTable table2 = new JTable(3, 5);
        //table2.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        // Размещение таблиц в панели с блочным расположением
        Box contents = new Box(BoxLayout.Y_AXIS);
        contents.add(new JScrollPane(table1));
        //contents.add(new JScrollPane(table2));

        // Кнопка добавления колонки в модель TableColumnModel
        JButton add = new JButton("Додати новий режим зйомки");
        // Слушатель обработки события
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ShModeAdd();
                dispose();
                // Добавление столбца к модели TableColumnModel
                //TableView.TableRow row = new TableView.TableRow(3);
                //table1.add
                //TableColumn сolumn = new TableColumn(3, 50);
                //сolumn.setHeaderValue("<html><b>Цена</b></html>");
                //columnModel.addColumn(сolumn);
            }
        });
        add.setBackground( new Color(172, 227, 132) );
        // Кнопка перемещения колонки
        //JButton move = new JButton("Переместить колонку");
        // Слушатель обработки события
        /*move.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Индекс первой колоки
                int first = table1.getSelectedColumn();
                // Индекс второй колонки
                int last = (first == columnModel.getColumnCount()) ? first + 1 : 0;
                // Перемещение столбцов
                columnModel.moveColumn(first, last);
            }
        });*/
        // Панель кнопок
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
                        new ShModeFrame();
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
                    new ShModeFrame();
                }else{
                    System.out.println("Помилка");
                    JOptionPane.showMessageDialog(ShModeFrame.this, "Ви не вибрали рядок, який потрібно видалити",
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
                    new ShModeEdit(array[sRow][0], array[sRow][1], array[sRow][2]);
                    dispose();
                }else{
                    System.out.println("Помилка");
                    JOptionPane.showMessageDialog(ShModeFrame.this, "Ви не вибрали рядок, який потрібно відредагувати",
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
                    ArrayList<ShootingMode> arr = new ArrayList<>();
                    for (int i = 0; i < array.length; i++) {
                        ShootingMode temp = new ShootingMode((Integer)array[i][0], (String) array[i][1],((Integer) array[i][2])==1?true:false);
                        arr.add((temp) );
                    }
                    System.out.println(arr.size());
                    FileOutputStream fos = new FileOutputStream(path);
                    ObjectOutputStream out = new ObjectOutputStream(fos);

                    for (int i = 0; i < arr.size(); i++) {
                        System.out.println(arr.get(i).getId() + " " + arr.get(i).getName() + " " + arr.get(i).isFixation());
                    }
                    for (int i = 0; i < arr.size(); i++) {
                        out.writeObject(arr.get(i));
                    }

                    out.close();
                    fos.close();
                    JOptionPane.showMessageDialog(ShModeFrame.this, "Запис в файл пройшов успішно",
                            "Важливо", JOptionPane.INFORMATION_MESSAGE);
                }catch (IOException i){
                    System.out.println("Помилка серіалізації");
                    JOptionPane.showMessageDialog(ShModeFrame.this, "Помилка серіалізації",
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
                    ArrayList<ShootingMode> arr = new ArrayList<>();
                    ShootingMode shMode = (ShootingMode)in.readObject();
                    do{
                        arr.add(shMode);
                        try {
                            System.out.println("+"+shMode.getId());
                            shMode = (ShootingMode) in.readObject();
                        }catch (IOException ee){
                            Object[][] newArray = new Object[arr.size()][3];
                            for (int i = 0;i < arr.size(); i++) {
                                newArray[i][0] = arr.get(i).getId();
                                newArray[i][1] = arr.get(i).getName();
                                newArray[i][2] = arr.get(i).isFixation()?1:0;
                            }
                            array = newArray.clone();
                            JOptionPane.showMessageDialog(ShModeFrame.this, "Читання з в файлу пройшло успішно",
                                    "Важливо", JOptionPane.INFORMATION_MESSAGE);
                            dispose();
                            new ShModeFrame();
                            in.close();
                            fis.close();

                            return;
                        }
                    }while (shMode != null);


                }catch (IOException | ClassNotFoundException ee){
                    System.out.println("Помилка десеріалізації");
                    JOptionPane.showMessageDialog(ShModeFrame.this, "Помилка десеріалізації",
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
                    JOptionPane.showMessageDialog(ShModeFrame.this, "Файл " + file.getName() + " очищено",
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
        //pnlButtons.add(move);
        // Слушатель событий модели столбцов таблицы
        columnModel.addColumnModelListener(new TableColumnModelListener() {
            @Override
            public void columnAdded(TableColumnModelEvent arg0) {
                out.println("TableColumnModelListener.columnAdded()");
            }

            @Override
            public void columnMarginChanged(ChangeEvent arg0) {
                out.println("TableColumnModelListener.columnMarginChanged()");
            }

            @Override
            public void columnMoved(TableColumnModelEvent arg0) {
                out.println("TableColumnModelListener.columnMoved()");
            }

            @Override
            public void columnRemoved(TableColumnModelEvent arg0) {
            }

            @Override
            public void columnSelectionChanged(ListSelectionEvent arg0) {
                out.println("TableColumnModelListener.columnSelectionChanged()");
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
