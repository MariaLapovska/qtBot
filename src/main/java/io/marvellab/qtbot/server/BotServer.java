package io.marvellab.qtbot.server;

import lombok.extern.log4j.Log4j2;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;

@Log4j2
public class BotServer {

    private Server server;

    public BotServer() {
        server = new Server();
    }

    public void start() throws Exception {
        int port = getPort();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(port);
        server.setConnectors(new Connector[]{connector});

        ServletHandler servletHandler = new ServletHandler();
        servletHandler.addServletWithMapping(BotServlet.class, "/");
        server.setHandler(servletHandler);

        log.debug("Starting server on port {}...", port);

        server.start();

        log.debug("Started server on port {}", port);
    }

    private int getPort() {
        return Integer.parseInt(System.getenv("PORT"));
    }
}
