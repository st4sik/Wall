package com.game.wall.patch;

import com.game.wall.patch.frames.WallFrame;
import javax.swing.*;

public class WallMain {

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new WallFrame().setVisible(true));
  }
}