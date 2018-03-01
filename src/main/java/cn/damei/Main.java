package cn.damei;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import java.io.File;
import java.net.URL;
import java.security.ProtectionDomain;
public class Main {
    public static void main(String[] args) throws Exception {
        String contextPath = "/";
        int port = Integer.getInteger("port", 13093);
        Server server = createServer(contextPath, port);
        try {
            server.start();
            server.join();
            System.out.println("[INFO] Server running at http://localhost:" + port + contextPath);
            System.out.println("[HINT] Hit Enter to reload the application quickly");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(100);
        }
    }
    private static Server createServer(String contextPath, int port) {
        Server server = new Server(port);
        server.setStopAtShutdown(true);
        ProtectionDomain protectionDomain = Main.class.getProtectionDomain();
        URL location = protectionDomain.getCodeSource().getLocation();
        String warFile = location.toExternalForm();
        WebAppContext context = new WebAppContext(warFile, contextPath);
        context.setServer(server);
        String currentDir = new File(location.getPath()).getParent();
        File workDir = new File(currentDir, "work");
        context.setTempDirectory(workDir);
        server.setHandler(context);
        return server;
    }
}
