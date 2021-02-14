package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VEdit extends JFrame {
    public VEdit(Object id, Object vMode, Object link) {
        super("Редагувати знімок");
        String[]vModes = VModeFrame.getVModes();
        JPanel pnlTextFields = new JPanel(new GridLayout(0, 2));
        JLabel l1 = new JLabel("Режим зйомки");
        JLabel l2 = new JLabel("Посилання");
        pnlTextFields.add(l1);
        JTextField tf2 = new JTextField();
        JComboBox<String> cb1 = new JComboBox<>();
        for(int i = 0; i < vModes.length; i++) {
            cb1.addItem(vModes[i]);
        }
        cb1.setSelectedItem(vMode);
        tf2.setText((String)link);
        pnlTextFields.add(cb1);
        pnlTextFields.add(l2);
        pnlTextFields.add(tf2);
        JButton add = new JButton("Змінити");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int eId;
                String eLink;
                String eVMode;
                try {
                    if (tf2.getText().equals("") || cb1.getSelectedItem() == null) {
                        System.out.println("Помилка");
                        JOptionPane.showMessageDialog(VEdit.this, "Заповніть всі поля",
                                "Помилка", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    else{
                        eId = (Integer)id;
                        eVMode = cb1.getSelectedItem().toString();
                        eLink = tf2.getText();
                    }
                }
                catch (NumberFormatException i){
                    System.out.println("Помилка");
                    JOptionPane.showMessageDialog(VEdit.this, "Некоректно введені дані",
                            "Помилка", JOptionPane.ERROR_MESSAGE);
                    tf2.setText("");
                    return;
                }
                Video v = new Video(eId, eVMode, eLink);
                System.out.println(eId+ " " + eVMode + " " + eLink );
                VFrame.edit(v);
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
