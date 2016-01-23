package com.patches.wall.works;

import com.patches.wall.models.FileTableModel;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.*;
import org.apache.log4j.Logger;

/**
 * SwingWorker "SearcFile" find all files in current directory with sub-directories. Is is running in other thread.
 */

public class SearchFile extends SwingWorker<ArrayList<File>,File> {

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
    protected ArrayList<File> doInBackground() throws Exception {

        log.info("SearchFile is running");
        ArrayList<File> allFiles=new ArrayList<File>(searchFilesInDir(path));
        return allFiles;
    }

    @Override
    protected final void done(){
        log.info("SearchFile was finished");
        ArrayList<File>allFiles=new ArrayList<File>();
        try {
            allFiles=get();
            if(allFiles!=null){
                for (File currFile : allFiles) {
                    jTable.addRow(new Object[]{currFile.getPath(), currFile.getName()});
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
    private ArrayList<File> searchFilesInDir(File file) {
        ArrayList<File> allFiles = new ArrayList<File>();
        for (File currFile : file.listFiles()) {
            if (!currFile.isDirectory()) {
                allFiles.add(currFile);
            }
            else { allFiles.addAll(searchFilesInDir(currFile));
            }

        } return allFiles;
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
