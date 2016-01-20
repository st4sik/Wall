package com.patches.wall.helper;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by stri0214 on 20.01.2016.
 */
public class FileFindHelper extends Thread  {
    private List<File> allFiles;

    private static FileFindHelper INSTANCE;

    private FileFindHelper() {
    }


    public static synchronized FileFindHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FileFindHelper();
        }
        return INSTANCE;
    }

    public List<File> searchAllFiles(String path) {
        allFiles = new CopyOnWriteArrayList<File>();//ArrayList<File>();

        File firstFile = new File(path);
        Thread myThready = new Thread(new Runnable() {
            @Override
            public void run() {
                searchFilesInDir(firstFile);
            }
        });
        myThready.setDaemon(true);
        myThready.start();
        //myThready.interrupt();
        //searchFilesInDir(firstFile);
        /*for (File currFile : firstFile.listFiles()) {
            if (!isDirectory(currFile)) {
                allFiles.add(currFile);
            } else {
                searchFilesInDir(currFile);
            }
        }//*/
        return allFiles;
    }


    private void searchFilesInDir(File file) {
        for (File currFile : file.listFiles()) {
            if (!currFile.isDirectory()) {
                allFiles.add(currFile);
            } else {
                searchFilesInDir(currFile);
            }
        }
    }

    static private boolean isDirectory(File file) {
        return file.isDirectory() ? true : false;
    }

}
