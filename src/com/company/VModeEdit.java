package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VModeEdit extends JFrame {
    public VModeEdit(Object id, Object name, Object speed) {
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
        tf1.setText((String)name);
        tf2.setText(((Double)speed).toString());
        JButton add = new JButton("Змінити");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int eId;
                String eName;
                double eSpeed;
                try {
                    if (tf1.getText().equals("") || tf2.getText().equals("")) {
                        System.out.println("Помилка");
                        JOptionPane.showMessageDialog(VModeEdit.this, "Заповніть всі поля",
                                "Помилка", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    else{
                        eId = (Integer)id;
                        eSpeed = Double.parseDouble(tf2.getText());
                        eName = tf1.getText();
                    }
                }
                catch (NumberFormatException i){
                    System.out.println("Помилка");
                    JOptionPane.showMessageDialog(VModeEdit.this, "Некоректно введені дані",
                            "Помилка", JOptionPane.ERROR_MESSAGE);
                    tf1.setText("");
                    tf2.setText("");
                    return;
                }
                VideoMode vMode = new VideoMode(eId, eName, eSpeed);
                System.out.println(eId+ " " + eName + " " + eSpeed );
                VModeFrame.edit(vMode);
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
