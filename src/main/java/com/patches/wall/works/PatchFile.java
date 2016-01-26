package com.patches.wall.works;

import com.patches.wall.dialogs.ResultDialog;
import com.patches.wall.helper.PatchHelper;

import javax.swing.*;
import java.io.File;
import java.util.concurrent.ExecutionException;
import org.apache.log4j.Logger;

/**
 * SwingWorker "PatchFile" of patching a current file. It's running in other thread.
 */

public class PatchFile extends SwingWorker<Boolean, Void> {

    private static Logger log=Logger.getLogger(PatchFile.class);

    private final File file;

    /**
     * Constructor for SwingWorker "PatchFile"
     * @param file a current file for patch.
     */
    public PatchFile (File file ) {
        this.file=file;
    }

    @Override
    protected Boolean doInBackground() throws Exception {

        log.debug("PatchFile is running");

        boolean wasPatched=PatchHelper.patchFile(file);

        return wasPatched;
    }

    @Override
    protected final void done() {

        log.debug("PatchFile was finished");

        try {
            if(get()){
                ResultDialog resultDialog=new ResultDialog(file);
                resultDialog.setVisible(true);
            }else{
                //ToDo
            }
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        } catch (ExecutionException e) {
            log.error(e.getMessage());
        }
    }
}
