package com.tamakicontrol.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class CharmTCPConnectionThread extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(CharmTCPConnectionThread.class);

    private final Socket socket;
    private BufferedReader is;

    public CharmTCPConnectionThread(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try{
            is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }catch (IOException e){
            logger.error("IOException Thrown While Setting Up Charm TCP Connection", e);
            return;
        }

        try{
            String line = is.readLine();
            while(line != null){
                logger.trace(String.format("Read From Charm Socket: %s", line));
                line = is.readLine();
            }

            //todo things

        }catch (IOException e){
            logger.error("IOException While Handling Input from Charm TCP Socket", e);
        }finally {
            try {
                socket.close();
                is.close();
                logger.trace("Sockets Closed");
            }catch(IOException e){
                logger.warn("Exception Thrown While Closing IO Socket", e);
            }
        }
    }

}
