package apofig.integration;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * The simplest possible Jetty server.
 */
public class SimplestServer
{
    public static void main( String[] args ) throws Exception
    {
        Server server = new Server(8080);

        // Creating the first web application context
        WebAppContext webapp = new WebAppContext();
        webapp.setResourceBase("src/main/webapp");
        webapp.setContextPath("/");
        webapp.setClassLoader(ClassLoader.getSystemClassLoader());
        webapp.setServer(server);
        webapp.setParentLoaderPriority(true);

        // Adding the handlers to the server
        server.setHandler(webapp);

        // Starting the Server
        server.start();
        System.out.println("Started!");
        System.out.println("http://localhost:8080/");
        server.join();
    }
}