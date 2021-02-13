package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShModeAdd extends JFrame {
    //
    public ShModeAdd() {
        super("Додати новий режим зйомки");
        //setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Размещение таблиц в панели с блочным расположением
        JPanel pnlTextFields = new JPanel(new GridLayout(0, 2));
        //contents.add(new JScrollPane(table2));
        JLabel l1 = new JLabel("Назва");
        JLabel l2 = new JLabel("Фіксація");
        l1.setBounds(50, 50, 50, 20);
        l2.setBounds(100, 50, 50, 20);
        pnlTextFields.add(l1);

        JTextField tf1 = new JTextField();
        JCheckBox cb2 = new JCheckBox();
        tf1.setBounds(100, 50, 150, 20);
        pnlTextFields.add(tf1);
        pnlTextFields.add(l2);
        pnlTextFields.add(cb2);

        // Кнопка добавления колонки в модель TableColumnModel
        JButton add = new JButton("Додати");
        // Слушатель обработки события
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id;
                String name;
                boolean fixation;
                try {
                    if (tf1.getText().equals("")) {
                        System.out.println("Помилка");
                        JOptionPane.showMessageDialog(ShModeAdd.this, "Заповніть всі поля",
                                "Помилка", JOptionPane.ERROR_MESSAGE);
                        return;
                    } else {
                        id = ShModeFrame.getNewId();
                        name = tf1.getText();
                        fixation = cb2.isSelected();
                    }
                }
                catch (NumberFormatException i){
                    System.out.println("Помилка");
                    JOptionPane.showMessageDialog(ShModeAdd.this, "Некоректно введені дані",
                            "Помилка", JOptionPane.ERROR_MESSAGE);
                    tf1.setText("");
                    return;
                }
                ShootingMode shm = new ShootingMode(id, name, fixation);
                System.out.println(id + " " + name + " " + (fixation?1:0));
                ShModeFrame.add(shm);
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


        // Вывод окна на экран
        getContentPane().add(pnlTextFields, BorderLayout.NORTH);
        getContentPane().add(pnlButtons, BorderLayout.SOUTH);
        setSize(480, 300);
        setVisible(true);
    }
}
