package com.game.wall.patch.works;

import com.game.wall.patch.helper.PatchHelper;
import com.game.wall.patch.models.FileTableModel;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;
import javax.swing.*;
import lombok.extern.slf4j.Slf4j;

/** SwingWorker "SearchFile" finds all files in current directory with subdirectories. */
@Slf4j
public class SearchFile extends SwingWorker<Map<File, String>, File> {

  private final FileTableModel jTable;

  private final File path;

  /**
   * Constructor for SwingWorker "Search File".
   *
   * @param path The path of current directory.
   * @param jTable Result will be returned in JTable.
   */
  public SearchFile(File path, FileTableModel jTable) {
    this.path = path;
    this.jTable = jTable;
  }

  @Override
  protected Map<File, String> doInBackground() {
    log.debug("SearchFile is running.");
    return new HashMap<>(searchFilesInDir(path));
  }

  @Override
  protected final void done() {
    log.debug("SearchFile was finished.");

    try {
      Map<File, String> allFiles = get();
      if (Objects.nonNull(allFiles)) {
        for (File currFile : allFiles.keySet()) {
          jTable.addRow(new Object[] {currFile.getPath(), allFiles.get(currFile)});
        }
      }
    } catch (InterruptedException | ExecutionException e) {
      log.error(e.getMessage(), e);
      Thread.currentThread().interrupt();
    }
  }

  /**
   * Recursive method for search files.
   *
   * @param file Current directory for search files.
   * @return Array all files in current directory with files from subdirectories.
   */
  private Map<File, String> searchFilesInDir(File file) {

    Map<File, String> allFiles = new HashMap<>();

    for (File currFile : file.listFiles()) {
      if (!currFile.isDirectory() && currFile.getName().toLowerCase().endsWith(".avi")) {
        if (PatchHelper.isPatch(currFile)) {
          allFiles.put(currFile, "Patched");
        } else {
          allFiles.put(currFile, "Original");
        }
      } else {
        allFiles.putAll(searchFilesInDir(currFile));
      }
    }
    return allFiles;
  }
}
