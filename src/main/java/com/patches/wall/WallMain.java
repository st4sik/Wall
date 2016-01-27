package com.patches.wall;

import com.patches.wall.frames.WallFrame;

import javax.swing.*;


public class WallMain {

    public static void main (String argv[]){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new WallFrame().setVisible(true);
            }
        });
    }
}
