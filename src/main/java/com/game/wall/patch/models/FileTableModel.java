package com.game.wall.patch.models;

import javax.swing.table.DefaultTableModel;

public class FileTableModel extends DefaultTableModel {

  private static final String[] COLUMNS = {"File", "Status"};

  public FileTableModel() {
    super(0, 2);
    super.setColumnIdentifiers(COLUMNS);
  }
}
