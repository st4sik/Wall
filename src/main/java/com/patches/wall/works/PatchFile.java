package com.patches.wall.works;

import com.patches.wall.dialogs.ResultDialog;
import com.patches.wall.helper.PatchHelper;

import javax.swing.*;
import java.io.File;
import java.util.Formatter;
import java.util.concurrent.ExecutionException;

/**
 * Created by stri0214 on 21.01.2016.
 */
public class PatchFile extends SwingWorker<Boolean, Void> {
    private final File file;


    public PatchFile (File file ) {
        this.file=file;
    }
    @Override
    protected Boolean doInBackground() throws Exception {

        //PatchHelper patchFile=new PatchHelper(file);
        boolean wasPatched=PatchHelper.patchFile(file);

        return true;
    }


    @Override
    protected final void done()
    {
        try {
            if(get()){
                ResultDialog resultDialog=new ResultDialog(file);
                resultDialog.setVisible(true);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private String byteToHex(final byte[] hash)
    {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("\\x%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

}
