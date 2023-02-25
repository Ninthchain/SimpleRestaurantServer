import com.sun.net.httpserver.*;

import java.io.Console;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executor;

public class Server extends HttpServer {
    private HttpServer serverCore;
    private InetSocketAddress clientSocket;
    private InetSocketAddress databaseSocket;

    private boolean isAllServicesAreReachable() throws IOException
    {
        if(databaseSocket == null)
            return this.isClientReachable();
        return this.isClientReachable() && this.isDatabaseReachable();
    }
    public Server(InetSocketAddress clientSocket) throws IOException {
        InetSocketAddress socket = new InetSocketAddress("localhost", 2501);
        this.databaseSocket = null;
        this.clientSocket = clientSocket;
        this.serverCore = HttpServer.create(socket, 0);
    }

    public Server(InetSocketAddress clientSocket, InetSocketAddress databaseSocket) throws IOException {
        InetSocketAddress socket = new InetSocketAddress("localhost", 2501);
        this.databaseSocket = databaseSocket;
        this.clientSocket = clientSocket;
        this.serverCore = HttpServer.create(socket, 0);
    }

    @Override
    public void bind(InetSocketAddress addr, int backlog) throws IOException {
        serverCore.bind(addr, backlog);
    }

    @Override
    public void start() {
        serverCore.start();
    }

    @Override
    public void setExecutor(Executor executor) {
        serverCore.setExecutor(executor);
    }

    @Override
    public Executor getExecutor() {
        return serverCore.getExecutor();
    }

    @Override
    public void stop(int delay) {
        serverCore.stop(delay);
    }

    @Override
    public HttpContext createContext(String path, HttpHandler handler) {
        return serverCore.createContext(path, handler);
    }

    @Override
    public HttpContext createContext(String path) {
        return serverCore.createContext(path);
    }

    @Override
    public void removeContext(String path) throws IllegalArgumentException {
        serverCore.removeContext(path);
    }

    @Override
    public void removeContext(HttpContext context) {
        serverCore.removeContext(context);
    }

    public boolean isDatabaseReachable() throws IOException, NullPointerException {
        if(databaseSocket == null)
            throw new NullPointerException();
        if(!databaseSocket.getAddress().isReachable(100))
            throw new ConnectException();
        return true;
    }

    public boolean isClientReachable() throws IOException
    {
        if(!clientSocket.getAddress().isReachable(100))
            throw new ConnectException();
        return true;
    }

    @Override
    public InetSocketAddress getAddress() {
        return serverCore.getAddress();
    }

}
