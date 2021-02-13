package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VModeAdd extends JFrame {
    public VModeAdd() {
        super("Додати новий режим відео");
        JPanel pnlTextFields = new JPanel(new GridLayout(0, 2));
        JLabel l1 = new JLabel("Назва");
        JLabel l2 = new JLabel("Модифікатор швидкості");
        l1.setBounds(50, 50, 50, 20);
        l2.setBounds(100, 50, 50, 20);
        pnlTextFields.add(l1);
        JTextField tf1 = new JTextField();
        JTextField tf2 = new JTextField();
        tf1.setBounds(100, 50, 150, 20);
        pnlTextFields.add(tf1);
        pnlTextFields.add(l2);
        pnlTextFields.add(tf2);
        JButton add = new JButton("Додати");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id;
                String name;
                double speed;
                try {
                    if(tf1.getText().equals("") || tf2.getText().equals("")) {
                        System.out.println("Помилка");
                        JOptionPane.showMessageDialog(VModeAdd.this, "Заповніть всі поля",
                                "Помилка", JOptionPane.ERROR_MESSAGE);
                        return;
                    }else{
                        id = VModeFrame.getNewId();
                        name = tf1.getText();
                        speed = Double.parseDouble(tf2.getText());
                    }
                }
                catch (NumberFormatException i){
                    System.out.println("Помилка");
                    JOptionPane.showMessageDialog(VModeAdd.this, "Некоректно введені дані",
                            "Помилка", JOptionPane.ERROR_MESSAGE);
                    tf1.setText("");
                    tf2.setText("");
                    return;
                }
                VideoMode vm = new VideoMode(id, name, speed);
                System.out.println(id + " " + name + " " + speed);
                VModeFrame.add(vm);
                dispose();
            }
        });
        JPanel pnlButtons = new JPanel();
        pnlButtons.add(add);
        getContentPane().add(pnlTextFields, BorderLayout.NORTH);
        getContentPane().add(pnlButtons, BorderLayout.SOUTH);
        setSize(480, 300);
        setVisible(true);
    }
}
