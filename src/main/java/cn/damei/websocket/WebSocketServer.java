package cn.damei.websocket;
import com.corundumstudio.socketio.SocketIOServer;
public class WebSocketServer {
    private WebSocketServer() {
    }
    private static SocketIOServer server = null;
    public static SocketIOServer getServer() {
        return server;
    }
    public static void setServer(SocketIOServer server) {
        WebSocketServer.server = server;
    }
}
