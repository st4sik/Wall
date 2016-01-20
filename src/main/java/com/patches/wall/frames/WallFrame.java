package com.patches.wall.frames;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.patches.wall.helper.FileFindHelper;
import com.patches.wall.models.FileTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

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
    private ArrayList<File> allFiles;
    private JScrollPane scrollPane;

    public WallFrame() {
        super("Patch for game");
        initUI();

    }


    private void initUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        FileTableModel fileTableModel = new FileTableModel();
        table1.setModel(fileTableModel);

        scrollPane = new JScrollPane(table1);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        workPanel.add(scrollPane, gbc);

        findButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnVal = fileChooser.showOpenDialog(WallFrame.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    textField1.setText(file.getAbsolutePath());
                    allFiles = new ArrayList<File>();
                    allFiles.addAll(FileFindHelper.getInstance().searchAllFiles(file.getPath()));
                    if (allFiles != null) {
                        for (File currFile : allFiles) {
                            fileTableModel.addRow(new Object[]{currFile.getPath(), currFile.getName()});
                        }
                    }
                }
            }
        });


        this.setVisible(true);

    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), null));
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FormLayout("fill:d:grow", "center:d:noGrow,top:4dlu:noGrow,center:max(d;4px):noGrow"));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(buttonPanel, gbc);
        buttonPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-16777216)), null));
        findButton = new JButton();
        findButton.setText("Find");
        CellConstraints cc = new CellConstraints();
        buttonPanel.add(findButton, cc.xy(1, 1));
        patchButton = new JButton();
        patchButton.setText("Patch");
        buttonPanel.add(patchButton, cc.xy(1, 3));
        workPanel = new JPanel();
        workPanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(workPanel, gbc);
        workPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-16777216)), null));
        textField1 = new JTextField();
        textField1.setEditable(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        workPanel.add(textField1, gbc);
        final JLabel label1 = new JLabel();
        label1.setText("Path:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        workPanel.add(label1, gbc);
        table1 = new JTable();
        table1.setBackground(new Color(-1049089));
        table1.setRowSelectionAllowed(true);
        table1.setSelectionBackground(new Color(-1));
        table1.setUpdateSelectionOnSort(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        workPanel.add(table1, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }
}
