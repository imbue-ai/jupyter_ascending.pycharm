import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Logger;


public class JupyterUtils {
    public static Logger JupyterLogger = Logger.getLogger("JupyterLogger");;

    public static String getCmdOutput(String[] cmdString) throws IOException {
        JupyterLogger.info("Cmd to execute: " + String.join(" ", cmdString));

        ProcessBuilder builder = new ProcessBuilder(cmdString);
        builder.redirectErrorStream(true);
        Process process = builder.start();
        InputStream is = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        ArrayList<String> lines = new ArrayList<String>();
        String line = null;
        while ((line = reader.readLine()) != null) {
            JupyterLogger.info("LINE_OUTPUT: " + line);
            lines.add(line);
        }

        return String.join("\n", lines);
    }
}
