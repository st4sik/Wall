package com.game.wall.patch.helper;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Formatter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PatchHelper {

  private PatchHelper() {}

  private static final String PATCH_SIGNATURE =
      "\\x02\\x00\\x00\\x80\\x01\\x00\\x00\\x01\\x00\\x18\\x00\\x64\\x69\\x76\\x78";
  private static final String ORIGINAL_SIGNATURE =
      "\\x00\\x00\\x00\\x00\\x00\\x00\\x00\\x01\\x00\\x18\\x00\\x00\\x00\\x00\\x00";

  /**
   * Method of patching for a current file.
   *
   * @param file a current file
   * @return if "true", it was patched or "false".
   */
  public static boolean patchFile(File file) {
    byte[] sign = new byte[15];
    try (RandomAccessFile patchFile = new RandomAccessFile(file, "rw")) {
      patchFile.seek(0xb1);
      patchFile.readFully(sign);

      log.debug("File = {} has been read.", file.getAbsolutePath());

      if (ORIGINAL_SIGNATURE.equals(byteToHex(sign))) {
        patchFile.seek(0xb1);
        patchFile.write(hexToByte(PATCH_SIGNATURE));
        log.debug("File = {} has been patched.", file.getAbsolutePath());
        return true;
      }

    } catch (IOException e) {
      log.error(e.getMessage(), e);
    }
    return false;
  }

  /**
   * The method checks, if current file is patched or original.
   *
   * @param file current file.
   * @return "true" if the file was patched or "false".
   */
  public static boolean isPatch(File file) {
    byte[] sign = new byte[15];

    try (RandomAccessFile patchFile = new RandomAccessFile(file, "r")) {
      patchFile.seek(0xb1);
      patchFile.readFully(sign);

      log.debug("File = {} was read.", file.getAbsolutePath());

      if (ORIGINAL_SIGNATURE.equals(byteToHex(sign))) {
        log.debug("File = {} is original.", file.getAbsolutePath());
        return false;
      }
    } catch (IOException e) {
      log.error(e.getMessage(), e);
    }
    return true;
  }

  /**
   * Converts byte[] to hex's string.
   *
   * @param sign a byte[] to convert to hex's string.
   * @return hex's string.
   */
  private static String byteToHex(final byte[] sign) {
    Formatter formatter = new Formatter();
    for (byte b : sign) {
      formatter.format("\\x%02x", b);
    }
    String result = formatter.toString();

    log.debug("Signature of file = {}", result);
    formatter.close();
    return result;
  }

  public static byte[] hexToByte(String sign) {
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
