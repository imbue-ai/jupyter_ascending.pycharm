import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.ScriptRunnerUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class JupyterSyncAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
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

        ArrayList<String> cmds = new ArrayList<>();

        cmds.add("python");
        cmds.add("-m");
        cmds.add("jupyter_ascending.requests.sync");
        cmds.add("--filename");
        cmds.add(file_name);

        GeneralCommandLine command_line = new GeneralCommandLine(cmds);
        command_line.setWorkDirectory(base_path);

        try {
            ScriptRunnerUtil.getProcessOutput(command_line);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(AnActionEvent e) {
        // Set the availability based on whether a project is open
        Project project = e.getProject();
        e.getPresentation().setEnabledAndVisible(project != null);
    }

}
