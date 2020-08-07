import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.newvfs.BulkFileListener;
import com.intellij.openapi.vfs.newvfs.events.VFileEvent;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class JupyterSyncFileListener implements BulkFileListener {
    @Override
    public void after(@NotNull List<? extends VFileEvent> events) {
        boolean shouldWriteFile = true;

        Logger logger = JupyterUtils.JupyterLogger;

        for (VFileEvent event : events) {
            VirtualFile file = event.getFile();
            if (file == null) {
                continue;
            }

            String filePath = file.getPath();
            if (filePath == null) {
                continue;
            }

            if (!filePath.endsWith(".synced.py")) {
                continue;
            }

            logger.info("File Save event: " + filePath);
            try {
                JupyterUtils.getCmdOutput(new String[]{"python", "-m", "jupyter_ascending.requests.sync", "--filename", filePath});
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (shouldWriteFile) {
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter("/home/tj/files_changed.txt", true));

                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
                    Date date = new Date(System.currentTimeMillis());

                    writer.newLine();
                    writer.write(filePath + ": " + formatter.format(date));
                    writer.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



}
