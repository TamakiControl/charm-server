package com.tamakicontrol.utils;

import com.tamakicontrol.CharmTestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CharmTestResultParser {

    private static final Logger logger = LoggerFactory.getLogger(CharmTestResultParser.class);

    public static CharmTestResult parse(String message) throws ParseException {
        CharmTestResult testResult = new CharmTestResult();

        try {

            // This line of code can separate message and store them in the split array by ","
            String[] split = message.split(",");

            if (split.length < 155)
                throw new ParseException("Was not Provided CSV Input", 0);

            // The very fist character EZ reader send out is a "<", which is not useful to us,
            // so we want to remove "<" and store it in testOutputName.
            testResult.setTestOutputName(split[0].replace("<", ""));
            testResult.setStructVersion(split[1]);
            testResult.setChannelNumber(split[2]);
            testResult.setAssay(split[3]);

            // EZ reader send out date and time in two separate field. We want them to be stored in
            // a single variable date.
            // The below code convert the original output(28SEP2020,14:10:21)
            // into Mon Sep 28 14:10:21 IST 2020, which meet the standard format of Date datatype.
            String sDate1 = split[4] + " " + split[5];
            Date date1 = new SimpleDateFormat("ddMMMyyyy hh:mm:ss").parse(sDate1);
            testResult.setDate(date1);

            testResult.setResult(split[6]);
            testResult.setAnswer(split[7]);
            testResult.setUnitSN(split[8]);
            testResult.setLotNumber(split[9]);
            testResult.setSampleID(split[10]);
            testResult.setOperatorID(split[11]);
            testResult.setMode(split[12]);
            testResult.setFailCode(split[13]);
            testResult.setControlLINE(split[14]);
            testResult.setTestLINE(split[15]);
            testResult.setXLine(split[16]);
            testResult.setInterpretation(split[17]);
            testResult.setTestTemperature(split[18]);

            // There are 128 remission data points coming out from EZ reade,
            // we want to store them individually.
            String[] charmRemissions = new String[128];
            for (int i = 0; i < 128; i++) {
                charmRemissions[i] = (split[i + 19]);
            }

            testResult.setRemissions(charmRemissions);

            testResult.setTipRed(split[147]);
            testResult.setTipGreen(split[148]);
            testResult.setTipBlue(split[149]);
            testResult.setDirt(split[150]);
            testResult.setFlow(split[151]);
            testResult.setInterpString(split[152]);
            testResult.setY_Line(split[153]);

            // The transmission message from EZ reader is ended by a ">"
            testResult.setZ_Line(split[154].replace(">", "").replace("\n", ""));

        } catch (Exception e) {
            logger.error("Failed to parse", e);
            throw new ParseException("Failed to Parse", 0);
        }

        return testResult;
    }


    private static Integer parseInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static Double parseDouble(String str) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }

}



    
    
