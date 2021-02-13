package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PhAdd extends JFrame {
    public PhAdd() {
        super("Додати новий знімок");
        String[]shMode = ShModeFrame.getShModes();
        JPanel pnlTextFields = new JPanel(new GridLayout(0, 2));
        JLabel l1 = new JLabel("Режим зйомки");
        JLabel l2 = new JLabel("Посилання");
        pnlTextFields.add(l1);
        JTextField tf2 = new JTextField();
        JComboBox<String> cb1 = new JComboBox<>();
        for(int i = 0; i < shMode.length; i++) {
            cb1.addItem(shMode[i]);
        }
        pnlTextFields.add(cb1);
        pnlTextFields.add(l2);
        pnlTextFields.add(tf2);
        JButton add = new JButton("Додати");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id;
                String link;
                String shMode;
                try {
                    if (tf2.getText().equals("") || cb1.getSelectedItem() == null) {
                        System.out.println("Помилка");
                        JOptionPane.showMessageDialog(PhAdd.this, "Заповніть всі поля",
                                "Помилка", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    else{
                        id = PhFrame.getNewId();
                        shMode = cb1.getSelectedItem().toString();
                        link = tf2.getText();
                    }
                }
                catch (NumberFormatException i){
                    System.out.println("Помилка");
                    JOptionPane.showMessageDialog(PhAdd.this, "Некоректно введені дані",
                            "Помилка", JOptionPane.ERROR_MESSAGE);
                    tf2.setText("");
                    return;
                }
                Photo p = new Photo(id, shMode, link);
                System.out.println(id+ " " + shMode + " " + link );
                PhFrame.add(p);
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
