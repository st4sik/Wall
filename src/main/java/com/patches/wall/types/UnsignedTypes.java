package com.patches.wall.types;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Formatter;

/**
 * Created by stri0214 on 23.01.2016.
 */
@Deprecated
public class UnsignedTypes {
    private RandomAccessFile nameOfFile;

    UnsignedTypes(RandomAccessFile f) {
        nameOfFile = f;
    }
    static public long convert(long signedvariable){
        long ans=0;
        for(int i=0;i<64;i++){
            ans+=(int)Math.pow(2,i)*(Math.abs(signedvariable % 2));
            signedvariable>>=1;
        }
        return ans;
    }

    //функция чтения WORD(unsigned short)
    public long readWord() throws IOException {
        byte[] mas = new byte[2];
        nameOfFile.readFully(mas,0,2);
        return read(mas,2);
    }
    //функция чтения DWORD(unsigned int)
    public long readDWord() throws IOException {
        byte[] mas = new byte[4];
        nameOfFile.readFully(mas,0,4);
        return read(mas, 4);
    }
    //функция используеая для работы двух верхних функций
    private static long read(byte mas[],int numberOfByte) {
        long ans = 0;
        for (int i = 0; i <numberOfByte; i++) {
            for (int j = 0; j < 8; j++) {
                //System.out.print(Math.abs(mas[i] % 2));
                ans+=(int)Math.pow(2,j+i*8)*(Math.abs(mas[i] % 2));
                mas[i] >>= 1;
            }
            //System.out.print(" ");
        }
        return ans;
    }
    //функция чтения 1байтового Char из бинарного файла
    public char readChar() throws IOException {
        char ans =0;
        byte b=nameOfFile.readByte();
        ans=(char) b;
        return ans;
    }
    private static String patch="\\x02\\x00\\x00\\x80\\x01\\x00\\x00\\x01\\x00\\x18\\x00\\x64\\x69\\x76\\x78";
    private String unpatch="\\x00\\x00\\x00\\x00\\x00\\x00\\x00\\x01\\x00\\x18\\x00\\x00\\x00\\x00\\x00";

    private static String a="000000000000000100180000000000";
    private static String b="020000800100000100180064697678";
    public static void main(String argv[]){

        RandomAccessFile patchFile = null;
        byte[] sig=new byte[15];
        try {
            patchFile = new RandomAccessFile("E:\\36022.avi", "rw");
            patchFile.seek(0xb1);
            patchFile.readFully(sig);
            //patchFile.close();
            if(a.equals(byteToHex(sig))){
                patchFile.seek(0xb1);
                patchFile.write(asBytes(b));

            }
            System.out.println(" dsds" +byteToHex(sig));
            //чтение
            //проверка
            //Запись
        } catch (FileNotFoundException e) {

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (patchFile != null) {
                try {
                    patchFile.close();
                } catch (IOException e) {

                }
            }
        }

    }

    static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();

        for (byte b : hash) {
            formatter.format("%02x", b);
        }

        String result = formatter.toString();
        formatter.close();
        return result;
    }

    static byte[] asBytes(String str)
    {
        if ((str.length() % 2) == 1) str = "0" + str; // pad leading 0 if needed
        byte[] buf = new byte[str.length() / 2];
        int i = 0;

        for (char c : str.toCharArray())
        {
            byte b = Byte.parseByte(String.valueOf(c), 16);
            buf[i/2] |= (b << (((i % 2) == 0) ? 4 : 0));
            i++;
        }

        return buf;
    }

}
