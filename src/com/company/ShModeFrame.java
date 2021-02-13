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
import java.util.Enumeration;

import static java.lang.System.out;

public class ShModeFrame extends JFrame {
    private static JTable table1;
    private DefaultTableModel model;
    private static Object[] columnsHeader = new String[]{"ID", "Назва", "Фіксація"};
    // Модель столбцов таблицы
    private TableColumnModel columnModel;
    // Данные для таблиц
    private static Object[][] array = new Object[][]{{1, "Портрет", 1},
            {3, "Панорама", 0},
            {4, "Звичайне фото", 0}};
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
    // Заголовки столбцов

    //
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
        JPanel pnlButtons = new JPanel();
        pnlButtons.add(add);
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

        // Вывод окна на экран
        getContentPane().add(contents);
        getContentPane().add(pnlButtons, BorderLayout.SOUTH);
        setMinimumSize(new Dimension(600, 300));
        setSize(600, 300);
        setVisible(true);
    }
}
