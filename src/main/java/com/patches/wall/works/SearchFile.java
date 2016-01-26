package com.patches.wall.works;

import com.patches.wall.helper.PatchHelper;
import com.patches.wall.models.FileTableModel;

import javax.swing.*;
import java.io.File;
import java.util.HashMap;
import java.util.concurrent.*;
import org.apache.log4j.Logger;

/**
 * SwingWorker "SearcFile" find all files in current directory with sub-directories. Is is running in other thread.
 */

public class SearchFile extends SwingWorker<HashMap<File,String>,File> {

    private static final Logger log=Logger.getLogger(SearchFile.class);

    private final FileTableModel jTable;

    private File path;

    /**
     * Contructor for SwingWorker "Search File".
     * @param path The path of choosed directory
     * @param jTable Result will be returned in JTable.
     */
    public SearchFile(File path, FileTableModel jTable) {
        this.path=path;
        this.jTable=jTable;
    }

    @Override
    protected HashMap<File,String> doInBackground() throws Exception {

        log.debug("SearchFile is running");

        HashMap<File,String> allFiles=new HashMap<>(searchFilesInDir(path));

        return allFiles;
    }

    @Override
    protected final void done(){

        log.debug("SearchFile was finished");

        HashMap<File,String> allFiles = new HashMap<>();

        try {
            allFiles=get();

            if(allFiles!=null){
                for (File currFile : allFiles.keySet()) {
                    jTable.addRow(new Object[]{currFile.getPath(), allFiles.get(currFile)});
                }
            }
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        } catch (ExecutionException e) {
            log.error(e.getMessage());
        }
    }


    /**
     * Recursive method for search files.
     * @param file Current directory for search files.
     * @return Array all files in current directory with files from sub-directories.
     */
    private HashMap<File,String> searchFilesInDir(File file) {

        HashMap<File,String> allFiles = new HashMap<>();

        for (File currFile : file.listFiles()) {
            if (!currFile.isDirectory() || currFile.getName().endsWith(".avi")) {
                if(PatchHelper.isPatch(currFile)){
                   allFiles.put(currFile,"Patched");
                } else {
                    allFiles.put(currFile,"Original");
                }
            }
            else {
                allFiles.putAll(searchFilesInDir(currFile));
            }
        }

        return allFiles;
    }

    /**
     * Check current file. If it is directory then return "true" else "false"
     * @param file Current file for check directory.
     * @return "true" or "false"
     */
    static private boolean isDirectory(File file) {
        return file.isDirectory() ? true : false;
    }

}
