package com.tamakicontrol.server;

import org.python.modules._py_compile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class CharmTCPServer implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(CharmTCPServer.class);
    private ServerSocket serverSocket;
    private final int port;

    public CharmTCPServer(int port){
        this.port = port;
    }

    private boolean enabled = true;
    public synchronized void enable(){
        this.enabled = true;
    }

    public synchronized void disable(){
        this.enabled = false;
    }

    public synchronized void shutdown(){
        disable();
        close();
    }

    @Override
    public void run(){
        try {
            serverSocket = new ServerSocket(port);
            logger.debug(String.format("CHARM Server Listeneing on Port %d", port));

            while(enabled){
                Socket socket = serverSocket.accept();
                onSocketConnected(socket);
            }

        } catch (IOException e) {
            logger.error("IOException While Listening on Server", e);
        } finally {
            close();
        }
    }

    public void close(){
        try {
            serverSocket.close();
        }catch (IOException e){
            logger.warn("CHARM Server Socket Did not Close Neatly", e);
        }
    }

    public abstract void onSocketConnected(Socket clientSocket) throws IOException;

}
