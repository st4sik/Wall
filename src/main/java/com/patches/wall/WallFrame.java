package com.patches.wall;

import javax.swing.*;

/**
 * Created by STASСтас on 1/19/2016.
 */
public class WallFrame extends JFrame {
    private JPanel mainPanel;
    private JButton findButton;
    private JButton patchButton;
    private JPanel buttonPanel;
    private JPanel workPanel;
    private JTextField textField1;
    private JTable table1;

    public WallFrame (){
        super("Тестовое окно");
        pack();
        setContentPane(mainPanel);
        setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
