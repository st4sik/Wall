package com.patches.wall;

import com.patches.wall.frames.WallFrame;

import javax.swing.*;

/**
 * Created by STASСтас on 1/19/2016.
 */
public class WallMain {
    public static void main (String argv[]){
        //new WallFrame();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                new WallFrame().setVisible(true);
            }
        });

    }
}
