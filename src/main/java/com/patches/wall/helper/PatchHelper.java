package com.patches.wall.helper;

import com.patches.wall.works.PatchFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.concurrent.*;

import org.apache.log4j.Logger;


public class PatchHelper {

    private final static Logger log = Logger.getLogger(PatchHelper.class);

    private File file;

    public PatchHelper(File file) {
        this.file = file;
    }

    public boolean patchFile() {
        RandomAccessFile patchFile = null;
        try {
            patchFile = new RandomAccessFile(file, "rw");
            //чтение
            //проверка
            //Запись
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
        } finally {
            if (patchFile != null) {
                try {
                    patchFile.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }
        return false;
    }

    public boolean isPatch(File file) {
        RandomAccessFile patchFile = null;
        try {
            patchFile = new RandomAccessFile(file, "r");
            //чтение
            //проверка
            //return
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
        } finally {
            if (patchFile != null) {
                try {
                    patchFile.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }
        return false;
    }

    static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();

        for (byte b : hash) {
            formatter.format("\\x%02x", b);
        }

        String result = formatter.toString();
        log.debug("Signature of file = ",result);
        formatter.close();
        return result;
    }
}
