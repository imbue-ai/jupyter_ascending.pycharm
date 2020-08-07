import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.LogicalPosition;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.logging.Logger;

public class JupyterExecuteAction extends AnAction {
    private boolean forceSync = false;

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Logger logger = JupyterUtils.JupyterLogger;

        if (forceSync) {
            logger.info("Forcing sync...");
            // Force a sync if you're going to execute.
            new JupyterSyncAction().actionPerformed(event);
            logger.info("...Sync complete");
        }


        // Using the event, create and show a dialog
        Project currentProject = event.getProject();
        if (currentProject == null) {
            return;
        }

        String base_path = currentProject.getBasePath();
        if (base_path == null) {
            return;
        }

        VirtualFile virt_file = event.getData(PlatformDataKeys.VIRTUAL_FILE);
        if (virt_file == null) {
            return;
        }

        String file_name = virt_file.getPath();

        CaretModel caretModel = event.getRequiredData(CommonDataKeys.EDITOR).getCaretModel();
        final Caret primaryCaret = caretModel.getPrimaryCaret();
        LogicalPosition logPos = primaryCaret.getLogicalPosition();

        Integer line_number = logPos.line;

        logger.info(String.format("Executing for: %s:%s", file_name, line_number));
        try {
            JupyterUtils.getCmdOutput(new String[]{"python", "-m", "jupyter_ascending.requests.execute", "--filename", file_name, "--linenumber", line_number.toString()});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
