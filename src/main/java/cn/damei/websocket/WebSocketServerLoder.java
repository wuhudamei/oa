package cn.damei.websocket;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.Transport;
import cn.damei.common.PropertyHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
@Component
@Lazy(false)
public class WebSocketServerLoder {
    private Logger logger = LoggerFactory.getLogger(WebSocketServerLoder.class);
    @Value("${socket.io.port}")
    private int port;
    @Value("${profile}")
    private String profile;
    @PostConstruct
    public void load() {
        logger.info("WebSocket Init Start");
        if (!"development".equals(profile)) {
//            this.initServer();
        }
        logger.info("WebSocket Init End...");
    }
    private void initServer() {
        Configuration config = new Configuration();
        config.setAllowCustomRequests(true);
        config.setPort(port);
        config.setTransports(Transport.WEBSOCKET, Transport.XHRPOLLING, Transport.FLASHSOCKET);
        SocketConfig socketConfig = new SocketConfig();
        socketConfig.setTcpNoDelay(true);
        socketConfig.setTcpKeepAlive(true);
        config.setSocketConfig(socketConfig);
        config.setCloseTimeout(60);
        SocketIOServer server = new SocketIOServer(config);
        server.addListeners(new DefaultListeners());
        server.start();
        WebSocketServer.setServer(server);
    }
}
