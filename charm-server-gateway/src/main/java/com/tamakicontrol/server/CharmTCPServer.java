package com.tamakicontrol.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * Sets up a socket to listen on the specified port.  This class is abstracted
 * so that it can be implemented multiple different ways (mostly useful for JUnit tests at this point).
 * */
public abstract class CharmTCPServer extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(CharmTCPServer.class);
    private ServerSocket serverSocket;
    private final int port;

    public CharmTCPServer(int port){
        this.port = port;
    }

    private boolean enabled = true;
    public synchronized void setEnabled(final boolean enabled){
        logger.debug("Disabling CHARM Server");
        this.enabled = enabled;
    }

    public boolean isEnabled(){
        return enabled;
    }

    public synchronized void shutdown(){
        logger.debug("Shutting down CHARM server");
        setEnabled(false);
        close();
    }

    public int getPort(){
        return port;
    }

    @Override
    public void run(){
        logger.trace("Starting Charm TCP Server");
        try {
            serverSocket = new ServerSocket(port);
            logger.debug(String.format("CHARM Server Listening on Port %d", port));

            while(enabled){
                Socket socket = serverSocket.accept();
                onSocketConnected(socket);
            }
        }catch (SocketException e){
            // This is expected behavior when the server gracefully closes
            logger.debug("Socket Closed While Waiting for Connections", e);
        } catch (IOException e) {
            logger.error("IOException While Listening on Server", e);
        } finally {
            logger.info("CHARM Server Socket Shutting Down");
            close();
        }
    }

    /**
     * Close the server socket
     * */
    public void close(){
        if(serverSocket == null)
            return;

        try {
            if(!serverSocket.isClosed())
                serverSocket.close();
        }catch (IOException e){
            logger.warn("CHARM Server Socket Did Not Cleanly Close", e);
        }
    }

    public abstract void onSocketConnected(Socket clientSocket) throws IOException;

}
