import com.intellij.execution.configurations.ConfigurationTypeBase;
import com.intellij.openapi.util.NotNullLazyValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class JupyterSyncConfiguration extends ConfigurationTypeBase {

    protected JupyterSyncConfiguration(@NotNull String id, @NotNull String displayName, @Nullable String description, @Nullable NotNullLazyValue<Icon> icon) {
        super(id, displayName, description, icon);
    }
}
