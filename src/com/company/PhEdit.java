package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PhEdit extends JFrame {
    private int id;
    private String shMode;
    private String link;
    public PhEdit(Object id, Object shMode, Object link) {
        super("Редагувати знімок");
        String[]shModes = ShModeFrame.getShModes();
        JPanel pnlTextFields = new JPanel(new GridLayout(0, 2));
        JLabel l1 = new JLabel("Режим зйомки");
        JLabel l2 = new JLabel("Посилання");
        pnlTextFields.add(l1);
        JTextField tf2 = new JTextField();
        JComboBox<String> cb1 = new JComboBox<>();
        for(int i = 0; i < shModes.length; i++) {
            cb1.addItem(shModes[i]);
        }
        cb1.setSelectedItem(shMode);
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
                String eShMode;
                try {
                    if (tf2.getText().equals("") || cb1.getSelectedItem() == null) {
                        System.out.println("Помилка");
                        JOptionPane.showMessageDialog(PhEdit.this, "Заповніть всі поля",
                                "Помилка", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    else{
                        eId = (Integer)id;
                        eLink = cb1.getSelectedItem().toString();
                        eShMode = tf2.getText();
                    }
                }
                catch (NumberFormatException i){
                    System.out.println("Помилка");
                    JOptionPane.showMessageDialog(PhEdit.this, "Некоректно введені дані",
                            "Помилка", JOptionPane.ERROR_MESSAGE);
                    tf2.setText("");
                    return;
                }
                Photo p = new Photo(eId, eShMode, eLink);
                System.out.println(eId+ " " + eShMode + " " + eLink );
                PhFrame.edit(p);
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
