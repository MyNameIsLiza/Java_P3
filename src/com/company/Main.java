package com.company;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Main {

    public static void main(String[] args) {
        JFrame f =new JFrame("Фотоапарат");
        f.setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel pnlButtons = new JPanel(new GridLayout(0, 1));
        JPanel pnlShm = new JPanel(new GridLayout(0, 1));
        JPanel pnlVm = new JPanel(new GridLayout(0, 1));
        JPanel pnlP = new JPanel(new GridLayout(0, 1));
        JPanel pnlV = new JPanel(new GridLayout(0, 1));
        JPanel pnlG = new JPanel(new GridLayout(0, 1));
        JButton shmButton = new JButton("Переглянути режими зйомки");
        JButton vmButton = new JButton("Переглянути режими відео");
        JButton pButton = new JButton("Переглянути знімки");
        JButton vButton = new JButton("Переглянути відео");
        JButton gButton = new JButton("Переглянути галерею");
        pnlShm.add(shmButton);
        pnlVm.add(vmButton);
        pnlP.add(pButton);
        pnlV.add(vButton);
        pnlG.add(gButton);
        pnlButtons.add(pnlShm);
        pnlButtons.add(pnlVm);
        pnlButtons.add(pnlP);
        pnlButtons.add(pnlV);
        pnlButtons.add(pnlG);

        Border empty = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        pnlButtons.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        pnlShm.setBorder(empty);
        pnlVm.setBorder(empty);
        pnlP.setBorder(empty);
        pnlV.setBorder(empty);
        pnlG.setBorder(empty);

        shmButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new ShModeFrame();
                /*JFrame shFrame =new JFrame("Режим зйомки");
                Gallery g = new Gallery(1);
                JList list = new JList();
                JTable table = new JTable();
                shFrame.add(table);
                table.setSize(1, 1);
                table.setValueAt(g, 0, 0);

                shFrame.setSize(400,400);
                shFrame.setLayout(new GridLayout());
                shFrame.setVisible(true);*/
            }
        });
        vmButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new VModeFrame();

            }
        });
        pButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new PhFrame();

            }
        });
        vButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new VFrame();

            }
        });
        gButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new GFrame();

            }
        });
        f.getContentPane().add(pnlButtons, BorderLayout.CENTER);
        f.setMinimumSize(new Dimension(400,400));
        f.setSize(400,400);
        f.setVisible(true);
    }

}

