package com.tamakicontrol.server;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class CharmTCPServerTest {

    @BeforeAll
    public static void setLoggers(){
        System.setProperty("org.slf4j.simpleLogger.logFile", "System.out");
        Logger slf4j = (Logger) LoggerFactory.getLogger("com.tamakicontrol");
        slf4j.setLevel(Level.TRACE);
    }

    private CharmTCPServer server1 = new CharmTCPServer(502) {
        @Override
        public void onSocketConnected(Socket clientSocket) throws IOException {
            socketConnected(clientSocket);
        }
    };

    private void socketConnected(Socket clientSocket) throws IOException {
        Thread connectionThread = new CharmTCPConnectionThread(clientSocket);
        connectionThread.run();
        server1.shutdown();
    }

    // enable this with an @Test annotation if you want to run it.  This will echo whatever is written back to the command line
    public void tcpToCommandLine() {
        server1.run();
    }

    private static final String testString = "<28092002.020,A6,2,SL-3,28SEP2020,14:10:21,Negative,-1547,6182,181,79,5,30,0,1900,3348,0,,55.7,76.07,74.32,73.87,73.35,72.23,72.36,72.76,72.97,72.93,71.56,71.58,72.90,71.78,71.07,70.76,70.80,72.21,72.93,72.10,72.16,71.23,71.15,72.23,71.52,70.22,69.87,71.25,70.53,70.41,69.28,68.93,68.04,69.83,68.75,68.19,66.70,65.96,64.45,65.24,64.44,61.64,54.28,39.19,22.82,32.80,42.31,50.41,57.31,61.86,63.77,64.16,64.77,62.89,63.57,64.66,63.55,64.73,65.07,64.85,62.95,64.48,62.76,63.21,60.11,59.46,52.97,41.61,42.17,44.05,48.26,56.63,60.58,62.16,63.29,63.51,63.21,65.19,65.18,63.55,64.88,64.84,64.39,62.82,62.63,64.08,61.22,57.48,52.98,50.88,51.08,48.27,43.57,48.72,74.83,90.82,105.33,113.63,117.68,119.41,118.67,119.50,121.41,122.76,125.94,124.94,125.50,125.85,129.48,129.73,129.13,131.41,134.76,137.57,136.37,138.33,138.82,138.84,146.50,158.10,185.48,199.62,215.17,231.55,241.71,232.00,234.09,237.75,249.27,475,666,985,423,67,,0,0\n";

    private CharmTCPServer server2 = new CharmTCPServer(502) {
        @Override
        public void onSocketConnected(Socket clientSocket) throws IOException {
            test2SocketConnected(clientSocket);
        }
    };

    private void test2SocketConnected(Socket socket) throws IOException {
        BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        StringBuilder messageBuilder = new StringBuilder();
        String line = is.readLine();
        messageBuilder.append(line);
        while(line != null){
            messageBuilder.append(line);
            line = is.readLine();
        }

        String message = messageBuilder.toString();
        assert message.contentEquals(message);

        server2.shutdown();
    }

    @Test
    public void test2() throws Exception {

        System.setProperty("org.slf4j.simpleLogger.logFile", "System.out");
        Logger slf4j = (Logger) LoggerFactory.getLogger("com.tamakicontrol");
        slf4j.setLevel(Level.TRACE);

        String hostName = "127.0.0.1";
        int portNumber = 502;

        server2.start();

        Socket socket = new Socket(hostName, portNumber);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(testString);

        out.close();
        socket.close();
    }

    public void test3() throws Exception {

        //server1.start();
        String hostName = "127.0.0.1";
        int portNumber = 502;

        Socket socket = new Socket(hostName, portNumber);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        Random rnd = new Random();

        //IOUtils.copy(rnd.doubles(1000), socket.getOutputStream());

        out.close();
        socket.close();

    }

}
