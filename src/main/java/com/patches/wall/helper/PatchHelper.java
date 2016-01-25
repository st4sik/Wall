package com.patches.wall.helper;

import com.patches.wall.works.PatchFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Formatter;

import org.apache.log4j.Logger;

public class PatchHelper {

    private final static Logger log = Logger.getLogger(PatchHelper.class);

    private static final String patch = "\\x02\\x00\\x00\\x80\\x01\\x00\\x00\\x01\\x00\\x18\\x00\\x64\\x69\\x76\\x78";
    private static final String original = "\\x00\\x00\\x00\\x00\\x00\\x00\\x00\\x01\\x00\\x18\\x00\\x00\\x00\\x00\\x00";


    /**
     * Method of patching for a current file.
     *
     * @param file a current file
     * @return if "true", it was patched or "false", if it was't patched.
     */
    static public boolean patchFile(File file) {

        byte[] sign = new byte[15];
        RandomAccessFile patchFile = null;

        try {

            patchFile = new RandomAccessFile(file, "rw");
            patchFile.seek(0xb1);
            patchFile.readFully(sign);

            log.debug("File = " + file.getAbsolutePath() + " has been read.");

            if (original.equals(byteToHex(sign))) {
                patchFile.seek(0xb1);
                patchFile.write(hexToByte(patch));

                log.debug("File = " + file.getAbsolutePath() + " has been patched.");

                return true;
            }

        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
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

    /**
     * The method of checking if a file was patched or original.
     * @param file a current file
     * @return "true" if the file was patched or "false".
     */
    static public boolean isPatch(File file) {

        byte[] sign = new byte[15];
        RandomAccessFile patchFile = null;

        try {

            patchFile = new RandomAccessFile(file, "r");
            patchFile.seek(0xb1);
            patchFile.readFully(sign);

            log.debug("File = " + file.getAbsolutePath() + " was read.");

            if (original.equals(byteToHex(sign))) {

                log.debug("File = " + file.getAbsolutePath() + " is original.");

                return false;
            }
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
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

        return true;
    }

    /**
     * Convert byte[] to hex's string.
     * @param sign a byte[] to convert to hex's string.
     * @return hex's string
     */
    static String byteToHex(final byte[] sign) {

        Formatter formatter = new Formatter();

        for (byte b : sign) {
            formatter.format("\\x%02x", b);
        }

        String result = formatter.toString();

        log.debug("Signature of file = " + result);

        formatter.close();

        return result;
    }

    static byte[] hexToByte(String sign) {

        sign = sign.replace("\\x", "");

        if ((sign.length() % 2) == 1) sign = "0" + sign;
        byte[] buf = new byte[sign.length() / 2];
        int i = 0;

        for (char c : sign.toCharArray()) {
            byte b = Byte.parseByte(String.valueOf(c), 16);
            buf[i / 2] |= (b << (((i % 2) == 0) ? 4 : 0));
            i++;
        }

        return buf;
    }
}
