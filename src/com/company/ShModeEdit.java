package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShModeEdit extends JFrame {

    public ShModeEdit(Object id, Object name, Object fixation) {
        super("Редагувати режим зйомки");
        String[]shModes = ShModeFrame.getShModes();
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
        cb2.setSelected(((Integer) fixation) == 1?true:false);
        tf1.setText((String)name);
        JButton add = new JButton("Змінити");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int eId;
                String eName;
                boolean eFixation;
                try {
                    if (tf1.getText().equals("")) {
                        System.out.println("Помилка");
                        JOptionPane.showMessageDialog(ShModeEdit.this, "Заповніть всі поля",
                                "Помилка", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    else{
                        eId = (Integer)id;
                        eFixation = cb2.isSelected();
                        eName = tf1.getText();
                    }
                }
                catch (NumberFormatException i){
                    System.out.println("Помилка");
                    JOptionPane.showMessageDialog(ShModeEdit.this, "Некоректно введені дані",
                            "Помилка", JOptionPane.ERROR_MESSAGE);
                    tf1.setText("");
                    return;
                }
                ShootingMode shMode = new ShootingMode(eId, eName, eFixation);
                System.out.println(eId+ " " + eName + " " + eFixation );
                ShModeFrame.edit(shMode);
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
