package com.patches.wall.models;

import javax.swing.table.DefaultTableModel;


public class FileTableModel extends DefaultTableModel {

    private static String columns[]={"File","Status"};

    public FileTableModel(){
        super(0,2);
        super.setColumnIdentifiers(columns);
    }


}

