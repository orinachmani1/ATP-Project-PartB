package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server implements IServerStrategy {

    private int port;
    private int listeningIntervalMS;
    private IServerStrategy strategy;
    private volatile boolean stop;
   //private final Logger LOG = LogManager.getLogger(); //
    private ExecutorService threadPool;


    public Server(int port, int listeningIntervalMS, IServerStrategy strategy) {
        this.port = port;
        this.listeningIntervalMS = listeningIntervalMS;
        this.strategy = strategy;
        this.stop = false;
        this.threadPool = Executors.newFixedThreadPool(3);
        //this.threadPool = Executors.newFixedThreadPool(Configurations.getNumOfThreads());
}

    public void runServer(){
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningIntervalMS);
            //LOG.info("Starting server at port = " + port);

            while (!stop) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    //LOG.info("Client accepted: " + clientSocket.toString());

                    //This thread will handle the new Client
                    /*threadPool.execute(() -> {
                        handleClient(clientSocket);
                    });*/

                    Thread t = new Thread(() -> {
                        handleClient(clientSocket);
                    });
                    threadPool.execute(t);

                } catch (SocketTimeoutException e){
                    //LOG.debug("Socket timeout");
                }
            }
            threadPool.shutdown();
            serverSocket.close();
        } catch (IOException e) {
            //LOG.error("IOException", e);
        }
    }

    private void handleClient(Socket clientSocket) {
        try {
            strategy.serverStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
            //LOG.info("Done handling client: " + clientSocket.toString());
            clientSocket.close();
        } catch (IOException e){
            //LOG.error("IOException", e);
        }
    }

    public void stop(){
        //LOG.info("Stopping server...");
        stop = true;
    }

    public void start() {
        new Thread(() -> {
            runServer();
        }).start();
    }
    @Override
    public void serverStrategy(InputStream inputStream, OutputStream outputStream) { }
}
