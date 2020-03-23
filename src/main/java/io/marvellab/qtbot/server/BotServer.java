package io.marvellab.qtbot.server;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;

public class BotServer {

    private Server server;

    public void start() throws Exception {
        server = new Server();

        ServerConnector connector = new ServerConnector(server);
        connector.setPort(getPort());
        server.setConnectors(new Connector[]{connector});

        ServletHandler servletHandler = new ServletHandler();
        servletHandler.addServletWithMapping(BotServlet.class, "/");
        server.setHandler(servletHandler);

        server.start();
    }

    private int getPort() {
        return Integer.parseInt(System.getenv("PORT"));
    }
}
