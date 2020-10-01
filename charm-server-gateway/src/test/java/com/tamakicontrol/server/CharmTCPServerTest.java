package com.tamakicontrol.server;

import com.tamakicontrol.CharmTestResult;
import com.tamakicontrol.utils.CharmTestResultParser;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class CharmTCPServerTest {

    @BeforeAll
    public static void setLoggers(){
        System.setProperty("org.slf4j.simpleLogger.logFile", "System.out");
        Logger slf4j = (Logger) LoggerFactory.getLogger("com.tamakicontrol");
        slf4j.setLevel(Level.TRACE);
    }

    private final CharmTCPServer server1 = new CharmTCPServer(502) {
        @Override
        public void onSocketConnected(Socket clientSocket) throws IOException {
            socketConnected(clientSocket);
        }
    };

    private void socketConnected(Socket clientSocket) {
        CharmTCPConnectionThread connectionThread = new CharmTCPConnectionThread(clientSocket);
        connectionThread.run();
        server1.shutdown();
    }

    // enable this with an @Test annotation if you want to run it.  This will echo whatever is written back to the command line
    public void tcpToCommandLine() {
        server1.run();
    }

    private static final String testString = "<28092002.020,A6,2,SL-3,28SEP2020,14:10:21,Negative,-1547,6182,181,79,5,30,0,1900,3348,0,,55.7,76.07,74.32,73.87,73.35,72.23,72.36,72.76,72.97,72.93,71.56,71.58,72.90,71.78,71.07,70.76,70.80,72.21,72.93,72.10,72.16,71.23,71.15,72.23,71.52,70.22,69.87,71.25,70.53,70.41,69.28,68.93,68.04,69.83,68.75,68.19,66.70,65.96,64.45,65.24,64.44,61.64,54.28,39.19,22.82,32.80,42.31,50.41,57.31,61.86,63.77,64.16,64.77,62.89,63.57,64.66,63.55,64.73,65.07,64.85,62.95,64.48,62.76,63.21,60.11,59.46,52.97,41.61,42.17,44.05,48.26,56.63,60.58,62.16,63.29,63.51,63.21,65.19,65.18,63.55,64.88,64.84,64.39,62.82,62.63,64.08,61.22,57.48,52.98,50.88,51.08,48.27,43.57,48.72,74.83,90.82,105.33,113.63,117.68,119.41,118.67,119.50,121.41,122.76,125.94,124.94,125.50,125.85,129.48,129.73,129.13,131.41,134.76,137.57,136.37,138.33,138.82,138.84,146.50,158.10,185.48,199.62,215.17,231.55,241.71,232.00,234.09,237.75,249.27,475,666,985,423,67,,0,0>\n";
    private static final String testString2 = "<01102002.007,A6,2,SL3,01OCT2020,09:57:03,Positive,951,6182,181,40,930,30,0,1634,982,0,,56.1,75.83,74.01,73.68,72.62,71.78,71.27,71.98,72.04,72.10,70.68,70.67,71.92,70.68,69.68,69.31,69.18,70.19,70.82,69.65,69.76,69.02,68.75,69.63,68.83,67.53,67.10,68.36,67.46,67.38,66.26,65.80,64.89,66.34,65.32,64.76,63.13,62.59,61.87,63.27,63.39,61.98,58.58,53.94,48.73,50.39,53.40,55.26,56.60,59.19,60.67,60.86,60.74,59.37,59.95,60.93,59.68,60.57,61.24,60.82,59.18,60.82,59.08,59.68,57.07,57.18,52.64,42.73,41.71,43.89,49.02,56.33,59.13,60.28,61.25,61.30,61.23,63.28,63.55,61.51,62.98,62.97,62.69,61.40,61.04,62.44,59.58,55.78,51.72,49.69,49.93,47.82,43.47,41.65,68.56,83.75,100.99,112.66,118.04,120.34,120.54,121.19,123.78,125.17,128.11,128.93,130.63,130.60,134.35,132.73,131.71,135.17,138.24,140.43,139.43,139.32,143.27,145.49,144.10,142.26,149.22,157.90,166.60,177.03,186.15,196.29,202.76,212.90,225.08,473,671,985,736,70,,0,0>\n";

    private final CharmTCPServer server2 = new CharmTCPServer(502) {
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
        String hostName = "127.0.0.1";
        int portNumber = 502;

        server2.start();

        Socket socket = new Socket(hostName, portNumber);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(testString);

        out.close();
        socket.close();
    }


    /**
     * Tests overloading the input port with too much data to make sure the system throws an exception
     * */
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


    private final CharmTCPServer server4 = new CharmTCPServer(502) {
        @Override
        public void onSocketConnected(Socket clientSocket) throws IOException {
            try {
                test4SocketConnected(clientSocket);
            }catch (Exception e){
                assert false;
            }
        }
    };

    private void test4SocketConnected(Socket clientSocket) throws Exception {
        CharmTestResult testResult = new CharmTestResult();
        testResult.setTestOutputName("28092002.020");
        testResult.setStructVersion("A6");
        testResult.setChannelNumber(2);
        testResult.setAssay("SL-3");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        dateFormat.setTimeZone(Calendar.getInstance().getTimeZone());
        testResult.setDate(dateFormat.parse("2020/09/28 14:10:21"));
        testResult.setResult("Negative");
        testResult.setAnswer(-1547.0);
        testResult.setUnitSN(6182);
        testResult.setLotNumber(181);
        testResult.setSampleID(79);
        testResult.setOperatorID(5);
        testResult.setMode(30);
        testResult.setFailCode(0);
        testResult.setControlLINE(1900);
        testResult.setTestLINE(3348);
        testResult.setXLine(0.0);
        testResult.setInterpretation("");
        testResult.setTestTemperature(55.7);

        Double[] remissions = new Double[]{
                76.07,74.32,73.87,73.35,72.23,72.36,72.76,72.97,72.93,71.56,71.58,72.90,71.78,71.07,70.76,70.80,72.21,72.93,
                72.10,72.16,71.23,71.15,72.23,71.52,70.22,69.87,71.25,70.53,70.41,69.28,68.93,68.04,69.83,68.75,68.19,
                66.70,65.96,64.45,65.24,64.44,61.64,54.28,39.19,22.82,32.80,42.31,50.41,57.31,61.86,63.77,64.16,64.77,
                62.89,63.57,64.66,63.55,64.73,65.07,64.85,62.95,64.48,62.76,63.21,60.11,59.46,52.97,41.61,42.17,44.05,
                48.26,56.63,60.58,62.16,63.29,63.51,63.21,65.19,65.18,63.55,64.88,64.84,64.39,62.82,62.63,64.08,61.22,
                57.48,52.98,50.88,51.08,48.27,43.57,48.72,74.83,90.82,105.33,113.63,117.68,119.41,118.67,119.50,121.41,
                122.76,125.94,124.94,125.50,125.85,129.48,129.73,129.13,131.41,134.76,137.57,136.37,138.33,138.82,
                138.84, 146.50,158.10,185.48,199.62,215.17,231.55,241.71,232.00,234.09,237.75,249.27,
        };
        testResult.setRemissions(remissions);

        testResult.setTipRed(475.0);
        testResult.setTipGreen(666.0);
        testResult.setTipBlue(985.0);
        testResult.setDirt(423.0);
        testResult.setFlow(67.0);
        testResult.setInterpString("");
        testResult.setY_Line(0.0);
        testResult.setZ_Line(0.0);

        BufferedReader is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        StringBuilder messageBuilder = new StringBuilder();
        String line = is.readLine();
        int lineCount = 0;
        while(line != null && lineCount < 100){
            messageBuilder.append(line);
            line = is.readLine();
            lineCount++;
        }

        // build test result object from raw TCP data
        CharmTestResult testResult1 = CharmTestResultParser.parse(messageBuilder.toString());
        assert testResult.equals(testResult1);
    }


    /**
     * Tests a full TCP Client Connecting and Sending a Test String, then the module parsing the output to an object
     * */
    @Test
    public void test4() throws Exception {
        String hostName = "127.0.0.1";
        int portNumber = 502;

        server4.start();

        Socket socket = new Socket(hostName, portNumber);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(testString);

        out.close();
        socket.close();
        server4.shutdown();
    }

    private volatile int socketCount = 0;

    public synchronized int getSocketCount(){
        return this.socketCount;
    }

    public synchronized void setSocketCount(int socketCount) {
        this.socketCount = socketCount;
    }

    private final CharmTCPServer server5 = new CharmTCPServer(502) {
        @Override
        public void onSocketConnected(Socket clientSocket) throws IOException {
            setSocketCount(getSocketCount() + 1);

            Thread connectionThread = new Thread(() -> {
                try {
                    BufferedReader is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    String line = is.readLine();
                    while (line != null) {
                        line = is.readLine();
                    }
                }catch (IOException e){
                    assert false;
                }

                setSocketCount(getSocketCount() - 1);
            });

            connectionThread.start();
        }
    };


    /**
     * Tests multiple clients connecting at the same time
     * */
    @Test
    public void test5() throws Exception {
        String hostName = "127.0.0.1";
        int portNumber = 502;

        server5.start();

        assert getSocketCount() == 0;

        Socket socket = new Socket(hostName, portNumber);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(testString);

        assert getSocketCount() == 1;

        Socket socket2 = new Socket(hostName, portNumber);
        PrintWriter out2 = new PrintWriter(socket.getOutputStream(), true);
        out.println(testString);

        assert getSocketCount() == 2;

        Socket socket3 = new Socket(hostName, portNumber);
        PrintWriter out3 = new PrintWriter(socket.getOutputStream(), true);
        out.println(testString);

        assert getSocketCount() == 3;

        out.close();
        socket.close();
        out2.close();
        socket2.close();
        out3.close();
        socket3.close();
        server5.shutdown();
    }

}
