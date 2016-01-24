package com.patches.wall.models;

import javax.swing.table.DefaultTableModel;

/**
 * Created by stri0214 on 20.01.2016.
 */
public class FileTableModel extends DefaultTableModel {

    private static String columns[]={"File","Status"};

    public FileTableModel(){
        super(0,2);
        super.setColumnIdentifiers(columns);
    }


}

