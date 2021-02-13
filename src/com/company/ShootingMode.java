package com.company;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

public class ShootingMode {
    private int id;
    private String name;
    private boolean fixation;

    public ShootingMode(int id, String name, boolean fixation) {
        this.id = id;
        this.name = name;
        this.fixation = fixation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFixation() {
        return fixation;
    }

    public void setFixation(boolean fixation) {
        this.fixation = fixation;
    }
}

