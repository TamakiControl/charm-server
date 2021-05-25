package com.tamakicontrol.server;

import com.tamakicontrol.CharmTestResult;
import com.tamakicontrol.utils.CharmTestResultParser;
import com.tamakicontrol.utils.POJOToTagMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.text.ParseException;

/**
 * Whenever a TCP connection is made to the Server, this thread will spawn and handle the
 * result.  This keeps the server free to continue accepting more connections should
 * more than 1 CHARM reader send data at once.
 * */
public class CharmTCPConnectionThread implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(CharmTCPConnectionThread.class);
    private final Socket socket;

    public CharmTCPConnectionThread(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        logger.trace("Starting Charm TCP Connection Thread");
        BufferedReader is;

        try{
            is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }catch (IOException e){
            logger.error("IOException Thrown While Setting Up Charm TCP Connection", e);
            return;
        }

        try{
            StringBuilder messageBuilder = new StringBuilder();
            String line = is.readLine();
            int lineCount = 0;
            while(line != null && lineCount < 100){
                logger.trace(String.format("Read From Charm Socket: %s", line));
                messageBuilder.append(line);
                line = is.readLine();
                lineCount++;
            }

            // build test result object from raw TCP data
            CharmTestResult testResult = CharmTestResultParser.parse(messageBuilder.toString());

            // publish data from test result to tag provider
            String tagRootPath1 = "Last Result/";
            POJOToTagMapper<CharmTestResult> resultMapper1 = new POJOToTagMapper<>(tagRootPath1, testResult);

            String tagRootPath2 = "UnitSN_" + testResult.getUnitSN() + "/";
            POJOToTagMapper<CharmTestResult> resultMapper2 = new POJOToTagMapper<>(tagRootPath2, testResult);

            resultMapper1.configureTags();
            resultMapper1.updateTags();

            resultMapper2.configureTags();
            resultMapper2.updateTags();

        }catch (IOException e){
            logger.error("IOException While Handling Input from Charm TCP Socket", e);
        }catch (ParseException e){
            logger.warn("Failed to parse message from CHARM reader", e);
        } finally {
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
