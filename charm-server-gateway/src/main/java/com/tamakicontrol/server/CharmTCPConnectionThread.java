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

public class CharmTCPConnectionThread extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(CharmTCPConnectionThread.class);

    private final Socket socket;
    public CharmTCPConnectionThread(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader is;

        try{
            is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }catch (IOException e){
            logger.error("IOException Thrown While Setting Up Charm TCP Connection", e);
            return;
        }

        try{
            // todo this is a potential security problem if somebody sent us a very very very long message.  Need to limit the size somehow
            StringBuilder messageBuilder = new StringBuilder();
            String line = is.readLine();
            messageBuilder.append(line);
            while(line != null){
                logger.trace(String.format("Read From Charm Socket: %s", line));
                messageBuilder.append(line);
                line = is.readLine();
            }

            // build test result object from raw TCP data
            CharmTestResult testResult = CharmTestResultParser.parse(messageBuilder.toString());

            // publish data from test result to tag provider
            String tagRootPath = String.format("Unit %d", testResult.getSerialNo());
            POJOToTagMapper<CharmTestResult> resultMapper = new POJOToTagMapper<>(tagRootPath, testResult);
            resultMapper.configureTags();
            resultMapper.updateTags();

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
