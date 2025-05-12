package example.jdbc.config;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.h2.tools.*;

import java.sql.SQLException;

@Component
public class H2ServerConfig {

    private Server tcpServer;

    @PostConstruct
    public void startTcpServer() throws SQLException {
        this.tcpServer = Server.createTcpServer(
                "-tcp", "-tcpAllowOthers", "-tcpPort", "9092"
        ).start();
        System.out.println("H2 TCP server started on port 9092");
    }
}