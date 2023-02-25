import java.io.Console;
import java.io.IOException;
import java.util.logging.ConsoleHandler;

public class Main {
    public static void main(String[] args) {


        System.out.println("Hello drain!");
        Server server;
        try{
            System.out.println("Creating server...");
            server = new Server();
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
            return;
        }

        System.out.println("Starting server...");
        server.start();
    }
}