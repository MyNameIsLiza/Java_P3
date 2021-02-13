package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VAdd extends JFrame {

    public VAdd() {
        super("Додати нове відео");
        String[]vMode = VModeFrame.getVModes();
        JPanel pnlTextFields = new JPanel(new GridLayout(0, 2));
        JLabel l1 = new JLabel("Режим відео");
        JLabel l2 = new JLabel("Посилання");
        pnlTextFields.add(l1);
        JTextField tf2 = new JTextField();
        JComboBox<String> cb1 = new JComboBox<>();
        for(int i = 0; i < vMode.length; i++) {
            cb1.addItem(vMode[i]);
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
                String vMode;
                try {
                    if(tf2.getText().equals("") || cb1.getSelectedItem() == null) {
                        System.out.println("Помилка");
                        JOptionPane.showMessageDialog(VAdd.this, "Заповніть всі поля",
                                "Помилка", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    else{
                        id = VFrame.getNewId();
                        vMode = cb1.getSelectedItem().toString();
                        link = tf2.getText();;
                    }
                }
                catch (NumberFormatException i){
                    JOptionPane.showMessageDialog(VAdd.this, "Некоректно введені дані",
                            "Помилка", JOptionPane.ERROR_MESSAGE);
                    System.out.println("Помилка");
                    tf2.setText("");
                    return;
                }
                Video v = new Video(id, vMode, link);
                System.out.println(id+ " " + vMode + " " + link );
                VFrame.add(v);
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
