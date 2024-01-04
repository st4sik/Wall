package com.game.wall.patch.works;

import com.game.wall.patch.dialogs.ResultDialog;
import com.game.wall.patch.helper.PatchHelper;
import java.io.File;
import java.util.concurrent.ExecutionException;
import javax.swing.*;
import lombok.extern.slf4j.Slf4j;

/** SwingWorker "PatchFile" of patching a current file. It's running in other thread. */
@Slf4j
public class PatchFile extends SwingWorker<Boolean, Void> {

  private final File file;

  /**
   * Constructor for SwingWorker "PatchFile"
   *
   * @param file a current file for patch.
   */
  public PatchFile(File file) {
    this.file = file;
  }

  @Override
  protected Boolean doInBackground() {
    log.debug("PatchFile is running.");
    return PatchHelper.patchFile(file);
  }

  @Override
  protected final void done() {
    log.debug("PatchFile was finished.");
    try {
      if (Boolean.TRUE.equals(get())) {
        ResultDialog resultDialog = new ResultDialog(file);
        resultDialog.setVisible(true);
      }
    } catch (InterruptedException | ExecutionException e) {
      log.error(e.getMessage(), e);
      Thread.currentThread().interrupt();
    }
  }
}
